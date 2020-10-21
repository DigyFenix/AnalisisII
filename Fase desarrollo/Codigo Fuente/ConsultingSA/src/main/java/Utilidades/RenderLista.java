package Utilidades;

import DigyFenix.DFUtils;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.*;

/**
 *
 * @author echacon
 */
public class RenderLista extends JLabel implements ListCellRenderer {

    Hashtable<Object, ImageIcon> elementos = new Hashtable<>();
    ImageIcon icononulo;

    public RenderLista() {
        String s = DFUtils.getRutaImagenes() + "noimagen.png";

        icononulo = new ImageIcon(DFUtils.getRutaImagenes() + "noimagen.png");
        Map<String, String> opciones = new HashMap<>();
        opciones.put("Recuros humanos", DFUtils.getRutaImagenes() + "rh.jpeg");
        opciones.put("Produccion", DFUtils.getRutaImagenes() + "produccion.png");
        opciones.put("Logistica", DFUtils.getRutaImagenes() + "logistica.jpg");
        opciones.put("Finanzas", DFUtils.getRutaImagenes() + "finanzas.png");
        opciones.put("Ventas", DFUtils.getRutaImagenes() + "ventas.jpg");

        opciones.forEach((t, u) -> {
            ImageIcon icon = new ImageIcon(u);
            elementos.put(t, icon);
        });

    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (elementos.get(value) != null) {
            setIcon(elementos.get(value));
            setText("" + value);
            if (isSelected) {
                setFont(new Font("Verdana", Font.BOLD, 16));
            } else {
                setFont(null);
            }
        } else {
            setIcon(icononulo);
            setText("" + value);
            if (isSelected) {
                setFont(new Font("Verdana", Font.BOLD, 16));
            } else {
                setFont(null);
            }
        }
        return this;
    }
}
