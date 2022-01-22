package com.example.roomdb_ilham_16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roomdb_ilham_16.room.Constant
import com.example.roomdb_ilham_16.room.Movie
import com.example.roomdb_ilham_16.room.MovieDB
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy { MovieDB(this) }
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupview()
        setupSave()
    }

    fun setupview(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }

            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getmovie()
            }

            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getmovie()
            }
        }
    }

    fun setupSave(){
        btn_save.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDAO().addMovie(
                    Movie(0, et_title.text.toString(),
                    et_description.text.toString())
                )

                finish()
            }
        }

        btn_update.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDAO().updateMovie(
                    Movie(movieId, et_title.text.toString(),
                        et_description.text.toString())
                )

                finish()
            }
        }
    }

    fun  getmovie(){
        movieId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val moviess = db.movieDAO().getMovie(movieId)[0]
            et_title.setText(moviess.title)
            et_description.setText(moviess.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}