# DAMFlix

Pantalla de perfil de usuario hecha con **Jetpack Compose** (Kotlin) para Android.

## Captura

![Captura de DAMFlix](docs/Captura%20de%20pantalla.png)

## Qué muestra

- **Cabecera de usuario:** avatar redondo (`Modifier.clip(CircleShape)`) con borde personalizado.
- **Información general:** nombre, rol (*Crítico de Cine*) y estadísticas estáticas (*42 Películas Vistas*, *12 Reseñas*).
- **Sin textos hardcoded:** todos los textos se leen de `strings.xml` con `stringResource()`.

## Estructura del código

| Composable | Función |
|---|---|
| `DAMFlix()` | Contenedor general que ocupa toda la pantalla |
| `CabeceraUsuario()` | Avatar, nombre y rol |
| `Estadisticas()` | Fila con las dos estadísticas |
| `EstadisticaItem()` | Tarjeta reutilizable de una estadística |

## Tecnologías

- Kotlin
- Jetpack Compose
- Material 3
- minSdk 24 / targetSdk 37

## Cómo ejecutarlo

1. Clona el repositorio.
2. Ábrelo con Android Studio.
3. Espera a que termine la sincronización de Gradle.
4. Pulsa **Run** en un emulador o dispositivo, o abre el `@Preview` de `MainActivity.kt`.
