package com.example.eventdetails.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.eventdetails.notification.NotificationWorker

class ChargingReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {
            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}