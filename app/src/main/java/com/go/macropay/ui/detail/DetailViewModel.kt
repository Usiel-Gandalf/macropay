package com.go.macropay.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.go.macropay.domain.models.Movie
import com.go.macropay.domain.models.MovieDetail
import com.go.macropay.domain.repository.IMoviesRepository
import com.go.macropay.utils.ResponseAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiRepository: IMoviesRepository
): ViewModel() {

    private val _individualMovieIdSelectedForDetail = mutableStateOf(0)

    var movieDetail: MovieDetail = MovieDetail()
        private set

    fun setComicIdForDetail(idMovie: Int){
        _individualMovieIdSelectedForDetail.value = idMovie
        getDetailMovie()
    }

     private fun getDetailMovie(){
        viewModelScope.launch {
            when(val result = apiRepository.getDetailMovie(_individualMovieIdSelectedForDetail.value)){
                is ResponseAPI.OnFailure -> {

                }

                is ResponseAPI.Success-> {
                    withContext(Dispatchers.Main){
                        movieDetail = result.data
                    }
                }
            }

        }

    }
}