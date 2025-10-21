package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import exception.SalesSystemException;

/**
 * 会員情報削除のサーブレットクラス
 */
@WebServlet("/DeleteMemberServlet")
public class DeleteMemberServlet extends HttpServlet {

	/**
	 * "削除"ボタン押下で処理(param user_id)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nextPage = null;

		try {

			String user_id = request.getParameter("user_id");
			
			// 会員情報の取得
			CustomerDao memberDao = new CustomerDao();
			memberDao.deleteMember(user_id);
			
			// 一覧画面を表示する準備
			nextPage = "MemberList.jsp";

		} catch (SalesSystemException e) {

			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);

	}
}