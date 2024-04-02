package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {

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

		long id_client = Long.parseLong(request.getParameter("id"));
		Client client = null;

		List<Reservation> reservationList = null;
        try {
            client = clientService.findById(id_client);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            reservationList = reservationService.findByClientId(id_client);
			for (Reservation reservation : reservationList){
				try {
					Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
					reservation.setVehicle(vehicle);
				} catch (ServiceException e) {
					throw new RuntimeException(e);
				}

			}
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("client", client);
		request.setAttribute("reservations", reservationList);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
	}
}
