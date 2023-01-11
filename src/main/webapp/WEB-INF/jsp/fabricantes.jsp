<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.dto.FabricanteDTO"%>
<%@page import="java.util.List"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%
HttpServletRequest httpRequest =(HttpServletRequest)request;
		
		//Accediendo al objeto de sesión
		session = httpRequest.getSession();
		Usuario usuarioSesion = null;
		
		Boolean usuAuth = (session != null //Seteo inline de usuario
		&& (usuarioSesion = (Usuario)session.getAttribute("usuario-logado") )!= null
		&& "administrador".equals(usuarioSesion.getRol()));
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Fabricantes</title>
	<%@ include file="/WEB-INF/jsp/head.jspf" %>
</head>

<body>
	<%@ include file="/WEB-INF/jsp/header.jspf" %>
	<%@ include file="/WEB-INF/jsp/nav.jspf" %>
	<main>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Fabricantes</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					<%
					if(usuAuth) {
					%>
						<form action="/tienda_informatica/fabricantes/crear">
							<input type="submit" value="Crear">
						</form>
					<%
					}
					%>
				</div>
				
			</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
		
		
		<div style="margin-top: 6px;" class="clearfix">
			<br>
			<form action="/tienda_informatica/fabricantes" method="get">
				
				<div style="width: 50%">
					Reordenar fabricantes:
				</div>
				<select name="ordenar-por" style="float: none;width: auto;overflow: hidden;">
					<option value="codigo">Código</option>
				  	<option value="nombre">Nombre</option>
				</select>
				
				<select name="modo-ordenar" style="float: none;width: auto;overflow: hidden;">
				  <option value="asc">Ascendente</option>
				  <option value="desc">Descendente</option>
				</select>
				
				<input type="submit" value="Filtrar">
				
				
			</form>	
			<br>	
		</div>
		

		<div class="clearfix">
			<hr/>
		</div>
		
		<div class="clearfix">
			<div style="float: left;width: 25%">Código</div>
			<div style="float: left;width: 25%">Nombre</div>
			<div style="float: left;width: 25%">Número productos</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
	<%
	if (request.getAttribute("listaFabricantes") != null) {
	            List<FabricanteDTO> listaFabricante = (List<FabricanteDTO>)request.getAttribute("listaFabricantes");
	            
	            for (FabricanteDTO fabricante : listaFabricante) {
	%>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 25%"><%= fabricante.getCodigo()%></div>
			<div style="float: left;width: 25%"><%= fabricante.getNombre()%></div>
			<div style="float: left;width: 25%"><%= fabricante.getEnteroProductos()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/fabricantes/<%= fabricante.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<% if(usuAuth) {%>
					<form action="/tienda_informatica/fabricantes/editar/<%= fabricante.getCodigo()%>" style="display: inline;">
	    				<input type="submit" value="Editar" />
					</form>
					<form action="/tienda_informatica/fabricantes/borrar/" method="post" style="display: inline;">
						<input type="hidden" name="__method__" value="delete"/>
						<input type="hidden" name="codigo" value="<%= fabricante.getCodigo()%>"/>
	    				<input type="submit" value="Eliminar" />
					</form>
				<% }%>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros de fabricante
	<% } %>
	</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer.jspf" %>
</body>
</html>