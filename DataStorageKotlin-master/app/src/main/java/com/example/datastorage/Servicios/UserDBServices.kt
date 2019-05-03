package com.example.datastorage.Servicios

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.util.Log
import com.example.datastorage.Modelos.Movie
import com.example.datastorage.Modelos.User
import java.lang.Exception

class UserDBServices(context: Context) : SQLiteOpenHelper(context, "UserDBService", null, 1), IUserServices
{
    override fun onCreate(db: SQLiteDatabase?) {
        val sql: String = "CREATE TABLE users(idUser int primarykey," +
                " name text," +
                " email text," +
                " age integer," +
                " password text," +
                " image blob)"

        db?.execSQL(sql)

        val sql2: String = "CREATE TABLE movies(idMovie int primary key," +
                " nombreMovie text," +
                " sinopsis text," +
                " imageMovie blob)"

        db?.execSQL(sql2)

        val sql3: String = "CREATE TABLE token(idToken int primary key," +
                " emailtoken text DEFAULT \"default\")"

        db?.execSQL(sql3)

        val sql4: String = "CREATE TABLE favorites(idFavorite int primary key," +
                " emailFav text," +
                " movieFav text)"

        db?.execSQL(sql4)

        /*val altsql : String = "ALTER TABLE users " +
                            "ADD image blob"
        db?.execSQL(altsql)*/
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("Sin implementación")
    }

    override fun verifyUser(user: User) : Boolean
    {
        val sql : String = "SELECT email, password FROM users" +
                           " where email='${user.email}'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = false



        if(result.moveToFirst())
        {
            if (user.email.equals(result.getString(0)) && user.password.equals(result.getString(1)))
            {
                returnValue = true
            }
        }

        this.close()
        return returnValue
    }

    override fun saveUser(user: User)
    {
        var localUser = ContentValues()
        localUser.put("name", user.name)
        localUser.put("email", user.email)
        localUser.put("age", user.age)
        localUser.put("password", user.password)
        localUser.put("image",user.image)
        this.executeModification(localUser,"users")
    }

    override fun saveMovie(movie: Movie)
    {
        var localMovie = ContentValues()
        localMovie.put("nombreMovie", movie.nombreMovie)
        localMovie.put("sinopsis", movie.sinopsis)
        localMovie.put("imageMovie",movie.imagenMovie)
        //Log.i("juan2","saving # " +movie.sinopsis)
        //Log.i("juan2","saving2 # " +movie.imagenMovie.toString())
        this.executeModification(localMovie,"movies")
    }

    override fun consultUsers(): List<User>?
    {
        val sql : String = "SELECT idUser, name, email, age, password,image FROM users"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var listUsers : MutableList<User>? = ArrayList<User>()
        result.moveToFirst()

        while(!result.isAfterLast)
        {

            var user : User = User(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getString(4),
                result.getBlob(5)
            )

            listUsers?.add(user)
            result.moveToNext()
        }

        return listUsers
    }

    override fun consultMovies(): List<Movie>?
    {
        val sql : String = "SELECT idMovie, nombreMovie, sinopsis, imageMovie FROM movies"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        //Log.i("juan","prueba2 # " + result.count.toString())
        var listMovies : MutableList<Movie>? = ArrayList<Movie>()
        result.moveToFirst()

        while(!result.isAfterLast)
        {
            var movie : Movie = Movie(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getBlob(3)
            )

            listMovies?.add(movie)
            result.moveToNext()

        }

        /*if(listMovies!=null){
            val x=listMovies.size.toString()
            Log.i("juanlista",x)
        }
        else{
            Log.i("juanlista","es nula la lista")
        }*/

        return listMovies
    }

    override fun getUser(mail: String): User {
        val sql : String = "SELECT idUser,name,email,age,password,image FROM users" +
                " where email='$mail'"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)

        lateinit var user: User
        if(result.moveToFirst()) {

            user = User(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                "None",
                result.getBlob(5)
            )
        }
        return user
    }

    override fun getMovie(nombreM: String): Movie {
        val sql : String = "SELECT idMovie, nombreMovie, sinopsis, imageMovie FROM movies" +
                " where nombreMovie='$nombreM'"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)

        lateinit var movie:Movie
        if(result.moveToFirst()) {

            movie = Movie(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getBlob(3)
            )
        }
        return movie
    }


    private fun executeQuery(sql: String, bd : SQLiteDatabase) : Cursor
    {
        val consulta : Cursor = bd.rawQuery(sql,null)
        return consulta
    }

    private fun executeModification(objeto: ContentValues,table:String)
    {
        val bd = this.writableDatabase
        bd.insert(table, null, objeto)
        bd.close()
        //Log.i("juan2","se guardo # " + objeto.get("sinopsis"))
    }


        /*try{
            bd.insertOrThrow("movies", null, objeto)
        }
        catch(e: RuntimeException) {
            Log.i("juanx","Runtime Error Exception")
            bd.close()
        }
        finally{
            Log.i("juanx","aiuda2")
            bd.close()
        }
        val x=bd.insert("movies", null, objeto)
        Log.i("juan7","error # " + x.toString())
        bd.close()
        Log.i("juan7","se guardo # " + objeto.get("sinopsis"))*/

    override fun existUser(user: User) : Boolean
    {
        val sql : String = "SELECT email FROM users" +
                " where email='${user.email}'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        var returnValue : Boolean = false

        if(result.moveToFirst())
        {
            if (user.email.equals(result.getString(0)))
            {
                returnValue = true
            }
        }

        this.close()
        return returnValue
    }

    override fun updateToken(newMail: String) {
        var localToken = ContentValues()
        localToken.put("emailtoken", newMail)
        this.executeUpdate(localToken,"token")
    }

    private fun executeUpdate(objeto: ContentValues,table:String)
    {
        val bd = this.writableDatabase
        //Log.i("juan","executeUpdate 1"+objeto.get("emailtoken"))
        val arg= arrayOf("default")
        bd.update(table, objeto,null,null)
        bd.close()
    }

    override fun getToken(): String {
        val sql : String = "SELECT emailtoken FROM token"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        //Log.i("juan","getToken 2 : " + result.count.toString())

        lateinit var ans:String
        ans="error"
        if(result.moveToFirst()) {

            ans=result.getString(0)
        }
        //Log.i("juan",ans)
        return ans
    }

    override fun saveToken(newToken: String) {
        val sql : String = "SELECT emailtoken FROM token"
        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        if(result.count==0){
            var currentUser = ContentValues()
            currentUser.put("emailtoken", newToken)
            this.executeModification(currentUser,"token")
        }
        else{
            this.updateToken("default")
        }
    }

    override fun getFav(mail: String, movie: String): Boolean {
        val sql : String = "SELECT * FROM favorites" +
                " where emailFav='$mail'" + "AND movieFav='$movie'"

        val result : Cursor = this.executeQuery(sql, this.writableDatabase)
        if(result.count==0){
            return false
        }
        return true
    }

    override fun saveFav(mailF: String, movieF: String) {
        var localFav = ContentValues()
        localFav.put("emailFav", mailF)
        localFav.put("movieFav", movieF)
        this.executeModification(localFav,"favorites")
    }

    override fun updateFav(mailF: String, movieF: String) {
        if(getFav(mailF,movieF)){
            val bd = this.writableDatabase
            //Log.i("juan","executeUpdate 1"+objeto.get("emailtoken"))
            val arg= arrayOf(mailF,movieF)
            bd.delete("favorites","emailFav=? and movieFav=?",arg)
            bd.close()
        }
        else{
            this.saveFav(mailF,movieF)
        }
    }
}