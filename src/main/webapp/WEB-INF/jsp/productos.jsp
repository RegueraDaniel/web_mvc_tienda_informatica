<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
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
	<title>Productos</title>
	<%@ include file="/WEB-INF/jsp/head.jspf" %>
</head>

<body>
	<%@ include file="/WEB-INF/jsp/header.jspf" %>
	<%@ include file="/WEB-INF/jsp/nav.jspf" %>
	<main>
	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Productos</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					<% if(usuAuth) {%>
						<form action="/tienda_informatica/productos/crear">
							<input type="submit" value="Crear">
						</form>
					</div>
					<% }%>
				
			</div>
		</div>
		
		
		<div class="clearfix">
			<hr/>
		</div>
		
		
		<div style="margin-top: 6px;" class="clearfix">
			<br>
			<form action="/tienda_informatica/productos" method="get">
				
				<div style="width: 50%">
					Buscar fabricantes por nombre (parcial o total):
				</div>
				
				
				<div style="margin-top: 6px;" class="clearfix">
					
					<div style="float: none;width: auto;overflow: hidden;">
						<input name="filtrar-por-nombre" value=""/>
					</div> 
				</div> 
				<input type="submit" value="Buscar">
			</form>	
				
		
		</div>
<br>
		<div class="clearfix">
			<hr/>
		</div>
		
		
		<div class="clearfix">
			<hr/>
		</div>
		<div class="clearfix">
			<div style="float: left;width: 15%">Código</div>
			<div style="float: left;width: 30%">Nombre</div>
			<div style="float: left;width: 15%">Precio</div>
			<div style="float: left;width: 15%">Código Fabricante</div>
			<div style="float: none;width: auto;overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr/>
		</div>
	<% 
        if (request.getAttribute("listaProductos") != null) {
            List<Producto> listaProducto = (List<Producto>)request.getAttribute("listaProductos");
            
            for (Producto producto : listaProducto) {
    %>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 15%"><%= producto.getCodigo()%></div>
			<div style="float: left;width: 30%"><%= producto.getNombre()%></div>
			<div style="float: left;width: 15%"><%= producto.getPrecio()%></div>
			<div style="float: left;width: 15%"><%= producto.getCodigoFabricante()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/productos/<%= producto.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<% if(usuAuth) {%>
					<form action="/tienda_informatica/productos/editar/<%= producto.getCodigo()%>" style="display: inline;">
	    				<input type="submit" value="Editar" />
					</form>
					<form action="/tienda_informatica/productos/borrar/" method="post" style="display: inline;">
						<input type="hidden" name="__method__" value="delete"/>
						<input type="hidden" name="codigo" value="<%= producto.getCodigo()%>"/>
	    				<input type="submit" value="Eliminar" />
					</form>
				<% }%>
			</div>
		</div>

	<% 
            }
        } else { 
    %>
		No hay registros del producto
	<% } %>
	</div>
	</main>
	<%@ include file="/WEB-INF/jsp/footer.jspf" %>
</body>

</html>