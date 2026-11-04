package com.example.damflix6.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peliculas")
data class PeliculaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val director: String,
    val genero: String,
    val anio: Int,
    val descripcion: String = "",
    val esVista: Boolean = false,
    val esFavorita: Boolean = false,
)

/** Películas de ejemplo para que el catálogo no empiece vacío. */
val peliculasIniciales = listOf(
    PeliculaEntity(titulo = "Inception", director = "Christopher Nolan", genero = "Ciencia Ficción", anio = 2010, descripcion = "Un ladrón que roba secretos corporativos a través del uso de la tecnología de intercambio de sueños.", esVista = true),
    PeliculaEntity(titulo = "The Dark Knight", director = "Christopher Nolan", genero = "Acción", anio = 2008, descripcion = "Batman acepta uno de los mayores desafíos psicológicos y físicos para luchar contra la injusticia.", esVista = true),
    PeliculaEntity(titulo = "Interstellar", director = "Christopher Nolan", genero = "Ciencia Ficción", anio = 2014, descripcion = "Un grupo de exploradores viaja a través de un agujero de gusano en el espacio para salvar a la humanidad."),
    PeliculaEntity(titulo = "Pulp Fiction", director = "Quentin Tarantino", genero = "Crimen", anio = 1994, descripcion = "Las vidas de dos matones de la mafia, un boxeador y la esposa de un gángster se entrelazan.", esVista = true),
    PeliculaEntity(titulo = "Matrix", director = "Lana y Lilly Wachowski", genero = "Ciencia Ficción", anio = 1999, descripcion = "Un hacker descubre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores."),
    PeliculaEntity(titulo = "El Señor de los Anillos", director = "Peter Jackson", genero = "Fantasía", anio = 2003, descripcion = "Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron."),
    PeliculaEntity(titulo = "Gladiator", director = "Ridley Scott", genero = "Acción", anio = 2000, descripcion = "Un antiguo general romano busca venganza contra el corrupto emperador que asesinó a su familia."),
    PeliculaEntity(titulo = "Avatar", director = "James Cameron", genero = "Ciencia Ficción", anio = 2009, descripcion = "Un marine parapléjico enviado al planeta Pandora se debate entre seguir órdenes o proteger su nuevo hogar."),
)
