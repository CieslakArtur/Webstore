<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:include page="header.jsp" />

<section>
	<div class="jumbotron animated_bg_category">
		<div class="container slideY">
			<h1>${user}
				<a href="<spring:url value="/categories" />" class="btn btn-default"
					style="padding: right;"> <span
					class="glyphicon-hand-left glyphicon"></span> <spring:message
						code="product.form.productBackButton.label" />
				</a>
			</h1>
			<p>${title}</p>
		</div>
	</div>
</section>
<section class="container">
	<table class="table">
		<thead>
			<tr>
				<th>Produkt</th>
				<th>Cena</th>
				<th>Ilość</th>
				<th>Suma</th>
				<th>Data zamówienia</th>
				<th>Imię</th>
				<th>Nazwisko</th>
				<th>Adres</th>
				<th>Email</th>
				<th>Użytkownik</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orders}" var="order">
				<tr>
					<td>${order.productName}</td>
					<td>${order.productPrice}</td>
					<td>${order.unitsInOrder}</td>
					<td>${order.productPrice*order.unitsInOrder}</td>
					<td>${order.date}</td>
					<td>${order.customerName}</td>
					<td>${order.customerSurname}</td>
					<td>${order.address}</td>
					<td>${order.email}</td>
					<td>${order.sessionUser}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</section>
<jsp:include page="footer.jsp" />