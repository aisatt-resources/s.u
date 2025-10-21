package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import exception.SalesSystemException;
import model.SalesInformation;

/**
 * 売上情報関連のDAOクラス
 * 管理者画面の操作時に使用
 */
public class MemberDao extends BaseDao {
	/**
	 * スーパークラスのコンストラクタ（DB接続）を実施
	 * @throws MembershipException DB接続失敗時に発生
	 */
	public MemberDao() throws SalesSystemException {
		super();
	}

	//入力された商品コードから商品名を検索し、返却するメソッド
	public String findItem_Name(SalesInformation salesinfo) throws SalesSystemException {

		// 商品名を格納する変数
		String item_name = null;

		// 商品コードを変数に格納
		String item_cd = salesinfo.getItem_Cd();

		try {
			// SQL文
			// 商品コードで対象を絞り、商品名を検索
			String sql = "SELECT item_name FROM m_item WHERE item_cd=?";

			ps = con.prepareStatement(sql);
			ps.setString(1, item_cd);
			rs = ps.executeQuery();

			if (rs.next()) {
				
				// 商品名を取得
				item_name = rs.getString("item_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("商品情報の取得に失敗しました。");
		}

		// 商品名を返却
		return item_name;

	}

	/**
	 * 4つのテーブル(order, order_detail, member, m_item)を結合させ、
	 * 検索条件を満たすレコードを検索し、該当レコードの注文番号をリスト化して返却するメソッド
	 * ※検索結果が0件の場合は空のリストを返却
	 * @return 注文番号リスト
	 * @throws SalesSystemException 検索失敗の際に発生
	 **/
	public List<String> findOrderNo(SalesInformation salesinfo) throws SalesSystemException {
		// 注文番号を格納するリスト
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

			//条件文を格納するリスト
			List<String> conditionList = new ArrayList<String>();

			//入力値を格納するリスト
			List<String> valueList = new ArrayList<String>();

			// SQL文
			//フォームから受け取った値に合わせて条件を追加し、注文番号を検索
			String str = "SELECT `order`.order_no FROM `order` "
					+ "INNER JOIN order_detail ON `order`.order_no = order_detail.order_no "
					+ "INNER JOIN `member` ON `order`.member_id = `member`.user_id "
					+ "INNER JOIN m_item ON order_detail.item_cd = m_item.item_cd ";
			
			//入力情報をもとに条件文を作成
			// 注文日付
			//フォームに開始日と終了日が双方入力されている場合
			if (!order_date_start.equals("") && !order_date_end.equals("")) {

				conditionList.add("(order_date BETWEEN ? AND ?)");
				valueList.add(order_date_start);
				valueList.add(order_date_end);

				//フォームに開始日のみ入力されている場合
			} else if (!order_date_start.equals("") && order_date_end.equals("")) {

				conditionList.add("(order_date BETWEEN ? AND ?)");
				valueList.add(order_date_start);
				valueList.add(order_date_start);

				//フォームに終了日のみ入力されている場合
			} else if (order_date_start.equals("") && !order_date_end.equals("")) {

				conditionList.add("(order_date BETWEEN ? AND ?)");
				valueList.add(order_date_end);
				valueList.add(order_date_end);

				//それ以外の場合
			} else {

			}

			// 会員ID
			// 会員IDが入力された場合は、日付と会員ID以外の検索条件を無視
			if (!member_id.equals("")) {

				conditionList.add("(member_id = ?)");
				valueList.add(member_id);

				//支払方法、納品済or未納、商品コードの入力値は無視
				
				//会員IDが入力されていない場合
			} else {

				// 決済方法
				// フォームで"代引き"が選択された場合
				if (payment_method != null && payment_method.equals("0")) {

					conditionList.add("(payment_method = ?)");
					valueList.add(payment_method);

					// フォームで"クレジット"が選択された場合
				} else if (payment_method != null && payment_method.equals("1")) {

					conditionList.add("(payment_method = ?)");
					valueList.add(payment_method);

					// フォームで現金が選択された場合
				} else if (payment_method != null && payment_method.equals("2")) {

					conditionList.add("(payment_method = ?)");
					valueList.add(payment_method);

					//それ以外の場合
				} else {

				}

				// 登録or未登録判定
				// "未納品"が選択された場合
				if (delivery_date_flag.equals("未納品")) {

					// 条件：納品日カラムがNULLとなっている
					conditionList.add("(delivery_date IS NULL)");

					// "納品済"が選択された場合
				} else if (delivery_date_flag.equals("納品済")) {

					// 条件：納品日カラムに日付が記載されている
					conditionList.add("(delivery_date IS NOT NULL)");

					// それ以外の場合
				} else {

				}

				// 商品コード
				// 商品コードが入力されている場合
				if (!item_cd.equals("")) {

					conditionList.add("(order_detail.item_cd = ?)");
					valueList.add(item_cd);

					// それ以外の場合
				} else {

				}
			}

			//WHERE以降のSQL文を作成
			// 条件文が存在する場合、すなわち入力値が存在する場合
			if (!conditionList.isEmpty()) {

				// 条件文の番号
				// WHEREとANDの挿入に利用
				int counter = 1;

				//条件格納リストの要素、すなわち条件文の数だけループ
				for (String condition : conditionList) {

					//最初だけWHEREを入れる
					if (counter == 1) {
						str += " WHERE ";
					}

					//条件式をSQL文に追加
					str += condition;

					// SQL文の最後にANDが追加されるのを防ぐためのif文
					// 最後の条件文ではない場合
					if (counter < conditionList.size()) {
						
						//条件式の最後にANDを追加
						str += " AND ";
					}
					
					// 条件文番号を１繰り上げ
					counter++;

				}

			}

			//SQL文をコンパイル用変数に代入
			String sql = str;

			//【エラーチェック用】作成したSQL文を確認可能
			System.out.println(sql);

			//SQL文のコンパイル
			ps = con.prepareStatement(sql);

			//入力値リストが空ではない場合
			if (!valueList.isEmpty()) {

				//プレースホルダ―の番号
				int i = 1;

				//リストから入力値を取り出す
				for (String value : valueList) {

					//リストの値をプレースホルダ―に設定
					ps.setString(i, value);

					i++;
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// 検索結果から注文番号を取得
				String order_no_value = rs.getString("order_no"); //注文番号 orderテーブル
				
				// 注文番号をリストに追加
				OrderNoList.add(order_no_value);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("売上情報の取得に失敗しました");
		}

		//※order_detailテーブルを結合していることにより、
		//　1つの注文番号に対して複数（購入商品の種類数）のレコードが存在することがあるので、
		//　注文番号リスト内の重複する値(注文番号)を1つにする
		List<String> OrderNoListMerge = new ArrayList<String>(new LinkedHashSet<>(OrderNoList));

		// 注文番号格納リストを返却
		return OrderNoListMerge;

	}

	/**
	 * 2つのテーブル(order, member)を結合させ、
	 * 引数で受け取った注文番号リストの各注文番号で検索し、注文番号ごとの売上情報を取得するメソッド
	 * 検索結果が0件の場合は空のリストを返却
	 * @return 売上情報の入ったリスト
	 * @throws SalesSystemException 検索失敗の際に発生
	 **/
	public List<SalesInformation> findOrder(List<String> OrderNoList) throws SalesSystemException {

		// 売上情報を格納するリスト
		ArrayList<SalesInformation> OrderList = new ArrayList<>();

		try {

			//注文番号の数だけ処理
			for (String OrderNo : OrderNoList) {
				
				// SQL文
				// 注文番号をもとに売上情報を検索
				String sql = "SELECT * FROM `order` "
						+ "INNER JOIN `member` ON `order`.member_id = `member`.user_id "
						+ "WHERE `order`.order_no = ?";

				ps = con.prepareStatement(sql);

				ps.setString(1, OrderNo);

				rs = ps.executeQuery();

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
					
					// 売上情報を売上情報格納リストに追加
					OrderList.add(orderinfo);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("注文情報の取得に失敗しました");
		}

		// 売上情報格納リストを返却
		return OrderList;

	}
	
	/**
	 * 引数で受け取った注文番号リストの各注文番号で検索し、注文番号ごとの売上詳細情報を取得するメソッド
	 * 検索結果が0件の場合は空のリストを返却
	 * @return 売上詳細情報の入ったリスト
	 * @throws SalesSystemException 検索失敗の際に発生
	 **/
	public HashMap<String, List<SalesInformation>> findOrder_Detail(List<String> OrderNoList)
			throws SalesSystemException {

		// 注文番号をキー、注文詳細情報を値にして格納するハッシュマップ
		HashMap<String, List<SalesInformation>> hashmap = new HashMap<>();

		try {
			//注文番号の数だけ処理
			for (String Order : OrderNoList) {

				//初期化
				// 売上詳細情報を格納するリスト
				ArrayList<SalesInformation> OrderDetailList = new ArrayList<>();

				// SQL文
				//注文番号から売上詳細情報を取得
				String sql = "SELECT * FROM order_detail WHERE order_no = ?";

				ps = con.prepareStatement(sql);

				ps.setString(1, Order);

				rs = ps.executeQuery();

				while (rs.next()) {

					//売上詳細情報
					int row_no_value = rs.getInt("row_no"); //注文行
					String item_cd_value = rs.getString("item_cd"); //商品コード
					String item_name_value = rs.getString("item_name"); //商品名
					String unit_price_value = rs.getString("unit_price"); //単価
					String quantity_value = rs.getString("quantity"); //数量
					String subtotal_value = rs.getString("subtotal"); //小計

					SalesInformation orderinfo = new SalesInformation(row_no_value, item_cd_value, item_name_value,
							unit_price_value, quantity_value,
							subtotal_value);
					
					// 検索結果の会員情報をリストに追加
					OrderDetailList.add(orderinfo);

					//注文番号をキー、売上詳細情報リストを値にして格納
					hashmap.put(Order, OrderDetailList);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("売上情報の取得に失敗しました");
		}

		// 売上情報を格納したHashMapを返却
		return hashmap;

	}
	
	/**
	 * 入力された新規登録する商品の情報をDBへ登録するメソッド
	 * @throws SalesSystemException 検索失敗の際に発生
	 **/
	public void InsertItemInfo(SalesInformation iteminfo)
			throws SalesSystemException {

		String item_name = iteminfo.getItem_Name();
		String product_category = iteminfo.getProduct_Category();
		String price = iteminfo.getPrice();
		String description = iteminfo.getDescription();
		String regist_date = LocalDateTime.now().toString();

		try {
			
			// SQL文
			// 商品情報をDBへ新規登録
			String sql = "INSERT INTO m_item "
					+ "(item_name, product_category, price, description, regist_date)"
					+ "VALUES (?, ?, ?, ?, ?)";

			ps = con.prepareStatement(sql);

			ps.setString(1, item_name);
			ps.setString(2, product_category);
			ps.setString(3, price);
			ps.setString(4, description);
			ps.setString(5, regist_date);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SalesSystemException("商品情報の登録に失敗しました");
		}

	}

}