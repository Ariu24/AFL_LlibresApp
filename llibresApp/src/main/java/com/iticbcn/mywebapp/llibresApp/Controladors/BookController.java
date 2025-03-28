package com.iticbcn.mywebapp.llibresApp.Controladors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.iticbcn.mywebapp.llibresApp.Model.Llibre;
import com.iticbcn.mywebapp.llibresApp.Model.Usuaris;
import com.iticbcn.mywebapp.llibresApp.Serveis.ProductService;

@Controller
@SessionAttributes("users")
public class BookController {

    @Autowired
    ProductService repoll;

    @GetMapping("/")
    public String iniciar(Model model) {
        return "login";
    }

    @PostMapping("/index")
    public String login(@ModelAttribute("users") Usuaris users, Model model) {

        model.addAttribute("users", users);

        if (users.getUsuari().equals("ariadnaLlibres")
                && users.getPassword().equals("1234")) {
            return "index";
        } else {
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }

    @ModelAttribute("users")
    public Usuaris getDefaultUser() {
        return new Usuaris();
    }

    @GetMapping("/index")
    public String index(@ModelAttribute("users") Usuaris users, Model model) {

        return "index";

    }

    @GetMapping("/consulta")
    public String consulta(@ModelAttribute("users") Usuaris users, Model model) {

        Set<Llibre> llibres = repoll.findAll();

        model.addAttribute("llibres", llibres);

        return "consulta";
    }

    @GetMapping("/inserir")
    public String inputInserir(@ModelAttribute("users") Usuaris users, Model model) {

        return "inserir";
    }


    @GetMapping("/cercaid")
    public String inputCerca(@ModelAttribute("users") Usuaris users, Model model) {

        Llibre llibre = new Llibre();
        model.addAttribute("llibreErr", true);
        model.addAttribute("message", "");
        model.addAttribute("llibre", llibre);

        return "cercaid";

    }

    @PostMapping("/inserir")
    public String inserir(@ModelAttribute("users") Usuaris users,
            @RequestParam(name = "titol") String titol,
            @RequestParam(name = "autor") String autor,
            @RequestParam(name = "editorial") String editorial,
            @RequestParam(name = "datapublicacio") String datapublicacio,
            @RequestParam(name = "tematica") String tematica,
            @RequestParam(name = "isbn") String isbn,
            Model model) {
        String message = "";
        boolean llibreErr = false;
        LocalDate dataPublicacio = null;
        try {
            dataPublicacio = LocalDate.parse(datapublicacio);
        } catch (DateTimeParseException e) {
            message = "La data de publicació no té el format correcte (yyyy-MM-dd)";
            llibreErr = true;
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }
        if (!repoll.validarISBN(isbn)) { 
            llibreErr = true;
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }

        Llibre llibre = new Llibre();
        llibre.setTitol(titol);
        llibre.setAutor(autor);
        llibre.setEditorial(editorial);
        llibre.setDatapublicacio(dataPublicacio);
        llibre.setTematica(tematica);
        llibre.setIsbn(isbn);
        repoll.save(llibre);

        Set<Llibre> llibres = repoll.findAll();
        model.addAttribute("llibres", llibres);

        return "consulta";
    }

    @PostMapping("/cercaid")
    public String cercaId(@ModelAttribute("users") Usuaris users,
            @RequestParam(name = "id_Llibre", required = false) String id_Llibre,
            Model model) {

        Long idLlib;
        String message = "";
        boolean llibreErr = false;

        try {
            idLlib = Long.parseLong(id_Llibre);
            Optional<Llibre> llibre = repoll.findById_Llibre(idLlib);
            if (llibre != null) {
                model.addAttribute("llibre", llibre);
            } else {
                message = "No hi ha cap llibre amb aquesta id";
                llibreErr = true;
            }

        } catch (Exception e) {
            message = "La id de llibre ha de ser un nombre enter";
            llibreErr = true;
        }

        model.addAttribute("message", message);
        model.addAttribute("llibreErr", llibreErr);

        return "cercaid";

    }

}