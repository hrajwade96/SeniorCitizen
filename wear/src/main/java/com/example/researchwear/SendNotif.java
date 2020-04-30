package com.example.researchwear;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import retrofit2.Callback;

public class SendNotif {
    public void sendNotificationToPatner() {

        final SendNotificationModel sendNotificationModel = new SendNotificationModel( "d5vK6t6ORnCWLuKnd_b-ot:APA91bF0N4ynsoraYzmoLH7iURGnDOZxnSRHGTiw8UF3RERZ4PqqeHFj-T3pLAtHkRZg1wu5psKjDHtT-TN5ji_AtQjloezgO-xtLNQIqo5ZdUj9_Bv_D3p9lKyZIo3KCS2Hh5_3RRQD","Help!!", "Please help me!");
        RequestNotificaton requestNotificaton = new RequestNotificaton();
        requestNotificaton.setSendNotificationModel(sendNotificationModel);
        //token is id , whom you want to send notification ,
        requestNotificaton.setToken("d5vK6t6ORnCWLuKnd_b-ot:APA91bF0N4ynsoraYzmoLH7iURGnDOZxnSRHGTiw8UF3RERZ4PqqeHFj-T3pLAtHkRZg1wu5psKjDHtT-TN5ji_AtQjloezgO-xtLNQIqo5ZdUj9_Bv_D3p9lKyZIo3KCS2Hh5_3RRQD");

        ApiInterface apiService = ApiClientClass.getClient().create(ApiInterface.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendChatNotification(requestNotificaton);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("kkkk",sendNotificationModel.getBody());
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d("kkkk",t.getMessage());

            }
        });
    }
}
