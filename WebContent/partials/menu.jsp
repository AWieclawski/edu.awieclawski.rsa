<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String ctx = request.getContextPath();
%>

<div class="menu">

	<ul class="button-container">
		<li><a href="<%=ctx%>/welcome">Welcome</a></li>
		<li><a href="<%=ctx%>/rsa-step-one">Process</a></li>
		<li><a href="#">News</a></li>
		<li><a href="#">Contact</a></li>
		<li class="right"><a href="#">About</a></li>
	</ul>

</div>
