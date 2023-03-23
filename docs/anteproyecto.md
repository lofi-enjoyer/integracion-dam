# Anteproyecto

## OBJETIVOS

Diseñar, desarrollar y preparar para su despliegue una red social (de nombre 'Quiet') enfocada en reducir la toxicidad en las interacciones y proteger la salud mental de sus usuarios. Como usuario de Twitter que usa esta plataforma en gran parte para leer opiniones y debatir sobre temas, he visto una tendencia a avisar al comenzar una publicación de que puede contener temas sensibles (acoso sexual, violencia, suicidio...) que a algunos usuarios les gustaría evitar, pero no existe ninguna forma "oficial" de hacerlo. Por ello me gustaría hacer posible filtrar y bloquear contenido problemático o controversial, así como indicar si tu publicación los contiene. Aparte, al igual que las redes sociales generalistas, será posible seguir a otros usuarios, hacer publicaciones, compartirlas y buscar contenido.

## ANÁLISIS DEL SOFTWARE

El proyecto deberá contar con los siguientes requisitos (casos de uso) para ser considerado como listo para su uso:
- Funciones generales
    - Inicio de sesión / registrarse
- Funciones sociales
    - Publicar texto
    - Valorar contenido de otros usuarios
    - Seguir / dejar de seguir a otros usuarios
    - Ver el contenido publicado por otros usuarios
    - Establecer un perfil con nombre de usuario único
- Funciones específicas
    - Ocultar contenido no deseado
    - Mostrar advertencias en el contenido sensible

Además, se consideran como opcionales:
- Autenticación en dos pasos
- Publicar imágenes
- Notificaciones sobre eventos e interacciones
- Compartir publicaciones dentro y fuera de la plataforma

![Casos_de_uso](https://user-images.githubusercontent.com/25750692/227209464-410c53e3-f42e-44f5-b0c2-0469542f4274.png)

> Diagrama simple de casos de uso

## DISEÑO DEL SOFTWARE

### Arquitectura

Teniendo en cuenta que se espera que la aplicación sea capaz de soportar un alto número de usuarios, el software se implementará bajo una arquitectura de microservicios, que frente a una solución monolítica (más adecuada para proyectos pequeños o con bajo volumen de trabajo) aporta ventajas considerables:
- Mayor capacidad de carga: la aplicación será capaz de soportar gran cantidad de peticiones
- Resiliencia: el sistema es capaz de funcionar aun cuando existan fallos en alguna de sus partes, aislando los errores y evitando fallos en cascada que afecten al resto de servicios
- Escalabilidad horizontal: gracias al balance de carga se puede ampliar la capacidad del sistema añadiendo más instancias de los servicios que lo requieran
- Desacoplamiento: cada servicio es independiente salvo en su comunicación con otros, por lo que es posible utilizar múltiples lenguajes y tecnologías dependiendo de lo que sea necesario en cada uno

![Boceto_Arquitectura](https://user-images.githubusercontent.com/25750692/224835847-d82f4857-fcf1-438d-888b-1aa5e0ef30ff.png)

> *Boceto simplificado de la arquitectura*

### Componentes

La aplicación estará compuesta por los siguientes componentes:
- Servicios
    - Gateway: enruta las peticiones a su correspondiente servicio, y autentica los endpoints protegidos con ayuda del servicio de autenticación.
    - Autenticación: crea y valida tokens.
    - Usuarios: gestiona la creación y edición de cuentas y perfiles de usuario.
    - Posts: se encarga de las operaciones relacionadas con posts (crear, editar y eliminar).
    - Front: sirve la interfaz web al usuario.
    - Contenido: genera el feed (publicaciones de perfiles seguidos) y gestiona las interacciones entre usuarios. *(opcional, en un principio irá integrado en el servicio de posts y se dividirá si hay margen de tiempo antes de la entrega)*
    - Notificaciones: envía notificaciones y comunicaciones a usuarios mediante e-mail. *(opcional)*
    - Media: ofrece y maneja recursos multimedia. *(opcional)*
- Gestión de datos
    - Base de datos: almacena la información de la aplicación
    - Almacenamiento de datos en memoria: utilizado para la caché de la aplicación *(opcional)*

### Tecnologías

En el proyecto utilizaré mayormente tecnologías con las que tengo experiencia previa para centrarme en el proyecto y expandir mis conocimientos sobre ellas.

Para el servidor utilizaré Java con el framework Spring en su versión 5, que proporciona herramientas para manejar peticiones HTTP, seguridad, gestión de bases de datos, cachés y otras funcionalidades ya integradas que agilizan la construcción del proyecto. También HTML, JavaScript y CSS para la interfaz web y MySQL para gestionar bases de datos.

El despliegue se planificará suponiendo que se utilizan servicios de Microsoft Azure, que estoy aprendiendo a usar en la FCT.

## ESTIMACIÓN DE COSTES

La implementación se llevará a cabo en dos meses, con una duración estimada de entre sesenta y ochenta horas de desarrollo.

Los costes económicos de la aplicación serán únicamente por el alojamiento. Para ello se va a utilizar como proveedor Amazon Web Services, que oferta los servicios necesarios para el despliegue. Además, ofrece una calculadora de presupuestos en la que obtener una estimación de los gastos de la aplicación.

Suponiendo un alojamiento en la región de España, con una base de datos MySQL de 2TB, una caché de Redis con 5 instancias de 26GiB y un tráfico de 5TB entrada/10TB salida, el coste total ascendería a 1189,92 dólares mensuales.

![Captura de pantalla de 2023-03-17 10-32-39](https://user-images.githubusercontent.com/25750692/225880542-3c009fce-9683-409f-b096-4856315967ed.png)
