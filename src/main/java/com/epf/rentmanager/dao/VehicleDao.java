package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY)){
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.execute();

		}catch (SQLException e){
			throw new DaoException();
		}
		return vehicle.getId();

	}

	public long delete(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);){
			ps.setLong(1,vehicle.getId());
			ps.executeUpdate();

		}catch(SQLException e){
			throw new DaoException();
		}
		return vehicle.getId();
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicle = new Vehicle();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY)){
			ps.setLong(1, id);
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				vehicle.setId(id);
				vehicle.setConstructeur(resultSet.getString(2));
				vehicle.setModele(resultSet.getString(3));
				vehicle.setNb_places(resultSet.getInt(4));
			}

		}catch (SQLException e){
			throw new DaoException();
		}
		return vehicle;
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> listOfAllVehicules = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY)){
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()){
				Vehicle vehicle = new Vehicle();
				vehicle.setId(resultSet.getLong(1));
				vehicle.setConstructeur(resultSet.getString(2));
				vehicle.setModele(resultSet.getString(3));
				vehicle.setNb_places(resultSet.getInt(4));
				listOfAllVehicules.add(vehicle);
			}

		}catch(SQLException e){
			throw new DaoException();
		}
		return listOfAllVehicules;
	}

	public long count() throws DaoException {
		long nbrVehicle = 0;
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_QUERY);){
			ResultSet resultSet = ps.executeQuery();
			if(resultSet.next()){
				nbrVehicle = resultSet.getLong("count");
			}
		}catch(SQLException e){
			throw new DaoException();
		}
		return nbrVehicle;
	}


}
