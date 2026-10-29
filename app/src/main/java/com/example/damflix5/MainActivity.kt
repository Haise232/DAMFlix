package com.example.damflix5

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.damflix5.data.DAMFlixDbHelper
import com.example.damflix5.data.PeliculaRepository
import com.example.damflix5.navigation.Ruta
import com.example.damflix5.ui.screens.FormularioAgregarPeliculaScreen
import com.example.damflix5.ui.screens.ListaPeliculasScreen
import com.example.damflix5.ui.screens.PantallaInicio
import com.example.damflix5.ui.screens.Perfil
import com.example.damflix5.ui.theme.DAMFlix5Theme
import com.example.damflix5.ui.viewmodel.PeliculaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DAMFlix5Theme {
                // El ViewModel recibe el repositorio, así que se crea con una factory
                val peliculaViewModel: PeliculaViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            PeliculaViewModel(PeliculaRepository(DAMFlixDbHelper(applicationContext)))
                        }
                    }
                )
                DAMFlixApp(peliculaViewModel)
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
fun DAMFlixApp(peliculaViewModel: PeliculaViewModel) {
    val navController = rememberNavController()
    val peliculas by peliculaViewModel.listaPeliculas.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_app),
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
                    val etiqueta = stringResource(ruta.label)
                    NavigationBarItem(
                        selected = rutaActual == ruta.path,
                        onClick = {
                            if (rutaActual != ruta.path) {
                                navController.navegarAPestana(ruta)
                            }
                        },
                        icon = { Icon(ruta.icon, contentDescription = etiqueta) },
                        label = { Text(etiqueta) }
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
                ListaPeliculasScreen(viewModel = peliculaViewModel)
            }
            composable(Ruta.Formulario.path) {
                FormularioAgregarPeliculaScreen(
                    viewModel = peliculaViewModel,
                    onPeliculaGuardada = { navController.navegarAPestana(Ruta.Listado) }
                )
            }
            composable(Ruta.Perfil.path) {
                Perfil(
                    totalVistas = peliculas.count { it.esVista },
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
