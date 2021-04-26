package edu.awieclawski.web.servlets;

import java.io.IOException;
import java.util.Enumeration;
//import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.awieclawski.web.models.Attributes;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = -1501795724394672406L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Welcome() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// reset infoBar messages
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String tmpAttr = attributeNames.nextElement();
			if (!tmpAttr.equals(Attributes.CTX_PTH_A.getName()))
				session.removeAttribute(tmpAttr);
		}

		response.setContentType("text/html");
		request.getRequestDispatcher("welcome.jsp").forward(request, response);
	}

}
