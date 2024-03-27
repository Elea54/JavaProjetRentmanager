package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ClientService clientService = ClientService.getInstance();
		VehicleService vehicleService = VehicleService.getInstance();
		ReservationService reservationService = ReservationService.getInstance();
		long nbrVehicles = 0;
		long nbrClients = 0;
		long nbrReservations = 0;

		try {
			nbrClients = clientService.count();
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
        try {
            nbrVehicles = vehicleService.count();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            nbrReservations = reservationService.count();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("nbrClients", nbrClients);
		request.setAttribute("nbrVehicles", nbrVehicles);
		request.setAttribute("nbrReservations", nbrReservations);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
}
