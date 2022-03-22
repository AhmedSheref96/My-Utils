package com.el3asas.utils.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.StrictMode
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


fun getBitmapFromURL(strURL: String): Bitmap? {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    return try {
        val url = URL(strURL)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun getMutableBitmapFromURL(strURL: String): Bitmap? {
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    return try {
        val url = URL(strURL)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input = connection.inputStream
        convertToMutable(BitmapFactory.decodeStream(input))
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun convertToMutable(imgIn: Bitmap): Bitmap {
    var imgIn = imgIn
    try {
        val file =
            File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator.toString() + "temp.tmp"
            )

        val randomAccessFile = RandomAccessFile(file, "rw")

        // get the width and height of the source bitmap.
        val width = imgIn.width
        val height = imgIn.height
        val type: Bitmap.Config = imgIn.config

        //Copy the byte to the file
        //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
        val channel: FileChannel = randomAccessFile.channel
        val map: MappedByteBuffer =
            channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.rowBytes.toLong() * height)
        imgIn.copyPixelsToBuffer(map)
        //recycle the source bitmap, this will be no longer used.
        imgIn.recycle()
        System.gc() // try to force the bytes from the imgIn to be released

        //Create a new bitmap to load the bitmap again. Probably the memory will be available.
        imgIn = Bitmap.createBitmap(width, height, type)
        map.position(0)
        //load it back from temporary
        imgIn.copyPixelsFromBuffer(map)
        //close the temporary file and channel , then delete that also
        channel.close()
        randomAccessFile.close()

        // delete the temp file
        file.delete()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return imgIn
}

