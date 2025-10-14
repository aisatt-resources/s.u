package model;

/**
 * 注文情報クラス
 */
public class OrderInformation {

	private String member_id; // 会員ID
	private String payment_method; // 支払方法	
	private String item_cd; // 商品コード
	private String[] item_cd_list;
	private String order_no; //注文番号
	private String user_name; //会員名
	private String total_amount; //税抜合計金額
	private String remarks; //備考
	private int row_no; // 注文行番号
	private String item_name; // 商品名
	private String quantity; // 数量
	private String[] quantity_list;
	private String subtotal; // 小計
	private String price; // 税抜販売価格
	
	//Top.jspにて、商品を選択した際に使用
	public OrderInformation(String item_cd) {
		this.item_cd = item_cd;
	}
	
	//Payment.jspにて、注文確定時に使用
	public OrderInformation(String[] item_cd_list, String[] quantity_list, String payment_method, String remarks, String member_id) {
		this.item_cd_list = item_cd_list;
		this.quantity_list = quantity_list;
		this.payment_method = payment_method;
		this.remarks = remarks;
		this.member_id = member_id;
	}
	
	/**
	 * コンストラクタ
	 * @param item_cd
	 */

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
	 * 商品コードリストを返却
	 * @return item_cd
	 */
	public String[] getItem_Cd_list() {
		return item_cd_list;
	}

	/**
	 * 商品コードリストをセット
	 * @param item_cd
	 */
	public void setItem_Cd_list(String item_cd_list[]) {
		this.item_cd_list = item_cd_list;
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
	 * 数量リストを返却
	 * @return quantity
	 */
	public String[] getQuantity_list() {
		return quantity_list;
	}

	/**
	 * 数量リストをセット
	 * @param quantity
	 */
	public void setQuantity_list(String quantity_list[]) {
		this.quantity_list = quantity_list;
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

	/**
	 * 税抜販売価格を返却
	 * @return subtotal
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * 税抜販売価格をセット
	 * @param subtotal
	 */
	public void setPrice(String price) {
		this.price = price;
	}

}