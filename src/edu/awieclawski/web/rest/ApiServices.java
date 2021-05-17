package edu.awieclawski.web.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import edu.awieclawski.web.models.Cluster;
import edu.awieclawski.web.models.CoPrimes;
import edu.awieclawski.web.models.Prime;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class ApiServices {
	private final static Logger LOGGER = Logger.getLogger(ApiServices.class.getName());
	private final String packageName = ApiServices.class.getPackage().toString();
	private final ApiUtils apiUtils;

	@Inject
	public ApiServices(ApiUtils apiUtils) {
		this.apiUtils = apiUtils;
	}

	// TODO catch org.glassfish.jersey.server.model.ModelValidationException:

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
		LOGGER.log(Level.INFO, "User agent: {" + userAgent + "}");
		return Response.ok(userAgent).build();
	}

	@GET
	@Path("/host")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getUserHost(@Context HttpServletRequest request) {
		LOGGER.log(Level.INFO, "User host: {" + request.getRemoteAddr() + "}");
		return Response.ok(request.getRemoteAddr()).build();
	}

	@GET
	@Path("/headers")
//	@Produces(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHeaders(@Context HttpHeaders headers) {
		if (headers == null)
			return Response.noContent().build();
		LOGGER.log(Level.INFO, "Headers list {" + apiUtils.getHeadersList(headers) + "}");
		return Response.ok(apiUtils.getHeadersList(headers)).build();
	}

	@GET
	@Path("/isprime/{number}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response isPrime(@PathParam("number") String number) {
		int numberInt = apiUtils.isPrime(number);
		if (numberInt < 0) // not integer
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		if (numberInt == 0)
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.ok(numberInt).build();
	}

	@GET
	@Path("/isprime_rj/{number}") // return JSON
	@Produces(MediaType.APPLICATION_JSON)
	public Response isPrimeJ(@PathParam("number") String number) {
		Prime prime = apiUtils.isPrimeJ(number);
		if (prime.getValue() < 0) // not integer
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		if (prime.getValue() == 0)
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.ok(prime).build();
	}

	@POST
	@Path("/coprimes_rj") // return JSON
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response isCoPrimeJ(CoPrimes coprimes) {
		CoPrimes newCoprimes = apiUtils.isCoPrimeJ(coprimes);
		if (newCoprimes.getPubkey() < 0)
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.ok(newCoprimes).build();
	}

	@POST
	@Path("/encode_rj") // return JSON
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEncodeJ(Cluster cluster) {
		Cluster encoded = apiUtils.getEncodeJ(cluster);
		if (encoded.getKey() < 0) // not co-primes input
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		if (encoded.getEncoded() == null) // encode fail
			return Response.status(Response.Status.EXPECTATION_FAILED).build();
		return Response.ok(encoded).build();
	}

}
