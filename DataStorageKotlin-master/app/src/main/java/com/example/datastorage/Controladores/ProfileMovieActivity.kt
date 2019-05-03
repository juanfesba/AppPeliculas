package com.example.datastorage.Controladores

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.datastorage.R
import com.example.datastorage.Servicios.UserDBServices
import java.io.ByteArrayInputStream

class ProfileMovieActivity : AppCompatActivity() {

    companion object {
        const val NOMBREMOVIE = "nombremovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_movie)
        showProfile()
        val token=UserDBServices(this).getToken()
        setFav(token)


    }

    fun showProfile()
    {
        val nombremovieextra = intent.getStringExtra(NOMBREMOVIE)


        val movie = UserDBServices(this).getMovie(nombremovieextra)
        /*val tamañosafe:ByteArray?=user.image
        val tamaño = if (tamañosafe != null) tamañosafe.size else -1
        val image= BitmapFactory.decodeByteArray(user.image,0,tamaño)*/

        val stream= ByteArrayInputStream(movie.imagenMovie)
        val image= BitmapFactory.decodeStream(stream)
        val drawable= RoundedBitmapDrawableFactory.create(resources,image)


        val imageUpload=findViewById<ImageView>(R.id.imageMovie)
        //imageUpload.setImageBitmap(image)
        imageUpload.setImageDrawable(drawable)


        findViewById<TextView>(R.id.nombrePeliProfile).text = "Nombre: " + movie.nombreMovie
        findViewById<TextView>(R.id.sinopsisProfile).text = "Sinopsis: " + movie.sinopsis
    }

    fun volver(view: View)
    {
        val intent = Intent(this, MoviesListActivity::class.java)
        startActivity(intent)
    }

    fun setFav(tok:String){
        if(UserDBServices(this).getFav(tok,intent.getStringExtra(NOMBREMOVIE))){
            findViewById<ImageView>(R.id.fav).setImageResource(R.drawable.hearth)
        }
        else {
            findViewById<ImageView>(R.id.fav).setImageResource(R.drawable.heartbroken)
        }
    }

    fun clickedFav(view: View){
        val token=UserDBServices(this).getToken()
        UserDBServices(this).updateFav(token,intent.getStringExtra(NOMBREMOVIE))
        this.setFav(token)
    }
}
