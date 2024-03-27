package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
		ApplicationContext context = new
				AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);
		ReservationService reservationService = context.getBean(ReservationService.class);

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
