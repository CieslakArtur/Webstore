<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div class="navbar navbar-inverse bg-inverse" style="margin-bottom: 0px;">
	<a href="<spring:url value="/" />" class="navbar-brand">Home</a> <a
		href="<spring:url value="/categories" />" class="navbar-brand">Sklep</a>
	<security:authorize
		access="!hasRole('ROLE_ADMIN') && !hasRole('ROLE_USER')">
		<a href="<spring:url value="/login" />" class="navbar-brand"
			style="float: right;">Logowanie</a>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<a href="<spring:url value="/description" />" class="navbar-brand"
			style="float: left;"> <span class="glyphicon glyphicon-info-sign"
			aria-hidden="true" style="margin-right: 5px;"></span>Opis serwisu
		</a>
		<a href="<spring:url value="/j_spring_security_logout" />"
			class="navbar-brand" style="float: right;">Wyloguj</a>
		<img src="<c:url value="/resource/images/avatar.jpg"></c:url>"
			alt="Avatar" class="navbar-brand"
			style="width: 50px; height: 50px; float: right;" />
		<a class="navbar-brand" style="float: right;">Admin</a>
		<a href="<spring:url value="/products/add" />" class="navbar-brand"
			style="margin-left: 50px;">Dodaj produkt</a>
		<a href="<spring:url value="/categories/add" />" class="navbar-brand">Dodaj
			kategorię</a>
		<a href="<spring:url value="/categories/manufacturer/add" />"
			class="navbar-brand">Dodaj wytwórcę</a>
	</security:authorize>
	<security:authorize access="hasRole('ROLE_USER')">
		<a href="<spring:url value="/j_spring_security_logout" />"
			class="navbar-brand" style="float: right;">Wyloguj</a>
		<img src="<c:url value="/resource/images/avatar.jpg" ></c:url>"
			alt="Avatar" class="navbar-brand"
			style="width: 50px; height: 50px; float: right;" />
		<a class="navbar-brand" style="float: right;">User</a>
	</security:authorize>

	<div class="pull-right">
		<c:if test="${Home!='true'}">
			<a href="<spring:url value="/orders" />" class="navbar-brand"><span
				class="cart"></span>Cart</a>
		</c:if>
	</div>
</div>
