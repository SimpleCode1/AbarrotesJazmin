/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import metodos.Conexion;
import metodos.GenerarNumero;


public class Boleta extends javax.swing.JInternalFrame {

    
    public Boleta() {
        initComponents();
        
        jtnumero.setEnabled(false);
        jtfecha.setText(fechaact());
        jtfecha.setEditable(false);
        jtnumero.setDisabledTextColor(Color.red);
        numeros();
    }
    
    public static String fechaact(){
        Date fecha= new Date();
        SimpleDateFormat formatofecha= new SimpleDateFormat("dd/MM/YYYY");
        return formatofecha.format(fecha);
    }
    
    void numeros(){
        Conexion cc= new Conexion();
        Connection cn= cc.getConexcionMYSQL();
        String c="";
        String SQL="select max(num_boleta) from boleta";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(SQL);
            if(rs.next()){              
                 c=rs.getString(1);
            }
            if(c==null){
                jtnumero.setText("00000001");
            }
            else{
            int j=Integer.parseInt(c);
            GenerarNumero gen= new GenerarNumero();
            gen.generar(j);
             jtnumero.setText(gen.serie());
            }

        } catch (SQLException ex) {
           Logger.getLogger(Boleta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void descontarstock(int id_arti, int can){
       Conexion cc= new Conexion();
       Connection cn= cc.getConexcionMYSQL();
       String cap="";
       int desfinal;
       String consul="SELECT * FROM articulo WHERE  id_articulo='"+id_arti+"'";
        try {
            Statement st= cn.createStatement();
            ResultSet rs= st.executeQuery(consul);
            while(rs.next())
            {
                cap= rs.getString(4);
            }
            
            
        } catch (Exception e) {
        }
        int fin = Integer.parseInt(cap);
        desfinal = fin-can;
        String modi="UPDATE articulo SET stock='"+desfinal+"' WHERE id_articulo = '"+id_arti+"'";
        try {
            PreparedStatement pst = cn.prepareStatement(modi);
            pst.executeUpdate();
        } catch (Exception e) {
        }    
    }
    
    void calcular(){
        String pre, can;
        double subtotal=0, precio, imp=0.0;
        int cantidad;
       
        for(int i=0;i<tabla6.getRowCount();i++)
        {
            pre=tabla6.getValueAt(i, 3).toString();
            can=tabla6.getValueAt(i, 5).toString();
            precio=Double.parseDouble(pre);
            cantidad=Integer.parseInt(can);
            
            imp=precio*cantidad;
            subtotal=subtotal+imp;
            
            tabla6.setValueAt(Math.rint(imp*100)/100, i, 6);
            
        }
       
        jttotal.setText(""+Math.rint(subtotal*100)/100);       
    }
    
    void boleta(){
        Conexion cc= new Conexion();
        Connection cn= cc.getConexcionMYSQL();
        String InsertarSQL="INSERT INTO boleta(num_boleta,pre_total,fecha) VALUES (?,?,?)";
        int numbol=Integer.parseInt(jtnumero.getText());
        float total=Float.parseFloat(jttotal.getText());
        String fecha=jtfecha.getText();
        try {
            PreparedStatement pst = cn.prepareStatement(InsertarSQL);
            pst.setInt(1,numbol);
            pst.setFloat(2,total);
            pst.setString(3,fecha);
            int n= pst.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null,"Los datos se guardaron correctamente");
            }
           
        } catch (SQLException ex) {
          
        }
    }
    
    void detalleboleta(){
        Conexion cc= new Conexion();
        Connection cn= cc.getConexcionMYSQL();
        
        for(int i=0;i<tabla6.getRowCount();i++){
            String InsertarSQL="INSERT INTO detalleboleta(num_boleta,id_articulo,categoria,cantidad,costo,venta) VALUES (?,?,?,?,?,?)";
            
            int numbol = Integer.parseInt(jtnumero.getText());
            int idarti = Integer.parseInt(tabla6.getValueAt(i, 0).toString());
            float vent = Float.parseFloat(tabla6.getValueAt(i, 3).toString());
            String cate = tabla6.getValueAt(i, 4).toString();
            int cant= Integer.parseInt(tabla6.getValueAt(i, 5).toString());
            float tot = Float.parseFloat(tabla6.getValueAt(i, 6).toString());
        
    
            try {
                PreparedStatement pst = cn.prepareStatement(InsertarSQL);
                pst.setInt(1,numbol);
                pst.setInt(2,idarti);
                pst.setString(3,cate);
                pst.setInt(4,cant);
                pst.setFloat(5,vent);
                pst.setFloat(6,tot);
          
                pst.executeUpdate();
          
           
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(this, "Error al guard", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla6 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jtfecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtnumero = new javax.swing.JTextField();
        jttotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btncalculo = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnventa = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel1.setText("Boleta de venta");

        tabla6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id articulo", "Codigo", "Nombre", "Venta", "Categoria", "Cantidad", "Venta total"
            }
        ));
        jScrollPane1.setViewportView(tabla6);

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel2.setText("Fecha");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setText("Productos");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel4.setText("Boleta de venta");

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel5.setText("Nº");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel6.setText("Total");

        btncalculo.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btncalculo.setText("Realizar calculo");
        btncalculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalculoActionPerformed(evt);
            }
        });

        btneliminar.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnventa.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnventa.setText("Realizar venta");
        btnventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnventaActionPerformed(evt);
            }
        });

        btnsalir.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtfecha)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(37, 37, 37))))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btncalculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnventa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1)
                    .addComponent(jtnumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btncalculo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btneliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnventa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnsalir)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         try {
            Productos2 pro2= new Productos2();
            Menu.escritorio.add(pro2);
            pro2.toFront();
            pro2.setVisible(true);
        
         } catch (Exception e) {
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btncalculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalculoActionPerformed
         if(tabla6.getRowCount()<1)
         {
            JOptionPane.showMessageDialog(this, "ingrese algun producto");
          }
          else
          {
            calcular();
          }
    }//GEN-LAST:event_btncalculoActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        DefaultTableModel model = (DefaultTableModel) tabla6.getModel();
        int fila = tabla6.getSelectedRow();
        if(fila>=0)
        {
            model.removeRow(fila);
        }
        else
        {
         JOptionPane.showMessageDialog(null, "Tabla vacia o no se selecciono ninguna fila");
        }
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnventaActionPerformed
    if(jttotal.getText().equals("")){
        JOptionPane.showMessageDialog(this, "Ingrese producto o realice operacion");
    }
    else{
         int id_arti,can;
         for(int i=0;i<Boleta.tabla6.getRowCount();i++){
            id_arti = Integer.parseInt(Boleta.tabla6.getValueAt(i, 0).toString());
            can = Integer.parseInt(Boleta.tabla6.getValueAt(i, 5).toString());
            descontarstock(id_arti, can);  
         }
         boleta();
         detalleboleta();
        jttotal.setText("");
       
        DefaultTableModel modelo = (DefaultTableModel) tabla6.getModel();
        int a =tabla6.getRowCount()-1;
        int i;
        for(i=a;i>=0;i--)
        {
            modelo.removeRow(i);
        }
        numeros();
    }
    }//GEN-LAST:event_btnventaActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
         this.dispose();
    }//GEN-LAST:event_btnsalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncalculo;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JButton btnventa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtfecha;
    private javax.swing.JTextField jtnumero;
    private javax.swing.JTextField jttotal;
    public static javax.swing.JTable tabla6;
    // End of variables declaration//GEN-END:variables
}
