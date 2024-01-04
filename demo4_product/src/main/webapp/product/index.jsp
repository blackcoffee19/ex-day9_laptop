<%@page import="vn.aptech.dto.ProductDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-8 mx-auto">
			<h1 class="mt-4 mb-2">Product List</h1>
				<% if(session.getAttribute("messageS") !=null){ 
				%>
				<p class="alert bg-success text-white">
					<%=session.getAttribute("messageS")  %>
				</p>
				<%} %>
				<%if(session.getAttribute("messageF")!=null ){ %>
					<p class="alert bg-danger text-white">
					<%= session.getAttribute("messageF") %>
					</p>
				<%} %>
			<a href="Controller?a=DisplayCreate" class="btn btn-outline-primary ">Create</a>
			<hr/>
			<table class="table table-borderless">
				<thead>
					<tr>
						<th class="fw-bold">Id</th>
						<th class="fw-bold">Name</th>
						<th class="fw-bold">Price</th>
						<th class="fw-bold">Image</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<%
						if(request.getAttribute("prods")!=null){
							List<ProductDto> prods = (List<ProductDto>) request.getAttribute("prods");
							for(ProductDto p: prods){
					%>
						<tr>
							<td><%= p.getId() %></td>
							<td><%= p.getName() %></td>
							<td class="fw-bold">$<%= p.getPrice() %></td>
							<td><img src="images/<%= p.getImage() %>"  width="140" /></td>
							<td class="d-flex flex-row flex-nowrap">
								<a href="Controller?a=DisplayUpdate&b=<%= p.getId() %>" class="btn btn-warning me-2">Update</a> |
								<form method="post" action="${pageContext.request.contextPath}/Controller?a=Delete" onsubmit="return confirm('Do you want to delete this product?');">
									<input type="hidden" name="id" value="<%= p.getId() %>" />
									<input type="submit" value="Delete" class="btn btn-danger ms-2"/>
								</form>			
							</td>
						</tr>
					<%		}
						}else{%>
						<tr><td colspan="4">No data</td></tr>
					<% }
					%>
				</tbody>
			</table>			
		</div>
	</div>
</div>
</body>
</html>