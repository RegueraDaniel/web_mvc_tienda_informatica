package org.iesvegademijas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Comparator;

import static java.util.stream.Collectors.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dto.FabricanteDTO;
import org.eclipse.jdt.internal.compiler.util.Sorting;
import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.model.Fabricante;

public class FabricantesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * HTTP Method: GET
	 * Paths: 
	 * 		/fabricantes/
	 * 		/fabricantes/{id}
	 * 		/fabricantes/edit/{id}
	 * 		/fabricantes/create
	 */		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
				
		String pathInfo = request.getPathInfo(); //
		System.out.println(pathInfo);
		
		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		//String 
		if (pathInfo == null || "/".equals(pathInfo)){
			
			String riques = request.getQueryString();
			
			
			if(riques != null) {
				
			String ordenarPor = request.getParameter("ordenar-por");
			String modoOrdenar = request.getParameter("modo-ordenar");
			var lfdto = fabDAO.getAllDTOPlusCountProductosPlusOrdered(ordenarPor, modoOrdenar);
			request.setAttribute("listaFabricantes", lfdto);				
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
			
		/*if (riques != null){
			
			String ordenarPor = request.getParameter("ordenar-por");
			String modoOrdenar = request.getParameter("modo-ordenar");
			
			FabricanteDAO fabDAO = new FabricanteDAOImpl();
			
			var lfdto = fabDAO.getAllDTOPlusCountProductos();
			
			
			if(ordenarPor.equals("codigo")) {
				if(modoOrdenar.equals("asc")){
					request.setAttribute("listaFabricantes", lfdto);				
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}else {
					var listaOrdenada = lfdto.stream().sorted((f1, f2) -> f2.getCodigo() - f1.getCodigo()).collect(toList());
					request.setAttribute("listaFabricantes", listaOrdenada);
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}
				
			}else{//nombre
				if(modoOrdenar.equals("asc")){
					var listaOrdenada = lfdto.stream()
							.sorted((f1, f2) -> f1.getNombre().compareToIgnoreCase(f2.getNombre()) )
							.collect(toList());
					request.setAttribute("listaFabricantes", listaOrdenada);
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}else {
					var listaOrdenada = lfdto.stream()
							.sorted((f1, f2) -> f2.getNombre().compareToIgnoreCase(f1.getNombre()) )
							.collect(toList());
					request.setAttribute("listaFabricantes", listaOrdenada);
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}
			}	
			*/
			
			} else {
				
				//GET 
				//	/fabricantes/
				//	/fabricantes
				//	/fabricantes?...
				/*
				List<Fabricante> lf = fabDAO.getAll();
				var lfdto = lf.stream()
								.map(f -> {
									FabricanteDTO fdto = new FabricanteDTO(f);
									fdto.setNumeroProductos(fabDAO.getCountProductos(f.getCodigo()));
									return fdto;
								})
								.collect(toList());*/
				
				var lfdto = fabDAO.getAllDTOPlusCountProductos();
				request.setAttribute("listaFabricantes", lfdto);				
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
			}
		} else {
			// GET
			// 		/fabricantes/{id}
			// 		/fabricantes/{id}/
			// 		/fabricantes/edit/{id}
			// 		/fabricantes/edit/{id}/
			// 		/fabricantes/create
			// 		/fabricantes/create/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				// GET
				// /fabricantes/create									
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-fabricante.jsp");
        												
			
			} else if (pathParts.length == 2) {
				// GET
				// /fabricantes/{id}
				try {
					request.setAttribute("fabricante",fabDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-fabricante.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				
				// GET
				// /fabricantes/edit/{id}
				try {
					request.setAttribute("fabricante",fabDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-fabricante.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes.jsp");
			
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
			// Crear uno nuevo
			FabricanteDAO fabDAO = new FabricanteDAOImpl();
			
			String nombre = request.getParameter("nombre");
			Fabricante nuevoFab = new Fabricante();
			nuevoFab.setNombre(nombre);
			fabDAO.create(nuevoFab);			
			
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
		
		response.sendRedirect("/tienda_informatica/fabricantes");
		//response.sendRedirect("/tienda_informatica/fabricantes");
		
		
	}
	
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		Fabricante fab = new Fabricante();
		
		try {
			
			int id = Integer.parseInt(codigo);
			fab.setCodigo(id);
			fab.setNombre(nombre);
			fabDAO.update(fab);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		FabricanteDAO fabDAO = new FabricanteDAOImpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
		fabDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
}
