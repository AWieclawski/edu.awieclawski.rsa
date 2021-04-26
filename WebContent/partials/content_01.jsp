<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="left">

	<div class="description">

		<h1>Calculation</h1>

		<div class="scrollable">

			<p>This is calculation page</p>

		</div>

	</div>

</div>

<div id="right">

	<div class="calculation">

		<form action="${contextPath_A}/rsa-step-one" method="post">

			<div class="submits">

				<input type="submit" value="Confirm&NextStep"> <input
					type="reset" value="Reset">

			</div>

			<h3>Step 1. Compute N as the product of two prime numbers 'p' and 'q':</h3>

			<label for="pNumber_P">Primary number 'p' (between 1 and 1000):</label>

			<input type="number" id="pNumber_P" name="pNumber_P" min="1" max="1000">

			<br> 
			
			<label for="qNumber_P">Primary number 'q' (between 1 and 1000):</label> 

			<input type="number" id="qNumber_P" name="qNumber_P" min="1" max="1000">
			
		</form>

	</div>

</div>