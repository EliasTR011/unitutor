package org.example.controlador;

import org.example.dao.UsuarioDAO;
import org.example.dao.EstudianteDAO;
import org.example.modelo.Usuario;
import org.example.modelo.Estudiante;

import java.util.List;

public class EstudianteControlador {

    private UsuarioDAO usuarioDAO;
    private EstudianteDAO estudianteDAO;

    public EstudianteControlador() {
        this.usuarioDAO = new UsuarioDAO();
        this.estudianteDAO = new EstudianteDAO();
    }

    public boolean registrarEstudiante(Estudiante estudiante, String password) {

        Usuario usuario = new Usuario();
        usuario.setNombre(estudiante.getNombre());
        usuario.setEmail(estudiante.getEmail());
        usuario.setContraseña(password);
        usuario.setRol("estudiante");

        // Convertir el DNI de int a String
        usuario.setDni(String.valueOf(estudiante.getDni()));

        int idGenerado = usuarioDAO.registrarUsuario(usuario);

        if (idGenerado == -1) {
            System.out.println("Error: no se pudo registrar el usuario.");
            return false;
        }

        boolean registradoEst = estudianteDAO.registrarEstudiante(idGenerado);

        if (!registradoEst) {
            System.out.println("Error: el usuario fue creado, pero no se pudo registrar como estudiante.");
            return false;
        }

        System.out.println("Estudiante registrado con éxito.");
        return true;
    }

    public void listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteDAO.listarEstudiantes();

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.println("\n=== LISTA DE ESTUDIANTES ===");
        for (Estudiante e : estudiantes) {
            System.out.println("ID: " + e.getId() +
                    " | Nombre: " + e.getNombre() +
                    " | Email: " + e.getEmail() +
                    " | DNI: " + e.getDni());
        }
    }
}
