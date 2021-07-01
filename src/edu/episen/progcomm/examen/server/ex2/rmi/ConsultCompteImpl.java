package edu.episen.progcomm.examen.server.ex2.rmi;

import edu.episen.progcomm.examen.server.shared.models.Compte;
import edu.episen.progcomm.examen.server.shared.services.CompteService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConsultCompteImpl extends UnicastRemoteObject implements ConsultCompte {
    private final CompteService service;

    public ConsultCompteImpl() throws RemoteException {
        service = CompteService.Instance;
    }

    @Override
    public Compte getAccount(Integer id) {
        return service.getAccoutById(id);
    }
}
