package com.epf.rentmanager.service;


import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ReservationDao reservationDao = ReservationDao.getInstance();
	public static ClientService instance;
	private ReservationService reservationService = ReservationService.getInstance();

	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}
	
	public long create(Client client) throws ServiceException {
		if(client.getNom()==null || client.getPrenom() == null){
			throw new ServiceException("Le nom ou le prenom du client est nul.");
		}
		try{
			clientDao.create(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return client.getId();
	}
	public long delete(Client client) throws ServiceException{
		try{
			List<Reservation> reservationsByClientId = reservationService.findByClientId(client.getId());
			reservationsByClientId.forEach(
					reservation -> {
						try {
							reservationDao.delete(reservation.getId());
						} catch (DaoException e) {
							throw new RuntimeException(e);
						}
					}
				);
			clientDao.delete(client);
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return client.getId();
	}
	public Client findById(long id) throws ServiceException {
		try{
			Client client = clientDao.findById(id);

			if(client != null){
				return client;
			}
			throw new ServiceException("Le client n°" + id + " n'a pas éré trouvé dans la db.");
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			List<Client> listOfAllClients = clientDao.findAll();
			if(listOfAllClients != null){
				return listOfAllClients;
			}

			throw new ServiceException("Il n'y a pas de client dans la db.");
		} catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long count() throws ServiceException {
		long nbrClient;
		try{
			nbrClient = clientDao.count();
		}catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return nbrClient;
	}
}
