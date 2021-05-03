package edu.awieclawski.web.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import edu.awieclawski.cmd.utils.Calculator;
import edu.awieclawski.web.service.MessageService;
import edu.awieclawski.web.utils.NumberUtil;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class ApiServices {
	private final static Logger LOGGER = Logger.getLogger(ApiServices.class.getName());
	private String packageName = "edu.awieclawski.rsa";

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

}
