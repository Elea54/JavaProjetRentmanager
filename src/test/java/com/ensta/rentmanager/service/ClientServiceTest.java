package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}