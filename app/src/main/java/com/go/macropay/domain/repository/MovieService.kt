package com.go.macropay.domain.repository

import com.go.macropay.data.remote.models.moviedetail.MovieDetailDTO
import com.go.macropay.data.remote.models.movielist.MovieListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MovieService {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDgyMzkzNDQzODA3NWQ2M2YxZGJkYTQwMjNlNzZmYyIsInN1YiI6IjY1MDBmNzJkNTU0NWNhMDExYmE2N2RkYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4QxbpZq9Tj3uzhA8uv2qLNcCA7NIcGBHDzoC4bWv9t8")
    @GET("now_playing")
    suspend fun getListMovies() : Response<MovieListDTO>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMDgyMzkzNDQzODA3NWQ2M2YxZGJkYTQwMjNlNzZmYyIsInN1YiI6IjY1MDBmNzJkNTU0NWNhMDExYmE2N2RkYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4QxbpZq9Tj3uzhA8uv2qLNcCA7NIcGBHDzoC4bWv9t8")
    @GET("{movieId}?language=en-US")
    suspend fun getDetailMovie(@Path("movieId") movieId: Int) : Response<MovieDetailDTO>
}