# Registro de desarrollo de la API REST

## Día 1 — Creación y configuración inicial

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


## Día 2 — Ajustes del modelo de datos y autenticación

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

## Día 3 — Desarrollo y pruebas de la API

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
