package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.example.datastorage.Adapters.MoviesListAdapter
import com.example.datastorage.Adapters.UsersListAdapter
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User
import com.example.datastorage.R
import com.example.datastorage.Servicios.UserDBServices

class MoviesListActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)

        val listPosts: List<Movie>? = UserDBServices(this).consultMovies()

        listView = findViewById<ListView>(R.id.listMovies) as ListView

        val token=UserDBServices(this).getToken()

        val adapter = MoviesListAdapter(this, listPosts,token)
        listView.adapter = adapter

        listView.setClickable(true)
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val idMovie = adapter.getNombreMovie(i)
            val intent = Intent(this, ProfileMovieActivity::class.java)
            intent.putExtra(ProfileMovieActivity.NOMBREMOVIE, idMovie)
            startActivity(intent)
            //Toast.makeText(this, "Item Clicked " + adapter.getName(i),Toast.LENGTH_SHORT).show()
        }
    }

    fun salir(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
