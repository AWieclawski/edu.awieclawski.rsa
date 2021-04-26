<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="edu.awieclawski.web.models.Attributes" %>

<div class="infobar">
	<font color="<%=Attributes.ERROR_A.getColor()%>">${error_A}</font><br> 
	<font color="<%=Attributes.INFO_A.getColor()%>">${info_A}</font>
</div>