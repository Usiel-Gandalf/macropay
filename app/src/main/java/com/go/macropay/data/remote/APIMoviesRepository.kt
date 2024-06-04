package com.go.macropay.data.remote

import com.go.macropay.domain.repository.MovieService
import com.go.macropay.domain.models.Movie
import com.go.macropay.domain.models.MovieDetail
import com.go.macropay.domain.repository.IMoviesRepository
import com.go.macropay.domain.toDomain
import com.go.macropay.utils.ResponseAPI
import java.lang.Exception
import javax.inject.Inject

class APIMoviesRepository @Inject constructor(
    private val movieService: MovieService,
) : IMoviesRepository {
    override suspend fun getMovies(): ResponseAPI<List<Movie>> {
        return try {
            val result = movieService.getListMovies()
            result.body()?.results?.get(0)?.toDomain()

            ResponseAPI.Success(result.body()?.results?.map {
                it.toDomain()
            } ?: emptyList())
        } catch (e: Exception) {
            ResponseAPI.OnFailure(e.message.toString())
        }
    }

    override suspend fun getDetailMovie(movieId: Int): ResponseAPI<MovieDetail> {
        return try {
            val result = movieService.getDetailMovie(movieId)
            ResponseAPI.Success(result.body()!!.toDomain())
        } catch (e: Exception) {
            ResponseAPI.OnFailure(e.message.toString())
        }
    }


}