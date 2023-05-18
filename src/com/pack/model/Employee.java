package com.pack.model;

import java.io.Serializable;

public class Employee implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String numero;
    private String nom;
    private String adresse;
    private float salaire;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "Employee:: Numero=" + this.numero + " Nom=" + this.nom + " Adresse=" + this.adresse + " Salaire=" + this.salaire;
    }
}
