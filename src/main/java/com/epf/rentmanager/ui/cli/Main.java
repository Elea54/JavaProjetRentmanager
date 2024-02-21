package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exeptions.ServiceException;

import java.util.Scanner;

public class Main {
    //TODO : voir pourquoi la colonne model de voiture n'existe pas dans la database. La rajouter ?
    //TODO : voir une solution pour assurer que les id sont bien des long et les retaper sinon
    //TODO : supprimer une réservation lorsqu'un client ou un véhicule est supprimé

    public static void main(String[] args) throws ServiceException {
        boolean cliIsRunning = true;
        Scanner scanner = new Scanner(System.in);
        CliCLient cliCLient = new CliCLient();
        CliVehicle cliVehicle = new CliVehicle();
        CliReservation cliReservation = new CliReservation();
        String customerLine = choixDuClient(scanner);

        while(cliIsRunning){
            if (customerLine.equals("0")){
                cliIsRunning = false;
            }
            else if(customerLine.equals("1")){
                cliCLient.createClient(scanner);
                customerLine = choixDuClient(scanner);
            }
            else if (customerLine.equals("2")) {
                cliCLient.listAllClients();
                customerLine = choixDuClient(scanner);
            }
            else if (customerLine.equals("3")){
                cliCLient.deleteClient(scanner);
                customerLine = choixDuClient(scanner);
            }
            else if (customerLine.equals("4")){
                cliVehicle.createVehicle(scanner);
                customerLine = choixDuClient(scanner);

            }
            else if(customerLine.equals("5")){
                cliVehicle.listAllVehicles();
                customerLine = choixDuClient(scanner);
            }
            else if(customerLine.equals("6")){
                cliVehicle.deleteVehicle(scanner);
                customerLine = choixDuClient(scanner);
            }
            else if(customerLine.equals("7")){
                cliReservation.createReservation(scanner);
                customerLine = choixDuClient(scanner);
            }
            else if(customerLine.equals("8")){
                cliReservation.findReservations(scanner);
                customerLine = choixDuClient(scanner);
            }
            else if(customerLine.equals("9")){
                cliReservation.deleteReservation(scanner);
                customerLine = choixDuClient(scanner);
            }
            else{
                System.out.println("Ce n'est pas une option. Recommencer :");
                customerLine = choixDuClient(scanner);
            }
        }
    }
    public static String choixDuClient(Scanner scanner){
        System.out.println("\nQuitter : 0\nCréer un client : 1\nLister les clients : 2\nSupprimer un client : 3" +
                "\nCréer une voiture : 4\nLister les voitures : 5\nSupprimer une voiture : 6" +
                "\nCréer une réservation : 7\nChercher des réservations : 8\nSupprimer une réservation : 9");
        return scanner.nextLine();
    }
}