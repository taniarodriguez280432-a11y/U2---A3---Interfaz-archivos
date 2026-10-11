/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfazarchivo;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileOperationsGUI extends JFrame {

    private JTextField txtRuta;
    private JTextArea txtSalida;
    private JButton btnCrear, btnEscribir, btnLeer, btnMover, btnEliminar;

    public FileOperationsGUI() {
        setTitle("Operaciones con archivos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtRuta = new JTextField();
        txtSalida = new JTextArea();
        txtSalida.setEditable(false);

        JPanel panelBotones = new JPanel();
        btnCrear = new JButton("Crear Archivo");
        btnEscribir = new JButton("Escribir en Archivo");
        btnLeer = new JButton("Leer Archivo");
        btnMover = new JButton("Mover Archivo");
        btnEliminar = new JButton("Eliminar Archivo");
        panelBotones.add(btnCrear);
        panelBotones.add(btnEscribir);
        panelBotones.add(btnLeer);
        panelBotones.add(btnMover);
        panelBotones.add(btnEliminar);

        add(txtRuta, BorderLayout.NORTH);
        add(new JScrollPane(txtSalida), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ejemplo:crear archivo
        btnCrear.addActionListener(e -> {
            try {
                Path ruta = Paths.get(txtRuta.getText());
                if (ruta.getParent() != null) {
                    Files.createDirectories(ruta.getParent());
                }
                if (Files.notExists(ruta)) {
                    Files.createFile(ruta);
                    txtSalida.setText("Archivo creado: " + ruta);
                } else {
                    txtSalida.setText("El archivo ya existe.");
                }
            } catch (IOException ex) {
                txtSalida.setText("Error al crear el archivo: " + ex);
            }
        });

        //listeners de Escribir Leer Mover  Eliminar
        
        //escribir en archivo
        btnEscribir.addActionListener(e -> {
            try {
                Path ruta = Paths.get(txtRuta.getText());
                String texto = JOptionPane.showInputDialog(this, "Texto a escribir:");
                if (texto != null) {
                    Files.writeString(ruta, texto);
                    txtSalida.setText("Se escribio en el archivo: " + ruta);
                }
            } catch (IOException ex) {
                txtSalida.setText("Error al escribir en el archivo: " + ex.getMessage());
            }
        });

        //leer archivo
        btnLeer.addActionListener(e -> {
            try {
                Path ruta = Paths.get(txtRuta.getText());
                List<String> lineas = Files.readAllLines(ruta);
                txtSalida.setText("");
                for (String linea : lineas) {
                    txtSalida.append(linea + "\n");
                }
            } catch (IOException ex) {
                txtSalida.setText("Error al leer el archivo: " + ex.getMessage());
            }
        });

        //mover archivo
        btnMover.addActionListener(e -> {
            try {
                Path origen = Paths.get(txtRuta.getText());
                String destinoTxt = JOptionPane.showInputDialog(this, "Nueva ruta de destino:");
                if (destinoTxt != null) {
                    Path destino = Paths.get(destinoTxt);
                    Files.move(origen, destino);
                    txtSalida.setText("Archivo movido a: " + destino);
                }
            } catch (IOException ex) {
                txtSalida.setText("Error al mover el archivo: " + ex.getMessage());
            }
        });

        //eliminar archivo
        btnEliminar.addActionListener(e -> {
            try {
                Path ruta = Paths.get(txtRuta.getText());
                if (Files.exists(ruta)) {
                    Files.delete(ruta);
                    txtSalida.setText("Archivo eliminado: " + ruta);
                } else {
                    txtSalida.setText("El archivo no existe");
                }
            } catch (IOException ex) {
                txtSalida.setText("Error al eliminar el archivo: " + ex.getMessage());
            }
        });
    }
}
