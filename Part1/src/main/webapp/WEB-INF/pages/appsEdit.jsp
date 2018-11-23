<%@include file="includes/header.jsp" %>
<%@ page import="ch.heigvd.amt.mvc.model.UserApplication" %>

<div id="wrapper">

  <%@include file="includes/sidebar.jsp" %>

  <div id="content-wrapper">

    <div class="container-fluid">
      <%
        UserApplication ua = (UserApplication) request.getAttribute("appEdit");
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
    </div>

    <!-- Sticky Footer -->
    <footer class="sticky-footer">
      <div class="container my-auto">
        <div class="copyright text-center my-auto">
          <span>Copyright © Your AMT Project 2018</span>
        </div>
      </div>
    </footer>

  </div>
</div>

<%@include file="includes/footer.jsp" %>
