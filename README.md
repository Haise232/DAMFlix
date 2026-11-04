# DAMFlix — Parte 6: Room ORM, MVVM y navegación con Drawer

App de catálogo de películas hecha con **Jetpack Compose** (Kotlin). En esta fase la persistencia pasa de SQLite nativo (`SQLiteOpenHelper`) a **Room ORM**, y la barra de navegación inferior se sustituye por un menú lateral **`ModalNavigationDrawer`**.

> Esta rama (`parte6`) contiene la sexta actividad. Las anteriores están en `main` y `parte2`–`parte5`.

## Qué hace

- **Room:** `PeliculaEntity` (`@Entity`), `PeliculaDao` (`@Query`, `@Insert`, `@Update`, `@Delete`) y `AppDatabase` (`@Database`, Singleton). El listado devuelve `Flow<List<PeliculaEntity>>`.
- **Reactividad:** `PeliculaViewModel` convierte el `Flow` en `StateFlow` con `stateIn()`; ya no hay recarga manual tras insertar o actualizar.
- **Ciclo de vida:** `DAMFlixApplication` (con `by lazy`) guarda la BD y el repositorio; `PeliculaViewModelFactory` crea el ViewModel, que sobrevive a las rotaciones.
- **Drawer:** `ModalNavigationDrawer` + `ModalDrawerSheet` con cabecera y `NavigationDrawerItem`; botón hamburguesa (`Icons.Default.Menu`) en el `navigationIcon` de la `TopAppBar`, que abre/cierra el cajón con corrutinas.
- **Películas de ejemplo** insertadas con un `RoomDatabase.Callback` en la primera creación.
- **Sin textos hardcodeados:** todo va por `strings.xml` y `stringResource()`.

## Tecnologías

Kotlin, Jetpack Compose, Material 3, Room 2.8.5 + KSP, Navigation Compose, ViewModel, Flow/StateFlow.
`gradle.properties` incluye `android.disallowKotlinSourceSets=false` (compatibilidad KSP con AGP 9).
