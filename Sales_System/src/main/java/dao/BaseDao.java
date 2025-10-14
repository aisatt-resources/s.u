package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.SalesSystemException;

/**
 * DAOクラスの共通処理をまとめたスーパークラス
 */
public abstract class BaseDao {

	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	/**
	 * コンストラクタ<br>
	 * 初期処理としてDBに接続
	 * @throws MembershipException DB接続に失敗した際に発生
	 */
	public BaseDao() throws SalesSystemException {
		// DBに接続
		getConnection();
	}

	/**
	 * DB接続処理
	 * @throws MembershipException DB接続に失敗した際に発生
	 */
	private void getConnection() throws SalesSystemException {
		try {
			if (con == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				//接続DB名、ユーザ名、パスワードは各環境に合わせて設定
				String url  = "jdbc:mysql://localhost/Sales_System";
				String user = "root";
				String password = "07kICVo0!";
				// DB接続
				con = DriverManager.getConnection(url, user, password);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SalesSystemException("JDBCドライバが見つかりません");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("SQL実行中に例外が発生しました");
		}
	}

	/**
	 * DBとの接続を解除
	 * @throws MembershipException
	 */
	protected void close() throws SalesSystemException {
		try {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new SalesSystemException("close処理中に例外が発生しました");
		}
	}
}