<!DOCTYPE html>
<html lang="pl-PL">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<title>Zamowienie</title>
<jsp:include page="headerIncludes.jsp" />
</head>
<body>
<jsp:include page="header.jsp" />
<section>
	<div class="jumbotron animated_bg_product">
		<div class="container slideY">
			<h1>
				Zamówienie <a href="<spring:url value="/products/product?id=${product.productId}" />"
					class="btn btn-default"> <span
					class="glyphicon-hand-left glyphicon"></span> <spring:message
						code="product.form.productBackButton.label" />
				</a>
			</h1>
			<p>${product.name}</p>
		</div>
	</div>
</section>
<section class="container">

		<div class="col-md-5">
			<c:if test="${empty product.base64Image}">
				<img src="<c:url value="/resource/images/empty.jpg"></c:url>"
					alt="Empty product image" style="max-width: 300px; max-height: 300px;" />
			</c:if>
			<c:if test="${not empty product.base64Image}">
				<img src="data:image/jpeg;base64,${product.base64Image}" 
				alt="Product image" style="max-width: 300px; max-height: 300px;"/>
			</c:if>
		</div>
	<form:form modelAttribute="newOrder" class="form-horizontal"
		enctype="multipart/form-data">
		<form:errors path="*" cssClass="alert alert-danger" element="div" />
		<form:hidden path="productId" />
		<fieldset>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="unitsInOrder"><spring:message
						code="addOrder.form.unitsInOrder.label" /></label>
				<div class="col-lg-10">
					<form:input id="unitsInOrder" path="unitsInOrder" type="text"
						class="form:input-large" />
					<form:errors path="unitsInOrder" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="customerName"><spring:message
						code="addOrder.form.name.label" /></label>
				<div class="col-lg-10">
					<form:input id="customerName" path="customerName" type="text"
						class="form:input-large" />
					<form:errors path="customerName" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="customerSurname"><spring:message
						code="addOrder.form.surname.label" /></label>
				<div class="col-lg-10">
					<form:input id="customerSurname" path="customerSurname" type="text"
						class="form:input-large" />
					<form:errors path="customerSurname" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="address"><spring:message
						code="addOrder.form.address.label" /></label>
				<div class="col-lg-10">
					<form:input id="address" path="address" type="text"
						class="form:input-large" />
					<form:errors path="address" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="email"><spring:message
						code="addOrder.form.email.label" /></label>
				<div class="col-lg-10">
					<form:input id="email" path="email" type="text"
						class="form:input-large" />
					<form:errors path="email" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<input type="submit" id="btnAdd" class="btn btn-primary"
						value="Zamów" />
				</div>
			</div>
		</fieldset>

	</form:form>
</section>
<jsp:include page="footer.jsp" />
</body>

