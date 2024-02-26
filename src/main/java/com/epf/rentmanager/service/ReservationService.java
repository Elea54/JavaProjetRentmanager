package com.epf.rentmanager.service;


import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {

	private ReservationDao reservationDao;
	public static ReservationService instance;

	private ReservationService() {
		this.reservationDao = ReservationDao.getInstance();
	}
	
	public static ReservationService getInstance() {
		if (instance == null) {
			instance = new ReservationService();
		}
		
		return instance;
	}
	
	
	public long create(Reservation reservation) throws ServiceException {
		try{
			reservationDao.create(reservation);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return reservation.getId();
	}

	public long delete(long reservationId) throws ServiceException{
		try{
			reservationDao.delete(reservationId);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return reservationId;
	}

	public List<Reservation> findByClientId(long clientId) throws ServiceException {
		try{
			List<Reservation> reservationsByClientId = reservationDao.findResaByClientId(clientId);

			if(reservationsByClientId != null){
				return reservationsByClientId;
			}
			throw new ServiceException("Aucune reservation trouvée pour le client n°" + clientId + ".");
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Reservation> findByVehicleId(long vehicleId) throws ServiceException {
		try{
			List<Reservation> reservationsByVehicleId = reservationDao.findResaByVehicleId(vehicleId);

			if(reservationsByVehicleId != null){
				return reservationsByVehicleId;
			}
			throw new ServiceException("Aucune reservation trouvée pour le véhicule n°" + vehicleId + ".");
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Reservation> findAll() throws ServiceException {
		try{
			List<Reservation> listOfAllReservations = reservationDao.findAll();
			if(listOfAllReservations != null){
				return listOfAllReservations;
			}

			throw new ServiceException("Il n'y a pas de réservation dans la db.");
		} catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}
}
