# Pruebas

### Introducción

Para la detección de errores en la aplicación se hace uso del framework JUnit junto a la integración con Spring, que permite cargar componentes de forma independiente sin tener que poner en marcha toda la aplicación y evitando la carga que ello conlleva.

Las pruebas se realizan en las capas de controlador (peticiones de usuario) y de repositorio (manejo y persistencia de datos) de cada servicio, exceptuando el Gatweway y el Discovery que no cuentan con ellas.

### Capas

#### Controlador

Para la capa de controlador se levanta temporalmente un servidor web en un puerto aleatorio, para evitar conflictos en caso de estar haciendo tests de forma paralela con otro servicio. Además, se crea un componente falso de `WebClient` (encargado de realizar peticiones HTTP) para simular la comunicación con otros servicios.

#### Repositorio

Para la capa de repositorio se cargan únicamente los componentes JPA de Spring y sus dependencias, y se utiliza una base de datos temporal H2 en memoria que se creará al comenzar los tests y será destruida al acabar. Se hacen pruebas insertando, leyendo, actualizando y modificando datos principalmente para comprobar que funcionan correctamente las entidades y sus relaciones, no tanto 

### Automatización de las pruebas

El proyecto se compila de forma automática en un servidor Jenkins cada vez que se actualiza la rama `main` o `dev`. Durante el proceso se realizan los tests unitarios para cada servicio y se recogen los éxitos y fallos, además de quedar registrado el log completo del proceso y otras estadísticas como el tiempo de ejecución en el historial de builds.

Detección de fallos cometidos en las etapadas anteriores para corregirlos. Elaborar planes de pruebas a distintos niveles: pruebas unitarias, pruebas de integración, pruebas con usuarios, ...

Incluye en este apartado la documentación de las pruebas que te planteas realizar.

También la documentación resultante de las pruebas realizadas con la confimación de su correcto funcionamiento y las conclusiones que se puedan extraer de las mismas. 
