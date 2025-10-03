package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.MembershipException;

/**
 * DAOクラスの共通処理をまとめたスーパークラス
 */
public abstract class BaseDao {

	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	/**
	 * 初期処理としてDBに接続
	 * @throws MembershipException 
	 */
	public BaseDao() throws MembershipException {
		// DBに接続
		getConnection();
	}

	/**
	 * DB接続処理
	 * @throws MembershipException
	 */
	private void getConnection() throws MembershipException {
		try {
			if (con == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				//接続DB名、ユーザ名、パスワード
				String url  = "jdbc:mysql://localhost/membership";
				String user = "root";
				String password = "";
				// DB接続
				con = DriverManager.getConnection(url, user, password);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new MembershipException("JDBCドライバが見つかりません");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MembershipException("SQL実行中に例外が発生しました");
		}
	}

	/**
	 * DBとの接続を解除
	 * @throws MembershipException
	 */
	protected void close() throws MembershipException {
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
			throw new MembershipException("close処理中に例外が発生しました");
		}
	}
}