<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp" />

<section>
	<div class="jumbotron">
		<div class="container">
			<h1>
				Produkty <a
					href="<spring:url value="/categories" />"
					class="btn btn-default" style="padding: right;"> <span
					class="glyphicon-hand-left glyphicon"></span> <spring:message
						code="product.form.productBackButton.label" />
				</a>
			</h1>
			<p>Dodaj produkty</p>
		</div>
	</div>
</section>
<section class="container">
	<form:form modelAttribute="newProduct" class="form-horizontal"
		enctype="multipart/form-data">
		<form:errors path="*" cssClass="alert alert-danger" element="div" />
		<fieldset>
			<form:hidden path="productId" />
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="productId"><spring:message
						code="addProduct.form.name.label" /></label>
				<div class="col-lg-10">
					<form:input id="name" path="name" type="text"
						class="form:input-large" />
					<form:errors path="name" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="productId">Cena [PLN]</label>
				<div class="col-lg-10">
					<form:input id="unitPrice" path="unitPrice" type="text"
						class="form:input-large" />
					<form:errors path="unitPrice" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="productId">Marka</label>
				<div class="col-lg-10">
					<select id="manufacturer" name="manufacturer"
						style="max-height: 100px;">
						<c:forEach items="${manufacturers}" var="manufacturer">
							<c:if test="${newProduct.manufacturer==manufacturer.id}">
								<option value="${manufacturer.id}" selected="selected">${manufacturer.name}</option>
							</c:if>
							<c:if test="${newProduct.manufacturer!=manufacturer.id}">
								<option value="${manufacturer.id}">${manufacturer.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="productId">Kategoria</label>
				<div class="col-lg-10">
					<select id="category" name="category" style="max-height: 100px;">
						<c:forEach items="${categories}" var="category">
							<c:if test="${newProduct.category==category.id}">
								<option value="${category.id}" selected="selected">${category.name}</option>
							</c:if>
							<c:if test="${newProduct.category!=category.id}">
								<option value="${category.id}">${category.name}</option>
							</c:if>
						</c:forEach>
					</select>
					<form:errors path="category" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="productId">Liczba
					sztuk w magazynie</label>
				<div class="col-lg-10">
					<form:input id="unitsInStock" path="unitsInStock" type="text"
						class="form:input-large" />
					<form:errors path="unitsInStock" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2" for="description" >Opis</label>
				<div class="col-lg-10">
					<form:textarea id="description" path="description" rows="3" style="min-width: 300px;"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2" for="condition">Stan</label>
				<div class="col-lg-10">
					<form:radiobutton path="condition" value="new" checked="checked" />
					Nowy
					<form:radiobutton path="condition" value="used" />
					Używany
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2" for="productImage"> <spring:message
						code="addProdcut.form.productImage.label" />
				</label>
				<div class="col-lg-10">
					<form:input id="productImage" path="productImage" type="file"
						class="form:input-large" />
					<form:errors path="productImage" cssClass="text-danger" />
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
</section>
<jsp:include page="footer.jsp" />

