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
	<div class="admin-page">
		<h1>Users</h1>
		<hr>
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="all-users" />
			<button type="submit">All users</button>
		</form>
		<c:if test="${not empty sessionScope.users }">
			<table>
				<thead>
					<tr>
						<th>Login</th>
						<th>FName</th>
						<th>LName</th>
						<th>Address</th>
						<th>Email</th>
						<th>Role</th>
						<th>Blacklist</th>
						<th>Phone</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="users" items="${sessionScope.users}">
						<tr>
							<td><c:out value="${users.login}" /></td>
							<td><c:out value="${users.name}" /></td>
							<td><c:out value="${users.sname}" /></td>
							<td><c:out value="${users.address}" /></td>
							<td><c:out value="${users.email}" /></td>
							<td><c:out value="${users.role}" /></td>
							<td><c:out value="${users.blackList}" /></td>
							<td><c:out value="${users.phone }" /></td>
							<c:if test="${users.role eq 'user' }">
								<c:if test="${users.blackList eq false }">
									<td style="width: 110px; padding-bottom: 0px">
										<form action="Controller" method="post">
											<input type="hidden" name="command" value="add-to-blacklist" />
											<input type="hidden" name="login" value="${users.login }" />
											<button type="submit">Add to Blacklist</button>
										</form>
									</td>
								</c:if>
								<c:if test="${users.blackList eq true }">
									<td style="width: 110px; padding-bottom: 0px">
										<form action="Controller" method="post">
											<input type="hidden" name="command"
												value="remove-from-blacklist" /> <input type="hidden"
												name="login" value="${users.login }" />
											<button type="submit">Remove from Blacklist</button>
										</form>
									</td>
								</c:if>
								<td style="width: 70px; padding-bottom: 0px">
									<form action="Controller" method="post">
										<input type="hidden" name="command" value="delete-user" /> <input
											type="hidden" name="login" value="${users.login }" />
										<button type="submit">Delete user</button>
									</form>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<h1>Products</h1>
		<hr>
		<form action="Controller" method="post" enctype="multipart/form-data">
			<input type="hidden" name="command" value="add_product" /> <input
				type="hidden" name="number" value="${generator.randomNumber}" />

			<p>
				<input type="text" name="name" value="" placeholder="Name" required />
			</p>

			<p>
				<input type="text" name="price" value="" placeholder="Price 12.34"
					required />
			</p>

			<p>
				<input type="text" name="description" value=""
					placeholder="Description" />
			</p>

			<p>
				<select name="genre" value="" placeholder="Genre" required>
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

			<p>
				<input type="text" name="author" value="" placeholder="Author"
					required />
			</p>

			<p>Image</p>
			<input type="file" name="file" accept="image/jpeg" /> <br>



			<p>
				<label> <c:if test="${not empty message }">
						<fmt:message key="${message}" />
					</c:if>
				</label>
			</p>

			<p>
				<button type="submit">Add product</button>
			</p>
		</form>

		<%-- <h2>Orders</h2>
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="all-orders" />
		<button type="submit">All orders</button>
	</form>
	--%>
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="to-main" />
			<button type="submit">Back</button>
		</form>

	</div>
</body>
</html>