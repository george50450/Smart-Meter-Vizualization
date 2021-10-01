package com.example.gwf_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gwf_app.Refresh.RefreshRequest;
import com.example.gwf_app.Refresh.RefreshResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMeasures extends AppCompatActivity {
    private ArrayList<String> latitudes = new ArrayList<String>();
    private ArrayList<String> longitudes = new ArrayList<String>();
    private ArrayList<String> mp_names = new ArrayList<String>();
    private ArrayList<String> meter_ids = new ArrayList<String>();
    private ArrayList<String> meter_types = new ArrayList<String>();
    private ArrayList<String> last_entries = new ArrayList<String>();
    private ArrayList<String> volumes = new ArrayList<String>();
    private ArrayList<String> comm_mod_types= new ArrayList<String>();
    private ArrayList<String> comm_mod_serials = new ArrayList<String>();
    private ArrayList<String> battery_lifetimes = new ArrayList<String>();
    private ArrayList<String> broken_pipes = new ArrayList<>();
    private ArrayList<String> continuous_flows = new ArrayList<>();
    private ArrayList<String> battery_lows = new ArrayList<>();
    private ArrayList<String> backflows = new ArrayList<>();
    private ArrayList<String> communication_errors = new ArrayList<>();
    private ArrayList<String> parsing_errors = new ArrayList<>();
    private ArrayList<String> encoder_errors = new ArrayList<>();
    private ArrayList<String> us_water_levels = new ArrayList<>();
    private ArrayList<String> v_sensor_comm_timouts = new ArrayList<>();
    private ArrayList<String> water_level_errors = new ArrayList<>();
    private ArrayList<String> t_air_errors = new ArrayList<>();
    private ArrayList<String> t_water_errors = new ArrayList<>();
    private ArrayList<String> w_air_errors = new ArrayList<>();
    private ArrayList<String> w_water_errors = new ArrayList<>();
    private ArrayList<String> velocity_errors = new ArrayList<>();
    private ArrayList<String> system_errors = new ArrayList<>();


    private JSONArray updated_listings = new JSONArray();
    private List<Object> recyclerItems = new ArrayList<>();

    String access, refresh, email, password;
    String latitude="",longitude="", mp_name = "",meter_id="",meter_type="",last_entry="",volume="", comm_mod_type="", comm_mod_serial="", battery_lifetime="", continuous_flow="", broken_pipe="",battery_low="",backflow="", communication_error="", parsing_error="", encoder_error="";
    String us_water_level="",v_sensor_comm_timout="", water_level_error="",t_air_error="",t_water_error="",w_air_error="",w_water_error="",velocity_error="",system_error="";
    String totalMeters="", totalUsage="", totalAlerts="", totalReadouts="";

    RecyclerView recyclerView;
    RecyclerListAdapter adapter;

    Toolbar mainToolbar;
    SearchView searchView;
    MenuItem searchItem;

    TextView metersTxt, waterusgTxt, alertTxt, readoutsTxt;


    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_measures);

        mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        metersTxt = (TextView)findViewById(R.id.meters);
        waterusgTxt = (TextView)findViewById(R.id.water_usg);
        alertTxt = (TextView)findViewById(R.id.alert);
        readoutsTxt = (TextView)findViewById(R.id.readouts);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Meters");
        mainToolbar.setTitleTextColor(Color.WHITE);

        //Get stuff from Main activity
        Bundle b = getIntent().getExtras();
        String Array = b.getString("Array");
        access = b.getString("access");
        refresh = b.getString("refresh");
        email = b.getString("email");
        password = b.getString("password");


        try {
            JSONArray jsonArray = new JSONArray(Array);
            //Log.d("jsonarray", jsonArray +"");
            if (jsonArray != null && jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    mp_name = "";
                    try {
                        //latitudes
                        latitude = object.get("lat").toString();
                        if (!(TextUtils.isEmpty(latitude))) {
                            latitudes.add(latitude);
                        } else {
                            latitudes.add("-");
                        }

                        //longitudes
                        longitude = object.get("lng").toString();
                        if (!(TextUtils.isEmpty(longitude))) {
                            longitudes.add(longitude);
                        } else {
                            longitudes.add("-");
                        }

                        //mp_names
                        mp_name = object.get("mp_name").toString();
                        if (!(TextUtils.isEmpty(mp_name))) {
                            mp_names.add(mp_name);
                        } else {
                            mp_names.add("");
                        }

                        //meter_ids
                        meter_id = object.get("meter_id").toString();
                        if (!(TextUtils.isEmpty(meter_id))) {
                            meter_ids.add(meter_id);
                        } else {
                            meter_ids.add("-");
                        }

                        //meter_type
                        meter_type = object.get("meter_type").toString();
                        if (!(TextUtils.isEmpty(meter_type))) {
                            meter_types.add(meter_type);
                        } else {
                            meter_types.add("-");
                        }


                        //in case of fuelicsencoder
                        if(object.has("comm_mod_type") && !object.isNull("comm_mod_type")) {
                            comm_mod_type = object.get("comm_mod_type").toString();
                            if (!(TextUtils.isEmpty(comm_mod_type))) {
                                comm_mod_types.add(comm_mod_type);
                            } else {
                                comm_mod_types.add("-");
                            }
                        }
                        else {
                            comm_mod_type = "";
                            comm_mod_types.add("");
                        }

                        if(object.has("comm_mod_serial") && !object.isNull("comm_mod_serial")) {
                            comm_mod_serial = object.get("comm_mod_serial").toString();
                            if (!(TextUtils.isEmpty(comm_mod_serial))) {
                                comm_mod_serials.add(comm_mod_serial);
                            } else {
                                comm_mod_serials.add("-");
                            }
                        }
                        else {
                            comm_mod_serial = "";
                            comm_mod_serials.add("");
                        }


                        //last_entry
                        last_entry = object.get("last_entry").toString();
                        if (!(TextUtils.isEmpty(last_entry))) {
                            last_entries.add(last_entry);
                        } else {
                            last_entries.add("-");
                        }

                        //volumes
                        volume = object.get("volume").toString();
                        if (!(TextUtils.isEmpty(volume))) {
                            volumes.add(volume);
                        } else {
                            volumes.add("-");
                        }

                        //battery lifetime
                        if(object.has("battery_lifetime") && !object.isNull("battery_lifetime")) {
                            battery_lifetime = object.get("battery_lifetime").toString();
                            if (!(TextUtils.isEmpty(battery_lifetime))) {
                                battery_lifetimes.add(battery_lifetime);
                            } else {
                                battery_lifetimes.add("-");
                            }
                        }
                        else {
                            battery_lifetime = "";
                            battery_lifetimes.add("");
                        }

                        //state!!
                        if(object.has("state") && !object.isNull("state")) {
                            JSONObject state = (JSONObject) object.get("state");
                            //Log.d("state: ", state.toString());
                            if(state.has("continuous_flow") && !state.isNull("continuous_flow")) {
                                try {
                                    continuous_flow = state.get("continuous_flow").toString();
                                    if (!(TextUtils.isEmpty(continuous_flow))) {
                                        continuous_flows.add(continuous_flow);
                                    } else {
                                        continuous_flows.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                continuous_flows.add("");
                            }

                            if(state.has("broken_pipe") && !state.isNull("broken_pipe")) {
                                try {
                                    broken_pipe = state.get("broken_pipe").toString();
                                    if (!(TextUtils.isEmpty(broken_pipe))) {
                                        broken_pipes.add(broken_pipe);
                                    } else {
                                        broken_pipes.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                broken_pipes.add("");
                            }

                            if(state.has("battery_low") && !state.isNull("battery_low")) {
                                try {
                                    battery_low = state.get("battery_low").toString();
                                    if (!(TextUtils.isEmpty(battery_low))) {
                                        battery_lows.add(battery_low);
                                    } else {
                                        battery_lows.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                battery_lows.add("");
                            }

                            if(state.has("backflow") && !state.isNull("backflow")) {
                                try {
                                    backflow = state.get("backflow").toString();
                                    if (!(TextUtils.isEmpty(backflow))) {
                                        backflows.add(backflow);
                                    } else {
                                        backflows.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                backflows.add("");
                            }

                            if(state.has("communication_error") && !state.isNull("communication_error")) {
                                try {
                                    communication_error = state.get("communication_error").toString();
                                    if (!(TextUtils.isEmpty(communication_error))) {
                                        communication_errors.add(communication_error);
                                    } else {
                                        communication_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                communication_errors.add("");
                            }

                            if(state.has("parsing_error") && !state.isNull("parsing_error")) {
                                try {
                                    parsing_error = state.get("parsing_error").toString();
                                    if (!(TextUtils.isEmpty(parsing_error))) {
                                        parsing_errors.add(parsing_error);
                                    } else {
                                        parsing_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                parsing_errors.add("");
                            }

                            if(state.has("encoder_error") && !state.isNull("encoder_error")) {
                                try {
                                    encoder_error = state.get("encoder_error").toString();
                                    if (!(TextUtils.isEmpty(encoder_error))) {
                                        encoder_errors.add(encoder_error);
                                    } else {
                                        encoder_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                encoder_errors.add("");
                            }


                            if(state.has("us_water_level") && !state.isNull("us_water_level")) {
                                try {
                                    us_water_level = state.get("us_water_level").toString();
                                    if (!(TextUtils.isEmpty(us_water_level))) {
                                        us_water_levels.add(us_water_level);
                                    } else {
                                        us_water_levels.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                us_water_levels.add("");
                            }

                            if(state.has("v_sensor_comm_timout") && !state.isNull("v_sensor_comm_timout")) {
                                try {
                                    v_sensor_comm_timout = state.get("v_sensor_comm_timout").toString();
                                    if (!(TextUtils.isEmpty(v_sensor_comm_timout))) {
                                        v_sensor_comm_timouts.add(v_sensor_comm_timout);
                                    } else {
                                        v_sensor_comm_timouts.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                v_sensor_comm_timouts.add("");
                            }

                            if(state.has("water_level_error") && !state.isNull("water_level_error")) {
                                try {
                                    water_level_error = state.get("water_level_error").toString();
                                    if (!(TextUtils.isEmpty(water_level_error))) {
                                        water_level_errors.add(water_level_error);
                                    } else {
                                        water_level_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                water_level_errors.add("");
                            }

                            if(state.has("t_air_error") && !state.isNull("t_air_error")) {
                                try {
                                    t_air_error = state.get("t_air_error").toString();
                                    if (!(TextUtils.isEmpty(t_air_error))) {
                                        t_air_errors.add(t_air_error);
                                    } else {
                                        t_air_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                t_air_errors.add("");
                            }

                            if(state.has("t_water_error") && !state.isNull("t_water_error")) {
                                try {
                                    t_water_error = state.get("t_water_error").toString();
                                    if (!(TextUtils.isEmpty(t_air_error))) {
                                        t_water_errors.add(t_water_error);
                                    } else {
                                        t_water_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                t_water_errors.add("");
                            }


                            if(state.has("w_air_error") && !state.isNull("w_air_error")) {
                                try {
                                    w_air_error = state.get("w_air_error").toString();
                                    if (!(TextUtils.isEmpty(w_air_error))) {
                                        w_air_errors.add(w_air_error);
                                    } else {
                                        w_air_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                w_air_errors.add("");
                            }

                            if(state.has("w_water_error") && !state.isNull("w_water_error")) {
                                try {
                                    w_water_error = state.get("w_water_error").toString();
                                    if (!(TextUtils.isEmpty(w_water_error))) {
                                        w_water_errors.add(w_water_error);
                                    } else {
                                        w_water_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                w_water_errors.add("");
                            }

                            if(state.has("velocity_error") && !state.isNull("velocity_error")) {
                                try {
                                    velocity_error = state.get("velocity_error").toString();
                                    if (!(TextUtils.isEmpty(velocity_error))) {
                                        velocity_errors.add(velocity_error);
                                    } else {
                                        velocity_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                velocity_errors.add("");
                            }

                            if(state.has("system_error") && !state.isNull("system_error")) {
                                try {
                                    system_error = state.get("system_error").toString();
                                    if (!(TextUtils.isEmpty(system_error))) {
                                        system_errors.add(system_error);
                                    } else {
                                        system_errors.add("-");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                system_errors.add("");
                            }


                        }
                        else {
                            //if state not exists in json fill with ""
                            continuous_flows.add("");
                            broken_pipes.add("");
                            battery_lows.add("");
                            backflows.add("");
                            communication_errors.add("");
                            parsing_errors.add("");
                            encoder_errors.add("");
                            us_water_levels.add("");
                            v_sensor_comm_timouts.add("");
                            t_air_errors.add("");
                            t_water_errors.add("");
                            w_air_errors.add("");
                            w_water_errors.add("");
                            velocity_errors.add("");
                            system_errors.add("");
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                for(int j=0; j<meter_ids.size(); j++) {
                    recyclerItems.add(new RecyclerListInfo(latitudes.get(j), longitudes.get(j), mp_names.get(j), meter_ids.get(j), meter_types.get(j), comm_mod_types.get(j), comm_mod_serials.get(j), last_entries.get(j), volumes.get(j), battery_lifetimes.get(j), continuous_flows.get(j), broken_pipes.get(j), battery_lows.get(j), backflows.get(j), communication_errors.get(j), parsing_errors.get(j), encoder_errors.get(j), us_water_levels.get(j), v_sensor_comm_timouts.get(j), water_level_errors.get(j) , t_air_errors.get(j), t_water_errors.get(j), w_air_errors.get(j), w_water_errors.get(j), velocity_errors.get(j), system_errors.get(j)));
                }

                adapter = new RecyclerListAdapter(recyclerItems, getApplicationContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                metersTxt.setText("-"  + "\n" + "Total Meters");
                waterusgTxt.setText("-"  + "\n" + "Total Usage");
                alertTxt.setText("-"  + "\n" + "Total Alerts");
                readoutsTxt.setText("-"  + "\n" + "Total Readouts");

                //Refresh recyclerview
                updated_listings = new JSONArray();
                refreshRecyclerView();
                /// Get total measures and refresh
                getTotalMeasures();

                //When refreshing reset menu options and search-filter
                invalidateOptionsMenu();
                searchItem.collapseActionView();

                //Stop refreshing as soon as job is done
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        // Get total measures
        getTotalMeasures();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                //logout -> return back to login activity and generate new access token, etc by logging in
                Toast.makeText(getApplicationContext(),"Logged out...", Toast.LENGTH_LONG).show();
                onBackPressed();
                return true;

            case R.id.search:

                //Toast.makeText(getApplicationContext(),"Searching...", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshRecyclerView() {
        RefreshRequest refreshRequest = new RefreshRequest();
        refreshRequest.setRefresh(refresh);

        Call<RefreshResponse> refreshResponseCall = RetroClient.getRefreshService().userRefresh(refreshRequest);

        refreshResponseCall.enqueue(new Callback<RefreshResponse>() {
            @Override
            public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfull Refresh", Toast.LENGTH_LONG);
                    RefreshResponse refreshResponse = response.body();
                    Log.d("New access: ", refreshResponse.getAccess());

                    access = refreshResponse.getAccess(); //store new access

                    //Get meters using new access
                    getMeasures();
                    try {
                        if (updated_listings.length() > 0 && updated_listings != null) {

                            for (int i = 0; i < updated_listings.length(); i++) {


                                JSONObject object = updated_listings.getJSONObject(i);

                                try {
                                    //latitudes
                                    latitude = object.get("lat").toString();
                                    if (!(TextUtils.isEmpty(latitude))) {
                                        latitudes.add(latitude);
                                    } else {
                                        latitudes.add("-");
                                    }

                                    //longitudes
                                    longitude = object.get("lng").toString();
                                    if (!(TextUtils.isEmpty(longitude))) {
                                        longitudes.add(longitude);
                                    } else {
                                        longitudes.add("-");
                                    }

                                    //mp_names
                                    mp_name = object.get("mp_name").toString();
                                    if (!(TextUtils.isEmpty(mp_name))) {
                                        mp_names.add(mp_name);
                                    } else {
                                        mp_names.add("-");
                                    }

                                    //meter_ids
                                    meter_id = object.get("meter_id").toString();
                                    if (!(TextUtils.isEmpty(meter_id))) {
                                        meter_ids.add(meter_id);
                                    } else {
                                        meter_ids.add("-");
                                    }

                                    //meter_type
                                    meter_type = object.get("meter_type").toString();
                                    if (!(TextUtils.isEmpty(meter_type))) {
                                        meter_types.add(meter_type);
                                    } else {
                                        meter_types.add("-");
                                    }

                                    //in case of fuelicsencoder
                                    if (object.has("comm_mod_type") && !object.isNull("comm_mod_type")) {
                                        comm_mod_type = object.get("comm_mod_type").toString();
                                        if (!(TextUtils.isEmpty(comm_mod_type))) {
                                            comm_mod_types.add(comm_mod_type);
                                        } else {
                                            comm_mod_types.add("-");
                                        }
                                    } else {
                                        comm_mod_type = "";
                                        comm_mod_types.add("");
                                    }

                                    if (object.has("comm_mod_serial") && !object.isNull("comm_mod_serial")) {
                                        comm_mod_serial = object.get("comm_mod_serial").toString();
                                        if (!(TextUtils.isEmpty(comm_mod_serial))) {
                                            comm_mod_serials.add(comm_mod_serial);
                                        } else {
                                            comm_mod_serials.add("-");
                                        }
                                    } else {
                                        comm_mod_serial = "";
                                        comm_mod_serials.add("");
                                    }

                                    //last_entry
                                    last_entry = object.get("last_entry").toString();
                                    if (!(TextUtils.isEmpty(last_entry))) {
                                        last_entries.add(last_entry);
                                    } else {
                                        last_entries.add("-");
                                    }

                                    //volumes
                                    volume = object.get("volume").toString();
                                    if (!(TextUtils.isEmpty(volume))) {
                                        volumes.add(volume);
                                    } else {
                                        volumes.add("-");
                                    }

                                    //battery lifetime
                                    if (object.has("battery_lifetime") && !object.isNull("battery_lifetime")) {
                                        battery_lifetime = object.get("battery_lifetime").toString();
                                        if (!(TextUtils.isEmpty(battery_lifetime))) {
                                            battery_lifetimes.add(battery_lifetime);
                                        } else {
                                            battery_lifetimes.add("-");
                                        }
                                    } else {
                                        battery_lifetime = "";
                                        battery_lifetimes.add("");
                                    }


                                    //state!!
                                    if (object.has("state") && !object.isNull("state")) {
                                        JSONObject state = (JSONObject) object.get("state");
                                        Log.d("state: ", state.toString());
                                        if (state.has("continuous_flow") && !state.isNull("continuous_flow")) {
                                            try {
                                                continuous_flow = state.get("continuous_flow").toString();
                                                if (!(TextUtils.isEmpty(continuous_flow))) {
                                                    continuous_flows.add(continuous_flow);
                                                } else {
                                                    continuous_flows.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            continuous_flows.add("");
                                        }

                                        if (state.has("broken_pipe") && !state.isNull("broken_pipe")) {
                                            try {
                                                broken_pipe = state.get("broken_pipe").toString();
                                                if (!(TextUtils.isEmpty(broken_pipe))) {
                                                    broken_pipes.add(broken_pipe);
                                                } else {
                                                    broken_pipes.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            broken_pipes.add("");
                                        }

                                        if (state.has("battery_low") && !state.isNull("battery_low")) {
                                            try {
                                                battery_low = state.get("battery_low").toString();
                                                if (!(TextUtils.isEmpty(battery_low))) {
                                                    battery_lows.add(battery_low);
                                                } else {
                                                    battery_lows.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            battery_lows.add("");
                                        }

                                        if (state.has("backflow") && !state.isNull("backflow")) {
                                            try {
                                                backflow = state.get("backflow").toString();
                                                if (!(TextUtils.isEmpty(backflow))) {
                                                    backflows.add(backflow);
                                                } else {
                                                    backflows.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            backflows.add("");
                                        }

                                        if (state.has("communication_error") && !state.isNull("communication_error")) {
                                            try {
                                                communication_error = state.get("communication_error").toString();
                                                if (!(TextUtils.isEmpty(communication_error))) {
                                                    communication_errors.add(communication_error);
                                                } else {
                                                    communication_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            communication_errors.add("");
                                        }

                                        if (state.has("parsing_error") && !state.isNull("parsing_error")) {
                                            try {
                                                parsing_error = state.get("parsing_error").toString();
                                                if (!(TextUtils.isEmpty(parsing_error))) {
                                                    parsing_errors.add(parsing_error);
                                                } else {
                                                    parsing_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            parsing_errors.add("");
                                        }

                                        if (state.has("encoder_error") && !state.isNull("encoder_error")) {
                                            try {
                                                encoder_error = state.get("encoder_error").toString();
                                                if (!(TextUtils.isEmpty(encoder_error))) {
                                                    encoder_errors.add(encoder_error);
                                                } else {
                                                    encoder_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            encoder_errors.add("");
                                        }


                                        if (state.has("us_water_level") && !state.isNull("us_water_level")) {
                                            try {
                                                us_water_level = state.get("us_water_level").toString();
                                                if (!(TextUtils.isEmpty(us_water_level))) {
                                                    us_water_levels.add(us_water_level);
                                                } else {
                                                    us_water_levels.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            us_water_levels.add("");
                                        }

                                        if (state.has("v_sensor_comm_timout") && !state.isNull("v_sensor_comm_timout")) {
                                            try {
                                                v_sensor_comm_timout = state.get("v_sensor_comm_timout").toString();
                                                if (!(TextUtils.isEmpty(v_sensor_comm_timout))) {
                                                    v_sensor_comm_timouts.add(v_sensor_comm_timout);
                                                } else {
                                                    v_sensor_comm_timouts.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            v_sensor_comm_timouts.add("");
                                        }

                                        if (state.has("water_level_error") && !state.isNull("water_level_error")) {
                                            try {
                                                water_level_error = state.get("water_level_error").toString();
                                                if (!(TextUtils.isEmpty(water_level_error))) {
                                                    water_level_errors.add(water_level_error);
                                                } else {
                                                    water_level_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            water_level_errors.add("");
                                        }

                                        if (state.has("t_air_error") && !state.isNull("t_air_error")) {
                                            try {
                                                t_air_error = state.get("t_air_error").toString();
                                                if (!(TextUtils.isEmpty(t_air_error))) {
                                                    t_air_errors.add(t_air_error);
                                                } else {
                                                    t_air_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            t_air_errors.add("");
                                        }

                                        if (state.has("t_water_error") && !state.isNull("t_water_error")) {
                                            try {
                                                t_water_error = state.get("t_water_error").toString();
                                                if (!(TextUtils.isEmpty(t_air_error))) {
                                                    t_water_errors.add(t_water_error);
                                                } else {
                                                    t_water_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            t_water_errors.add("");
                                        }


                                        if (state.has("w_air_error") && !state.isNull("w_air_error")) {
                                            try {
                                                w_air_error = state.get("w_air_error").toString();
                                                if (!(TextUtils.isEmpty(w_air_error))) {
                                                    w_air_errors.add(w_air_error);
                                                } else {
                                                    w_air_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            w_air_errors.add("");
                                        }

                                        if (state.has("w_water_error") && !state.isNull("w_water_error")) {
                                            try {
                                                w_water_error = state.get("w_water_error").toString();
                                                if (!(TextUtils.isEmpty(w_water_error))) {
                                                    w_water_errors.add(w_water_error);
                                                } else {
                                                    w_water_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            w_water_errors.add("");
                                        }

                                        if (state.has("velocity_error") && !state.isNull("velocity_error")) {
                                            try {
                                                velocity_error = state.get("velocity_error").toString();
                                                if (!(TextUtils.isEmpty(velocity_error))) {
                                                    velocity_errors.add(velocity_error);
                                                } else {
                                                    velocity_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            velocity_errors.add("");
                                        }

                                        if (state.has("system_error") && !state.isNull("system_error")) {
                                            try {
                                                system_error = state.get("system_error").toString();
                                                if (!(TextUtils.isEmpty(system_error))) {
                                                    system_errors.add(system_error);
                                                } else {
                                                    system_errors.add("-");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            system_errors.add("");
                                        }


                                    } else {
                                        //if state not exists in json fill with ""
                                        continuous_flows.add("");
                                        broken_pipes.add("");
                                        battery_lows.add("");
                                        backflows.add("");
                                        communication_errors.add("");
                                        parsing_errors.add("");
                                        encoder_errors.add("");
                                        us_water_levels.add("");
                                        v_sensor_comm_timouts.add("");
                                        t_air_errors.add("");
                                        t_water_errors.add("");
                                        w_air_errors.add("");
                                        w_water_errors.add("");
                                        velocity_errors.add("");
                                        system_errors.add("");
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                recyclerItems = new ArrayList<>(); //empty list to update with new records

                //Insert the new records
                for (int j = 0; j < meter_ids.size(); j++) {

                    recyclerItems.add(new RecyclerListInfo(latitudes.get(j), longitudes.get(j), mp_names.get(j), meter_ids.get(j), meter_types.get(j), comm_mod_types.get(j), comm_mod_serials.get(j), last_entries.get(j), volumes.get(j), battery_lifetimes.get(j), continuous_flows.get(j), broken_pipes.get(j), battery_lows.get(j), backflows.get(j), communication_errors.get(j), parsing_errors.get(j), encoder_errors.get(j), us_water_levels.get(j), v_sensor_comm_timouts.get(j), water_level_errors.get(j), t_air_errors.get(j), t_water_errors.get(j), w_air_errors.get(j), w_water_errors.get(j), velocity_errors.get(j), system_errors.get(j)));
                }

                //Toast.makeText(getApplicationContext(), "Length new of arraylist: " + recyclerItems.size(), Toast.LENGTH_LONG).show();

                adapter = new RecyclerListAdapter(recyclerItems, getApplicationContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RefreshResponse> call, Throwable t) {

            }
        });
    }







    public void getMeasures() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://test-api.gwf.ch/reports/measurements/";
        Request request = new Request.Builder()
                .url(url)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + access)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {


            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                    e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Log.d("get resp: ", myResponse);

                    try {

                        JSONArray Response = new JSONArray(myResponse);
                        //Log.d("length array: ", Response.length() +"");
                        updated_listings = Response;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("Service not available" ,"fail");
                }

            }
        });
    }


    public void getTotalMeasures() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://test-api.gwf.ch/reports/measurements/total/";
        Request request = new Request.Builder()
                .url(url)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + access)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {


            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Log.d("Total Resp: ", myResponse);

                    JSONObject Response = null;
                    try {
                        Response = new JSONObject(myResponse);
                        totalMeters = Response.get("meters").toString();
                        totalUsage = Response.get("volume").toString();
                        totalAlerts = Response.get("errors").toString();
                        totalReadouts = Response.get("readouts").toString();

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                metersTxt.setText(totalMeters +"\n" + "Total Meters");
                                waterusgTxt.setText(totalUsage +"\n" + "Total Usage");
                                alertTxt.setText(totalAlerts +"\n" + "Total Alerts");
                                readoutsTxt.setText(totalReadouts +"\n" + "Total Readouts");
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d("length array: ", Response.length() +"");

                }
            }
        });
    }

    //the following overrides are used to reset search view
    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchItem.collapseActionView();

        return super.onPrepareOptionsMenu(menu);
    }
}


