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
 * Servlet implementation class generateServlet
 */
@WebServlet("/generate")
public class generateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();	
		
		Operation op = (Operation) session.getAttribute("operation");
	    if (op == null) {
	        op = new Operation();
	        session.setAttribute("operation", op);
	    }

	    op.generate(session);
		
		// エラーメッセージがセットされていなければクリア
	    if (session.getAttribute("text") != null) {
	        session.removeAttribute("errorMsg");
	    }
		
		String url = "generate.jsp";
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
