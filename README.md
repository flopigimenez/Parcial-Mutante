# Proyecto: Parcial Programación Mutante
Esta API determina si un ADN es mutante o no.


## Contenidos
- [Introducción](#introducción)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración de la base de datos](#configuración-de-la-base-de-datos)
- [Despliegue en Render](#despliegue-en-render)
- [Probar la API localmente](#probar-la-api-localmente)
- [Pruebas con Postman](#pruebas-con-postman)
- [Endpoints de la API](#endpoints-de-la-api)


## Introducción
Esta API está desarrollada con Spring Boot. Permite determinar si un ADN es mutante o no, y devuelve las estadísticas de las verificaciones de ADN.


## Requisitos
- Java 17
- Gradle
- Spring Boot
- Base de datos H2
- Git
- Postman


## Instalación
1. Clona el repositorio:

    ```bash
    git clone https://github.com/flopigimenez/Parcial-Mutante.git
    cd Parcial-Mutante
    ```

2.Compilar y ejecutar el proyecto:

Desde la terminal, navega hasta la carpeta del proyecto y ejecuta el siguiente comando:
./gradlew bootRun


## Configuración de la base de datos
El proyecto utiliza una base de datos **H2**. Está disponible en `http://localhost:8080/h2-console`.

- **URL JDBC**: jdbc:h2:file:./mutant
- **Usuario**: sa
- **Contraseña**: (dejar vacío)


## Despliegue en Render
La API está disponible en el siguiente enlace:

- **URL de la API**: https://parcial-mutante.onrender.com


## Probar la API localmente:
La API está disponible en http://localhost:8080.
Puedes probar los endpoints usando Postman.


## Pruebas con Postman:
1. Importar la colección de Postman que puedes encontrar en el archivo Postman_Collection.json en la raíz del proyecto.
2. Ejecutar los tests en Postman.


## Endpoints de la API 
1. Enviar una secuencia de ADN para determinar si es mutante.

- **URL**: `/human/save`
- **Método**: `POST`
- **Cuerpo de la Solicitud**:

    ```json
    {
        "name": "Marta",
        "dna": ["AAAACG", "ACATTG", "CCCTTG", "ACTCGG", "TTATCG", "TTAGGC"]
    }
    ```

- **Respuesta**:

    - **200 OK**: Si la secuencia de ADN pertenece a un mutante.

        ```json
        "Es mutante"
        ```

    - **403 Forbidden**: Si la secuencia de ADN no pertenece a un mutante.

        ```json
        "No es mutante"
        ```

    - **400 Bad Request**: Si la secuencia de ADN es inválida.

        ```json
        "Error al guardar la persona: Solo puede contener las letras A,T,C,G"
        ```

2. Obtener estadísticas de mutantes y humanos

- **URL**: `/human/stats`
- **Método**: `GET`
- **Respuestas**:

    - **200 OK**: Devuelve las estadísticas de mutantes vs. humanos.

        ```json
        {
            "countMutantDna": 40,
            "countHumanDna": 100,
            "ratio": 0.4
        }
        ```
