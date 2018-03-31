<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<title>Produkty</title>
<jsp:include page="header.jsp" />
<body>
	<section>
		<div class="jumbotron animated_bg_product">
			<div class="container slideY">
				<h1>${categoryName}
					<a href="<spring:url value="/categories" />"
						class="btn btn-default" ><span
						class="glyphicon-hand-left glyphicon"></span>Powrót
					</a>
				</h1>
				<p>Wszystkie produkty dostępne w naszym sklepie</p>
			</div>
		</div>
	</section>
	<section class="container">


		<c:forEach items="${products}" var="product" varStatus="status"
			step="1" begin="0">
			<c:if test="${status.index%4==0}">
				<div class="row">
			</c:if>
			<div class="col-sm-6 col-md-3 appeared" style="padding-bottom: 15px;">
				<div class="thumbnail">
					<c:if test="${empty product.base64Image}">
						<img src="<c:url value="/resource/images/empty.jpg"></c:url>"
							alt="Empty product image" style="width: 100%; height: 200px;" />
					</c:if>
					<c:if test="${not empty product.base64Image}">
						<img src="data:image/jpeg;base64,${product.base64Image}" 
						alt="Product image" style="width: 100%; height: 200px;"/>
					</c:if>
					<div class="caption">
						<h3>${product.name}</h3>
						<div class="scrollable-description">${product.description}</div>
						<p>${product.unitPrice}PLN</p>
						<p>Liczba sztuk w magazynie:${product.unitsInStock}</p>
						<p>
							<a
								href="<spring:url value="/products/product?id=${product.productId}" />"
								class="btn btn-primary"> <span
								class="glyphicon-info-sign glyphicon" /></span> Szczegóły
							</a> 
							<security:authorize access="hasRole('ROLE_ADMIN')">
							<a href="<spring:url value="/products/delete?product=${product.productId}&&category=${categoryId}" />"
								class="btn btn-danger" style="float: right;">Usuń
							</a>
							<a
								href="<spring:url value="/products/edit?product=${product.productId}" />"
								class="btn btn-primary" style="float: right;">Edytuj
							</a>
							</security:authorize>
						</p>
					</div>
				</div>
			</div>
			<c:if test="${status.index%4==3}">
				</div>
			</c:if>
		</c:forEach>

	</section>
</body>
<jsp:include page="footer.jsp" />