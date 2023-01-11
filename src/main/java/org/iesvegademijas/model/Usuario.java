package org.iesvegademijas.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Arrays;
import java.util.List;

public class Usuario {
	
	public static String[] roles = {"cliente", "vendedor", "administrador"};
	private int id;
	private String usuario;
	private String password;
	private String rol;

	public void encriptar(String password) {
		try {
			this.password = hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	public static String hashPassword(String password ) throws NoSuchAlgorithmException {
		MessageDigest digest;
		
		digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(
				password.getBytes(StandardCharsets.UTF_8));
		
		return bytesToHex(encodedhash);					
		
	}
	
	private static String bytesToHex(byte[] byteHash) {
	    StringBuilder hexString = new StringBuilder(2 * byteHash.length);	  	
	    for (int i = 0; i < byteHash.length; i++) {
	        String hex = Integer.toHexString(0xff & byteHash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString(); 
	}
	
	
	/*CONSTRUCTOR*/
	public Usuario() {
		super();
		// TODO Apéndice de constructor generado automáticamente
	}
	
	//para las comprobaciones; se reutiliza en el constructor de todos los atributos
	public Usuario(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;		
	}
	
	
	public Usuario(String usuario, String password, String rol) {
		
		//inicializa usuario y pass en constructor anterior
		this(usuario, password);
		
		this.rol = Arrays.asList(roles).contains(rol)? rol: "cliente";
	}
	
	public Usuario(int id, String usuario, String password, String rol) {
		
		//inicializa usuario y pass en constructor anterior
		this(usuario, password, rol);
		
		this.id = id;
	}
	
public Usuario(int id, String usuario, String rol) {
		
		//inicializa usuario y pass en constructor anterior
		this.id = id;	
		this.usuario = usuario;
		this.setRol(rol);
		
	}
	
	/*GETERS Y SETERS*/
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
			this.password = password;
	}
	
	public String getRol() {
		return rol;
	}
	
	//si no es un rol, poner cliente por defecto
	public void setRol(String rol) {
		this.rol = Arrays.asList(roles).contains(rol)? rol: "cliente";
	}
		
	
	//comparamos solo por usuario y contraseña para usarlo en comprobaciones->usuario creado o no
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return /*id == other.id && Objects.equals(usuario, other.usuario) &&*/
				 Objects.equals(password, other.password)
				&& Objects.equals(rol, other.rol);
	}
	
	@Override
	public String toString() {
		return "Usuario [ID= " + id + ", usuario= " + usuario + ", rol= " + rol +"  ]";
	}
}
