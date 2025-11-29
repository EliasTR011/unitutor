package org.example.vista;

import org.example.modelo.Usuario;
import org.example.controlador.TutorControlador;
import java.util.Scanner;

public class MenuTutor {

    public void visIniTuto() {
        Scanner scanner = new Scanner(System.in);
        TutorControlador controlador = new TutorControlador();

        System.out.println("INICIO DE SESIÓN AL TUTOR");
        System.out.print("Ingrese el número de su DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        Usuario tutor = controlador.inSesTutor(dni, contraseña);

        if (tutor != null) {
            System.out.println("Inicio de sesión exitoso, bienvenido " + tutor.getNombre());
            visMenuTuto(tutor);
        } else {
            System.out.println("Los datos ingresados son incorrectos");
        }
    }

    private void visMenuTuto(Usuario tutor) {
        Scanner scanner = new Scanner(System.in);
        TutorControlador controlador = new TutorControlador();
        int opcion = 0;

        do {
            System.out.println("\nMenú del tutor");
            System.out.println("1) Ver tutorías programadas");
            System.out.println("2) Aceptar tutorías");
            System.out.println("3) Cambiar la contraseña");
            System.out.println("4) Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion){
                case 1:
                    System.out.println("Ver tutorías programadas...");
                    break;
                case 2:
                    System.out.println("Aceptar tutorías...");
                    break;
                case 3:
                    System.out.print("Ingrese la nueva contraseña: ");
                    String nuevaCon = scanner.nextLine();
                    boolean actu = controlador.cambioContra(tutor.getDni(), nuevaCon);
                    if(actu){
                        System.out.println("Contraseña actualizada con éxito");
                    } else {
                        System.out.println("No se logró actualizar la contraseña");
                    }
                    break;
                case 4:
                    System.out.println("Sesión cerrada");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while(opcion != 4);
    }
}
