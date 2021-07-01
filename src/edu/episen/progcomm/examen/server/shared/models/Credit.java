package edu.episen.progcomm.examen.server.shared.models;

public class Credit {
    private Compte compte;
    private Double somme;
    private String motif;

    public Credit(Compte compte, Double somme, String motif) {
        this.compte = compte;
        this.somme = somme;
        this.motif = motif;
    }

    public Credit() {
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public Double getSomme() {
        return somme;
    }

    public void setSomme(Double somme) {
        this.somme = somme;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
