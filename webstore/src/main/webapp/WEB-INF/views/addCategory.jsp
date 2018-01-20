<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp" />
<section>
	<div class="jumbotron">
		<div class="container">
			<h1>Kategoria</h1>
			<p>Dodaj kategorię</p>
		</div>
	</div>
</section>
<section class="container">
	<form:form modelAttribute="newCategory" class="form-horizontal"
		enctype="multipart/form-data">
		<form:errors path="*" cssClass="alert alert-danger" element="div" />
		<fieldset>
			<div class="form-group">
				<label class="control-label col-lg-2 col-lg-2" for="id"><spring:message
						code="addCategory.form.name.label" /></label>
				<div class="col-lg-10">
					<form:input id="name" path="name" type="text"
						class="form:input-large" />
					<form:errors path="name" cssClass="text-danger" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2" for="description"><spring:message
						code="addCategory.form.categoryDescription.label" /></label>
				<div class="col-lg-10">
					<form:textarea id="description" path="description" rows="2" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-lg-2" for="categoryImage"> <spring:message
						code="addCategory.form.categoryImage.label" />
				</label>
				<div class="col-lg-10">
					<form:input id="categoryImage" path="categoryImage" type="file"
						class="form:input-large" />
					<form:errors path="categoryImage" cssClass="text-danger" />
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

