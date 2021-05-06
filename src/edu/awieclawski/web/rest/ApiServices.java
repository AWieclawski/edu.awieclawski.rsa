package edu.awieclawski.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import edu.awieclawski.cmd.utils.Calculator;
import edu.awieclawski.cmd.utils.DeEncoder;
import edu.awieclawski.web.models.Cluster;
import edu.awieclawski.web.models.CoPrimes;
import edu.awieclawski.web.models.Prime;
import edu.awieclawski.web.service.MessageService;
import edu.awieclawski.web.utils.NumberUtil;
import edu.awieclawski.web.utils.TimeUtils;

//import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class ApiServices {
	private final static Logger LOGGER = Logger.getLogger(ApiServices.class.getName());
	private String packageName = ApiServices.class.getPackage().toString();

//	@Inject
	TimeUtils tUtils = new TimeUtils();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return packageName;
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello " + packageName + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello " + packageName + "</title>" + "<body><h1>" + "Hello " + packageName
				+ "</body></h1>" + "</html> ";
	}

	@GET
	@Path("/agent")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUserAgent(@HeaderParam("user-agent") String userAgent) {
		LOGGER.log(Level.INFO, "User agent: {}", userAgent);
		return Response.ok(userAgent).build();
	}

	@GET
	@Path("/isprime/{number}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response isPrime(@PathParam("number") String number) {
		Calculator calc = new Calculator();
		NumberUtil nUtil = new NumberUtil();
		MessageService thisMsgServ = MessageService.getNewMessageService();
		int numberInt = -1;
		try {
			thisMsgServ = nUtil.getIntFromStringAndMsg(number);
		} catch (NumberFormatException ex) {
			LOGGER.log(Level.SEVERE, "getIntFromString failed. Received: " + number);
		}
		if (thisMsgServ != null) {
			numberInt = thisMsgServ.getIntResult();
			if (numberInt < 0) { // not integer
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			} else { // not prime
				if (!calc.isPrimeNumber(numberInt))
					return Response.status(Response.Status.EXPECTATION_FAILED).build();
			}
		}
		return Response.ok(number).build();
	}

	@GET
	@Path("/isprime_rj/{number}") // return JSON
	@Produces(MediaType.APPLICATION_JSON)
	public Response isPrimeJ(@PathParam("number") String number) {
		Calculator calc = new Calculator();
		NumberUtil nUtil = new NumberUtil();
		MessageService thisMsgServ = MessageService.getNewMessageService();
		int numberInt = -1;
		try {
			thisMsgServ = nUtil.getIntFromStringAndMsg(number);
		} catch (NumberFormatException ex) {
			LOGGER.log(Level.SEVERE, "getIntFromString failed. Received: " + number);
		}
		if (thisMsgServ != null) {
			numberInt = thisMsgServ.getIntResult();
			if (numberInt < 0) { // not integer
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			} else { // not prime
				if (!calc.isPrimeNumber(numberInt))
					return Response.status(Response.Status.EXPECTATION_FAILED).build();
			}
		}
		Prime prime = new Prime(numberInt, tUtils.nowString());
		return Response.ok(prime).build();
	}

	@POST
	@Path("/coprimes_rj") // return JSON
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response isCoPrimeJ(CoPrimes coprimes) {
		Calculator calc = new Calculator();
		NumberUtil nUtil = new NumberUtil();
		MessageService thisMsgServ = MessageService.getNewMessageService();
		int modN = -1;
		int pubKey = -1;
		int phiN = -1;
		int numberInt = -1;

//		LOGGER.log(Level.WARNING, "coprimes.getModulusn()=" + coprimes.getModulusn() + ",coprimes.getPubkey()="
//				+ coprimes.getPubkey() + ",coprimes.getPhin()=" + coprimes.getPhin());

		if ((Long) coprimes.getModulusn() > 0)
			modN = (int) coprimes.getModulusn();
		if ((Long) coprimes.getPubkey() > 0)
			pubKey = (int) coprimes.getPubkey();
		if ((Long) coprimes.getPhin() > 0)
			phiN = (int) coprimes.getPhin();
		if ((Long) coprimes.getPhin() == 0 && (Long) coprimes.getModulusn() > 0)
			phiN = (int) calc.phiEuler(modN);

//		LOGGER.log(Level.WARNING, "modN=" + modN + ",pubKey=" + pubKey + ",phiN=" + phiN);

		thisMsgServ = nUtil.getCoPrimeAndMsg(modN, pubKey, phiN);
		if (thisMsgServ != null) {
			numberInt = thisMsgServ.getIntResult();
			if (numberInt < 0) { // not coprime
				return Response.status(Response.Status.EXPECTATION_FAILED).build();
			}
		}
		coprimes.setReqid(tUtils.nowString());
		if (phiN > 0)
			coprimes.setPhin(phiN);
		return Response.ok(coprimes).build();
	}

	@POST
	@Path("/encode_rj") // return JSON
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEncode(Cluster cluster) {
		Cluster encoded = new Cluster();
		DeEncoder enc = new DeEncoder();
		int modN = -1;
		int key = -1;
		String msg = "";
		List<Integer> asciiList = new ArrayList<>();
		List<Integer> encodedList = new ArrayList<>();

		if ((Long) cluster.getModulusn() > 0)
			modN = (int) cluster.getModulusn();

		if ((Long) cluster.getKey() > 0)
			key = (int) cluster.getKey();

		if (cluster.getMessage() != null)
			msg = cluster.getMessage();

		if (msg != null)
			asciiList = enc.getAsciiFromString(msg);

		if (asciiList != null) {
			encodedList = enc.getRSAfromInt(asciiList, key, modN);
			encoded = cluster;
			encoded.setEncoded(encodedList);
			encoded.setReqid(tUtils.nowString());
		} else
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
// TODO catch org.glassfish.jersey.server.model.ModelValidationException:
		return Response.ok(encoded).build();
	}

}
