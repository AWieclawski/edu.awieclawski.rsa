package edu.awieclawski.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.awieclawski.cmd.utils.DeEncoder;
import edu.awieclawski.web.models.Attributes;
//import edu.awieclawski.web.service.MessageService;
import edu.awieclawski.web.utils.NumberUtil;

/**
 * Servlet implementation class StepThree
 */
@WebServlet("/rsa-step-three")
public class StepThree extends HttpServlet {
	private static final long serialVersionUID = -7493929285084375983L;
	private final static Logger LOGGER = Logger.getLogger(StepOne.class.getName());

//	private String m_ctxPth = null;
//	private String m_errComm = null;
//	private String m_infComm = null;
	private int m_nMod = -1;
	private int[] privateKey = new int[2];
	private int[] publicKey = new int[2];

	private List<Integer> asciiList = new ArrayList<>();
	private List<Integer> encodedList = new ArrayList<>();

	NumberUtil nUtil = new NumberUtil();
	DeEncoder enc = new DeEncoder();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StepThree() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		m_ctxPth = request.getContextPath();

		HttpSession session = request.getSession(false);
		if (session != null)
			session.setAttribute(Attributes.FINISH_A.getName(), Boolean.valueOf(false));
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

		response.setContentType("text/html");
		request.getRequestDispatcher("/pages/step_three.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
//		m_errComm = null;
//		m_infComm = null;

		m_nMod = (int) session.getAttribute(Attributes.N_A.getName());
		publicKey[0] = (int) session.getAttribute(Attributes.E_A.getName());
		publicKey[1] = m_nMod;
		privateKey[0] = (int) session.getAttribute(Attributes.D_A.getName());
		privateKey[1] = m_nMod;

		// reset infoBar messages
		session.removeAttribute(Attributes.ERROR_A.getName());
		session.removeAttribute(Attributes.INFO_A.getName());
		session.setAttribute(Attributes.PUBLIC_KEY_A.getName(), Arrays.toString(publicKey));
		session.setAttribute(Attributes.PRIVATE_KEY_A.getName(), Arrays.toString(privateKey));

		String toASCIItxt = (String) request.getParameter(Attributes.TEXT_EN_A.getParam());
		if (toASCIItxt != null) {
			asciiList = enc.getAsciiFromString(toASCIItxt);
			LOGGER.log(Level.INFO, "Source Text=" + toASCIItxt);
			session.setAttribute(Attributes.TEXT_EN_A.getName(), toASCIItxt);
		}
		if (asciiList != null) {
			LOGGER.log(Level.INFO, "Source to ASCII=" + asciiList.toString());
			session.setAttribute(Attributes.ASCII_EN_A.getName(), asciiList.toString());
			encodedList = enc.getRSAfromInt(asciiList, privateKey[0], m_nMod);
		}
		if (encodedList != null) {
			LOGGER.log(Level.INFO,
					"Encoded from ASCII=" + encodedList + " with public key " + Arrays.toString(publicKey));
			session.setAttribute(Attributes.RSA_EN_A.getName(), encodedList.toString());
			asciiList = enc.getRSAfromInt(encodedList, publicKey[0], m_nMod);
			if (asciiList != null) {
				LOGGER.log(Level.INFO, "Decoded to ASCII=" + asciiList.toString() + " with private key "
						+ Arrays.toString(privateKey));
				session.setAttribute(Attributes.ASCII_DE_A.getName(), asciiList.toString());
				String encodedTxt = enc.getStringFromAscii(asciiList);
				LOGGER.log(Level.INFO, "Received from decoded ASCII=" + encodedTxt);
				session.setAttribute(Attributes.TEXT_DE_A.getName(), encodedTxt);
				if (encodedTxt != null)
					session.setAttribute(Attributes.RSA_SUCC_A.getName(), Boolean.valueOf(true));
			}
		}
		doGet(request, response);
	}

}
