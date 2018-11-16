<%@ page import="java.util.Collection" %>
<%@include file="includes/header.jsp" %>

<div id="wrapper">

  <%@include file="includes/sidebar.jsp" %>

  <div id="content-wrapper">

    <div class="container-fluid">

      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="./home">Home</a>
        </li>
        <li class="breadcrumb-item active">Admin</li>
      </ol>

      <div class="card mb-3">
        <div class="card-header">
          <i class="fas fa-table"></i>
          All registered users
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
                <th></th>
              </tr>
              </thead>
              <tfoot>
              <tr>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Rights</th>
                <th></th>
              </tr>
              </tfoot>
              <tbody>
              <%
                Collection<User> cu = (Collection<User>) request.getAttribute("users");
                if (cu != null) {
              %>
              <%for (User u : cu) {%>
              <tr <%if(u.isBanned()){%>style="color:red"<%}%>  >
                <td><%=u.getEmail()%></td>
                <td><%=u.getFirstName()%></td>
                <td><%=u.getLastName()%></td>
                <td><%=u.getRight()%></td>
                <td>
                  <div class="row" style="margin:auto">
                    <%
                      System.out.println("Guy " + u.getEmail() + " " + u.isBanned());
                      if (u.isBanned()) {
                    %>
                    <form action="admin" method="post">
                      <button name="unban" value="<%=u.getEmail()%>" data-toggle="tooltip" data-placement="top"
                              title="Click to unban" class="btn btn-default far fa-times-circle"></button>
                    </form>
                    <%
                    } else {
                    %>
                    <form action="admin" method="post">
                      <button name="ban" value="<%=u.getEmail()%>" data-toggle="tooltip" data-placement="top"
                              title="Click to ban" class="btn btn-default fas fa-times-circle"></button>
                    </form>
                    <%
                      }
                    %>
                    <form action="admin" method="post">
                      <button name="resetMail" value="<%=u.getEmail()%>" data-toggle="tooltip" data-placement="top"
                              title="Reset pwd and send mail" class="btn btn-default far fa-envelope"></button>
                    </form>
                  </div>
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
