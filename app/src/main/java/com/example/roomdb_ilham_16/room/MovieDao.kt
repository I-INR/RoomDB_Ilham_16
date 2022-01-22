package com.example.roomdb_ilham_16.room

import androidx.room.*

@Dao
interface MovieDao {

    @Insert
    suspend fun addMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * from movie")
    suspend fun getMovies():List<Movie>

    @Query("SELECT * from movie WHERE id=:movie_id")
    suspend fun getMovie(movie_id: Int):List<Movie>

}