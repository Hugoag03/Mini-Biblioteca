package com.tcna.springboot.controllers;

import com.tcna.springboot.entitites.Editorial;
import com.tcna.springboot.services.EditorialService;
import com.tcna.springboot.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroService libroService;


    @GetMapping({"listar", "/"})
    public String listarEditorial(Model model) {
        List<Editorial> editoriales = editorialService.listarTodasLasEditoriales();
        model.addAttribute("editorial", editoriales);
        return "editorial/lista_editoriales";
    }

    @GetMapping("/{id}")
    public String mostrarEditorial(@PathVariable Long id, Model model) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();
            model.addAttribute("editorial", editorial);
            model.addAttribute("libros", editorial.getLibros());

            return "editorial/mostrar_editorial";
        }
        return null;

    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevaEditorial(Model model) {
        model.addAttribute("editorial", new Editorial());

        return "editorial/formulario_editoriales";
    }

    @PostMapping("/guardar")
    public String guardarEditorial(@ModelAttribute Editorial editorial) {
        editorialService.guardarEditorial(editorial);

        return "redirect:/editoriales/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarEditorial(@PathVariable Long id, Model model) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);
        editorialOptional.ifPresent(value -> model.addAttribute("editorial", value));

        return "editorial/formulario_editoriales";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarEditorial(@PathVariable Long id, @ModelAttribute Editorial editorial) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);

        if (editorialOptional.isPresent()) {
            Editorial editorialActual = editorialOptional.get();
            editorialActual.setNombre(editorial.getNombre());
            editorialService.actualizarEditorial(editorialActual);

            return "redirect:/editoriales/listar";
        }
        return null;
    }

    @GetMapping("eliminar/{id}")
    public String eliminarEditorial(@PathVariable Long id) {
        editorialService.eliminarEditorial(id);
        return "redirect:/editoriales/listar";
    }

    @GetMapping("libros/{id}")
    public String mostrarLibrosDeEditorial(@PathVariable Long id, Model model) {
        Optional<Editorial> editorialOptional = editorialService.buscarPorId(id);
        if (editorialOptional.isPresent()) {
            Editorial editorial = editorialOptional.get();
            model.addAttribute("editorial", editorial);
            model.addAttribute("libro", editorial.getLibros());
        }
        return "editorial/mostrar_libros_editorial";
    }

}
