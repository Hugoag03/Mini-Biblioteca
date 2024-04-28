package com.tcna.springboot.controllers;

import com.tcna.springboot.entitites.Categoria;
import com.tcna.springboot.entitites.Libro;
import com.tcna.springboot.services.CategoriaService;
import com.tcna.springboot.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private LibroService libroService;

    @GetMapping({"/listar", "/"})
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.listarTodasLasCategorias();
        model.addAttribute("categorias", categorias);

        return "categoria/lista_categorias";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaCategoria(Model model) {
        //Puede fallar por no crear el objeto
        model.addAttribute("categoria", new Categoria());
        return "categoria/formulario_categorias";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoria);
        List<Libro> libros = libroService.buscarPorCategoria(categoriaGuardada);
        categoriaGuardada.setLibros(libros);
        categoriaService.guardarCategoria(categoria);
        return "redirect:/categorias/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCategoria(@PathVariable Long id, Model model) {
        Optional<Categoria> categoriaOptional = categoriaService.buscarPorId(id);
        categoriaOptional.ifPresent(value -> model.addAttribute("categoria", value));

        return "categoria/formulario_categorias";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarCategoria(@PathVariable Long id, @ModelAttribute Categoria categoria) {
        Categoria categoriaActual = categoriaService.buscarPorId(id).orElse(null);

        if (categoriaActual != null) {
            categoria.setLibros(categoriaActual.getLibros());
            categoriaService.actualizarCategoria(categoria);
            return "redirect:/categorias/listar";
        }
        return null;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias/listar";
    }

}
