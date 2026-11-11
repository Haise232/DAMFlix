package com.example.damflix7.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.damflix7.R

sealed class Ruta(val path: String, @StringRes val label: Int, val icon: ImageVector) {
    data object Inicio : Ruta("inicio", R.string.nav_inicio, Icons.Filled.Home)
    data object Listado : Ruta("listado", R.string.nav_listado, Icons.AutoMirrored.Filled.List)
    data object Formulario : Ruta("alta", R.string.nav_anadir, Icons.Filled.Add)
    data object Perfil : Ruta("perfil", R.string.nav_perfil, Icons.Filled.Person)

    // Ruta con parámetro: el id de la película va dentro de la propia ruta
    data object Detalle : Ruta("detalle/{peliculaId}", R.string.nav_detalle, Icons.Filled.Info) {
        fun crearRuta(peliculaId: Int) = "detalle/$peliculaId"
    }
}
