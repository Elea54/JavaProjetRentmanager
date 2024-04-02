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
import java.util.List;

@WebServlet("/users")
public class ClientListServlet extends HttpServlet {
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
			try {
				Client client = clientService.findById(clientId);
				clientService.delete(client);
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}
		}

	}
}
