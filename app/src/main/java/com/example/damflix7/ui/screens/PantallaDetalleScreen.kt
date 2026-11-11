package com.example.damflix7.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.damflix7.R
import com.example.damflix7.data.local.PeliculaEntity
import com.example.damflix7.data.local.peliculasIniciales
import com.example.damflix7.ui.theme.DAMFlix7Theme
import com.example.damflix7.ui.viewmodel.DetallePeliculaViewModel
import com.example.damflix7.ui.viewmodel.DetalleUiState

@Composable
fun PantallaDetalleScreen(
    peliculaId: Int,
    viewModel: DetallePeliculaViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Se lanza la carga cada vez que cambia el id de la película
    LaunchedEffect(peliculaId) {
        viewModel.cargarDetallePelicula(peliculaId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetallePelicula(
        uiState = uiState,
        onVolver = onVolver,
        modifier = modifier
    )
}

@Composable
fun DetallePelicula(
    uiState: DetalleUiState,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onVolver) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.detalle_volver),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Según el estado se muestra una cosa u otra
        when (uiState) {
            is DetalleUiState.Cargando -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.detalle_cargando))
                    }
                }
            }

            is DetalleUiState.Exito -> {
                TarjetaDetalle(pelicula = uiState.pelicula)
            }

            is DetalleUiState.Error -> {
                Text(
                    text = stringResource(R.string.detalle_error, uiState.mensaje),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun TarjetaDetalle(
    pelicula: PeliculaEntity,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = pelicula.titulo,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            HorizontalDivider()
            Text(
                text = stringResource(R.string.detalle_director, pelicula.director),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.detalle_genero, pelicula.genero),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.detalle_anio, pelicula.anio),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(
                    R.string.detalle_estado,
                    stringResource(if (pelicula.esVista) R.string.estado_vista else R.string.estado_pendiente)
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            if (pelicula.esFavorita) {
                Text(
                    text = stringResource(R.string.detalle_favorita),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = pelicula.descripcion.ifBlank { stringResource(R.string.detalle_sin_descripcion) },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleCargandoPreview() {
    DAMFlix7Theme {
        DetallePelicula(uiState = DetalleUiState.Cargando, onVolver = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleExitoPreview() {
    DAMFlix7Theme {
        DetallePelicula(
            uiState = DetalleUiState.Exito(peliculasIniciales.first()),
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleErrorPreview() {
    DAMFlix7Theme {
        DetallePelicula(uiState = DetalleUiState.Error("Película no encontrada"), onVolver = {})
    }
}
