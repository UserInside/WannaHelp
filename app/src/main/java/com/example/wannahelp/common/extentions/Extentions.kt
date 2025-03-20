package com.example.wannahelp.common.extentions

import android.content.Context
import android.content.res.AssetManager
import kotlinx.serialization.json.Json

fun AssetManager.readFile(fileName: String) =
    open(fileName)
        .bufferedReader()
        .use {
            it.readText()
        }

class JsonParser(
    val context: Context,
    val fileName: String,
) {
    inline fun <reified T> parseToList(): List<T> {
        val jsonString = context.assets.readFile(fileName)
        return Json.decodeFromString<List<T>>(jsonString)
    }
}

inline fun <reified T> Json.parseToList(
    context: Context,
    fileName: String,
): List<T> {
    val jsonString = context.assets.readFile(fileName)
    return decodeFromString<List<T>>(jsonString)
}
