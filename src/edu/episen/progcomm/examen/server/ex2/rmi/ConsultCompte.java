package edu.episen.progcomm.examen.server.ex2.rmi;

import edu.episen.progcomm.examen.server.shared.models.Compte;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConsultCompte extends Remote {
    Compte getAccount(Integer id) throws RemoteException;
}
