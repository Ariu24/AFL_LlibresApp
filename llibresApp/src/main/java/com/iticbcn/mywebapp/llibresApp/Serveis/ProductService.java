package com.iticbcn.mywebapp.llibresApp.Serveis;

import java.util.Optional;
import java.util.Set;

import com.iticbcn.mywebapp.llibresApp.Model.Llibre;

public interface ProductService {
    Set<Llibre>findAll();
    Llibre findByTitol(String nom);
    Set<Llibre>findByTitolAndEditorial (String titol, String editorial);
    boolean validarISBN(String isbn);
    Optional<Llibre> findById_Llibre(Long id_Llibre);
    void save(Llibre llibre);
}