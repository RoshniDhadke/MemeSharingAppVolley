package com.example.memesharingappvolley

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load()
    }

    fun load() {
        progressbar.visibility = View.VISIBLE
        //  val queue=Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"
        val JSONRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }
                }).into(imageview)
                // Log.d("success respone",response.toString())
            }, Response.ErrorListener { error ->
                //  Log.d("error response".,error.toString())
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            })
        // queue.add(JSONRequest)
        MySingelton.getInstance(this).addToRequestQueue(JSONRequest)
    }


    fun Sharebtn(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plan"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "hey!!! Checkout this cool meme i got from reddit $currentImageUrl"
        )
        val chooser = Intent.createChooser(intent, "share this meme using...")
        startActivity(chooser)
    }

    fun Nextbtn(view: View) {
        load()
    }


}