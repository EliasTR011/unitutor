package org.example.Test;

import org.example.controlador.EstudianteControlador;
import org.example.modelo.Estudiante;
import java.util.Scanner;


public class TestRegistrarEstudiante {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EstudianteControlador controlador = new EstudianteControlador();

        System.out.println("Registrando estudiante: \n");

        try {
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("DNI: ");
            String dni = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            Estudiante estudiante = new Estudiante(nombre, email, dni);



            System.out.println("\n Registrando estudiante...\n");
            boolean exito = controlador.registrarEstudiante(estudiante, password);

            if (exito) {
                System.out.println("Registro exitoso");
            } else {
                System.out.println("El Registro no se pudo completar");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nEl DNI debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("\nError inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
