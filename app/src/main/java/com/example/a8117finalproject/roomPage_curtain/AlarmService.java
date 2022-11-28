package com.example.a8117finalproject.roomPage_curtain;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AlarmService {
    @POST("/user/alarm-on/trigger")
    Call<AlarmResponse> saveUsers(@Body AlarmRequest alarmRequest);
}
