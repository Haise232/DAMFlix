package com.example.damflix5.model

data class Pelicula(
    val id: Int = 0,
    val titulo: String,
    val director: String,
    val genero: String,
    val anio: Int,
    val descripcion: String,
    val esVista: Boolean = false,
    val esFavorita: Boolean = false,
)

val peliculasIniciales = listOf(
    Pelicula(1, "Inception", "Christopher Nolan", "Ciencia Ficción", 2010, "Un ladrón que roba secretos corporativos a través del uso de la tecnología de intercambio de sueños.", esVista = true),
    Pelicula(2, "The Dark Knight", "Christopher Nolan", "Acción", 2008, "Batman acepta uno de los mayores desafíos psicológicos y físicos para luchar contra la injusticia.", esVista = true),
    Pelicula(3, "Interstellar", "Christopher Nolan", "Ciencia Ficción", 2014, "Un grupo de exploradores viaja a través de un agujero de gusano en el espacio para salvar a la humanidad."),
    Pelicula(4, "Pulp Fiction", "Quentin Tarantino", "Crimen", 1994, "Las vidas de dos matones de la mafia, un boxeador y la esposa de un gángster se entrelazan.", esVista = true),
    Pelicula(5, "Matrix", "Lana y Lilly Wachowski", "Ciencia Ficción", 1999, "Un hacker descubre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores."),
    Pelicula(6, "El Señor de los Anillos", "Peter Jackson", "Fantasía", 2003, "Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron."),
    Pelicula(7, "Gladiator", "Ridley Scott", "Acción", 2000, "Un antiguo general romano busca venganza contra el corrupto emperador que asesinó a su familia."),
    Pelicula(8, "Avatar", "James Cameron", "Ciencia Ficción", 2009, "Un marine parapléjico enviado al planeta Pandora se debate entre seguir órdenes o proteger su nuevo hogar."),
)
