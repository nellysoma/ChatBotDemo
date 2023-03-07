<%-- 
    Document   : index
    Created on : Jan 24, 2023, 7:39:44 PM
    Author     : Comfort
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home - Logistics ChatBot</title>
        
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <style>
            .bd-placeholder-img {
                font-size: 1.125rem;
                text-anchor: middle;
                -webkit-user-select: none;
                -moz-user-select: none;
                user-select: none;
            }

            @media (min-width: 768px) {
                .bd-placeholder-img-lg {
                    font-size: 3.5rem;
                }
            }
        </style>


        <!-- Custom styles for this template -->
        <link href="css/sticky-footer-navbar.css" rel="stylesheet">
        
        
    </head>
    <body>
        <jsp:include page="headers.jsp"/>
        <br />
        <br />
     

        <div class="col-lg-8 mx-auto p-3 py-md-5">
            <div class="row"></div>
            <img width="100%" height="225" src="home.jpg" class="rounded card-img">
            <br/>
            <br/>
        
            <main>
                <h1>Logistics ChatBot</h1>
                <br/>
                
                
                <a href="/ChatterBot?action=goToChatPage"" class="btn-primary btn-lg">Start Chat</a>
                
                
            </main>
            
        </div>
        <jsp:include page="footer.jsp"/>
        <script src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>
