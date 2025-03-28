package com.iticbcn.mywebapp.llibresApp.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="books")
public class Llibre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_Llibre")
    private Long id_Llibre;
    @Column(unique=true,nullable =false)
    private String titol;
    @Column(nullable = false)
    private String autor;
    @Column
    private String editorial;
    @Column
    private LocalDate datapublicacio;
    @Column
    private String tematica;
    @Column(unique = true, nullable = false)
    private String isbn;
}

