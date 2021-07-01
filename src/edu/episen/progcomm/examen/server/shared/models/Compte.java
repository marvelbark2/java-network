package edu.episen.progcomm.examen.server.shared.models;

import java.io.Serializable;

public class Compte implements Serializable {
    private Integer id;
    private Double solde;
    private String owner;
    private String rib;

    public Compte(Integer id, Double solde, String owner, String rib) {
        this.id = id;
        this.solde = solde;
        this.owner = owner;
        this.rib = rib;
    }

    public Compte() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", solde=" + solde +
                ", owner='" + owner + '\'' +
                ", rib='" + rib + '\'' +
                '}';
    }
}
