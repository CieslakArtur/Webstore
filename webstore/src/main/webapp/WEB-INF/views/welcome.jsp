<!DOCTYPE html>
<html lang="pl-PL">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<title>Strona startowa</title>
<jsp:include page="headerIncludes.jsp" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<script>
		$('document').ready(function() {
			GREETINGS.animate();
		});
	</script>
	<div id="large-header" class="large-header slideY">
		<canvas id="demo-canvas"></canvas>
		<h1 class="main-title">Witaj w sklepie internetowym!</h1>
		<h1 class="thin">
			Wyjątkowym i jedynym sklepie internetowym

			<spring:url value="/categories" var="market" />
			<br>
			<button class="btn btn-lg" style="margin-top: 20px;"
				onclick="location.href='${market}'">Przejdź do sklepu</button>
		</h1>
	</div>
</body>