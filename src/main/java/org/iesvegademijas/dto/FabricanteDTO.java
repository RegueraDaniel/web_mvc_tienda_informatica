package org.iesvegademijas.dto;

import java.util.Optional;

import org.iesvegademijas.model.Fabricante;

public class FabricanteDTO extends Fabricante{
	
	private Optional<Integer> numeroProductos;
	private int enteroProductos;

	public Optional<Integer> getNumeroProductos() {
		return numeroProductos;
	}

	public void setNumeroProductos(Optional<Integer> numeroProductos) {
		this.numeroProductos = numeroProductos;
	}
	
	public int getEnteroProductos() {
		return enteroProductos;
	}

	public void setEnteroProductos(int enteroProductos) {
		this.enteroProductos = enteroProductos;
	}
	
	public FabricanteDTO(Fabricante f) {
		this.setCodigo(f.getCodigo());
		this.setNombre(f.getNombre());
	}
	
		
	/*List<Fabricante> lf = FabDao.getAll();
	var lfdto =	lf.stream()
					.map(f -> {
							FabDTO fdto = new FabDTO(f)
						fdto.getNumProd(FabDao.getCountProductos(f.getCodigo()))
							})
					.collect(toList()).*/
	
	//request.setAtributo("listaFabdto", lf)
}
