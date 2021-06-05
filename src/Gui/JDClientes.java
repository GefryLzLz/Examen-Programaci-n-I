
package Gui;

import ConexiónBD.*;
import java.awt.HeadlessException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class JDClientes extends javax.swing.JDialog {

    
    public JDClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         Conexion basedatos= new Conexion();
        Connection conn=null;
        conn=basedatos.obtenerConexion();
    }

    public JDClientes() {
    }
    
    
    
    public void limpiar (){
        txtID.setText("");
        txtnom.setText("");
        txtdir.setText("");                
    }
    
     public void guardar(){
      String Id,nom,des;
      Id=txtID.getText();
      nom=txtnom.getText();
      des=txtdir.getText();
     
      
       if(Id.equals("")||nom.equals("")|| des.equals("")){
            JOptionPane.showMessageDialog(this,"porfavor ingrese todos los datos de los campos");
         }else {
            try{
                Conexion basedatos= new Conexion();
                Connection conn;
                conn= basedatos.obtenerConexion();
                PreparedStatement ps=null;
                //ResultSet consulta=null; 
                
                String sql = "insert into cliente  (IdCliente,	Nombre,	Direccion,estado)values (?, ?, ?,1)";
                
                ps= conn.prepareStatement(sql);
                String id= txtID.getText ();        
                ps.setString(1, id);
                String nomb=txtnom.getText();
                ps.setString(2, nomb);
                String desp=txtdir.getText();
                ps.setString(3, desp);
                
                
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
                    consulta= comando.executeQuery ("SELECT IdCliente,	Nombre,	Direccion FROM `cliente` WHERE estado !=0");
                    
                     DefaultTableModel modelo = new DefaultTableModel();
                    this.tblcli.setModel(modelo);
                     
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
      
    
       
       public void eliminar(){
        int filInicio= tblcli.getSelectedRow();
        int numfila= tblcli.getSelectedRowCount();
        
        ArrayList<String>listausuarios= new ArrayList<>();
        String id=null;
        
        if(filInicio>=0){
            for(int i=0; i<numfila; i++){
                id= String.valueOf(tblcli.getValueAt(i+filInicio,0));
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
                     
                     ps= conn.prepareStatement("UPDATE cliente SET estado= 0 WHERE IdCliente="+id);
                     
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
          public void desbloquear(){
    txtID.setEnabled(true);
    btnCrear.setEnabled(true);
    btnActualizar.setEnabled(true);
    btnEliminar.setEnabled(true);
    btnAceptar.setEnabled(true);
    btnSalir.setEnabled(true);
    btnMostrar.setEnabled(true);
    btnLimpiar.setEnabled(true);
    }
       
      public void cargar() {

		if (tblcli.getSelectedRowCount() > 0) {
                    
			txtID.setText(tblcli.getValueAt(tblcli.getSelectedRow(), 0).toString());
                        txtnom.setText(tblcli.getValueAt(tblcli.getSelectedRow(),1).toString());
                        txtdir.setText(tblcli.getValueAt(tblcli.getSelectedRow(),2).toString());     
		}
    }                   
        
    public void Editar(){
        
       int filInicio = tblcli.getSelectedRow();
        int numfilas = tblcli.getSelectedRowCount();
        ArrayList<String> listaCod = new ArrayList<>();
        String codigo = null;
        if(filInicio>=0){
             for(int i = 0; i<numfilas; i++)
              {
                    codigo = String.valueOf(tblcli.getValueAt(i+filInicio, 0));
                    listaCod.add(i, codigo);
                   
              }
                for(int j = 0; j<numfilas; j++){
                    int rpta = JOptionPane.showConfirmDialog(null, "Desea Actualizar registro con ID: "+listaCod.get(j)+"? ");
              
                        if(rpta==0){
                    //jDateChooser1.setEnabled(false);
                     txtID.setText(listaCod.get(j));             
    
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
 
                        txtID.setText(""); 
                       desbloquear(); 
                 }
                  if(rpta==2){   
    
                        txtID.setText(""); 
                              desbloquear();    
                }
            }
             mostrar();
                }else{
                JOptionPane.showMessageDialog(null, "Elija al menos un registro para actualizar.");
        }
    }
        public void actualizar(){
        String Id,nom,des;
      Id=txtID.getText();
      nom=txtnom.getText();
      des=txtdir.getText();
     
      
       if(Id.equals("")||nom.equals("")|| des.equals("")){
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
                     
                     ps= conn.prepareStatement("UPDATE cliente SET IdCliente=?,	Nombre=?,Direccion=?  WHERE IdCliente="+Id);
                     
                         
                ps.setString(1, txtID.getText ());
                        
                ps.setString(2, txtnom.getText () );
                        
                ps.setString(3, txtdir.getText ());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Actualizado Correctamente");
                } catch (Exception e) {
                       JOptionPane.showMessageDialog(this, "Error al Actualizar:" + e);
                    }
         mostrar();
        }//Fin else
    }
        
       public ArrayList<String> listarID(String Id){
           
           Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                PreparedStatement ps= null;
                     ResultSet consulta = null;
                     ArrayList<String> cliente=new ArrayList<String>();
                try{
                     Statement comando= conn.createStatement();
                consulta= comando.executeQuery("SELECT IdCliente=?,Nombre=?,,Direccion=?, FROM `cliente` WHERE IdCliente=?");
                ps.setString(1, Id);
                consulta =ps.executeQuery();
                while(consulta.next()){
                
                cliente.add(Integer.toString(consulta.getInt(1)));
               cliente.add(Integer.toString(consulta.getInt(2)));
                cliente.add(Integer.toString(consulta.getInt(3)));
                 }
                
                }catch(Exception e){ 
                }
                return cliente;
       
       
       }
        
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtdir = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblcli = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ID Cliente");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Dirreccion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 204, 0)));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnLimpiar))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMostrar)
                    .addComponent(btnEliminar))
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnSalir))
                .addGap(18, 18, 18)
                .addComponent(btnAceptar)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblcli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombres", "Direccion"
            }
        ));
        jScrollPane1.setViewportView(tblcli);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(272, 272, 272))
        );

        jLabel5.setFont(new java.awt.Font("Rockwell", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 153, 255));
        jLabel5.setText("Cliente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
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

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        Editar();
        cargar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(JDClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDClientes dialog = new JDClientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblcli;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtdir;
    private javax.swing.JTextField txtnom;
    // End of variables declaration//GEN-END:variables
}
