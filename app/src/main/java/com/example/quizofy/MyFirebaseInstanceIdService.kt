package com.example.quizofy

import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIdService : FirebaseMessagingService() {

 val TAG="MyFirebaseInstanceId"

    override fun onNewToken(p0: String) {

        val newtoken=FirebaseInstanceId.getInstance().getToken()
        Log.d(TAG,"Refreshed Token" + newtoken)


    }


}