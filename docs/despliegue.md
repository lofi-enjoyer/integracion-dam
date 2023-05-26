# Instalación/Despliegue

El proyecto se compila de forma automática cada vez que ocurre un cambio en las ramas `main` o `dev` haciendo uso de Jenkins, una herramienta para automatizar la construcción, los tests y el despliegue de aplicaciones. Se han creado dos proyectos, uno por cada rama, aunque ambos siguen fases de compilación similares.

### Fases

#### Clonación del repositorio

En esta fase se clona la rama que corresponda desde GitHub en su versión más reciente.

#### Construcción y tests

Se compila individualmente cada servicio y se ejecutan sus tests. Los resultados de esta fase se almacenan en el histórico.

#### Exportación a Docker

Una vez los servicios han sido empaquetados en archivos .jar se crea una imagen de Docker de cada uno de ellos.
