package com.example.gwf_app;


import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MeterInfo extends FragmentActivity implements OnMapReadyCallback {
    String latitude="",longitude="", mp_name = "",meter_id="",meter_type="",last_entry="",volume="", comm_mod_type="", comm_mod_serial="", battery_lifetime="";
    String continuous_flow="", broken_pipe="",battery_low="",backflow="", communication_error="", parsing_error="", encoder_error="";
    String us_water_level="",v_sensor_comm_timout="", water_level_error="",t_air_error="",t_water_error="",w_air_error="",w_water_error="",velocity_error="",system_error="";
    TextView meterIdTxt, mp_nameTxt, meter_typeTxt, volumeTxt, last_entryTxt, comm_mod_typeTxt,comm_mod_serialTxt, battery_lifetimeTxt;
    TextView state1Txt, state2Txt, state3Txt, state4Txt, state5Txt, state6Txt, state7Txt, state8Txt,state9Txt;

    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_info);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        //get stuff from chosen Meter
        Bundle b = getIntent().getExtras();

        latitude = b.getString("lat","-");
        longitude = b.getString("lng","-");
        mp_name = b.getString("mp_name","Unnamed");
        meter_id = b.getString("meter_id","-");
        meter_type = b.getString("meter_type","-");
        last_entry = b.getString("last_entry","-");
        volume = b.getString("volume","-");
        comm_mod_type = b.getString("comm_mod_type","-");
        comm_mod_serial = b.getString("comm_mod_serial","-");
        battery_lifetime = b.getString("battery_lifetime","-");

        continuous_flow = b.getString("continuous_flow","-");
        broken_pipe = b.getString("broken_pipe","-");
        battery_low = b.getString("battery_low","-");
        backflow = b.getString("backflow","-");
        communication_error = b.getString("communication_error","-");
        parsing_error = b.getString("parsing_error","-");
        encoder_error = b.getString("encoder_error","-");

        us_water_level = b.getString("us_water_level","-");
        v_sensor_comm_timout = b.getString("v_sensor_comm_timout","-");
        water_level_error = b.getString("water_level_error","-");
        t_air_error = b.getString("t_air_error","-");
        t_water_error = b.getString("t_water_error","-");
        w_air_error = b.getString("w_air_error","-");
        w_water_error = b.getString("w_water_error","-");
        velocity_error = b.getString("velocity_error","-");
        system_error = b.getString("system_error","-");

        meterIdTxt = (TextView) findViewById(R.id.meter_idTxt);
        mp_nameTxt = (TextView) findViewById(R.id.mp_name);
        meter_typeTxt = (TextView) findViewById(R.id.meter_type);
        volumeTxt = (TextView)findViewById(R.id.volume);
        last_entryTxt = (TextView)findViewById(R.id.last_entry);
        comm_mod_typeTxt = (TextView)findViewById(R.id.comm_mod_type);
        comm_mod_serialTxt = (TextView)findViewById(R.id.comm_mod_serial);
        battery_lifetimeTxt = (TextView)findViewById(R.id.battery_lifetime);

        //state
        state1Txt = (TextView)findViewById(R.id.state1);
        state2Txt = (TextView)findViewById(R.id.state2);
        state3Txt = (TextView)findViewById(R.id.state3);
        state4Txt = (TextView)findViewById(R.id.state4);
        state5Txt = (TextView)findViewById(R.id.state5);
        state6Txt = (TextView)findViewById(R.id.state6);
        state7Txt = (TextView)findViewById(R.id.state7);
        state8Txt = (TextView)findViewById(R.id.state8);
        state9Txt = (TextView)findViewById(R.id.state8);


        meterIdTxt.setText("Meter id: " + meter_id);

        if(mp_name.equals("") || mp_name.equals("null")) {
            mp_name = "Unnamed";
        }
        mp_nameTxt.setText("Name: " + mp_name);

        if(meter_type.equals("") || meter_type.equals("null")) {
            meter_type = "-";
        }
        meter_typeTxt.setText("Type: " + meter_type);

        if(volume.equals("") || volume.equals("null")) {
            volume = "-";
        }
        volumeTxt.setText("Volume: " + volume);

        if(last_entry.equals("") || last_entry.equals("null")) {
            last_entry = "-";
        }
        last_entryTxt.setText("Last Entry: " + last_entry);

        if(comm_mod_type.equals("") || comm_mod_type.equals("null")) {
            comm_mod_type="-";
        }
        comm_mod_typeTxt.setText("Mode Type: " + comm_mod_type);

        if(comm_mod_serial.equals("") || comm_mod_serial.equals("null")) {
            comm_mod_serial = "-";
        }
        comm_mod_serialTxt.setText("Serial Txt: " + comm_mod_serial);

        if(battery_lifetime.equals("") || battery_lifetime.equals("null")) {
            battery_lifetime = "-";
        }
        battery_lifetimeTxt.setText("Battery Life: " + battery_lifetime);


        //Check meter type and display the apropriate information
        if(!(meter_type.equals("afm"))) {
            if(continuous_flow.equals("") || continuous_flow.equals("null")) {
                continuous_flow = "-";
            }
            state1Txt.setText("Continous Flow: " + continuous_flow);

            if(broken_pipe.equals("") || broken_pipe.equals("null")) {
                broken_pipe = "-";
            }
            state2Txt.setText("Broken Pipe: " + broken_pipe);

            if(battery_low.equals("") || battery_low.equals("null")) {
                battery_low = "-";
            }
            state3Txt.setText("Battery Low: " + battery_low);

            if(backflow.equals("") || backflow.equals("null")) {
                backflow = "-";
            }
            state4Txt.setText("Back Flow: " + backflow);

            if(communication_error.equals("") || communication_error.equals("null")) {
                communication_error = "-";
            }
            state5Txt.setText("Communication Error: " + communication_error);

            if(parsing_error.equals("") || parsing_error.equals("null")) {
                parsing_error = "-";
            }
            state6Txt.setText("Parsing Error: " + parsing_error);

            if(encoder_error.equals("") || encoder_error.equals("null")) {
                encoder_error = "-";
            }
            state7Txt.setText("Encoder Error: " + encoder_error);
        }

        else {
            if(us_water_level.equals("") || us_water_level.equals("null")) {
                us_water_level = "-";
            }
            state1Txt.setText("US water level: " + us_water_level);

            if(v_sensor_comm_timout.equals("") || v_sensor_comm_timout.equals("null")) {
                v_sensor_comm_timout = "-";
            }
            state2Txt.setText("Sensor conn timout: " + v_sensor_comm_timout);

            if(water_level_error.equals("") || water_level_error.equals("null")) {
                water_level_error = "-";
            }
            state3Txt.setText("Water level error: " + water_level_error);

            if(t_air_error.equals("") || t_air_error.equals("null")) {
                t_air_error = "-";
            }
            state4Txt.setText("T air error: " + t_air_error);

            if(t_water_error.equals("") || t_water_error.equals("null")) {
                t_water_error = "-";
            }
            state5Txt.setText("T water error: " + t_water_error);

            if(w_air_error.equals("") || w_air_error.equals("null")) {
                w_air_error = "-";
            }
            state6Txt.setText("W air error: " + w_air_error);

            if(w_water_error.equals("") || w_water_error.equals("null")) {
                w_water_error = "-";
            }
            state7Txt.setText("W water error: " + w_water_error);

            if(velocity_error.equals("") || velocity_error.equals("null")) {
                velocity_error = "-";
            }
            state8Txt.setText("Velocity error: " + velocity_error);

            if(system_error.equals("") || system_error.equals("null")) {
                system_error = "-";
            }
            state9Txt.setText("System error: " + system_error);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        if(isNumeric(latitude) && isNumeric(longitude)) {
            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            //Add marker with the id of the meter as title
            map.addMarker(new MarkerOptions().position(latLng).title(meter_id)).showInfoWindow();;
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera( CameraUpdateFactory.zoomTo( 2.0f ) );
        }
        else {
            Toast.makeText(getApplicationContext(),"No valid coordinates", Toast.LENGTH_LONG).show();
        }


    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
