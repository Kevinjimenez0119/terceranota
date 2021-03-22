<%-- 
    Document   : inicio2
    Created on : 29/05/2020, 12:58:52 PM
    Author     : USUARIO
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <title>Inicio</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!-- CSS -->
  <link rel="stylesheet" href="main.css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>

<body>

  <div class="main-inicio contenedor">

    <!-- MENU ADMI -->
    <div class="menu-incio">
      <nav class="displey-flex-between">

        <!-- LOGO -->
        <ul class="displey-flex items-logos">
          <li>
            <img src="logoufps.jpg" title="Nuestra Universidad!" class="logo">
          </li>
        </ul>

        <!-- ENLACES -->
        <ul class="items displey-flex-between" id="menu-superior">
          <li>
            <a class="enlace cerrar-menu" onclick="abrirMenu()">
              <img img class="icono" src="close.png" title="Cerrar Menú">
            </a>
          </li>

        </ul>

        <!-- ICONOS -->
        <ul class="displey-flex-between items-iconos">
          <li class="icono-menu">
            <a class="enlace" onclick="abrirMenu()">
              <img class="icono" src="menu.png">
            </a>
          </li>
          <li class="displey-flex">
            <a class="enlace">
              <img class="icono" src="user.png" title="Usuario">
            </a>
            <span><span></li>
          <li class="displey-flex">

              <img class="icono" src="session.png" title="Cerrar Sesión">
            </a>
            <input type="button" onclick="location.href='index.jsp';" value="SALIR" />
        </ul>
      </nav>
    </div>
    <div class="section">



      <div class="contenedor-principal contenedor-tablas">

        <table id="datos" class="tabla-clientes tabla-generica">
          <thead>
            <tr>
              <th>CODIGO</th>
              <th>NOMBRE</th>
              <th>CORREO</th>
              <th>TRABAJOS</th>
              <th>QUIZ</th>
              <th>ASISTENCIA</th>
              <th>TERCERA NOTA</th>
            </tr>
          </thead>

          <tbody>
            

             
             
            <% 
        Connection con=null;
        Statement st=null;
        ResultSet re=null;
   
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
            System.out.println("conexion establecida");
            st = con.createStatement();
            re = st.executeQuery("select * from notas where codigo = '"+request.getParameter("codigo")+"'");
            while(re.next()){
            %>
            <h1 class="titulo"> <%=re.getString(8) %></h1>
            <tr>
                <th><%=re.getString(1) %></th>
                <th><%=re.getString(2) %></th>
                <th><%=re.getString(3) %></th>
                <th><%=re.getString(4) %></th>
                <th><%=re.getString(5) %></th>
                <th><%=re.getString(6) %></th>
                <th><%=re.getString(7) %></th>
                
                
                
            </tr>
            
            
    
                
            <% 
                }
            } catch (Exception e) {
                 System.out.println(e);
            }

        %>
          </tbody>
        </table>
      </div>
    </div>




    <!--JS -->
    <script src="assets/js/main.js"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous">
    </script>
    <!-- DATATABLES -->
    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"> </script>
</body>

</html>
