<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html charset=UTF-8">
      <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
      <title>Witaj</title>
   </head>
   <body>
      <section>
         <div class="jumbotron">
            <div class="container">
            <img
							src="<c:url value="/resource/images/productNotFound.jpg"></c:url>"
							alt="image" style="width: 150px; height=150px;" />  
							<h1 class="alert alert-danger" style="padding:right"> ${description}</h1> 
            </div>
         </div>
      </section>
      <section>
         <div class="container">
            <p>
               <a href="<spring:url value="${returnURL}" />" class="btn btn-primary">
                  <span class="glyphicon-hand-left glyphicon"></span> ${btnName}
               </a>
            </p>
          </div>
      </section>
   </body>
</html>