<%@ page import="ch.heigvd.amt.mvc.model.UserApplication" %><%--
  Created by IntelliJ IDEA.
  User: jimmy
  Date: 16.11.2018
  Time: 01:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<%
  UserApplication ua = (UserApplication) request.getAttribute("appEdit");
  System.out.println("QQC" + ua);
%>
  <div class="card card-login mx-auto mt-5">
    <div class="card-header">Application Informations</div>
    <div class="card-body">
      <form method="POST" action="edit" class="form-signin">
        <div class="form-group">
          <div class="form-label-group">
            <input type="text" name="name" id="inputName" class="form-control" placeholder="Name" required autofocus>
            <label for="inputName"><%=ua.getName()%></label>
          </div>
        </div>
        <div class="form-group">
          <div class="form-label-group">
            <input type="text" name="description" id="inputDescription" class="form-control" placeholder="Description" required>
            <label for="inputDescription"><%=ua.getDescription()%></label>
          </div>
        </div>
        <div class="form-group">
          <div class="form-label-group">
            <input type="text" name="APIKey" id="inputAPIKey" class="form-control" placeholder="API Key" required readonly>
            <label for="inputAPIKey"><%=ua.getAPI_KEY()%></label>
          </div>
        </div>
        <input type="hidden" name="idApp" value="<%=ua.getId()%>">
        <button class="btn btn-lg btn-primary btn-block" id="btnUpdate" type="submit">Update</button>
      </form>
    </div>
  </div>
</body>
</html>
