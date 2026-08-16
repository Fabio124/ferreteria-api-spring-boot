package com.ferreteria.ferreteria_api.service;

import com.ferreteria.ferreteria_api.model.Herramienta;
import com.ferreteria.ferreteria_api.repository.HerramientaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    @Autowired
    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = herramientaRepository;
    }

    // Crear una nueva herramienta
    public Herramienta crear(Herramienta herramienta){
       //validacion que el producto no sea null
        if(herramienta==null || !valdarDatos(herramienta)){
            return null;
        }
        //Id se genera  automaticmente en la BD y
        // con esto la devolvemos junto con el producto
        return herramientaRepository.save(herramienta);
    }
    // Listar todas

    public List<Herramienta>listarTodos() {
        return herramientaRepository.findAll();
    }
    // Buscar una por id
    public Herramienta  buscarPorId(Long id){
        return herramientaRepository.findById(id).orElse(null);
    }
    // Actualizar una existente
    public Herramienta actulizar(Long id, Herramienta datosActualizados){

        if (!valdarDatos(datosActualizados)){
            return null;
        }
        //buscar si existe el product
        Herramienta e= herramientaRepository.findById(id).orElse(null);
        //verificr que existe
        if(e !=null){


            //actualizamos los datos del product
            e.setNombre(datosActualizados.getNombre());
            e.setMarca(datosActualizados.getMarca());
            e.setCategoria(datosActualizados.getCategoria());
            e.setPrecio(datosActualizados.getPrecio());
            e.setCantidadStock(datosActualizados.getCantidadStock());
            e.setDescripcion(datosActualizados.getDescripcion());
            return herramientaRepository.save(e);
        }
        return null;

    }
    // Eliminar
    public boolean eliminar(Long id){
        Herramienta e=buscarPorId(id);

        if (e==null){
            return  false;
        }
        herramientaRepository.deleteById(id);
        return true;
    }

    public boolean valdarDatos(Herramienta herramienta){
        if (herramienta.getNombre()== null || herramienta.getNombre().isBlank()){
            return false;
        }
        if (herramienta.getMarca()== null || herramienta.getMarca().isBlank()){
            return false;
        }
        if (herramienta.getCategoria()== null || herramienta.getCategoria().isBlank()){
            return false;
        }
        if (herramienta.getPrecio()== null || herramienta.getPrecio().compareTo(BigDecimal.ZERO)<= 0) {
            return false;
        }
        if (herramienta.getCantidadStock()<0) {
            return false;
        }
        if (herramienta.getDescripcion()== null || herramienta.getDescripcion().isBlank()){
            return false;
        }
        return true;
    }
    /*
    // Eliminar
    public void eliminar(Long id){


        if (!herramientaRepository.existsById(id)) {
            throw new RuntimeException("Herramienta no encontrada con id: " + id);
        }
        herramientaRepository.deleteById(id);
    }

     */
}
