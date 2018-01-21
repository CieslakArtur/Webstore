<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
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
		<c:forEach items="${categories}" var="category" varStatus="status"
			step="1" begin="0">
			<c:if test="${status.index%4==0}">
				<div class="row">
			</c:if>
			<div class="col-sm-6 col-md-3 appeared" style="padding-bottom: 15px">
				<div class="thumbnail">
					<c:if test="${empty category.base64Image}">
						<img src="<c:url value="/resource/images/empty.jpg"></c:url>"
							alt="image" style="width: 100%; height: 200px;" />
					</c:if>
					<c:if test="${not empty category.base64Image}">
						<img src="data:image/jpeg;base64,${category.base64Image}"
							alt="image" style="width: 100%; height: 200px;" />
					</c:if>
					<div class="caption">
						<h3>${category.name}</h3>
						<p>${category.description}</p>
						<a href=" <spring:url value="/products/${category.id}" /> "
							class="btn btn-primary"> <span
							class="glyphicon-info-sign glyphicon" /></span> Przejdź
						</a>
						<security:authorize access="hasRole('ROLE_ADMIN')">
							<a
								href="<spring:url value="/categories/delete?category=${category.id}" />"
								class="btn btn-danger" style="padding: right; float: right;">Usuń
							</a>
							<a
								href="<spring:url value="/categories/edit?category=${category.id}" />"
								class="btn btn-primary" style="padding: right; float: right;">Edytuj
							</a>
						</security:authorize>
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

