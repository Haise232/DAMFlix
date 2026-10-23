package com.example.damflix4.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Ruta(val path: String, val label: String, val icon: ImageVector) {
    data object Inicio : Ruta(
        path = "inicio",
        label = "Inicio",
        icon = Icons.Filled.Home,
    )

    data object Listado : Ruta(
        path = "listado",
        label = "Listado",
        icon = Icons.AutoMirrored.Filled.List,
    )

    data object Formulario : Ruta(
        path = "alta",
        label = "Añadir",
        icon = Icons.Filled.Add,
    )

    data object Perfil : Ruta(
        path = "perfil",
        label = "Perfil",
        icon = Icons.Filled.Person,
    )
}
