package com.example.eventdetails

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.net.toUri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context, workerParams
) {
    override fun doWork(): Result {
        val showRemindLaterButton = inputData.getBoolean("SHOW_REMIND_LATER", true)
        showNotification(showRemindLaterButton)
        return Result.success()
    }

    private fun showNotification(showRemindLaterButton: Boolean) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val deepLink = Intent(applicationContext, EventDetailsScreenFragment::class.java).apply {
            action = Intent.ACTION_VIEW
            data = "MainApp://event/details/{eventId}".toUri()
            //todo flaggs ?
        }


        val pendingIntent = PendingIntent.getActivity(
            applicationContext, REQUEST_CODE, deepLink, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val notificationChannel = NotificationChannel(
            "notification_channel", "Notification", NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(notificationChannel)


        //todo      исправить нажатие открытия диалога доната. сейчас сразу вылетает уведомление.
        //todo      сделать диплинки
        //todo      regex for sum


        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, "notification_channel")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(applicationContext.getString(R.string.thanks_for_donation_later_notification))
                )
                .setSmallIcon(com.example.common.R.drawable.placeholder_24)
                .setContentTitle(inputData.getString(DonationFragment.EVENT_NAME))
                .setContentText(applicationContext.getString(R.string.thanks_for_donation_later_notification))
                .setContentIntent(pendingIntent).setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
        //todo

        if (showRemindLaterButton) {

            val remindLaterIntent = Intent(applicationContext, RemindLaterReceiver::class.java)

            val remindLaterPendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                REQUEST_CODE,
                remindLaterIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )

            notificationBuilder.addAction(
                R.drawable.ic_watch_later_24,
                "Напомнить позже",
                remindLaterPendingIntent,
            ).setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    applicationContext.getString(
                        R.string.thanks_for_donation_notification,
                        inputData.getString(DonationFragment.DONATION_AMOUNT)
                    )
                )
            ).setContentText(applicationContext.getString(
                R.string.thanks_for_donation_notification,
                inputData.getString(DonationFragment.DONATION_AMOUNT)
            ))

        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val NOTIFICATION_ID = 7
        const val REQUEST_CODE = 1
        const val KEY_SHOW_REMIND_LATER = "SHOW_REMIND_LATER"
    }
}


class RemindLaterReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.cancel(NotificationWorker.NOTIFICATION_ID)

        val inputData = workDataOf(NotificationWorker.KEY_SHOW_REMIND_LATER to false)

        val laterRequest = OneTimeWorkRequestBuilder<NotificationWorker>().setInputData(inputData)
            .setInitialDelay(4, TimeUnit.SECONDS) // секунды для демонстрации. Должны быть 30 минут.
            .build()

        WorkManager.getInstance(context).enqueue(laterRequest)
    }

}

class ChargingReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {
            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}