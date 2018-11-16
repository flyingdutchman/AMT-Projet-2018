<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="${pageContext.request.contextPath}/">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>AMT Project 2018</title>

    <!-- Bootstrap core CSS-->
    <link href="static/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="static/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="static/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="static/css/sb-admin.css" rel="stylesheet">

  </head>

  <body id="page-top">
      <!-- Static navbar -->
      <%--<nav class="navbar navbar-default">--%>
        <%--<div class="container-fluid">--%>
          <%--<div class="navbar-header">--%>
            <%--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">--%>
              <%--<span class="sr-only">Toggle navigation</span>--%>
              <%--<span class="icon-bar"></span>--%>
              <%--<span class="icon-bar"></span>--%>
              <%--<span class="icon-bar"></span>--%>
            <%--</button>--%>
            <%--<a class="navbar-brand" href="">MVC Demo</a>--%>
          <%--</div>--%>
          <%--<div id="navbar" class="navbar-collapse collapse">--%>
            <%--<ul class="nav navbar-nav">--%>
              <%--<li><a href="pages/about">About</a></li>--%>
              <%--<li class="dropdown">--%>
                <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Examples <span class="caret"></span></a>--%>
                <%--<ul class="dropdown-menu">--%>
                  <%--<li><a href="pages/beers">Beers</a></li>--%>
                  <%--<li><a href="pages/ugly">Ugly servlet</a></li>--%>
                  <%--<li><a href="data/json">Json servlet</a></li>--%>
                  <%--<li><a href="data/graph">Graph servlet</a></li>--%>
                  <%--<li><a href="pages/ajax">AJAX page</a></li>--%>
                  <%--<li><a href="pages/concurrency">Concurrency</a></li>--%>
                  <%--<!----%>
                  <%--<li role="separator" class="divider"></li>--%>
                  <%--<li class="dropdown-header">Nav header</li>--%>
                  <%---->--%>
                <%--</ul>--%>
              <%--</li>--%>
            <%--</ul>--%>
            <%--<ul class="nav navbar-nav navbar-right">--%>
              <%--<li><a href="./auth?action=logout">Logout</a></li>--%>
            <%--</ul>--%>
          <%--</div><!--/.nav-collapse -->--%>
        <%--</div><!--/.container-fluid -->--%>
      <%--</nav>--%>

      <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

        <a class="navbar-brand mr-1" href="index.html">AMT Project 2018</a>

        <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
          <i class="fas fa-bars"></i>
        </button>

        <!-- Navbar Search -->
        <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
          <div class="input-group">
            <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
            <div class="input-group-append">
              <button class="btn btn-primary" type="button">
                <i class="fas fa-search"></i>
              </button>
            </div>
          </div>
        </form>

        <!-- Navbar -->
        <ul class="navbar-nav ml-auto ml-md-0">
          <%--<li class="nav-item dropdown no-arrow mx-1 show">--%>
            <%--<a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
              <%--<i class="fas fa-bell fa-fw"></i>--%>
              <%--<span class="badge badge-danger">9+</span>--%>
            <%--</a>--%>
            <%--<div class="dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown">--%>
              <%--<a class="dropdown-item" href="#">Action</a>--%>
              <%--<a class="dropdown-item" href="#">Another action</a>--%>
              <%--<div class="dropdown-divider"></div>--%>
              <%--<a class="dropdown-item" href="#">Something else here</a>--%>
            <%--</div>--%>
          <%--</li>--%>
          <%--<li class="nav-item dropdown no-arrow mx-1">--%>
            <%--<a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">--%>
              <%--<i class="fas fa-envelope fa-fw"></i>--%>
              <%--<span class="badge badge-danger">7</span>--%>
            <%--</a>--%>
            <%--<div class="dropdown-menu dropdown-menu-right" aria-labelledby="messagesDropdown">--%>
              <%--<a class="dropdown-item" href="#">Action</a>--%>
              <%--<a class="dropdown-item" href="#">Another action</a>--%>
              <%--<div class="dropdown-divider"></div>--%>
              <%--<a class="dropdown-item" href="#">Something else here</a>--%>
            <%--</div>--%>
          <%--</li>--%>
          <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <i class="fas fa-user-circle fa-fw"></i>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
              <div class="dropdown-item disabled" >${principal}</div>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#">Settings</a>
              <%--<a class="dropdown-item" href="#">Activity Log</a>--%>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="./auth?action=logout" id="btnLogout">Logout</a>
            </div>
          </li>
        </ul>

      </nav>


