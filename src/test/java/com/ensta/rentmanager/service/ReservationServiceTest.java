package com.ensta.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;


    @Test
    void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        when(this.reservationDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> reservationService.findAll());
    }

    @Test
    void findAll_should_return_reservations_list() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        when(reservationDao.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationService.findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void create_should_fail_when_dao_throws_exception() throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        Reservation reservation = new Reservation(0,0,0,debut,fin);
        when(this.reservationDao.create(reservation)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }
    @Test
    void create_should_return_a_reservation_id() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-05-04", formatter);
        Reservation reservation = new Reservation(0,0,0,debut,fin);
        when(reservationDao.create(reservation)).thenReturn(reservation.getId());
        long result = reservationService.create(reservation);
        assertNotNull(result);
    }
    @Test
    void create_should_throw_service_exception_because_of_period_of_days_too_long() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-03", formatter);
        Reservation reservation = new Reservation(0,0,0,debut,fin);
        assertThrows(ServiceException.class, () -> {
            reservationService.create(reservation);
        });
    }

    @Test
    void delete_should_fail_when_dao_throws_exception() throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        Reservation reservation = new Reservation(0,0,0,debut,fin);
        when(this.reservationDao.delete(reservation.getId())).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> reservationService.delete(reservation.getId()));
    }
    @Test
    void delete_should_return_a_reservation_id() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        Reservation reservation = new Reservation(0,0,0,debut,fin);
        when(reservationDao.delete(reservation.getId())).thenReturn(reservation.getId());
        long result = reservationService.delete(reservation.getId());
        assertNotNull(result);
    }
    @Test
    void findVehicleById_should_fail_when_dao_throws_exception() throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        when(this.reservationDao.findResaByVehicleId(0)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> reservationService.findByVehicleId(0));
    }

    @Test
    void findByVehiculeId_should_return_reservations_list() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        List<Reservation> reservationsOk = Arrays.asList(
                new Reservation(0,0,0,debut,fin)
        );

        when(reservationDao.findResaByVehicleId(0)).thenReturn(reservationsOk);
        List<Reservation> result = reservationService.findByVehicleId(0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void findClientById_should_fail_when_dao_throws_exception() throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        when(this.reservationDao.findResaByClientId(0)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> reservationService.findByClientId(0));
    }

    @Test
    void findByClientId_should_return_reservations_list() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        List<Reservation> reservationsOk = Arrays.asList(
                new Reservation(0,0,0,debut,fin)
        );

        when(reservationDao.findResaByClientId(0)).thenReturn(reservationsOk);
        List<Reservation> result = reservationService.findByClientId(0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void count_should_fail_when_dao_throws_exception() throws DaoException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        when(this.reservationDao.count()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> reservationService.count());
    }
    @Test
    void count_should_return_a_long() throws DaoException, ServiceException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate debut = LocalDate.parse("2024-05-02", formatter);
        LocalDate fin = LocalDate.parse("2024-06-02", formatter);
        List<Reservation> reservations = Arrays.asList(
                new Reservation(0,0,0,debut,fin),
                new Reservation(1,1,1,debut,fin)
        );
        when(reservationDao.count()).thenReturn((long) reservations.size());
        long result = reservationService.count();
        assertNotNull(result);
    }
}