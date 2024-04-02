package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationDao reservationDao;
	
	private VehicleService(VehicleDao vehicleDao, ReservationDao reservationDao) {
		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		if(vehicle.getConstructeur() == "" || vehicle.getConstructeur() == null){
			throw new ServiceException("Le constructeur du véhicule est nul.");
		} else if (vehicle.getModele() == "" || vehicle.getModele() == null) {
			throw new ServiceException("Le modèle du véhicule est nul.");
		} else if (vehicle.getNb_places() < 2 || vehicle.getNb_places() > 9){
			throw new ServiceException("le nombre de places est inférieur à 2 ou supérieur à 9");
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
