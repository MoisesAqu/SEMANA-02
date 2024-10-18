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
import java.text.SimpleDateFormat;

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

    private void agregarAlumno() {
    limpiarCampos(); // Aquí limpiamos los campos para permitir la entrada de un nuevo alumno
    modoEdicion = false; // Aseguramos que no estamos en modo edición
    filaSeleccionada = -1; // Reiniciamos la selección de filas
}




    private void modificarAlumno() {
        // No limpiamos los campos aquí
        int selectedRow = vista.getVistaRegistro().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla.");
            return;
        }

        // Obtener los datos del alumno seleccionado
        Alumno alumno = modelo.getListaAlumnos().get(selectedRow);

        // Rellenar los campos del formulario con los datos del alumno
        vista.getTxtCodigo().setText(alumno.getCodigo());
        vista.getTxtNombre().setText(alumno.getNombre());
        vista.getTxtApellido().setText(alumno.getApellido());
        vista.getTxtDni().setText(alumno.getDni());
        vista.getTxtTelefono().setText(alumno.getTelefono());
        vista.getJCalendar().setDate(alumno.getFechaNacimiento());

        // Activar el modo edición
        modoEdicion = true;
        filaSeleccionada = selectedRow;
    }


   private void mostrarAlumnos() {
    tableModel.setRowCount(0);
    for (Alumno alumno : modelo.getListaAlumnos()) {
        tableModel.addRow(new Object[]{
                alumno.getCodigo(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni(),
                alumno.getTelefono(),
                alumno.calcularEdad() + " años" // Mostrar la edad en formato "X años"
        });
    }
}

    
   private void buscarAlumno(String texto) {
    tableModel.setRowCount(0); // Limpiamos la tabla
    ArrayList<Alumno> resultados = modelo.buscarPorNombreOApellido(texto); // Usamos el método modificado
    for (Alumno alumno : resultados) {
        tableModel.addRow(new Object[]{
            alumno.getCodigo(),
            alumno.getNombre(),
            alumno.getApellido(),
            alumno.getDni(),
            alumno.getTelefono(),
            alumno.calcularEdad() + " años" // Seguimos mostrando la edad calculada
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

    // Si estamos en modo edición, actualizamos el alumno seleccionado
    if (modoEdicion && filaSeleccionada != -1) {
        Alumno alumnoModificado = modelo.getListaAlumnos().get(filaSeleccionada);
        alumnoModificado.setCodigo(vista.getTxtCodigo().getText());
        alumnoModificado.setNombre(vista.getTxtNombre().getText());
        alumnoModificado.setApellido(vista.getTxtApellido().getText());
        alumnoModificado.setDni(vista.getTxtDni().getText());
        alumnoModificado.setTelefono(vista.getTxtTelefono().getText());
        alumnoModificado.setFechaNacimiento(vista.getJCalendar().getDate());

        mostrarAlumnos(); // Actualizamos la tabla
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
        mostrarAlumnos(); // Actualizamos la tabla
        JOptionPane.showMessageDialog(vista, "Alumno guardado correctamente.");
    }

    limpiarCampos(); // Limpiamos los campos después de guardar

    // Restablecemos las variables para el modo de agregar
    modoEdicion = false; // Desactivamos el modo edición después de guardar
    filaSeleccionada = -1; // Reiniciamos la selección de la fila
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
