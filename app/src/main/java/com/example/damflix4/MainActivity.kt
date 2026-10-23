package com.example.damflix4

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.damflix4.model.Pelicula
import com.example.damflix4.model.peliculasIniciales
import com.example.damflix4.navigation.Ruta
import com.example.damflix4.ui.screens.Formulario
import com.example.damflix4.ui.screens.ListaPeliculas
import com.example.damflix4.ui.screens.PantallaInicio
import com.example.damflix4.ui.screens.Perfil
import com.example.damflix4.ui.theme.DAMFlix4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DAMFlix4Theme {
                DAMFlixApp()
            }
        }
    }
}

private val rutasBottomBar = listOf(
    Ruta.Inicio,
    Ruta.Listado,
    Ruta.Formulario,
    Ruta.Perfil,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DAMFlixApp() {
    val navController = rememberNavController()
    val peliculas = remember { mutableStateListOf(*peliculasIniciales.toTypedArray()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DAMFlix - Catálogo",
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val rutaActual = navBackStackEntry?.destination?.route

            NavigationBar {
                rutasBottomBar.forEach { ruta ->
                    NavigationBarItem(
                        selected = rutaActual == ruta.path,
                        onClick = {
                            if (rutaActual != ruta.path) {
                                navController.navegarAPestana(ruta)
                            }
                        },
                        icon = { Icon(ruta.icon, contentDescription = ruta.label) },
                        label = { Text(ruta.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Ruta.Inicio.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Ruta.Inicio.path) {
                PantallaInicio(
                    totalPeliculas = peliculas.size,
                    totalFavoritas = peliculas.count { it.esFavorita },
                    onVerCatalogo = { navController.navegarAPestana(Ruta.Listado) },
                    onAnadir = { navController.navegarAPestana(Ruta.Formulario) }
                )
            }
            composable(Ruta.Listado.path) {
                ListaPeliculas(
                    peliculas = peliculas,
                    onToggleFavorito = { pelicula ->
                        val index = peliculas.indexOfFirst { it.id == pelicula.id }
                        if (index != -1) {
                            peliculas[index] = peliculas[index].copy(
                                esFavorita = !peliculas[index].esFavorita
                            )
                        }
                    }
                )
            }
            composable(Ruta.Formulario.path) {
                Formulario(
                    onGuardar = { titulo, director, genero, anyo, descripcion, vista ->
                        val nuevoId = (peliculas.maxOfOrNull { it.id } ?: 0) + 1
                        peliculas.add(Pelicula(nuevoId, titulo, director, genero, anyo, descripcion, vista))
                        navController.navegarAPestana(Ruta.Listado)
                    }
                )
            }
            composable(Ruta.Perfil.path) {
                Perfil(
                    totalVistas = peliculas.count { it.vista },
                    totalFavoritas = peliculas.count { it.esFavorita }
                )
            }
        }
    }
}

private fun NavHostController.navegarAPestana(ruta: Ruta) {
    navigate(ruta.path) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DAMFlixAppPreview() {
    DAMFlix4Theme {
        DAMFlixApp()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DAMFlixAppDarkPreview() {
    DAMFlix4Theme {
        DAMFlixApp()
    }
}
