# Registro de desarrollo de la API REST

## Día 1 — 29/09/2026 — Creación y configuración inicial

Durante esta primera fase he creado la API REST utilizando Spring Boot y he realizado la configuración inicial del proyecto.

### Trabajo realizado

- Creación del proyecto Spring Boot.
- Configuración de las dependencias necesarias.
- Configuración de la conexión con la base de datos.
- Definición del modelo de datos inicial.
- Creación de las entidades.
- Organización de la estructura del proyecto.
- Creación de la estructura inicial de la API REST.
- Comprobación de la configuración y del funcionamiento inicial.

### Uso de herramientas de IA

He utilizado ChatGPT como herramienta de apoyo durante el desarrollo para consultar dudas técnicas, revisar configuraciones y analizar decisiones relacionadas con la estructura de la API REST.

Las propuestas obtenidas se han revisado y comprobado antes de incorporarlas al proyecto.

### Control de versiones

He realizado el siguiente commit:

`Creación del modelo de datos y estructura inicial de la API REST`


## Día 2 — 01/10/2026 — Ajustes del modelo de datos y autenticación

Durante esta fase he realizado algunos ajustes en el modelo de datos de la API y he comenzado la implementación del sistema de autenticación y seguridad de los usuarios.

### Trabajo realizado

- Renombrado del campo `puesta` a `vacunaAdministrada` en la entidad `Vacunacion`.
- Actualización de los métodos relacionados con este campo.
- Comprobación del funcionamiento de la API después del cambio.
- Implementación del registro de usuarios.
- Cifrado de las contraseñas mediante BCrypt antes de almacenarlas.
- Implementación del inicio de sesión mediante email y contraseña.
- Generación de tokens JWT después de un inicio de sesión correcto.
- Configuración de Spring Security para proteger los endpoints de la API.
- Implementación del filtro encargado de comprobar los tokens JWT recibidos en las peticiones.
- Validación de los datos introducidos durante el registro.
- Adaptación de la gestión de mascotas para que cada usuario pueda acceder únicamente a sus propias mascotas.
- Asociación automática de las nuevas mascotas con el usuario autenticado.
- Comprobación de que un usuario no pueda consultar, modificar ni eliminar mascotas pertenecientes a otro usuario.
- Comprobación del funcionamiento mediante Postman.

### Uso de herramientas de IA

He utilizado ChatGPT como herramienta de apoyo durante el desarrollo para revisar cambios, analizar la implementación de la autenticación y seguridad, y comprobar cómo realizar el aislamiento de los datos de cada usuario.

Las propuestas obtenidas se han revisado y probado durante el desarrollo antes de incorporarlas al proyecto.

### Control de versiones

He realizado los siguientes commits:

`Renombrar puesta a vacunaAdministrada en las vacunaciones`

`Implementar autenticación de usuarios con JWT y BCrypt`

## Día 3 — 03/10/2026 — Desarrollo y pruebas de la API

### Trabajo realizado

He continuado con el desarrollo de la API REST, adaptando los servicios, repositorios y controladores para completar la gestión de mascotas, vacunas y vacunaciones teniendo en cuenta el usuario autenticado.

También he realizado pruebas mediante Postman para comprobar el funcionamiento de la gestión de mascotas, vacunas y vacunaciones.

He realizado pruebas de acceso con distintos usuarios para comprobar que cada usuario pueda acceder únicamente a sus propios datos.

Durante las pruebas de vacunaciones he corregido la configuración de la relación con la vacuna para permitir recibir correctamente su identificador al crear una nueva vacunación.

Por último, he comprobado el funcionamiento de la API después de realizar estos cambios.

### Uso de IA

He utilizado ChatGPT como herramienta de apoyo durante el desarrollo para revisar los cambios realizados en los servicios, repositorios y controladores, analizar posibles problemas y resolver dudas durante las pruebas de la API.

Las propuestas obtenidas se han revisado y probado durante el desarrollo antes de incorporarlas al proyecto.

### Control de versiones

He realizado los siguientes commits:

`Actualizar acceso a mascotas, vacunas y vacunaciones`

`Actualizar configuración de JWT`

`Actualizar documentación del proyecto`

## Día 4 — 24/10/2026 — Reorganización de la estructura del proyecto

Durante esta fase he comenzado a reorganizar una copia del proyecto para separar la aplicación Android de la API REST y facilitar su mantenimiento.

### Trabajo realizado

- Reorganización de la estructura del proyecto.
- Separación de la aplicación Android en la carpeta `Frontend` y de la API REST en la carpeta `Backend`.
- Revisión de la estructura de directorios después de los cambios.
- Comprobación de la compilación de los proyectos después de la reorganización.

### Uso de herramientas de IA

He utilizado ChatGPT y Codex como herramientas de apoyo para revisar la nueva estructura del proyecto, resolver dudas durante la reorganización y comprobar que los cambios no afectaran al funcionamiento de la aplicación Android ni de la API REST.

### Control de versiones

La reorganización se registró mediante el siguiente commit, realizado el 25/10/2026:

`Reorganizar proyecto en Frontend y Backend`

## Día 5 — 25/10/2026 — Implementación de fotografías de mascotas

Durante esta fase he incorporado la funcionalidad para gestionar fotografías reales de las mascotas en la API REST.

### Trabajo realizado

- Adaptación de la entidad `Mascota` para almacenar la referencia a la fotografía.
- Creación del servicio `FotoMascotaService` para gestionar el almacenamiento de imágenes.
- Incorporación de un endpoint para subir fotografías de mascotas.
- Incorporación de un endpoint para consultar las fotografías almacenadas.
- Configuración del directorio de almacenamiento de las imágenes.
- Generación de nombres únicos para los archivos de fotografía.
- Revisión de los cambios realizados en los servicios y controladores.
- Comprobación de la compilación de la API después de los cambios.

### Uso de herramientas de IA

He utilizado ChatGPT y Codex como herramientas de apoyo para implementar la gestión de fotografías de mascotas, revisar los cambios realizados en las entidades, servicios y controladores y resolver dudas durante el desarrollo.

### Control de versiones

He realizado el siguiente commit en la API original:

`Implementar la gestión de fotografías de mascotas`

## Día 6 — 26/10/2026 — Implementación de fotografías de las etiquetas de las vacunas

Durante esta fase he comenzado a adaptar la API REST original para permitir guardar y consultar fotografías de las etiquetas de las vacunas administradas a las mascotas.

### Trabajo realizado

- Ampliación de la entidad `Vacunacion` con el campo `fotoEtiqueta`, destinado a almacenar el nombre del archivo de la fotografía.
- Creación del servicio `FotoVacunacionService` para gestionar el almacenamiento de las imágenes.
- Configuración del directorio de almacenamiento `uploads/vacunaciones`.
- Generación de nombres únicos para los archivos mediante UUID.
- Validación de los archivos recibidos para aceptar los formatos de imagen admitidos.
- Incorporación al servicio `VacunacionService` de un método para actualizar la fotografía de una vacunación.
- Adaptación del controlador `VacunacionController` para incorporar un endpoint que permita subir la fotografía de la etiqueta.
- Incorporación de un endpoint para consultar la fotografía almacenada.
- Revisión de las comprobaciones de acceso a las vacunaciones del usuario autenticado.

### Uso de herramientas de IA

He utilizado ChatGPT y Codex como herramientas de apoyo durante el desarrollo para resolver dudas técnicas, revisar los cambios realizados en las entidades, servicios y controladores, y analizar posibles soluciones para implementar la funcionalidad de almacenamiento y consulta de fotografías de las etiquetas de las vacunas.

### Control de versiones

`Implementar la gestión de fotografías de etiquetas de vacunación`

`Actualizar documentación del proyecto`

## Día 7 — 30/10/2026 — Mejoras en seguridad y gestión de la API REST

Durante esta fase he continuado adaptando la API REST original para mejorar la seguridad, la gestión de las cuentas de usuario y el tratamiento de errores.

### Trabajo realizado

- Implementación de `GlobalExceptionHandler` para centralizar la gestión de excepciones de la API REST.
- Actualización del filtro `JwtAuthenticationFilter` para mejorar la validación de los tokens JWT.
- Revisión de la configuración de seguridad mediante `SecurityConfig`.
- Incorporación de un endpoint para consultar los datos de la cuenta del usuario autenticado.
- Implementación de la funcionalidad para cambiar la contraseña de la cuenta.
- Validación de la contraseña actual y de la nueva contraseña antes de realizar el cambio.
- Adaptación de los controladores y servicios para mejorar la gestión de errores.
- Revisión de los métodos de gestión de usuarios, mascotas, vacunas y vacunaciones.
- Comprobación de que cada usuario pueda acceder únicamente a sus propios datos.
- Realización de pruebas mediante Postman para comprobar el funcionamiento de las nuevas funcionalidades y la gestión de errores.

### Uso de herramientas de IA

He utilizado ChatGPT como herramienta de apoyo durante el desarrollo para resolver dudas técnicas, revisar los cambios realizados en la seguridad, analizar el tratamiento de excepciones y comprobar las modificaciones de los servicios y controladores.

Las propuestas obtenidas se han revisado y probado durante el desarrollo antes de incorporarlas al proyecto.

### Control de versiones

He realizado los siguientes commits:

`Mejorar la gestión de errores y seguridad`

`Implementar gestión de cuenta y cambio de contraseña`

`Mejorar la gestión de usuarios, mascotas, vacunas y vacunaciones`

`Actualizar registro de desarrollo de la API REST`