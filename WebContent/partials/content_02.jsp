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
	Boolean dSucces_A = (Boolean) session.getAttribute(Attributes.D_SUCC_A.getName());
	Boolean finish_A = (Boolean) session.getAttribute(Attributes.FINISH_A.getName());
	String autoSearch_P = Attributes.AUTO_A.getParam();
//	Boolean autoSearch_A = (Boolean) session.getAttribute(Attributes.AUTO_A.getName()); 
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
	
		<p>The values of 'p' and 'q' you provided yield a modulus 'n', and also a Euler function for n - Ø(n).</p>
		<p>Using the Euclidean algorithm appropriately, you should find the number 'e' (Public Key), 
		which is relatively prime to the computed value of the Euler function Ø (i.e. GCD (e, Ø) = 1). GCD - greatest common divisor.</p>
		<p>This number should also satisfy the inequality 1 &#62; e &#62; n. 
		<strong>It does not have to be prime but odd only.</strong> </p>

		<form action="<%=ctx%>/rsa-step-two" method="post">
		
		<% if (!dSucces_A) { %>	
		
			<div class="submits">

				<input type="submit" value="Submit"> 
				
				<input type="reset" value="Reset">

			</div>
			
			<h3>Step 2. Compute RSA keys as the product of two co-prime numbers 'e' and 'd':</h3>

		<% } %>				
			
			<% if (e_A == null) { %>
			
			<label for="<%=e_P%>">Co-prime candidate 'e' (between 1 and <%=n_A-1%>):</label>

			<input type="number" id="<%=e_P%>" name="<%=e_P%>" min="1" max="<%=n_A-1%>">

			<br> 
			
			<% } %>
			
			<% if (d_A != null && e_A != null) { %>	
			
			<h3>Solution:</h3>
			
			Public Key <b><font color="<%=Attributes.E_A.getColor()%>">[<%= e_A %>,<%= n_A %>]</font></b>, Private Key <b><font color="<%=Attributes.D_A.getColor()%>">[<%= d_A %>,<%= n_A %>]</font></b>
			
			<% } %>		
			
		<% if (!dSucces_A && finish_A) { %>	
		
			<label for="<%=autoSearch_P%>">Would you like to auto-calculate 'd'?</label>

			<input type="checkbox" id="<%=autoSearch_P%>" name="<%=autoSearch_P%>" value="<%=autoSearch_P%>" >		
		
		<% } %>								
			
		</form>

	</div>

</div>