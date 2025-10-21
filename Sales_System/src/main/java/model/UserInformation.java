package model;

/**
 * 注文情報クラス
 */
public class UserInformation {

	private String user_id; // ユーザーID
	private String user_name; // ユーザー名
	private String email; // メールアドレス
	private String tel; // 電話番号
	private String address; // 住所
	private String birthday; // 生年月日
	private String job; //職業
	private String password; //パスワード
	private String created_datetime; //登録日
	private String update_datetime; //更新日

	//ログイン時に使用
	public UserInformation(String user_id, String password) {
		this.user_id = user_id;
		this.password = password;

	}
	
	public UserInformation(String name, String email,
			String tel, String address,
			String birthday, String job, String password) {
		this.user_name = name;
		this.email = email;
		this.tel = tel;
		this.address = address;
		this.birthday = birthday;
		this.job = job;
		this.password = password;
	}
	
	public UserInformation(String user_id, String user_name, String email,
			String tel, String address,
			String birthday, String job, String password) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.email = email;
		this.tel = tel;
		this.address = address;
		this.birthday = birthday;
		this.job = job;
		this.password = password;
	}
	
	public UserInformation(String user_id, String user_name, String email,
			String tel, String address,
			String birthday, String job, String password, String created_datetime, String update_datetime) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.email = email;
		this.tel = tel;
		this.address = address;
		this.birthday = birthday;
		this.job = job;
		this.password = password;
		this.created_datetime = created_datetime;
		this.update_datetime = update_datetime;
	}

	/**
	 * ユーザーIDを返却
	 * @return user_id
	 */
	public String getUser_Id() {
		return user_id;
	}

	/**
	 * ユーザーIDをセット
	 * @param user_id
	 */
	public void setUser_Id(String user_id) {
		this.user_id = user_id;
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
	 * メールアドレスを返却
	 * @return mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * メールアドレスをセット
	 * @param mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 電話番号を返却
	 * @return tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 電話番号をセット
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 住所を返却
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 住所をセット
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 生年月日を返却
	 * @return birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * 生年月日をセット
	 * @param birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * 職業を返却
	 * @return job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * 職業をセット
	 * @param job
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * パスワードを返却
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードをセット
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 登録日を返却
	 * @return created_datetime
	 */
	public String getCreated_Datetime() {
		return created_datetime;
	}

	/**
	 * 登録日をセット
	 * @param created_datetime
	 */
	public void setCreated_Datetime(String created_datetime) {
		this.created_datetime = created_datetime;
	}

	/**
	 * 更新日を返却
	 * @return updated_datetime
	 */
	public String getUpdate_Datetime() {
		return update_datetime;
	}

	/**
	 * 更新日をセット
	 * @param updated_datetime
	 */
	public void setUpdate_Datetime(String update_datetime) {
		this.update_datetime = update_datetime;
	}

}