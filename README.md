# DEMO PROJECT - SPRING BOOT
Ejemplo de proyecto spring boot con las siguientes tecnologias:

* Liquibase
* Spring Boot 2.3.3.RELEASE
* Spring security with jwt
* JPA with hibernate - spring repository
* Spring doc - open api
* TDD - junit and mock
* Java versión 8
* Lombok

## Sumario
* [Getting Started](#getting-Started)
* [Estructura de los modulos](#estructura-de-los-modulos)
* [Archivos perfiles del proyecto](#archivos-perfiles-del-proyecto)
* [Lanzado pruebas de integracion](#lanzado-pruebas-de-integracion)
* [Swagger - Open Api, documentación del proyecto](#documentación-del-proyecto)

## Getting Started
## 1. Instalacion de lombok.

- Descargar la libreria https://projectlombok.org/download
- Instalar en la ide (eclipse o sts)
![Minion](https://github.com/cparaguay/demo-springboot/blob/master/img/lombok_1.png?raw=true?raw=true)
![Minion](https://github.com/cparaguay/demo-springboot/blob/master/img/lombok_2.png?raw=true?raw=true)
![Minion](https://github.com/cparaguay/demo-springboot/blob/master/img/lombok_3.png?raw=true?raw=true)

## 2. Definiendo perfil
Los perfiles son definiciones de variables para poder
trabajar con spring boot y con un entorno personalizado.
El proyecto trabaja con 3:
 * **dev** Se usa para entornos de desarrollo.
 * **oa** Se usa durante la compilacion para generar documentación openapi.
 * **it**  Se usa durante las fases de pruebas de it.
 * **prod**  Se usa durante las fase de despliegue de producción.
 * **default** (es cuando no asignas un nombre de profile con el que trabajar, es vacio, el proyecto interpreta como el default o prod)

Tenemos 2 formar de asignar un perfil para el entorno de desarrollo de la app

### Forma 01: Variable de entorno
Asignar en la variable de entorno, la clave es:
spring.profiles.active
```bash
#ejemplo
spring.profiles.active =  dev
```

![Minion](https://github.com/cparaguay/demo-springboot/blob/master/img/variables_de_entorno.png?raw=true?raw=true)

### Forma 02: STS (IDE SPRING)
En la configuración del run tambien se puede establecer el perfil.

![Minion](https://github.com/cparaguay/demo-springboot/blob/master/img/sts.png?raw=true)


## 3. Iniciando proyecto modo desarrollo  (DEV)

### Estableciendo configuracion de base de datos
En este caso vamos a usar oracle, para ellos usaremos el archivo **_application-dev**
ya que es el archivo establecer configuraciones para el perfil dev.

```bash
# propiedades para entorno desarrollo
# spring.profiles.active=dev
server:
  port: 8080
  servlet:
    context-path: /demo
  
#Configurando el dataSource
spring:
  datasource:
    name: jdbc/DEMO
    username: USERNAME
    password: PASSWORD
    url: URLDATABASE
    driver-class-name: oracle.jdbc.driver.OracleDriver

```

### Iniciando el proyecto

-  **Modo Consola:** Situarse en la raiz del proyecto demo-proj y ejecutar el siguiente comando.

```bash
mvn spring-boot:run
```

- **Modo ide STS:** Correr la clase principal

![Minion](https://github.com/cparaguay/demo-springboot/blob/master/img/run_main.png?raw=true)


## Estructura de los modulos
```
demo-proj/
├── demo-controller
├── demo-dom
├── demo-dto
├── demo-ear
├── demo-service
├── demo-repository
├── demo-exception
├── demo-interceptor
├── demo-sql
└── demo-util
```

### demo-controller
Modulo de desarrollo de la app.
### demo-dom
Modulo de dominios.
### demo-dto
Modulo de dto del proyecto operaciones rest.
### demo-ear
Modulo del ear final, es el componente final para el despliegue.
### demo-service
Modulo de la capa de servicio
### demo-repository
Modulo de la capa de persistencia.
### demo-exception
Modulo de las clases de excepciones personalizadas.
### demo-interceptor
Modulo de las clases de interceptadoras personalizadas.
### demo-sql
Modulo de proyecto que contiene la configuración liquibase. Se puede ejecutar solo
este modulo, tambien puede ejecutarse integrado al modulo demo-controller.

### demo-util
Modulo del utilitarios para el proyecto.

## Archivos Perfiles del proyecto

Los archivos de perfiles se definen por la sintaxis que viene despues de la palabra "application-", un ejemplo seria: 
- **application-_it_** para integracion. 
- **application-_dev_** para desarrollo.
- **application-_prod_** para para producción.
- **application-_oa_** para para generar documentación openapi.
- **application** por default.

### Perfil IT: application-it
Este perfil usa las configuraciones para las pruebas de integración.
Como base de datos, usa H2. Una base de datos en memoria para realizar las pruebas con el modelo de persistencia.
Ademas que se ejecuta automaticamente durante el ciclo de vida de maven.

![profile-it](https://github.com/cparaguay/demo-springboot/blob/master/img/properties-it.png?raw=true)
### Perfil DEV: application-dev
Este perfil usa las configuraciones para la fase de desarrollo.

![profile-it](https://github.com/cparaguay/demo-springboot/blob/master/img/properties-dev.png?raw=true)

### Perfil PROD: application-prod
Este perfil usa las configuraciones para la fase del despliegue de producción.

![profile-it](https://github.com/cparaguay/demo-springboot/blob/master/img/properties-prod.png?raw=true)

----

## Lanzado pruebas de integracion
### 1. Modo ide
Las pruebas de integración, normalmente se encuentran en el paquete com.example.demo.it
#### Lanzando una prueba de integracion

![profile-it](https://github.com/cparaguay/demo-springboot/blob/master/img/prueba_it_example.png?raw=true)

#### Resultado de una prueba de integración

Por cada clase de prueba de integración se levanta un contexto web y de base de datos.

![profile-it](https://github.com/cparaguay/demo-springboot/blob/master/img/resultado_it.png?raw=true)

## 2. Modo compilación 
Se realiza durante la compilacion. Es transparente para el desarrollador.

- Se activa de manera automatica el perfil *it* y toma el archivo *application_it* para usar la configuración necesaria.
- Usa base de datos en memoria, H2, para realizar las pruebas. 

## Documentación del proyecto
Para acceder de manera facil, basta con elegir el perfil 
- **application-_oa_**
```bash
#ejemplo
spring.profiles.active =  oa
```

El acceso para la documentación de los servicios web es:
- [/demo/swagger-ui.html](../demo/swagger-ui.html)

