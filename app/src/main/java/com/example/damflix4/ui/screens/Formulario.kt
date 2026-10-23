package com.example.damflix4.ui.screens

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.damflix4.ui.theme.DAMFlix4Theme

@Composable
fun Formulario(
    onGuardar: (titulo: String, director: String, genero: String, anyo: Int, descripcion: String, vista: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var director by rememberSaveable { mutableStateOf("") }
    var genero by rememberSaveable { mutableStateOf("") }
    var anyo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var vista by rememberSaveable { mutableStateOf(false) }

    val anyoValido = anyo.toIntOrNull()?.let { year -> year in 1888..2100 } ?: false
    val formularioValido = titulo.isNotBlank() && director.isNotBlank() && genero.isNotBlank() && anyoValido

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Registrar Película", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de la película") },
            placeholder = { Text("Ej: Interstellar") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = director,
            onValueChange = { director = it },
            label = { Text("Director/a") },
            placeholder = { Text("Ej: Christopher Nolan") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text("Género") },
            placeholder = { Text("Ej: Ciencia Ficción") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = anyo,
            onValueChange = { nuevo -> anyo = nuevo.filter { it.isDigit() }.take(4) },
            label = { Text("Año de estreno") },
            placeholder = { Text("Ej. 2014") },
            singleLine = true,
            isError = anyo.isNotEmpty() && !anyoValido,
            supportingText = {
                if (anyo.isNotEmpty() && !anyoValido) Text("Introduce un año válido")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = vista,
                onCheckedChange = { vista = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (vista) "Película vista" else "Película pendiente",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = {
                onGuardar(titulo.trim(), director.trim(), genero.trim(), anyo.toInt(), descripcion.trim(), vista)
                titulo = ""
                director = ""
                genero = ""
                anyo = ""
                descripcion = ""
                vista = false
            },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Película")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioPreview() {
    DAMFlix4Theme {
        Formulario(onGuardar = { _, _, _, _, _, _ -> })
    }
}
