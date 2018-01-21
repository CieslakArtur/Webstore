<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp" />
<section>
	<div class="jumbotron">
		<div class="container">
			<h1>
				Wytwórca
				<a href="<spring:url value="/categories" />"
					class="btn btn-default" style="padding: right;"> <span
					class="glyphicon-hand-left glyphicon"></span> <spring:message
						code="product.form.productBackButton.label" />
				</a>
			</h1>
			<p>Dodaj wytwórcę</p>
		</div>
	</div>
</section>
<section class="container">
	<form:form modelAttribute="newManufacturer" class="form-horizontal"
		enctype="multipart/form-data">
		<form:errors path="*" cssClass="alert alert-danger" element="div" />
		<fieldset>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="id"><spring:message
						code="addManufacturer.form.name.label" /></label>
				<div class="col-lg-10">
					<form:input id="name" path="name" type="text"
						class="form:input-large" />
					<form:errors path="name" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-offset-2 col-lg-10">
					<input type="submit" id="btnAdd" class="btn btn-primary"
						value="Dodaj" />
				</div>
			</div>
		</fieldset>
	</form:form>
	<div class="row">
		<c:forEach items="${manufacturers}" var="manufacturer">
			<div class="col-sm-6 col-md-3 appeared" style="padding-bottom: 15px">
				<div class="thumbnail" style="height: 45px;">${manufacturer.name}<a href="<spring:url value="/categories/manufacturer/delete?manufacturer=${manufacturer.id}" />"
					class="btn btn-danger" style="padding: right; float: right;">Usuń
				</a></div>
				
			</div>
		</c:forEach>
	</div>
</section>
<jsp:include page="footer.jsp" />

