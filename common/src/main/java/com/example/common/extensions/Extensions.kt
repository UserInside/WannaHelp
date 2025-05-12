package com.example.common.extensions

import android.content.Context
import android.content.res.AssetManager
import kotlinx.serialization.json.Json

fun AssetManager.readFile(fileName: String) =
    open(fileName)
        .bufferedReader()
        .use {
            it.readText()
        }

inline fun <reified T> Json.parseToList(
    context: Context,
    fileName: String,
): List<T> {
    val jsonBuilder =
        Json {
            ignoreUnknownKeys = true
        }
    val jsonString = context.assets.readFile(fileName)
    return jsonBuilder.decodeFromString<List<T>>(jsonString)
}
