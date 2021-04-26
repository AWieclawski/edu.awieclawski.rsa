package edu.awieclawski.web.servlets;

import java.io.IOException;
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
	
	private String m_ctxPth = null;

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
		m_ctxPth = request.getContextPath();
		HttpSession session = request.getSession();

		session.setAttribute(Attributes.CTX_PTH_A.getName(),m_ctxPth);
		
		// reset infoBar messages
		session.removeAttribute(Attributes.ERROR_A.getName());
		session.removeAttribute(Attributes.INFO_A.getName());

		response.setContentType("text/html");
		request.getRequestDispatcher("welcome.jsp").forward(request, response);
	}

}
