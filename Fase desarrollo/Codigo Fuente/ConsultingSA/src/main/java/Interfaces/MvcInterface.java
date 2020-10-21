package Interfaces;

import DigyFenix.DFMvcEnum;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author echacon
 */
public interface MvcInterface {

    public void mapearFields(HashMap<String, Object> fields);

    public void limpiarFields(boolean eliminarValoresVariables);

    public void setValue(String llave, DFMvcEnum tipo, boolean eliminarValoresVariables);

    public void llenarFields();

    public void fieldsActivados(boolean si_o_no);

    public List<String> getVariables();

    /*
     --============================================================== Implementacion en el modelo:
    private HashMap<String, Object> mapFields = new HashMap<>();
    
    @Override
    public void mapearFields(HashMap<String, Object> fields) {
        this.mapFields = fields;
        mapFields.forEach((t, field) -> {
            if (field instanceof JTextField) {
                ((JTextField) field).addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            } else if (field instanceof JTextArea) {
                ((JTextArea) field).addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            } else if (field instanceof JComboBox) {
                ((JComboBox) field).addItemListener((ItemEvent e) -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            } else if (field instanceof JCheckBox) {
                ((JCheckBox) field).addItemListener((ItemEvent e) -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    } else {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            }
        });

    }

    @Override
    public void limpiarFields(boolean eliminarValoresVariables) {
        this.mapFields.forEach((t, u) -> {
            setValue(t, DFMvcEnum.FIELD_A_VARIABLE, true);
        });
    }

    @Override
    public void setValue(String llave, DFMvcEnum tipo, boolean eliminarValoresVariables) {
        Object obj = mapFields.get(llave);
        try {
            //Switch generado
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @Override
    public void llenarFields() {
        mapFields.forEach((t, u) -> {
            setValue(t, DFMvcEnum.VARIABLE_A_FIELD, false);
        });
    }
    
    
    --============================================================== Cuando Instanciamos el modelo 
        --Primero se generar el bloque switch para no escribir todo ese codigo,
        --luego la variable sw generada se pega en el metodo setValue en la parte que dice //Switch generado
        HashMap<String, MvcEnum> fields = new HashMap<>();

        fields.put("nombre", STRING);
        fields.put("puntos", FLOAT);
        fields.put("edad", INT);
        fields.put("fecha", DATE);

        String sw = Utils.getSwitchParaMvc(fields);
        System.out.println(sw);
    
        --Segundo se realiza el mapeo de el nombre de variables con los textfields.
        HashMap<String, JTextField> modeloMap = new HashMap<>();
        modeloMap.put("nombre", txbNombre);
        modeloMap.put("puntos", txbPuntos);
        modeloMap.put("edad", txbEdad);
        modeloMap.put("fecha", txbFecha);
        modelo.mapearFields(modeloMap);
     */
}
