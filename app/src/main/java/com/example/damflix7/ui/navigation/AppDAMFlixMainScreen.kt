package com.example.damflix7.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel as obtenerViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.damflix7.DAMFlixApplication
import com.example.damflix7.R
import com.example.damflix7.ui.screens.FormularioAgregarPeliculaScreen
import com.example.damflix7.ui.screens.ListaPeliculasScreen
import com.example.damflix7.ui.screens.PantallaDetalleScreen
import com.example.damflix7.ui.screens.PantallaInicio
import com.example.damflix7.ui.screens.Perfil
import com.example.damflix7.ui.viewmodel.DetallePeliculaViewModel
import com.example.damflix7.ui.viewmodel.DetallePeliculaViewModelFactory
import com.example.damflix7.ui.viewmodel.PeliculaViewModel
import kotlinx.coroutines.launch

private val opcionesMenu = listOf(Ruta.Inicio, Ruta.Listado, Ruta.Formulario, Ruta.Perfil)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDAMFlixMainScreen(viewModel: PeliculaViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route
    val peliculas by viewModel.listaPeliculas.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                    Text(
                        text = stringResource(R.string.drawer_cabecera),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                opcionesMenu.forEach { opcion ->
                    val etiqueta = stringResource(opcion.label)
                    NavigationDrawerItem(
                        icon = { Icon(opcion.icon, contentDescription = etiqueta) },
                        label = { Text(etiqueta) },
                        selected = rutaActual == opcion.path,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            if (rutaActual != opcion.path) {
                                navController.navegarADestino(opcion)
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.titulo_app),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.abrir_menu)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
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
                        onVerCatalogo = { navController.navegarADestino(Ruta.Listado) },
                        onAnadir = { navController.navegarADestino(Ruta.Formulario) }
                    )
                }
                composable(Ruta.Listado.path) {
                    // La lista avisa con el id y aquí se hace la navegación
                    ListaPeliculasScreen(
                        viewModel = viewModel,
                        onPeliculaClick = { peliculaId ->
                            navController.navigate(Ruta.Detalle.crearRuta(peliculaId))
                        }
                    )
                }
                composable(
                    route = Ruta.Detalle.path,
                    arguments = listOf(
                        navArgument("peliculaId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val peliculaId = backStackEntry.arguments?.getInt("peliculaId") ?: 0

                    val app = LocalContext.current.applicationContext as DAMFlixApplication
                    val detalleViewModel: DetallePeliculaViewModel = obtenerViewModel(
                        factory = DetallePeliculaViewModelFactory(app.repository)
                    )

                    PantallaDetalleScreen(
                        peliculaId = peliculaId,
                        viewModel = detalleViewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }
                composable(Ruta.Formulario.path) {
                    FormularioAgregarPeliculaScreen(
                        viewModel = viewModel,
                        onPeliculaGuardada = { navController.navegarADestino(Ruta.Listado) }
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
}

private fun NavHostController.navegarADestino(ruta: Ruta) {
    navigate(ruta.path) {
        popUpTo(graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
