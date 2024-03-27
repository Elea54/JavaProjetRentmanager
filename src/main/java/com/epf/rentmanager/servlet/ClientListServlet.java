package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exeptions.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class ClientListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationContext context = new
				AnnotationConfigApplicationContext(AppConfiguration.class);
		ClientService clientService = context.getBean(ClientService.class);
		List<Client> listClients = null;
        try {
            listClients = clientService.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage());
        }
		request.setAttribute("clients", listClients);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String inputId = request.getParameter("id");
		Long clientId = Long.parseLong(inputId);
		if(clientId >= 0){
			ApplicationContext context = new
					AnnotationConfigApplicationContext(AppConfiguration.class);
			ClientService clientService = context.getBean(ClientService.class);
			try {
				Client client = clientService.findById(clientId);
				clientService.delete(client);
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}
			response.sendRedirect("/rentmanager/home");
		}
	}
}
