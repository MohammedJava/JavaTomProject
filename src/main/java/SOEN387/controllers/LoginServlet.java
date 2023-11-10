package SOEN387.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private ObjectMapper objectMapper;
    private HashMap<String, String> passcodes;

    public LoginServlet() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        passcodes = new HashMap<>();
        loadPasscodesFromFile();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the login.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		              throws ServletException, IOException {
    	
        String name = request.getParameter("username");
        String passcode = request.getParameter("password");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800); // Set the session timeout to 30 minutes (1800 seconds)

        if (passcode == null) {
            response.sendRedirect("/error");
            return;
        }
        
        if ("secret".equals(passcode)) {
            session.setAttribute("role", "staff");
            response.sendRedirect("/JavaTomProject_war_exploded/products");
        } 

        // Check if the passcode (password) exists in the passcodes HashMap
        if (passcodes.containsKey(name) && passcodes.get(name).equals(passcode)) {
            session.setAttribute("name", name);
            response.sendRedirect("/JavaTomProject_war_exploded/products");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }

    private void loadPasscodesFromFile() {
        try {
            File file = new File("C://Users//Mohammed//IdeaProjects//JavaTomProject//src//main//java//SOEN387//user_management.json"); // Specify the path to your JSON file
            if (file.exists()) {
                passcodes = objectMapper.readValue(file, HashMap.class);
            } else {
                passcodes = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





