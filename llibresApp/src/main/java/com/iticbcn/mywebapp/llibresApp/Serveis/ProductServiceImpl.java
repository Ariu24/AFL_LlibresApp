package com.iticbcn.mywebapp.llibresApp.Serveis;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iticbcn.mywebapp.llibresApp.Model.Llibre;
import com.iticbcn.mywebapp.llibresApp.Repositoris.Repositori;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private Repositori repo;

    @Override
    public Set<Llibre> findAll() {
        return repo.findAll();
    }

    @Override
    public Llibre findByTitol(String titol) {
        return repo.findByTitol(titol);
    }

    @Override
    public Set<Llibre> findByTitolAndEditorial(String titol, String editorial) {
        return repo.findByTitolAndEditorial(titol, editorial);
    }

    @Override
    public boolean validarISBN(String isbn) {
        if (isbn == null || isbn.length() != 13) {
            return false; // ISBN debe tener exactamente 13 caracteres
        }
    
        // Verificar que las dos primeras posiciones sean letras
        String prefix = isbn.substring(0, 2);
        if (!prefix.matches("[A-Za-z]{2}")) {
            System.out.println("Las dos primeras posiciones del ISBN deben ser letras.");
            return false;
        }
    
        // Verificar que los caracteres restantes sean números
        for (int i = 2; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                System.out.println("El ISBN debe contener solo números después de las dos primeras letras.");
                return false;
            }
        }
    
        return true; // Si pasa todas las validaciones, es un ISBN válido
    }
    

    @Override
    public Optional<Llibre> findById_Llibre(Long id_Llibre) {
        Optional<Llibre> llibre = repo.findById(id_Llibre);
        if (!llibre.isPresent()) {
            throw new NoSuchElementException("Llibre amb l'" + id_Llibre + " no existeix");
        }
        return llibre;
    }
    @Override
    public void save(Llibre llibre) {
        repo.save(llibre);
    }
}
