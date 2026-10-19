package com.example.damflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.damflix.ui.theme.DAMFlixTheme

data class Pelicula(
    val id: Int,
    val titulo: String,
    val genero: String,
    val anyo: Int,
    val descripcion: String,
    var esFavorita: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DAMFlixTheme {
                CatalogoPeliculasApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoPeliculasApp() {
    val listaPeliculas = remember {
        mutableStateListOf(
            Pelicula(1, "Inception", "Ciencia Ficción", 2010, "Un ladrón que roba secretos corporativos a través del uso de la tecnología de intercambio de sueños."),
            Pelicula(2, "The Dark Knight", "Acción", 2008, "Batman acepta uno de los mayores desafíos psicológicos y físicos para luchar contra la injusticia."),
            Pelicula(3, "Interstellar", "Ciencia Ficción", 2014, "Un grupo de exploradores viaja a través de un agujero de gusano en el espacio para salvar a la humanidad."),
            Pelicula(4, "Pulp Fiction", "Crimen", 1994, "Las vidas de dos matones de la mafia, un boxeador y la esposa de un gángster se entrelazan."),
            Pelicula(5, "Matrix", "Ciencia Ficción", 1999, "Un hacker descubre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores."),
            Pelicula(6, "El Señor de los Anillos", "Fantasía", 2003, "Gandalf y Aragorn lideran el mundo de los hombres contra el ejército de Sauron."),
            Pelicula(7, "Gladiator", "Acción", 2000, "Un antiguo general romano busca venganza contra el corrupto emperador que asesinó a su familia."),
            Pelicula(8, "Avatar", "Ciencia Ficción", 2009, "Un marine parapléjico enviado al planeta Pandora se debate entre seguir órdenes o proteger su nuevo hogar.")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DAMFlix",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValores ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValores),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = listaPeliculas,
                key = { pelicula -> pelicula.id }
            ) { pelicula ->
                TarjetaPelicula(
                    pelicula = pelicula,
                    onFavoritoClick = {
                        val index = listaPeliculas.indexOfFirst { it.id == pelicula.id }
                        if (index != -1) {
                            listaPeliculas[index] = listaPeliculas[index].copy(
                                esFavorita = !listaPeliculas[index].esFavorita
                            )
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun TarjetaPelicula(
    pelicula: Pelicula,
    onFavoritoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = pelicula.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${pelicula.genero} • ${pelicula.anyo}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = pelicula.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onFavoritoClick) {
                Icon(
                    imageVector = if (pelicula.esFavorita) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (pelicula.esFavorita) "Quitar de favoritos" else "Marcar como favorita",
                    tint = if (pelicula.esFavorita) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoPeliculasAppPreview() {
    DAMFlixTheme {
        CatalogoPeliculasApp()
    }
}
