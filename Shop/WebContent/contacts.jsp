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
<fmt:message bundle="${loc}" key="address.header" var="address" />
<fmt:message bundle="${loc}" key="address" var="our_address" />
<fmt:message bundle="${loc}" key="schema" var="schema" />

</head>
<body>
	<a id="home-link" href="#"></a>
	<div id="wrapper">

		<%@ include file="jspf/header.jspf"%>

		<div class="middle clearfix center">
			<div class="sidebar">
				<%@ include file="jspf/sidebar.jspf"%>

			</div>
			<div class="content-contacts">
				<div class="contacts">
					<div class="title">
						<c:out value="${address}" />
					</div>
					<div class="item">
						<div class="icon icon-location"></div>
						<c:out value="${our_address}" />
						<br>
					</div>
					<div class="item">
						<div class="icon icon-phone"></div>
						+375 (29) 937-99-92
					</div>
					<div class="item">
						<div class="icon icon-email"></div>
						<a href="mailto:mixp95@gmail.com">mixp95@gmail.com</a>
					</div>
				</div>
				<div class="m-title">
					<c:out value="${schema}" />
				</div>
				<div class="map">
					<iframe
						src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2349.348989701224!2d27.591929615489803!3d53.92554348010449!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbcf09584a03b5%3A0x7dbad5687dcd4d7a!2z0ZbQvdGC0Y3RgNC90LDRgiDihJYxINCR0JTQo0nQoCwg0LLRg9C7adGG0LAg0K_QutGD0LHQsCDQmtC-0LvQsNGB0LAgMjgsINCc0ZbQvdGB0LosINCR0LXQu9Cw0YDRg9GB0Yw!5e0!3m2!1sru!2sru!4v1458651465279"
						width="500" height="450" frameborder="0" style="border: 5px"
						allowfullscreen></iframe>
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

