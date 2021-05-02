<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.awieclawski.web.models.Attributes" %>
	
<%
	String ctx = request.getContextPath();
	Integer n_A = (Integer) session.getAttribute(Attributes.N_A.getName());
	Integer fi_A = (Integer) session.getAttribute(Attributes.PHI_A.getName());
	String n_P = Attributes.N_A.getParam();
	String fi_P = Attributes.PHI_A.getParam();
	Integer e_A = (Integer) session.getAttribute(Attributes.E_A.getName());
	Integer d_A = (Integer) session.getAttribute(Attributes.D_A.getName());
	String e_P = Attributes.E_A.getParam();
	String d_P = Attributes.D_A.getParam();	
	Boolean rsaSucces_A = (Boolean) session.getAttribute(Attributes.RSA_SUCC_A.getName());
	Boolean finish_A = (Boolean) session.getAttribute(Attributes.FINISH_A.getName());
	String textEn_P = Attributes.TEXT_EN_A.getParam();
	String textEn_A = (String) session.getAttribute(Attributes.TEXT_EN_A.getName());
	String textDe_A = (String) session.getAttribute(Attributes.TEXT_DE_A.getName());
	String asciiEn_A = (String) session.getAttribute(Attributes.ASCII_EN_A.getName());
	String asciiDe_A = (String) session.getAttribute(Attributes.ASCII_DE_A.getName());
	String rsaEn_A = (String) session.getAttribute(Attributes.RSA_EN_A.getName());
	String publicKey_A = (String) session.getAttribute(Attributes.PUBLIC_KEY_A.getName());
	String privateKey_A = (String) session.getAttribute(Attributes.PRIVATE_KEY_A.getName());
%>

<div id="left">

	<div class="description">

		<h1>Results</h1>

		<div class="scrollable">

			<jsp:include page="/partials/results.jsp" />
			
		</div>

	</div>

</div>

<div id="right">

	<div class="calculation">
	
		<form action="<%=ctx%>/rsa-step-three" method="post">
		
		<% if (!rsaSucces_A) { %>	
		
			<div class="submits">

				<input type="submit" value="Submit"> 
				
				<input type="reset" value="Reset">

			</div>
			
			<h3>Step 3. Testing encode and decode using the RSA Keys:</h3>

		<% } %>				
			
			<% if (!rsaSucces_A) { %>
			
			<label for="<%=textEn_P%>">Please enter any text:</label>

			<input type="text" id="<%=textEn_P%>" name="<%=textEn_P%>" >

			<br> 
			
			<% } %>
			
			<% if (rsaSucces_A) { %>	
			
			<h3>Solution:</h3>
			
			Source text: <font color="<%=Attributes.TEXT_EN_A.getColor()%>"><%= textEn_A %></font> <br>
			Source to ASCII: <font color="<%=Attributes.ASCII_EN_A.getColor()%>"><%= asciiEn_A %></font> <br>
			Encoded from ASCII: <font color="<%=Attributes.RSA_EN_A.getColor()%>"><%= rsaEn_A %></font> <br> with private key 
			<font color="<%=Attributes.PRIVATE_KEY_A.getColor()%>"><%=privateKey_A%></font> <br>
			<p> -- (Transmission channel) -- </p>
			Decoded to ASCII: <font color="<%=Attributes.ASCII_DE_A.getColor()%>"><%= asciiDe_A %></font> <br> with public key  
			<font color="<%=Attributes.PUBLIC_KEY_A.getColor()%>"><%=publicKey_A%></font> <br>
			Received from decoded ASCII: <font color="<%=Attributes.TEXT_DE_A.getColor()%>"><%= textDe_A %></font> 
			<% } %>		
			
		</form>

	</div>

</div>