package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReservationService reservationService = ReservationService.getInstance();

		VehicleService vehicleService = VehicleService.getInstance();
		List<Reservation> reservationsList = null;
		try {
			reservationsList = reservationService.findAll();
		} catch (ServiceException e) {
			throw new RuntimeException(e.getMessage());
		}
		request.setAttribute("reservations", reservationsList);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
	}
	public static String getClientId(long id) throws ServiceException {
		ClientService clientService = ClientService.getInstance();
		Client client = null;
		try{
			client = clientService.findById(id);
		}catch(ServiceException e){
			throw new RuntimeException(e.getMessage());
		}
		return client.getNom();
	}

}
