# 🐾 Mascotas y Vacunas

Aplicación Android para gestionar mascotas y llevar un seguimiento de sus vacunas y vacunaciones. Permite a los usuarios registrar su cuenta, iniciar sesión y administrar su propia información de forma segura.

El proyecto está dividido en dos partes principales:

- **Frontend:** aplicación Android desarrollada con Kotlin y Jetpack Compose.
- **Backend:** API REST desarrollada con Spring Boot, encargada de gestionar la comunicación entre la aplicación y la base de datos.

## ✨ Funcionalidades principales

### 👤 Usuarios y autenticación

- Registro de nuevos usuarios.
- Inicio de sesión mediante autenticación JWT.
- Protección de contraseñas mediante BCrypt.
- Consulta y modificación de los datos de la cuenta.
- Cambio de contraseña con verificación de la contraseña actual.
- Acceso exclusivo a los datos de cada usuario.

### 🐶 Gestión de mascotas

- Registro, consulta, modificación y eliminación de mascotas.
- Asociación de cada mascota a su usuario.
- Añadir, visualizar, sustituir y eliminar fotografías de las mascotas.

### 💉 Gestión de vacunas

- Consulta y gestión de vacunas.

### 📋 Gestión de vacunaciones

- Registro, consulta, modificación y eliminación de vacunaciones.
- Asociación de las vacunaciones a las mascotas.
- Añadir, visualizar, sustituir y eliminar fotografías de las etiquetas de vacunación.

## 🛠️ Tecnologías utilizadas

### Frontend – Android

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose y Material 3
- **IDE:** Android Studio
- **Arquitectura:** MVVM
- **Comunicación con la API:** Retrofit y OkHttp
- **Inyección de dependencias:** Hilt
- **Almacenamiento de datos:** DataStore
- **Construcción:** Gradle

### Backend – API REST

- **Lenguaje:** Java 17
- **Framework:** Spring Boot
- **Seguridad:** Spring Security y JWT
- **Persistencia:** Spring Data JPA e Hibernate
- **Base de datos:** MariaDB
- **Protección de contraseñas:** BCrypt

## 📁 Estructura del proyecto

```text
Mascotas-Vacunas/
│
├── Frontend/                 # Aplicación Android
│   └── mascotasyvacunas/
│       ├── authentication/   # Autenticación y cuenta
│       ├── core/             # Inyección de dependencias
│       ├── home/             # Pantalla principal
│       ├── mascotas/         # Gestión de mascotas
│       ├── navigation/       # Navegación
│       ├── ui/               # Tema y estilos
│       ├── utils/            # Utilidades
│       ├── vacunaciones/     # Gestión de vacunaciones
│       └── vacunas/          # Gestión de vacunas
│
├── Backend/                  # API REST
│   └── mascotasvacunas/
│       ├── config/           # Configuración
│       ├── controller/       # Controladores REST
│       ├── dto/              # Objetos de transferencia
│       ├── entity/           # Entidades
│       ├── exception/        # Excepciones
│       ├── repository/       # Acceso a datos
│       ├── security/         # Seguridad
│       └── service/          # Lógica de negocio
│
└── README.md
```

## 🚧 Estado del proyecto

Las principales funcionalidades de la aplicación están implementadas. Actualmente se están realizando los últimos ajustes, entre ellos la incorporación de animaciones, la adaptación de la interfaz a pantallas de menor tamaño y las comprobaciones finales antes de su despliegue.

## 👤 Autor

**Teresa Sánchez Moreno**

[GitHub](https://github.com/teresasanchez96)

