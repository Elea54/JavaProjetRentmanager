package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;

public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationDao reservationDao = ReservationDao.getInstance();
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		if(vehicle.getConstructeur() == null){
			throw new ServiceException("Le constructeur du véhicule est nul.");
		}
		else if (vehicle.getNb_places()<1){
			throw new ServiceException("le nombre de places est inférieur à 1");
		}
		try{
			vehicleDao.create(vehicle);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return vehicle.getId();
	}

	public long delete(Vehicle vehicle) throws ServiceException{
		try{
			List<Reservation> reservationsByVehicleId = reservationDao.findResaByVehicleId(vehicle.getId());
			reservationsByVehicleId.forEach(
					reservation -> {
						try {
							reservationDao.delete(reservation.getId());
						} catch (DaoException e) {
							throw new RuntimeException(e);
						}
					}
			);
			vehicleDao.delete(vehicle);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return vehicle.getId();
	}

	public Vehicle findById(long id) throws ServiceException {
		try{
			Vehicle vehicle = vehicleDao.findById(id);
			if(vehicle != null){
				return vehicle;
			}
			throw new ServiceException("Le véhicule n°" + id + " n'a pas éré trouvé dans la db.");
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			List<Vehicle> listOfAllVehicules = vehicleDao.findAll();
			if(listOfAllVehicules != null){
				return listOfAllVehicules;
			}

			throw new ServiceException("Il n'y a pas de véhicule dans la db.");
		} catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long count() throws ServiceException {
		long nbrVehicle;
		try{
			nbrVehicle = vehicleDao.count();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return nbrVehicle;
	}
}
