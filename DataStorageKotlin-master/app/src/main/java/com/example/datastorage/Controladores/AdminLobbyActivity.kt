package com.example.datastorage.Controladores

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.datastorage.R
import com.example.datastorage.Servicios.UserDBServices

class AdminLobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_lobby)
        /*val token= UserDBServices(this).getToken()
        if(token=="default"){
            Toast.makeText(this, "Vuelve a introducir credenciales",  Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/
    }

    fun salir(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun registrar(view: View)
    {
        val intent = Intent(this, AddMovieActivity::class.java)
        startActivity(intent)
    }

    fun listar(view: View)
    {
        val intent = Intent(this, UsersListActivity::class.java)
        startActivity(intent)
    }
}
