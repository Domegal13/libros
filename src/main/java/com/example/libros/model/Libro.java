package com.example.libros.model;


import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String nombreAutor;
    private String idiomas;
    private Long numerosDescargas;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;



    public Libro(){}      // Constructor predeterminado

//    public Libro(DatosLibro datosLibro){
//        this.titulo = datosLibro.titulo();
//        this.idiomas = datosLibro.idiomas().getFirst();
//        this.numerosDescargas = datosLibro.numerosDescargas();
//    }
    public Libro(DatosLibro datosLibro, Autor autor){          //pendiente
        this.titulo = datosLibro.titulo();
        this.nombreAutor = autor.getNombre();
        this.idiomas = datosLibro.idiomas().getFirst();
        this.numerosDescargas = datosLibro.numerosDescargas();
        this.autor = autor;

    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Long getNumerosDescargas() {
        return numerosDescargas;
    }

    public void setNumerosDescargas(Long numerosDescargas) {
        this.numerosDescargas = numerosDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "Título='" + titulo + '\'' +
                ", Autor=" + autor +
                ", Idiomas=" + idiomas +
                ", Descargas=" + numerosDescargas;
    }
}


