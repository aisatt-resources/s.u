package model;

import java.sql.Timestamp;

/**
 * 注文情報クラス
 */
public class SalesInformation {

	private String order_date_start; // 注文日付（from）
	private String order_date_end; // 注文日付（to）
	private String member_id; // 会員ID
	private String payment_method; // 支払方法	
	private String delivery_date_flag; // 登録or未登録判定
	private String item_cd; // 商品コード
	private String order_no; //注文番号
	private Timestamp order_date; //注文日
	private String user_name; //会員名
	private String total_amount; //税抜合計金額
	private String delivery_date; //納品日
	private String remarks; //備考
	private int row_no; // 注文行番号
	private String item_name; // 商品名
	private String unit_price; // 税抜単価
	private String quantity; // 数量
	private String subtotal; // 小計

	/**
	 * コンストラクタ
	 * @param order_date_start
	 * @param order_date_end
	 * @param member_id
	 * @param payment_method
	 * @param regist_flag
	 * @param item_cd
	 */
	public SalesInformation(String order_date_start, String order_date_end,
			String member_id, String payment_method,
			String delivery_date_flag, String item_cd) {
		this.order_date_start = order_date_start;
		this.order_date_end = order_date_end;
		this.member_id = member_id;
		this.payment_method = payment_method;
		this.delivery_date_flag = delivery_date_flag;
		this.item_cd = item_cd;
	}
	
	//購入内訳用
	public SalesInformation(String order_no) {

		this.order_no = order_no;

	}
	
	//購入内訳用
	public SalesInformation(int row_no, String item_cd,
			String item_name, String unit_price, String quantity, String subtotal) {

		this.row_no = row_no;
		this.item_cd = item_cd;
		this.item_name = item_name;
		this.unit_price = unit_price;
		this.quantity = quantity;
		this.subtotal = subtotal;

	}


	/**
	 * コンストラクタ
	 * @param order_no
	 * @param order_date
	 * @param member_id
	 * @param user_name
	 * @param payment_method
	 * @param total_amount
	 * @param regist_datetime
	 * @param remarks
	 * @param row_no
	 * @param item_cd
	 * @param item_name
	 * @param unit_price
	 * @param quantity
	 * @param subtotal
	 */
	public SalesInformation(String order_no, Timestamp order_date, String member_id,
			String user_name, String payment_method, String total_amount,
			String delivery_date, String remarks) {
		this.order_no = order_no;
		this.order_date = order_date;
		this.member_id = member_id;
		this.user_name = user_name;
		this.payment_method = payment_method;
		this.total_amount = total_amount;
		this.delivery_date = delivery_date;
		this.remarks = remarks;

	}

	/**
	 * 注文日付（from）を返却
	 * @return order_date_start
	 */
	public String getOrder_Date_Start() {
		return order_date_start;
	}

	/**
	 * 注文日付（from）をセット
	 * @param order_date_start
	 */
	public void setOrder_Date_Start(String order_date_start) {
		this.order_date_start = order_date_start;
	}

	/**
	 * 注文日付（to）を返却
	 * @return order_date_end
	 */
	public String getOrder_Date_End() {
		return order_date_end;
	}

	/**
	 * 注文日付（to）をセット
	 * @param order_date_end
	 */
	public void setOrder_Date_End(String order_date_end) {
		this.order_date_end = order_date_end;
	}

	/**
	 * 会員IDを返却
	 * @return member_id
	 */
	public String getMember_Id() {
		return member_id;
	}

	/**
	 * 会員IDをセット
	 * @param member_id
	 */
	public void setMember_Id(String member_id) {
		this.member_id = member_id;
	}

	/**
	 * 支払方法を返却
	 * @return payment_method
	 */
	public String getPayment_Method() {
		return payment_method;
	}

	/**
	 * 支払方法をセット
	 * @param payment_method
	 */
	public void setPayment_Method(String payment_method) {
		this.payment_method = payment_method;
	}

	/**
	 * 登録or未登録判定を返却
	 * @return regist_flag
	 */
	public String getDelivery_Date_Flag() {
		return delivery_date_flag;
	}

	/**
	 * 登録or未登録判定をセット
	 * @param regist_flag
	 */
	public void setDelivery_Date_Flag(String delivery_date_flag) {
		this.delivery_date_flag = delivery_date_flag;
	}

	/**
	 * 商品コードを返却
	 * @return item_cd
	 */
	public String getItem_Cd() {
		return item_cd;
	}

	/**
	 * 商品コードをセット
	 * @param item_cd
	 */
	public void setItem_Cd(String item_cd) {
		this.item_cd = item_cd;
	}

	/**
	 * 注文番号を返却
	 * @return order_no
	 */
	public String getOrder_No() {
		return order_no;
	}

	/**
	 * 注文番号をセット
	 * @param order_no
	 */
	public void setOrder_No(String order_no) {
		this.order_no = order_no;
	}

	/**
	 * 注文日を返却
	 * @return order_date
	 */
	public Timestamp getOrder_Date() {
		return order_date;
	}

	/**
	 * 注文日をセット
	 * @param order_date
	 */
	public void setOrder_Date(Timestamp order_date) {
		this.order_date = order_date;
	}

	/**
	 * ユーザー名を返却
	 * @return user_name
	 */
	public String getUser_Name() {
		return user_name;
	}

	/**
	 * ユーザー名をセット
	 * @param user_name
	 */
	public void setUser_Name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * 税抜合計金額を返却
	 * @return total_amount
	 */
	public String getTotal_Amount() {
		return total_amount;
	}

	/**
	 * 税抜合計金額をセット
	 * @param total_amount
	 */
	public void setTotal_Amount(String total_amount) {
		this.total_amount = total_amount;
	}

	/**
	 * 納品日を返却
	 * @return regist_datetime
	 */
	public String getDelivery_Date() {
		return delivery_date;
	}

	/**
	 * 納品日をセット
	 * @param regist_datetime
	 */
	public void setDelivery_Date(String delivery_date) {
		this.delivery_date = delivery_date;
	}

	/**
	 * 備考を返却
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 備考をセット
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 注文行番号を返却
	 * @return row_no
	 */
	public int getRow_No() {
		return row_no;
	}

	/**
	 * 注文行番号をセット
	 * @param row_no
	 */
	public void setRow_No(int row_no) {
		this.row_no = row_no;
	}

	/**
	 * 商品名を返却
	 * @return item_name
	 */
	public String getItem_Name() {
		return item_name;
	}

	/**
	 * 商品名をセット
	 * @param item_name
	 */
	public void setItem_Name(String item_name) {
		this.item_name = item_name;
	}

	/**
	 * 税抜単価を返却
	 * @return unit_price
	 */
	public String getUnit_Price() {
		return unit_price;
	}

	/**
	 * 税抜単価をセット
	 * @param unit_price
	 */
	public void setUnit_Price(String unit_price) {
		this.unit_price = unit_price;
	}

	/**
	 * 数量を返却
	 * @return quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * 数量をセット
	 * @param quantity
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * 小計を返却
	 * @return subtotal
	 */
	public String getSubtotal() {
		return subtotal;
	}

	/**
	 * 小計をセット
	 * @param subtotal
	 */
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

}