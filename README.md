# Alarma-Proyecto

Aplicacion Android para monitoreo y control de dispositivos IoT, desarrollada para el curso TI3042 - Aplicaciones Moviles para IoT.

## Stack Tecnologico

| Componente | Tecnologia |
|------------|------------|
| IDE | Android Studio |
| Lenguaje app | Kotlin |
| UI | Jetpack Compose + Material3 |
| Navegacion | Navigation Compose |
| Base de datos local | SQLite |
| Placa IoT | Arduino UNO |
| Conectividad | Bluetooth clasico SPP (modulo HC-05 / HC-06) |
| Sensores/actuadores | PIR (movimiento) + modulo de 2 reles (sirena y luz) |

> Nota de alcance: el plan inicial contemplaba ESP32 + WiFi/MQTT + Firebase (ver `../analisis_proyecto_iot.md`). La implementacion se pivoteo a **Arduino UNO + Bluetooth SPP** por disponibilidad de hardware y para cumplir el criterio de conexion inalambrica. El detalle del hardware esta en `docs/plan_implementacion.md` y `docs/diagrama_conexiones.md`.

## Sistema de Autenticacion (v1.1)

La aplicacion implementa un sistema de autenticacion local utilizando **SQLite**.

### Metodos de autenticacion evaluados

| Opcion | Descripcion | Seleccion |
|--------|-------------|-----------|
| **SQLite** | Autenticacion local, sin internet | **SELECCIONADA** |
| Firebase Authentication | Autenticacion en la nube (Google/Email) | Pendiente v1.3 |
| API con MySQL | Backend externo | No seleccionada |

### Justificacion de la decision

- **Sin dependencia de servicios externos**: No requiere conexion a internet ni configuracion de servidores
- **Bajo consumo de recursos**: SQLite esta integrado nativamente en Android
- **Rapidez de implementacion**: Permite validar el flujo de autenticacion sin configurar Firebase ni backend
- **Seguridad basica**: Las contrasenas se almacenan con hash SHA-256, nunca en texto plano

### Estructura de la base de datos

```
Base de datos: alarma_users.db
Tabla: users
  - id: INTEGER PRIMARY KEY AUTOINCREMENT
  - email: TEXT UNIQUE NOT NULL
  - password: TEXT NOT NULL (hash SHA-256)
```

### Flujo de autenticacion

```
App inicia -> LoginScreen
  |-- Credenciales validas -> MainScreen (Dashboard + Control)
  |-- "Registrate" -> RegisterScreen
       |-- Registro exitoso -> LoginScreen
       |-- "Ya tengo cuenta" -> LoginScreen
```

### Seguridad

- **Hash SHA-256**: Las contrasenas se hashean antes de almacenarse
- **Normalizacion de email**: Se almacenan en minusculas y sin espacios
- **Validacion de entrada**: Campos obligatorios, formato email, longitud minima de 6 caracteres
- **Cierre de recursos**: La conexion a la base de datos se cierra despues de cada operacion

> Mejoras de seguridad planificadas (ISO 27400): salt en el hash de contrasenas y cifrado de la base SQLite.

## Conexion IoT por Bluetooth (v1.2)

La app se comunica con el Arduino mediante **Bluetooth clasico (SPP)** usando la UUID estandar `00001101-0000-1000-8000-00805F9B34FB`.

- `BluetoothConnection.kt` es un singleton que expone el estado (`StateFlow`) y las lineas recibidas (`SharedFlow`).
- La conexion intenta 6 variantes (secure, insecure y canal fijo 1 por reflexion) porque algunos modulos HC-05 rechazan la primera conexion.
- La app solo lista **dispositivos ya emparejados**; el emparejamiento se hace en Ajustes > Bluetooth (PIN tipico `1234` o `0000`).
- El `AlarmViewModel` parsea el protocolo y dispara una **notificacion** cuando llega `ALERT:motion:1`.

### Protocolo de comunicacion

```
Arduino -> App
  SENSOR:motion:1     movimiento detectado
  SENSOR:motion:0     sin movimiento
  STATUS:armed:1|0    sistema armado/desarmado
  STATUS:siren:1|0    sirena on/off
  STATUS:light:1|0    luz on/off
  ALERT:motion:1      alerta (sistema armado + movimiento)

App -> Arduino
  CMD:arm:1|0         activar/desactivar alarma
  CMD:siren:1|0       encender/apagar sirena
  CMD:light:1|0       encender/apagar luz
  CMD:status          solicitar estado completo
```

## Estructura del Proyecto

```
IoT_Alarm/
├── app/src/main/java/com/example/alarmaproyecto/
│   ├── MainActivity.kt                    # Activity principal
│   ├── bluetooth/
│   │   └── BluetoothConnection.kt         # Conexion SPP (singleton)
│   ├── data/
│   │   ├── UserDatabaseHelper.kt          # SQLite helper
│   │   └── UserRepository.kt              # Repositorio de datos
│   ├── navigation/
│   │   └── AppNavigation.kt               # Navegacion entre pantallas
│   ├── notifications/
│   │   └── NotificationHelper.kt          # Notificacion de alertas
│   └── ui/
│       ├── screens/
│       │   ├── LoginScreen.kt             # Login
│       │   ├── RegisterScreen.kt          # Registro
│       │   ├── MainScreen.kt              # Scaffold + pestanas
│       │   ├── DashboardScreen.kt         # Estado en tiempo real
│       │   └── ControlScreen.kt           # Controles (armar/sirena/luz)
│       ├── theme/
│       │   ├── Color.kt                   # Colores IoT
│       │   ├── Theme.kt                   # Tema Material3
│       │   └── Type.kt                    # Tipografia
│       └── viewmodel/
│           └── AlarmViewModel.kt          # Estado compartido + protocolo
├── arduino/
│   └── alarma_bt/
│       └── alarma_bt.ino                  # Firmware Arduino UNO
└── docs/
    ├── plan_implementacion.md             # Arquitectura y protocolo
    └── diagrama_conexiones.md             # Cableado del circuito
```

## Versiones

| Version | Fecha | Descripcion |
|---------|-------|-------------|
| v1.0 | Sep 2026 | Template inicial |
| v1.1 | Sep 2026 | Login con SQLite, navegacion, tema IoT |
| v1.2 | Sep 2026 | Conexion Bluetooth SPP, dashboard/control en tiempo real, notificaciones, firmware Arduino |
| v1.3 | - | Firebase Authentication |
| v1.4 | - | Almacenamiento y seguridad (cifrado, ISO 27400) |

## Como Ejecutar

1. Abrir el proyecto en Android Studio
2. Sincronizar Gradle: **File** > **Sync Project with Gradle Files**
3. Emparejar el modulo HC-05/HC-06 en **Ajustes > Bluetooth** del telefono (PIN `1234` o `0000`)
4. Cargar `arduino/alarma_bt/alarma_bt.ino` en el Arduino UNO desde el IDE de Arduino
5. Seleccionar dispositivo o emulador y ejecutar (**Run** / `Shift + F10`)
6. Iniciar sesion y en el **Dashboard** conectar al modulo Bluetooth

## Criterios de Evaluacion (TI3042 Unidad 2)

| Criterio | Estado |
|----------|--------|
| 2.1.1 Herramientas de desarrollo movil | Completado |
| 2.1.2 Conexiones inalambricas | Completado (Bluetooth SPP) |
| 2.1.3 Seguridad ISO 27400 | En progreso (hash SHA-256; falta salt y cifrado) |
| 2.1.4 Interconexion entre dispositivos | En progreso (app <-> Arduino/HC-05; pendiente definir Android <-> Android) |
