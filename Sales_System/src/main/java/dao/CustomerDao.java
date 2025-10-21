package dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.SalesSystemException;
import model.OrderInformation;
import model.UserInformation;

/**
 * 注文情報関連のDAOクラス
 * 注文画面の操作時に使用
 */
public class CustomerDao extends BaseDao {
	/**
	 * スーパークラスのコンストラクタ（DB接続）を実施
	 * @throws MembershipException DB接続失敗時に発生
	 */
	public CustomerDao() throws SalesSystemException {
		super();
	}

	//DBから商品名・税抜販売価格を取得するメソッド
	public HashMap<String, String> findItem_Info(String item_cd) throws SalesSystemException {

		// 商品名を格納する変数
		HashMap<String, String> hashmap = new HashMap<String, String>();

		//商品名を格納する変数
		String item_name = null;

		//税抜販売価格を格納する変数
		String price = null;

		//画像URLを格納する変数
		String url = null;

		try {
			// SQL文
			//商品コードをもとに商品名と税抜販売価格を検索
			String sql = "SELECT item_name, price FROM m_item WHERE item_cd=?";

			// SQL実行
			ps = con.prepareStatement(sql);
			ps.setString(1, item_cd);
			rs = ps.executeQuery();

			// 商品名を取得
			if (rs.next()) {
				item_name = rs.getString("item_name");
				price = rs.getString("price");
			}

			// 商品の画像URLを格納
			url = "item_" + item_cd;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("該当の商品はありませんでした。");
		}

		//HashMapにキーと値をセットで格納
		hashmap.put("item_name", item_name);
		hashmap.put("price", price);
		hashmap.put("url", url);

		// 商品情報を格納したHashMapを返却
		return hashmap;

	}

	//売上情報をDBへ登録するメソッド
	public void InsertOrderInfo(OrderInformation orderinfo) throws SalesSystemException {

		//引数から売上情報を取得して配列に格納
		//商品コード
		String[] item_cd_list = orderinfo.getItem_Cd_list();

		//数量
		String[] quantity_list = orderinfo.getQuantity_list();

		//会員ID
		String member_id = orderinfo.getMember_Id();

		//支払方法
		String payment_method = orderinfo.getPayment_Method();

		//備考
		String remarks = orderinfo.getRemarks();

		//LocalDateTimeクラスを用いて現在時刻を取得
		//注文日
		String order_date = LocalDateTime.now().toString();

		//登録日付
		String regist_datetime = LocalDateTime.now().toString();

		//納品日（注文日から３日後に設定）
		String delivery_date = LocalDateTime.now().plusDays(3).toString();

		//注文番号を格納する変数
		int order_no = 0;

		//DBから取得した値を格納。
		String result = "";

		//SQL文
		String sql = "";

		try {
			// 一意制約を満たす注文番号を採番するまでループ処理を実行
			//order_noカラムにAUTO_INCREMENTが設定されていなかったことによる追加機能
			while (result != "採番完了") {

				result = "採番完了";

				//注文番号を"１"から採番
				order_no++;
				
				//SQL文
				//採番された注文番号をもつレコードが存在するか検索
				sql = "SELECT order_no FROM `order` WHERE order_no=?";

				// SQL実行
				ps = con.prepareStatement(sql);
				ps.setInt(1, order_no);
				rs = ps.executeQuery();
				
				// 採番された注文番号をもつレコードが存在するか確認
				// 該当のレコードが存在しない場合はrs.next()はfalseを返却
				while (rs.next()) {
					
					// 検索結果から注文番号を取得
					result = rs.getString("order_no");
				}

				//【エラーチェック用】既に採番されている番号を確認可能
				System.out.println("既採番：" + result);

			}

			//【エラーチェック用】採番された注文番号を確認可能
			System.out.println(order_no);
			
			//【エラーチェック用】引数で受け取った商品コードリストを確認可能
			for (int i = 0; i < 4; i++) {
				System.out.println(item_cd_list[i]);
			}

			//税抜金額を格納する配列
			int[] amount = new int[4];

			//合計金額を格納する配列
			int total_amount = 0;

			//税抜販売価格を格納する配列
			int[] price = new int[4];

			//税抜単価を格納する配列
			int[] unit_price = new int[4];

			//注文行番号を格納する変数
			int row_no = 1;
			
			//商品の種類の数だけループ
			for (int i = 0; i < 4; i++) {

				//購入個数が0個のものはfor文内の以降の処理をスキップ
				if (quantity_list[i] == null) {
					continue;
				}
				
				//SQL文
				//税抜販売価格を取得
				sql = "SELECT price FROM m_item WHERE item_cd=?";
				
				ps = con.prepareStatement(sql);
				ps.setString(1, item_cd_list[i]);
				ps.executeQuery();
				
				rs = ps.executeQuery();

				while (rs.next()) {
					
					// 検索結果から商品情報の税抜販売価格を取得して格納
					price[i] = rs.getInt("price");
					
					//【エラーチェック】税抜販売価格を確認可能
					System.out.println(price[i]);
				}

				//税抜単価
				unit_price[i] = price[i];

				//税抜金額に税抜販売価格と個数の積を格納
				amount[i] = price[i] * Integer.parseInt(quantity_list[i]);

				//合計金額
				total_amount += amount[i];
				
				//SQL文
				//order_detailテーブルに売上詳細情報を登録
				sql = "INSERT INTO order_detail "
						+ "(order_no, row_no, item_cd, quantity, unit_price, amount) "
						+ "VALUES (?,?,?,?,?,?)";

				ps = con.prepareStatement(sql);
				ps.setInt(1, order_no);
				ps.setInt(2, row_no);
				ps.setString(3, item_cd_list[i]);
				ps.setString(4, quantity_list[i]);
				ps.setInt(5, unit_price[i]);
				ps.setInt(6, amount[i]);
				ps.executeUpdate();
				
				//注文行番号を１繰り上げ
				row_no++;

			}
			
			//SQL文
			//orderテーブルに売上情報を登録
			sql = "INSERT INTO `order` (order_no, member_id, order_date, total_amount, "
					+ "payment_method, delivery_date, remarks, "
					+ "regist_datetime) VALUES (?,?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, order_no);
			ps.setString(2, member_id);
			ps.setString(3, order_date);
			ps.setInt(4, total_amount);
			ps.setString(5, payment_method);
			ps.setString(6, delivery_date);
			ps.setString(7, remarks);
			ps.setString(8, regist_datetime);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("注文登録に失敗しました。");
		}

	}

	//ユーザーIDとパスワードをもとにユーザー情報テーブルを検索するメソッド
	//該当するレコードがない場合はメッセージを返却
	public String findUser_Name(UserInformation userinfo)
			throws SalesSystemException {

		String user_id = userinfo.getUser_Id();
		String password = userinfo.getPassword();

		String result = "検索結果：0件";

		try {
			
			//SQL文
			//会員情報テーブルでユーザーIDとパスワードが一致するレコードを検索
			String sql = "SELECT * FROM `member` WHERE user_id=? AND `password`=?";

			ps = con.prepareStatement(sql);

			ps.setString(1, user_id);
			ps.setString(2, password);

			rs = ps.executeQuery();

			while (rs.next()) {
				//DBから取得したユーザー名を格納
				result = rs.getString("user_name");

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("会員情報の検索に失敗しました");
		}
		
		//検索結果を返却
		return result;
	}

	/**
	 * 入力された会員情報をDBへ新規登録するメソッド
	 * @param info 入力情報
	 * @throws SalesSystemException
	 */
	public void insertMember(UserInformation info) throws SalesSystemException {
		// DBに登録する会員情報を取得
		String user_name = info.getUser_Name(); // ユーザー名
		String email = info.getEmail(); // メールアドレス
		String tel = info.getTel(); // 電話番号
		String address = info.getAddress(); //住所
		String birthday = info.getBirthday(); //生年月日
		String job = info.getJob(); //職業
		String password = info.getPassword(); //パスワード
		String created_datetime = LocalDateTime.now().toString(); //登録日付
		String update_datetime = LocalDateTime.now().toString(); //更新日付

		try {
			// SQL文
			// 会員情報テーブルにレコードを登録
			String sql = "INSERT INTO member(user_name, email,"
					+ "tel, address, birthday,"
					+ "job, password, created_datetime, update_datetime)"
					+ "VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?)";

			ps = con.prepareStatement(sql);
			
			ps.setString(1, user_name);
			ps.setString(2, email);
			ps.setString(3, tel);
			ps.setString(4, address);
			ps.setString(5, birthday);
			ps.setString(6, job);
			ps.setString(7, password);
			ps.setString(8, created_datetime);
			ps.setString(9, update_datetime);
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("会員登録に失敗しました");
		}
	}

	/**
	 * 会員情報テーブルに登録されている会員情報を全検索
	 * 検索結果が0件の場合は空のリストを返却
	 * @return 会員情報リスト
	 * @throws MembershipException
	 **/
	public List<UserInformation> findAllMember() throws SalesSystemException {
		
		// 会員情報リスト
		ArrayList<UserInformation> memberList = new ArrayList<>();
		
		try {
			// SQL文
			// 全会員情報を検索
			String sql = "SELECT * FROM member";

			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			//会員数だけループ
			while (rs.next()) {
				
				String user_id = rs.getString("user_id");
				String user_name = rs.getString("user_name");
				String email = rs.getString("email");
				String tel = rs.getString("tel");
				String address = rs.getString("address");
				String birthday = rs.getString("birthday");
				String job = rs.getString("job");
				String password = rs.getString("password");
				String created_datetime = rs.getString("created_datetime");
				String update_datetime = rs.getString("update_datetime");

				UserInformation member = new UserInformation(user_id, user_name, email,
						tel, address, birthday, job,
						password, created_datetime, update_datetime);

				// 検索結果から会員情報の各項目を取得してリストに追加
				memberList.add(member);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("会員情報の取得に失敗しました");
		}

		// 会員情報リストを返却
		return memberList;
	}

	public List<UserInformation> findMember(String user_id, String user_name) throws SalesSystemException {
		// 会員情報リスト
		ArrayList<UserInformation> memberList = new ArrayList<>();

		try {
			// SQL文
			// ユーザーIDとユーザー名をもとに検索
			String sql = "SELECT * FROM member WHERE user_id=? OR user_name=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.setString(2, user_name);
			rs = ps.executeQuery();

			while (rs.next()) {

				user_id = rs.getString("user_id");
				user_name = rs.getString("user_name");
				String email = rs.getString("email");
				String tel = rs.getString("tel");
				String address = rs.getString("address");
				String birthday = rs.getString("birthday");
				String job = rs.getString("job");
				String password = rs.getString("password");
				String created_datetime = rs.getString("created_datetime");
				String update_datetime = rs.getString("update_datetime");

				UserInformation member = new UserInformation(user_id, user_name, email,
						tel, address, birthday, job,
						password, created_datetime, update_datetime);
				
				// 検索結果から会員情報の各項目を取得してリストに追加
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("会員情報の取得に失敗しました");
		}

		return memberList;

	}

	public void editMember(UserInformation memberinfo) throws SalesSystemException {

		String user_id = memberinfo.getUser_Id(); //ユーザーID
		String user_name = memberinfo.getUser_Name(); // ユーザー名
		String email = memberinfo.getEmail(); // メールアドレス
		String tel = memberinfo.getTel(); // 電話番号
		String address = memberinfo.getAddress(); //住所
		String birthday = memberinfo.getBirthday(); //生年月日
		String job = memberinfo.getJob(); //職業
		String password = memberinfo.getPassword(); //パスワード

		try {

			// SQL文
			//ユーザーIDで対象を絞り、該当するレコードの情報を更新
			String sql = "UPDATE member "
					+ "SET user_name=?, email=?, tel=?, "
					+ "address=?, birthday=?, job=?, "
					+ "password=? WHERE user_id=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, user_name);
			ps.setString(2, email);
			ps.setString(3, tel);
			ps.setString(4, address);
			ps.setString(5, birthday);
			ps.setString(6, job);
			ps.setString(7, password);
			ps.setString(8, user_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("会員情報の更新に失敗しました");
		}
	}

	public void deleteMember(String user_id) throws SalesSystemException {

		try {
			// SQL文
			// ユーザーIDで対象を絞り、該当するレコードを削除
			String sql = "DELETE FROM member "
					+ "WHERE user_id=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("会員情報の削除に失敗しました");
		}

	}

}