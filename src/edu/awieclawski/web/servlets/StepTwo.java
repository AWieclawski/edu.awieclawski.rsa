package edu.awieclawski.web.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
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
@WebServlet("/rsa-step-two")
public class StepTwo extends HttpServlet {
	private final static long serialVersionUID = 3371696875741740842L;
	private final static Logger LOGGER = Logger.getLogger(StepTwo.class.getName());

	private String m_ctxPth = null;
	private String m_errComm = null;
	private String m_infComm = null;
	private int m_nMod = -1;
	private int m_PhiN = -1;
	private int privateKey = -1;
	private int publicKey = -1;

	NumberUtil nUtil = new NumberUtil();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StepTwo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		m_ctxPth = request.getContextPath();

		// pre-Declare attributes for "/rsa-step-three"
		HttpSession session = request.getSession(false);
		if (session != null)
			session.setAttribute(Attributes.RSA_SUCC_A.getName(), Boolean.valueOf(false));
		else
			request.getRequestDispatcher("/welcome.jsp").forward(request, response);

		// log list of Attributes
//		Enumeration<String> attributeNames;
//		if (session != null) {
//			attributeNames = session.getAttributeNames();
//			if (attributeNames.hasMoreElements())
//				LOGGER.log(Level.INFO, "\n ** Attribute List ** ");
//			while (attributeNames.hasMoreElements()) {
//				String tmpAttr = attributeNames.nextElement();
//				LOGGER.log(Level.INFO, " -- attribute=" + tmpAttr + ",value=" + session.getAttribute(tmpAttr));
//			}
//		}

		Integer p_A = (Integer) session.getAttribute(Attributes.P_NUM_A.getName());
		Integer q_A = (Integer) session.getAttribute(Attributes.Q_NUM_A.getName());

		if (p_A > 0 && q_A > 0) {
			m_nMod = p_A * q_A;
			m_PhiN = nUtil.phiEuler(m_nMod);
			session.setAttribute(Attributes.N_A.getName(), m_nMod);
			session.setAttribute(Attributes.PHI_A.getName(), m_PhiN);
		}

		response.setContentType("text/html");
		request.getRequestDispatcher("/pages/step_two.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		m_errComm = null;
		m_infComm = null;

		/// reset infoBar messages
		session.removeAttribute(Attributes.ERROR_A.getName());
		session.removeAttribute(Attributes.INFO_A.getName());

		MessageService thisMsgServ = nUtil
				.getIntFromStringAndMsg((String) request.getParameter(Attributes.E_A.getParam()));
		int numberInt = thisMsgServ.getIntResult();
		if (numberInt < 0) { // not integer
			session.setAttribute(Attributes.ERROR_A.getName(), getActualErrorByString(thisMsgServ.getError()));
			m_errComm = getActualErrorByString(thisMsgServ.getError());
			session.setAttribute(Attributes.INFO_A.getName(), getActualInfoByString(thisMsgServ.getInfo()));
			m_infComm = getActualInfoByString(thisMsgServ.getInfo());

		} else {
			thisMsgServ = nUtil.getCoPrimeAndMsg(m_nMod, numberInt, m_PhiN);

			publicKey = thisMsgServ.getIntResult();
			if (publicKey > 0) { // OK - is co-Prime
				session.setAttribute(Attributes.E_A.getName(), publicKey);
				privateKey = nUtil.privateKeyGenerator(m_nMod, numberInt);
				if (privateKey > 0) { // OK - d is found
					session.setAttribute(Attributes.D_A.getName(), privateKey);
					session.setAttribute(Attributes.D_SUCC_A.getName(), Boolean.valueOf(true));
					session.setAttribute(Attributes.FINISH_A.getName(), Boolean.valueOf(true));
				} else {
					String infoTxt = "Ops! privateKey can not be calculated for 'e'=" + numberInt;
					session.setAttribute(Attributes.INFO_A.getName(), getActualInfoByString(infoTxt));
					session.setAttribute(Attributes.D_SUCC_A.getName(), Boolean.valueOf(false));
					session.setAttribute(Attributes.FINISH_A.getName(), Boolean.valueOf(true));
					m_infComm = getActualInfoByString(thisMsgServ.getInfo());
				}

			} else {
				session.setAttribute(Attributes.ERROR_A.getName(), getActualErrorByString(thisMsgServ.getError()));
				m_errComm = getActualErrorByString(thisMsgServ.getError());
				session.setAttribute(Attributes.INFO_A.getName(), getActualInfoByString(thisMsgServ.getInfo()));
				m_infComm = getActualInfoByString(thisMsgServ.getInfo());
			}
		}

		String autoSearch_P = (String) request.getParameter(Attributes.AUTO_A.getParam());
		if (autoSearch_P != null)
			if (autoSearch_P.equals(Attributes.AUTO_A.getParam())) {
				// TODO progress bar of auto search logic
				Integer n_A = (Integer) session.getAttribute(Attributes.N_A.getName());
				Integer e_A = (Integer) session.getAttribute(Attributes.E_A.getName());
				int[] resultArr = nUtil.autoSearchRSAkey(n_A, e_A + 1);
				privateKey = resultArr[2];
				publicKey = resultArr[1];
				if (privateKey > 0) {
					session.setAttribute(Attributes.D_A.getName(), privateKey);
					session.setAttribute(Attributes.E_A.getName(), publicKey);
					session.setAttribute(Attributes.D_SUCC_A.getName(), Boolean.valueOf(true));
				} else
					LOGGER.log(Level.WARNING, " NOT ANY SUCCESS after autoSearch. 'd'=" + privateKey);
			}

		// log list of parameters
//		Map<String, String[]> params = request.getParameterMap();
//		LOGGER.log(Level.INFO, "\n ** Parameter List ** ");
//		Iterator<String> i = params.keySet().iterator();
//		while (i.hasNext()) {
//			String key = (String) i.next();
//			String value = ((String[]) params.get(key))[0];
//			LOGGER.log(Level.INFO, " --- parammeter=" + key + ",value=" + value);
//		}

		// pass-to logic
		if (privateKey > 0 && request.getParameter(Attributes.TESTKEYS_A.getParam()) != null) {
			response.sendRedirect(m_ctxPth + "/rsa-step-three");
		} else {
			response.sendRedirect(m_ctxPth + "/rsa-step-two");
		}
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
