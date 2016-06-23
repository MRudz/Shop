<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mtg" uri="/WEB-INF/tld/mytag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MStore</title>
<link rel="stylesheet" href="css/style.css" />
<link rel="icon" type="image/png" href="img/favicon.png" />
<fmt:setLocale value="${sessionScope.local }" />
<fmt:setBundle basename="resources.local" var="loc" />
<fmt:message bundle="${loc}" key="sort.price" var="price" />
<fmt:message bundle="${loc}" key="sort.date" var="date" />
<fmt:message bundle="${loc}" key="author" var="author" />
<fmt:message bundle="${loc}" key="total" var="total" />
<fmt:message bundle="${loc}" key="subtotal" var="subtotal" />
<fmt:message bundle="${loc}" key="shipping" var="shipping" />
<fmt:message bundle="${loc}" key="tax" var="tax" />
<fmt:message bundle="${loc}" key="grandtotal" var="grandtotal" />
<fmt:message bundle="${loc}" key="checkout" var="checkout" />
<fmt:message bundle="${loc}" key="genre" var="genre" />

<%@ include file="jspf/fmt/common_fmt.jspf"%>
</head>
<body>
	<div id="wrapper">

		<%@ include file="jspf/header.jspf"%>

		<div class="middle clearfix center">
			<c:if test="${not empty message }">
				<div class="info">
					<h4>
						<fmt:message bundle="${loc}" key="${message}" />
						!
					</h4>
				</div>
			</c:if>

			<c:if test="${not empty userOrders }">
				<c:forEach var="orders" items="${sessionScope.userOrders}">
					<h1 class="date">
						<c:out value="${date}" />
						: ${orders.date}
					</h1>
					<div class="shopping-cart">
						<c:forEach var="products" items="${orders.productsList}">


							<div class="column-labels">
								<label class="product-image">&nbsp;</label> <label
									class="product-details">&nbsp;</label> <label
									class="product-price"><c:out value="${genre}" /></label> <label
									class="product-author"><c:out value="${author}" /></label> <label
									class="product-download">&nbsp;</label>
							</div>



							<div class="product">
								<div class="product-image">
									<img src="img/albums/<c:out value="${products.image}" />" />
								</div>
								<div class="product-details">
									<div class="product-title">
										<c:out value="${products.name}" />
									</div>
									<p class="product-description">Cool Album</p>
								</div>
								<div class="product-price">
									<c:out value="${products.genre}" />
								</div>
								<div class="product-author">
									<c:out value="${products.author}" />
								</div>


								<div class="product-download">
									www.mstore.com/albums/<c:out value="${products.name}" />.zip
								</div>
							</div>
						</c:forEach>
						<div class="totals">
							<div class="totals-item">
								<label><c:out value="${total}" /></label>
								<div class="totals-value">${orders.sumPrice }</div>
							</div>

						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<div id="footer">

			<a href="#home-link" class="home-link">&nbsp;</a>

			<div class="footer-bottom">
				<div class="center">
					<p>MStore @2016 by MaksimRudz | All Rights Reserved</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>