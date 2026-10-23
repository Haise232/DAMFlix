# DAMFlix — Parte 4: Navegación entre pantallas

App de catálogo de películas con varias pantallas conectadas mediante **Navigation Compose** y una barra de navegación inferior, hecha con **Jetpack Compose** (Kotlin) para Android.

> Esta rama (`parte4`) contiene la cuarta actividad del proyecto.
> - La primera actividad (pantalla de perfil de usuario) está en la rama [`main`](https://github.com/Haise232/DAMFlix/tree/main).
> - La segunda actividad (formulario Registrar Película) está en la rama [`parte2`](https://github.com/Haise232/DAMFlix/tree/parte2).
> - La tercera actividad (catálogo de películas con favoritos) está en la rama [`parte3`](https://github.com/Haise232/DAMFlix/tree/parte3).

## Qué hace

- **Navegación** con `NavHost` y `rememberNavController()`: cuatro pantallas (Inicio, Listado, Añadir y Perfil).
- **Rutas tipadas** con una `sealed class Ruta`: cada ruta guarda su `path`, su etiqueta y su icono.
- **Barra inferior** (`NavigationBar` + `NavigationBarItem`) que marca la pestaña activa leyendo `currentBackStackEntryAsState()`.
- **Navegación entre pestañas** con `popUpTo(startDestination) { saveState = true }`, `launchSingleTop` y `restoreState`, para no apilar pantallas repetidas y conservar el estado de cada pestaña.
- **Estado compartido:** la lista de películas vive en `DAMFlixApp()` (`mutableStateListOf`) y se pasa a cada pantalla, así lo que se añade o se marca como favorito se ve en todas.
- **Pantalla de inicio** con un resumen (total de películas y favoritas) y botones para ir al catálogo o añadir una película.
- **Listado en cuadrícula** con `LazyVerticalGrid` de 2 columnas: cada tarjeta muestra la inicial del título, director, género, año, si está vista o pendiente, y el botón de favorito.
- **Formulario** para registrar películas: valida que título, director y género no estén vacíos y que el año sea válido (1888–2100). Al guardar, la película se añade al catálogo y se navega al listado.
- **Perfil** con avatar, nombre, rol y estadísticas de películas vistas y favoritas (con `pluralStringResource`).
- **Vistas previas** en modo claro y oscuro.

## Estructura del código

| Archivo / elemento | Función |
|---|---|
| `MainActivity` | Carga el tema `DAMFlix4Theme` y la app |
| `DAMFlixApp()` | `Scaffold` con la `TopAppBar`, la `NavigationBar` y el `NavHost`; guarda la lista de películas |
| `navegarAPestana()` | Extensión de `NavHostController` para cambiar de pestaña sin duplicar pantallas |
| `navigation/Ruta.kt` | `sealed class` con las rutas `Inicio`, `Listado`, `Formulario` y `Perfil` |
| `model/Pelicula.kt` | `data class Pelicula` y la lista `peliculasIniciales` |
| `ui/screens/PantallaInicio.kt` | Pantalla de bienvenida con estadísticas y accesos rápidos |
| `ui/screens/ListaPeliculas.kt` | Cuadrícula de películas y `TarjetaPeliculaGrid()` |
| `ui/screens/Formulario.kt` | Formulario para registrar una película nueva |
| `ui/screens/Perfil.kt` | Perfil del usuario con sus estadísticas |

## Tecnologías

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Material Icons (`material-icons-core` y `material-icons-extended`)

## Cómo ejecutarlo

1. Clona el repositorio y cambia a esta rama:
   ```bash
   git clone https://github.com/Haise232/DAMFlix.git
   cd DAMFlix
   git checkout parte4
   ```
2. Ábrelo con Android Studio.
3. Espera a que termine la sincronización de Gradle.
4. Pulsa **Run** en un emulador o dispositivo, o abre los `@Preview` de `MainActivity.kt`.
