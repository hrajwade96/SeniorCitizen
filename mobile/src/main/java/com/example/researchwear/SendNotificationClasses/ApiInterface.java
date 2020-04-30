package com.example.researchwear.SendNotificationClasses;

import com.example.researchwear.SendNotificationClasses.RequestNotificaton;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization: key=AAAAy-H4K6c:APA91bEvbPibO-HAY8KpZ49MjHpO_PoNT9-FrjluTrS3iVBmTg5uZSEFsBeg3a25BVg1J6sZP4boQSCqE3eCt3rtye6QP3HV7Q7E2M28OvnjErgGqcfAxHGhfKJok8QgpVYj79w3cEBf",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendChatNotification(@Body RequestNotificaton requestNotificaton);
}