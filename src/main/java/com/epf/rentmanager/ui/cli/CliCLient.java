package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CliCLient {
    public CliCLient() {
    }
   ClientService clientService = ClientService.getInstance();
    public void listAllClients() throws ServiceException {
        List<Client> allClientsList= clientService.findAll();
        System.out.println("Nombre total de clients : "+ allClientsList.size());
        allClientsList.forEach(System.out::println);
    }

    public void createClient(Scanner scanner) throws ServiceException {
        System.out.println("Nom du client : ");
        String nom = scanner.nextLine();
        while(nom.isEmpty()){
            System.out.println("Le nom entré est nul. Réessayer.");
            nom = scanner.nextLine();
        }
        System.out.println("Prénom du client");
        String prenom = scanner.nextLine();
        while(prenom.isEmpty()){
            System.out.println("Le prénom entré est nul. Réessayer.");
            prenom = scanner.nextLine();
        }
        System.out.println("E-mail du client");
        String email = scanner.nextLine();
        while(!email.contains("@")){
            System.out.println("L'e-mail n'est pas conforme. Réessayer.");
            email = scanner.nextLine();
        }
        System.out.println("Date de naissance du client : (format yyyy-MM-dd");
        String dateNaissance = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateNaissance, formatter);
            Client client = new Client(0, nom, prenom,email,date);
            clientService.create(client);
            System.out.println("Le client a bien été créé.");
        }catch(DateTimeException e){
            System.out.println("Le système n'est pas au bon format yyyy-MM-dd). Le client n'a pas été créé.");
        }
    }

    public void deleteClient(Scanner scanner) throws ServiceException {
        this.listAllClients();
        System.out.println("\n\nQuel est l'id du client à supprimer ?");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Client client = clientService.findById(id);
            clientService.delete(client);
            System.out.println("Le client a bien été supprimé.");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
