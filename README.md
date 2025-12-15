# Serena - Aplicación de Bienestar Emocional

## Descripción General

Serena es una aplicación móvil desarrollada para el registro y seguimiento de estados emocionales, con el objetivo de fomentar el bienestar mental de los usuarios. La aplicación permite gestionar perfiles, registrar emociones y sincronizar la información con un backend basado en microservicios.

## Integrantes del Equipo

* Diego Arias
* Solgrey Medina

## Funcionalidades Principales

La aplicación Serena ofrece las siguientes capacidades funcionales:

1. **Gestión de Usuarios**
   Permite la creación, consulta, actualización y eliminación de perfiles de usuario.

2. **Registro Emocional**
   Facilita el registro detallado de los estados emocionales del usuario, asociados a una fecha, descripción y emoción seleccionada.

3. **Persistencia y Sincronización**
   Implementa persistencia local de datos críticos mediante almacenamiento local y sincroniza automáticamente los cambios con el backend cuando existe conectividad, permitiendo el funcionamiento en modo offline.

4. **Recursos Nativos Integrados**

   * **Notificaciones:** Uso de notificaciones del sistema para recordatorios o alertas relacionadas con el registro emocional.
   * **Integración de Salud:** Acceso funcional a recursos como Google Fit o Health Connect (según la plataforma), con el fin de enriquecer el contexto del registro emocional.

5. **Arquitectura y Diseño**

   * La aplicación está desarrollada bajo el patrón arquitectónico **MVVM (Model-View-ViewModel)**, asegurando modularidad, mantenibilidad y escalabilidad.
   * El código presenta una estructura de carpetas claramente definida, con separación de la lógica de negocio, validaciones y capa de presentación.
   * Se incluyen demostraciones de programación orientada a objetos, tales como uso de clases, herencia y polimorfismo.

## Backend y Microservicios Consumidos (Serena API)

La aplicación móvil consume los siguientes microservicios para su operación:

### Microservicios y URLs Base

* **MCSV-USER (Gestión de Usuarios y Autenticación)**
  URL Base: [http://localhost:8091](http://localhost:8091)
  Swagger: [http://localhost:8091/swagger-ui/index.html](http://localhost:8091/swagger-ui/index.html)

* **MCSV-EMOTION (Catálogo de Emociones)**
  URL Base: [http://localhost:8092](http://localhost:8092)
  Swagger: [http://localhost:8092/swagger-ui/index.html](http://localhost:8092/swagger-ui/index.html)

* **MCSV-EMOTIONAL-REGISTER (Registros Emocionales)**
  URL Base: [http://localhost:8093](http://localhost:8093)
  Swagger: [http://localhost:8093/swagger-ui/index.html](http://localhost:8093/swagger-ui/index.html)

No se identificaron endpoints externos (de terceros) en la documentación del proyecto.

## Endpoints Principales

### Gestión de Usuarios (MCSV-USER)

* **GET** `/api/v1/users`
  Lista todos los usuarios.

* **POST** `/api/v1/users`
  Crea un nuevo usuario.

* **GET** `/api/v1/users/{id}`
  Obtiene un usuario por su identificador.

* **GET** `/api/v1/users/email/{email}`
  Busca un usuario por correo electrónico.

* **PUT** `/api/v1/users/{id}`
  Actualiza la información de un usuario.

* **DELETE** `/api/v1/users/{id}`
  Elimina un usuario.

### Sesión Activa (MCSV-USER)

* **GET** `/api/v1/user-active-sessions`
  Lista las sesiones activas.

* **GET** `/api/v1/user-active-sessions/{id}`
  Obtiene una sesión activa por ID.

* **POST** `/api/v1/user-active-sessions`
  Crea una sesión activa.

* **PUT** `/api/v1/user-active-sessions/{id}`
  Actualiza una sesión activa.

* **DELETE** `/api/v1/user-active-sessions/{id}`
  Elimina una sesión activa.

### Autenticación con Token (MCSV-USER)

* **POST** `/api/v1/auth/register`
  Registro de usuario con generación de token.

* **POST** `/api/v1/auth/login`
  Inicio de sesión con credenciales y obtención de token.

### Gestión de Emociones (MCSV-EMOTION)

* **GET** `/api/v1/emotions`
  Lista todas las emociones disponibles.

* **GET** `/api/v1/emotions/{id}`
  Obtiene una emoción por ID.

* **POST** `/api/v1/emotions`
  Crea una nueva emoción.

* **PUT** `/api/v1/emotions/{id}`
  Actualiza una emoción existente.

* **DELETE** `/api/v1/emotions/{id}`
  Elimina una emoción.

### Registro Emocional del Usuario (MCSV-EMOTIONAL-REGISTER)

* **GET** `/api/v1/emotional-registers`
  Lista todos los registros emocionales.

* **GET** `/api/v1/emotional-registers/{id}`
  Obtiene un registro emocional por ID.

* **POST** `/api/v1/emotional-registers`
  Crea un nuevo registro emocional.

* **PUT** `/api/v1/emotional-registers/{id}`
  Actualiza un registro emocional existente.

* **DELETE** `/api/v1/emotional-registers/{id}`
  Elimina un registro emocional.

## Instrucciones para Ejecutar el Proyecto

1. **Ejecutar el Backend**
   Asegúrese de que el proyecto de microservicios (Serena API) se encuentre en ejecución y accesible en las URLs base indicadas (localhost:8091 a localhost:8093).

2. **Clonar el Repositorio**

   ```bash
   git clone https://github.com/SolgreyDuocUC/Serena.sdk
   ```

3. **Abrir en Android Studio**
   Abrir la carpeta del proyecto clonado en Android Studio.

4. **Sincronizar Gradle**
   Esperar a que Gradle sincronice correctamente todas las dependencias. El proyecto incluye pruebas unitarias utilizando Kotest, JUnit 5 y MockK.

5. **Ejecutar la Aplicación**
   Seleccionar un emulador o dispositivo físico y presionar el botón Run.

## Código Fuente

El código fuente de la aplicación móvil se encuentra disponible en el repositorio principal del proyecto:

* Repositorio móvil: https://github.com/SolgreyDuocUC/Serena.sdk

## Evidencia de Colaboración

El trabajo colaborativo se evidencia mediante el uso de control de versiones con Git y la utilización de ramas y commits individuales en GitHub. Se recomienda revisar el historial de commits asociados a los siguientes integrantes:

* Diego Arias
* Solgrey Medina


