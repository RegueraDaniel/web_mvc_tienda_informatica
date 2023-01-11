<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle Usuario</title>
<%@ include file="/WEB-INF/jsp/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jspf" %>
	<%@ include file="/WEB-INF/jsp/nav.jspf" %>
	<main>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Detalle Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
						<form action="/tienda_informatica/usuarios" >
							<input type="submit" value="Volver" />
						</form>
					</div>
				
			</div>
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
		
		<% 	Optional<Usuario> optUser = (Optional<Usuario>)request.getAttribute("usuario");
			if (optUser.isPresent()) {
		%>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>ID</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input value="<%= optUser.get().getId() %>" readonly="readonly"/>
			</div> 
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Nombre de usuario</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input value="<%= optUser.get().getUsuario() %>" readonly="readonly"/>
			</div> 
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Rol</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input value="<%= optUser.get().getRol() %>" readonly="readonly"/>
			</div> 
		</div>
		
		
		<% 	} else { %>
			
				request.sendRedirect("usuarios/");
		
		<% 	} %>
		
</div>
</main>
	<%@ include file="/WEB-INF/jsp/footer.jspf" %>
</body>
</html>