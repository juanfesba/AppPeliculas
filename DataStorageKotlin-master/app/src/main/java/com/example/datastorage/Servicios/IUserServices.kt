package com.example.datastorage.Servicios

import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User

interface IUserServices
{
    fun verifyUser(user: User) : Boolean
    fun saveUser(user: User)
    fun saveMovie(movie: Movie)
    fun saveToken(newToken:String)
    fun saveFav(mailF:String,movieF:String)
    fun consultUsers() : List<User>?
    fun consultMovies() : List<Movie>?
    fun existUser(user: User) : Boolean
    fun getUser(mail:String) : User
    fun getMovie(nombreM:String) : Movie
    fun getToken():String
    fun getFav(mail:String,movie:String):Boolean
    fun updateToken(newMail:String)
    fun updateFav(mailF:String,movieF:String)
}