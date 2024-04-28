Bienvenido a la Aplicación de la Mini Biblioteca

Esta es una aplicación web basada en una minibiblioteca. Antes de probarla debes tener en cuenta varios factores.

Para poder ejecutar esta app debes realizar varios pasos:

1. Crea una base de datos en MySQL con el nombre que prefieras (te recomiendo llamarla 'minibiblioteca_springboot' para no tener que modificarla después).

Configura el archivo application.properties modificando las siguientes partes (te muestro un ejemplo):

#Configuraciones de la base de datos

spring.datasource.url=jdbc:mysql://localhost:3306/minibiblioteca_springboot --> Introduce tu puerto y el nombre de la bbdd que creaste

spring.datasource.username=root --> Introduce el username para acceder a tu bbdd

spring.datasource.password= --> Introduce la contraseña para acceder a tu bbdd

2. Luego ejecuta la base de datos.

3. ¡Disfruta de la Experiencia!
