package com.example.damflix5.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.damflix5.R
import com.example.damflix5.model.Pelicula
import com.example.damflix5.model.peliculasIniciales
import com.example.damflix5.ui.theme.DAMFlix5Theme
import com.example.damflix5.ui.viewmodel.PeliculaViewModel

@Composable
fun ListaPeliculasScreen(
    viewModel: PeliculaViewModel,
    modifier: Modifier = Modifier,
) {
    val peliculas by viewModel.listaPeliculas.collectAsStateWithLifecycle()

    ListaPeliculas(
        peliculas = peliculas,
        onToggleFavorito = { pelicula -> viewModel.conmutarFavorito(pelicula) },
        modifier = modifier
    )
}

@Composable
fun ListaPeliculas(
    peliculas: List<Pelicula>,
    onToggleFavorito: (Pelicula) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (peliculas.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.lista_vacia))
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = peliculas,
            key = { pelicula -> pelicula.id }
        ) { pelicula ->
            TarjetaPeliculaGrid(
                pelicula = pelicula,
                onFavoritoClick = { onToggleFavorito(pelicula) }
            )
        }
    }
}

@Composable
fun TarjetaPeliculaGrid(
    pelicula: Pelicula,
    onFavoritoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pelicula.titulo.take(1).uppercase(),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            IconButton(
                onClick = onFavoritoClick,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (pelicula.esFavorita) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(
                        if (pelicula.esFavorita) R.string.quitar_favorito else R.string.marcar_favorito
                    ),
                    tint = if (pelicula.esFavorita) Color.Red else Color.Gray
                )
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = pelicula.titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = pelicula.director,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(R.string.genero_anio, pelicula.genero, pelicula.anio),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(if (pelicula.esVista) R.string.estado_vista else R.string.estado_pendiente),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 180)
@Composable
fun TarjetaPeliculaGridPreview() {
    DAMFlix5Theme {
        TarjetaPeliculaGrid(pelicula = peliculasIniciales.first(), onFavoritoClick = {})
    }
}

@Preview(showBackground = true, widthDp = 180)
@Composable
fun TarjetaPeliculaGridFavoritaPreview() {
    DAMFlix5Theme {
        TarjetaPeliculaGrid(pelicula = peliculasIniciales.first().copy(esFavorita = true), onFavoritoClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ListaPeliculasVaciaPreview() {
    DAMFlix5Theme {
        ListaPeliculas(peliculas = emptyList(), onToggleFavorito = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ListaPeliculasPreview() {
    DAMFlix5Theme {
        ListaPeliculas(peliculas = peliculasIniciales, onToggleFavorito = {})
    }
}
