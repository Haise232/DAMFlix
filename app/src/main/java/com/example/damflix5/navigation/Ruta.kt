package com.example.damflix5.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.damflix5.R

sealed class Ruta(val path: String, @StringRes val label: Int, val icon: ImageVector) {
    data object Inicio : Ruta(
        path = "inicio",
        label = R.string.nav_inicio,
        icon = Icons.Filled.Home,
    )

    data object Listado : Ruta(
        path = "listado",
        label = R.string.nav_listado,
        icon = Icons.AutoMirrored.Filled.List,
    )

    data object Formulario : Ruta(
        path = "alta",
        label = R.string.nav_anadir,
        icon = Icons.Filled.Add,
    )

    data object Perfil : Ruta(
        path = "perfil",
        label = R.string.nav_perfil,
        icon = Icons.Filled.Person,
    )
}
