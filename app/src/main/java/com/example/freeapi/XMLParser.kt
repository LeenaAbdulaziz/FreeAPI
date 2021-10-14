package com.example.freeapi

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

data class Channel(val title: String?, val link: String?,val description: String?)



class XMLParser {
    private val ns: String? = null

    fun parse(inputStream: InputStream): List<Channel> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
           // parser.nextTag()
            return readchannels(parser)
        }
    }

    private fun readchannels(parser: XmlPullParser): List<Channel> {
        val ch = mutableListOf<Channel>()
        //parser.require(XmlPullParser.START_TAG, ns, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "channel") {
                parser.require(XmlPullParser.START_TAG, ns, "channel")
                var title: String? = null
                var description: String? = null
                var link: String? = null

                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.eventType != XmlPullParser.START_TAG) {
                        continue
                    }
                    when (parser.name) {
                        "title" -> title = readTitle(parser)
                        "description" -> description = readDescription(parser)
                        "link" -> link = readLink(parser)

                        else -> skip(parser)
                    }
                }
                ch.add(Channel(title,description,link))
            } else {
                skip(parser)
            }
        }
        return ch
    }



    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    private fun readLink(parser: XmlPullParser): String {

        parser.require(XmlPullParser.START_TAG, ns, "link")
        val tag = parser.name
      //  val relType = parser.getAttributeValue(null, "rel")
//        if (tag == "link") {
//            if (relType == "alternate") {
//                link = parser.getAttributeValue(null, "href")
//                parser.nextTag()
//            }
//        }
        val link = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "link")
        return link
    }

    private fun readDescription(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "description")
        val description = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "description")
        return description
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}