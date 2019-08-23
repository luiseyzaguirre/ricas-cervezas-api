package cl.bender.ricascervezas.api.controller;

import cl.bender.ricascervezas.api.exception.BeerAlreadyExists;
import cl.bender.ricascervezas.api.exception.BeerInternalServerError;
import cl.bender.ricascervezas.api.exception.BeerInvalidRequest;
import cl.bender.ricascervezas.api.exception.BeerNotFoundException;
import cl.bender.ricascervezas.api.model.BeerItem;
import cl.bender.ricascervezas.api.service.BeerItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BeerItemController {
    @Autowired
    private BeerItemService beerItemService;

    /**
     * Lista todas las cervezas
     *
     * @return listado de todas las cervezas
     * y codigo http (Ok:200;Internal Server Error:500)
     */
    @GetMapping("/beers")
    @ApiOperation(value = "Lista todas las cervezas",
                  notes = "Lista todas las cervezas que se encuentran en la base de datos",
                  tags  = "cerveza")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operacion exitosa", response = BeerItem.class),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> searchBeers() {
        try {
            return new ResponseEntity<>(beerItemService.searchBeers(), OK);
        }catch (BeerInternalServerError e){
            return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca una cerveza por su id
     *
     *@param  id
     *            {@link Integer} identificador
     *            de la cerveza
     * @return una cerveza {@link BeerItem}
     * con codigo http Ok:200
     * Y si hubiera error Internal Server Error:500 o Not Found:404
     */
    @GetMapping("/beers/{id}")
    @ApiOperation(value = "Busca una cerveza", notes = "Busca una cerveza por su Id", tags="cerveza")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operacion exitosa", response = BeerItem.class),
            @ApiResponse(code = 404, message = "El Id de la cerveza no existe"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> searchBeerById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(beerItemService.searchBeerById(id), OK);
        } catch (BeerInternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
        } catch (BeerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    /**
     * Ingresa una nueva cerveza
     *
     *@param  beerItem
     *            {@link BeerItem} identificador
     *            de la cerveza
     * @return una cerveza {@link BeerItem}
     * con codigo http Ok:200
     * Y si hubiera error Internal Server Error:500, Bad Request:400, Conflict:409)
     */
    @PostMapping("/beers")
    @ApiOperation(value = "Ingresa una nueva cerveza", notes = "Ingresa una nueva cerveza", tags="cerveza")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cerveza creada", response = BeerItem.class),
            @ApiResponse(code = 400, message = "Request invalido"),
            @ApiResponse(code = 409, message = "El ID de la cerveza ya existe")})
    public ResponseEntity<?> addBeers(@RequestBody BeerItem beerItem) {
        try {
            return new ResponseEntity<>(beerItemService.addBeers(beerItem), CREATED);
        } catch (BeerInvalidRequest e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        } catch (BeerInternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
        } catch (BeerAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), CONFLICT);
        }
    }
}

