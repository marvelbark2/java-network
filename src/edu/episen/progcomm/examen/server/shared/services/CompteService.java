package edu.episen.progcomm.examen.server.shared.services;

import edu.episen.progcomm.examen.server.shared.models.Compte;

import java.util.ArrayList;
import java.util.List;

public enum CompteService {
    Instance;
    private List<Compte>  comptes;

    CompteService() {
        comptes = new ArrayList<>();
        Compte c1 = new Compte(1, 250.04, "Marco", "FR34223114552");
        Compte c2 = new Compte(2, 350.04, "Leo", "FR34223114552");
        Compte c3 = new Compte(3, 450.04, "Liz", "FR34223114552");
        Compte c4 = new Compte(4, 550.04, "Steph", "FR34223114552");

        comptes.addAll(List.of(c1, c2, c3, c4));
    }

    public Compte getAccoutById(int id) {
        return comptes.stream().filter(compte -> compte.getId() == id).findFirst().orElse(null);
    }

    public Compte changeRibAccount(int id, String rib) {
        Compte compte = getAccoutById(id);
        compte.setRib(rib);
        return compte;
    }
}
