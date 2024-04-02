package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	VehicleService vehicleService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id_vehicle = Long.parseLong(request.getParameter("id"));
		Vehicle vehicle = null;
        try {
            vehicle = vehicleService.findById(id_vehicle);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
		request.setAttribute("vehicle", vehicle);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/delete.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
		if(vehicleId >= 0){
			try {
				Vehicle vehicle = vehicleService.findById(vehicleId);
				vehicleService.delete(vehicle);
				response.sendRedirect("/rentmanager/cars");
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
