<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="edu.awieclawski.web.models.Attributes" %>
	
<%
	String ctx = request.getContextPath();
	Integer p_A = (Integer) session.getAttribute(Attributes.P_NUM_A.getName());
	Integer q_A = (Integer) session.getAttribute(Attributes.Q_NUM_A.getName());
	String p_P = Attributes.P_NUM_A.getParam();
	String q_P = Attributes.Q_NUM_A.getParam();
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

		<form action="<%=ctx%>/rsa-step-one" method="post">

			<div class="submits">

				<input type="submit" value="Submit"> 
				
				<input type="reset" value="Reset">

			</div>

			<h3>Step 1. Compute N as the product of two prime numbers 'p' and 'q':</h3>
			
			<% if (p_A == null) { %>
			
			<label for="<%=p_P%>">Primary number 'p' (between 1 and 1000):</label>

			<input type="number" id="<%=p_P%>" name="<%=p_P%>" min="1" max="1000">

			<br> 
			
			<% } %>
			
			<% if (q_A == null) { %>	
			
			<label for="<%=q_P%>">Primary number 'q' (between 1 and 1000):</label> 

			<input type="number" id="<%=q_P%>" name="<%=q_P%>" min="1" max="1000">
			
			<% } %>			
			
		</form>

	</div>

</div>