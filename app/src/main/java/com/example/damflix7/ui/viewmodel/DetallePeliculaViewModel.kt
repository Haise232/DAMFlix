package com.example.damflix7.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damflix7.data.PeliculaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetallePeliculaViewModel(
    private val repository: PeliculaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    fun cargarDetallePelicula(peliculaId: Int) {
        viewModelScope.launch {
            _uiState.value = DetalleUiState.Cargando

            // La consulta a Room se hace en un hilo secundario para no bloquear la pantalla
            val resultado = withContext(Dispatchers.IO) {
                delay(1000) // retardo solo para poder ver el indicador de carga
                repository.obtenerPorId(peliculaId)
            }

            if (resultado != null) {
                _uiState.value = DetalleUiState.Exito(resultado)
            } else {
                _uiState.value = DetalleUiState.Error("Película no encontrada")
            }
        }
    }
}
