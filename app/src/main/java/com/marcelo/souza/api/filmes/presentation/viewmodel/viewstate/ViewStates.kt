package com.marcelo.souza.api.filmes.presentation.viewmodel.viewstate

import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData

sealed interface ViewStates {
    data object Loading : ViewStates

    data class Success(val movies: List<CategoryViewData>) : ViewStates

    data class Error(val error: MoviesApiErrors) : ViewStates
}