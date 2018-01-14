<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="header.jsp" />
<body>
	<c:if test="${!error}">
		<section class="slideY">
	</c:if>
	<c:if test="${error}">
		<section>
	</c:if>
	<div class="jumbotron">
		<div class="container">
			<h1>Logowanie</h1>
			<p>Jeżeli nie posiadasz konta, zarejestruj się</p>
		</div>
	</div>
	</section>
	<div class="container">
		<div class="row">
			<c:if test="${!error}">
				<div class="col-md-4 col-md-offset-4 appeared">
			</c:if>
			<c:if test="${error}">
				<div class="col-md-4 col-md-offset-4">
			</c:if>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Zaloguj się</h3>
				</div>
				<div class="panel-body">
					<c:if test="${not empty error}">
						<div class="alert alert-danger">
							<spring:message
								code="AbstractUserDetailsAuthenticationProvider.badCredentials" />
							<br />
						</div>
					</c:if>
					<form action="<c:url value="/j_spring_security_check"></c:url>"
						method="post">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="Nazwa użytkownika"
									name='j_username' type="text">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Hasło"
									name='j_password' type="password" value="">
							</div>
							<input class="btn btn-lg btn-success btn-block" type="submit"
								value="Zaloguj się"> 
								<a href=" <spring:url value="/categories" /> "
								class="btn btn-lg btn-warning btn-block"> <span
								class="glyphicon-hand-left glyphicon" /></span> Powrót do sklepu
							</a>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
