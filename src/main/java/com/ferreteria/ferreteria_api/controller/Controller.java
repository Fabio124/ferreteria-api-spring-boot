package com.ferreteria.ferreteria_api.controller;


import com.ferreteria.ferreteria_api.model.Herramienta;
import com.ferreteria.ferreteria_api.service.HerramientaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/herramientas")
public class Controller {

    private HerramientaService herramientaService;
    @Autowired
    public Controller(HerramientaService herramientaService) {
        this.herramientaService = herramientaService;
    }
    // GET /api/herramientas -> listar todas read
    @GetMapping
    public ResponseEntity<List<Herramienta>>listarTodas(){
        List<Herramienta> herramienta=herramientaService.listarTodos();
        return new ResponseEntity<>(herramienta, HttpStatus.OK) ;
    }

    // GET /api/herramientas/{id} -> buscar una
    @GetMapping("/{id}")
    public ResponseEntity<Herramienta> buscarPorId(@PathVariable Long id){
        Herramienta herramienta=herramientaService.buscarPorId(id);
        if(herramienta==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(herramienta, HttpStatus.OK);

    }
    // POST /api/herramientas -> crear
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Herramienta herramienta){
        if(!herramientaService.valdarDatos(herramienta)){
            return new ResponseEntity<>("Datos inválidos: revisa nombre, marca, categoría," +
                    " precio y stock", HttpStatus.BAD_REQUEST);       }

        Herramienta nuevaHerramienta= herramientaService.crear(herramienta);
        return new ResponseEntity<>(nuevaHerramienta, HttpStatus.CREATED);
    }

    // PUT /api/herramientas/{id} -> actualizar
    @PutMapping("/{id}")
    public  ResponseEntity<?>actualizar(@PathVariable Long id,
                                                  @RequestBody Herramienta herramienta){
        if(!herramientaService.valdarDatos(herramienta)){
            return new ResponseEntity<>("Datos inválidos: revisa nombre," +
                    " marca, categoría, precio y stock", HttpStatus.BAD_REQUEST);
        }


        Herramienta herramientaActualizada=herramientaService.actulizar(id, herramienta);
        if (herramientaActualizada==null){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(herramientaActualizada,HttpStatus.OK );
    }
    // DELETE /empleados/A001 → elimina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = herramientaService.eliminar(id);

        if (!eliminado) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }
