package com.example.demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.demo.config.PersistenceApplication;
import com.example.demo.config.ServiceApplication;
import com.example.demo.config.ServletApplication;
import com.example.demo.config.WebSecurityConfig;

/**
 * 
 * 
 * // Anotaciones Generales
 * @SpringBootApplication Encapsula 
 * 						  @Configuration, 
 *  					  @EnableAutoConfiguration
 *                        @ComponentScan 
 *                        anotaciones con sus atributos
 *                        predeterminados (No recomendado), sirve para lanzar en una sola
 *                        anotacion el proyecto springboot.
 * 
 * // Detalle
 * @EnableAutoConfiguration como su nombre lo indica, habilita la configuración automática. 
 *                          Significa que Spring Boot busca beans de configuración automática en su
 *                          classpath y los aplica automáticamente en funcion a dependencias(Incluido el started)
 *                          que hayan sido agregados (pom o jar). Solo debe añadirse en un sitio, y 
 *                          es muy frecuente situarla en la clase main
 * 
 *                          Tenga en cuenta que tenemos que usar esta anotación
 *                          con @Configuration
 *                          
 *                          Ejemplo: para el spring-boot-starter-web dependencia en su classpath, 
 *                          configura automáticamente Tomcat y Spring MVC.
 *                          
 *                           Tiene dos elementos opcionales,
 *
 *							 excluir: si desea excluir la configuración automática de una clase.
 *							 excludeName: si desea excluir la configuración automática de una clase utilizando el 
 *							 nombre completo de la clase.
 * 

 * @Configuration           Indica que en la clase que se encuentra anotada contiene la configuracion
 * 							principal del proyecto. Son clases de configuracion basadas en java.
 * 
 * @ComponentScan           Ayuda a localizar elementos etiquetados con otras anotaciones (Spring anotados) 
 * 							cuando sean necesarios en el paquete especificado.
 * 							Si no se especifica el paquete, usa el paquete base(Raiz) actual donde esta la anotación.
 * 							
 * 							Ejemplo:
 *						    Uno de los elementos opcionales es,
 *							
 *							basePackages: se puede usar para indicar paquetes específicos para escanear.
 *
 *@Import					Importa configuraciones adicionales, es equivalente al import de xml <import/> 
 *                   
 * @author tarja
 *
 */
@Configuration
@EnableAutoConfiguration
@Import({ServletApplication.class, ServiceApplication.class, PersistenceApplication.class, WebSecurityConfig.class})
public class DemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
