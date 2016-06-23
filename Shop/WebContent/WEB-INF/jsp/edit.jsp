<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="img/favicon.png" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MStore</title>
</head>
<body>
	<div>
		<form action="Controller" method="post">
		<input type="hidden" name="command" value="to-main"/>
		<button type="submit">Back</button>
		</form>
		
		<h1>Edit</h1>
		<hr>
		<form action="Controller" method="post" enctype="multipart/form-data">
			<input type="hidden" name="command" value="edit_product" />

			<p>
				<input type="text" name="name" class="form-control"
					value="${product.name}" placeholder="Name" required>
			</p>

			<p>
				<input type="text" name="price" class="form-control"
					value="${product.price}" placeholder="Price" required>
			</p>

			<p>
				<input type="text" name="description" class="form-control"
					value="${product.desc}" placeholder="Description">
			</p>

			<p>
				<input type="text" name="author" class="form-control"
					value="${product.author}" placeholder="Author" required>
			</p>

			<p>
				<select name="genre" value="" placeholder="Genre" required>
					<option selected value="${product.genre}"><c:out
							value="${product.genre}" /></option>
					<option value="blues">Blues</option>
					<option value="country">Country</option>
					<option value="folk">Folk</option>
					<option value="hiphop">Hiphop</option>
					<option value="instrumental">Instrumental</option>
					<option value="jazz">Jazz</option>
					<option value="pop">Pop</option>
					<option value="reggae">Reggae</option>
					<option value="rock">Rock</option>
					<option value="shanson">Shanson</option>
				</select>
			</p>
			<input type="hidden" name="image" value="${product.image}">
			<p>${image}</p>
			<input type="file" name="file" accept="image/jpeg"> <br>


			<p>
				<input type="hidden" name="id" value="${product.id}" />
			</p>

			<p>
				<label> <c:if test="${not empty message }">
						<fmt:message key="${message}" />
					</c:if>
				</label>
			</p>

			<p>
				<button type="submit">Edit</button>
			</p>
		</form>
	</div>
</body>
</html>