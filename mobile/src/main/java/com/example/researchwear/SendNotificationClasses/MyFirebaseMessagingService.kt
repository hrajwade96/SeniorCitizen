package com.example.researchwear.SendNotificationClasses

import android.app.NotificationChannel
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.researchwear.UserActivity
import com.example.researchwear.R


class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FirebaseMessagingService"


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, ": ")
        Log.d(TAG, ": ${remoteMessage.from}")
        createChannel("id1","not1")
//        var intent = Intent(this,NotificationActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
       // Toast.makeText(baseContext,remoteMessage.toString(), Toast.LENGTH_SHORT).show()
        if (remoteMessage.notification != null) {
            val notificationManager = ContextCompat.getSystemService(
                baseContext,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendNotification(
                baseContext.getText(R.string.eggs_ready).toString(),
                baseContext
            )
        }
        else{
            Log.d(TAG,"NULL ${remoteMessage.data}")
        }
    }
    fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
        // Create the content intent for the notification, which launches
        // this activity
        // TODO: Step 1.11 create intent
        val contentIntent = Intent(applicationContext, UserActivity::class.java)
        // TODO: Step 1.12 create PendingIntent
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // TODO: Step 2.0 add style
        val eggImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.common_google_signin_btn_icon_dark
        )
        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(eggImage)
            .bigLargeIcon(null)

        // TODO: Step 2.2 add snooze action


        // TODO: Step 1.2 get an instance of NotificationCompat.Builder
        // Build the notification
        val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.egg_notification_channel_id)
        )

            // TODO: Step 1.8 use the new 'breakfast' notification channel

            // TODO: Step 1.3 set title, text and icon to builder
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setContentTitle(applicationContext
                .getString(R.string.notification_title))
            .setContentText(messageBody)

            // TODO: Step 1.13 set content intent
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

            // TODO: Step 2.1 add style to builder
            .setStyle(bigPicStyle)
            .setLargeIcon(eggImage)

            // TODO: Step 2.3 add snooze action


            // TODO: Step 2.5 set priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        // TODO: Step 1.4 call notify
        notify(1, builder.build())
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )// TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager = this.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // TODO: Step 1.6 END create a channel
    }

//    private fun showNotification(title: String?, body: String?) {
//        val intent = Intent(baseContext, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(baseContext, 0, intent,
//            PendingIntent.FLAG_ONE_SHOT)
//
//        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(baseContext)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setAutoCancel(true)
//            .setSound(soundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, notificationBuilder.build())
//    }
}