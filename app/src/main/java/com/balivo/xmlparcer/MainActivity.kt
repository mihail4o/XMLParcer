package com.balivo.xmlparcer

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class MainActivity : AppCompatActivity() {


    private val LOG_TAG = "XML"
    private val UpdateFlag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GetXMLFromServer().execute()
    }

    fun ParseXML(xmlString: String) {

        try {

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(StringReader(xmlString))
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    val name = parser.name
                    if (name == "UpdateFlag") {

                        val ref = parser.getAttributeValue(null, "ref")
                        Log.d(LOG_TAG, "ref:$ref")

                        if (parser.next() == XmlPullParser.TEXT) {
                            val UpdateFlag = parser.text
                            Log.d(LOG_TAG, "UpdateFlag:$UpdateFlag")
                        }


                    } else if (name == "Name") {

                        if (parser.next() == XmlPullParser.TEXT) {
                            val Name = parser.text
                            Log.d(LOG_TAG, "Name:$Name")
                        }
                    } else if (name == "Range") {

                        if (parser.next() == XmlPullParser.TEXT) {
                            val Range = parser.text
                            Log.d(LOG_TAG, "Range:$Range")
                        }
                    }


                } else if (eventType == XmlPullParser.END_TAG) {


                }
                eventType = parser.next()

            }


        } catch (e: Exception) {
            Log.d(LOG_TAG, "Error in ParseXML()", e)
        }

    }

    private inner class GetXMLFromServer : AsyncTask<String, Void, String>() {

        internal lateinit var nh: HttpHandler

        override fun doInBackground(vararg strings: String): String {

            val URL = "http://androidpala.com/tutorial/horoscope.xml"
            var res = ""
            nh = HttpHandler()
            val `is` = nh.CallServer(URL)
            if (`is` != null) {

                res = nh.StreamToString(`is`)

            } else {
                res = "NotConnected"
            }

            return res
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            if (result == "NotConnected") {

                Toast.makeText(applicationContext, "Connection Error", Toast.LENGTH_SHORT).show()

            } else {
                ParseXML(result)
            }
        }


    }

}
