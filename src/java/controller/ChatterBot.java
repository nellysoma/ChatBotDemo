/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DbConnection;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

/**
 *
 * @author Harmony
 */
public class ChatterBot extends HttpServlet {

    
    private static final boolean TRACE_MODE = false;
    static String botName = "super";
    
    public void sendMessage(HttpServletRequest request, HttpServletResponse response){
        
        try {

			String resourcesPath = getResourcesPath(request,response);
			System.out.println(resourcesPath);
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();
			String textLine = "";
                        
                        String question = request.getParameter("question");
                        String sessionId = request.getParameter("sessionId");

			while(true) {
				System.out.print("Human : ");
				//textLine = IOUtils.readInputTextLine();
                                textLine =  question;
				if ((textLine == null) || (textLine.length() < 1))
					textLine = MagicStrings.null_input;
				if (textLine.equals("q")) {
					System.exit(0);
				} else if (textLine.equals("wq")) {
					bot.writeQuit();
					System.exit(0);
				} else {
					//String request = textLine;
                                        String user_request = textLine;
					if (MagicBooleans.trace_mode)
						System.out.println("STATE=" + user_request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
					//String response = chatSession.multisentenceRespond(request);
                                        String bot_response = chatSession.multisentenceRespond(user_request);
					while (bot_response.contains("&lt;"))
						bot_response = bot_response.replace("&lt;", "<");
					while (bot_response.contains("&gt;"))
						bot_response = bot_response.replace("&gt;", ">");
					System.out.println("Robot : " + bot_response);
                                        
                                        DbConnection dbobject = new DbConnection();
                                        
                                        dbobject.insertChat(sessionId, question, bot_response);
                                        
                                        request.setAttribute("sessionId", sessionId);

                                        RequestDispatcher rd = getServletContext().getRequestDispatcher("/chatPage.jsp");
                                        rd.forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private  String getResourcesPath(HttpServletRequest request, HttpServletResponse response) {
		//File currDir = new File(".");
		//String path = currDir.getAbsolutePath();
		//path = path.substring(0, path.length() - 2);
		//System.out.println(path);
                
                String servletContext = getServletContext().getRealPath("/");
            
                String servletContext1 = servletContext.replace("\\build\\web\\", "");
		String resourcesPath = servletContext1 + File.separator + "src" + File.separator + "resources";
                
		return resourcesPath;
	}
    
    public void goToChatPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        HttpSession session = request.getSession(true);
        
        String sessionId = session.getId();
        
        request.setAttribute("sessionId", sessionId);
       
        
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/chatPage.jsp");
        rd.forward(request, response);
    }
    
    public void addAIML(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        try {

            String resourcesPath = getResourcesPath(request, response);
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);

            bot.writeAIMLFiles();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            
            String action = request.getParameter("action");
            
            switch (action) {
                
                case "sendMessage":
                    sendMessage(request, response);
                    break; 
                 case "goToChatPage":
                    goToChatPage(request, response);
                    break; 
                 case "addAIML":
                    addAIML(request, response);
                    break; 
                
            }
            
        }catch (Exception error) {

            error.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
