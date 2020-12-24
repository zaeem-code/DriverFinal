package com.loadease.uberclone.driverapp.Model;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCM_send_msg {


    final
    String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final String serverKey = "key=" + "AAAAOmcjWXA:APA91bFX_6U0IGMsMV3dNlNBrvKXcEZvZs4vW1SLaGOcgSHyA0VJ9irlmXRj56f8hjCeAHxUuhotvfUy9ZFnkg5qs9tQjhqCO3viv1q0dNEo76OotmGfbVnV3o86JIhyJlQH3EgnfFoJ";
    final  String contentType = "application/json";
    final  String   TOPIC = "/topics/Admin";
String key;
    JSONObject notification = new JSONObject();
    JSONObject notifcationBody = new JSONObject();
    public FCM_send_msg(Context context, String uid, LinearLayout root) {
key=uid;

        try {

            notifcationBody.put("title", "New Driver");
            notifcationBody.put("message"," A new Driver is Waiting for your aproval");
            notifcationBody.put("Key", key);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e("hassan", "fcm exp: " + e.getMessage() );
        }
        sendNotification(notification,context,root);
    }






    private void sendNotification(JSONObject notification, Context context, LinearLayout root) {

       RequestQueue requestQueue = null;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("hassan", "onResponse: " + response.toString());

                        Snackbar.make(root,"A Request Against Your Id is Sent to the LoadEase Office\n" +
                                "You will soon be contacted by our Staff\n" +
                                "Please wait for verification", Snackbar.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                              Log.i("hassan", "hassan: volley Didn't work"+error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        if (requestQueue==null){

            requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
        }else {
            requestQueue.add(jsonObjectRequest);}
    }
}




