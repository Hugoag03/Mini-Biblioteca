package com.tcna.springboot.controllers;

import com.tcna.springboot.entitites.Autor;
import com.tcna.springboot.entitites.Categoria;
import com.tcna.springboot.entitites.Editorial;
import com.tcna.springboot.entitites.Libro;
import com.tcna.springboot.services.AutorService;
import com.tcna.springboot.services.CategoriaService;
import com.tcna.springboot.services.EditorialService;
import com.tcna.springboot.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroService libroService;

    @GetMapping({"/listar", "/"})
    public String listarLibros(Model model) {
        List<Libro> libros = libroService.listarTodosLosLibros();
        model.addAttribute("libro", libros);

        return "libro/lista_libros";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        model.addAttribute("editoriales", editorialService.listarTodasLasEditoriales());
        model.addAttribute("categorias", categoriaService.listarTodasLasCategorias());
        model.addAttribute("autores", autorService.listarTodosLasAutores());
        return "libro/formulario_libros";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro,
                               @RequestParam("editorialId") Long editorialId,
                               @RequestParam("categoriaId") Long categoriaId,
                               @RequestParam("autoresIds") List<Long> autoresIds) {
        //Obtener y asignar la editorial y la categoría al libro
        Optional<Editorial> editorial = editorialService.buscarPorId(editorialId);
        editorial.ifPresent(libro::setEditorial);

        Optional<Categoria> categoria = categoriaService.buscarPorId(categoriaId);
        categoria.ifPresent(libro::setCategoria);

        List<Autor> autores = autorService.buscarPorIds(autoresIds);
        libro.setAutores(new ArrayList<>(autores));

        libroService.saveLibro(libro);

        return "redirect:/libros/listar";

    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarLibro(@PathVariable Long id, Model model) {
        Optional<Libro> libroOptional = libroService.buscarPorId(id);
        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            model.addAttribute("libro", libro);
            model.addAttribute("editoriales", editorialService.listarTodasLasEditoriales());
            model.addAttribute("categorias", categoriaService.listarTodasLasCategorias());
            model.addAttribute("autores", autorService.listarTodosLasAutores());

            return "libro/formulario_libros";
        }


        return null;
    }

    @PostMapping("/actualizar/{id}")
    public String guardarLibro(@PathVariable Long id, @ModelAttribute Libro libro,
                               @RequestParam("editorialId") Long editorialId,
                               @RequestParam("categoriaId") Long categoriaId,
                               @RequestParam("autoresIds") List<Long> autoresIds) {
        //Obtener y asignar la editorial y la categoría al libro
        Optional<Editorial> editorial = editorialService.buscarPorId(editorialId);
        editorial.ifPresent(libro::setEditorial);

        Optional<Categoria> categoria = categoriaService.buscarPorId(categoriaId);
        categoria.ifPresent(libro::setCategoria);

        List<Autor> autores = autorService.buscarPorIds(autoresIds);
        libro.setAutores(new ArrayList<>(autores));

        libro.setId(id);
        libroService.actualizarLibro(libro);

        return "redirect:/libros/listar";

    }

    @GetMapping("/autores/{id}")
    public String mostrarAutoresDelLibro(@PathVariable Long id, Model model) {
        Optional<Libro> libroOptional = libroService.buscarPorId(id);
        if (libroOptional.isPresent()) {
            Libro libro = libroOptional.get();
            model.addAttribute("libro", libro);
            model.addAttribute("autores", libro.getAutores());

            return "libro/mostrar_autores_libros";
        }
        return null;

    }

    @GetMapping("eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
        return "redirect:/libros/listar";
    }
}
