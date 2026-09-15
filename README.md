# IoT Alarm - Aplicación Móvil Android

Aplicación móvil para el monitoreo y control de dispositivos IoT mediante conexión WiFi/MQTT.

## Descripción

Proyecto desarrollado para el curso **TI3042 - Aplicaciones Móviles Android** (Unidad 2). La aplicación permite la interconexión entre dispositivos IoT y un teléfono Android para funciones de monitoreo y control en tiempo real.

## Stack Tecnológico

| Componente | Tecnología |
|------------|------------|
| **Plataforma** | Android (API 24+) |
| **IDE** | Android Studio |
| **Lenguaje** | Kotlin + Jetpack Compose |
| **Placa IoT** | ESP32 |
| **Protocolo** | MQTT sobre WiFi |
| **Broker** | Mosquitto / HiveMQ |
| **Autenticación** | Firebase Authentication |
| **Almacenamiento** | SQLite + Firebase |

## Funcionalidades

- Pantalla de autenticación (login)
- Dashboard con datos de sensores en tiempo real
- Panel de control para dispositivos conectados
- Conexión bidireccional vía MQTT
- Almacenamiento local y en la nube

## Hardware

- **ESP32** con WiFi y Bluetooth LE integrados
- Sensor de temperatura y humedad (DHT22)
- Sensor de luz (LDR)
- Relay para control de dispositivos

## Estructura del Proyecto

```
IoT_Alarm/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/com/example/iotalarm/
│           ├── MainActivity.kt
│           └── ui/theme/
├── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
└── settings.gradle.kts
```

## Requisitos

- Android Studio (última versión)
- Java JDK 17
- Dispositivo Android con USB Debugging habilitado
- ESP32 para pruebas de hardware

## Autor

**Michel** - Estudiante TI3042

## Licencia

Proyecto académico - Curso TI3042
