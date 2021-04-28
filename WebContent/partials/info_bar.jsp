<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="edu.awieclawski.web.models.Attributes"%>

<%
	String erTxt = (String) session.getAttribute(Attributes.ERROR_A.getName());
	String inTxt = (String) session.getAttribute(Attributes.INFO_A.getName());
	String erClr = Attributes.ERROR_A.getColor();
	String inClr = Attributes.INFO_A.getColor();
%>

<div class="infobar">

	<% if(erTxt != null) { %>

		<font color="<%=erClr%>">${error_A}</font><br>
		<font color="<%=inClr%>">${info_A}</font> 

	<% 	} else { %>

	<font color="<%=inClr%>">${info_A}</font> 

	<% } %>
	
</div>