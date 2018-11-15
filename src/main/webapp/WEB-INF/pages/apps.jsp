<%@ page import="ch.heigvd.amt.mvc.model.UserApplication" %>
<%@ page import="java.util.Collection" %>
<%@include file="includes/header.jsp" %>

<div id="wrapper">

  <%@include file="includes/sidebar.jsp" %>

  <div id="content-wrapper">

    <div class="container-fluid">

      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="/home">Home</a>
        </li>
        <li class="breadcrumb-item active">Apps</li>
      </ol>

      <div class="card mb-3">
        <div class="card-header">
          <i class="fas fa-table"></i>
          Data Table Example
        </div>
        <div class="card-body">
          <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
              <thead>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Description</th>
                <th>Active</th>
                <th>Active</th>
                <th>Active</th>
              </tr>
              </thead>
              <tfoot>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Description</th>
                <th>Active</th>
                <th>Api Key</th>
              </tr>
              </tfoot>
              <tbody>
              <%
                Collection<UserApplication> cua = (Collection<UserApplication>) request.getAttribute("app_list");
                if(cua != null) {
              %>
              <%for(UserApplication ua : cua){ //TODO Add Active variable %>
                <tr>
                  <td><%=ua.getId()%></td>
                  <td><%=ua.getName()%></td>
                  <td><%=ua.getDescription()%></td>
                  <td>False</td>
                  <td><%=ua.getAPI_KEY()%></td>
                </tr>
              <% }}%>

              </tbody>
            </table>
          </div>
        </div>
        <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
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
