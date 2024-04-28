package com.tcna.springboot.services;

import com.tcna.springboot.entitites.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorService {

    Autor guardarAutor(Autor autor);

    Optional<Autor> buscarPorId(Long id);

    Optional<Autor> buscarPorNombre(String nombre);

    List<Autor> listarTodosLasAutores();

    Autor actualizarAutor(Autor autor);

    void eliminarAutor(Long id) throws ClassNotFoundException;

    List<Autor> buscarPorIds(List<Long> ids);
}
