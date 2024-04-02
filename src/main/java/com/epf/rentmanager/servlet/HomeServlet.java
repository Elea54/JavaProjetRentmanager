package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
	@Autowired
	ClientService clientService;
	@Autowired
	ReservationService reservationService;
	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
