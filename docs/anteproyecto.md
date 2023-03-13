# Anteproyecto

## OBJETIVOS

Diseñar, desarrollar y desplegar una red social (de nombre 'Quiet') enfocada en reducir la toxicidad en las interacciones y proteger la salud mental de sus usuarios. Para ello será posible filtrar y bloquear contenido problemático o controversial, así como indicar si tu publicación los contiene. Aparte, al igual que las redes sociales generalistas, será posible seguir a otros usuarios, hacer publicaciones, compartirlas y buscar contenido.

## ANÁLISIS DEL SOFTWARE

El proyecto deberá contar con los siguientes requisitos para ser considerado como listo para su uso:
- Funciones generales
    - Registro de cuentas
    - Inicio de sesión
    - Notificaciones sobre eventos e interacciones
- Funciones sociales
    - Publicar contenido (texto y/o imágenes)
    - Valorar contenido de otros usuarios
    - Seguir / dejar de seguir a otros usuarios
    - Ver de forma cronológica el contenido publicado por los usuarios seguidos
    - Compartir publicaciones dentro y fuera de la plataforma
    - Establecer un perfil con nombre de usuario, imagen y descripción
    - Buscador de contenido
- Funciones específicas
    - Avisar si nuestras publicaciones contienen temas sensibles o controversiales
    - Ocultar contenido no deseado
    - Mostrar advertencias en el contenido sensible

Además, se consideran como opcionales:
- Autenticación en dos pasos
- Modo claro y oscuro
- Programación de publicaciones

## DISEÑO DEL SOFTWARE

Teniendo en cuenta que se espera que la aplicación sea capaz de soportar un alto número de usuarios, el software se implementará bajo una arquitectura de microservicios, que frente a una solución monolítica (más adecuada para proyectos pequeños o con bajo volumen de trabajo) aporta ventajas considerables:
- Mayor capacidad de carga: la aplicación será capaz de soportar gran cantidad de peticiones
- Resiliencia: el sistema es capaz de funcionar aun cuando existan fallos en alguna de sus partes, aislando los errores y evitando fallos en cascada que afecten al resto de servicios
- Escalabilidad horizontal: gracias al balance de carga se puede ampliar la capacidad del sistema añadiendo más instancias de los servicios que lo requieran
- Desacoplamiento: cada servicio es independiente salvo en su comunicación con otros, por lo que es posible utilizar múltiples lenguajes y tecnologías dependiendo de lo que sea necesario en cada uno

![Boceto_Arquitectura](https://user-images.githubusercontent.com/25750692/224835847-d82f4857-fcf1-438d-888b-1aa5e0ef30ff.png)

> *Boceto simplificado de la arquitectura*

## ESTIMACIÓN DE COSTES

*[TODO] Estimar el coste que representará la creación del proyecto. Esta estimación será temporal y/o económica si procede (costes de contratación de servicios en la nube, por ejemplo).*
