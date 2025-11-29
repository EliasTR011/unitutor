package org.example.dao;

import org.example.modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {

    // Registrar usuario
    public int registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, contraseña, rol, dni) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContraseña());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getDni());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
        return -1;
    }

    // Iniciar sesión del tutor
    public Usuario iniSesTutor(String dni, String contraseña) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE dni = ? AND contraseña = ? AND rol = 'tutor'";
        try (Connection conex = ConexionDB.obtenerConexion();
             PreparedStatement decla = conex.prepareStatement(sql)) {

            decla.setString(1, dni);
            decla.setString(2, contraseña);

            ResultSet resul = decla.executeQuery();
            if (resul.next()) {
                usuario = new Usuario();
                usuario.setId(resul.getInt("id"));
                usuario.setNombre(resul.getString("nombre"));
                usuario.setEmail(resul.getString("email"));
                usuario.setRol(resul.getString("rol"));
                usuario.setDni(resul.getString("dni"));
            }
        } catch (Exception e) {
            System.out.println("Error de inicio de sesión: " + e.getMessage());
        }
        return usuario;
    }

    // Actualizar contraseña
    public boolean actualizarContra(String dni, String nuevaContra) {
        String sql = "UPDATE usuarios SET contraseña = ? WHERE dni = ?";
        try (Connection conex = ConexionDB.obtenerConexion();
             PreparedStatement decla = conex.prepareStatement(sql)) {

            decla.setString(1, nuevaContra);
            decla.setString(2, dni);

            int filaActu = decla.executeUpdate();
            return filaActu > 0;

        } catch (Exception e) {
            System.out.println("No se logró actualizar contraseña: " + e.getMessage());
            return false;
        }
    }
}
