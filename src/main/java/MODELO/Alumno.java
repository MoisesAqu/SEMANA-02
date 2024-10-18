/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.util.Date;

/**
 *
 * @author USER 17
 */
public class Alumno {
    private String codigo;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private Date fechaNacimiento;

    // Constructor vacío
    public Alumno() {
    }

    // Constructor con parámetros
    public Alumno(String codigo, String nombre, String apellido, String dni, String telefono, Date fechaNacimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public int calcularEdad() {
    if (fechaNacimiento == null) return 0;

    java.util.Calendar hoy = java.util.Calendar.getInstance();
    java.util.Calendar nacimiento = java.util.Calendar.getInstance();
    nacimiento.setTime(fechaNacimiento);

    // Calcular la edad
    int edad = hoy.get(java.util.Calendar.YEAR) - nacimiento.get(java.util.Calendar.YEAR);
    
    // Verificar si el cumpleaños ya ocurrió este año
    if (hoy.get(java.util.Calendar.MONTH) < nacimiento.get(java.util.Calendar.MONTH) || 
        (hoy.get(java.util.Calendar.MONTH) == nacimiento.get(java.util.Calendar.MONTH) && 
         hoy.get(java.util.Calendar.DAY_OF_MONTH) < nacimiento.get(java.util.Calendar.DAY_OF_MONTH))) {
        edad--;
    }
    
    return edad;
}

    
    @Override
    public String toString() {
        return "Alumno{" + "codigo=" + codigo + ", nombre=" + nombre + ", apellido=" + apellido + 
               ", dni=" + dni + ", telefono=" + telefono + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}
