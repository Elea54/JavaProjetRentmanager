package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse
			response) throws ServletException, IOException {
		ApplicationContext context = new
				AnnotationConfigApplicationContext(AppConfiguration.class);
		VehicleService vehicleService = context.getBean(VehicleService.class);

		String constructeur = request.getParameter("manufacturer");
		String modele = request.getParameter("modele");
		int nb_places = Integer.parseInt(request.getParameter("seats")) ;
		Vehicle vehicle = new Vehicle(0, constructeur, modele, nb_places);
        try {
            vehicleService.create(vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
		response.sendRedirect("/rentmanager/cars");
    }
	}
