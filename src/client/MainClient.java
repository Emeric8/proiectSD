package client;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;


public class MainClient {
   
    Client client;

    private Scanner scanner;

    private String[] files;


    public MainClient() {
        client = new Client();
        scanner = new Scanner(System.in);
    }


    public void mainMenu() {
        int choice;
        do {
            System.out.println("1 - Upload\n2 - Download\n3 - List files\n4 - Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    uploadSection();
                    break;
                case 2:
                    downloadSection();
                    break;
                case 3:
                    listFilesSection();
                    break;
                case 4:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    private void listFilesSection() {
        try {
            files = client.listFiles();
            for (int i = 0, filesLength = files.length; i < filesLength; i++) {
                String file = files[i];
                System.out.printf("File [%d]: %s%n", i, file);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void downloadSection() {
        listFilesSection();
        System.out.println("Select a file to download From Server: ");
        int choice = scanner.nextInt();
        try {
            boolean result = client.downloadFromServer(files[choice]);
           
            System.out.println(!result ? "File downloaded successfully!" : "File download failed!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void uploadSection() {
        System.out.println("Enter a file path for upload To Server: ");
        scanner.nextLine();
        String path = scanner.nextLine();
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("The file doesn't exist!");
            return;
        }
        try {
            boolean result = client.uploadToServer(file);
           System.out.println(!result ? "File uploaded successfully!" : "File upload failed!");
               
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        MainClient m = new MainClient();
        m.mainMenu();
    }
}
