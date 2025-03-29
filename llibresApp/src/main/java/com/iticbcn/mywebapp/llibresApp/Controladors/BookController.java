package com.iticbcn.mywebapp.llibresApp.Controladors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
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

    @GetMapping("/cercaTitol")
    public String inputCercaTitol(@ModelAttribute("users") Usuaris users, Model model) {

        Llibre llibre = new Llibre();
        model.addAttribute("llibreErr", true);
        model.addAttribute("message", "");
        model.addAttribute("llibre", llibre);
        return "cercaTitol";

    }
    @GetMapping("/cercaTitolEditorial")
    public String inputCercaTitolEditorial(@ModelAttribute("users") Usuaris users, Model model) {
        Llibre llibre = new Llibre();
        model.addAttribute("llibreErr", true);
        model.addAttribute("message", "");
        model.addAttribute("llibre", llibre);
        return "cercaTitolEditorial";

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
            message = "El ISBN no és vàlid";
            llibreErr = true;
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }
        if (titol.isEmpty()) {
            message = "El títol no pot estar en blanc";
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

        try {
            repoll.save(llibre);
        } catch (Exception e) {
            message = "Error al guardar el llibre: ISBN o titol duplicat";
            llibreErr = true;
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }
        Set<Llibre> llibres = repoll.findAll();
        model.addAttribute("llibres", llibres);
        return "consulta";
    }

    @PostMapping("/cercaid")
    public String cercaId(@ModelAttribute("users") Usuaris users,
            @RequestParam(name = "id_Llibre", required = false) String id_Llibre,
            Model model) {

        Long idLlib = null;
        String message = "";
        boolean llibreErr = false;

        if (id_Llibre == null || id_Llibre.trim().isEmpty()) {
            message = "La id del llibre no pot estar buida.";
            llibreErr = true;
        } else {
            try {
                id_Llibre = id_Llibre.trim();
                idLlib = Long.parseLong(id_Llibre);
                Optional<Llibre> llibre = repoll.findById_Llibre(idLlib);
                if (llibre.isPresent()) {
                    model.addAttribute("llibre", llibre.get());
                } else {
                    throw new Exception("No hi ha cap llibre amb aquesta id");
                }

            } catch (NumberFormatException e) {
                message = "La id de llibre ha de ser un nombre enter";
                llibreErr = true;
            } catch (Exception e) {
                message = e.getMessage();
                llibreErr = true;
            }
        }
        model.addAttribute("message", message);
        model.addAttribute("llibreErr", llibreErr);
        return "cercaid";
    }

    @PostMapping("/cercaTitol")
    public String CercaTitol(@ModelAttribute("users") Usuaris users,
            @RequestParam(name = "titol", required = false) String titol,
            Model model) {

        String message = "";
        boolean llibreErr = false;
        Llibre llibre = new Llibre();
        if (titol.isEmpty()) {
            message = "El títol no pot estar buit.";
            llibreErr = true;
        } else {
            try {
                llibre = repoll.findByTitol(titol);
                if (llibre == null) {
                    message = "No s'ha trobat cap llibre amb aquest títol.";
                    llibreErr = true;
                }
            } catch (Exception e) {
                message = "Error: " + e.getMessage();
                llibreErr = true;
            }
        }
        model.addAttribute("llibre", llibre);
        model.addAttribute("message", message);
        model.addAttribute("llibreErr", llibreErr);
        return "cercaTitol";
    }
    @PostMapping("/cercaTitolEditorial")
    public String CercaTitolEditorial(@ModelAttribute("users") Usuaris users,
        @RequestParam(name = "titol", required = false) String titol,
        @RequestParam(name = "editorial", required = false) String editorial,
        Model model) {
    String message = "";
    boolean llibreErr = false;
    Set<Llibre> llibres = new HashSet<Llibre>();
    if (titol == null || titol.trim().isEmpty() || editorial == null || editorial.trim().isEmpty()) {
        message = "Omple tots els camps";
        llibreErr = true;
    } else {
        try {
            llibres = repoll.findByTitolAndEditorial(titol, editorial);
            if (llibres.isEmpty()) {
                message = "No s'ha trobat cap llibre amb aquest títol i editorial";
                llibreErr = true;
            }
        } catch (Exception e) {
            message = "Error: " + e.getMessage();
            llibreErr = true;
        }
    }
    model.addAttribute("llibres", llibres);
    model.addAttribute("message", message);
    model.addAttribute("llibreErr", llibreErr);
    return "cercaTitolEditorial";
}

}