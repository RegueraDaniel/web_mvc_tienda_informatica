package org.iesvegademijas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Usuario;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO{

	/**
	 * Inserta en base de datos el nuevo Usuario, actualizando el id en el bean Usuario.
	 */
	@Override	
	public synchronized void create(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        /***antes comprobar si existe***/
        
        try {
        	conn = connectDB();

        	
        	//String usuario, String password, String rol
        	ps = conn.prepareStatement("INSERT INTO Usuario (usuario, password, rol) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++, usuario.getUsuario());
            ps.setString(idx++, usuario.getPassword());
            ps.setString(idx++, usuario.getRol());
            
            int rows = ps.executeUpdate();
            if (rows == 0) 
            	System.out.println("INSERT de usuario con 0 filas insertadas.");
            
            rsGenKeys = ps.getGeneratedKeys();
            /*
            if (rsGenKeys.next()) 
            	usuario.setId(rsGenKeys.getInt(1));
            */       
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
	}

	/**
	 * Devuelve lista con todos loa fabricantes.
	 */
	@Override
	public List<Usuario> getAll() {
		
		Connection conn = null;
		Statement s = null;
        ResultSet rs = null;
        
        List<Usuario> listUser = new ArrayList<>(); 
        
        try {
        	conn = connectDB();

        	// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
        	s = conn.createStatement();
            		
        	rs = s.executeQuery("SELECT * FROM usuario");          
            while (rs.next()) {
            	Usuario user = new Usuario();
            	user.setId(rs.getInt("id"));
            	user.setUsuario(rs.getString("usuario"));
            	user.setPassword(rs.getString("password"));
            	user.setRol(rs.getString("rol"));
            	listUser.add(user);
            }
          
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, s, rs);
        }
        return listUser;
        
	}

	/**
	 * Devuelve Optional de usuario con el ID dado.
	 */
	@Override
	public Optional<Usuario> find(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE id = ?");
        	
        	int idx =  1;
        	ps.setInt(idx, id);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Usuario user = new Usuario();
        		user.setId(rs.getInt("id"));
            	user.setUsuario(rs.getString("usuario"));
            	user.encriptar(rs.getString("password"));
            	user.setRol(rs.getString("rol"));
        		
        		return Optional.of(user);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
        return Optional.empty();
        
	}
	/**
	 * Actualiza usuario con campos del bean usuario según ID del mismo.
	 */
	@Override
	public void update(Usuario usuario) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	/*ps = conn.prepareStatement("UPDATE usuario SET nombre = ?, precio = ?  WHERE codigo = ?");*/
        	ps = conn.prepareStatement("UPDATE usuario SET usuario = ?, rol = ? WHERE id = ?");
        	int idx = 1;
        	ps.setString(idx++, usuario.getUsuario());
        	ps.setString(idx++, usuario.getRol());
        	
        	ps.setInt(idx, usuario.getId());
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Update de usuario con 0 registros actualizados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
    
	}

	/**
	 * Borra fabricante con ID proporcionado.
	 */
	@Override
	public void delete(int id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");
        	int idx = 1;        	
        	ps.setInt(idx, id);
        	
        	int rows = ps.executeUpdate();
        	
        	if (rows == 0) 
        		System.out.println("Delete de usuario con 0 registros eliminados.");
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
		
	}
	
	@Override
	public Optional<Usuario> encontrarUsuario(String usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
        	conn = connectDB();
        	
        	ps = conn.prepareStatement("SELECT * FROM usuario WHERE usuario = ?");
        	
        	int idx =  1;
        	ps.setString(idx, usuario);
        	
        	rs = ps.executeQuery();
        	
        	if (rs.next()) {
        		Usuario user = new Usuario();
        		user.setId(rs.getInt("id"));
            	user.setUsuario(rs.getString("usuario"));
            	user.setPassword(rs.getString("password"));
            	user.setRol(rs.getString("rol"));
        		
        		return Optional.of(user);
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
            closeDb(conn, ps, rs);
        }
        
        return Optional.empty();
	}

}