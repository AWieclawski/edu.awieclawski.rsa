package edu.awieclawski.web.servlets;

import java.io.IOException;
import java.util.Enumeration;
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

import edu.awieclawski.web.models.Attributes;
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

	private String m_ctxPth = null;
	private String m_errComm = null;
	private String m_infComm = null;

	NumberUtil nUtil = new NumberUtil();

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
		m_ctxPth = request.getContextPath();

		// log list of Attributes
		HttpSession session = request.getSession(false);
		Enumeration<String> attributeNames = session.getAttributeNames();
		LOGGER.log(Level.INFO, "\n ** Attribute List ** ");
		while (attributeNames.hasMoreElements()) {
			String tmpAttr = attributeNames.nextElement();
			LOGGER.log(Level.INFO, " -- attribute=" + tmpAttr + ",value=" + session.getAttribute(tmpAttr));
		}

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
		m_errComm = null;
		m_infComm = null;

		/// reset infoBar messages
		session.removeAttribute(Attributes.ERROR_A.getName());
		session.removeAttribute(Attributes.INFO_A.getName());

		pqMap.put(Attributes.P_NUM_A.getName(),
				nUtil.getIntFromString((String) request.getParameter(Attributes.P_NUM_A.getParam())));
		pqMap.put(Attributes.Q_NUM_A.getName(),
				nUtil.getIntFromString((String) request.getParameter(Attributes.Q_NUM_A.getParam())));
		controlSum = pqMap.size();

		if (pqMap != null)
			for (Map.Entry<String, MessageService> entry : pqMap.entrySet()) {
				MessageService thisMsgServ = entry.getValue();
				int numberInt = thisMsgServ.getIntResult();
				if (numberInt < 0) { // not integer
					session.setAttribute(Attributes.ERROR_A.getName(), getActualErrorByString(thisMsgServ.getError()));
					m_errComm = getActualErrorByString(thisMsgServ.getError());
					session.setAttribute(Attributes.INFO_A.getName(), getActualInfoByString(thisMsgServ.getInfo()));
					m_infComm = getActualInfoByString(thisMsgServ.getInfo());
				} else {
					thisMsgServ = nUtil.primeNumbersHandling(numberInt);
					int numberValid = thisMsgServ.getIntResult();
					if (numberValid > 0) { // OK - is Prime
						session.setAttribute(entry.getKey(), numberValid);
						count++;

						LOGGER.log(Level.INFO, "key=" + entry.getKey() + ",numberValid=" + numberValid + ",count="
								+ count + ",controlSum=" + controlSum);

					} else {
						thisMsgServ = getActualMsgServByMsgServ(thisMsgServ, entry);
						pqMap.put(entry.getKey(), thisMsgServ);
						session.setAttribute(Attributes.ERROR_A.getName(),
								getActualErrorByString(thisMsgServ.getError()));
						m_errComm = getActualErrorByString(thisMsgServ.getError());
						session.setAttribute(Attributes.INFO_A.getName(), getActualInfoByString(thisMsgServ.getInfo()));
						m_infComm = getActualInfoByString(thisMsgServ.getInfo());
					}
				}
			}

		// pass-to logic
		if (controlSum == count) { // next step if all map lines are ok
			response.sendRedirect(m_ctxPth + "/rsa-step-two");
		} else {
			response.sendRedirect(m_ctxPth + "/rsa-step-one");
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
		String newMsgValue = "";
		if (msgServ != null) {

			if (entry.getValue().getError() != null)
				newMsgValue = entry.getValue().getError() + " | " + msgServ.getError();
			else
				newMsgValue = msgServ.getError();
			msgServ.setError(newMsgValue);

			if (entry.getValue().getInfo() != null)
				newMsgValue = entry.getValue().getInfo() + " | " + msgServ.getInfo();
			else
				newMsgValue = msgServ.getInfo();

			result = msgServ;
			msgServ.setInfo(newMsgValue);
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
			if (m_errComm != null)
				newErrorValue = m_errComm + " | " + msgTxt;
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
			if (m_infComm != null)
				newInfoValue = m_infComm + " | " + msgTxt;
			else
				newInfoValue = msgTxt;
		}
		return newInfoValue;
	}

}
