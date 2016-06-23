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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css" />
<link rel="icon" type="image/png" href="img/favicon.png" />
<script src="js/jquery-1.12.3.min.js" type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"
	type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript" src="js/cusel-min-2.5.js"></script>
<script>
	$(function() {
		$("#slider-range").slider({
			range : true,
			min : 0,
			max : 30,
			values : [ 0, 30 ],
			slide : function(event, ui) {
				$("#amount").val("$" + ui.values[0] + " - $" + ui.values[1]);
			}
		});
		$("#amount").val(
				"$" + $("#slider-range").slider("values", 0) + " - $"
						+ $("#slider-range").slider("values", 1));

		/* select style */
		var params = {
			changedEl : "select",
			visRows : 5,
			scrollArrows : true
		}
		cuSel(params);
	});
</script>

<fmt:setLocale value="${sessionScope.local }" />
<fmt:setBundle basename="resources.local" var="loc" />
<%@ include file="jspf/fmt/common_fmt.jspf"%>
<fmt:message bundle="${loc}" key="cart.add" var="cart_add" />
<fmt:message bundle="${loc}" key="sale" var="sale" />
<fmt:message bundle="${loc}" key="sort" var="sort" />
<fmt:message bundle="${loc}" key="sort.name" var="name" />
<fmt:message bundle="${loc}" key="sort.date" var="date" />
<fmt:message bundle="${loc}" key="sort.price" var="price" />
<fmt:message bundle="${loc}" key="sort.author" var="author" />
<fmt:message bundle="${loc}" key="filter" var="filter" />
<fmt:message bundle="${loc}" key="filter.button" var="filter_button" />
<fmt:message bundle="${loc}" key="message.nothing_found"
	var="no_products" />
</head>
<body>
	<a id="home-link" href="#"></a>
	<div id="wrapper">

		<%@ include file="jspf/header.jspf"%>



		<div class="middle clearfix center">
			<div class="sidebar">
				<div class="sorting  clearfix">
					<div class="s-list">
						<a href="#" class="ic-squares">&nbsp;</a> <a href="#"
							class="ic-list">&nbsp;</a>
					</div>

					<div class="sel-sorting">
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="sort-products" /> <select
								name="type">
								<option value="name"><c:out value="${name}" /></option>
								<option value="author"><c:out value="${author}" /></option>
								<option value="price"><c:out value="${price}" /></option>
							</select>
							<button class="add-cart" type="submit">
								<c:out value="${sort}" />
							</button>
						</form>
					</div>

				</div>
				<h4 class="s-title">
					<c:out value="${filter}" />
					<span class="s-title-br"></span>
				</h4>
				<div class="s-slider">
					<div id="slider-range"></div>
					<p>
						<label for="amount"><c:out value="${price}" /></label>
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="price-filter" /> <input
								type="text" name="amount" id="amount" readonly
								style="border: 0; color: #f6931f; font-weight: bold;" />
							<button type="submit" class="right add-cart">
								<c:out value="${filter_button}" />
							</button>
						</form>
					</p>
				</div>
				<h4 class="s-title">
					<c:out value="${best}" />
					<span class="s-title-br"></span>
				</h4>
				<div class="s-products">
					<c:forEach var="bestSellers" items="${bestSellers}">
						<div class="s-products-item clearfix">
							<div class="s-products-img">
								<a href="#"><img src="img/albums/${bestSellers.image}"
									alt="" /></a>
							</div>
							<div class="s-products-info">
								<h6>
									<a href="#">${bestSellers.name }</a>
								</h6>
	
								<span class="comments"><i class="ic-comment"></i>${bestSellers.author}</span>
							</div>
						</div>
					</c:forEach>
				</div>
				<%@ include file="jspf/sidebar.jspf"%>


			</div>
			<div class="content">
				<c:if test="${not empty message }">
					<div class="info">
						<h4>
							<fmt:message bundle="${loc}" key="${message}" />
							!
						</h4>
					</div>
				</c:if>

				<div class="product-catalog clearfix">
					<c:if test="${empty products}">
						<div class="info">
							<h4>
								<c:out value="${no_products }" />
								!
							</h4>
						</div>
					</c:if>
					<c:if test="${not empty products }">
						<c:forEach var="product" items="${products}">
							<div class="products">
								<div class="p-img">
									<c:if test="${product.sale == true }">
										<span class="sale"><c:out value="${sale}" /></span>
									</c:if>
									<img src="img/albums/${product.image}" height="220" width="220"
										alt="" />
								</div>
								<div class="p-footer">
									<h6>
										${product.name} <span> ${product.author}</span>
									</h6>

									<dl class="star-rating">
										<dd>
											<ol>
												<li><a
													href="Controller?command=rate-product&rate=1&id=${product.id}"
													class="star1">Ужасно</a></li>
												<li><a
													href="Controller?command=rate-product&rate=2&id=${product.id}"
													class="star2">Плохо</a></li>
												<li><a
													href="Controller?command=rate-product&rate=3&id=${product.id}"
													class="star3">Нормально</a></li>
												<li class="current" style="width: ${product.rate*20}px"><a
													href="Controller?command=rate-product&rate=4&id=${product.id}"
													class="star4">Хорошо</a></li>
												<li><a
													href="Controller?command=rate-product&rate=5&id=${product.id}"
													class="star5">Отлично</a></li>
											</ol>
										</dd>
									</dl>
									<p>
										The fifth album of britain rock group <strong>Bring
											Me The Horizon</strong>
									</p>
									<div class="p-footer-price">
										<c:if test="${product.sale == true }">
											<span class="price-sale">$${product.price }</span>
											<span class="price-new">${product.salePrice}</span>
										</c:if>

										<c:if test="${product.sale == false }">
											<span class="price">$${product.price }</span>
										</c:if>
										<form action="Controller" method="post">
											<input type="hidden" name="command" value="add-to-basket" />
											<input type="hidden" name="id" value="${product.id }" />
											<button type="submit" class="right add-cart">
												<c:out value="${cart_add}" />
											</button>
										</form>
									</div>
									<c:if test="${sessionScope.login.role eq 'admin' }">
										<div class="admin">
											<form action="Controller" method="post">
												<input type="hidden" name="id" value='${product.id}' /> <input
													type="hidden" name="command" value="delete_product" /> <input
													type="hidden" name="image" value="${product.image}" />
												<button type="submit" class="delete-button">&nbsp;</button>
											</form>


											<form action="Controller" method="post">
												<input type="hidden" name="id" value="${product.id}" /> <input
													type="hidden" name="command" value="to-edit" />
												<button type="submit" class="edit-button">&nbsp;</button>
											</form>

											<form action="Controller" method="post">
												<input type="hidden" name="id" value="${product.id}" /> <input
													type="hidden" name="command" value="change-sale" /> <input
													type="hidden" name="sale" value="${product.sale }" />
												<button type="submit" class="sale-button">&nbsp;</button>
											</form>
										</div>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>

				<div class="pagination">
					<ul class="clearfix">
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">6</a></li>
						<li><a href="#">&raquo;</a></li>
					</ul>
				</div>

			</div>
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


</body>
</html>
