<%@include file="includes/header.jsp" %>

<div id="wrapper">

  <%@include file="includes/sidebar.jsp" %>

  <div id="content-wrapper">

    <div class="container-fluid">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#">Home</a>
        </li>
        <li class="breadcrumb-item active">Overview</li>
      </ol>
      <h2>Welcome to the demo app!</h2>

      <div class="alert alert-info" role="alert">
        You are logged in as ${principal}.
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
