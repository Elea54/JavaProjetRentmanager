package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
	//TODO : Récup les listes de voiture et de client pour les mettre dans le select
	//TODO : création de la reservation à la fin de la requette

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse
			response) throws ServletException, IOException {
		ReservationService reservationService = ReservationService.getInstance();

		response.sendRedirect("/rentmanager/rents");
	}
}
