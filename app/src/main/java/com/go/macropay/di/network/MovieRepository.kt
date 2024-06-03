package com.go.macropay.di.network

import com.go.macropay.data.remote.APIMoviesRepository
import com.go.macropay.domain.repository.IMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepository {

    @Binds
    abstract fun bindMovieRepository(movieRepository: APIMoviesRepository): IMoviesRepository
}