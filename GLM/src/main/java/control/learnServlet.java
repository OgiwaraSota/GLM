package control;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Operation;
/**
 * Servlet implementation class learnServlet
 */
@WebServlet("/learn")
public class learnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");
		HttpSession session = request.getSession();	
		
		String url;
		
		if(text == null) {
			url = "learn.jsp";
		}else {
			Operation op = new Operation();
			op.learn(text, session);
			
			url = "learnDone.html";
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
