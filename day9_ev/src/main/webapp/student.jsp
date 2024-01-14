<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Student</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-6 col-md-8 mx-auto">
				<form class="mt-3">
					<div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="lang" id="eng" value="eng" onchange="this.form.submit()"/>
					  <label class="form-check-label" for="eng">English</label>
					</div>
					<div class="form-check form-check-inline">
					  <input class="form-check-input" type="radio" name="lang" id="viet" value="viet"onchange="this.form.submit()" />
					  <label class="form-check-label" for="viet">VietName</label>
					</div>
				</form>
				
				<fmt:setLocale value="en_US" />
				<c:if test="${param.lang!=null&&param.lang=='viet'}">
					<fmt:setLocale value="vi_VN" />
				</c:if>
				<fmt:setBundle basename="vn.aptech.lang.student" var="n" />
				<h1 class="my-2"><fmt:message bundle="${n}" key="create.student"/></h1>
				<form action="Controller" method="post">
					<div class="mb-3">
						<label class="form-label" for="name"><fmt:message bundle="${n}" key="name"/></label>
						<input type="text" name="name" id="name"  class="form-control" />
					</div>
					<div class="mb-3">
						<label class="form-label" for="email" ><fmt:message bundle="${n}" key="email"/></label>
						<input type="text" name="email" id="email" class="form-control" />
					</div>
					<div class="mb-3">
						<label class="form-label" for="phone"><fmt:message bundle="${n}" key="phone"/></label>
						<input type="text" name="phone" id="phone" class="form-control" />
					</div>
					<div class="mb-3">
						<button type="submit" class="btn btn-primary"> 
						<fmt:message bundle="${n}" key="create"/>
						</button>
					</div>
					
				</form>
			</div>
		</div>
	</div>
</body>
</html>