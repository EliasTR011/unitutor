package org.example.controlador;

import org.example.dao.MateriaDAO;
import org.example.modelo.Materia;

public class MateriaControlador {

    private final MateriaDAO materiaDAO = new MateriaDAO();

    public boolean registrarMateria(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre de la materia es obligatorio.");
            return false;
        }

        Materia materia = new Materia(nombre);

        boolean ok = materiaDAO.registrarMateria(materia);

        if (ok) {
            System.out.println("Registro de materia exitoso.");
        }

        return ok;
    }

    public void listarMaterias() {
        System.out.println("\n=== LISTA DE MATERIAS ===");
        materiaDAO.obtenerMaterias().forEach(m ->
                System.out.println(m.getId() + " - " + m.getNombre())
        );
    }
}
