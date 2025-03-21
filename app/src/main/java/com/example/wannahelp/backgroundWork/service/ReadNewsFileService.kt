package com.example.wannahelp.backgroundWork.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.newsScreen.NewsItem
import com.example.wannahelp.newsScreen.NewsScreenFragment
import kotlinx.serialization.json.Json
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReadNewsFileService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val fileName: String = intent?.getStringExtra(NewsScreenFragment.NEWS_FILE_NAME_KEY) ?: ""

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.submit {
            Thread.sleep(5000)
            resultLiveData.postValue(Json.parseToList<NewsItem>(this, fileName))
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        val resultLiveData = MutableLiveData<List<NewsItem>>()
    }
}