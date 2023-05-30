# Pruebas

## Errores durante el desarrollo

#### Al ejecutar los tests es necesario un servidor MySQL disponible
Para solventar este problema se utiliza una base de datos temporal en memoria H2, por lo que los tests pueden ejecutarse en cualquier máquina y no alteran los datos de producción.

#### Problemas de carga de entidades
En algunas ocasiones cargar una entidad producía errores y alto uso de memoria, ocasionado por hacer *fetch* de forma incorrecta a las entidades relacionadas. Para arreglar este problema se utilizan consultas propias en JPQL, en vez de delegar en Spring JPA para que las genere automáticamente, y se especifica en cada entidad el método de carga para cada una de sus relaciones.

## Tests unitarios

Para la detección de errores en la aplicación se hace uso del framework JUnit junto a la integración con Spring, que permite cargar componentes de forma independiente sin tener que poner en marcha toda la aplicación y evitando la carga que ello conlleva.

Las pruebas se realizan en la capa de repositorio (manejo y persistencia de datos) de cada servicio, exceptuando el Gatweway y el Discovery que no cuentan con ellas.

### Capa de repositorio

Para la capa de repositorio se cargan únicamente los componentes JPA de Spring y sus dependencias, y se utiliza una base de datos temporal H2 en memoria que se creará al comenzar los tests y será destruida al acabar. Se hacen pruebas insertando, leyendo, actualizando y modificando datos principalmente para comprobar que funcionan correctamente las entidades y sus relaciones, no tanto 

## Automatización de las pruebas

El proyecto se compila de forma automática en un servidor Jenkins cada vez que se actualiza la rama `main` o `dev`. Durante el proceso se realizan los tests unitarios para cada servicio y se recogen los éxitos y fallos, además de quedar registrado el log completo del proceso y otras estadísticas como el tiempo de ejecución en el historial de builds.
