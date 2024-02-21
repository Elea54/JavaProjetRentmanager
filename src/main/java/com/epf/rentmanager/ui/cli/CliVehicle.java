package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.util.List;
import java.util.Scanner;

public class CliVehicle {
    public CliVehicle() {
    }
    VehicleService vehicleService = VehicleService.getInstance();

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
        Vehicle vehicle = new Vehicle(0, nameConstructor, vehicleModel, true);
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
            System.out.println("Une erreur a eu lieu lors de la suppression du véhicule.");
        }
    }

}
