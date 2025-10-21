package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import exception.SalesSystemException;
import model.UserInformation;

/**
 * 検索画面のサーブレットクラス
 */
@WebServlet("/SearchMemberServlet")
public class SearchMemberServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user_id = request.getParameter("user_id");
		String user_name = request.getParameter("user_name");
		String nextPage = null;
		try {
			// 入力されたユーザーIDとユーザー名の会員がいるかチェック
			CustomerDao customerDao = new CustomerDao();
			List<UserInformation> memberList = customerDao.findMember(user_id, user_name);

			// 取得した会員情報をリクエストスコープにセット
			request.setAttribute("memberList", memberList);

			// 検索画面へ遷移する準備
			nextPage = "SearchMember.jsp";

		} catch (SalesSystemException e) {
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			// 検索画面へ遷移する準備
			nextPage = "SearchMember.jsp";
		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

}