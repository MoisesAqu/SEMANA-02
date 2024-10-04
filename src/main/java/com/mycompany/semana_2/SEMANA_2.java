/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.semana_2;


import VISTA.FrmAlumno;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import javax.swing.UIManager;


/**
 *
 * @author ADMIN
 */
public class SEMANA_2 {

    public static void main(String[] args) {
        
        try {
            // Establecer el tema Aluminium de JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Iniciar tu aplicación o mostrar la interfaz gráfica
        java.awt.EventQueue.invokeLater(() -> {
            new FrmAlumno().setVisible(true); // Reemplaza FrmAlumno con el nombre de tu JFrame o formulario principal
        });
    }
}