/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Controladores.DaoEmpleado;
import DigyFenix.DFDate;
import DigyFenix.DFOptionPane;
import Modelos.Empleado;
import Modelos.IngresoEgreso;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author echacon
 */
public class SWLeerDescuentos extends SwingWorker<Integer, Integer> {

    private final JProgressBar jpb_progreso;
    private final JLabel label_progreso;
    private int tamano_archivo = 100;
    private File excelFile;
    private List<IngresoEgreso> listEgresos;
    String stringProgreso = "";
    private JTable tblDatos;
    private Date fecha;
    private int movimientoDescuento, movimientoAhorro;
    private JLabel lblDeescuento, lblAhorro;
    String encabezado[] = {"Fecha", "Id Empleado", "Nombre", "Tienda", "Ahorro"};
    DefaultTableModel model = new DefaultTableModel(null, encabezado);

    public SWLeerDescuentos(JProgressBar jpb_progreso, JLabel label_progreso, File file,
            List<IngresoEgreso> listEgresos,
            JTable tblDatos,
            Date fecha,
            int movimientoDescuento,
            int movimientoAhorro,
            JLabel lblDeescuento,
            JLabel lblAhorro) {
        this.jpb_progreso = jpb_progreso;
        this.label_progreso = label_progreso;
        this.excelFile = file;
        this.listEgresos = listEgresos;
        this.tblDatos = tblDatos;
        this.fecha = fecha;
        this.movimientoAhorro = movimientoAhorro;
        this.movimientoDescuento = movimientoDescuento;
        this.lblAhorro = lblAhorro;
        this.lblDeescuento = lblDeescuento;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        int linea = 0;
        try {

            int indice_avance = 0;
            publicarAvance(++indice_avance, "Cargando Excel en memoria ");
            InputStream excelStream = null;

            excelStream = new FileInputStream(excelFile);
            XSSFWorkbook hssfWorkbook = new XSSFWorkbook(excelStream);
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            XSSFRow hssfRow;
            // Inicializo el objeto que leerá el valor de la celda
            XSSFCell cell;
            // Obtengo el número de filas ocupadas en la hoja
            int rows = hssfSheet.getLastRowNum();
            tamano_archivo = rows;
            this.jpb_progreso.setMaximum(tamano_archivo);
            // Obtengo el número de columnas ocupadas en la hojaamos para almacenar la lectura de la celda
            //String cellValue;
            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos  

            DecimalFormat df = new DecimalFormat("###,###,###.##");

            float totalDescuento = 0;
            float totalAhorro = 0;
            DaoEmpleado daoemp = new DaoEmpleado();

            for (int r = 1; r <= rows; r++) {
                publicarAvance(++indice_avance, "Procesando fila " + (r + 1));
                String row[];
                hssfRow = hssfSheet.getRow(r);
                int cols = hssfRow.getLastCellNum();

                if (hssfRow == null) {
                    break;
                } else {
                    row = new String[cols];

                    for (int c = 0; c < cols; c++) {
                        row[c] = hssfRow.getCell(c) == null ? ""
                                : (hssfRow.getCell(c).getCellType() == CellType.STRING) ? hssfRow.getCell(c).getStringCellValue()
                                : (hssfRow.getCell(c).getCellType() == CellType.NUMERIC) ? "" + hssfRow.getCell(c).getNumericCellValue()
                                : (hssfRow.getCell(c).getCellType() == CellType.BOOLEAN) ? "" + hssfRow.getCell(c).getBooleanCellValue()
                                : (hssfRow.getCell(c).getCellType() == CellType.BLANK) ? "BLANK"
                                : (hssfRow.getCell(c).getCellType() == CellType.FORMULA) ? "FORMULA"
                                : (hssfRow.getCell(c).getCellType() == CellType.ERROR) ? "ERROR" : "";
                    }

                    int codigoEmp = ((int) Math.round(Float.parseFloat(row[0])));
                    Empleado emp = daoemp.buscarPorId(codigoEmp);
                    if (emp == null) {
                        throw new Exception("Archivo con errores, revise la linea " + linea
                                + "\nNo existe el empleado con codigo " + codigoEmp);
                    }

                    Float montoDescuento = Float.parseFloat(row[2]);
                    Float montoaAhorro = Float.parseFloat(row[3]);

                    totalDescuento += montoDescuento;
                    totalAhorro += montoaAhorro;

                    String filaTabla[] = {"" + DFDate.get_fecha(fecha),
                        emp.getIdempleado() + "",
                        row[1],
                        "" + montoDescuento,
                        "" + montoaAhorro
                    };

                    for (int i = 0; i < filaTabla.length; i++) {
                        System.out.print(filaTabla[0] + " - ");
                    }

                    IngresoEgreso ahorro = new IngresoEgreso();
                    ahorro.setIdempleado(emp.getIdempleado());
                    ahorro.setCantidad(1f);
                    ahorro.setIdmovimiento(this.movimientoAhorro);
                    ahorro.setMonto(montoaAhorro);
                    ahorro.setFecha(fecha);
                    listEgresos.add(ahorro);

                    IngresoEgreso descuento = new IngresoEgreso();
                    descuento.setIdempleado(emp.getIdempleado());
                    descuento.setCantidad(1f);
                    descuento.setIdmovimiento(this.movimientoDescuento);
                    descuento.setMonto(montoDescuento);
                    descuento.setFecha(fecha);

                    listEgresos.add(descuento);

                    lblAhorro.setText(df.format(totalAhorro));
                    lblDeescuento.setText(df.format(totalDescuento));

                    model.addRow(filaTabla);
                    tblDatos.setModel(model);
                }
            }
            excelStream.close();

        } catch (Exception e) {
            DFOptionPane.mostrar_Error("Archivo con errores, revise la linea " + linea + " \n" + e.toString());
        }
        this.jpb_progreso.setMaximum(tamano_archivo);
        return 1;
    }

    @Override
    protected void process(List<Integer> valor_actual
    ) {
        jpb_progreso.setValue(valor_actual.get(0));
        label_progreso.setText(stringProgreso);
    }

    @Override
    protected void done() {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        jpb_progreso.setValue(tamano_archivo);
        label_progreso.setText("Proceso Finalizado: " + hourdateFormat.format(date));

    }

    private void publicarAvance(int avance_numerico, String avance_texto) {
        publish(avance_numerico);
        stringProgreso = avance_texto;
    }

}
