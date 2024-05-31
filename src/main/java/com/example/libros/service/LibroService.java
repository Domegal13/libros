package com.example.libros.service;

import com.example.libros.model.Autor;
import com.example.libros.model.Libro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {

//    @Autowired
//    private LibroRepository libroRepository;
//
//    @Autowired
//    private AutorRepository autorRepository;
//
//    @Transactional
//    public Libro guardarLibroConAutores(String titulo, List<String> nombresAutores) {
//        Libro libro = new Libro();
//        libro.setTitulo(titulo);
//
//        List<Autor> autores = new ArrayList<>();
//        for (String nombre : nombresAutores) {
//            Autor autor = autorRepository.findByNombre(nombre);
//            if (autor == null) {
//                autor = new Autor();
//                autor.setNombre(nombre);
//                autor = autorRepository.save(autor);  // Guardar el autor primero
//            }
//            autores.add(autor);
//        }
//
//        libro.setAutores(autores);
//        return libroRepository.save(libro);  // Guardar el libro con los autores ya guardados
//    }
}
