package org.example.dao;

import org.example.modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class EstudianteDAO {

    public boolean registrarEstudiante(int idUsuario) {
        String sql = "INSERT INTO estudiantes (id_usuario) VALUES (?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar estudiante: " + e.getMessage());
            return false;
        }
    }

    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT u.id, u.nombre, u.email, u.dni FROM estudiantes e " +
                "INNER JOIN usuarios u ON e.id_usuario = u.id";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Estudiante est = new Estudiante();
                est.setId(rs.getInt("id"));
                est.setNombre(rs.getString("nombre"));
                est.setEmail(rs.getString("email"));
                est.setDni(rs.getString("dni"));
                estudiantes.add(est);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage());
        }

        return estudiantes;
    }
}
