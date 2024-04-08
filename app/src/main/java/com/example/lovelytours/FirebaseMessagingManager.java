package com.example.lovelytours;

import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lovelytours.models.Tour;
import com.example.lovelytours.models.Tourist;
import com.example.lovelytours.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMessagingManager {

    private static final String URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAA9nogjXo:APA91bF04yEF49lLm6bTulCavQo8ct_Ns34VzENDEYD3ZU9UFut6BdoXsGGcAp16PxzPnd2_wh4-RLUUKWaZiKVtDAGCeo4eY8HdzB_dkD1F2kHb2eEC3J9FSwlY0wwtZYvTr3f1hmux";

    public static void unSubscribePush() {
        FirebaseMessaging.getInstance().deleteToken();
    }

    public static void subscribeToPushIfNeeded(String topic) {
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseMessaging.getInstance().subscribeToTopic(topic);
            }
        });
    }

    public static void sendNotificationToGuide(AppCompatActivity activity , int tickets, User tourist, Tour tour) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", "/topics/" + tour.getId());
            JSONObject object = new JSONObject();
            object.put("title", Html.fromHtml(activity.getString(R.string.you_have_a_new_tour_registration,
                    tourist.getFullName(), tickets, tour.getName())));
            mainObject.put("notification", object);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                mainObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int x = 0;
            }

        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                int x = 0;
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String > header = new HashMap<>();
                header.put("content-type", "application/json");
                header.put("authorization", "key=" + SERVER_KEY);
                return header;
            }
        };
        requestQueue.add(request);
    }
}
