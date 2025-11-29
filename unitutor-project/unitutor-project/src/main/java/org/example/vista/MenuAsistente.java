package org.example.vista;

import java.util.Scanner;
import org.example.controlador.TutorControlador;
import org.example.controlador.MateriaControlador;

public class MenuAsistente {

   /* private Scanner scanner;
    private MateriaControlador materiaControlador;

    public MenuAsistente() {
        scanner = new Scanner(System.in);
        materiaControlador = new MateriaControlador();
    }

    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("\n=== MENÚ ASISTENTE ===");
            System.out.println("1. Registrar materia");
            System.out.println("2. Listar materias");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpia buffer

            switch (opcion) {
                case 1:
                    registrarMateria();
                    break;
                case 2:
                    listarMaterias();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void registrarMateria() {
        System.out.println("\n=== REGISTRO DE MATERIAS ===");

        // Nombre
        System.out.print("Ingrese el nombre de la materia: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre es obligatorio. Registro cancelado.");
            return;
        }

        // Confirmación
        System.out.print("¿Confirmar registro? (s/n): ");
        String confirmacion = scanner.nextLine().trim();

        if (!confirmacion.equalsIgnoreCase("s")) {
            System.out.println("Registro cancelado.");
            return;
        }

        // Registrar en el controlador
        materiaControlador.registrarMateria(nombre);
    }

    private void listarMaterias() {
        System.out.println("\n=== LISTA DE MATERIAS ===");
        materiaControlador.listarMaterias();
    }

   private final Scanner scanner = new Scanner(System.in);
   private final TutorControlador tutorControlador = new TutorControlador();

    public void mostrarMenuRegistroTutores() {
       int opcion;

       do {
           System.out.println("MENÚ REGISTRO DE TUTORES");
           System.out.println("1. Registrar tutor");
           System.out.println("2. Salir");
           System.out.print("Opción: ");

           String linea = scanner.nextLine();
           try {
               opcion = Integer.parseInt(linea);
           } catch (NumberFormatException e) {
               System.out.println("Opción inválida. Intente nuevamente.");
               opcion = 0;
           }

           switch (opcion) {
               case 1:
                   registrarTutor();
                   break;
               case 2:
                   System.out.println("Saliendo del menú de registro de tutores...");
                   break;
               default:
                   System.out.println("Opción inválida. Intente nuevamente.");
           }

       } while (opcion != 2);
   }

    private void registrarTutor() {
        System.out.println("----- Registro de tutor -----");

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        System.out.print("DNI / ID: ");
        String dni = scanner.nextLine();

        System.out.print("Correo institucional: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña inicial: ");
        String contraseña = scanner.nextLine();

        System.out.print("ID de la materia asignada: ");
        int idMateria;
        try {
            idMateria = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El ID de materia debe ser un número.");
            return;
        }

        // Llamo TutorControlador
        String resultado = tutorControlador.registrarTutor(
                nombre, dni, email, contraseña, idMateria
        );

        if ("OK".equals(resultado)) {
            System.out.println("Registro exitoso.");
        } else {
            System.out.println("No se pudo registrar el tutor: " + resultado);

        }
    }

    */
}
