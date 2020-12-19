package com.loadease.uberclone.chatIntegration.Activity;


import com.loadease.uberclone.chatIntegration.Notifications.MyResponse;
import com.loadease.uberclone.chatIntegration.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAOmcjWXA:APA91bFX_6U0IGMsMV3dNlNBrvKXcEZvZs4vW1SLaGOcgSHyA0VJ9irlmXRj56f8hjCeAHxUuhotvfUy9ZFnkg5qs9tQjhqCO3viv1q0dNEo76OotmGfbVnV3o86JIhyJlQH3EgnfFoJ"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
