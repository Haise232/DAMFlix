package com.example.damflix7.ui.viewmodel

import com.example.damflix7.data.local.PeliculaEntity

// Estados posibles de la pantalla de detalle
sealed interface DetalleUiState {
    data object Cargando : DetalleUiState
    data class Exito(val pelicula: PeliculaEntity) : DetalleUiState
    data class Error(val mensaje: String) : DetalleUiState
}
