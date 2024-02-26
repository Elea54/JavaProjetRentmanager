package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CliReservation {

    public CliReservation() {
    }
    private CliCLient cliCLient = new CliCLient();
    private CliVehicle cliVehicle = new CliVehicle();
    private ReservationService reservationService = ReservationService.getInstance();
    public void createReservation(Scanner scanner) throws ServiceException {
        System.out.println("\nListe des clients :");
        cliCLient.listAllClients();
        System.out.println("\nListe des voitures :");
        cliVehicle.listAllVehicles();
        System.out.println("Id du client : ");
        long idClient = Long.parseLong(scanner.nextLine());
        System.out.println("Id du véhicule : ");
        long idVehicle = Long.parseLong(scanner.nextLine());
        System.out.println("Date de début de réservation : (format yyyy-MM-dd)");
        String dateDebut = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse(dateDebut, formatter);
        System.out.println("Date de fin de réservation : (format yyyy-MM-dd");
        String dateFin = scanner.nextLine();
        LocalDate fin = LocalDate.parse(dateFin, formatter);
        Reservation reservation = new Reservation(0, idClient, idVehicle, debut, fin);
        reservationService.create(reservation);
    }

    public void findReservations(Scanner scanner) throws ServiceException {
        System.out.println("\nRecherche de réservations :\nLister tout : 1\nPar client : 2\nPar véhicule : 3");
        String choixClient = scanner.nextLine();
        if(choixClient.equals("1")){
            listAllReservations();
        } else if (choixClient.equals("2")) {
            listReservationsByClientId(scanner);
        } else if (choixClient.equals("3")) {
            listReservationsByVehicleId(scanner);
        }else{
            System.out.println("Ce n'est pas une option. Recommencer :");
            choixClient = scanner.nextLine();
        }
    }

    private void listReservationsByClientId(Scanner scanner) throws ServiceException {
        boolean idIsValid = false;
        long idClient = 0;
        System.out.println("Id du client : ");
        while (!idIsValid){
            try {
                idClient = Long.parseLong(scanner.nextLine());
                idIsValid = true;
            }catch (Exception e){
                System.out.println("Erreur : Entrée invalide. Veuillez saisir un nombre entier long.");
            }
        }
        List<Reservation> reservationsByClientIdList = reservationService.findByClientId(idClient);
        reservationsByClientIdList.forEach(System.out::println);
    }

    private void listReservationsByVehicleId(Scanner scanner) throws ServiceException {
        boolean idIsValid = false;
        long idVehicle =0;
        System.out.println("Id du vehicule : ");
        while (!idIsValid){
            try{
                String input = scanner.nextLine();
                idVehicle = Long.parseLong(input);
                idIsValid = true;
            }catch (NumberFormatException e) {
                System.out.println("Erreur : Entrée invalide. Veuillez saisir un nombre entier long.");
            }
        }
        List<Reservation> reservationsByVehicleIdList = reservationService.findByVehicleId(idVehicle);
        reservationsByVehicleIdList.forEach(System.out::println);
    }

    private void listAllReservations() throws ServiceException {
        List<Reservation> allReservationsList = reservationService.findAll();
        allReservationsList.forEach(System.out::println);
    }

    public void deleteReservation(Scanner scanner) throws ServiceException {
        listAllReservations();
        System.out.println("Id de la réservation a supprimer :");
        try {
            long reservationId = Long.parseLong(scanner.nextLine());
            reservationService.delete(reservationId);
            System.out.println("La réservation a bien été supprimée.");
        }catch(Exception e){
            System.out.println("Une erreur a eu lieu lors de la suppression de la réservation.");
        }
    }
}

