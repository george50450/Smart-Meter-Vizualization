package com.example.gwf_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gwf_app.Login.LoginRequest;
import com.example.gwf_app.Login.LoginResponse;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView emailTxt, passwordTxt;
    EditText emailInpt, passwordInpt;
    Button loginBtn;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        TextView emailTxt = (TextView) findViewById(R.id.emailtxt);
        TextView passwordTxt = (TextView) findViewById(R.id.passwordtxt);

        EditText emailInpt = (EditText) findViewById(R.id.emailinpt);
        EditText passwordInpt = (EditText) findViewById(R.id.passwordinpt);

        loginBtn = (Button) findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setEnabled(false);
                //First check if internet connection is established
                if(isNetworkAvailable()) {

                    spinner.setVisibility(View.VISIBLE);
                    if ((emailInpt.getText().toString().matches("")) || (passwordInpt.getText().toString().toString().matches(""))) {
                        Toast.makeText(getApplicationContext(), "Please fill the necessary credentials", Toast.LENGTH_LONG).show();
                        spinner.setVisibility(View.GONE);
                        loginBtn.setEnabled(true);
                    }
                    else {

                        //try login
                        LoginRequest loginRequest = new LoginRequest();
                        loginRequest.setEmail(emailInpt.getText().toString());
                        loginRequest.setPassword(passwordInpt.getText().toString());

                        Call<LoginResponse> loginResponseCall = RetroClient.getLoginService().userLogin(loginRequest);

                        loginResponseCall.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Successfull Login", Toast.LENGTH_LONG);

                                    LoginResponse loginResponse = response.body();
                                    Log.d("Access: ", loginResponse.getAccess());

                                    //Get measures
                                    getMeasures(emailInpt.getText().toString(), passwordInpt.getText().toString(), loginResponse.getAccess(), loginResponse.getRefresh(),spinner);

                                } else {
                                    Log.d("Loging failed" ,"fail");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loginBtn.setEnabled(true);
                                            spinner.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Wrong credentials...Please try again!", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Internet connection is not available", Toast.LENGTH_LONG).show();
                    loginBtn.setEnabled(true);
                }
            }
        });
    }

    public void getMeasures(String email, String password, String  access, String refresh, ProgressBar spinner) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://test-api.gwf.ch/reports/measurements/";
        Request request = new Request.Builder()
                .url(url)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + access)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {


            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {

               if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Log.d("get resp: ", myResponse);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //if we get response stop spinner
                            spinner.setVisibility(View.GONE);
                            loginBtn.setEnabled(true);
                        }
                    });

                    try {

                        JSONArray Response = new JSONArray(myResponse);
                        //Log.d("length array: ", Response.length() +"");

                        Intent intent = new Intent(getApplicationContext(), ListMeasures.class);

                        Bundle b = new Bundle();
                        b.putString("Array", Response.toString());
                        intent.putExtras(b);
                        intent.putExtra("email",  email);
                        intent.putExtra("password",  password);
                        intent.putExtra("access",  access);
                        intent.putExtra("refresh",  refresh);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               else {


                   Log.d("Service not available" ,"fail");
                   runOnUiThread(new Runnable() {


                       @Override
                       public void run() {

                           spinner.setVisibility(View.GONE);

                           Toast.makeText(getApplicationContext(), "Service not available", Toast.LENGTH_LONG).show();
                       }
                   });
               }
            }

            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}

