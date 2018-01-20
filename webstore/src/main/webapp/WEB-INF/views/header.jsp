<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<script type="text/javascript"
	src="/webstore/resource/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/webstore/resource/js/scripts.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/gsap/1.18.0/TweenMax.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/webstore/resource/css/styles.css" media="screen" />

<div class="navbar navbar-inverse bg-inverse"
	style="margin-bottom: 0px;">
	<a href="<spring:url value="/" />" class="navbar-brand">Home</a>
	<security:authorize
		access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_USER')">
		<a href="<spring:url value="/login" />" class="navbar-brand">Logowanie</a>
		<a href="#" class="navbar-brand">Rejestracja</a>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<a href="<spring:url value="/j_spring_security_logout" />"
			class="navbar-brand">Wyloguj</a>
		<img src="<c:url value="/resource/images/avatar.jpg"></c:url>"
			alt="image" class="navbar-brand" style="width: 50px; height: 50px;" />
		<a class="navbar-brand" style="padding: right;">Admin</a>
		<a href="<spring:url value="/products/add" />" class="navbar-brand">Dodaj produkt</a>
		<a href="<spring:url value="/categories/add" />" class="navbar-brand">Dodaj kategorię</a>
		<a href="<spring:url value="/categories/manufacturer/add" />" class="navbar-brand">Dodaj wytwórcę</a>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_USER')">
		<a href="<spring:url value="/j_spring_security_logout" />"
			class="navbar-brand">Wyloguj</a>
		<img src="<c:url value="/resource/images/avatar.jpg"></c:url>"
			alt="image" class="navbar-brand" style="width: 50px; height: 50px;" />
		<a class="navbar-brand" style="padding: right;">User</a>
	</security:authorize>

	<div class="pull-right" style="padding-right: 50px">
		<c:if test="${Home!='true'}">
			<a href="#" class="navbar-brand"><span class="cart" /></span>Cart</a>
		</c:if>
	</div>
</div>
