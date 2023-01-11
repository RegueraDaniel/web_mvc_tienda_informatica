<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login de Usuario</title>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jspf" %>
	<%@ include file="/WEB-INF/jsp/nav.jspf" %>
	<main>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	<form action="/tienda_informatica/usuarios/login/" method="post">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Login de Usuario</h1>
			</div>
			
			
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Nombre de usuario
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="usuario" />
			</div> 
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Contraseña
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="password" />
			</div> 
		</div>
		<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">								
					<input type="submit" value="Iniciar sesión"/>					
				</div>
				
		</div>
	</form>
	
	<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
	
		<% 	String errorLogin = (String)request.getAttribute("errores");
			if (errorLogin != null) {
		%>
			<div style="position: absolute; left: 39%; top : 39%;">								
				<h2><%=errorLogin %></h2>					
			</div>
		<% 	}%>	
	</div>
		
	
</div>
</main>
	<%@ include file="/WEB-INF/jsp/footer.jspf" %>
</body>
</html>