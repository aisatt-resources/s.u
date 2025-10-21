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
 * 会員情報編集画面のサーブレットクラス
 */
@WebServlet("/EditMemberServlet")
public class EditMemberServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 画面に入力された会員情報を取得
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
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
			UserInformation memberInfo = new UserInformation(user_id, user_name, email, tel,
					address, birthday, job, password);

			// 会員情報編集
			customerDao.editMember(memberInfo);
			message = "会員情報を更新しました";

		} catch (SalesSystemException e) {
			message = e.getMessage();
			request.setAttribute("error", "true");
			e.printStackTrace();
		}

		request.setAttribute("message", message);
		// 変更箇所のユーザーIDをリクエストスコープにセット
		request.setAttribute("user_id", user_id);
		// 一覧画面に遷移
		request.getRequestDispatcher("MemberList.jsp").forward(request, response);

	}

	/**
	 * "編集"ボタン押下で処理(param user_id)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nextPage = null;

		try {

			String user_name = null;
			String user_id = request.getParameter("user_id");

			// 会員情報の取得
			CustomerDao customerDao = new CustomerDao();
			List<UserInformation> memberList = customerDao.findMember(user_id, user_name);

			// 取得した編集情報をリクエストスコープにセット
			request.setAttribute("memberList", memberList);

			// 編集画面を表示する準備
			nextPage = "EditMember.jsp";

		} catch (SalesSystemException e) {

			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			
			// 編集画面を表示する準備
			nextPage = "EditMember.jsp";

		}

		// 次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);

	}
}