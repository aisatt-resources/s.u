package controller;

import java.io.IOException;
import java.util.HashMap;

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
 * 検索画面のサーブレットクラス
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	
	// Register.jspから商品コードと購入数量を受け取り、カート情報へ追加する
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 商品コードと購入数量を取得
		request.setCharacterEncoding("UTF-8");
		//String item_cd = request.getParameter("item_cd");
		//String quantity = request.getParameter("quantity");
		
		String message= "カートに追加しました。";
		request.setAttribute("message", message);
		
		String nextPage = "Order.jsp";

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
	
	// Top.jspからRegister.jspへの画面遷移を行う
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Top.jspから商品コードを受け取る
		String item_cd = request.getParameter("item_cd");
		
		String nextPage = null;

		try {
			
			CustomerDao customerDao = new CustomerDao();
			OrderInformation orderinfo = new OrderInformation(item_cd);
			item_cd = orderinfo.getItem_Cd();
			
			//商品コードから商品名(item_name)と単価(price)をHashMapで取得
			HashMap<String, String> hashmap = customerDao.findItem_Info(item_cd);
			
			// 取得した商品名と単価をリクエストスコープにセット
			request.setAttribute("item_name", hashmap.get("item_name"));
			request.setAttribute("price", hashmap.get("price"));
			request.setAttribute("url", hashmap.get("url"));
			request.setAttribute("item_cd", item_cd);

			// 検索結果表示画面へ遷移する準備
			nextPage = "Order.jsp";
			
		} catch (SalesSystemException e) {
			// エラーの場合、メッセージをSearch.jspに表示
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");

			// 検索結果表示画面へ遷移する準備
			nextPage = "Order.jsp";

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);

	}

}