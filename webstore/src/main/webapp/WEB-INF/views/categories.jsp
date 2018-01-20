<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="header.jsp" />
<body>


	<section class="slideY">
		<div class="jumbotron">
			<div class="container">
				<h1>Kategorie</h1>
				<p>Wszystkie kategorie</p>
			</div>
		</div>
	</section>
	<section class="container">
		<div class="row">
			<c:forEach items="${categories}" var="category">
				<div class="col-sm-6 col-md-3 appeared" style="padding-bottom: 15px">
					<div class="thumbnail">
						<c:if test="${empty category.base64Image}">
							<img src="<c:url value="/resource/images/empty.jpg"></c:url>"
								alt="image" style="width: 100%; max-height: 300px;" />
						</c:if>
						<c:if test="${not empty category.base64Image}">
							<img src="data:image/jpeg;base64,${category.base64Image}" />
						</c:if>
						<div class="caption">
							<h3>${category.name}</h3>
							<p>${category.description}</p>
							<a href=" <spring:url value="/products/${category.id}" /> "
								class="btn btn-primary"> <span
								class="glyphicon-info-sign glyphicon" /></span> Przejdź do kategorii
							</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>

</body>
<jsp:include page="footer.jsp" />

