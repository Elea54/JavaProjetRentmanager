package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Mock
    private ReservationDao reservationDao;

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        when(this.clientDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    void findAll_should_return_clients_list() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        List<Client> clients = Arrays.asList(
                new Client(1,"Nom","Prénom","email@gmail.com",date),
                new Client(2,"Nom2","Prénom2","email@gmail.com",date)
        );
        when(clientDao.findAll()).thenReturn(clients);
        List<Client> result = clientService.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void create_should_fail_when_dao_throws_exception() throws DaoException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        Client client = new Client(1,"Nom","Prénom","email@gmail.com",date);
        when(this.clientDao.create(client)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.create(client));
    }
    @Test
    void create_should_return_a_client_id() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        Client client = new Client(1,"Nom","Prénom","email@gmail.com",date);
        when(clientDao.create(client)).thenReturn(client.getId());
        long result = clientService.create(client);
        assertNotNull(result);
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        Client client = new Client(1,"Nom","Prénom","email@gmail.com",date);
        when(this.clientDao.delete(client)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.delete(client));
    }
    @Test
    void delete_should_return_a_client_id() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        Client client = new Client(1,"Nom","Prénom","email@gmail.com",date);
        when(clientDao.delete(client)).thenReturn(client.getId());
        long result = clientService.delete(client);
        assertNotNull(result);
    }
    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        List<Client> clients = Arrays.asList(
                new Client(1,"Nom","Prénom","email@gmail.com",date),
                new Client(2,"Nom2","Prénom2","email@gmail.com",date)
        );
        when(this.clientDao.findById(0)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.findById(0));
    }

    @Test
    void findById_should_return_client() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        Client client = new Client(1,"Nom","Prénom","email@gmail.com",date);
        List<Client> clients = Arrays.asList(
                client,
                new Client(2,"Nom2","Prénom2","email@gmail.com",date)
        );
        when(clientDao.findById(0)).thenReturn(client);
        Client result = clientService.findById(0);
        assertNotNull(result);
        assertFalse(result == null);
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        List<Client> clients = Arrays.asList(
                new Client(1,"Nom","Prénom","email@gmail.com",date),
                new Client(2,"Nom2","Prénom2","email@gmail.com",date)
        );
        when(this.clientDao.count()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> clientService.count());
    }
    @Test
    void count_should_return_a_long() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2002-05-02", formatter);
        List<Client> clients = Arrays.asList(
                new Client(1,"Nom","Prénom","email@gmail.com",date),
                new Client(2,"Nom2","Prénom2","email@gmail.com",date)
        );

        when(clientDao.count()).thenReturn((long) clients.size());
        long result = clientService.count();
        assertNotNull(result);
    }

}