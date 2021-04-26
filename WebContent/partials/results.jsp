<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="edu.awieclawski.web.models.Attributes" %>
	
<%
	String ctx = request.getContextPath();
	Integer p_A = (Integer) session.getAttribute(Attributes.P_NUM_A.getName());
	Integer q_A = (Integer) session.getAttribute(Attributes.Q_NUM_A.getName());
	String p_P = Attributes.P_NUM_A.getParam();
	String q_P = Attributes.Q_NUM_A.getParam();
	Integer n_A = (Integer) session.getAttribute(Attributes.N_A.getName());
	Integer fi_A = (Integer) session.getAttribute(Attributes.PHI_A.getName());
	String n_P = Attributes.N_A.getParam();
	String fi_P = Attributes.PHI_A.getParam();
	Integer e_A = (Integer) session.getAttribute(Attributes.E_A.getName());
	Integer d_A = (Integer) session.getAttribute(Attributes.D_A.getName());
	String e_P = Attributes.E_A.getParam();
	String d_P = Attributes.D_A.getParam();
%>    

	<div class="results">

<b>
			
	<% if (p_A != null) { %>
			
		Primary number 'p' = <font color="<%=Attributes.P_NUM_A.getColor()%>"><%= p_A %></font>
				
		<br>
			
	<% } %>
			
	<% if (q_A != null) { %>
			
		Primary number 'q' = <font color="<%=Attributes.Q_NUM_A.getColor()%>"><%= q_A %></font>
		
		<br>
			
	<% } %>	
	
		<% if (n_A != null) { %>
			
		Modulo 'n' = <font color="<%=Attributes.N_A.getColor()%>"><%= n_A %></font>
				
		<br>
			
	<% } %>
			
	<% if (fi_A != null) { %>
			
		Euler function 'Phi(n)' = <font color="<%=Attributes.PHI_A.getColor()%>"><%= fi_A %></font>
		
		<br>
			
	<% } %>	
	
	<% if (e_A != null) { %>
			
		Public key 'e' = <font color="<%=Attributes.E_A.getColor()%>"><%= e_A %></font>
				
		<br>
			
	<% } %>
			
	<% if (d_A != null) { %>
			
		Private key 'd' = <font color="<%=Attributes.D_A.getColor()%>"><%= d_A %></font>
		
		<br>
			
	<% } %>	
			
</b>

</div>