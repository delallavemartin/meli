# RUN.md

# FRONTEND

## Requisitos previos
1. Asegúrate de tener instalado [Node.js](https://nodejs.org/) (versión 16 o superior).
2. Instala un gestor de paquetes como `npm` (incluido con Node.js).

---

## Pasos para instalar y ejecutar el proyecto

### 1. Clonar el repositorio
Clona el repositorio en tu máquina local:
```bash
git clone https://github.com/delallavemartin/meli.git
```

### 2. Navegar al directorio del proyecto
Accede al directorio del proyecto:
```bash
cd mercadolibre-prototype
```

### 3. Instalar dependencias
Ejecuta el siguiente comando para instalar las dependencias necesarias:
```bash
npm install
```

---

## Scripts disponibles

### 1. Ejecutar el entorno de desarrollo
Para iniciar el servidor de desarrollo:
```bash
npm run dev
```
El servidor estará disponible en `http://localhost:5173`.

### 2. Construir el proyecto para producción
Para generar los archivos optimizados para producción:
```bash
npm run build
```

### 3. Previsualizar la versión de producción
Para previsualizar la aplicación después de construirla:
```bash
npm run preview
```

### 4. Ejecutar pruebas
Para ejecutar las pruebas con Vitest:
```bash
npm run test
```

### 5. Ejecutar pruebas con cobertura
Para ejecutar las pruebas y generar un informe de cobertura:
```bash
npm run test:coverage
```

---

## Notas
- Asegúrate de que el puerto 5173 esté disponible para el servidor de desarrollo.

# BACKEND

## Requisitos previos
1. Asegúrate de tener instalado [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (versión 17 o superior).
2. Instala [Maven](https://maven.apache.org/) para la gestión de dependencias.
3. Verifica que las variables de entorno `JAVA_HOME` y `MAVEN_HOME` estén configuradas correctamente.

---

## Pasos para instalar y ejecutar el proyecto

### 1. Clonar el repositorio
Clona el repositorio en tu máquina local:
```bash
git clone https://github.com/delallavemartin/meli.git
```

### 2. Navegar al directorio del proyecto
Accede al directorio del proyecto:
```bash
cd ml
```

### 3. Instalar dependencias
Ejecuta el siguiente comando para descargar las dependencias necesarias:
```bash
mvn clean install
```

---

## Scripts disponibles

### 1. Ejecutar el servidor
Para iniciar la aplicación Spring Boot:
```bash
mvn spring-boot:run
```
El servidor estará disponible en `http://localhost:8080`.

### 2. Ejecutar pruebas
Para ejecutar las pruebas unitarias:
```bash
mvn test
```

### 3. Generar informe de cobertura
Para generar un informe de cobertura de pruebas (usando plugins como `jacoco`):
```bash
mvn jacoco:report
```
El informe estará disponible en el directorio `target/site/jacoco`.

---

## Notas
- Asegúrate de que el puerto 8080 esté disponible para el servidor.
