package com.example.a8117finalproject.bean;

public class Room {
    private String room_name;
    private String username;
    private String light_on;
    private String curtain_on;
    private String thermostat_temp;
    private String preferred_temp;

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLight_on() {
        return light_on;
    }

    public void setLight_on(String light_on) {
        this.light_on = light_on;
    }

    public String getCurtain_on() {
        return curtain_on;
    }

    public void setCurtain_on(String curtain_on) {
        this.curtain_on = curtain_on;
    }

    public String getThermostat_temp() {
        return thermostat_temp;
    }

    public void setThermostat_temp(String thermostat_temp) {
        this.thermostat_temp = thermostat_temp;
    }

    public String getPreferred_temp() {
        return preferred_temp;
    }

    public void setPreferred_temp(String preferred_temp) {
        this.preferred_temp = preferred_temp;
    }

    public String getAlarm_time_weekday() {
        return alarm_time_weekday;
    }

    public void setAlarm_time_weekday(String alarm_time_weekday) {
        this.alarm_time_weekday = alarm_time_weekday;
    }

    public String getAlarm_time_weekend() {
        return alarm_time_weekend;
    }

    public void setAlarm_time_weekend(String alarm_time_weekend) {
        this.alarm_time_weekend = alarm_time_weekend;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getCold() {
        return cold;
    }

    public void setCold(String cold) {
        this.cold = cold;
    }

    private String alarm_time_weekday;
    private String alarm_time_weekend;
    private String heat;
    private String cold;



}
