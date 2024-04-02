package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ReservationService reservationService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id_resa = Long.parseLong(request.getParameter("id"));
		request.setAttribute("id_resa", id_resa);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/delete.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long reservationId = Long.parseLong(request.getParameter("reservationId"));
		if(reservationId >= 0){
			try {
				reservationService.delete(reservationId);
				response.sendRedirect("/rentmanager/rents");
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
