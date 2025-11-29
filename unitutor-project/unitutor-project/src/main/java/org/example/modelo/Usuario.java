package org.example.modelo;

public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String rol;
    private String dni;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int id, String nombre, String email, String contraseña, String rol, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
        this.dni = dni;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getContraseña() { return contraseña; }
    public String getRol() { return rol; }
    public String getDni() { return dni; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public void setRol(String rol) { this.rol = rol; }
    public void setDni(String dni) { this.dni = dni; }
}
