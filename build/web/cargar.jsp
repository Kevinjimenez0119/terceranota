<%-- 
    Document   : cargar
    Created on : 29/05/2020, 01:26:53 PM
    Author     : USUARIO
--%>

<%@page import="java.io.File"%>
<%@page import="javax.swing.JFileChooser"%>
<%@page import="modelo.ModeloExcel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
     
    ModeloExcel m=new ModeloExcel();
    String archivo=request.getParameter("0");
    m.cargar();
%>
<jsp:forward page="inicio.jsp"></jsp:forward>
