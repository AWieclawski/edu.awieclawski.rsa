package edu.awieclawski.web.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.awieclawski.web.service.MessageService;
import edu.awieclawski.web.utils.NumberUtil;

/**
 * Servlet implementation class StepOne
 * 
 * @author AWieclawski
 * 
 */
@WebServlet("/rsa-step-one")
public class StepOne extends HttpServlet {
	private final static long serialVersionUID = 3371696875741740842L;
	private final static Logger LOGGER = Logger.getLogger(StepOne.class.getName());
	private String m_contextPath = null;

	NumberUtil nUtil = new NumberUtil();

	String errorCommunicate = null;
	String infoCommunicate = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StepOne() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		m_contextPath = request.getContextPath();
		HttpSession session = request.getSession();
		session.setAttribute("contextPath", m_contextPath);

		LOGGER.log(Level.INFO, "contextPath=" + m_contextPath);

		errorCommunicate = (String) request.getAttribute("error");
		infoCommunicate = (String) request.getAttribute("info");

		LOGGER.log(Level.WARNING, "errorCommunicate=" + errorCommunicate);
		LOGGER.log(Level.WARNING, "infoCommunicate=" + infoCommunicate);

		response.setContentType("text/html");
		request.getRequestDispatcher("/pages/step_one.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, MessageService> pqMap = new HashMap<>();
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		int controlSum = -1;
		int count = 0;

		pqMap.put("pNumber", nUtil.getIntFromString((String) request.getParameter("pNumber")));
		pqMap.put("qNumber", nUtil.getIntFromString((String) request.getParameter("qNumber")));
		controlSum = pqMap.size();

		if (pqMap != null)
			for (Map.Entry<String, MessageService> entry : pqMap.entrySet()) {
				MessageService thisMsgServ = entry.getValue();
				int numberInt = thisMsgServ.getIntResult();
				if (numberInt < 0) // not integer
					session.setAttribute("error", errorCommunicate + "|" + thisMsgServ.getError());
				else {
					thisMsgServ = nUtil.primeNumbersHandling(numberInt);
					int numberValid = thisMsgServ.getIntResult();
					if (numberValid > 0) { // OK - is Prime
						session.setAttribute(entry.getKey(), numberValid);
						count++;

//						LOGGER.log(Level.INFO,
//								"key=" + entry.getKey() + ",numberValid=" + numberValid + ",count=" + count);

					} else {
						thisMsgServ = getActualErrorValue(thisMsgServ, entry);
						pqMap.put(entry.getKey(), thisMsgServ);
						session.setAttribute("error", errorCommunicate + "|" + thisMsgServ.getError());
					}
				}
			}

		if (pqMap != null)
			LOGGER.log(Level.INFO, "pqMap=" + pqMap.toString());

		// pass-to logic
		if (controlSum == count) {
			session.setAttribute("pqNumbersMap", pqMap);
			response.sendRedirect(m_contextPath + "/rsa-step-two");
		} else {
			response.sendRedirect(m_contextPath + "/rsa-step-one");
		}
	}

	/**
	 * Logic to avoid null in error message string
	 * 
	 * @param msgServ, MessageService
	 * @param entry,  Map.Entry<String, MessageService>
	 * @return MessageService, actualized with error message
	 */
	private MessageService getActualErrorValue(MessageService msgServ, Map.Entry<String, MessageService> entry) {
		MessageService result = null;
		String newErrorValue = "";
		if (msgServ != null) {
			if (entry.getValue().getError() != null)
				newErrorValue = entry.getValue().getError() + "|" + msgServ.getError();
			else
				newErrorValue = msgServ.getError();
			msgServ.setError(newErrorValue);
			result = msgServ;
		}
		return result;
	}

}
