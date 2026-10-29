package com.example.damflix5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damflix5.data.PeliculaRepository
import com.example.damflix5.model.Pelicula
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PeliculaViewModel(private val repository: PeliculaRepository) : ViewModel() {

    private val _listaPeliculas = MutableStateFlow<List<Pelicula>>(emptyList())
    val listaPeliculas: StateFlow<List<Pelicula>> = _listaPeliculas.asStateFlow()

    init {
        cargarPeliculas()
    }

    fun cargarPeliculas() {
        viewModelScope.launch {
            _listaPeliculas.value = repository.obtenerTodas()
        }
    }

    fun agregarPelicula(titulo: String, director: String, genero: String, anio: Int, descripcion: String, esVista: Boolean) {
        viewModelScope.launch {
            val nueva = Pelicula(
                titulo = titulo,
                director = director,
                genero = genero,
                anio = anio,
                descripcion = descripcion,
                esVista = esVista
            )
            repository.insertar(nueva)
            cargarPeliculas()
        }
    }

    fun conmutarFavorito(pelicula: Pelicula) {
        viewModelScope.launch {
            val actualizada = pelicula.copy(esFavorita = !pelicula.esFavorita)
            repository.actualizar(actualizada)
            cargarPeliculas()
        }
    }
}
