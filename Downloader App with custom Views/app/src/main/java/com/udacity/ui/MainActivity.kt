package com.udacity.ui

import android.app.*
import android.app.DownloadManager.COLUMN_TOTAL_SIZE_BYTES
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.LoadingButton
import com.udacity.R
import com.udacity.utils.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    var indexClicked = -1
    var lastindexClicked = -1
    private lateinit var notificationManager: NotificationManager
    lateinit var urls: List<List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        urls = initializeURLsDataList()

        val rbGroup = findViewById<RadioGroup>(R.id.rb_main)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


        custom_button.setOnClickListener {
            val checkedID = rbGroup.checkedRadioButtonId
            handleRadioButtonClick(checkedID)
            if (indexClicked != -1) {
                lastindexClicked = indexClicked
                download()
                (it as LoadingButton).startTheButton()
            }
            indexClicked = -1
        }
    }

    private fun handleRadioButtonClick(checkedID: Int) {
        when (checkedID) {
            R.id.radioButton1 -> indexClicked = 0
            R.id.radioButton2 -> indexClicked = 1
            R.id.radioButton3 -> indexClicked = 2
            else -> sendMessage("Please choose one downloadelement first")
        }
        if (indexClicked != -1) {
            //      URL = urls[indexClicked][0]
        }
    }

    private fun sendNotifications(message: String, status: String) {
        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )
        val notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.sendNotification(message, status, this)

    }

    private fun sendMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
          //  sendMessage("before enter the download completed")
            if (id == downloadID && intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                val myQuery = DownloadManager.Query()
                myQuery.setFilterById(downloadID)
                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val cursor: Cursor = downloadManager.query(myQuery)
                if (cursor.moveToFirst()) {
                    var downloadStatus = checkStatus(cursor)
                    if (downloadStatus == "Status_Successful") {
                        sendMessage("Download is Completed and the state is $downloadStatus")
                    }
                    sendNotifications(urls[lastindexClicked][1], downloadStatus)
                }
            }
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
        private var URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Download is done!"

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun initializeURLsDataList(): List<List<String>> {
        return listOf(
            listOf(
                "https://github.com/bumptech/glide/archive/master.zip",
                getString(R.string.glide)
            ),
            listOf(
                "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip",
                getString(R.string.loadapp)
            ),
            listOf(
                "https://github.com/square/retrofit/archive/master.zip",
                getString(R.string.retrofit)
            )
        )
    }

    fun checkStatus(cursor: Cursor): String {
        var statusText = ""
        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        val status = cursor.getInt(columnIndex)

        when (status) {
            DownloadManager.STATUS_SUCCESSFUL -> statusText = "Status_Successful"
            DownloadManager.STATUS_RUNNING -> statusText = "Status_Running"
            DownloadManager.STATUS_FAILED -> statusText = "Status_Failed"
            DownloadManager.STATUS_PAUSED -> statusText = "Status_Paused"
            DownloadManager.STATUS_PENDING -> statusText = "Status_Pending"
            else -> statusText = "Error in status"
        }
        return statusText
    }
}
