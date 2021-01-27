package server;


import common.ClientInterface;
import static common.Configuration.SERVER_DEFAULT_FOLDER;
import common.ServerInterface;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServerInterfaceImpl extends UnicastRemoteObject implements ServerInterface {



    ServerInterfaceImpl() throws RemoteException {
       
    }

    public byte[] downloadFile(String fileName) throws RemoteException {
        File file = new File(SERVER_DEFAULT_FOLDER + File.separator + fileName);
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buffer = new byte[(int) file.length()];
            int read = bufferedInputStream.read(buffer, 0, buffer.length);
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public boolean uploadFile(byte[] buffer, String fileName) throws RemoteException {
        File file = new File(SERVER_DEFAULT_FOLDER + File.separator + fileName);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
       
    }


    

    public String[] listFiles() throws RemoteException {
        File defaultFolder = new File(SERVER_DEFAULT_FOLDER);
        return defaultFolder.list();
    }


}
