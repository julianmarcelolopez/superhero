# Superhero

[![Repo](https://img.shields.io/badge/Repo-GitHub-blue)](https://github.com/julianmarcelolopez/superhero)
[![Linkedin](https://img.shields.io/badge/Linkedin-Julian-blue)](https://www.linkedin.com/in/julian-marcelo-lopez/)

## SuperHero API
Aplicación construida con [jdk-11](https://www.oracle.com/ar/java/technologies/javase-jdk11-downloads.html)
* [About](#about)
* [Proposito](#proposito)
* [Features](#features)
* [Base de Datos](#base-de-datos)
* [@TrackTime](#tracktime)
* [Excepciones](#excepciones)
* [Tests](#tests)
  * [Unitarios](#test-unitarios)
  * [De Integracion](#test-de-integracion)
* [Docker](#docker)
  * [Dockerfile](#dockerfile)
  * [Dockerizando](#dockerizando)
* [Cache](#cache)
* [Documentacion](#documentacion)
* [JAVA 11 Features](#java-11-features)
  * [New Features List](#new-features-list)
  * [JAVA 11 Features en el Proyecto](#java-11-features-en-el-proyecto)
  * [JAVA 8 Features en el Proyecto](#java-8-features-en-el-proyecto)
* [Construir el Proyecto](#construir-el-proyecto)
* [Running Spring Boot](#running-spring-boot)


### About

> API de mantenimiento de superheroes
* Consulta de todos los Superhéroes.
* Consulta de Superhéroe por id.
* Consulta de Superhéroes por parametro nombre.
* Modificar Superhéroe.
* Eliminar Superhéroe.


## Proposito

Surge como iniciativa de W2M, de la necesidad brindar a su clientes sistemas y servicios de calidad capaces de realizar búsquedas de destinos turisticos con alta performance.


## Features

## Base de Datos
> Base de datos H2 en memoria
* Se utiliza los scripts correspondientes DDL e inserción de datos.


## @TrackTime

Anotación personalizada que sirve para medir cuánto tarda en ejecutarse una petición. 
* Puede anotarse alguno o todos los métodos de la API.
* Similar a funcionamiento al @Timed de Spring
* Imprime la duración de la petición en un log.


## Excepciones

* Gestión centralizada de excepciones.
* Utilización de la anotación @ControllerAdvice.
* Retorno de HttpStatus 
* Definición de Mensaje de error para la anotación @ResponseBody

```
{
    "exception": "NotFoundCustomException",
    "message": "Not Found Exception. Id: 4",
    "path": "/api/superhero/4"
}
```

## Tests

* Utilización de Librerias
    ```xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>org.hamcrest</groupId>
		<artifactId>hamcrest-all</artifactId>
		<version>1.3</version>
	</dependency>
	```
### Test de Integracion
Verifican que los componentes de la aplicación funcionen correctamente.
Se verifican por ejemplo un endpoint de un controlador, ya que puede requerir 
la participación de uno o mas servicios.

* @InjectMocks para la unidad de prueba integral (Controller).
* @MockBean para los componentes internos de esta unidad (Service).
* Ejemplo de verificación del getAll
 
```
	@Test
	void shouldFetchAllSuperHerosSuccessAndHasSize3_whenFindAll() throws Exception {
		when(superHeroService.findAll()).thenReturn(superHeroResponseList);

		this.mockMvc.perform(get("/api/superhero/")).andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
	}
```

### Test Unitarios
Pruebas más a bajo nivel. Verifican que un componente de la aplicacion funcione correctamente.
Generalmente un método de un servicio.

* @InjectMocks para la unidad de prueba integral (Servicio).
* @Mock para los componentes internos de esta unidad (Repository).
* Ejemplo de verificación del método findAllByActive

```
	@Test
	void whenFindAllActiveSuperHeros_returnAllSuperHerosTest() throws Exception {
		Optional<SuperHero> superHeroA = Optional.ofNullable(new SuperHero());
		superHeroA.get().setId(1);
		superHeroA.get().setName("Spiderman");
		superHeroA.get().setOwner("Marvel");
		superHeroA.get().setActive(true);

		Optional<SuperHero> superHeroB = Optional.ofNullable(new SuperHero());
		superHeroB.get().setId(2);
		superHeroB.get().setName("Hulk");
		superHeroB.get().setOwner("Marvel");
		superHeroB.get().setActive(true);

		Optional<SuperHero> superHeroC = Optional.ofNullable(new SuperHero());
		superHeroC.get().setId(3);
		superHeroC.get().setName("Superman");
		superHeroC.get().setOwner("DC");
		superHeroC.get().setActive(true);

		List<SuperHero> superHeroList = new ArrayList<>();
		superHeroList.add(superHeroA.get());
		superHeroList.add(superHeroB.get());
		superHeroList.add(superHeroC.get());

		repository.save(superHeroA.get());
		when(repository.findAllByActive(true)).thenReturn(superHeroList);

		List<SuperHeroResponse> expected = superHeroService.findAll();

		assertEquals(expected.size(), superHeroList.size());
	}
```

## Docker

* La aplicación se presenta dockerizada.
* Cuenta con un Dockerfile en base a openjdk:11-jre-slim-buster

### Dockerfile

```
FROM openjdk:11-jre-slim-buster
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/superhero.jar
ADD ${JAR_FILE} superhero.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/superhero.jar"]
```

### Dockerizando
* Construccion de la imagen Docker. En el directorio raiz de la aplicación:

```
docker build -t superhero .
```

* Verificacion de la imagen creada (superhero)

```
docker images
```

* Ejecucion de la imagen creada

```
docker run -it -p 8080:8080 superhero
```

## Cache
Aplicación con caching de peticiones

```
@Cacheable(cacheNames = "superhero", key = "#name")
public List<SuperHeroResponse> findByName(String name) ...
```


## Documentacion
Se utiliza Swagger para documentar la API Rest.

```xml
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
    </dependency>
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
    </dependency>
```

Con sus respectivas anotaciones se define las descripciones de cada uno de los endpoints 
creados en el controlador

```
@ApiOperation(value = "Find SuperHero By Id")
@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = SuperHeroResponse.class) })
```
## Seguridad
* Se utiliza Spring Security con Basic Security para securizar los accesos a los endpoints de la aplicación.
* Se tiene en cuenta usuarios y roles para cada uno de los accesos.
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```
```
	@PreAuthorize("hasRole('USER')")
	@PreAuthorize("hasRole('MANAGER')")
```
## JAVA 11 Features 
### New Features List
-   [181](https://openjdk.java.net/jeps/181): Nest-Based Access Control
-   [309](https://openjdk.java.net/jeps/309): Dynamic Class-File constants
-   [315](https://openjdk.java.net/jeps/315): Improve Aarch6 Intrinsic
-   [318](https://openjdk.java.net/jeps/318): Epsilon: A no-op Garbage collector (Experimental)
-   [320](https://openjdk.java.net/jeps/320): Remove the Java EE and CORBA Modules
-   [321](https://openjdk.java.net/jeps/321): HTTP Client (Standard)
-   [323](https://openjdk.java.net/jeps/323): Local-Variable Syntax for Lambda Parameters
-   [324](https://openjdk.java.net/jeps/324): Key Agreement with Curve25519 and Curve448
-   [327](https://openjdk.java.net/jeps/327): Unicode 10
-   [328](https://openjdk.java.net/jeps/328): Flight Recorder
-   [329](https://openjdk.java.net/jeps/329): ChaCha20 and Poly1305 Cryptographic Algorithms
-   [330](https://openjdk.java.net/jeps/330): Launch Single-File Source Code Programs
-   [331](https://openjdk.java.net/jeps/331): Low-Overhead Heap Profiling
-   [332](https://openjdk.java.net/jeps/332): Transport Layer Security (TLS) 1.3
-   [333](https://openjdk.java.net/jeps/333): ZGC: A Scalable Low-Latency Garbage Collector (Experimental)
-   [336](https://openjdk.java.net/jeps/336): Deprecate the Pack200 Tools and API

### JAVA 11 Features en el Proyecto 
> JDK 11 incorpora 6 nuevos metodos para java.lang.String 
```
boolean isBlank()
```
>returns true if the string is empty or contains only white-spaces.

> Ejemplo:  
```
	// JAVA 11: String.isBlank
	if (name.isBlank()) {
		throw new BadRequestCustomException("String is blank");
	}
```

> JDK 11 `java.util.function.Predicate` con el método estático `not()` 

```
List<String> nameList = superHeroList.stream().map(x -> x.getName()).filter(Predicate.not(String::isBlank)).collect(Collectors.toList());
```

> Utilización de StringBuilder (desde Java 1.5 mejorado en la JDK11)
```
StringBuilder logInfo = new StringBuilder(100);
		logInfo.append(joinPoint.getSignature()
		.getDeclaringTypeName())
	 .append("::").append(joinPoint.getSignature().getName())
	.append(" [").append(timeTaken).append("ms]");

		log.info(logInfo.toString());
```

> Nuevos metodos para List, Set y Map.
> `List.of` crea una copia inmutable de una lista. 
```
		var immutableListJava11 = List.of(nameList);
		log.info("SuperHero Names:".concat(immutableListJava11.toString()));
```

> Métodos para Streams: `dropWhile` y `takeWhile` aceptan `Predicate`
```
		// Filter getOwner not blank in JAVA 11 Stream with takeWhile accepts predicate 
		return superHeroList.stream().takeWhile(s -> !s.getOwner().isBlank()).map(SuperHeroMapper::superHeroToResponse)
				.collect(Collectors.toList());
```

### JAVA 8 Features en el Proyecto 
> `Optional`
```
 if (!superHeroOp.isPresent()) { .. }
 ```

> Streams & Lambda Expressions
```
return superHeroList.stream().map(SuperHeroMapper::superHeroToResponse).collect(Collectors.toList());
 ```
 
## Construir el Proyecto
Se puede construir el proyecto con `Maven`.   
En el directorio raíz:
> `mvn clean install`

## Running Spring Boot 
Con el siguiente comando en el directorio raiz del modulo:
> `mvn spring-boot:run` 