<%@include file="includes/header.jsp" %>

<div id="wrapper">

  <%@include file="includes/sidebar.jsp" %>

  <div id="content-wrapper">

    <div class="container-fluid">

      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="home">Home</a>
        </li>
        <li class="breadcrumb-item">
          <a href="apps">Apps</a>
        </li>
        <li class="breadcrumb-item active">New</li>
      </ol>

      <div class="card mb-3">
        <div class="card-header mb-3">
          <i class="fas fa-plus"></i>
          New App
        </div>

        <div class="container-fluid">
          <form method="POST" action="apps/new" class="form-signin">
            <div class="form-group">
              <div class="form-label-group">
                <input type="text" name="appName" id="inputAppName" class="form-control" placeholder="App Name" required autofocus>
                <label for="inputAppName">App Name</label>
              </div>
            </div>
            <div class="form-group">
              <div class="form-label-group">
                <input type="text" name="appDescription" id="inputAppDescription" class="form-control" placeholder="Description" required>
                <label for="inputAppDescription">Description</label>
              </div>
            </div>
            <input type="hidden" name="action" value="login">
            <button class="btn btn-lg btn-primary btn-block mb-3" id="btnCreate" type="submit">Create</button>
          </form>
        </div>
      </div>

    </div> <!-- End Container Fluid -->


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
