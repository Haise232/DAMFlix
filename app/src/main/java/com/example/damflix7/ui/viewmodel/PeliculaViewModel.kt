package com.example.damflix7.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damflix7.data.PeliculaRepository
import com.example.damflix7.data.local.PeliculaEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PeliculaViewModel(private val repository: PeliculaRepository) : ViewModel() {

    // Flow de Room convertido en StateFlow: se actualiza solo tras cualquier escritura
    val listaPeliculas: StateFlow<List<PeliculaEntity>> = repository.todasPeliculas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarPelicula(
        titulo: String,
        director: String,
        genero: String,
        anio: Int,
        descripcion: String,
        esVista: Boolean,
    ) {
        viewModelScope.launch {
            repository.insertar(
                PeliculaEntity(
                    titulo = titulo,
                    director = director,
                    genero = genero,
                    anio = anio,
                    descripcion = descripcion,
                    esVista = esVista
                )
            )
        }
    }

    fun conmutarFavorito(pelicula: PeliculaEntity) {
        viewModelScope.launch {
            repository.actualizar(pelicula.copy(esFavorita = !pelicula.esFavorita))
        }
    }

    fun eliminarPelicula(pelicula: PeliculaEntity) {
        viewModelScope.launch { repository.eliminar(pelicula) }
    }
}
