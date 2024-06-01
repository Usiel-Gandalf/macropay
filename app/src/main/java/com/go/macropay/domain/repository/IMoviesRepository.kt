package com.go.macropay.domain.repository

import com.go.macropay.domain.models.Movie
import com.go.macropay.domain.models.MovieDetail
import com.go.macropay.utils.ResponseAPI

interface IMoviesRepository {
    suspend fun getMovies() : ResponseAPI<List<Movie>>
    suspend fun getDetailMovie(movieId: Int) : ResponseAPI<MovieDetail>
}