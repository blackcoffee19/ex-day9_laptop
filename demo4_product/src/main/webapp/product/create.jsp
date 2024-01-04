<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Product</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-8 mx-auto">
				<h2 class="mt-4">Create Product</h2>
				<%if(session.getAttribute("error") !=null){ %>
					<p class="alert bg-danger text-white">
					<%= session.getAttribute("error") %>
					</p>
				<%} %>
				<form method="post" action="${pageContext.request.contextPath}/Controller?a=Create" enctype="multipart/form-data">
					<div class="mb-2">
						<label class="form-label">Name: </label>
						<input type="text" name="name" class="form-control"/>
					</div>
					<div class="mb-2">
						<label class="form-label">Price: </label>
						<input type="number" name="price" class="form-control"/>
					</div>
					<div class="mb-2">
						<label class="form-label">Image: </label>
						<input type="file" name="photo" class="form-control"/>
					</div>
					<div class="mb-2">
						<input type="submit"value="Create" class="btn btn-primary" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>