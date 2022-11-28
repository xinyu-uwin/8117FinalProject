package com.example.a8117finalproject.roomPage_curtain;

public class AlarmResponse {

  /*  private String room_name;
    private int thermostat_temp;
    private int preferred_temp;
    private String location;
    private String alarm_time_weekday;
    private String alarm_time_weekend;
    private String name;
    private String username;
    private String light_on;
    private String curtain_on;
    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getThermostat_temp() {
        return thermostat_temp;
    }

    public void setThermostat_temp(int thermostat_temp) {
        this.thermostat_temp = thermostat_temp;
    }

    public int getPreferred_temp() {
        return preferred_temp;
    }

    public void setPreferred_temp(int preferred_temp) {
        this.preferred_temp = preferred_temp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    /*"status": 200,
            "body": {
        "room_name": "bedroom-3",
                "thermostat_temp": 26,
                "preferred_temp": 23,
                "location": "windsor,canada",
                "alarm_time_weekday": "06:30",
                "alarm_time_weekend": "09:30",
                "name": "nav",
                "username": "vegesna00@gmail.com",
                "light_on": 100,
                "curtain_on": 0
    },
            "msg": "overcast clouds"*/

    private String status;
    private Object body;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
