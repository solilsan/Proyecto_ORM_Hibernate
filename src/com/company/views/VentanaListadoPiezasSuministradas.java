package com.company.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VentanaListadoPiezasSuministradas extends JFrame {

    private JPanel panel1;
    private JTable table1;

    private List<Object> datos;

    private String tipo;

    public VentanaListadoPiezasSuministradas(List<Object> datos, String tipo) {

        add(panel1);
        setResizable(false);
        setTitle("Lista Piezas Suministradas");
        setBounds(100, 100, 1000, 600);

        this.datos = datos;
        this.tipo = tipo;

        datosTabla();

    }

    private void datosTabla() {

        DefaultTableModel modeloTablaCliente = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (tipo.equalsIgnoreCase("PROVEEDORES")) {
            modeloTablaCliente.setColumnIdentifiers(new Object[]{
                    "Código Pieza",
                    "Código Proyecto",
                    "Cantidad Piezas"
            });

        } else if (tipo.equalsIgnoreCase("PIEZAS")) {
            modeloTablaCliente.setColumnIdentifiers(new Object[]{
                    "Código Proveedor",
                    "Código Proyecto",
                    "Cantidad Piezas"
            });
        }

        for (Object o : datos) {
            Object[] dato = (Object[]) o;
            modeloTablaCliente.addRow(new Object[]{
                    dato[0],
                    dato[1],
                    dato[2]
            });
        }

        table1.setModel(modeloTablaCliente);

    }

}
