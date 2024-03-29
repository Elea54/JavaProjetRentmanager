package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private ReservationDao reservationDao;

    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        when(this.vehicleDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> vehicleService.findAll());
    }

    @Test
    void findAll_should_return_vehicules_list() throws DaoException, ServiceException {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(0, "Peugeot", "206+", 5),
                new Vehicle(1, "Renault", "Clio Campus", 5)
        );
        when(vehicleDao.findAll()).thenReturn(vehicles);
        List<Vehicle> result = vehicleService.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void create_should_fail_when_dao_throws_exception() throws DaoException {
        Vehicle vehicle = new Vehicle(0, "Peugeot", "206+", 5);
        when(this.vehicleDao.create(vehicle)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> vehicleService.create(vehicle));
    }
    @Test
    void create_should_return_a_vehicle_id() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle(0, "Peugeot", "206+", 5);
        when(vehicleDao.create(vehicle)).thenReturn(vehicle.getId());
        long result = vehicleService.create(vehicle);
        assertNotNull(result);
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        Vehicle vehicle = new Vehicle(0, "Peugeot", "206+", 5);
        when(this.vehicleDao.delete(vehicle)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> vehicleService.delete(vehicle));
    }
    @Test
    void delete_should_return_a_vehicle_id() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle(0, "Peugeot", "206+", 5);
        when(vehicleDao.delete(vehicle)).thenReturn(vehicle.getId());
        long result = vehicleService.delete(vehicle);
        assertNotNull(result);
    }
    @Test
    void findById_should_fail_when_dao_throws_exception() throws DaoException {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(0, "Peugeot", "206+", 5),
                new Vehicle(1, "Renault", "Clio Campus", 5)
        );
        when(this.vehicleDao.findById(0)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> vehicleService.findById(0));
    }

    @Test
    void findById_should_return_client() throws DaoException, ServiceException {
        Vehicle vehicle = new Vehicle(0, "Peugeot", "206+", 5);
        List<Vehicle> vehicles = Arrays.asList(
                vehicle,
                new Vehicle(1, "Renault", "Clio Campus", 5)
        );
        when(vehicleDao.findById(0)).thenReturn(vehicle);
        Vehicle result = vehicleService.findById(0);
        assertNotNull(result);
        assertFalse(result == null);
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {

        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(0, "Peugeot", "206+", 5),
                new Vehicle(1, "Renault", "Clio Campus", 5)
        );
        when(this.vehicleDao.count()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> vehicleService.count());
    }
    @Test
    void count_should_return_a_long() throws DaoException, ServiceException {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(0, "Peugeot", "206+", 5),
                new Vehicle(1, "Renault", "Clio Campus", 5)
        );
        when(vehicleDao.count()).thenReturn((long) vehicles.size());
        long result = vehicleService.count();
        assertNotNull(result);
    }

}