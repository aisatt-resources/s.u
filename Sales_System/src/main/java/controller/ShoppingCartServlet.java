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
import model.OrderInformation;

/**
 * カート画面のサーブレットクラス
 */
@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String[] quantity_list = new String[4];
		String[] item_cd_list = new String[4];

		// 個数と商品コードを取得
		for (int i = 0; i < 4; i++) {
			quantity_list[i] = request.getParameter("quantity" + (i + 1));
			item_cd_list[i] = request.getParameter("item_cd" + (i + 1));
			System.out.println(item_cd_list[i]);
			System.out.println(quantity_list[i]);
		}
		
		//決済方法、備考、会員IDを取得
		String payment_method = request.getParameter("payment_method");
		String remarks = request.getParameter("remarks");
		String member_id = request.getParameter("member_id");

		String nextPage = null;

		try {

			CustomerDao customerDao = new CustomerDao();

			OrderInformation orderinfo = new OrderInformation(item_cd_list, quantity_list, payment_method, remarks, member_id);

			//売上情報をDBへ登録
			customerDao.InsertOrderInfo(orderinfo);

			String message = "購入が完了しました。";
			request.setAttribute("message", message);

			// 検索結果表示画面へ遷移する準備
			nextPage = "Payment.jsp";

		} catch (SalesSystemException e) {
			// エラーの場合、メッセージをSearch.jspに表示
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			// 検索結果表示画面へ遷移する準備
			nextPage = "Payment.jsp";

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

}