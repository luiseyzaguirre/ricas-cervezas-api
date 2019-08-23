# Test MS

### SOLUCION a Test MS

### Crear un API Rest basándonos en la definición que se encuentra en el archivo **openapi.yaml**.
Para ver las definiciones del rest ir a:  http://localhost:8080/bender/ricas/cervezas/swagger-ui.htm

     Se creo la api usando java con spring boot 2 con las definiciones de openapi.yaml
     

### Esta API debe permitir persistir la información en una base de datos y en memoria.
    Se usó H2 para persistir los datos en memoria y base de datos. También se podria haber utilizado Mongo.
   
###  Parametrizar en el inicio del servicio que método de persistencia se utilizara (base de datos o memoria).   
   POR DEFECTO la persistencia está seteada en MEMORIA y para cambiarla se debe parametrizar con el siguiente comando:
    
        --spring.datasource.url=jdbc:h2:file:~/:testdb;DB_CLOSE_ON_EXIT=FALSE;
    El parametro se debe pasar via linea de comandos cuando se inicia el aplicativo o configurando el IDE (Yo uso intellij idea).
    
    EJEMPLO 
            java -jar target/ricas-cervezas-api-0.0.1-SNAPSHOT.jar --spring.datasource.url=jdbc:h2:file:~/:testdb;DB_CLOSE_ON_EXIT=FALSE;
     
###  Información  Adicional
    
  Para revisar la base de datos via consola web: 
          http://localhost:8080/bender/ricas/cervezas/h2-console/
          
     Para persistencia en disco llenar en JDBC URL : jdbc:h2:file:~/:testdb;DB_CLOSE_ON_EXIT=FALSE;
     Para persistencia en memoria llenar en JDBC URL : jdbc:h2:mem:testdb;
     NOTA: Al inicio se llenar 4 registros de prueba el archivo es import.sql          
    
    Para ejecutar los rest desde postman:
       GET     localhost:8080/bender/ricas/cervezas/api/beers (Listar todas las cervezas)
       POST    localhost:8080/bender/ricas/cervezas/api/beers (Ingresar una cerveza)
       GET     localhost:8080/bender/ricas/cervezas/api/1     (Buscar una cerveza por id)
    
### TODO
    1)Securitizar servicios rest
    2)Securitizar Swagger 
    3)Usar mongo DB en lugar de H2
    4)Agregar rest para modificar los datos de la cerveza
    5)Agregar rest para eliminar una cerveza
    6)Llegar hasta el 90% de cobertura de test
    7)Usar Behavior Driven Development para las pruebas      
              


  