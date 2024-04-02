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
import java.util.List;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {

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

		List<Reservation> reservationsList = null;
		try {
			reservationsList = reservationService.findAll();
		} catch (ServiceException e) {
			throw new RuntimeException(e.getMessage());
		}
		for (Reservation reservation : reservationsList){
            try {
                Client client = clientService.findById(reservation.getClient_id());
				Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
				reservation.setClient(client);
				reservation.setVehicle(vehicle);
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}

		}
		request.setAttribute("reservations", reservationsList);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
	}
}
