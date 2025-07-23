package com.JoseLeonardo.springmvc.app.controlers;

import com.JoseLeonardo.springmvc.app.entities.User;
import com.JoseLeonardo.springmvc.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping({"/view", "/another"})
    public String viewData(Model model){
        model.addAttribute("title", "Hola Mundo SpringBoot!!!");
        model.addAttribute("message", "Esta es una aplicación de ejemplo usando Spring Boot!!!");
        model.addAttribute("user", new User("Andres", "Guzman"));
        return "view"; //view es el nombre de la plantilla (el archivo html dentro de templates, lo que retorna el metodo debe ser la plantilla)//;
    }

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("title", "Listado de Usuarios");
        model.addAttribute("users", service.findAll());
        return "list";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("title", "Crear Usuario");
        return "form";
    }

    @GetMapping("/form/{id}")
    public String form(@PathVariable Long id, Model model, RedirectAttributes redirect){
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()){
            model.addAttribute("user", optionalUser.get());
            model.addAttribute("title", "Editar Usuario");
            return "form";
        } else {
            redirect.addFlashAttribute("error", "El usuario con id " +
                    id +
                    " no existe en la base de datos");
            return "redirect:/users";
        }

    }

    @PostMapping
    public String form(User user, Model model, RedirectAttributes redirect){
        String message = (user.getId() != null && user.getId() >0)? "El usuario " + user.getName() + " Se ha actualizado con exito!" :"El usuario " + user.getName() + " Se ha creado con exito!";
        service.save(user);
        redirect.addFlashAttribute("Success", message);

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        Optional<User> optionalUser = service.findById(id);
        if (optionalUser.isPresent()) {
            redirect.addFlashAttribute("Success", "El usuario " + optionalUser.get().getName() + " Se ha eliminado con exito!");
            service.remove(id);
            return "redirect:/users";
        }
        redirect.addFlashAttribute("error", "El usuario con el id " + id + " no se encuentra en el sistema");
        return "redirect:/users";
    }


}
