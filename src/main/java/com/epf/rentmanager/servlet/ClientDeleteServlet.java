package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ClientService clientService;
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id_client = Long.parseLong(request.getParameter("id"));
		Client client = null;
        try {
            client = clientService.findById(id_client);

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
		request.setAttribute("client", client);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/delete.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long clientId = Long.parseLong(request.getParameter("clientId"));
		if(clientId >= 0){
			try {
				Client client = clientService.findById(clientId);
				clientService.delete(client);
				response.sendRedirect("/rentmanager/users");
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
