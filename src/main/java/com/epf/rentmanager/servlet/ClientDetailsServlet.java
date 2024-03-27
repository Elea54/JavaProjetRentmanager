package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationContext context = new
				AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		ReservationService reservationService = context.getBean(ReservationService.class);

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
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("client", client);
		request.setAttribute("reservations", reservationList);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
	}
}
