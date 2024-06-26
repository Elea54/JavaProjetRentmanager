package com.epf.rentmanager.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	public long create(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY)){
			ps.setString(1, client.getNom());
			ps.setString(2,client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.execute();

		}catch (SQLException e){
			System.out.println(e.getMessage());
			throw new DaoException();
		}
		return client.getId();
	}

	public long delete(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);){
			ps.setLong(1, client.getId());
			ps.executeUpdate();
		}catch (SQLException e){
			throw new DaoException();
		}
		return client.getId();
	}

	public Client findById(long id) throws DaoException {
		Client client = new Client();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY)){
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				client.setId(id);
				client.setNom(resultSet.getString(1).toUpperCase(Locale.ROOT));
				client.setPrenom(resultSet.getString(2));
				client.setEmail(resultSet.getString(3));
				client.setNaissance(resultSet.getDate(4).toLocalDate());
			}
		}catch (SQLException e){
			throw new DaoException();
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> listOfAllClients = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);){
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Client client = new Client();
				client.setId(resultSet.getInt(1));
				client.setNom(resultSet.getString(2));
				client.setPrenom(resultSet.getString(3));
				client.setEmail(resultSet.getString(4));
				client.setNaissance(resultSet.getDate(5).toLocalDate());
				listOfAllClients.add(client);
			}
		}catch(SQLException e){
			throw new DaoException();
		}
		return listOfAllClients;
	}


	public long count() throws DaoException {
		long nbrClients = 0;
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_CLIENTS_QUERY);){
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				nbrClients = resultSet.getLong("count");
			}
		}catch(SQLException e){
			throw new DaoException();
		}
		return nbrClients;
	}
}
