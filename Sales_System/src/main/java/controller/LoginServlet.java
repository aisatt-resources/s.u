package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CustomerDao;
import exception.SalesSystemException;
import model.UserInformation;

/**
 * 検索画面のサーブレットクラス
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 画面に入力された注文情報を取得
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");

		String nextPage = null;

		try {

			CustomerDao customerDao = new CustomerDao();
			UserInformation userinfo = new UserInformation(user_id, password);

			// 取得した入力内容をもとにDBを検索し、検索結果メッセージを取得
			String result = customerDao.findUser_Name(userinfo);
			
			String message = "";

			if (result != "検索結果：0件") {

				HttpSession session = request.getSession();
				
				//ユーザー名をリクエストスコープにセット
				session.setAttribute("user_name", result);
				
				//ユーザーIDをリクエストスコープにセット
				session.setAttribute("user_id", user_id);
				
				//ログイン状態をリクエストスコープにセット
				session.setAttribute("LoginStatus", "Login");
				
				message = "こんにちは、" + result + "さん！";
				
				// トップ画面へ遷移する準備
				nextPage = "Top.jsp";

			} else {

				message = "ログインに失敗しました。";
				
				// ログイン画面へ遷移する準備
				nextPage = "Login.jsp";

			}

			// メッセージをリクエストスコープにセット
			request.setAttribute("message", message);

		} catch (SalesSystemException e) {

			// エラーの場合、メッセージをRegister.jspに表示
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			// 検索結果表示画面へ遷移する準備
			nextPage = "Login.jsp";

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
	
	/**
	 * ログアウト時に使用
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		// セッションの情報を破棄
		HttpSession session = request.getSession();
		session.invalidate();
		
		//ログイン状態をリクエストスコープにセット
		request.getSession().setAttribute("LoginStatus", "Logout");
		
		// login.jspに表示するメッセージをセット
		request.getSession().setAttribute("LogoutMessage", "ログアウトしました");
		response.sendRedirect("Login.jsp");
		// login.jspを表示
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
		//requestDispatcher.forward(request, response);
	}

}