package com.example.freeapi

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var ch = mutableListOf<Channel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.rv)
        Fetchchanel().execute()

    }

    private inner class Fetchchanel : AsyncTask<Void, Void, MutableList<Channel>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Channel> {

            val url = URL("http://www.rediff.com/rss/moviesreviewsrss.xml")

            val urlConnection = url.openConnection() as HttpURLConnection

            ch = urlConnection.getInputStream()?.let {

                parser.parse(it)
            }
                    as MutableList<Channel>
            return ch
        }

        override fun onPostExecute(result: MutableList<Channel>?) {
            super.onPostExecute(result)
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, ch)
            recyclerView.adapter = RVAdapter(ch)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }
}