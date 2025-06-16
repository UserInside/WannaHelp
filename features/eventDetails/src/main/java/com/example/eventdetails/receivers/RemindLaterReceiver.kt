package com.example.eventdetails.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.eventdetails.DonationFragment.Companion.EVENT_ID
import com.example.eventdetails.notification.NotificationWorker
import java.util.concurrent.TimeUnit


class RemindLaterReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.cancel(NotificationWorker.NOTIFICATION_ID)

        val eventId = intent?.getIntExtra(EVENT_ID, 0)
        val inputData = workDataOf(
            NotificationWorker.KEY_SHOW_REMIND_LATER to false,
            EVENT_ID to eventId
        )

        val laterRequest = OneTimeWorkRequestBuilder<NotificationWorker>().setInputData(inputData)
            .setInitialDelay(4, TimeUnit.SECONDS) // секунды для демонстрации. Должны быть 30 минут.
            .build()

        WorkManager.getInstance(context).enqueue(laterRequest)
    }
}