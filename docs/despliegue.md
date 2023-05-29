# Instalación/Despliegue

## Automatización de builds

El proyecto se compila de forma automática cada vez que ocurre un cambio en las ramas `main` o `dev` haciendo uso de Jenkins, una herramienta para automatizar la construcción, los tests y el despliegue de aplicaciones. Se han creado dos proyectos, uno por cada rama, aunque ambos siguen fases de compilación similares:

#### Clonación del repositorio

En esta fase se clona la rama que corresponda desde GitHub en su versión más reciente.

#### Construcción y tests

Se compila individualmente cada servicio y se ejecutan sus tests. Los resultados de esta fase se almacenan en el histórico.

## Cómo ejecutar el proyecto

### Requisitos

- Java 17 o superior
- Base de datos MySQL

### Instrucciones

Estos pasos se deben seguir para cada uno de los servicios.

1. Descargar el archivo .jar correspondiente desde el servidor de Jenkins o la página de releases en GitHub.
2. Mover el archivo al directorio donde se quiera ejecutar, preferiblemente vacío.
3. Crear un archivo con nombre `application.properties`.
4. Definir las siguientes propiedades en el archivo:
```
eureka.client.service-url.defaultZone=http://{discovery_hostname}:{discovery_port}/eureka

spring.datasource.url=jdbc:mysql://{db_hostname}:{db_port}/quiet_dev
spring.datasource.username={db_username}
spring.datasource.password={db_password}

quiet.defaultdesc={default_profile_desc}

// Solo para el media service
quiet.profile_img_dir={profile_img_dir}
spring.servlet.multipart.max-file-size={max_file_size}
spring.servlet.multipart.max-request-size={max_file_size}
```
 5. Sustituir las variables por su valor correspondiente.

- discovery_hostname: IP o nombre de dominio del servicio de discovery.
- discovery_port: Puerto del servicio de discovery.
- db_hostname: IP o nombre de dominio de la base de datos.
- db_port: Puerto de la base de datos.
- db_username: Nombre de usuario de la base de datos.
- db_password: Contraseña de la base de datos.
- default_profile_desc: Descripción por defecto para los perfiles de usuario.
- profile_img_dir: Directorio donde el media service almacenará las imagenes de perfil de los usuarios.
- max_file_size: Tamaño máximo de los archivos que se pueden subir.

6. Ejecutar el archivo .jar (preferiblemente desde consola)

`java -jar SERVICENAME_VERSION.jar`
