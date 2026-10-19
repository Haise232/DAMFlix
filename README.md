# DAMFlix — Parte 3: Catálogo de Películas

Catálogo de películas en lista con opción de marcar favoritas, hecho con **Jetpack Compose** (Kotlin) para Android.

> Esta rama (`parte3`) contiene la tercera actividad del proyecto.
> - La primera actividad (pantalla de perfil de usuario) está en la rama [`main`](https://github.com/Haise232/DAMFlix/tree/main).
> - La segunda actividad (formulario Registrar Película) está en la rama [`parte2`](https://github.com/Haise232/DAMFlix/tree/parte2).

## Captura

![Captura de DAMFlix - Catálogo de Películas](docs/Captura%20Proyecto.png)

## Qué hace

- **Barra superior** (`TopAppBar`) con el título *DAMFlix* sobre el color `primaryContainer` del tema.
- **Lista eficiente** con `LazyColumn`: solo se dibujan las tarjetas visibles en pantalla, con separación entre ellas (`Arrangement.spacedBy`).
- **Modelo de datos** `Pelicula` (`data class`) con id, título, género, año, descripción y si es favorita.
- **Tarjeta por película** (`Card`) con título, género y año, y una breve descripción.
- **Botón de favorito** (`IconButton`): alterna entre corazón vacío gris (`FavoriteBorder`) y corazón relleno rojo (`Favorite`).
- **Estado con Compose:** la lista se guarda con `remember { mutableStateListOf(...) }` y, al pulsar el corazón, se sustituye la película por una copia (`copy(esFavorita = ...)`) para que Compose recomponga la tarjeta.
- **Claves estables** en `items(key = { it.id })` para que Compose identifique cada elemento de la lista.

## Estructura del código

| Elemento | Función |
|---|---|
| `Pelicula` | `data class` con los datos de cada película |
| `MainActivity` | Carga el tema `DAMFlixTheme` y la app del catálogo |
| `CatalogoPeliculasApp()` | `Scaffold` con la `TopAppBar` y la `LazyColumn` de películas; gestiona el estado de favoritos |
| `TarjetaPelicula()` | Tarjeta de una película con su información y el botón de favorito |
| `CatalogoPeliculasAppPreview()` | Vista previa en Android Studio |

## Tecnologías

- Kotlin
- Jetpack Compose
- Material 3
- Material Icons (`material-icons-core` y `material-icons-extended`)

## Cómo ejecutarlo

1. Clona el repositorio y cambia a esta rama:
   ```bash
   git clone https://github.com/Haise232/DAMFlix.git
   cd DAMFlix
   git checkout parte3
   ```
2. Ábrelo con Android Studio.
3. Espera a que termine la sincronización de Gradle.
4. Pulsa **Run** en un emulador o dispositivo, o abre el `@Preview` de `MainActivity.kt`.
