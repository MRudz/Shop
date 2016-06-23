<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="mtg" uri="/WEB-INF/tld/mytag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width-device-width, initial-scale=1.0" />
<title>MStore</title>
<link rel="stylesheet" href="css/style.css" />
<link rel="icon" type="image/png" href="img/favicon.png" />
<fmt:setLocale value="${sessionScope.local }" />
<fmt:setBundle basename="resources.local" var="loc" />
<%@ include file="jspf/fmt/common_fmt.jspf"%>
<fmt:message bundle="${loc}" key="sort.price" var="price" />
<fmt:message bundle="${loc}" key="remove" var="remove" />
<fmt:message bundle="${loc}" key="author" var="author" />
<fmt:message bundle="${loc}" key="total" var="total" />
<fmt:message bundle="${loc}" key="subtotal" var="subtotal" />
<fmt:message bundle="${loc}" key="shipping" var="shipping" />
<fmt:message bundle="${loc}" key="tax" var="tax" />
<fmt:message bundle="${loc}" key="grandtotal" var="grandtotal" />
<fmt:message bundle="${loc}" key="checkout" var="checkout" />
<fmt:message bundle="${loc}" key="genre" var="genre" />

<fmt:message bundle="${loc}" key="empty_basket" var="empty_basket" />
</head>
<body onload="recalculateCart()">
	<a id="home-link" href="#"></a>
	<div id="wrapper">

		<%@ include file="jspf/header.jspf"%>


		<div class="middle clearfix center">
			<c:if test="${not empty message }">
				<div class="success">
					<h4>
						<fmt:message bundle="${loc}" key="${message}" />
						!
					</h4>
				</div>
			</c:if>
			<c:if test="${empty sessionScope.basket}">
				<div class="info">
					<h4>
						<c:out value="${empty_basket}" />
						!
					</h4>

				</div>
			</c:if>

			<c:if test="${not empty sessionScope.basket }">
				<div class="shopping-cart">

					<div class="column-labels">
						<label class="product-image">&nbsp;</label> <label
							class="product-details"></label>&nbsp; <label
							class="product-price"><c:out value="${genre}" /></label> <label
							class="product-author"><c:out value="${author }" /></label> <label
							class="product-removal">&nbsp;</label> <label
							class="product-line-price"><c:out value="${price}" /></label>
					</div>

					<c:forEach var="basket" items="${basket}">

						<div class="product">
							<div class="product-image">
								<img src="img/albums/${basket.image}" />
							</div>
							<div class="product-details">
								<div class="product-title">${basket.name}</div>
								<p class="product-description">
									The fifth album of britain rock group <strong>Bring Me
										The Horizon</strong>
								</p>
							</div>
							<div class="product-price">${basket.genre}</div>
							<div class="product-author">${basket.author}</div>
							<div class="product-removal">
								<form action="Controller" method="post">
									<input type="hidden" name="command" value="delete-from-basket" />
									<input type="hidden" name="id" value="${basket.id}" />
									<button type="submit" class="remove-product">
										<c:out value="${remove}" />
									</button>
								</form>
							</div>
							<div class="product-line-price">${basket.price }</div>
						</div>

					</c:forEach>
					<div class="totals">
						<div class="totals-item">
							<label><c:out value="${total}" /></label>
							<div class="totals-value" id="cart-subtotal">&nbsp;</div>
						</div>
						<div class="totals-item">
							<label><c:out value="${tax}" /> (5%)</label>
							<div class="totals-value" id="cart-tax">&nbsp;</div>
						</div>
						<div class="totals-item totals-item-total">
							<label><c:out value="${grandtotal}" /></label>
							<div class="totals-value" id="cart-total">&nbsp;</div>
						</div>
					</div>

					<a href="Controller?command=to-confirm" class="checkout"><c:out
							value="${checkout}" /></a>

				</div>
			</c:if>
		</div>
	</div>

	<div id="footer">
		<a href="#home-link" class="home-link">&nbsp;</a>

		<div class="footer-bottom">
			<div class="center">
				<p>MStore @2016 by MaksimRudz | All Rights Reserved</p>
			</div>
		</div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script src="js/index.js"></script>
</body>
</html>
