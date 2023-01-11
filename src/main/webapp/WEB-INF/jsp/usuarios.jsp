<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.List"%>
<%  HttpServletRequest httpRequest =(HttpServletRequest)request;
		
		//Accediendo al objeto de sesión
		session = httpRequest.getSession();
		Usuario usuarioSesion = null;
		
		Boolean usuAuth = (session != null //Seteo inline de usuario
				&& (usuarioSesion = (Usuario)session.getAttribute("usuario-logado") )!= null
				&& "administrador".equals(usuarioSesion.getRol())); 		%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Usuarios</title>
	<%@ include file="/WEB-INF/jsp/head.jspf" %>
</head>

<body>
	<%@ include file="/WEB-INF/jsp/header.jspf" %>
	<%@ include file="/WEB-INF/jsp/nav.jspf" %>
	<main>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Usuarios</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					<% if(usuAuth) {%>
					<form action="/tienda_informatica/usuarios/crear">
						<input type="submit" value="Crear">
					</form>
					<% }%>
				</div>
				
			</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
		

		<div class="clearfix">
			<hr/>
		</div>
		
		<div class="clearfix">
			<div style="float: left;width: 25%">Id</div>
			<div style="float: left;width: 25%">Usuario</div>
			<div style="float: left;width: 25%">Rol</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaUsuarios") != null) {
        	
            List<Usuario> listaUsuario = (List<Usuario>)request.getAttribute("listaUsuarios");
            
            for (Usuario usuario : listaUsuario) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%"><%= usuario.getId()%></div>
			<div style="float: left;width: 25%"><%= usuario.getUsuario()%></div>
			<div style="float: left;width: 25%"><%= usuario.getRol()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
			
				<form action="/tienda_informatica/usuarios/<%= usuario.getId()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				
				<% if(usuAuth) {%>
					
						<form action="/tienda_informatica/usuarios/editar/<%= usuario.getId()%>" style="display: inline;">
		    				<input type="submit" value="Editar" />
						</form>
						
						<form action="/tienda_informatica/usuarios/borrar/" method="post" style="display: inline;">
							<input type="hidden" name="__method__" value="delete"/>
							<input type="hidden" name="id" value="<%= usuario.getId()%>"/>
		    				<input type="submit" value="Eliminar" />
						</form>
					
				<% }%>
		
			</div>
		</div>
	<% 
            }
        } else { 
    %>
		No hay registros de usuario
	<% } %>
	</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer.jspf" %>
</body>
</html>