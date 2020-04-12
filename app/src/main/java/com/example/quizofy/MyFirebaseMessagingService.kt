package com.example.quizofy

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG= String::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG,"From ${remoteMessage?.from}")

        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG,"Message data payload" + remoteMessage.data)

            if (!remoteMessage.data.isNullOrEmpty()){
                val msg : String=remoteMessage.data.get("message").toString()
            }
        }

       remoteMessage?.notification.let {
       }
    }

}