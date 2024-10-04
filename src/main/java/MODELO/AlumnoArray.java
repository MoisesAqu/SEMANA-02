/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.util.ArrayList;

/**
 * Clase que gestiona un array de objetos Alumno
 */
public class AlumnoArray {

    private ArrayList<Alumno> listaAlumnos;

    // Constructor que inicializa el ArrayList
    public AlumnoArray() {
        listaAlumnos = new ArrayList<>();
    }

    // Método para agregar un Alumno al ArrayList
    public void agregar(Alumno alumno) {
        listaAlumnos.add(alumno);
    }

    public boolean eliminarAlumno(String codigo) {
    for (int i = 0; i < listaAlumnos.size(); i++) {
        if (listaAlumnos.get(i).getCodigo().equals(codigo)) {
            listaAlumnos.remove(i);
            return true; // Alumno eliminado correctamente
        }
    }
    return false; // No se encontró el alumno
}


    // Método para buscar un Alumno por código
    public Alumno buscarPorCodigo(String codigo) {
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getCodigo().equals(codigo)) {
                return alumno;
            }
        }
        return null; // No se encontró el alumno
    }

    // Método para obtener la lista completa de alumnos
    public ArrayList<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    // Método para actualizar un Alumno por código
    public boolean actualizarAlumno(Alumno alumnoActualizado) {
    for (int i = 0; i < listaAlumnos.size(); i++) {
        Alumno alumno = listaAlumnos.get(i);
        if (alumno.getCodigo().equals(alumnoActualizado.getCodigo())) {
            listaAlumnos.set(i, alumnoActualizado);
            return true;  // Alumno actualizado correctamente
        }
    }
    return false;  // No se encontró el alumno con ese código
}

    
    // Método para buscar alumnos por nombre o apellido
    public ArrayList<Alumno> buscarPorNombreOApellido(String texto) {
        ArrayList<Alumno> resultados = new ArrayList<>();
        texto = texto.toLowerCase(); // Convertir a minúsculas para evitar problemas con mayúsculas
        for (Alumno alumno : listaAlumnos) {
            if (alumno.getNombre().toLowerCase().contains(texto) || 
                alumno.getApellido().toLowerCase().contains(texto)) {
                resultados.add(alumno);
            }
        }
        return resultados;
    }
}
