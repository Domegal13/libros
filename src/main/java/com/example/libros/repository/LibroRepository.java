package com.example.libros.repository;

import com.example.libros.model.Autor;
import com.example.libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    List<Libro> OrderByTitulo();

    List<Libro> findByIdiomasContaining(String idioma);



}
