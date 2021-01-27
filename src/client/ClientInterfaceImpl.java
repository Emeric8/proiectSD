package client;


import common.ClientInterface;
import common.ServerInterface;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface {


    ServerInterface serverInterface;

  
    ClientInterfaceImpl(Registry registry) throws RemoteException, NotBoundException {
        serverInterface = (ServerInterface) registry.lookup("server");
    }

 
    @Override
    public void notifyClient(String message) throws RemoteException {
        System.err.println(message);
    }
}
