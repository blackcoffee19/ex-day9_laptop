<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List Laptop</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
	crossorigin="anonymous"></script>

</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-6 col-md-8 mx-auto">
				<c:if test="${session.getAttribute('msg')!=null}}">
					<p class="alert bg-success mt-3"><%= session.getAttribute("msg")%></p>
				</c:if>
				<c:if test="${session.getAttribute('error')!=null}}">
					<p class="alert bg-danger mt-3"><%= session.getAttribute("error")%></p>
				</c:if>
				<h1 class="my-3">List Laptop</h1>
				<a href="Controller?a=DisplayCreate" class="btn btn-outline-primary">Create
					New</a>
				<hr />
				<form action="Controller">
				<input type="hidden" name="a" value="search" />
					<div class="row g-3">
						<div class="col-5">
							<input type="text" name="min" class="form-control" placeholder="Min" 
							<% if(request.getAttribute("min")!=null){
							%>
							value="<%= request.getAttribute("min")%>"
							<%} %>
							/>
							
						</div>
						<div class="col-5">
							<input type="text" name="max" class="form-control" placeholder="Max" 
							<% if(request.getAttribute("max")!=null){
							%>
							value="<%= request.getAttribute("max")%>"
							<%} %>
							/>
						</div>
						<div class="col-2">
							<input type="submit" value="Search" class="btn btn-primary"/>
						</div>
					</div>
				</form>
				<hr />
				<table class="table">
					<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Price</th>
							<th>Description</th>
							<th>Image</th>
							<th colspan="2">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${laptops}" var="lap">
							<tr>
								<td>${lap.id}</td>
								<td>${lap.name}</td>
								<td>${lap.price}</td>
								<td>${lap.description}</td>
								<td>
									<img alt="${lap.image }" src="images/${lap.image }" class="img-fluid" width="130" />
								</td>
								<td>
									<a href="Controller?a=DisplayUpdate&b=${lap.id}" class="text-warning text-center">Update</a>
								</td>
								<td>
									<form action="${pageContext.request.contextPath}/Controller?a=Delete" method="post" onsubmit="return confirm('Do you want to delete this Laptop?');">
										<input type="hidden" name="image" value="${lap.image}"/>
										<input type="hidden" name="id" value="${lap.id}" />
										<input type="submit" value="Delete" class="text-danger border-0 bg-white text-decoration-underline"/>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>