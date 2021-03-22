/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import com.mysql.jdbc.Connection;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author ricardo
 */

public class ModeloExcel {
    Workbook wb;
    JFileChooser selecArchivo = new JFileChooser();
     File archivo;
    int contAccion=0;
    public void AgregarFiltro(){
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
    }
    
    public void cargar() throws SQLException, IOException {

        Conexion con = new Conexion();
        PreparedStatement ps;

        try {
            java.sql.Connection conn = con.getConexion();
            FileInputStream file = new FileInputStream(new File("C:\\xampp\\htdocs\\Terceranota\\Prueba.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheetAt(0);

            int numFilas = sheet.getLastRowNum();

            for (int a = 1; a <= numFilas; a++) {
                Row fila = sheet.getRow(a);

                ps = conn.prepareStatement("INSERT INTO notas (codigo, nombres, email, trabajos, quiz, asistencia, terceranota, asignatura) VALUES(?,?,?,?,?,?,?,?)");
               ps.setDouble(1, fila.getCell(0).getNumericCellValue());
               ps.setString(2, fila.getCell(1).getStringCellValue());
               ps.setString(3, fila.getCell(2).getStringCellValue());
               ps.setDouble(4, fila.getCell(3).getNumericCellValue());
                ps.setDouble(5, fila.getCell(4).getNumericCellValue());
                ps.setDouble(6, fila.getCell(5).getNumericCellValue());
                ps.setDouble(7, fila.getCell(6).getNumericCellValue());
                ps.setString(8, fila.getCell(7).getStringCellValue());
                ps.execute();
            }
            
            conn.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ModeloExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public String Importar(File archivo, JTable tablaD){
        String respuesta="No se pudo realizar la importación.";
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        try {
            wb = WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja = wb.getSheetAt(0);
            Iterator filaIterator = hoja.rowIterator();
            int indiceFila=-1;
            while (filaIterator.hasNext()) {                
                indiceFila++;
                Row fila = (Row) filaIterator.next();
                Iterator columnaIterator = fila.cellIterator();
                Object[] listaColumna = new Object[1000];
                int indiceColumna=-1;
                while (columnaIterator.hasNext()) {                    
                    indiceColumna++;
                    Cell celda = (Cell) columnaIterator.next();
                    if(indiceFila==0){
                        modeloT.addColumn(celda.getStringCellValue());
                    }else{
                        if(celda!=null){
                            switch(celda.getCellType()){
                                case Cell.CELL_TYPE_NUMERIC:
                                    listaColumna[indiceColumna]= (int)Math.round(celda.getNumericCellValue());
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    listaColumna[indiceColumna]= celda.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    listaColumna[indiceColumna]= celda.getBooleanCellValue();
                                    break;
                                default:
                                    listaColumna[indiceColumna]=celda.getDateCellValue();
                                    break;
                            }
                            System.out.println("col"+indiceColumna+" valor: true - "+celda+".");
                        }                        
                    }
                }
                if(indiceFila!=0)modeloT.addRow(listaColumna);
            }
            respuesta="Importación exitosa";
        } catch (IOException | InvalidFormatException | EncryptedDocumentException e) {
            System.err.println(e.getMessage());
        }
        return respuesta;
    }
    
    public String Exportar(File archivo, JTable tablaD){
        String respuesta="No se realizo con exito la exportación.";
        int numFila=tablaD.getRowCount(), numColumna=tablaD.getColumnCount();
        if(archivo.getName().endsWith("xls")){
            wb = new HSSFWorkbook();
        }else{
            wb = new XSSFWorkbook();
        }
        Sheet hoja = wb.createSheet("Pruebita");
        
        try {
            for (int i = -1; i < numFila; i++) {
                Row fila = hoja.createRow(i+1);
                for (int j = 0; j < numColumna; j++) {
                    Cell celda = fila.createCell(j);
                    if(i==-1){
                        celda.setCellValue(String.valueOf(tablaD.getColumnName(j)));
                    }else{
                        celda.setCellValue(String.valueOf(tablaD.getValueAt(i, j)));
                    }
                    wb.write(new FileOutputStream(archivo));
                }
            }
            respuesta="Exportación exitosa.";
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return respuesta;
    }
    public static String buscarprofesor(String id, String correo, String contraseña)
    {
        String busqueda=null;
        Connection con=null;
        PreparedStatement sentencia;
        ResultSet resultado;
        try {
            con = Conexion.getConexion();
            String sentencia_buscar=("SELECT codigo,email,password FROM profesor WHERE codigo = '"+id+"' && email = '"+correo+"' && password = '"+contraseña+"'");
            sentencia = (PreparedStatement) con.prepareStatement(sentencia_buscar);
            resultado = sentencia.executeQuery();
            if(resultado.next())
            {
                busqueda = "encontrado";
            }else
            {
                busqueda = "no encontrado";
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return busqueda;
    }
    public static String buscarestudiante(String id, String correo, String contraseña)
    {
        String busqueda=null;
        Connection con=null;
        PreparedStatement sentencia;
        ResultSet resultado;
        try {
            con = Conexion.getConexion();
            String sentencia_buscar=("SELECT codigo,email,password FROM users WHERE codigo = '"+id+"' && email = '"+correo+"' && password = '"+contraseña+"'");
            sentencia = (PreparedStatement) con.prepareStatement(sentencia_buscar);
            resultado = sentencia.executeQuery();
            if(resultado.next())
            {
                busqueda = "encontrado";
            }else
            {
                busqueda = "no encontrado";
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return busqueda;
    }
}
