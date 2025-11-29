package org.example.modelo;
public class Tutor {
    private int id;
    private int idUsuario;
    private String nombre;
    private String email;
    private String dni;

    public Tutor() {
    }

    public Tutor(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDni() {return dni;}
    public void setDni(String dni) {this.dni = dni;}
}
