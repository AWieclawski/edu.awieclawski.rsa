package edu.awieclawski.web;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StepOne
 */
@WebServlet("/rsa-step-one")
public class StepOne extends HttpServlet {
    private static final long serialVersionUID = 3371696875741740842L;
    private static final LogManager lm = LogManager.getLogManager();
    private static final Logger logger = lm.getLogger(StepOne.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StepOne() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
        HttpSession session = request.getSession();
		session.setAttribute("contextPath",contextPath);

		response.setContentType("text/html");
        request.getRequestDispatcher("/pages/step_one.jsp").forward(request, response);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
//        HttpSession session = request.getSession(false);

		
		doGet(request, response);
	}

}
