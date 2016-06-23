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
<%@ include file="../../jspf/fmt/common_fmt.jspf"%>
<fmt:message bundle="${loc}" key="address.header" var="address" />
<fmt:message bundle="${loc}" key="name" var="name" />
<fmt:message bundle="${loc}" key="sname" var="sname" />
<fmt:message bundle="${loc}" key="phone" var="phone" />
<fmt:message bundle="${loc}" key="email" var="email" />
<fmt:message bundle="${loc}" key="repeat" var="repeat" />


</head>
<body>
	<a id="home-link" href="#"></a>
	<div id="wrapper">

		<%@ include file="../../jspf/header.jspf"%>


		<div class="middle clearfix center">

			<div class="register content">
				<c:if test="${not empty message }">
					<div class="validation">
						<h4>
							<fmt:message bundle="${loc}" key="${message}" />
							!
						</h4>
					</div>
				</c:if>
				<form action="Controller" method="post">
					<fieldset class="form-field">
						<input type="hidden" name="command" value="login" /> <input
							type='text' name="login" placeholder='<c:out value="${login}" />' />
						<input type='password' name="password"
							placeholder='<c:out value="${password}" />' /> <input
							type="submit" value="<c:out value="${enter}" />" /> <br> <label>

						</label>
					</fieldset>
				</form>
				<form action="Controller" method="post">
					<fieldset class="form_field">
						<input type="hidden" name="command" value="register-user" /> <input
							type="hidden" name="number" value="${generator.randomNumber}" />
						<p>
							<label for="login"><c:out value="${login}" /></label> <input
								type="text" name="login" placeholder="От 3 до 16 символов" minlength="3" maxlength="16" required /><span></span>
						</p>
						<p>
							<label for="name"><c:out value="${name}" /></label> <input
								type="text" name="name" maxlength="25" pattern="[A-Za-z\s]+$"
								required /><span></span>
						</p>

						<p>
							<label for="sname"><c:out value="${sname}" /></label> <input
								type="text" name="sname" maxlength="25" pattern="[A-Za-z\s]+$"
								required /><span></span>
						</p>

						<p>
							<label for="city"><c:out value="${address}" /></label> <input
								type="text" name="address" maxlength="25" pattern="[A-Za-z\s]+$" /><span></span>
						</p>

						<p>
							<label for="phone"><c:out value="${phone}" /></label> <input
								type="text" name="phone" maxlength="25" placeholder="375xxxxxxxxx"pattern="^375[0-9]{9}$"
								required /><span></span>
						</p>

						<p>
							<label for="email"><c:out value="${email}" /></label> <input
								type="email" name="email" maxlength="25" required /><span></span>
						</p>

						<p>
							<label for="password"><c:out value="${password}" /></label> <input
								type="password" name="password" placeholder="От 5 до 25 символов" pattern="\w{5,25}" required /><span></span>
						</p>

						<p>
							<label for="repeat"><c:out value="${repeat}" /></label> <input
								type="password" name="repeat" pattern="\w{5,25}" required /><span></span>
						</p>

						<button class="button7" type="submit">
							<c:out value="${register}" />
						</button>

					</fieldset>
				</form>

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
