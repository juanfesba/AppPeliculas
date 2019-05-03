package com.example.datastorage.Adapters

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.R
import com.example.datastorage.Modelos.User
import com.example.datastorage.Servicios.UserDBServices
import java.io.ByteArrayInputStream

class MoviesListAdapter(private val activity: Activity, moviesList: List<Movie>?, token:String) : BaseAdapter(){
    private var moviesList = ArrayList<Movie>()
    private val tok=token

    init {
        this.moviesList = moviesList as ArrayList<Movie>
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        //Log.i("juan2",moviesList.size.toString())
        return moviesList.size
    }

    override fun getItem(i: Int): Any {
        return moviesList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    fun getNombreMovie(i: Int): String? {
        return moviesList[i].nombreMovie
    }


        

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.row_movieitem, null)
        val nombre = vi.findViewById<TextView>(R.id.NameMovie)
        val imageMovie = vi.findViewById<ImageView>(R.id.movieImage)
        nombre.text = moviesList[i].nombreMovie


        val stream= ByteArrayInputStream(moviesList[i].imagenMovie)
        val image= BitmapFactory.decodeStream(stream)

        imageMovie.setImageBitmap(image)
        val heart=vi.findViewById<ImageView>(R.id.favList)
        val movieFav=moviesList[i].nombreMovie as String
        //Log.i("juan","llego "+movieFav+" "+tok)
        if(UserDBServices(viewGroup.context).getFav(tok,movieFav)){
            heart.setImageResource(R.drawable.hearth)
        }
        else{
            heart.setImageResource(R.drawable.heartbroken)
        }


        return vi
    }
}