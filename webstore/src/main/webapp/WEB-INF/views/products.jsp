<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="header.jsp" />

<body>
	<section >
		<div class="jumbotron">
			<div class="container slideY">
				<h1>${categoryName}
					<a href="<spring:url value="/categories" />" class="btn btn-default"
						style="padding: right;"> <span
						class="glyphicon-hand-left glyphicon"></span> Powrót
					</a>
				</h1>
				<p>Wszystkie produkty dostępne w naszym sklepie</p>
			</div>
		</div>
	</section>
	<section class="container">

		<div class="row">
			<c:forEach items="${products}" var="product">
				<div class="col-sm-6 col-md-3 appeared" style="padding-bottom: 15px">
					<div class="thumbnail">
						<img
							src="<c:url value="/resource/images/products/P${product.productId}.jpg"></c:url>"
							alt="image" style="width: 100%; max-height: 300px;" />
						<div class="caption">
							<h3>${product.name}</h3>
							<p>${product.description}</p>
							<p>${product.unitPrice}PLN</p>
							<p>Liczba sztuk w magazynie:${product.unitsInStock}</p>
							<p>
								<a
									href=" <spring:url value="/products/product?id=${product.productId}" /> "
									class="btn btn-primary"> <span
									class="glyphicon-info-sign glyphicon" /></span> Szczegóły
								</a>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>
</body>
<jsp:include page="footer.jsp" />