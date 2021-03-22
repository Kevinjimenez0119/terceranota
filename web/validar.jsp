<%-- 
    Document   : validar
    Created on : 30/05/2020, 12:53:49 PM
    Author     : USUARIO
--%>

<%@page import="modelo.ModeloExcel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ModeloExcel m= new ModeloExcel();
    String tipo=request.getParameter("tipo");
    String id=request.getParameter("codigo");
    String email=request.getParameter("email");
    String password=request.getParameter("password");
    if(tipo.equals("0"))
    {
        String m2=m.buscarprofesor(id, email, password);
        if(m2.equals("encontrado"))
        {
            %>
            <jsp:forward page="inicio.jsp"></jsp:forward>
            <%
        }else
        {
            %>
            <jsp:forward page="index.jsp"></jsp:forward>
            <%
        }
    }else
    {
        String m2=m.buscarestudiante(id, email, password);
        if(m2.equals("encontrado"))
        {
            %>
            <jsp:forward page="inicio2.jsp"></jsp:forward>
            <%
        }else
        {
            %>
            <jsp:forward page="index.jsp"></jsp:forward>
            <%
        }
    }
%>
