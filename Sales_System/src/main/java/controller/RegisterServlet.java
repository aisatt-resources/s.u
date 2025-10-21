package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.MemberDao;
import exception.SalesSystemException;
import model.SalesInformation;

/**
 * 検索画面のサーブレットクラス
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 画面に入力された注文情報を取得
		request.setCharacterEncoding("UTF-8");
		String item_name = request.getParameter("item_name");
		String product_category = request.getParameter("product_category");
		String price = request.getParameter("price");
		String description = request.getParameter("description");

		String nextPage = null;

		try {

			MemberDao memberDao = new MemberDao();
			SalesInformation iteminfo = new SalesInformation(item_name, product_category, price,
					description);

			// 取得した入力内容をDBへ登録
			memberDao.InsertItemInfo(iteminfo);

			// 取得した商品名をリクエストスコープにセット
			request.setAttribute("message", "商品を登録しました。");

			// 登録結果表示画面へ遷移する準備
			nextPage = "Register.jsp";

		} catch (SalesSystemException e) {
			
			// エラーの場合、メッセージをRegister.jspに表示
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			// 検索結果表示画面へ遷移する準備
			nextPage = "Register.jsp";

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

}