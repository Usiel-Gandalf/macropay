package com.go.macropay.domain

import com.go.macropay.data.remote.models.moviedetail.MovieDetailDTO
import com.go.macropay.data.remote.models.movielist.MovieResult
import com.go.macropay.domain.models.Movie
import com.go.macropay.domain.models.MovieDetail

fun MovieResult.toDomain(): Movie {
    var imageURL = "https://wallpaperaccess.com/full/4834549.jpg"

    if (this.poster_path.isNotEmpty()) {
        imageURL = "https://image.tmdb.org/t/p/w300/" + this.poster_path
    }

    return Movie(
        id = this.id,
        image = imageURL,
        title = this.original_title,
        calification = String.format("%.1f", this.vote_average)
    )
}

fun MovieDetailDTO.toDomain(): MovieDetail {
    var imageURL = "https://wallpaperaccess.com/full/4834549.jpg"

    if (this.poster_path.isNotEmpty()) {
        imageURL = "https://image.tmdb.org/t/p/w300/" + this.poster_path
    }

    return MovieDetail(
        image = imageURL,
        title = this.original_title,
        duration = this.runtime,
        fechaEsterno = this.release_date,
        clasification = if (this.adult == false) "Todos los publicos" else "Adulto",
        genere = this.genres[0].name,
        description = this.overview
    )
}