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
	private ReservationDao reservationDao;
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
		else if (!vehicle.getNb_places()){
			throw new ServiceException("le nombre de places est inférieur à 1");
		}
		try{
			vehicleDao.create(vehicle);
		}catch (DaoException e){
			throw new ServiceException("Une erreur a eu lieu lors de la creation du véhicule.");
		}
		return vehicle.getId();
	}

	public long delete(Vehicle vehicle) throws ServiceException{
		try{
			vehicleDao.delete(vehicle);
//			List<Reservation> reservationsByVehicleId = reservationDao.findResaByVehicleId(vehicle.getId());
//			reservationsByVehicleId.forEach(
//					reservation -> {
//						try {
//							reservationDao.delete(reservation);
//						} catch (DaoException e) {
//							throw new RuntimeException(e);
//						}
//					}
//			);
		}catch (DaoException e){
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du véhicule.");
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
			throw new ServiceException("Une erreur a eu lieu pendant la récupération du véhicule.");
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
			throw new ServiceException("Une erreur a eu lieu pednant la récupération de la liste de véhicules.");
		}
	}
}
