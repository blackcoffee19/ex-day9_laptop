<%@page import="vn.aptech.entity.Laptop"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Laptop</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-6 col-md-8 mx-auto">
			<h2 class="my-5">Update new Laptop</h2>
			<a href="Controller">Back</a>
			<hr/>
			<%if( request.getAttribute("laptop") !=null){ Laptop laptop = (Laptop) request.getAttribute("laptop");
			%>
			<form action="${pageContext.request.contextPath}/Controller?a=Update" method="post" enctype="multipart/form-data">
				<div class="card">
					<div class="card-body">
						<input type="hidden" name="id" value="<%= laptop.getId()%>" />
						<div class="mb-3">
							<label class="form-label" for="name">Name</label>
							<input type="text" name="name" id="name" class="form-control" value="${laptop.getName()}" />
						</div>
						<div class="mb-3">
							<label class="form-label" for="price">Price</label>
							<input type="number" name="price" id="price" class="form-control" value="${laptop.getPrice()}" />
						</div>
						<div class="mb-3">
							<label class="form-label" for="des">Description</label>
							<input type="text" name="description" id="des" class="form-control" value="<%=laptop.getDescription() %>" />
						</div>
						<div class="mb-3 row">
							<label class="col-form-label col-6" for="photo">Image</label>
							<div class="col-6">
							<img alt="${laptop.getImage() }" src="images/${laptop.image }" class="img-fluid" >
							</div>
							<input type="hidden" name="image" value="<%= laptop.getImage() %>"/>
							<input type="file" name="photo" id="photo" class="form-control" />
						</div>
					</div>
					<div class="card-footer">
						<input type="submit" value="Create" class="btn btn-primary" />
					</div>					
				</div>
			</form>
			
			
			<% }%>
		</div>
	</div>
</div>
</body>
</html>