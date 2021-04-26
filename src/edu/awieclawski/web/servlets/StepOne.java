package edu.awieclawski.web.servlets;

import java.io.IOException;
//import java.util.Arrays;
//import java.util.Enumeration;
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
	private String contextPath_A = null;

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
		contextPath_A = request.getContextPath();
		HttpSession session = request.getSession(false);
		session.setAttribute("contextPath_A", contextPath_A);

		LOGGER.log(Level.INFO, "contextPath=" + contextPath_A);

		errorCommunicate = (String) session.getAttribute("error_A");
		infoCommunicate = (String) session.getAttribute("info_A");

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
		errorCommunicate = null;

		/// reset infoBar messages
		session.removeAttribute("error_A");
		session.removeAttribute("info_A");

		pqMap.put("pNumber_A", nUtil.getIntFromString((String) request.getParameter("pNumber_P")));
		pqMap.put("qNumber_A", nUtil.getIntFromString((String) request.getParameter("qNumber_P")));
		controlSum = pqMap.size();

		if (pqMap != null)
			for (Map.Entry<String, MessageService> entry : pqMap.entrySet()) {
				MessageService thisMsgServ = entry.getValue();
				int numberInt = thisMsgServ.getIntResult();
				if (numberInt < 0) { // not integer
					session.setAttribute("error_A", getActualErrorByString(thisMsgServ.getError()));
					errorCommunicate = getActualErrorByString(thisMsgServ.getError());
					session.setAttribute("info_A", getActualInfoByString(thisMsgServ.getInfo()));
					errorCommunicate = getActualInfoByString(thisMsgServ.getInfo());
					session.removeAttribute(entry.getKey());
				} else {
					thisMsgServ = nUtil.primeNumbersHandling(numberInt);
					int numberValid = thisMsgServ.getIntResult();
					if (numberValid > 0) { // OK - is Prime
						session.setAttribute(entry.getKey(), numberValid);
						count++;

//						LOGGER.log(Level.INFO,
//								"key=" + entry.getKey() + ",numberValid=" + numberValid + ",count=" + count);

					} else {
						LOGGER.log(Level.INFO, "addedMsg=" + thisMsgServ.getError());
						thisMsgServ = getActualMsgServByMsgServ(thisMsgServ, entry);
						pqMap.put(entry.getKey(), thisMsgServ);
						session.setAttribute("error_A", getActualErrorByString(thisMsgServ.getError()));
						errorCommunicate = getActualErrorByString(thisMsgServ.getError());
						session.setAttribute("info_A", getActualInfoByString(thisMsgServ.getInfo()));
						errorCommunicate = getActualInfoByString(thisMsgServ.getInfo());						
						session.removeAttribute(entry.getKey());
					}
				}
			}

//		if (pqMap != null)
//			LOGGER.log(Level.INFO, "pqMap=" + pqMap.toString());
//
//		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//			LOGGER.log(Level.INFO,
//					" -- parameter=" + entry.getKey() + ": " + Arrays.toString(entry.getValue()) + "</p>");
//		}
//
//		Enumeration<String> attributeNames = session.getAttributeNames();
//		while (attributeNames.hasMoreElements()) {
//			String tmpAttr = attributeNames.nextElement();
//			LOGGER.log(Level.INFO, " --- attribute=" + tmpAttr + ",value=" + session.getAttribute(tmpAttr));
//		}

		// pass-to logic
		if (controlSum == count) { // next step if all map lines are ok
			session.setAttribute("pqNumbersMap", pqMap);
			response.sendRedirect(contextPath_A + "/rsa-step-two");
		} else {
			response.sendRedirect(contextPath_A + "/rsa-step-one");
		}
	}

	/**
	 * Logic to avoid 'null' in MessageService string by MessageService
	 * 
	 * @param msgServ, MessageService
	 * @param entry,   Map.Entry<String, MessageService>
	 * @return MessageService, actualized with error message
	 */
	private MessageService getActualMsgServByMsgServ(MessageService msgServ, Map.Entry<String, MessageService> entry) {
		MessageService result = null;
		String newErrorValue = "";
		if (msgServ != null) {
			
			if (entry.getValue().getError() != null)
				newErrorValue = entry.getValue().getError() + " | " + msgServ.getError();
			else
				newErrorValue = msgServ.getError();
			msgServ.setError(newErrorValue);
			
			if (entry.getValue().getInfo() != null)
				newErrorValue = entry.getValue().getInfo() + " | " + msgServ.getInfo();
			else
				newErrorValue = msgServ.getInfo();
			
			result = msgServ;
		}
		return result;
	}

	/**
	 * Logic to avoid 'null' in error message text string by String
	 * 
	 * @param msgTxt
	 * @return
	 */
	private String getActualErrorByString(String msgTxt) {
		String newErrorValue = "";
		if (msgTxt != null) {
			if (errorCommunicate != null)
				newErrorValue = errorCommunicate + " | " + msgTxt;
			else
				newErrorValue = msgTxt;
		}
		return newErrorValue;
	}
	
	/**
	 * Logic to avoid 'null' in info message text string by String
	 * 
	 * @param msgTxt
	 * @return
	 */
	private String getActualInfoByString(String msgTxt) {
		String newInfoValue = "";
		if (msgTxt != null) {
			if (infoCommunicate != null)
				newInfoValue = infoCommunicate + " | " + msgTxt;
			else
				newInfoValue = msgTxt;
		}
		return newInfoValue;
	}

}
