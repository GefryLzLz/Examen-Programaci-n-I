
package Gui;
// Importamos las librerias que utilicemos.
import ConexiónBD.*;
import java.awt.HeadlessException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class JDVenta extends javax.swing.JDialog {

     DefaultTableModel modelo=new DefaultTableModel();
     int can;
     int precio;
     double tpagar;
     
     
    public JDVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Conexion basedatos= new Conexion();
        Connection conn=null;
        conn=basedatos.obtenerConexion();
        
    }
     //Creacion de metodos para buacar Clientes, Proveedores, Producto y vendedor
      public void buscarCliente(){
          String id=txtidc.getText();       
           Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                PreparedStatement ps= null;
                     ResultSet rs = null;
                     if(txtidc.getText().equals("")){
                         JOptionPane.showMessageDialog(this,"Debe ingresar ID del Cliente");
                     }else{
                     try{
                     Statement comando= conn.createStatement();
                rs= comando.executeQuery("SELECT Nombre from cliente WHERE IdCliente="+id+"");

                while(rs.next()){
               
                txtcli.setText(rs.getString(1));
                 }
                
                }catch(Exception e){ 
                    JOptionPane.showMessageDialog(this, "Error al buscar:" + e);
           } 
        }
    } 
     
      public void buscarProveedor(){
          String nit=txtnit.getText();       
           Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                PreparedStatement ps= null;
                     ResultSet rs = null;
                       if(txtnit.getText().equals("")){
                         JOptionPane.showMessageDialog(this,"Debe ingresar NIT del Proveedor");
                     }else{
                     try{
                     Statement comando= conn.createStatement();
                rs= comando.executeQuery("SELECT Nombre from proveedores WHERE Nit="+nit+"");

                while(rs.next()){
               
                txtprove.setText(rs.getString(1));
                 }
                
                }catch(Exception e){ 
                    JOptionPane.showMessageDialog(this, "Error al buscar:" + e);
            } 
        }
    } 
      
       public void buscarProducto(){
          String Id=txtidp.getText();       
           Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                PreparedStatement ps= null;
                     ResultSet rs = null;
                       if(txtidp.getText().equals("")){
                         JOptionPane.showMessageDialog(this,"Debe ingresar ID del Proveedor");
                     }else{
                     try{
                     Statement comando= conn.createStatement();
                rs= comando.executeQuery("SELECT Nom_Prod from producto WHERE ID_Prod="+Id+"");

                while(rs.next()){
               
                txtnom.setText(rs.getString(1));
                 }
                
                }catch(Exception e){ 
                    JOptionPane.showMessageDialog(this, "Error al buscar:" + e);
            } 
        }
    } 
        public void buscarPrecio(){
          String Id=txtidp.getText();       
           Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                PreparedStatement ps= null;
                     ResultSet rs = null;
                       if(txtidp.getText().equals("")){
                         JOptionPane.showMessageDialog(this,"Debe ingresar ID del Proveedor");
                     }else{
                     try{
                     Statement comando= conn.createStatement();
                rs= comando.executeQuery("SELECT Pre_Ven from producto WHERE ID_Prod="+Id+"");

                while(rs.next()){
               
                txtuni.setText(rs.getString(1));
                 }
                
                }catch(Exception e){ 
                    JOptionPane.showMessageDialog(this, "Error al buscar:" + e);
            } 
        }
    } 
         public void buscarVendedor(){
          String Id=txtidven.getText();       
           Conexion basedatos= new Conexion();
                Connection conn;
                conn=basedatos.obtenerConexion();
                PreparedStatement ps= null;
                     ResultSet rs = null;
                       if(txtidven.getText().equals("")){
                         JOptionPane.showMessageDialog(this,"Debe ingresar ID del Vendedor");
                     }else{
                     try{
                     Statement comando= conn.createStatement();
                rs= comando.executeQuery("SELECT Nombre from vendedor WHERE IdVendedor="+Id+"");

                while(rs.next()){
               
                txtvenr.setText(rs.getString(1));
                 }
                
                }catch(Exception e){ 
                    JOptionPane.showMessageDialog(this, "Error al buscar:" + e);
            } 
        }
    } 
      //Metodo para agregar en un ARRAYLITS los productos para que se muestre en la tabla   
        public void agregar(){
           double total;
           int item=0;
           modelo=(DefaultTableModel)tbldetalle.getModel();
           String id=txtidp.getText();
           String nombre=txtnom.getText();
            precio=Integer.parseInt(txtuni.getText().toString());
            can=Integer.parseInt(jscan.getText().toString());
           total=can*precio;
           ArrayList lista=new ArrayList();
           lista.add(item);
           lista.add(id);
           lista.add(nombre);
           lista.add(can);
           lista.add(precio);
           lista.add(total);
           Object []ob=new Object[6];
           ob[0]=lista.get(0);
           ob[1]=lista.get(1);
           ob[2]=lista.get(2);
           ob[3]=lista.get(3);
           ob[4]=lista.get(4);
           ob[5]=lista.get(5);
           modelo.addRow(ob);
           tbldetalle.setModel(modelo);
           calculartotal();
                 
        }
       
        
          //Metodo para calcular el total a pagar  
        
        public void calculartotal(){
        
            tpagar=0;
            for (int i=0; i<tbldetalle.getRowCount(); i++){
             can=Integer.parseInt(tbldetalle.getValueAt(i, 3).toString());
            precio=Integer.parseInt(tbldetalle.getValueAt(i, 4).toString());
            tpagar=tpagar+(can*precio);
            }
             txttotal.setText(""+tpagar);
        
        }
        //Metodo de limpiar al finalizar una busqueda
        public void limpiar(){
        txtidp.setText("");
        txtnom.setText("");
        jscan.setText("");
        txtuni.setText("");
        
        }
        
        //Metodo con conexion a la base de datos para guardar la venta
        public void guardar(){
      String idcli,idvende,nitp,idpro,cant,pre,fch,tt;
      idcli=txtidc.getText();
      idvende=txtidven.getText();
      nitp=txtnit.getText();
      idpro=txtidp.getText();
      cant=jscan.getText();
      pre=txtuni.getText();
      fch=jDateChooser1.getDateFormatString();
      tt=txttotal.getText();
      
       if(idcli.equals("")||idvende.equals("")||nitp.equals("")|| idpro.equals("")
               ||cant.equals("")||pre.equals("")|| fch.equals("")|| tt.equals("")){
            JOptionPane.showMessageDialog(this,"porfavor ingrese todos los datos de los campos");
         }else {
            try{
                Conexion basedatos= new Conexion();
                Connection conn;
                conn= basedatos.obtenerConexion();
                PreparedStatement ps=null;

                String sql = "insert into factura  (IdClientes,IdVendedor,NIT_PROVEE,"
                        + "ID_Prod,Cantidad,Precio_Uni,Fecha,Total,estado)values (?, ?, ?,?,?, ?, ?,?,1)";
                
                ps= conn.prepareStatement(sql);
                String idc= txtidc.getText ();        
                ps.setString(1, idc);
                String idven=txtidven.getText();
                ps.setString(2, idven);
                String Nitp=txtnit.getText();
                ps.setString(3, Nitp);
                String idpr=txtidp.getText ();        
                ps.setString(4, idpr);
                String ant=jscan.getText();
                ps.setString(5, ant);
                String prec=txtuni.getText ();        
                ps.setString(6, prec);
                 String fching= new SimpleDateFormat("yyyy-MM-dd").format (jDateChooser1.getDate());
                ps.setString(7, fching);
                String tta=txttotal.getText ();        
                ps.setString(8, tta);
 
                 limpiar();
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Los datos han sido almacenados");
             
         }catch (HeadlessException | SQLException e){
                JOptionPane.showMessageDialog(this, "Error al almacer los datos");
                System.out.println(e.getMessage());         
            }
        }
    }
        
     // Cada boton tiene su propio metodo los cuales son los que estan arriba.    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtno = new javax.swing.JTextField();
        txtidc = new javax.swing.JTextField();
        txtcli = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnCod = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtprove = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtnit = new javax.swing.JTextField();
        btnnit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtidven = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtvenr = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtidp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtnom = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtuni = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldetalle = new javax.swing.JTable();
        btnId = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jscan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        btnguardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Serie de FAC.");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID Cliente");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Cliente");

        txtcli.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Fecha ");

        btnCod.setText("Buscar");
        btnCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Proveedor");

        txtprove.setEditable(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nit Proveedor");

        btnnit.setText("Buscar");
        btnnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnitActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ID Vendedor");

        txtidven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidvenActionPerformed(evt);
            }
        });

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Vendedor");

        txtvenr.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtcli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtidc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnCod, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtidven))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnit)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtprove)
                    .addComponent(txtvenr, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtidc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCod)
                            .addComponent(jLabel4)
                            .addComponent(txtidven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jLabel6)
                            .addComponent(txtvenr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtcli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(txtprove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnit))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("IDproducto");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Cantidad");

        txtidp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidpActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Nombre del producto");

        txtnom.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Precio unitario");

        txtuni.setEditable(false);

        tbldetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "ID Producto", "Producto", "Cantidad", "Precio Uni", "Total"
            }
        ));
        jScrollPane1.setViewportView(tbldetalle);

        btnId.setText("Buscar");
        btnId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIdActionPerformed(evt);
            }
        });

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Limpiar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 51, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(txtidp, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnId, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jscan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtuni, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtidp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(txtuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnId))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jscan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("Venta de zapatos");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Precio Total");

        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(407, 407, 407)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel13)
                .addGap(32, 32, 32)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalir)
                        .addComponent(btnguardar)))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodActionPerformed
        
     buscarCliente();
    }//GEN-LAST:event_btnCodActionPerformed

    private void txtidpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidpActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtidpActionPerformed

    private void btnnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnitActionPerformed
        // TODO add your handling code here:
        buscarProveedor();
    }//GEN-LAST:event_btnnitActionPerformed

    private void btnIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIdActionPerformed
        // TODO add your handling code here:
        buscarProducto();
        buscarPrecio();
    }//GEN-LAST:event_btnIdActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        agregar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtidvenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidvenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidvenActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        buscarVendedor();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_btnguardarActionPerformed

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
            java.util.logging.Logger.getLogger(JDVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDVenta dialog = new JDVenta(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCod;
    private javax.swing.JButton btnId;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnguardar;
    private javax.swing.JButton btnnit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jscan;
    private javax.swing.JTable tbldetalle;
    private javax.swing.JTextField txtcli;
    private javax.swing.JTextField txtidc;
    private javax.swing.JTextField txtidp;
    private javax.swing.JTextField txtidven;
    private javax.swing.JTextField txtnit;
    private javax.swing.JTextField txtno;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtprove;
    private javax.swing.JTextField txttotal;
    private javax.swing.JTextField txtuni;
    private javax.swing.JTextField txtvenr;
    // End of variables declaration//GEN-END:variables
}
