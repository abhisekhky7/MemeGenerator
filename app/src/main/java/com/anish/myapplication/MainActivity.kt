package com.anish.myapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    private lateinit var iv:ImageView
    private lateinit var pb:ProgressBar
     var currentMeme: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv=findViewById(R.id.imageView)
        pb=findViewById(R.id.progressBar)
        loadMeme()

    }

    private fun loadMeme(){
        val url = "https://meme-api.com/gimme"
        pb.visibility=View.VISIBLE


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                 currentMeme = response.getString("url")
                Glide.with(this).load(currentMeme).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility=View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility=View.GONE
                        return false
                    }

                }).into(iv)
            },
            { error ->
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun nextMeme(view: View){
        loadMeme()
    }
    fun shareMeme(view:View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Check out this meme $currentMeme ")
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }


}