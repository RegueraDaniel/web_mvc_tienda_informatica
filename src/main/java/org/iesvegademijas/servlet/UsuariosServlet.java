package org.iesvegademijas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iesvegademijas.dao.UsuarioDAO;
import org.iesvegademijas.dao.UsuarioDAOImpl;
import org.iesvegademijas.model.Usuario;

public class UsuariosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * HTTP Method: GET
	 * Paths: 
	 * 		/usuarios/
	 * 		/usuarios?filtrar-por-nombre=hua 
	 * 		/usuarios/{id}
	 * 		/usuarios/edit/{id}
	 * 		/usuarios/create
	 */		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
				
		String pathInfo = request.getPathInfo(); //
		
		String riques = request.getQueryString();
		
		//String buscarNombre = request.getParameter("filtrar-por-nombre");
		//String 
		if (pathInfo == null || "/".equals(pathInfo)){
			
			UsuarioDAO userDAO = new UsuarioDAOImpl();
			
			/*if(buscarNombre != null) {
				
				request.setAttribute("listaUsuarios", userDAO.buscarPorNombre(buscarNombre));		
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
		
			}else  {*/
				//GET 
				//	/usuarios/
				//	/usuarios
				
				request.setAttribute("listaUsuarios", userDAO.getAll());		
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			/*}*/   		       
		} else {
			// GET
			// 		/usuarios/{id}
			// 		/usuarios/{id}/
			// 		/usuarios/edit/{id}
			// 		/usuarios/edit/{id}/
			// 		/usuarios/create
			// 		/usuarios/create/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				// GET
				// /usuarios/create					
				UsuarioDAO useDAO = new UsuarioDAOImpl();
				request.setAttribute("roles", Usuario.roles);
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-usuario.jsp");
        												
			
			} else if (pathParts.length == 2) {
				UsuarioDAO userDAO = new UsuarioDAOImpl();
				
				if( "login".equals(pathParts[1]) ) {
					
					//request.setAttribute("listaUsuarios","listaUsuarios", userDAO.getAll());
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
					
				}else {
					// GET
					// /usuarios/{id}
					try {
						request.setAttribute("usuario",userDAO.find(Integer.parseInt(pathParts[1])));
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-usuario.jsp");
						        								
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuario.jsp");
					}
				}
				
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				UsuarioDAO userDAO = new UsuarioDAOImpl();
				
				// GET
				// /usuarios/edit/{id}
				try {
					request.setAttribute("usuario",userDAO.find(Integer.parseInt(pathParts[2])));
					request.setAttribute("roles", Usuario.roles);
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-usuario.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
			 
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo o login
			UsuarioDAO userDAO = new UsuarioDAOImpl();
			
			String usuario = request.getParameter("usuario");
			String pass = request.getParameter("password");
			
			String pathInfo = request.getPathInfo();
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			Object usuLogin = request.getSession().getAttribute("usuario-logado");
			 
			if("login".equals(pathParts[1])) {
				
				
				Usuario nuevoUser = new Usuario();
				String[] error= {"Usuario incorrecto", "Contraseña incorrecta"};

				nuevoUser.setUsuario(usuario);
				nuevoUser.encriptar(pass);
				
				Optional<Usuario> optUser = userDAO.encontrarUsuario(usuario);
				
				
				if(optUser.isPresent()) {
					if(nuevoUser.getPassword().equals( optUser.get().getPassword() ) ) {
						
						 HttpSession session=request.getSession(true);  
						 session.setAttribute("usuario-logado", optUser.get());  

						 dispatcher = request.getRequestDispatcher("/index.jsp");
						 
						 
					}else {
						request.setAttribute("errores", error[1]);
						dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
						
					}
				}else {
					request.setAttribute("errores", error[0]);
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
					
				}
				dispatcher.forward(request, response);
				return;
				
			}else if("logout".equals(pathParts[1])){
				
				HttpSession session=request.getSession();
				session.invalidate();
				dispatcher = request.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
				return;
				
			}else {
				String rol = request.getParameter("rol");
				
				Usuario nuevoUser = new Usuario();
				
				nuevoUser.setUsuario(usuario);
				nuevoUser.encriptar(pass);
				nuevoUser.setRol(rol);
				
				userDAO.create(nuevoUser);		
			}
				
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
			
			
			
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		response.sendRedirect("/tienda_informatica/usuarios");
		
		
	}
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UsuarioDAO userDAO = new UsuarioDAOImpl();
		
		String idUsuario = request.getParameter("id");
		String usuario = request.getParameter("usuario");
		String pass = request.getParameter("password");
		String rol = request.getParameter("rol");

		Usuario user = new Usuario();
		
		try {
			
			int id = Integer.parseInt(idUsuario);
			user.setId(id);
			user.setUsuario(usuario);
			user.setPassword(pass);
			user.setRol(rol);
			userDAO.update(user);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		UsuarioDAO userDAO = new UsuarioDAOImpl();
		
		String idUsuario = request.getParameter("id");
		
		try {
			
			int id = Integer.parseInt(idUsuario);
		
			userDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
}
