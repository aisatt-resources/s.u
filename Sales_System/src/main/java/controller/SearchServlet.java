package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.MemberDao;
import exception.SalesSystemException;
import model.SalesInformation;

/**
 * 検索画面のサーブレットクラス
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 画面に入力された注文情報を取得
		request.setCharacterEncoding("UTF-8");
		String order_date_start = request.getParameter("order_date_start");
		String order_date_end = request.getParameter("order_date_end");
		String member_id = request.getParameter("member_id");
		String payment_method = request.getParameter("payment_method");
		String delivery_date_flag = request.getParameter("delivery_date_flag");
		String item_cd = request.getParameter("item_cd");

		String nextPage = null;

		try {

			MemberDao memberDao = new MemberDao();
			SalesInformation orderinfo = new SalesInformation(order_date_start, order_date_end, member_id,
					payment_method, delivery_date_flag, item_cd);

			// 入力された商品コードから商品名を検索して取得
			String item_name = memberDao.findItem_Name(orderinfo);

			// 取得した検索結果（注文番号）をリストに格納
			List<String> OrderNoList = memberDao.findOrderNo(orderinfo);

			//購入情報ヘッダーリストを取得
			List<SalesInformation> OrderList = memberDao.findOrder(OrderNoList);

			//購入詳細リストを取得
			HashMap<String, List<SalesInformation>> OrderDetailHashMap = memberDao.findOrder_Detail(OrderNoList);

			// 取得した商品名をリクエストスコープにセット
			request.setAttribute("item_name", item_name);

			// 取得した購入情報ヘッダーリストをリクエストスコープにセット
			request.setAttribute("OrderList", OrderList);

			// 取得した購入詳細リストをリクエストスコープにセット
			request.setAttribute("OrderDetailHashMap", OrderDetailHashMap);

			// 検索結果表示画面へ遷移する準備
			nextPage = "Search.jsp";

		} catch (SalesSystemException e) {
			// エラーの場合、メッセージをSearch.jspに表示
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			// 検索結果表示画面へ遷移する準備
			nextPage = "Search.jsp";

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ログインIDをセッションから取得する
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String order_date_start = "";
		String order_date_end = "";
		String member_id = (String) session.getAttribute("user_id");
		String payment_method = "";
		String delivery_date_flag = "";
		String item_cd = "";

		String nextPage = null;

		//ブラウザバックしてから押下されたときの対策
		if (member_id == null) {
			
			nextPage = "Login.jsp";
			
			request.setAttribute("message", "ログインしてください。");
			
		} else {

			try {
				MemberDao memberDao = new MemberDao();
				SalesInformation orderinfo = new SalesInformation(order_date_start, order_date_end, member_id,
						payment_method, delivery_date_flag, item_cd);

				// 取得した検索結果（注文番号）をリストに格納
				List<String> OrderNoList = memberDao.findOrderNo(orderinfo);

				//購入情報ヘッダーリストを取得
				List<SalesInformation> OrderList = memberDao.findOrder(OrderNoList);

				//購入詳細リストを取得
				HashMap<String, List<SalesInformation>> OrderDetailHashMap = memberDao.findOrder_Detail(OrderNoList);

				// 取得した購入情報ヘッダーリストをリクエストスコープにセット
				request.setAttribute("OrderList", OrderList);

				// 取得した購入詳細リストをリクエストスコープにセット
				request.setAttribute("OrderDetailHashMap", OrderDetailHashMap);

				nextPage = "History.jsp";

			} catch (SalesSystemException e) {
				// エラーの場合、メッセージをSearch.jspに表示
				String message = e.getMessage();
				request.setAttribute("message", message);
				request.setAttribute("error", "true");

				// Top画面へ遷移する準備
				nextPage = "Top.jsp";

			}

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

}