/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
package CONTROLADOR;

import VISTA.FrmAlumno;
import MODELO.Alumno;
import MODELO.AlumnoArray;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.ArrayList;

public class Controlador {

    private FrmAlumno vista;
    private AlumnoArray modelo;
    private DefaultTableModel tableModel;
    private boolean modoEdicion = false;
    private int filaSeleccionada = -1; // Para guardar el índice de la fila seleccionada


    // Constructor que acepta los parámetros FrmAlumno y AlumnoArray
    public Controlador(FrmAlumno vista, AlumnoArray modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.tableModel = (DefaultTableModel) this.vista.getVistaRegistro().getModel();

        
        // Asignar los listeners a los botones
        this.vista.getBtnGuardar().addActionListener(e -> guardarAlumno());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarAlumno());
        this.vista.getBtnModificar().addActionListener(e -> modificarAlumno());
        this.vista.getBtnAgregar().addActionListener(e -> agregarAlumno());
        this.vista.getTxtBuscar().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarAlumno(vista.getTxtBuscar().getText());
            }
        });

        // Evento para seleccionar una fila y cargar los datos
        this.vista.getVistaRegistro().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarFila();
            }
        });

        mostrarAlumnos();
    }

    // Método para agregar un nuevo alumno
    private void agregarAlumno() {
        if (!validarCampos()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        Alumno nuevoAlumno = new Alumno(
                vista.getTxtCodigo().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtApellido().getText(),
                vista.getTxtDni().getText(),
                vista.getTxtTelefono().getText(),
                vista.getJCalendar().getDate()
        );

        modelo.agregar(nuevoAlumno);
        mostrarAlumnos();
        limpiarCampos();
        JOptionPane.showMessageDialog(vista, "Alumno agregado correctamente.");
    }


    // Método para modificar un alumno
    private void modificarAlumno() {
        int selectedRow = vista.getVistaRegistro().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla.");
            return;
        }

        Alumno alumno = modelo.getListaAlumnos().get(selectedRow);
        alumno.setCodigo(vista.getTxtCodigo().getText());
        alumno.setNombre(vista.getTxtNombre().getText());
        alumno.setApellido(vista.getTxtApellido().getText());
        alumno.setDni(vista.getTxtDni().getText());
        alumno.setTelefono(vista.getTxtTelefono().getText());
        alumno.setFechaNacimiento(vista.getJCalendar().getDate());

        mostrarAlumnos();
        limpiarCampos();
        JOptionPane.showMessageDialog(vista, "Alumno modificado correctamente.");
    }

    // Método para mostrar los alumnos en la tabla
    private void mostrarAlumnos() {
        tableModel.setRowCount(0);
        for (Alumno alumno : modelo.getListaAlumnos()) {
            tableModel.addRow(new Object[]{
                    alumno.getCodigo(),
                    alumno.getNombre(),
                    alumno.getApellido(),
                    alumno.getDni(),
                    alumno.getTelefono(),
                    alumno.getFechaNacimiento()
            });
        }
    }

    // Método para buscar alumnos por nombre o apellido
    private void buscarAlumno(String texto) {
        tableModel.setRowCount(0);
        ArrayList<Alumno> resultados = modelo.buscarPorNombreOApellido(texto);
        for (Alumno alumno : resultados) {
            tableModel.addRow(new Object[]{
                    alumno.getCodigo(),
                    alumno.getNombre(),
                    alumno.getApellido(),
                    alumno.getDni(),
                    alumno.getTelefono(),
                    alumno.getFechaNacimiento()
            });
        }
    }

    private void seleccionarFila() {
    filaSeleccionada = vista.getVistaRegistro().getSelectedRow();
    if (filaSeleccionada != -1) {
        // Obtenemos los datos directamente de la tabla
        vista.getTxtCodigo().setText((String) tableModel.getValueAt(filaSeleccionada, 0));
        vista.getTxtNombre().setText((String) tableModel.getValueAt(filaSeleccionada, 1));
        vista.getTxtApellido().setText((String) tableModel.getValueAt(filaSeleccionada, 2));
        vista.getTxtDni().setText((String) tableModel.getValueAt(filaSeleccionada, 3));
        vista.getTxtTelefono().setText((String) tableModel.getValueAt(filaSeleccionada, 4));
        vista.getJCalendar().setDate((Date) tableModel.getValueAt(filaSeleccionada, 5));

        // Activamos modo edición
        modoEdicion = true;
    }
}


    private void eliminarAlumno() {
    int filaSeleccionada = vista.getVistaRegistro().getSelectedRow();

    // Verificamos si hay una fila seleccionada
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla para eliminar.");
        return;
    }

    // Mostrar diálogo de confirmación
    int confirmacion = JOptionPane.showConfirmDialog(vista, 
        "¿Está seguro de que desea eliminar al alumno seleccionado?", 
        "Confirmar eliminación", 
        JOptionPane.YES_NO_OPTION);

    // Si el usuario confirma la eliminación
    if (confirmacion == JOptionPane.YES_OPTION) {
        // Eliminar el alumno de la lista y del modelo
        modelo.getListaAlumnos().remove(filaSeleccionada); // Elimina del ArrayList
        tableModel.removeRow(filaSeleccionada); // Elimina la fila de la tabla

        JOptionPane.showMessageDialog(vista, "Alumno eliminado correctamente.");
        limpiarCampos(); // Limpiar los campos después de la eliminación
    }
}


    private void guardarAlumno() {
    if (!validarCampos()) {
        JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
        return;
    }

    // Si estamos en modo edición, actualizamos la fila seleccionada
    if (modoEdicion && filaSeleccionada != -1) {
        // Modificamos los datos directamente en la tabla
        tableModel.setValueAt(vista.getTxtCodigo().getText(), filaSeleccionada, 0);
        tableModel.setValueAt(vista.getTxtNombre().getText(), filaSeleccionada, 1);
        tableModel.setValueAt(vista.getTxtApellido().getText(), filaSeleccionada, 2);
        tableModel.setValueAt(vista.getTxtDni().getText(), filaSeleccionada, 3);
        tableModel.setValueAt(vista.getTxtTelefono().getText(), filaSeleccionada, 4);
        tableModel.setValueAt(vista.getJCalendar().getDate(), filaSeleccionada, 5);

        // Actualizamos el alumno en la lista
        Alumno alumnoModificado = modelo.getListaAlumnos().get(filaSeleccionada);
        alumnoModificado.setCodigo(vista.getTxtCodigo().getText());
        alumnoModificado.setNombre(vista.getTxtNombre().getText());
        alumnoModificado.setApellido(vista.getTxtApellido().getText());
        alumnoModificado.setDni(vista.getTxtDni().getText());
        alumnoModificado.setTelefono(vista.getTxtTelefono().getText());
        alumnoModificado.setFechaNacimiento(vista.getJCalendar().getDate());

        // Salimos del modo edición
        modoEdicion = false;
        filaSeleccionada = -1;
        JOptionPane.showMessageDialog(vista, "Alumno modificado correctamente.");
    } else {
        // Si no estamos en modo edición, agregamos un nuevo alumno
        Alumno nuevoAlumno = new Alumno(
                vista.getTxtCodigo().getText(),
                vista.getTxtNombre().getText(),
                vista.getTxtApellido().getText(),
                vista.getTxtDni().getText(),
                vista.getTxtTelefono().getText(),
                vista.getJCalendar().getDate()
        );

        modelo.agregar(nuevoAlumno);
        mostrarAlumnos();
        JOptionPane.showMessageDialog(vista, "Alumno guardado correctamente.");
    }

    limpiarCampos();
}


    
    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        vista.getTxtCodigo().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtDni().setText("");
        vista.getTxtTelefono().setText("");
        vista.getJCalendar().setDate(null);
    }

    // Método para validar que todos los campos están llenos
    private boolean validarCampos() {
        return !(vista.getTxtCodigo().getText().isEmpty() || 
                 vista.getTxtNombre().getText().isEmpty() || 
                 vista.getTxtApellido().getText().isEmpty() || 
                 vista.getTxtDni().getText().isEmpty() || 
                 vista.getTxtTelefono().getText().isEmpty() ||
                 vista.getJCalendar().getDate() == null);
    }
}
