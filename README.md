# DAMFlix — Parte 7: Navegación con parámetros y corrutinas

App de catálogo de películas hecha con **Jetpack Compose** (Kotlin). En esta fase se añade el patrón **Maestro-Detalle**: al pulsar una película del catálogo se abre una pantalla de detalle que recibe el `peliculaId` por la ruta y carga la ficha desde Room con corrutinas.

> Esta rama (`parte7`) contiene la séptima actividad. Las anteriores están en `main` y `parte2`–`parte6`.

## Qué hace

- **Consulta por id:** `PeliculaDao.obtenerPorId(id)` (`@Query`, `suspend`) y su función en `PeliculaRepository`.
- **Estado de la UI:** `DetalleUiState` (interfaz sellada) con `Cargando`, `Exito` y `Error`.
- **ViewModel de detalle:** `DetallePeliculaViewModel` expone un `StateFlow<DetalleUiState>`; `cargarDetallePelicula()` lanza una corrutina en `viewModelScope` y lee Room con `withContext(Dispatchers.IO)`. Lleva su propia factory.
- **Ruta con argumento:** `Ruta.Detalle` (`detalle/{peliculaId}`) con `crearRuta(id)`, registrada en el `NavHost` con `navArgument("peliculaId") { type = NavType.IntType }`.
- **Event hoisting:** `ListaPeliculasScreen` recibe `onPeliculaClick: (Int) -> Unit` y es el `NavHost` quien navega.
- **Pantalla de detalle:** `PantallaDetalleScreen` usa `LaunchedEffect(peliculaId)` para cargar, muestra un `CircularProgressIndicator` mientras carga, una tarjeta con la ficha al terminar y un botón para volver con `popBackStack()`.
- **Sin textos hardcodeados:** todo va por `strings.xml` y `stringResource()`.

## Tecnologías

Kotlin, Jetpack Compose, Material 3, Room 2.8.5 + KSP, Navigation Compose, ViewModel, Coroutines, Flow/StateFlow.
`gradle.properties` incluye `android.disallowKotlinSourceSets=false` (compatibilidad KSP con AGP 9).
