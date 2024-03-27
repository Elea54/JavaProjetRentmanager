package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	ReservationService reservationService;
	@Autowired
	ClientService clientService;
	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Vehicle> vehiclesList = null;
		List<Client> clientsList = null;
		try {
			vehiclesList = vehicleService.findAll();
		} catch (ServiceException e) {
			throw new RuntimeException(e.getMessage());
		}
		try {
			clientsList = clientService.findAll();
		} catch (ServiceException e) {
			throw new RuntimeException(e.getMessage());
		}
		request.setAttribute("vehicles", vehiclesList);
		request.setAttribute("clients", clientsList);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse
			response) throws ServletException, IOException {
		ApplicationContext context = new
				AnnotationConfigApplicationContext(AppConfiguration.class);
		ReservationService reservationService = context.getBean(ReservationService.class);
		long vehicle_id = Long.parseLong(request.getParameter("car"));
		long client_id = Long.parseLong(request.getParameter("client"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date_debut = request.getParameter("begin");
		LocalDate debut = LocalDate.parse(date_debut, formatter);
		String date_fin = request.getParameter("end");
		LocalDate fin = LocalDate.parse(date_fin, formatter);
		Reservation reservation = new Reservation(0, client_id, vehicle_id, debut, fin);
		try {
			reservationService.create(reservation);
		} catch (ServiceException e) {
			throw new RuntimeException(e.getMessage());
		}
		response.sendRedirect("/rentmanager/rents");
	}
}
