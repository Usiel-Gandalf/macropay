package com.go.macropay.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.go.macropay.domain.models.Movie
import com.go.macropay.domain.repository.IMoviesRepository
import com.go.macropay.utils.ResponseAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: IMoviesRepository,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    var movieList: MutableList<Movie> = mutableStateListOf()
        private set

    var filterMovieList: MutableList<Movie> = mutableStateListOf()
        private set

    fun getMoviesList() {
        _loading.value = true
        _error.value = false

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = apiRepository.getMovies()) {
                is ResponseAPI.OnFailure -> {
                    _error.postValue(true)
                    Log.e("MyAppTag", "Ocurrió un error en riskyOperation")
                }

                is ResponseAPI.Success -> {
                    withContext(Dispatchers.Main) {
                        _loading.postValue(false)
                        movieList.clear()
                        movieList.addAll(result.data)
                        filterMovieList.clear()
                        filterMovieList.addAll(result.data)
                    }
                }
            }
        }
    }
}