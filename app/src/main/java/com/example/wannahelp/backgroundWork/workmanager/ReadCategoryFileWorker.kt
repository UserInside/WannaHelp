package com.example.wannahelp.backgroundWork.workmanager

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.wannaHelpScreen.CategoryItem
import com.example.wannahelp.wannaHelpScreen.WannaHelpScreenFragment
import kotlinx.serialization.json.Json

class ReadCategoryFileWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val fileName: String =
            inputData.getString(WannaHelpScreenFragment.CATEGORIES_FILE_NAME_KEY) ?: ""

        for (i in 0..1) { //todo 100
            Thread.sleep(50)
            progress.postValue(i)
        }
        resultLiveData.postValue(Json.parseToList<CategoryItem>(context, fileName))

        return Result.success()
    }

    companion object {
        val progress = MutableLiveData<Int>()
        val resultLiveData = MutableLiveData<List<CategoryItem>>()
    }
}
