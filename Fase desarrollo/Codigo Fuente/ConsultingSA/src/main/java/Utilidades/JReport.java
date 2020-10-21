/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import DigyFenix.DFOptionPane;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author echacon
 */
public class JReport {

    public static JasperPrint generarReporte(String nombreReporte, Map<String, Object> parametros, Connection cn) {
        try {
            JasperPrint reporteLleno = JasperFillManager.fillReport(nombreReporte, parametros, cn);
            return reporteLleno;
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.getMessage());
        }
        return null;
    }
}
