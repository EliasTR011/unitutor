package org.example.controlador;

import org.example.dao.TutorDAO;
import org.example.dao.UsuarioDAO;
import org.example.modelo.Usuario;

public class TutorControlador {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final TutorDAO tutorDAO = new TutorDAO();

    // Iniciar sesión del tutor
    public Usuario inSesTutor(String dni, String contraseña){
        return usuarioDAO.iniSesTutor(dni, contraseña);
    }

    // Cambiar contraseña
    public boolean cambioContra(String dni, String nuevaContra){
        return usuarioDAO.actualizarContra(dni, nuevaContra);
    }



    /**
     * Registra un tutor según la historia de usuario.
     *
     * @return "OK" si todo salió bien, o un mensaje de error si falló algo.
     */
    public String registrarTutor(String nombre,
                                 String dni,
                                 String email,
                                 String contraseña,
                                 int idMateria) {

        // 1) Validaciones de la historia
        if (nombre == null || nombre.isBlank()) {
            return "El nombre es obligatorio.";
        }
        if (email == null || email.isBlank()) {
            return "El correo es obligatorio.";
        }
        if (dni == null || dni.isBlank()) {
            return "El DNI es obligatorio.";
        }
        if (contraseña == null || contraseña.isBlank()) {
            return "La contraseña es obligatoria.";
        }

        // Validación simple de formato de email
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            return "El correo no tiene un formato válido.";
        }

        // 2) Validar que el correo no esté repetido
        if (tutorDAO.emailExiste(email)) {
            return "Ya existe un usuario registrado con ese correo.";
        }

        // 3) Crear el objeto Usuario con rol tutor
        Usuario tutor = new Usuario();
        tutor.setNombre(nombre);
        tutor.setDni(dni);
        tutor.setEmail(email);
        tutor.setContraseña(contraseña);
        tutor.setRol("tutor");

        // 4) Delegar la inserción al DAO (usuarios + tutores + tutor_materia)
        boolean ok = tutorDAO.registrarTutor(tutor, idMateria);

        // 5) Devolver solo el resultado, sin notificación
        if (ok) {
            return "OK";
        } else {
            return "Error al registrar el tutor en la base de datos.";
        }
    }
}
