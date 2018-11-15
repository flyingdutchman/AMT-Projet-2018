<%@ page import="ch.heigvd.amt.mvc.model.User" %>
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
        <li class="breadcrumb-item active">Users</li>
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
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Rights</th>
                <th>Nb Applications</th>
              </tr>
              </thead>
              <tfoot>
              <tr>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Rights</th>
                <th>Nb Applications</th>
              </tr>
              </tfoot>
              <tbody>
              <%
                Collection<User> cu = (Collection<User>) request.getAttribute("users");
                if (cu != null) {
              %>
              <%for (User u : cu) {%>
              <tr>
                <td><%=u.getEmail()%>
                </td>
                <td><%=u.getFirstName()%>
                </td>
                <td><%=u.getLastName()%>
                </td>
                <td><%=u.getRight()%>
                </td>
                <td><%=u.getApplicationList() == null ? 0 : u.getApplicationList().size()%>
                </td>
              </tr>
              <% }
              }%>

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
