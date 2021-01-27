package client;



import static common.Configuration.CLIENT_DEFAULT_FOLDER;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client {
    private ClientInterfaceImpl clientImpl;


    Client() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            clientImpl = new ClientInterfaceImpl(registry);
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


 
    String[] listFiles() throws RemoteException {
        return clientImpl.serverInterface.listFiles();
    }



    boolean downloadFromServer(String fileName) throws RemoteException {
        byte[] buffer = clientImpl.serverInterface.downloadFile(fileName);
        if(buffer.length == 0)
            return false;

        File file = new File(CLIENT_DEFAULT_FOLDER + File.separator + fileName);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            bufferedOutputStream.write(buffer, 0, buffer.length);
            return file.length() == buffer.length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

 
    boolean uploadToServer(File file) throws RemoteException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[(int) file.length()];
            int read = bufferedInputStream.read(buffer, 0, buffer.length);
            return clientImpl.serverInterface.uploadFile(buffer, file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



}
