package com.example.damflix5.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.damflix5.R
import com.example.damflix5.ui.theme.DAMFlix5Theme

@Composable
fun PantallaInicio(
    totalPeliculas: Int,
    totalFavoritas: Int,
    onVerCatalogo: () -> Unit,
    onAnadir: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.inicio_bienvenida),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.inicio_subtitulo),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaEstadistica(titulo = stringResource(R.string.inicio_peliculas), valor = totalPeliculas, modifier = Modifier.weight(1f))
            TarjetaEstadistica(titulo = stringResource(R.string.inicio_favoritas), valor = totalFavoritas, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onVerCatalogo, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.inicio_ver_catalogo))
        }
        OutlinedButton(onClick = onAnadir, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.inicio_anadir))
        }
    }
}

@Composable
fun TarjetaEstadistica(titulo: String, valor: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = valor.toString(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = titulo, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaEstadisticaPreview() {
    DAMFlix5Theme {
        TarjetaEstadistica(titulo = stringResource(R.string.inicio_peliculas), valor = 8)
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    DAMFlix5Theme {
        PantallaInicio(totalPeliculas = 8, totalFavoritas = 2, onVerCatalogo = {}, onAnadir = {})
    }
}
