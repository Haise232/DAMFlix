# DAMFlix — Parte 5: Persistencia local con SQLite y MVVM

App de catálogo de películas hecha con **Jetpack Compose** (Kotlin) para Android. En esta fase la lista de películas deja de estar en memoria y se guarda en una base de datos **SQLite** del móvil, usando la arquitectura **MVVM**.

> Esta rama (`parte5`) contiene la quinta actividad del proyecto.
> - La primera actividad (pantalla de perfil de usuario) está en la rama [`main`](https://github.com/Haise232/DAMFlix/tree/main).
> - La segunda actividad (formulario Registrar Película) está en la rama [`parte2`](https://github.com/Haise232/DAMFlix/tree/parte2).
> - La tercera actividad (catálogo de películas con favoritos) está en la rama [`parte3`](https://github.com/Haise232/DAMFlix/tree/parte3).
> - La cuarta actividad (navegación entre pantallas) está en la rama [`parte4`](https://github.com/Haise232/DAMFlix/tree/parte4).

## Qué hace

- **Base de datos SQLite** (`damflix_native.db`) con `SQLiteOpenHelper` (`DAMFlixDbHelper`): crea la tabla `peliculas` y controla la versión de la BD. La primera vez que se crea se rellenan las películas de ejemplo.
- **Repositorio** (`PeliculaRepository`) con las consultas a la BD:
  - `obtenerTodas()`: `SELECT` con `rawQuery` y lectura manual del `Cursor`.
  - `insertar()` y `actualizar()`: `INSERT` y `UPDATE` con `ContentValues`.
- **Corrutinas:** todas las operaciones del repositorio van dentro de `withContext(Dispatchers.IO)` para no bloquear el hilo principal.
- **ViewModel** (`PeliculaViewModel`): recibe el repositorio por constructor, guarda la lista en un `MutableStateFlow` y la expone como `StateFlow` (`listaPeliculas`). Después de `agregarPelicula()` o `conmutarFavorito()` llama a `cargarPeliculas()` para que la pantalla se actualice.
- **Compose:** `ListaPeliculasScreen` y `FormularioAgregarPeliculaScreen` reciben el ViewModel. El listado observa el estado con `collectAsStateWithLifecycle()` y el formulario llama a `agregarPelicula()` al guardar.
- **Los datos se mantienen** aunque se cierre la app: las películas añadidas y las favoritas siguen ahí al volver a abrirla.
- **Sin textos hardcodeados:** todos los textos de la interfaz están en `strings.xml` y se usan con `stringResource()` (también las etiquetas de la barra inferior, que ahora son `@StringRes`).
- Se mantiene la navegación de la parte 4 (`NavHost` + `NavigationBar`).

## Estructura del código

| Archivo / elemento | Función |
|---|---|
| `MainActivity` | Crea el ViewModel (con su factory) y `DAMFlixApp()` con el `Scaffold`, la barra inferior y el `NavHost` |
| `data/DAMFlixDbHelper.kt` | `SQLiteOpenHelper`: `CREATE TABLE`, datos iniciales y `onUpgrade` |
| `data/PeliculaRepository.kt` | Consultas SQL (`SELECT`, `INSERT`, `UPDATE`) con `Dispatchers.IO` |
| `ui/viewmodel/PeliculaViewModel.kt` | `ViewModel` con el `StateFlow` de películas |
| `navigation/Ruta.kt` | `sealed class` con las rutas de la app |
| `model/Pelicula.kt` | `data class Pelicula` y las películas de ejemplo |
| `ui/screens/ListaPeliculasScreen.kt` | Cuadrícula de películas conectada al ViewModel |
| `ui/screens/FormularioAgregarPeliculaScreen.kt` | Formulario de alta que guarda en SQLite |
| `ui/screens/PantallaInicio.kt`, `Perfil.kt` | Inicio con estadísticas y perfil del usuario |

## Tecnologías

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- SQLite (`SQLiteOpenHelper`, `Cursor`, `ContentValues`)
- Corrutinas de Kotlin
- ViewModel y `StateFlow` (`lifecycle-viewmodel-compose`, `lifecycle-runtime-compose`)

## Cómo ejecutarlo

1. Clona el repositorio y cambia a esta rama:
   ```bash
   git clone https://github.com/Haise232/DAMFlix.git
   cd DAMFlix
   git checkout parte5
   ```
2. Ábrelo con Android Studio.
3. Espera a que termine la sincronización de Gradle.
4. Pulsa **Run** en un emulador o dispositivo.
5. Para ver la base de datos: **App Inspection > Database Inspector** con la app en marcha.
