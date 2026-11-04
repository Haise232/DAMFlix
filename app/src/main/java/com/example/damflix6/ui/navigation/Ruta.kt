package com.example.damflix6.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.damflix6.R

sealed class Ruta(val path: String, @StringRes val label: Int, val icon: ImageVector) {
    data object Inicio : Ruta("inicio", R.string.nav_inicio, Icons.Filled.Home)
    data object Listado : Ruta("listado", R.string.nav_listado, Icons.AutoMirrored.Filled.List)
    data object Formulario : Ruta("alta", R.string.nav_anadir, Icons.Filled.Add)
    data object Perfil : Ruta("perfil", R.string.nav_perfil, Icons.Filled.Person)
}
