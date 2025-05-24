
# Documento de Decisiones de Diseño

Este documento detalla las decisiones arquitectónicas y tecnológicas tomadas durante el desarrollo de este proyecto, cubriendo tanto el Frontend como el Backend.

## Decisiones Arquitectónicas Generales
El proyecto se construyó siguiendo una arquitectura Cliente/Servidor desacoplada, donde el Frontend (FE) y el Backend (BE) operan en servidores independientes. Si bien para esta entrega inicial ambos residen en el mismo repositorio para simplificar el proceso, en un entorno de producción se optarían por repositorios separados para un mejor control de versiones y despliegue.

Para el Frontend, se eligió desarrollar una Single Page Application (SPA). Esta decisión se justifica por la necesidad inherente de una plataforma de e-commerce (similar a Mercado Libre) de ofrecer una experiencia de usuario fluida, navegación rápida y mínimas recargas de página.

## Proyecto Frontend

### 1. Stack Tecnológico
- **Framework Principal**: Vue 3
- **Herramienta de Build**: Vite
- **Librerías Adicionales**: Vue Router, Axios, TailwindCSS

**Justificación**:
La elección de Vue 3 se basa en su simplicidad, alta productividad, extensa documentación y una gran comunidad. Mi experiencia previa con esta tecnología también facilitó su implementación directa. Vue simplifica la creación de componentes modulares, agrupando HTML, CSS y JavaScript, y sus templates ofrecen una gran legibilidad. Vite se seleccionó por su integración nativa con Vue, proporcionando una experiencia de desarrollo ágil y rápida.

### 2. Estructura del Proyecto
La organización inicial del proyecto sigue una estructura por tipo de archivo (ej., components/, views/, router/).

**Justificación**:
Para la fase inicial del proyecto, esta estructura proporciona simplicidad y claridad. No obstante, si la aplicación escalara significativamente, se reconsideraría una estructura por funcionalidad para mantener la escalabilidad y la cohesión.

### 3. Ruteo y Navegación
- **Herramienta**: Vue Router

**Justificación**:
Vue Router es la solución recomendada y más eficiente para Vue. Permite gestionar tanto el ruteo estático como dinámico, adaptándose a las necesidades de navegación de la aplicación.

### 4. Consumo de APIs
- **Herramienta**: Axios

**Justificación**:
Aunque se comenzó usando la API nativa Workspace, la necesidad de manejar errores de forma centralizada mediante interceptores llevó a la adopción de Axios. Esta librería facilita una gestión de peticiones HTTP robusta y estandarizada.

### 5. Estilos y UI
- **Framework de Estilos**: TailwindCSS

**Justificación**:
Tailwind CSS se eligió por su filosofía "utility-first", que acelera la construcción de interfaces de usuario complejas, como las que se encuentran en un e-commerce. Proporciona todas las utilidades de estilo necesarias desde el inicio y se considera más simple y flexible que otras herramientas como Bootstrap para este contexto, además de ofrecer una excelente integración con Vue.

### 6. Testing
- **Herramientas**: Vitest
- **Cobertura**: 96%

**Justificación**:
La principal razón para elegir Vitest es su perfecta integración con el stack Vue + Vite, lo que garantiza un entorno de pruebas eficiente y alineado con el resto del proyecto.

## Proyecto Backend

### 1. Stack Tecnológico
- **Lenguaje**: Java 21
- **Framework Principal**: Spring Boot
- **Dependencias Clave**: Spring Web, Spring Data (potencial), SpringDoc OpenAPI, Maven.

**Justificación**:
La elección de Java 21 y Spring Boot se basa en años de experiencia con esta tecnología, reconocida como un ecosistema maduro y probado para el desarrollo de soluciones robustas del lado del servidor. Spring Boot, en particular, acelera el desarrollo con su enfoque de "convención sobre configuración".

### 2. Arquitectura del Proyecto
- **Tipo de Arquitectura**: Monolito con Arquitectura en Capas (Resource, Service, Repository).

**Justificación**:
Actualmente, el proyecto se implementa como un monolito (un único JAR ejecutable). Sin embargo, en un contexto real de e-commerce (ej., Mercado Libre), esta solución se concebiría como un módulo de productos dentro de una arquitectura de microservicios, el cual sería dockerizado y orquestado con tecnologías como Kubernetes.

El código se organiza en un patrón de arquitectura en capas (Resource, Service, Repository), una elección simple, probada y ampliamente adoptada en proyectos Spring. Es importante señalar que ProductDetailView se utiliza más como un DTO (Data Transfer Object) que como una vista, lo cual es adecuado dada la simplicidad de los datos (lectura de JSON). En caso de integrar una base de datos, el objeto de vista debería desacoplarse del modelo de dominio.

### 3. Estructura de Carpetas
src/
├── main/
│   └── java/com/martindelallave/ml/
│       ├── common/     # Manejo de excepciones (e.g., GlobalExceptionHandler, ErrorResponse)
│       ├── config/     # Configuraciones específicas de Spring
│       ├── file/       # Componente para la lectura de archivos (e.g., FileReaderService)
│       ├── logging/    # Configuración centralizada de logging y utilidades
│       ├── repository/ # Interfaces y clases de acceso a datos
│       ├── resource/   # Controladores REST (APIs)
│       └── views/      # DTOs/Objetos de respuesta para la API
└── test/
    └── java/com/martindelallave/ml/
        ├── integration/ # Pruebas de integración
        └── unit/        # Pruebas unitarias

**Justificación**:
Esta estructura organiza el código de manera lógica por capa, facilitando la navegación y el mantenimiento, un patrón bien establecido en proyectos Spring.

### 4. Gestión de Datos
- **Fuente de Datos**: Archivo JSON

**Justificación**:
La especificación del desafío indicaba el uso de un archivo JSON. Las clases en la capa repository se encargan de leer el archivo y proporcionar la información de los productos, actuando como la fuente de datos principal.

### 5. Documentación de APIs
- **Herramienta**: Swagger/OpenAPI
- **Acceso**: http://localhost:8080/swagger-ui/index.html#/

**Justificación**:
Swagger/OpenAPI se implementó para documentar la API REST, permitiendo a los consumidores comprender y utilizar los endpoints fácilmente. La API se diseñó siguiendo los principios del Richardson Maturity Model, respetando sus niveles para una API RESTful bien estructurada. Para la cantidad de endpoints actual, no se consideró necesario alcanzar el nivel de HATEOAS.

### 6. Testing
- **Tipos de Pruebas**: Unitarias y de Integración.
- **Herramientas**: JUnit 5, Mockito.
- **Cobertura**: 95%

**Justificación**:
Se implementaron pruebas unitarias para validar componentes individuales y pruebas de integración para verificar el funcionamiento de las capas en conjunto. JUnit 5 es el framework de pruebas estándar en Java, y Mockito se utiliza para la simulación de dependencias, garantizando una cobertura robusta.

### 7. Manejo de Errores
- **Mecanismo**: Controladores globales de errores (@ControllerAdvice).
- **Convención**: Códigos de estado HTTP consistentes y objeto de respuesta ErrorResponse uniforme.
- **Logging**: Centralizado en /logs/application.log con uso de anotaciones para reducir "boilerplate code".

**Justificación**:
La implementación de un manejo de errores global con @ControllerAdvice asegura una respuesta consistente y predecible al cliente, mejorando la experiencia del usuario y la depuración. Los códigos de estado HTTP se utilizan de manera semántica para comunicar el resultado de las operaciones.

### 8. Build y Deploy
- **Herramienta de Build**: Maven

**Justificación**:
Maven es la herramienta de construcción estándar y probada en el ecosistema Java. Se eligió por su simplicidad y la experiencia previa con su uso, facilitando la gestión de dependencias y el ciclo de vida del proyecto.

## Desafíos Encontrados
El principal desafío personal fue "ponerme al día" con las últimas tecnologías de estilos y testing en el Frontend, áreas que no había trabajado en profundidad recientemente. Sin embargo, el uso estratégico de herramientas de Inteligencia Artificial me permitió actualizarme rápidamente y resolver situaciones que, en otro momento, habrían tomado horas, en cuestión de minutos.
