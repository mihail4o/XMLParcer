package com.balivo.xmlparcer

//HttpHandler.java
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpHandler {


    internal var LOG_TAG = "HttpHandler"

    fun CallServer(remoteURL: String): InputStream? {
        var inStm: InputStream? = null

        try {
            val url = URL(remoteURL)
            val cc = url.openConnection() as HttpURLConnection
            cc.readTimeout = 5000
            cc.connectTimeout = 5000
            cc.requestMethod = "GET"
            cc.doInput = true
            val response = cc.responseCode

            if (response == HttpURLConnection.HTTP_OK) {
                inStm = cc.inputStream
            }

        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error in CallServer", e)
        }

        return inStm

    }

    fun StreamToString(stream: InputStream): String {


        val isr = InputStreamReader(stream)
        val reader = BufferedReader(isr)
        val response = StringBuilder()

        var line: String? = null

        try {
            while (true) {
                line = reader.readLine()
                if(line == null) { break}
                response.append(line)
            }

        } catch (e: IOException) {
            Log.e(LOG_TAG, "Error in StreamToString", e)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error in StreamToString", e)
        } finally {

            try {
                stream.close()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "Error in StreamToString", e)
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error in StreamToString", e)
            }

        }
        return response.toString()

    }
}