package com.example.eventdetails.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.eventdetails.DonationFragment
import com.example.eventdetails.DonationFragment.Companion.EVENT_ID
import com.example.eventdetails.R
import com.example.eventdetails.receivers.RemindLaterReceiver
import timber.log.Timber

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context, workerParams
) {
    override fun doWork(): Result {
        val showRemindLaterButton = inputData.getBoolean(KEY_SHOW_REMIND_LATER, true)
        showNotification(showRemindLaterButton)
        return Result.success()
    }

    private fun showNotification(showRemindLaterButton: Boolean) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val eventId = inputData.getInt(EVENT_ID, 0)
        val deepLinkEventUri =
            applicationContext.getString(R.string.deep_link_uri, eventId.toString())
        Timber.e("deplinkURI ---> $deepLinkEventUri")
        val deepLinkIntent = Intent(Intent.ACTION_VIEW, deepLinkEventUri.toUri()).apply {
            setPackage(applicationContext.packageName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, deepLinkIntent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            applicationContext.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL).setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(applicationContext.getString(R.string.thanks_for_donation_later_notification))
            ).setSmallIcon(com.example.common.R.drawable.placeholder_24)
                .setContentTitle(inputData.getString(DonationFragment.Companion.EVENT_NAME))
                .setContentText(applicationContext.getString(R.string.thanks_for_donation_later_notification))
                .setContentIntent(pendingIntent).setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (showRemindLaterButton) {
            val remindLaterIntent =
                Intent(applicationContext, RemindLaterReceiver::class.java).apply {
                    Bundle().apply {
                        putExtra(EVENT_ID, eventId)
                    }
                }

            val remindLaterPendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                REQUEST_CODE,
                remindLaterIntent,
                FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
            )

            notificationBuilder.addAction(
                R.drawable.ic_watch_later_24,
                applicationContext.getString(R.string.remind_later),
                remindLaterPendingIntent,
            ).setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    applicationContext.getString(
                        R.string.thanks_for_donation_notification,
                        inputData.getString(DonationFragment.Companion.DONATION_AMOUNT)
                    )
                )
            ).setContentText(
                applicationContext.getString(
                    R.string.thanks_for_donation_notification,
                    inputData.getString(DonationFragment.Companion.DONATION_AMOUNT)
                )
            )
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val NOTIFICATION_ID = 7
        const val NOTIFICATION_CHANNEL = "notification_channel"
        const val REQUEST_CODE = 1
        const val KEY_SHOW_REMIND_LATER = "SHOW_REMIND_LATER"
    }
}


