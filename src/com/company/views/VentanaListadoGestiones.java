package com.company.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VentanaListadoGestiones extends JFrame {

    private JPanel panel1;
    private JTable table1;

    private List<Object> datos;

    public VentanaListadoGestiones(List<Object> datos) {

        add(panel1);
        setResizable(false);
        setTitle("Lista Gestiones");
        setBounds(100, 100, 1000, 600);
        this.datos = datos;

        datosTabla();

    }

    private void datosTabla() {

        DefaultTableModel modeloTablaCliente = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTablaCliente.setColumnIdentifiers(new Object[]{
                "Código",
                "Código Proveedor",
                "Código Proyecto",
                "Código Pieza",
                "Cantidad Piezas"
        });

        for (int x = 0; x < datos.size(); x++) {
            Object[] dato = (Object[]) datos.get(x);
            modeloTablaCliente.addRow(new Object[]{
                    dato[0],
                    dato[1],
                    dato[2],
                    dato[3],
                    dato[4]
            });
        }

        table1.setModel(modeloTablaCliente);

    }

}
