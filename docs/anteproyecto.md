# Anteproyecto

> NOTA: Incluir diagramas donde proceda (diagramas de clases, casos de uso, entidad relación, ...).

## OBJETIVOS

Diseñar, desarrollar y desplegar una red social enfocada en reducir la toxicidad en las interacciones y proteger la salud mental de sus usuarios. Para ello será posible filtrar y bloquear contenido problemático o controversial, así como indicar si tu publicación los contiene. Aparte, al igual que las redes sociales generalistas, será posible seguir a otros usuarios, hacer publicaciones, compartirlas y buscar contenido.

## PREANALISIS DE LO EXISTENTE (Opcional)

*[TODO] Si procede, se informará brevemente sobre el funcionamiento del sistema actual. El que vamos a reemplazar o a mejorar. Este sistema no tiene por qué estar necesariamente automatizado pudiendo realizarse actualmente de forma manual por personas.*

## ANÁLISIS DEL SOFTWARE

El proyecto deberá contar con los siguientes requisitos para ser considerado como listo para su uso:
- Funciones sociales
    - Publicar contenido (texto y/o imágenes)
    - Valorar contenido de otros usuarios
    - Seguir / dejar de seguir a otros usuarios
    - Ver de forma cronológica el contenido publicado por los usuarios seguidos
    - Compartir publicaciones dentro y fuera de la plataforma
    - Establecer un perfil con nombre de usuario, imagen y descripción
    - Buscador de contenido
- Funciones generales
    - Registro de cuentas
    - Inicio de sesión
    - Notificaciones sobre eventos e interacciones

Además, se consideran como opcionales:
- Autenticación en dos pasos
- Modo claro y oscuro
- Programación de publicaciones

## DISEÑO DEL SOFTWARE

*[TODO] Propuesta de posibles opciones de implementación del software que hay que construir, determinar cómo se va a llevar a cabo la implementación.*

>  *Incluir los diagramas necesarios.*

Teniendo en cuenta que se espera que la aplicación sea capaz de soportar un alto número de usuarios, el software se implementará bajo una arquitectura de microservicios, que frente a una solución monolítica (más adecuada para proyectos pequeños o con bajo volumen de trabajo) aporta ventajas considerables:
- Mayor capacidad de carga: la aplicación será capaz de soportar gran cantidad de peticiones
- Resiliencia: el sistema es capaz de funcionar aun cuando existan fallos en alguna de sus partes, aislando los errores y evitando fallos en cascada que afecten al resto de servicios
- Escalabilidad horizontal: gracias al balance de carga se puede ampliar la capacidad del sistema añadiendo más instancias de los servicios que lo requieran
- Desacoplamiento: cada servicio es independiente salvo en su comunicación con otros, por lo que es posible utilizar múltiples lenguajes y tecnologías dependiendo de lo que sea necesario en cada uno

![Boceto_Arquitectura](https://user-images.githubusercontent.com/25750692/224827206-aba605f1-04a3-4c5b-baa4-1e4c71e0014e.png)

> *Boceto simplificado de la arquitectura*

## ESTIMACIÓN DE COSTES

*[TODO] Estimar el coste que representará la creación del proyecto. Esta estimación será temporal y/o económica si procede (costes de contratación de servicios en la nube, por ejemplo).*
