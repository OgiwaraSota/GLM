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
 * Servlet implementation class clearServlet
 */
@WebServlet("/clear")
public class clearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッションを取得
        HttpSession session = request.getSession();
        
        // Operationオブジェクトを生成
        Operation op = new Operation();
        
        // 学習履歴を削除
        op.deleteHistory(session);
        
        // 削除後に結果を表示するJSPにフォワード
        String url = "state.jsp"; // 履歴削除後に学習状況画面にリダイレクト
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
	}
}
