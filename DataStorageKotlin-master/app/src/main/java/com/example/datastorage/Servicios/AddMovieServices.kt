package com.example.datastorage.Servicios

import android.content.Context
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User

class AddMovieServices(context: Context) {
    private lateinit var movie : Movie
    private var dbConnection : UserDBServices = UserDBServices(context)
    private var sharedConnection : UserReservedServices = UserReservedServices(context)

    fun saveMovie(movie:Movie){
        dbConnection.saveMovie(movie)
    }
}