## Prueba- Ejercicio creacion api rest
## Qué usé?
* Java 17
* Docker
* Maven
* Spring boot 3.1.1
* Mapstruct 1.5.5
* Lombok
* Mysql 8.0.28

## Configuración inicial del proyecto
## 1. Configurar Base de datos en entorno docker.
En la raiz del proyecto se encuentra un archivo docker-compose.yml que levanta una base de datos en mysql en el puerto 3306.

```
 ./docker-compose.yml
```
Estando en la raiz del proyecto se procedera a abrir terminal y ejecutar el comando

```
docker compose up
```
si solicita algun permiso se le debera anteponer la palabra sudo para darle permisos de administrador.
```
sudo docker compose up
```
Esperar a que termine de levantar el ambiente docker
## 2. Ejecutar proyecto.
Despues de debera ejecutar el proyecto esta api esta corriendo en el puerto 8085.
## 3. Api disponible

* Data Rest: http://localhost:8085

## 4. Postman
En la raiz del proyecto se encuentra el archivo.
```
./ApiBancariaDevsu.postman_collection.json
```
Este json se debera importar en postman para poder probar los siguientes endpoints.

### SWAGGER
```
http://localhost:8085/swagger-ui/index.html#/
```
## Descripción

El proyecto "devsuChallenge API REST" es una API desarrollada como parte de una prueba técnica para
Devsu. Esta API permite realizar operaciones relacionadas con movimientos bancarios, como la
creación, lectura, actualización y eliminación de clientes y cuentas. También incluye la generación
de movimientos de depósito y retiro para cada cuenta, con limitaciones de monto para retiros
diarios. Además, ofrece la posibilidad de generar informes de movimientos por cliente dentro de un
rango de fechas.

## Endpoints

La API cuenta con varios endpoints para acceder a estas funcionalidades. Algunos de los principales
endpoints son:

/cuentas: Permite gestionar las cuentas, como crear una nueva cuenta, obtener información de una
cuenta específica, actualizar los detalles de una cuenta o eliminar una cuenta existente.

/clientes: Permite realizar operaciones relacionadas con los clientes, como crear un nuevo cliente,
obtener información de un cliente específico, actualizar los detalles de un cliente o eliminar un
cliente existente.

/movimientos: Permite gestionar los movimientos, como registrar un nuevo movimiento de depósito o
retiro en una cuenta específica.

/reportes: Permite generar informes de movimientos por cliente dentro de un rango de fechas
específico.

En el archivo "Devsu.postman_collection.json" se encuentra una coleccion para la validación de los
endpoints de la API. Este archivo debe ser importado desde Postman para su utilización.

## Dependencias y tecnologías principales

El proyecto utiliza Java 17 como lenguaje de programación y Maven como herramienta de gestión de
dependencias. Está desarrollado utilizando el framework Spring Boot 3.1.0, que proporciona
características y funcionalidades para facilitar el desarrollo de aplicaciones web. Se utiliza JPA
para la capa de persistencia, WEB para la capa de controladores y TEST para las pruebas unitarias.
La aplicación se conecta a una base de datos Postgresql en tiempo de ejecución y Además, se utiliza
Lombok para reducir la cantidad de código.

Además, el proyecto se encuentra configurado para ser ejecutado en un entorno Docker. Se proporciona
un archivo "docker-compose.yml" que permite la creación de una imagen de Docker con todas las
dependencias necesarias para ejecutar la aplicación, para eso es necesaria la ejecución del
comando "docker compose up" desde la raiz del proyecto y verificar tambien la configuracion correcta
del puerto

