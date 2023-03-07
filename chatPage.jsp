<%-- 
    Document   : chatPage
    Created on : Feb 1, 2023, 8:07:21 PM
    Author     : Harmony
--%>

<%@page import="model.Chat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="dao.DbConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat Page - Logistics ChatBot</title>
        
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
        
        <%
           String sessionId = (String)request.getAttribute("sessionId");
           
           DbConnection dbobject = new DbConnection();
           
           getServletContext().setAttribute("garageList", dbobject.viewChats(sessionId));
           List<Chat> garageList = (List<Chat>) application.getAttribute("garageList");
           Iterator<Chat> iterator = garageList.iterator();
             
       %>
        
    </head>
    <body>
        <jsp:include page="headers.jsp"/>
        <br />
        <br />
     

        <div class="col-lg-8 mx-auto p-3 py-md-5">
            <div class="row"></div>
            <br/>
            <br/>
        
            <main>
                <%
                    while (iterator.hasNext()) {
                        Chat c = (Chat) iterator.next();
                %>
                <p>You: <%=c.getQuery()%></p>
                <br/>
                <p>Bot: <%=c.getRespond()%></p>
                <%
                    }
                %>
                <form  class="row needs-validation" method="post" action="/ChatterBot?action=sendMessage"" novalidate>
                    <div class="row">
                        <div class="col-sm-12">
                            <label for="question">Type Message</label>
                            <textarea class="form-control" name="question"></textarea>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-sm-3">
                            <input type="hidden" name="sessionId" value="<%=sessionId%>"/>
                            
                            <button type="submit" class="btn btn-primary">Send Message</button>
                        </div>
                    </div>
                    
                </form>
                
                
            </main>
            
        </div>
        <jsp:include page="footer.jsp"/>
        <script src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>
