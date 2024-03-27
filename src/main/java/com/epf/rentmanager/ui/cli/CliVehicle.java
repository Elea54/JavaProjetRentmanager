package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class CliVehicle {
    public CliVehicle() {
    }
    ApplicationContext context = new
            AnnotationConfigApplicationContext(AppConfiguration.class);
    VehicleService vehicleService = context.getBean(VehicleService.class);

    public void listAllVehicles() throws ServiceException {
        List<Vehicle> allVehiculesList = vehicleService.findAll();
        System.out.println("Nombre total de véhicules :" + allVehiculesList.size());
        allVehiculesList.forEach(System.out::println);
    }

    public void createVehicle(Scanner scanner) throws ServiceException {
        System.out.println("Nom du constructeur : ");
        String nameConstructor = scanner.nextLine();
        while(nameConstructor.isEmpty()){
            System.out.println("Le nom entré est nul. Réessayer.");
            nameConstructor = scanner.nextLine();
        }
        System.out.println("Modèle de la voiture");
        String vehicleModel = scanner.nextLine();
        while(vehicleModel.isEmpty()){
            System.out.println("Le modèle entré est nul. Réessayer.");
            vehicleModel = scanner.nextLine();
        }
        System.out.println("Nombre de places :");
        int nbrPlaces = 0;
        boolean idIsValid = false;
        while (!idIsValid) {
            try {
                String input = scanner.nextLine();
                nbrPlaces = Integer.parseInt(input);
                idIsValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Entrée invalide. Veuillez saisir un nombre entier long.");
            }
        }
        Vehicle vehicle = new Vehicle(0, nameConstructor, vehicleModel, nbrPlaces);
        vehicleService.create(vehicle);
        System.out.println("Le vehicule a été créé.");
    }

    public void deleteVehicle(Scanner scanner) throws ServiceException {
        this.listAllVehicles();
        System.out.println("\n\nQuel est l'id du véhicule à supprimer ?");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Vehicle vehicle = vehicleService.findById(id);
            vehicleService.delete(vehicle);
            System.out.println("Le véhicule a bien été supprimé.");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
