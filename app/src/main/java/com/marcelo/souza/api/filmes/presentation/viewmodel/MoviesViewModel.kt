package com.marcelo.souza.api.filmes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.repository.MoviesRepository
import com.marcelo.souza.api.filmes.presentation.viewmodel.viewstate.ViewStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MoviesViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _viewStateGetMovies = MutableStateFlow<ViewStates>(ViewStates.Loading)
    val viewStateGetMovies: StateFlow<ViewStates> = _viewStateGetMovies.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() = viewModelScope.launch {
        repository.getMovies()
            .onStart { _viewStateGetMovies.value = ViewStates.Loading }
            .map { data -> ViewStates.Success(data) }
            .catch { throwable ->
                val apiError = when (throwable) {
                    is MoviesApiErrors -> throwable
                    else -> MoviesApiErrors.Error(throwable)
                }
                _viewStateGetMovies.value = ViewStates.Error(apiError)
            }
            .collect { newState ->
                _viewStateGetMovies.value = newState
            }
    }

    fun retryMovies() {
        getMovies()
    }
}