package com.example.datastorage.Controladores

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.AddMovieServices
import com.example.datastorage.Servicios.RegisterServices
import java.io.ByteArrayOutputStream

class AddMovieActivity : AppCompatActivity() {

    private lateinit var addServices : AddMovieServices
    private lateinit var imageUploaded : ImageView
    private val RESULTLOADIMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        addServices = AddMovieServices(this)
        imageUploaded = findViewById(R.id.imagePeli)
    }

    fun uploadImage(view: View)
    {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, RESULTLOADIMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULTLOADIMAGE && resultCode == RESULT_OK && data != null)
        {
            val selectedImage = data.getData()
            imageUploaded.setImageURI(selectedImage)
            //imageUploaded.
        }
    }

    fun addMovie(view: View){
        val nombre = findViewById<TextView>(R.id.nombrePeli);
        val sinop = findViewById<TextView>(R.id.sinopsisPeli);

        val bitmap = (imageUploaded.getDrawable() as BitmapDrawable).getBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val imagebyte = stream.toByteArray()

        val movie = Movie(null,nombre.text.toString(),sinop.text.toString(),imagebyte)

        this.addServices.saveMovie(movie)
        val intent = Intent(this, AdminLobbyActivity::class.java)
        startActivity(intent)

    }

    fun volverAddPeli(view: View)
    {
        val intent = Intent(this, AdminLobbyActivity::class.java)
        startActivity(intent)
    }
}
