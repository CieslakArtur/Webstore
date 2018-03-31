<!DOCTYPE html>
<html lang="pl-PL">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<title>Opis</title>
<jsp:include page="headerIncludes.jsp" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<section class="container">
		<div class="jumbotron">
			<div class="container">
				<h1>Artur Cieślak</h1>
				<p>nr indeksu: 251861</p>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<strong>Opis serwisu</strong>
			</div>
			<p class="container">
				Prezentowany serwis to sklep internetowy pozwalający na dodawanie i
				monitorowanie wszystkich zakupów przez administratora. Użytkownicy
				posiadający konta mogą zamawiać produkty i monitorować swoje
				zamówienia natomiast pozostali użytkownicy mogą swobodnie przeglądać
				ofertę sklepu internetowego.<br> <br> <strong>Uzytkownik
					1</strong><br> <strong>Login: </strong>User<br> <strong>Haslo:
				</strong>User123 <br> <br> <strong>Uzytkownik 2</strong><br> <strong>Login:
				</strong>User2<br> <strong>Haslo: </strong>User2123
			</p>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<strong>Technologie</strong>
			</div>
			<p class="container">
				Strona internetowa została wykonana z wykorzystaniem następujących
				technologii:<br> <br> <span
					class="glyphicon glyphicon-minus" aria-hidden="true"></span> JAVA 8
				<br> <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				Spring MVC 3.1.4 <br> <span class="glyphicon glyphicon-minus"
					aria-hidden="true"></span> Baza danych MySQL 5.7 <br> <span
					class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				sterownik JDBC mysql 5.1 <br> <span
					class="glyphicon glyphicon-minus" aria-hidden="true"></span> HTML 5
				<br> <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				CSS 3 <br> <span class="glyphicon glyphicon-minus"
					aria-hidden="true"></span> Bootstrap 3.3 <br> <span
					class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				JavaScript
			</p>

		</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<strong>Wymagania stawiane aplikacji</strong>
			</div>
			<ul class="list-group">
				<li class="list-group-item">Serwis może być zrealizowany na
					bazie HTML 4.01 lub XHTML (dopuszczalne jest też użycie XML+XSLT).
					Strony muszą być utworzone poprawnie i mieć poprawne deklaracje
					typu<br> <br>
					<div class="alert alert-success">
						Serwis został wykonany na bazie HTML 5 <strong>(deklaracja
							&lt;!DOCTYPE html&gt;)</strong> oraz został sprawdzony <a target="_blank"
							href="https://validator.w3.org/" class="alert-link">validatorem
							html</a>.
					</div>
				</li>
				<li class="list-group-item">Wymagane jest nietrywialne
					formatowanie przy użyciu CSS. reguł CSS również będzie sprawdzana
					walidatorem.<br> <br>
					<div class="alert alert-success">
						Nietrywialne formatowanie osiągnięto dzięki zastosowaniu
						frameworku <a target="_blank"
							href="http://getbootstrap.com/docs/3.3/" class="alert-link">Bootstrap
							3.3</a>. Poprawność reguł CSS została sprawdzona <a target="_blank"
							href="https://jigsaw.w3.org/css-validator/" class="alert-link">validatorem
							CSS</a>.
					</div>
				</li>
				<li class="list-group-item">Kompatybilność i „łagodna
					degradacja” - serwis powinien funkcjonować poprawnie i wyglądać
					dobrze we wszystkich popularnych przeglądarkach (najnowsze wersje),
					a korzystanie z serwisu powinno być też możliwe – choć zapewne nie
					bez zakłóceń w rodzaju nieudanego formatowania.<br> <br>
					<div class="alert alert-success">
						Serwis został przetestowany na popularnych przeglądarkach
						internetowych takich jak:<br> <span
							class="glyphicon glyphicon-minus" aria-hidden="true"></span>Google
						Chrome<br> <span class="glyphicon glyphicon-minus"
							aria-hidden="true"></span>Mozilla Firefox<br> <span
							class="glyphicon glyphicon-minus" aria-hidden="true"></span>Internet
						Explorer (MS Edge)<br>
					</div>
				</li>
				<li class="list-group-item">Dostępność dla niepełnosprawnych –
					w szczególności powinna być możliwa nawigacja przy użyciu
					oprogramowania czytającego.<br> <br>
					<div class="alert alert-success">Wszystkie obrazki posiadają
						teksty alternatywne a strony tytuły.</div>
				</li>
				<li class="list-group-item">Poza (X)HTML i CSS serwis powinien
					wykorzystywać co najmniej jedną wybraną technologię spośród
					przedstawionych na wykładzie (np. skrypty po stronie serwera i/lub
					klienta, ciasteczka, bazy danych, web serwisy, SSL...)<br> <br>
					<div class="alert alert-success">
						Strona posiada animowany <a target="_blank"
							href="<spring:url value="/" />" class="alert-link">widok
							powiatalny</a> wykonany z zastosowaniem skryptów JS po stronie
						użytkownika. Wszystkie informacje o zakupach i ofertach sklepu
						przechowywane są w relacyjnej bazie danych mySQL.
					</div>
				</li>
				<li class="list-group-item">Serwis powinien zawierać dobrze
					przemyślany, wygodny i spójny system nawigacji. Co więcej, serwis
					powinien być na tyle duży, aby ten system miał sens<br> <br>
					<div class="alert alert-success">
						Aplikacja posiada wygodny system nawigacji widoczny na początku
						każdegej strony.<br> <br> <img
							src="<spring:url value="https://image.ibb.co/irizEn/Przechwytywanie.jpg" />"
							alt="Navigation Bar" style="width: 45%;" border="0"> <br>
						<br> Serwis posiada układ w formie kafelków. Wszystkie
						produkty są pogrupowane w kategorie, które administrator może
						tworzyć lub modyfikować wedle uznania.<br> <br> <img
							src="<spring:url value="https://image.ibb.co/dhKLn7/Przechwytywanie2.jpg" />"
							alt="Category View" style="width: 45%;" border="0"> <br>
						<br> Administrator ma możliwość dodawania produktów,
						kategorii oraz wytwórców do swojego sklepu. <br> <br> <img
							src="<spring:url value="https://image.ibb.co/e7wBun/Przechwytywanie3.jpg" />"
							alt="Special administrator privileges" style="width: 45%;"
							border="0"> <br> <br> Użytkownicy mają możliwość
						dokonywania zakupów w sklepie oraz posiadają wgląd do historii
						swoich transakcji. <br> <br> <img
							src="<spring:url value="https://preview.ibb.co/hE0qLS/Przechwytywanie4.jpg" />"
							alt="User privileges" style="width: 45%;" border="0"> <img
							src="<spring:url value="https://preview.ibb.co/bDOo77/Przechwytywanie5.jpg" />"
							alt="User privileges" style="width: 45%;" border="0"> <br>
						<br> Administrator posiada podgląd historii wszystkich
						zakupów wykonanych w sklepie internetowym przez użytkowników. <br>
						<br> <img
							src="<spring:url value="/resource/images/desc/Przechwytywanie6.jpg" />"
							alt="Special administrator privileges" style="width: 45%;"
							border="0"> <br> <br>
					</div>
				</li>
			</ul>
		</div>
	</section>
</body>
<jsp:include page="footer.jsp" />
</body>