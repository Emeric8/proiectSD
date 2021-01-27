package common;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerInterface extends Remote {

  
    boolean uploadFile(byte[] buffer, String fileName) throws RemoteException;
   
    byte[] downloadFile(String fileName) throws RemoteException;


  
    String[] listFiles() throws RemoteException;

    
}
