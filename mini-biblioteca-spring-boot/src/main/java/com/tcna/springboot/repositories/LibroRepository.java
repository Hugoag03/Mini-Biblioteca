package com.tcna.springboot.repositories;

import com.tcna.springboot.entitites.Categoria;
import com.tcna.springboot.entitites.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByCategoria(Categoria categoria);
}
