package edu.awieclawski.web.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StepThree
 */
@WebServlet("/rsa-step-three")
public class StepThree extends HttpServlet {
	private static final long serialVersionUID = -7493929285084375983L;
	private final static Logger LOGGER = Logger.getLogger(StepOne.class.getName());

	private String m_ctxPth = null;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StepThree() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		m_ctxPth = request.getContextPath();

		// log list of Attributes
		HttpSession session = request.getSession(false);
		Enumeration<String> attributeNames = session.getAttributeNames();
		if (attributeNames.hasMoreElements())
			LOGGER.log(Level.INFO, "\n ** Attribute List ** ");
		while (attributeNames.hasMoreElements()) {
			String tmpAttr = attributeNames.nextElement();
			LOGGER.log(Level.INFO, " -- attribute=" + tmpAttr + ",value=" + session.getAttribute(tmpAttr));
		}

		response.setContentType("text/html");
		request.getRequestDispatcher("/pages/step_two.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
