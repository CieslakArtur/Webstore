<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="header.jsp" />

<section>
	<div class="jumbotron animated_bg_product">
		<div class="container slideY">
			<h1>${product.name}
				<a href="<spring:url value="/categories" />"
					class="btn btn-default" style="padding: right;"> <span
					class="glyphicon-hand-left glyphicon"></span> <spring:message
						code="product.form.productBackButton.label" />
				</a>
			</h1>
			<p>${product.manufacturer}</p>
		</div>
	</div>
</section>
<section class="container">
	<div class="row">
		<div class="col-md-5">
			<c:if test="${empty product.base64Image}">
				<img src="<c:url value="/resource/images/empty.jpg"></c:url>"
					alt="image" style="max-width: 300px; max-height: 300px;" />
			</c:if>
			<c:if test="${not empty product.base64Image}">
				<img src="data:image/jpeg;base64,${product.base64Image}" 
				alt="image" style="max-width: 300px; max-height: 300px;"/>
			</c:if>
		</div>
		<div class="col-md-5">
			<h3>${product.name}</h3>
			<p>${product.description}</p>
			<p>
				<strong><spring:message code="product.form.productId.label" /></strong>
				<span class="labellabel-warning">${product.productId}</span>
			</p>
			<p>
				<strong><spring:message
						code="product.form.productManufacturer.label" /></strong>:
				${product.manufacturer}
			</p>
			<p>
				<strong><spring:message
						code="product.form.productCategory.label" /></strong>: ${product.category}
			</p>
			<p>
				<strong><spring:message
						code="product.form.productUnitsInStock.label" /></strong>:${product.unitsInStock}
			</p>
			<h4>${product.unitPrice}<spring:message
					code="product.form.productCurrency.label" />
			</h4>
			<p>
				<a href="<spring:url value="/orders/add?id=${product.productId}" />" class="btn btn-warning btn-large"> <span
					class="glyphicon-shopping-cart glyphicon"></span> <spring:message
						code="product.form.productOrderButton.label" />
				</a> 
			</p>
		</div>
	</div>
</section>
<jsp:include page="footer.jsp" />