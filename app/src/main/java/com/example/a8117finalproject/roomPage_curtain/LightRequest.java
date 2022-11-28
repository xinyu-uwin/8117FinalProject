package com.example.a8117finalproject.roomPage_curtain;

public class LightRequest {
    private String username;
    private String room_name;
    private int light_on;
    private  int curtain_on;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getLight_on() {
        return light_on;
    }

    public void setLight_on(int light_on) {
        this.light_on = light_on;
    }

    public int getCurtain_on() {
        return curtain_on;
    }

    public void setCurtain_on(int curtain_on) {
        this.curtain_on = curtain_on;
    }
}
