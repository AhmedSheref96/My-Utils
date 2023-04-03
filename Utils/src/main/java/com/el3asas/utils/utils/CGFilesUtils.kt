package com.el3asas.utils.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun getFilePath(context: Context, uri: Uri) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    val resolver = context.contentResolver
    val openable = resolver.openFileDescriptor(uri, "r")
    val fileDescriptor = openable?.fileDescriptor
    val inputStream = FileInputStream(fileDescriptor)
    val file = File(context.cacheDir, "temp")
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)
    openable?.close()
    file.absolutePath
} else {
    getFilePathUnder30(context, uri)
}

private fun getFilePathUnder30(context: Context, uri: Uri): String? {
    var filePath: String? = null

    // Get the scheme of the URI
    val scheme = uri.scheme

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(
            context,
            uri,
        )
    ) {
        // For Android 4.4 and above, get the file path from the document ID
        val documentId = DocumentsContract.getDocumentId(uri)
        when {
            isExternalStorageDocument(uri) -> {
                val split = documentId.split(":")
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    filePath = "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }
            }
            isDownloadsDocument(uri) -> {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), documentId.toLong()
                )
                filePath = getDataColumn(context, contentUri, null, null)
            }
            isMediaDocument(uri) -> {
                val split = documentId.split(":")
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                filePath = getDataColumn(context, contentUri, selection, selectionArgs)
            }
        }
    } else if ("content".equals(scheme, ignoreCase = true)) {
        // For Android 4.3 and below, get the file path from the content URI
        filePath = getDataColumn(context, uri, null, null)
    } else if ("file".equals(scheme, ignoreCase = true)) {
        // For file URI scheme, get the file path directly
        filePath = uri.path
    }

    return filePath
}

private fun getDataColumn(
    context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = MediaStore.Images.Media.DATA
    val projection = arrayOf(column)
    try {
        cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } finally {
        cursor?.close()
    }
    return null
}

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}