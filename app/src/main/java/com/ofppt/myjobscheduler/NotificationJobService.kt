package com.ofppt.myjobscheduler

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import androidx.core.app.NotificationCompat


class NotificationJobService : JobService() {
    /**
     * This is called by the system once it determines it is time to run the job.
     * @param jobParameters Contains the information about the job
     * @return Boolean indicating whether or not the job was offloaded to a separate thread.
     * In this case, it is false since the notification can be posted on the main thread.
     */
    override fun onStartJob(jobParameters: JobParameters): Boolean {

        //Set up the notification content intent to launch the app when clicked
        val contentPendingIntent = PendingIntent.getActivity(
            this, 0, Intent(
                this,
                MainActivity::class.java
            ), PendingIntent.FLAG_UPDATE_CURRENT
        )
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this,"100")
            .setContentTitle(getString(R.string.job_service))
            .setContentText(getString(R.string.job_running))
            .setContentIntent(contentPendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
        manager.notify(0, builder.build())
        return false
    }

    /**
     * Called by the system when the job is running but the conditions are no longer met.
     * In this example it is never called since the job is not offloaded to a different thread.
     * @param jobParameters Contains the information about the job
     * @return Boolean indicating whether the job needs rescheduling
     */
    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return true
    }
}