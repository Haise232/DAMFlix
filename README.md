# DAMFlix — Parte 2: Registrar Película

Formulario para registrar una película, hecho con **Jetpack Compose** (Kotlin) para Android.

> Esta rama (`parte2`) contiene la segunda actividad del proyecto.
> La primera actividad (pantalla de perfil de usuario) está en la rama [`main`](https://github.com/Haise232/DAMFlix/tree/main).

## Qué hace

- **Campos de texto** (`OutlinedTextField`) con etiqueta y placeholder para:
  - Título de la película
  - Director/a
  - Año de estreno
- **Validación del año:** solo admite dígitos y un máximo de 4 caracteres, y abre el teclado numérico (`KeyboardType.Number`).
- **Checkbox "vista":** el texto cambia dinámicamente entre *Película vista* y *Película pendiente*.
- **Botón "Guardar Película":** solo se habilita cuando el título y el director no están vacíos.
- **Estado con Compose:** cada campo guarda su valor con `remember { mutableStateOf(...) }`.

## Estructura del código

| Composable | Función |
|---|---|
| `MainActivity` | Carga el tema `DAMFlixTheme` y un `Scaffold` con el formulario |
| `FormularioPelicula()` | Formulario completo: campos, checkbox y botón |
| `FormularioPeliculaPreview()` | Vista previa en Android Studio |

## Tecnologías

- Kotlin
- Jetpack Compose
- Material 3

## Cómo ejecutarlo

1. Clona el repositorio y cambia a esta rama:
   ```bash
   git clone https://github.com/Haise232/DAMFlix.git
   cd DAMFlix
   git checkout parte2
   ```
2. Ábrelo con Android Studio.
3. Espera a que termine la sincronización de Gradle.
4. Pulsa **Run** en un emulador o dispositivo, o abre el `@Preview` de `MainActivity.kt`.
