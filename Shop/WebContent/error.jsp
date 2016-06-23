<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="img/favicon.png" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MStore</title>
<fmt:setLocale value="${sessionScope.local }" />
<fmt:setBundle basename="resources.local" var="loc" />
</head>
<body>
	<h1 align="center">Error page</h1>
	<c:choose>
		<c:when test="${not empty message }">
			<h1 align="center">
				<fmt:message bundle="${loc }" key="${message}" />
			</h1>
		</c:when>
		<c:otherwise>
			<h1 align="center">Error</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>