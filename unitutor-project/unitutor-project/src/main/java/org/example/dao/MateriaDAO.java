package org.example.dao;

import org.example.modelo.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    public boolean registrarMateria(Materia materia) {

        String sql = "INSERT INTO materias (nombre) VALUES (?)";

        try (Connection conex = ConexionDB.obtenerConexion();
             PreparedStatement ps = conex.prepareStatement(sql)) {

            ps.setString(1, materia.getNombre());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar materia: " + e.getMessage());
            return false;
        }
    }

    public List<Materia> obtenerMaterias() {
        List<Materia> lista = new ArrayList<>();

        String sql = "SELECT id, nombre FROM materias";

        try (Connection conex = ConexionDB.obtenerConexion();
             PreparedStatement ps = conex.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Materia materia = new Materia(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                lista.add(materia);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener materias: " + e.getMessage());
        }

        return lista;
    }
}
