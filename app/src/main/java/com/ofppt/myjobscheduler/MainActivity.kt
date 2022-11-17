package com.ofppt.myjobscheduler


import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val JOB_ID = 0

    private var mScheduler: JobScheduler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mScheduler =  getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler;

        findViewById<View>(R.id.start).setOnClickListener(View.OnClickListener {

            val serviceName = ComponentName(
                packageName,
                NotificationJobService::class.java.getName()
            )
            val builder = JobInfo.Builder(JOB_ID, serviceName)

                .setRequiresCharging(true)
            builder.setPeriodic(15 * 1000); // min 15 min


            //Schedule the job and notify the user
            val myJobInfo = builder.build()
            mScheduler!!.schedule(myJobInfo)
        })

        findViewById<View>(R.id.cancel).setOnClickListener(View.OnClickListener {
            cancelJobs()
        })

    }

    private fun cancelJobs() {
        if (mScheduler != null) {
            mScheduler!!.cancelAll()
            mScheduler = null
            Toast.makeText(this, "Job Canceled", Toast.LENGTH_SHORT).show()
        }
    }
}