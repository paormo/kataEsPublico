# Prueba Pablo Ortega
    Repositoro para la prueba de selección de EsPublico.

## Requisitos
- Tener Docker instalado en la máquina que va a ejecutar el programa.
- Tener Java 21 instalado.
- Poseer una conexión activa a internet.

## Instrucciones de uso
### 1 Ejecutar el comando "docker-compose up db" desde la raíz del proyecto.
   - Esto descarga la imagen de Postgres desde DockerHub y crea un contenedor con la base de datos y la expone en el puerto 5432. 
### 2 Ejecutar el comando "mvn clean package" desde la raíz del proyecto
   - Esto descarga las dependencias del proyecto, compila el proyecto e intenta levantar el servicio a modo de test.
### 3 Ejecutar la clase java principal "Application"
   - Esto levanta a aplicación de Spring Boot y la expone en el puerto 8888 de nuestro localhost.
### 4 Acceder a la ruta 'localhost:8888/orders-summary' desde un navegador o un programa de peticiones http como Postman
   - Esto lanza el proceso de importación de pedidos y genera el CSV en la raíz del proyecto.
