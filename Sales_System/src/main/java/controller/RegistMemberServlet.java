package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import exception.SalesSystemException;
import model.UserInformation;

/**
 * 会員情報登録画面のサーブレットクラス
 */
@WebServlet("/RegistMemberServlet")
public class RegistMemberServlet extends HttpServlet {
	/**
	 * 会員情報の登録処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 画面に入力された会員情報を取得
		request.setCharacterEncoding("UTF-8");
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String address = request.getParameter("address");
		String birthday = request.getParameter("birthday");
		String job = request.getParameter("job");
		String password = request.getParameter("password");

		String message = null; // 処理後に画面に表示するメッセージ		

		try {

			CustomerDao customerDao = new CustomerDao();

			// DAOクラスに渡すために会員情報クラスに値を格納
			UserInformation info = new UserInformation(user_name, email, tel,
					address, birthday, job, password);

			// 新規登録
			customerDao.insertMember(info);
			message = "会員情報を登録しました";

		} catch (SalesSystemException e) {
			message = e.getMessage();
			request.setAttribute("error", "true");
			e.printStackTrace();
		}
		// 次の画面に遷移
		request.setAttribute("message", message);
		request.getRequestDispatcher("RegistMember.jsp").forward(request, response);
	}
}