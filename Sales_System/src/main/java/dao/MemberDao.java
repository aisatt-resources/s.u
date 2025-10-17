package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import exception.SalesSystemException;
import model.SalesInformation;

/**
 * 売上情報関連のDAOクラス
 */
public class MemberDao extends BaseDao {
	/**
	 * スーパークラスのコンストラクタ（DB接続）を実施
	 * @throws MembershipException DB接続失敗時に発生
	 */
	public MemberDao() throws SalesSystemException {
		super();
	}

	public String findItem_Name(SalesInformation salesinfo) throws SalesSystemException {

		// 商品名を格納する変数
		String item_name = null;

		// 入力された商品コードを変数に格納
		String item_cd = salesinfo.getItem_Cd();

		try {
			// SQL文
			String sql = "SELECT item_name FROM m_item WHERE item_cd=?";

			// SQL実行
			ps = con.prepareStatement(sql);
			ps.setString(1, item_cd);
			rs = ps.executeQuery();

			// 商品名を取得
			if (rs.next()) {
				item_name = rs.getString("item_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("該当の商品はありませんでした。");
		}

		// 商品名を返却
		return item_name;

	}

	/**
	 * 4つのテーブル(order, order_detail, member, m_item)に登録されている売上情報を検索
	 * 検索結果が0件の場合は空のリストを返却
	 * @return 売上情報の入ったリスト
	 * @throws MembershipException 検索失敗の際に発生
	 **/
	public List<String> findOrderNo(SalesInformation salesinfo) throws SalesSystemException {
		// 売上情報を格納するリスト
		List<String> OrderNoList = new ArrayList<>();

		// 入力された項目値をカプセル化
		String order_date_start = salesinfo.getOrder_Date_Start();
		String order_date_end = salesinfo.getOrder_Date_End();
		String member_id = salesinfo.getMember_Id();
		String payment_method = salesinfo.getPayment_Method();
		String delivery_date_flag = salesinfo.getDelivery_Date_Flag();
		String item_cd = salesinfo.getItem_Cd();

		// 【エラーチェック】入力値をコンソールで確認
		System.out.println("開始日：" + order_date_start);
		System.out.println("終了日：" + order_date_end);
		System.out.println("会員ID：" + member_id);
		System.out.println("支払方法：" + payment_method);
		System.out.println("納品済or未納：" + delivery_date_flag);
		System.out.println("商品コード：" + item_cd);

		try {

			// SQL文の文字列　
			//フォームから受け取った値に合わせて条件を追加していく。
			String str = "SELECT `order`.order_no FROM `order` "
					+ "INNER JOIN order_detail ON `order`.order_no = order_detail.order_no "
					+ "INNER JOIN `member` ON `order`.member_id = `member`.user_id "
					+ "INNER JOIN m_item ON order_detail.item_cd = m_item.item_cd "
					+ "WHERE ";

			// 注文日付
			//フォームに開始日と終了日が双方入力されている場合
			if (!order_date_start.equals("") && !order_date_end.equals("")) {

				str += "(order_date BETWEEN ? AND ?) AND ";

				//フォームに開始日のみ入力されている場合
			} else if (!order_date_start.equals("") && order_date_end.equals("")) {

				str += "(order_date BETWEEN ? AND ?) AND ";
				order_date_end = order_date_start;

				//フォームに終了日のみ入力されている場合
			} else if (order_date_start.equals("") && !order_date_end.equals("")) {

				str += "(order_date BETWEEN ? AND ?) AND ";
				order_date_start = order_date_end;

				//それ以外の場合
			} else {

				//注文日付は全検索
				str += "(? = ?) AND ";
				order_date_start = "empty";
				order_date_end = "empty";

			}

			// 会員ID
			// 会員IDが入力された場合は、日付以外の検索条件を無視
			if (!member_id.equals("")) {

				str += "(member_id = ?) AND ";

				//支払方法、納品済or未納、商品コードの入力値は無視
				payment_method = "empty";
				item_cd = "empty";
				str += "(payment_method = ? OR 1=1) AND ";
				str += "(order_detail.item_cd = ? OR 1=1)";

			} else {

				// フォームに会員コードが入力されていないので会員コード全検索
				str += "(member_id = ? OR 1=1) AND ";

				// 決済方法
				// フォームで"代引き"が選択された場合
				if (payment_method != null && payment_method.equals("0")) {

					str += "(payment_method = ?) AND ";

					// フォームで"クレジット"が選択された場合
				} else if (payment_method != null && payment_method.equals("1")) {

					str += "(payment_method = ?) AND ";

					// フォームで現金が選択された場合
				} else if (payment_method != null && payment_method.equals("2")) {

					str += "(payment_method = ?) AND ";

					//それ以外の場合
				} else {

					//全検索
					payment_method = "empty";
					str += "(payment_method = ? OR 1=1) AND ";

				}

				// 登録or未登録判定
				// "未納品"が選択された場合
				if (delivery_date_flag.equals("未納品")) {

					// 条件：納品日カラムがNULLとなっている
					str += "(delivery_date IS NULL) AND ";

					// "納品済"が選択された場合
				} else if (delivery_date_flag.equals("納品済")) {

					// 条件：納品日カラムに日付が記載されている
					str += "(delivery_date IS NOT NULL) AND ";

					// それ以外の場合
				} else {

				}

				// 商品コード
				// 商品コードが入力されている場合
				if (!item_cd.equals("")) {

					str += "(order_detail.item_cd = ?)";

					// それ以外の場合
				} else {

					//全検索
					item_cd = "empty";
					str += "(order_detail.item_cd = ? OR 1=1)";

				}
			}

			//SQL文に文字列を代入
			String sql = str;

			//SQL文のコンパイル
			ps = con.prepareStatement(sql);

			ps.setString(1, order_date_start);
			ps.setString(2, order_date_end);
			ps.setString(3, member_id);
			ps.setString(4, payment_method);
			ps.setString(5, item_cd);

			// SQLの実行
			rs = ps.executeQuery();

			// SQL文の内容をコンソールで確認
			System.out.println(sql);

			// 検索結果から会員情報の各項目を取得してリストに格納
			while (rs.next()) {

				String order_no_value = rs.getString("order_no"); //注文番号 orderテーブル

				OrderNoList.add(order_no_value);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("注文情報の取得に失敗しました");
		}

		List<String> OrderNoListMerge = new ArrayList<String>(new LinkedHashSet<>(OrderNoList));

		// 売上情報格納リストを返却
		return OrderNoListMerge;

	}

	/**
	 * 4つのテーブル(order, order_detail, member, m_item)に登録されている売上情報を検索
	 * 検索結果が0件の場合は空のリストを返却
	 * @return 売上情報の入ったリスト
	 * @throws MembershipException 検索失敗の際に発生
	 **/
	public List<SalesInformation> findOrder(List<String> OrderNoList) throws SalesSystemException {
		// 売上情報を格納するリスト
		ArrayList<SalesInformation> OrderList = new ArrayList<>();

		try {

			for (String OrderNo : OrderNoList) {

				String sql = "SELECT * FROM `order` "
						+ "INNER JOIN `member` ON `order`.member_id = `member`.user_id "
						+ "WHERE `order`.order_no = ?";

				//SQL文のコンパイル
				ps = con.prepareStatement(sql);

				ps.setString(1, OrderNo);

				// SQLの実行
				rs = ps.executeQuery();

				// 検索結果から会員情報の各項目を取得してリストに格納
				while (rs.next()) {

					String order_no_value = rs.getString("order_no"); //注文番号 orderテーブル
					Timestamp order_date_value = rs.getTimestamp("order_date"); //注文日 orderテーブル
					String member_id_value = rs.getString("member_id"); //会員ID orderテーブル	
					String user_name_value = rs.getString("user_name"); //会員名 memberテーブル
					String payment_method_value = rs.getString("payment_method"); //支払方法 orderテーブル
					String total_amount_value = rs.getString("total_amount"); //税込合計金額 orderテーブル
					String delivery_date_value = rs.getString("delivery_date"); //納品済or未納 orderテーブル
					String remarks_value = rs.getString("remarks"); //備考 orderテーブル

					SalesInformation orderinfo = new SalesInformation(order_no_value, order_date_value, member_id_value,
							user_name_value, payment_method_value, total_amount_value, delivery_date_value,
							remarks_value);

					OrderList.add(orderinfo);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("注文情報の取得に失敗しました");
		}

		ArrayList<SalesInformation> OrderListMerge = new ArrayList<>(new LinkedHashSet<>(OrderList));

		// 売上情報格納リストを返却
		return OrderListMerge;

	}

	public HashMap<String, List<SalesInformation>> findOrder_Detail(List<String> OrderNoList)
			throws SalesSystemException {

		HashMap<String, List<SalesInformation>> hashmap = new HashMap<>();

		try {
			for (String Order : OrderNoList) {

				//初期化
				// 売上情報を格納するリスト
				ArrayList<SalesInformation> OrderDetailList = new ArrayList<>();

				//SQL文に文字列を代入
				String sql = "SELECT * FROM order_detail WHERE order_no = ?";

				//SQL文のコンパイル
				ps = con.prepareStatement(sql);

				ps.setString(1, Order);

				// SQLの実行
				rs = ps.executeQuery();

				// 検索結果から会員情報の各項目を取得してリストに格納
				while (rs.next()) {

					//注文詳細
					int row_no_value = rs.getInt("row_no"); //注文行
					String item_cd_value = rs.getString("item_cd"); //商品コード
					String item_name_value = rs.getString("item_name"); //商品名
					String unit_price_value = rs.getString("unit_price"); //単価
					String quantity_value = rs.getString("quantity"); //数量
					String subtotal_value = rs.getString("subtotal"); //小計

					SalesInformation orderinfo = new SalesInformation(row_no_value, item_cd_value, item_name_value,
							unit_price_value, quantity_value,
							subtotal_value);

					OrderDetailList.add(orderinfo);

					//注文番号をキー、注文詳細リストを値にして格納
					hashmap.put(Order, OrderDetailList);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("注文情報の取得に失敗しました");
		}

		// 売上情報格納リストを返却
		return hashmap;

	}

}