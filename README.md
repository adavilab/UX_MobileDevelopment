# StudyFlow — Maqueta navegable (UX)

Prototipo de interfaz para **StudyFlow**, una alarma de estudio inteligente que organiza
pomodoros según el calendario académico del usuario. Este repositorio es un **entregable
académico para un curso de UX**: contiene únicamente la maquetación visual e interactiva
de la app, sin ningún backend ni lógica funcional real.

## Qué es (y qué no es) este prototipo

- ✅ Vistas construidas en **Jetpack Compose** con datos de ejemplo (hardcodeados).
- ✅ Navegación real entre pantallas (Login ⇄ Hoy), en ambos sentidos.
- ✅ Componentes interactivos a nivel visual: mostrar/ocultar contraseña, selección de
  eventos en el timeline, cambio del ítem activo en el bottom nav.
- ❌ No hay llamadas a red, base de datos, autenticación real ni persistencia de datos.
- ❌ No hay ViewModels con lógica de negocio ni capas de datos/dominio.
- ❌ Ningún dato mostrado en pantalla es real: correos, contraseñas, eventos del
  calendario y contadores de pomodoros son valores de muestra escritos directamente en
  el código de la UI.

Todo el estado vive en memoria dentro de los Composables (`remember` / `mutableStateOf`)
y se pierde al cerrar la app — es el comportamiento esperado de una maqueta de UX.

## Pantallas incluidas

1. **Login** (`ui/screens/login/LoginScreen.kt`) — Campos de correo y contraseña (con
   toggle de mostrar/ocultar contraseña), botón "Iniciar Sesión" que navega a la vista
   Hoy.
2. **Hoy / Calendario diario** (`ui/screens/today/TodayScreen.kt`) — Timeline vertical de
   7am a 9pm con bloques de eventos mockeados, contador de pomodoros, bottom navigation
   bar de 3 ítems, e ícono de logout/perfil que regresa al Login.

## Estructura del proyecto

```
app/src/main/java/com/studyflow/app/
├── MainActivity.kt
├── navigation/
│   └── StudyFlowNavHost.kt       # NavHost con las rutas "login" y "today"
├── ui/
│   ├── theme/                    # Color.kt, Type.kt, Shape.kt, Theme.kt
│   ├── components/                # TimelineEventBlock, DailyTimeline, BottomNavBar, LabeledTextField
│   └── screens/
│       ├── login/LoginScreen.kt
│       └── today/
│           ├── TodayScreen.kt
│           └── TodayMocks.kt      # datos hardcodeados de la vista Hoy
```

## Cómo correrlo

### Con Android Studio (recomendado)
1. Clona el repositorio: `git clone https://github.com/adavilab/UX_MobileDevelopment.git`
2. Ábrelo con Android Studio (Koala o superior) y deja que sincronice Gradle.
3. Ejecuta la configuración `app` en un emulador o dispositivo con **Android 8.0
   (API 26)** o superior.

### Por línea de comandos
```bash
./gradlew installDebug   # macOS/Linux
gradlew.bat installDebug # Windows
```
Requiere tener el Android SDK instalado y la variable `ANDROID_HOME` configurada
(o un archivo `local.properties` con `sdk.dir=...`).

## Stack técnico

- Kotlin + Jetpack Compose (Material 3)
- Navigation Compose para el flujo entre pantallas
- Gradle con Kotlin DSL y version catalog (`gradle/libs.versions.toml`)
- `minSdk` 26, `compileSdk`/`targetSdk` 35

## Nota académica

Este repositorio se entrega como maqueta de UX. La ausencia de backend, autenticación
real y persistencia de datos es intencional y forma parte del alcance del entregable.
