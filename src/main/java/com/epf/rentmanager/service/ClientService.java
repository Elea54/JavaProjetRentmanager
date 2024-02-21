package com.epf.rentmanager.service;


import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

public class ClientService {

	private ClientDao clientDao;
	private ReservationDao reservationDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		if(client.getNom()==null || client.getPrenom() == null){
			throw new ServiceException("Le nom ou le prenom du client est nul.");
		}
		try{
			clientDao.create(client);
		}catch (DaoException e){
			throw new ServiceException("Une erreur a eu lieu lors de la creation du client.");
		}
		return client.getId();
	}

	public long delete(Client client) throws ServiceException{
		try{
			clientDao.delete(client);
//			List<Reservation> reservationsByClientId = reservationDao.findResaByClientId(client.getId());
//			reservationsByClientId.forEach(
//					reservation -> {
//                        try {
//                            reservationDao.delete(reservation);
//                        } catch (DaoException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//			);
		}catch (DaoException e){
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du client.");
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
			throw new ServiceException("Une erreur a eu lieu pendant la récupération du client.");
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
			throw new ServiceException("Une erreur a eu lieu pednant la récupération de la liste de clients.");
		}
	}
}