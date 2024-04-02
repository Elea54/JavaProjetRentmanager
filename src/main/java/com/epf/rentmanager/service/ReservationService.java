package com.epf.rentmanager.service;


import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;

import java.time.Period;
import java.util.List;

@Service
public class ReservationService {

	private ReservationDao reservationDao;
	public ReservationService(ReservationDao reservationDao){
		this.reservationDao = reservationDao;
	}

	
	public long create(Reservation reservation) throws ServiceException {
		List<Reservation> reservationsWithVehicule = this.findByVehicleId(reservation.getVehicle_id());
		boolean isSameDay = false;
		Period resaPeriode = Period.between(reservation.getDebut(), reservation.getFin());

		for (Reservation reservationSelectionnee : reservationsWithVehicule){
			if(reservation.getDebut().isAfter(reservationSelectionnee.getFin()) || reservation.getFin().isBefore(reservationSelectionnee.getDebut())){
				isSameDay = false;
			}else {
				isSameDay = true;
				break;
			}
		}
		reservationsWithVehicule.add(reservation);
		Collections.sort(reservationsWithVehicule, Comparator.comparing(Reservation::getDebut));
		long nbrDeJour = Period.between(reservationsWithVehicule.get(0).getDebut(), reservationsWithVehicule.get(0).getFin()).getDays();
		for (int i = 1; i < reservationsWithVehicule.size(); i++) {
			if(reservationsWithVehicule.get(i-1).getFin().plusDays(1).isEqual(reservationsWithVehicule.get(i).getDebut())){
				Period period = Period.between(reservationsWithVehicule.get(i).getDebut(),reservationsWithVehicule.get(i).getFin());
				nbrDeJour += period.getDays();
			}
			else{
				nbrDeJour = 0;
			}
		}


		if (isSameDay){
			throw new ServiceException("Les périodes se superposent.");
		}
		else if(reservation.getDebut().isAfter(reservation.getFin())){
			throw new ServiceException("La date de fin est avant la date de début.");
		}
		else if(resaPeriode.getDays() >= 7 || resaPeriode.getMonths() != 0 || resaPeriode.getYears() != 0){
			throw new ServiceException("La période dépasse sept jours.");
		} else if (nbrDeJour > 30) {
			throw  new ServiceException("le véhicule est loué plus de 30 jours consécutifs");
		} else{
			try{
				reservationDao.create(reservation);
			}catch (DaoException e){
				throw new ServiceException(e.getMessage());
			}
			return reservation.getId();
		}
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

	public long count() throws ServiceException {
		long nbrReservations;
		try{
			nbrReservations = reservationDao.count();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return nbrReservations;
	}
}
