package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exeptions.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {
	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY)){
			ps.setLong(1, reservation.getClient_id());
			ps.setLong(2,reservation.getVehicle_id());
			ps.setDate(3, Date.valueOf(reservation.getDebut()));
			ps.setDate(4, Date.valueOf(reservation.getFin()));
			ps.execute();

		}catch (SQLException e){
			throw new DaoException();
		}
		return reservation.getId();
	}
	
	public long delete(long reservationId) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)){
			ps.setLong(1, reservationId);
			ps.executeUpdate();

		}catch (SQLException e){
			throw new DaoException();
		}
		return reservationId;
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		ArrayList<Reservation> reservationsByClient = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)){
			ps.setLong(1, clientId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setClient_id(clientId);
				reservation.setId(resultSet.getLong(1));
				reservation.setVehicle_id(resultSet.getLong(2));
				reservation.setDebut(resultSet.getDate(3).toLocalDate());
				reservation.setFin(resultSet.getDate(4).toLocalDate());
				reservationsByClient.add(reservation);
			}

		}catch (SQLException e){
			throw new DaoException();
		}
		return reservationsByClient;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		ArrayList<Reservation> reservationsByVehicle = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)){
			ps.setLong(1, vehicleId);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setVehicle_id(vehicleId);
				reservation.setId(resultSet.getLong(1));
				reservation.setClient_id(resultSet.getLong(2));
				reservation.setDebut(resultSet.getDate(3).toLocalDate());
				reservation.setFin(resultSet.getDate(4).toLocalDate());
				reservationsByVehicle.add(reservation);
			}

		}catch (SQLException e){
			throw new DaoException();
		}
		return reservationsByVehicle;
	}

	public List<Reservation> findAll() throws DaoException {
		ArrayList<Reservation> listOfAllReservations = new ArrayList<>();
		try(Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY)){
						ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getLong(1));
				reservation.setClient_id(resultSet.getLong(2));
				reservation.setVehicle_id(resultSet.getLong(3));
				reservation.setDebut(resultSet.getDate(4).toLocalDate());
				reservation.setFin(resultSet.getDate(5).toLocalDate());
				listOfAllReservations.add(reservation);
			}

		}catch (SQLException e){
			throw new DaoException();
		}
		return listOfAllReservations;
	}
}
