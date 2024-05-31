package com.example.libros.repository;

import com.example.libros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

//    Optional<Autor> findByNombreIgnoreCase(String nombre);
    Autor  findByNombreIgnoreCase(String nombre);

    List<Autor> OrderByNombre();

    @Query("Select a FROM Autor a WHERE :anno BETWEEN a.nacimiento AND a.deceso")
    List<Autor> autoresVivosEnDeterminadoAnno(@Param("anno") int anno);




}
