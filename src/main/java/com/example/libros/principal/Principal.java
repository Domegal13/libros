package com.example.libros.principal;

import com.example.libros.model.*;
import com.example.libros.repository.AutorRepository;
import com.example.libros.repository.LibroRepository;
import com.example.libros.service.ConsumoAPI;
import com.example.libros.service.ConvierteDatos;

import java.util.*;


public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    public ConsumoAPI consumoApi = new ConsumoAPI();
    public ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;
    private List<Libro> listaDeLibrosRegistrados;
    private List<Autor> listaAutoresRegidtrados;
    private DatosApiResponse datos;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = "-1";
        while (!opcion.equals("0")) {
            var menu = """
                    \n*************** Por Favor Elija una opción del menú ***************\n
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    *********************************************************************\n
                    """;
            System.out.println(menu);
            opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    buscarLibroPorTitulo();
                    break;
                case "2":
                    listarLibrosRegistrados();
                    break;
                case "3":
                    listarAutoresRegistrados();
                    break;
                case "4":
                    listarAutoresVivosPorAnio();
                    break;
                case "5":
                    listarLibrosPorIdiomas();
                    break;
                case "0":
                    System.out.println("Saliendo de aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }
    }

    public DatosApiResponse getDatosLibros(){
        System.out.println("Introduzca el nombre del libro a buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        datos = conversor.obtenerDatos(json, DatosApiResponse.class);
        return datos;
    }

    private void buscarLibroPorTitulo() {

        DatosApiResponse datosApiResponse = getDatosLibros();

        if(datosApiResponse.resultado().isEmpty()){
            System.out.println("Libro no encontrado");
        }else {
            DatosLibro datosLibro = datosApiResponse.resultado().getFirst();
            DatosAutor datosAutor = datosLibro.autor().getFirst();
            Optional<Libro> libroBuscado = libroRepository.findByTituloContainsIgnoreCase(datosLibro.titulo());

            if(libroBuscado.isPresent()){

                System.out.println("\n---------- LIBRO ----------");

                System.out.println("Título: " + libroBuscado.get().getTitulo());
                System.out.println("Autor: " + libroBuscado.get().getAutor().getNombre());
                System.out.println( "Idioma: " + libroBuscado.get().getIdiomas());
                System.out.println("Número de Descargas: " + libroBuscado.get().getNumerosDescargas());

                System.out.println("-----------------------------");


            } else {
                Autor autorBuscado = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());
                if (autorBuscado != null){
                    Libro libro =new Libro(datosLibro, autorBuscado);
                    libroRepository.save(libro);

                    System.out.println("\n---------- LIBRO ----------");

                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println( "Idioma: " + libro.getIdiomas());
                    System.out.println("Número de Descargas: " + libro.getNumerosDescargas());

                    System.out.println("-----------------------------");

                } else {
                    Autor autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                    Libro libro = new Libro(datosLibro, autor);
                    libroRepository.save(libro);

                    System.out.println("\n---------- LIBRO ----------");

                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println( "Idioma: " + libro.getIdiomas());
                    System.out.println("Número de Descargas: " + libro.getNumerosDescargas());

                    System.out.println("-----------------------------");

//                    System.out.println(libro);
                }

            }

        }
    }

    private void listarLibrosRegistrados() {

        listaDeLibrosRegistrados = libroRepository.OrderByTitulo();

        System.out.println("\n******************* Lista de Libros Registrados *******************\n");

        listaDeLibrosRegistrados.forEach(li -> {
            System.out.println("\n---------- LIBRO ----------");
            System.out.println("Título: " + li.getTitulo());
            System.out.println("Autor: " + li.getAutor().getNombre());
            System.out.println( "Idioma: " + li.getIdiomas());
            System.out.println("Número de Descargas: " + li.getNumerosDescargas());
            System.out.println("-----------------------------");

        });
    }

    private void listarAutoresRegistrados() {

        listaAutoresRegidtrados = autorRepository.OrderByNombre();

        System.out.println("\n******************* Lista de Autores Registrados *******************\n");

        listaAutoresRegidtrados.forEach(a -> {
            System.out.println("\nAutor: " + a.getNombre());
            System.out.println("Fecha de Nacimiento:: " + a.getNacimiento());
            System.out.println( "Fecha de Fallecimiento: " + a.getDeceso());
        });

        System.out.println("\n********************************************************************");

    }

    private void listarAutoresVivosPorAnio() {

        System.out.println("Introduzca la Año a buscar: ");
        var fechaBusqueda = teclado.nextInt();
        teclado.nextLine();

        List<Autor> listaAutores = autorRepository.autoresVivosEnDeterminadoAnno(fechaBusqueda);



        System.out.println("\n****************** Lista de Autores Vivos por Año ******************\n");
        System.out.println("Año Buscado: " + fechaBusqueda);

        listaAutores.forEach(a -> {
            System.out.println("\nAutor: " + a.getNombre());
            System.out.println("Fecha de Nacimiento:: " + a.getNacimiento());
            System.out.println("Fecha de Fallecimiento: " + a.getDeceso());

        });

        System.out.println("\n********************************************************************");

    }

    private void listarLibrosPorIdiomas() {
        String idioma;

            System.out.println("""
                    ---------- Elija el idioma por el que desea buscar: ----------
                    1 - Español
                    2 - Ingles
                    3 - Frances
                    4 - Portugues

                    -------------------------------------
                    
                    """);
            var opcionIdioma = teclado.nextLine();


        if (opcionIdioma.equals("1")) {
            idioma = "es";
        } else if (opcionIdioma.equals("2")) {
            idioma = "en";
        } else if (opcionIdioma.equals("3")) {
            idioma = "fr";
        } else if (opcionIdioma.equals("4")) {
            idioma = "pt";
        } else {
            idioma = "error";
            System.out.println(" ---------- Opción inválida ----------");
        }


        while (!idioma.equals("error")){
            List<Libro> listaIdiomas = libroRepository.findByIdiomasContaining(idioma);
            if(listaIdiomas.isEmpty()){
                System.out.println("No se ha encontrado libros en el idioma seleccionado: " + idioma);
            } else {
                System.out.println("\n************ Lista de libros por idioma ************");

                listaIdiomas.forEach(i -> {
                    System.out.println("\n---------- LIBRO ----------");
                    System.out.println("Título: " + i.getTitulo());
                    System.out.println("Autor: " + i.getAutor().getNombre());
                    System.out.println( "Idioma: " + i.getIdiomas());
                    System.out.println("Número de Descargas: " + i.getNumerosDescargas());
                    System.out.println("-----------------------------");
                });
            }

            idioma = "error";
        }


    }


}