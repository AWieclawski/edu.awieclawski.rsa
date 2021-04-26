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

<b>
			
	<% if (p_A != null) { %>
			
		Primary number 'p' = <font color="<%=Attributes.P_NUM_A.getColor()%>"><%= p_A %></font>
				
		<br>
			
	<% } %>
			
	<% if (q_A != null) { %>
			
		Primary number 'q' = <font color="<%=Attributes.Q_NUM_A.getColor()%>"><%= q_A %></font>
			
	<% } %>	
			
</b>