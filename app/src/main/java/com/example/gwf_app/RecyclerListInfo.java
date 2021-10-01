package com.example.gwf_app;

public class RecyclerListInfo {

    private String lat;
    private String lng;
    private String mp_name;
    private String meter_id;
    private String meter_type;
    private String comm_mod_type;
    private String comm_mod_serial;
    private String last_entry;
    private String volume;
    private String battery_lifetime;
    private String continuous_flow="", broken_pipe="",battery_low="",backflow="", communication_error="", parsing_error="", encoder_error="";
    private String us_water_level="",v_sensor_comm_timout="", water_level_error="",t_air_error="",t_water_error="",w_air_error="",w_water_error="",velocity_error="",system_error="";


    RecyclerListInfo(String lat, String lng, String mp_name, String meter_id, String meter_type, String comm_mod_type, String comm_mod_serial,
                     String last_entry, String volume, String battery_lifetime, String continuous_flow, String broken_pipe, String battery_low,
                     String backflow, String communication_error, String parsing_error, String encoder_error,String us_water_level, String v_sensor_comm_timout,
                     String water_level_error, String t_air_error, String t_water_error, String w_air_error, String w_water_error, String velocity_error,
                     String system_error) {
        this.lat = lat;
        this.lng = lng;
        this.mp_name = mp_name;
        this.meter_id = meter_id;
        this.meter_type = meter_type;
        this.comm_mod_type = comm_mod_type;
        this.comm_mod_serial = comm_mod_serial;
        this.last_entry = last_entry;
        this.volume = volume;
        this.battery_lifetime = battery_lifetime;
        this.continuous_flow = continuous_flow;
        this.broken_pipe = broken_pipe;
        this.battery_low = battery_low;
        this.backflow = backflow;
        this.communication_error = communication_error;
        this.parsing_error = parsing_error;
        this.encoder_error = encoder_error;
        this.us_water_level = us_water_level;
        this.v_sensor_comm_timout = v_sensor_comm_timout;
        this.water_level_error= water_level_error;
        this.t_air_error = t_air_error;
        this.t_water_error = t_water_error;
        this.w_air_error = w_air_error;
        this.w_water_error = w_water_error;
        this.velocity_error = velocity_error;
        this.system_error = system_error;
    }

    public String getUs_water_level() {
        return us_water_level;
    }

    public String getV_sensor_comm_timout() {
        return v_sensor_comm_timout;
    }

    public String getWater_level_error() {
        return water_level_error;
    }

    public String getT_air_error() {
        return t_air_error;
    }

    public String getT_water_error() {
        return t_water_error;
    }

    public String getW_air_error() {
        return w_air_error;
    }

    public String getW_water_error() {
        return w_water_error;
    }

    public String getVelocity_error() {
        return velocity_error;
    }

    public String getSystem_error() {
        return system_error;
    }

    public String getContinuous_flow() {
        return continuous_flow;
    }

    public String getBroken_pipe() {
        return broken_pipe;
    }

    public String getBattery_low() {
        return battery_low;
    }

    public String getBackflow() {
        return backflow;
    }

    public String getCommunication_error() {
        return communication_error;
    }

    public String getParsing_error() {
        return parsing_error;
    }

    public String getEncoder_error() {
        return encoder_error;
    }

    public String getBattery_lifetime() {
        return battery_lifetime;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getMeter_id() {
        return meter_id;
    }

    public String getMeter_type() {
        return meter_type;
    }

    public String getComm_mod_type() {
        return comm_mod_type;
    }

    public String getComm_mod_serial() {
        return comm_mod_serial;
    }
    public String getLast_entry() {
        return last_entry;
    }

    public String getVolume() {
        return volume;
    }

    public String getMp_name() {
        return mp_name;
    }

}
