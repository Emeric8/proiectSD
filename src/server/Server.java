
package server;

import static common.Configuration.PORT;
import static common.Configuration.SERVER_DEFAULT_FOLDER;
import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Server {
   
    public static void main(String[] args) {
        try {
       
            File defaultFolder = new File(SERVER_DEFAULT_FOLDER);
            if (!defaultFolder.exists()) {
                boolean b = defaultFolder.mkdirs();
                System.err.println(b ? "Default folder created successfully!" : "Failure creating default folder");
            }
            ServerInterfaceImpl server = new ServerInterfaceImpl();
     
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind("server", server);
            System.out.println("Running server on port " + PORT);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
