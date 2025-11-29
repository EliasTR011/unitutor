package org.example.dao;

import org.example.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutorDAO {

    // Verifica si ya existe un usuario con ese email
    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email = ? LIMIT 1";

        try (Connection conex = ConexionDB.obtenerConexion();
             PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si encontró algo

        } catch (SQLException e) {
            System.out.println("Error al verificar email: " + e.getMessage());
            // por seguridad, asumimos que existe si hay error
            return true;
        }
    }

    // Registra el tutor en usuarios, tutores y tutor_materia
    public boolean registrarTutor(Usuario tutor, int idMateria) {
        String sqlUsuario =
                "INSERT INTO usuarios (dni, nombre, email, contraseña, rol) VALUES (?, ?, ?, ?, 'tutor')";
        String sqlTutor =
                "INSERT INTO tutores (id_usuario) VALUES (?)";
        String sqlTutorMateria =
                "INSERT INTO tutor_materia (id_tutor, id_materia) VALUES (?, ?)";

        Connection conex = null;
        try {
            conex = ConexionDB.obtenerConexion();
            if (conex == null) {
                return false;
            }

            // Usamos transacción para que o se guarde todo o nada
            conex.setAutoCommit(false);

            int idGenerado;

            // 1) Insertar en usuarios y obtener el id generado
            try (PreparedStatement psUsuario =
                         conex.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {

                psUsuario.setString(1, tutor.getDni());
                psUsuario.setString(2, tutor.getNombre());
                psUsuario.setString(3, tutor.getEmail());
                psUsuario.setString(4, tutor.getContraseña());

                int filas = psUsuario.executeUpdate();
                if (filas == 0) {
                    throw new SQLException("No se pudo insertar el usuario.");
                }

                ResultSet rs = psUsuario.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID generado del usuario.");
                }
            }

            // 2) Insertar en tutores
            try (PreparedStatement psTutor = conex.prepareStatement(sqlTutor)) {
                psTutor.setInt(1, idGenerado);
                psTutor.executeUpdate();
            }

            // 3) Insertar en tutor_materia
            try (PreparedStatement psTutorMateria = conex.prepareStatement(sqlTutorMateria)) {
                psTutorMateria.setInt(1, idGenerado);
                psTutorMateria.setInt(2, idMateria);
                psTutorMateria.executeUpdate();
            }

            // Si todo salió bien, confirmamos la transacción
            conex.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar tutor: " + e.getMessage());
            if (conex != null) {
                try {
                    conex.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            return false;

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                    System.out.println("Conexión cerrada");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}
