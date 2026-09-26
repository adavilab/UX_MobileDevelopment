# StudyFlow — Maqueta navegable (UX)

Prototipo de interfaz para **StudyFlow**, una alarma de estudio inteligente que organiza
pomodoros según el calendario académico del usuario. Este repositorio es un **entregable
académico para un curso de UX**: contiene únicamente la maquetación visual e interactiva
de la app, sin ningún backend ni lógica funcional real.

## Cuenta de prueba

No hay registro ni backend: el login solo acepta esta cuenta de ejemplo.

| Campo | Valor |
|-------|-------|
| Correo | `ejemplo@gmail.com` |
| Contraseña | `1234567` |

Con cualquier otro dato aparece el mensaje "Correo o contraseña incorrectos".

## Qué es (y qué no es) este prototipo

- ✅ Vistas construidas en **Jetpack Compose** con datos de ejemplo (hardcodeados).
- ✅ Navegación real entre las 10 pantallas del wireframe de Figma, en ambos sentidos
  (incluye el botón "atrás" del sistema).
- ✅ Temporizadores reales: el pomodoro dura lo elegido en **Ajustes → Duración del
  pomodoro** (25 min por defecto) y el descanso dura 5 min. El anillo de color se vacía
  según el tiempo restante y, al llegar a cero, la app pasa sola a la alarma que sigue.
- ✅ **Cámara real** (CameraX) en la pantalla de evidencia: vista previa, tomar foto y
  repetir foto. Si se niega el permiso o no hay cámara, la captura se simula.
- ✅ **Alarma con sonido** (tono de alarma del dispositivo) y vibración opcional en las
  pantallas de alarma; se controlan con los switches de Ajustes.
- ✅ Otros componentes interactivos: mostrar/ocultar contraseña, selección de eventos en
  el timeline, barra inferior, botón Pausar/Play, switches y menú desplegable de Ajustes.
- ❌ No hay llamadas a red, base de datos ni autenticación real.
- ❌ Las fotos solo se muestran en pantalla; no se guardan ni se envían.
- ❌ Las tareas, eventos y datos del perfil son valores de muestra escritos en el código.

El estado (incluidos los Ajustes) vive en memoria y se pierde al cerrar la app — es el
comportamiento esperado de una maqueta de UX.

## Pantallas incluidas

| # | Pantalla | Archivo | Cómo llegar |
|---|----------|---------|-------------|
| 1 | Login | `ui/screens/login/LoginScreen.kt` | Pantalla inicial |
| 2 | Hoy (calendario diario) | `ui/screens/today/TodayScreen.kt` | "Iniciar Sesión" / tab **Hoy** |
| 3 | Tareas Sugeridas | `ui/screens/tasks/TasksScreen.kt` | Tab **Empezar sesión** |
| 5 | Tiempo de Estudio | `ui/screens/session/StudySessionScreen.kt` | "Empezar" en una tarea |
| 4a | Alarma después del pomodoro | `ui/screens/session/AlarmScreen.kt` | "Completar antes de tiempo" |
| 6 | Evidencia | `ui/screens/session/EvidenceScreen.kt` | "Tomar evidencia" |
| 4b | Alarma después de la foto | `ui/screens/session/AlarmScreen.kt` | "Guardar y continuar" |
| 7 | Descanso | `ui/screens/session/BreakScreen.kt` | "Empezar descanso" |
| 4c | Alarma después del descanso | `ui/screens/session/AlarmScreen.kt` | "Saltar Descanso" |
| 8 | Ajustes | `ui/screens/settings/SettingsScreen.kt` | Tab **Ajustes** |

Flujo de una sesión: Tareas → Tiempo de Estudio → Alarma → Evidencia → Alarma → Descanso →
Alarma → (Empezar trabajo vuelve a Tiempo de Estudio · Terminar sesión vuelve a Hoy).
"Cerrar Sesión" en Ajustes regresa al Login.

Las pantallas 5 y 7 pasan solas a la alarma cuando el temporizador llega a cero (también
se puede adelantar con "Completar antes de tiempo" / "Saltar Descanso"). Para probarlo
rápido, elige **1 min** en Ajustes → Duración del pomodoro.

## Estructura del proyecto

```
app/src/main/java/com/studyflow/app/
├── MainActivity.kt
├── navigation/
│   └── StudyFlowNavHost.kt       # NavHost con todas las rutas
├── ui/
│   ├── theme/                    # Color.kt, Type.kt, Shape.kt, Theme.kt
│   ├── components/               # BottomNavBar, DailyTimeline, CountdownRing, PillButton, ...
│   └── screens/
│       ├── login/                # Login
│       ├── today/                # Hoy + datos de ejemplo
│       ├── tasks/                # Tareas Sugeridas
│       ├── session/              # Tiempo de estudio, Alarmas, Evidencia, Descanso + mocks
│       └── settings/             # Ajustes
```

## Cómo correrlo

### Con Android Studio (recomendado)
1. Clona el repositorio: `git clone https://github.com/adavilab/UX_MobileDevelopment.git`
2. Ábrelo con Android Studio (versión reciente, con JDK 17 o superior) y deja que
   sincronice Gradle.
3. Ejecuta la configuración `app` en un emulador o dispositivo con **Android 8.0
   (API 26)** o superior.
4. Inicia sesión con la [cuenta de prueba](#cuenta-de-prueba). Si en el emulador no
   aparece el teclado en pantalla, escribe con el teclado del computador después de
   hacer clic en el campo.
5. La primera vez que abras "Enviar evidencia", acepta el permiso de cámara. En el
   emulador la cámara muestra una escena virtual.

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
- CameraX para la cámara de la pantalla de evidencia
- Gradle con Kotlin DSL y version catalog (`gradle/libs.versions.toml`)
- `minSdk` 26, `compileSdk`/`targetSdk` 36
- Gradle 9.2.1, AGP 8.13.2, Kotlin 2.2.21 (compatibles con JDK 17 a 25)

## Nota académica

Este repositorio se entrega como maqueta de UX. La ausencia de backend, autenticación
real y persistencia de datos es intencional y forma parte del alcance del entregable.
