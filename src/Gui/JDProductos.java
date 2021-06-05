
package Gui;

import ConexiónBD.*;
import java.awt.HeadlessException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JDProductos extends javax.swing.JDialog {

    
    public JDProductos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Conexion basedatos= new Conexion();
        Connection conn=null;
        conn=basedatos.obtenerConexion();
    }
        
    // Creacion de los metodos
    
    public void limpiar (){
        txtId.setText("");
        txtNomProd.setText("");
        txtDesProd.setText("");
        jDateChooser1.setDateFormatString("");
        jDateChooser2.setDateFormatString("");
        txtPreVen.setText("");
        txtPreCom.setText("");                
    }
    
    public void guardar(){
      String Id,nom,des,fcad,fcven,ven,com;
      Id=txtId.getText();
      nom=txtNomProd.getText();
      des=txtDesProd.getText();
      fcad=jDateChooser1.getDateFormatString();
      fcven=jDateChooser2.getDateFormatString();
      ven=txtPreVen.getText();
      com=txtPreCom.getText();
      
       if(Id.equals("")||nom.equals("")|| des.equals("")|| fcad.equals("")|| 
                 fcven.equals("")|| ven.equals("")|| com.equals("")){
            JOptionPane.showMessageDialog(this,"porfavor ingrese todos los datos de los campos");
         }else {
            try{
                Conexion basedatos= new Conexion();
                Connection conn;
                conn= basedatos.obtenerConexion();
                PreparedStatement ps=null;
                //ResultSet consulta=null; 
                
                String sql = "insert into producto  (ID_Prod,Nom_Prod,Des_Prod,Fech_Adq,Fech_Ven,"
                        + "Pre_Ven,Pre_Com,estado)values (?, ?, ?,?,?,?,?,1)";
                
                ps= conn.prepareStatement(sql);
                String id= txtId.getText ();        
                ps.setString(1, id);
                String nomb=txtNomProd.getText();
                ps.setString(2, nomb);
                String desp=txtDesProd.getText();
                ps.setString(3, desp);
                String fchcadq= new SimpleDateFormat("yyyy-MM-dd").format (jDateChooser1.getDate());
                ps.setString(4, fchcadq);
                String fching= new SimpleDateFormat("yyyy-MM-dd").format (jDateChooser2.getDate());
                ps.setString(5, fching);
                String pven= txtPreVen.getText ();        
                ps.setString(6, pven);
                String pcom=txtPreCom.getText();
                ps.setString(7, pcom);
                
                 limpiar();
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Los datos han sido almacenados");

                
                
         }catch (HeadlessException | SQLException e){
                JOptionPane.showMessageDialog(this, "Error al almacer los datos");
                System.out.println(e.getMessage());         
            }
        }
    }
    
    public void mostrar (){
        Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                ResultSet consulta = null;
        try{

     Statement comando= conn.createStatement();
                    consulta= comando.executeQuery ("SELECT ID_Prod,Nom_Prod,Des_Prod,Fech_Adq,Fech_Ven,Pre_Ven,"
                            + "Pre_Com FROM `producto` WHERE estado !=0");
                    
                     DefaultTableModel modelo = new DefaultTableModel();
                    this.tblProd.setModel(modelo);
                     
                      ResultSetMetaData rmd = consulta.getMetaData();
                    int numcol = rmd.getColumnCount();     
             for (int i=0; i<numcol;i++){
                        modelo.addColumn(rmd.getColumnLabel (i+1)); 
                        
                        } while (consulta.next()){
                        Object [] fila= new Object[numcol];
                        for (int i=0; i<numcol;i++){
                         fila [i]=  consulta.getObject(i+1);
                        
                         } 
                        modelo.addRow(fila);
                        }
                        
                        consulta.first();
    
        }catch (Exception e){
                    System.out.println("Error"+e);
                }
         }
    
       public void desbloquear(){
    txtId.setEnabled(true);
    btnCrear.setEnabled(true);
    btnActualizar.setEnabled(true);
    btnEliminar.setEnabled(true);
    btnAceptar.setEnabled(true);
    btnSalir.setEnabled(true);
    btnMostrar.setEnabled(true);
    btnLimpiar.setEnabled(true);
    }
       
       public void eliminar(){
        int filInicio= tblProd.getSelectedRow();
        int numfila= tblProd.getSelectedRowCount();
        
        ArrayList<String>listausuarios= new ArrayList<>();
        String id=null;
        
        if(filInicio>=0){
            for(int i=0; i<numfila; i++){
                id= String.valueOf(tblProd.getValueAt(i+filInicio,0));
                listausuarios.add(i, id);
            
            }
            
             for (int j=0; j<numfila;j++){
            int resp = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro"+listausuarios.get(j)+"? ");
            if(resp==0){
                int filAfectada= 0;
               
                  try{
                     Conexion basedatos= new Conexion();
                     Connection conn;
                     conn= basedatos.obtenerConexion();
                     PreparedStatement ps= null;
                     ResultSet consulta = null;
                     
                     ps= conn.prepareStatement("UPDATE producto SET estado= 0 WHERE ID_Prod="+id);
                     
                     int res= ps.executeUpdate();
                     if(res>0){
                         JOptionPane.showMessageDialog(null, "Los datos han sido Eliminados");
                         mostrar();
                     }else{
                         JOptionPane.showMessageDialog(null, "Error al Eliminar los datos");
                     }
                }catch(Exception e){
                    System.err.println(e);
                }
            }
        }
    }else{
        JOptionPane.showMessageDialog(null, "Porfavor elija un registro para eliminar");
    }
} 
     public void cargar() {

		if (tblProd.getSelectedRowCount() > 0) {
                    
			txtId.setText(tblProd.getValueAt(tblProd.getSelectedRow(), 0).toString());
                        txtNomProd.setText(tblProd.getValueAt(tblProd.getSelectedRow(),1).toString());
                        txtDesProd.setText(tblProd.getValueAt(tblProd.getSelectedRow(),2).toString());
			txtPreVen.setText(tblProd.getValueAt(tblProd.getSelectedRow(), 3).toString());
			txtPreCom.setText(tblProd.getValueAt(tblProd.getSelectedRow(), 4).toString());
                        
		}
	}                   
        
    public void Editar(){
        
       int filInicio = tblProd.getSelectedRow();
        int numfilas = tblProd.getSelectedRowCount();
        ArrayList<String> listaCod = new ArrayList<>();
        String codigo = null;
        if(filInicio>=0){
             for(int i = 0; i<numfilas; i++)
              {
                    codigo = String.valueOf(tblProd.getValueAt(i+filInicio, 0));
                    listaCod.add(i, codigo);
                   
              }
                for(int j = 0; j<numfilas; j++){
                    int rpta = JOptionPane.showConfirmDialog(null, "Desea Actualizar registro con ID: "+listaCod.get(j)+"? ");
              
                        if(rpta==0){
                    //jDateChooser1.setEnabled(false);
                     txtId.setText(listaCod.get(j));             
    
                    //btnnuevo.setEnabled(false);
                btnMostrar.setEnabled(false);
                btnActualizar.setEnabled(false);
                btnEliminar.setEnabled(false);
                btnLimpiar.setEnabled(false);
                btnSalir.setEnabled(false);
                btnCrear.setEnabled(false);
                btnAceptar.setEnabled(true);
                }
                  if(rpta==1){
 
                        txtId.setText(""); 
                       desbloquear(); 
                 }
                  if(rpta==2){   
    
                        txtId.setText(""); 
                              desbloquear();    
                }
            }
             mostrar();
                }else{
                JOptionPane.showMessageDialog(null, "Elija al menos un registro para actualizar.");
            }
        } 
    
     public void actualizar(){
      String Id,nom,des,fcad,fcven,ven,com;
      Id=txtId.getText();
      nom=txtNomProd.getText();
      des=txtDesProd.getText();
      fcad=jDateChooser1.getDateFormatString();
      fcven=jDateChooser2.getDateFormatString();
      ven=txtPreVen.getText();
      com=txtPreCom.getText();
      
       if(Id.equals("")||nom.equals("")|| des.equals("")|| fcad.equals("")|| 
                 fcven.equals("")|| ven.equals("")|| com.equals("")){
            JOptionPane.showMessageDialog(this,"Llene todos los campos de nuevo");
       }
       else{
           cargar();

               
                  try{
                     Conexion basedatos= new Conexion();
                     Connection conn;
                     conn= basedatos.obtenerConexion();
                     PreparedStatement ps= null;
                     ResultSet consulta = null;
                     
                     ps= conn.prepareStatement("UPDATE producto SET ID_Prod=?,Nom_Prod=?,Des_Prod=?,Fech_Adq=?,Fech_Ven=?,"
                             + "Pre_Ven=?,Pre_Com=?  WHERE ID_Prod="+Id);
                     
                         
                ps.setString(1, txtId.getText ());
                        
                ps.setString(2, txtNomProd.getText () );
                        
                ps.setString(3, txtDesProd.getText ());
                 
                ps.setString(4, txtPreVen.getText());
                 
                ps.setString(5, txtPreCom.getText());
                 
              
                String Nacimiento= new SimpleDateFormat("yyyy-MM-dd").format (jDateChooser2.getDate());
                ps.setString(6, Nacimiento);
               
                
                String fecha= new SimpleDateFormat("yyyy-MM-dd").format (jDateChooser1.getDate());
                ps.setString(7, fecha);
               
              
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Actualizado Correctamente");
                } catch (Exception e) {
                       JOptionPane.showMessageDialog(this, "Error al Actualizar:" + e);
                    }
         mostrar();
        }//Fin else
}
       //Cada boton tiene sus eventos con su correspondiente metodo.
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNomProd = new javax.swing.JTextField();
        txtDesProd = new javax.swing.JTextField();
        txtPreVen = new javax.swing.JTextField();
        txtPreCom = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProd = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Perpetua Titling MT", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 153));
        jLabel1.setText("Producto");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Fecha de vencimiento");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("ID Producto");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Nombre");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Descripción");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Fecha de adquisición");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Precio de venta");

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Precio de compra");

        txtNomProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDesProd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPreVen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPreCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel2)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(txtNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPreVen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtDesProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPreCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblProd);

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnMostrar.setText("Mostrar");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actulizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnLimpiar))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMostrar)
                    .addComponent(btnEliminar))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnSalir))
                .addGap(18, 18, 18)
                .addComponent(btnAceptar)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(330, 330, 330)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        // TODO add your handling code here:
        mostrar();
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtNomProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomProdActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        Editar();
        cargar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        actualizar();
         desbloquear();
        limpiar();
    }//GEN-LAST:event_btnAceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDProductos dialog = new JDProductos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnSalir;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProd;
    private javax.swing.JTextField txtDesProd;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNomProd;
    private javax.swing.JTextField txtPreCom;
    private javax.swing.JTextField txtPreVen;
    // End of variables declaration//GEN-END:variables
}
