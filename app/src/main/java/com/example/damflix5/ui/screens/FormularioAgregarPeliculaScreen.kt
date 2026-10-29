package com.example.damflix5.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.damflix5.R
import com.example.damflix5.ui.viewmodel.PeliculaViewModel

@Composable
fun FormularioAgregarPeliculaScreen(
    viewModel: PeliculaViewModel,
    onPeliculaGuardada: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var director by rememberSaveable { mutableStateOf("") }
    var genero by rememberSaveable { mutableStateOf("") }
    var anioTexto by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var esVista by rememberSaveable { mutableStateOf(false) }

    val anioValido = anioTexto.toIntOrNull()?.let { anio -> anio in 1888..2100 } ?: false
    val esFormularioValido = titulo.isNotBlank() &&
                             director.isNotBlank() &&
                             genero.isNotBlank() &&
                             anioValido

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = stringResource(R.string.form_titulo), style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text(stringResource(R.string.form_label_titulo)) },
            placeholder = { Text(stringResource(R.string.form_hint_titulo)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = director,
            onValueChange = { director = it },
            label = { Text(stringResource(R.string.form_label_director)) },
            placeholder = { Text(stringResource(R.string.form_hint_director)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text(stringResource(R.string.form_label_genero)) },
            placeholder = { Text(stringResource(R.string.form_hint_genero)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = anioTexto,
            onValueChange = { nuevo -> anioTexto = nuevo.filter { it.isDigit() }.take(4) },
            label = { Text(stringResource(R.string.form_label_anio)) },
            placeholder = { Text(stringResource(R.string.form_hint_anio)) },
            singleLine = true,
            isError = anioTexto.isNotEmpty() && !anioValido,
            supportingText = {
                if (anioTexto.isNotEmpty() && !anioValido) Text(stringResource(R.string.form_error_anio))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text(stringResource(R.string.form_label_descripcion)) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = esVista, onCheckedChange = { esVista = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(if (esVista) R.string.form_vista else R.string.form_pendiente),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {
                val anio = anioTexto.toIntOrNull() ?: 0
                viewModel.agregarPelicula(
                    titulo = titulo.trim(),
                    director = director.trim(),
                    genero = genero.trim(),
                    anio = anio,
                    descripcion = descripcion.trim(),
                    esVista = esVista
                )
                titulo = ""
                director = ""
                genero = ""
                anioTexto = ""
                descripcion = ""
                esVista = false
                onPeliculaGuardada()
            },
            enabled = esFormularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.form_guardar))
        }
    }
}
