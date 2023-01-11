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
	<form action="/tienda_informatica/usuarios/editar/" method="post" >
		<input type="hidden" name="__method__" value="put" />
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Editar Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
							<input type="submit" value="Guardar" />						
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
				<label>Id</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="id" value="<%= optUser.get().getId() %>" readonly="readonly"/>
			</div> 
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Nombre de usuario</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="usuario" value="<%= optUser.get().getUsuario() %>"/>
			</div> 
		</div>
		
		<input type="hidden" name="password" value="<%= optUser.get().getPassword() %>" />
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				<label>Rol</label>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<select name="rol" style="float: none;width: auto;overflow: hidden;">
					<% 
				       String[] roles = (String[])request.getAttribute("roles");
				            
				            for (String rol : roles) {
				    %>
				  <option value="<%= rol%>"><%= rol%></option>
				  <% 
	            }
				            %>
				</select>
			</div> 
		</div>
		
		
		<% 	} else { %>
			
				request.sendRedirect("usuarioss/");
		
		<% 	} %>
	</form>
</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer.jspf" %>
</body>
</html>