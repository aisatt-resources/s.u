<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.UserInformation"%>
<%@ page import="dao.CustomerDao"%>
<!DOCTYPE html>
<html>
<!-- 共通パーツ（ヘッダー） -->
<jsp:include page="common/header.jsp" />
<body>
	<!-- 共通パーツ（ナビ） -->
	<jsp:include page="common/Ad_navi.jsp" />
	<!-- 一覧表示 -->
	<section class="py-5">
		<div class="container">
			<h4 class="my-4">会員一覧</h4>
			<%
			String message = (String) request.getAttribute("message");
			%>
			<%
			if (message != null) {
			%>
			<div class="alert alert-success" role="alert">
				<%=message%>
			</div>
			<%
			}
			%>
			<!-- 会員一覧 -->
			<div style="font-size:12px;">
				<table class="table table-hover">
					<!-- 見出し部分 -->
					<thead class="thead-dark">
						<tr>
							<th scope="col">ユーザーID</th>
							<th scope="col">ユーザー名</th>
							<th scope="col">メールアドレス</th>
							<th scope="col">電話番号</th>
							<th scope="col">住所</th>
							<th scope="col">生年月日</th>
							<th scope="col">職業</th>
							<th scope="col">登録日</th>
							<th scope="col">更新日</th>
							<th scope="col"></th>
							<th scope="col"></th>
						</tr>
					</thead>
					<!-- 一覧部分 -->
					<!-- データベースから取得した会員情報一覧をリクエストスコープから取得 -->
					<tbody>
						<%
						CustomerDao memberDao = new CustomerDao();
						List<UserInformation> memberList = memberDao.findAllMember();
						%>
						<%
						//拡張for文
						for (UserInformation memberinfo : memberList) {
						%>
						<tr>
							<!-- 編集したレコードのユーザーIDをリクエストスコープから取得 -->
							<%
							if (request.getAttribute("user_id") != null) {

								String user_id = memberinfo.getUser_Id();
								String userid = (String) request.getAttribute("user_id");

								// 編集したレコードの場合は色を付ける。
								if (user_id.equals(userid)) {
							%>
						
						<tr style="background-color: yellow; color: black;">
							<%
							}
							}
							%>
							<td><%=memberinfo.getUser_Id()%></td>
							<td><%=memberinfo.getUser_Name()%></td>
							<td><%=memberinfo.getEmail()%></td>
							<td><%=memberinfo.getTel()%></td>
							<td><%=memberinfo.getAddress()%></td>
							<td><%=memberinfo.getBirthday()%></td>
							<td><%=memberinfo.getJob()%></td>
							<td><%=memberinfo.getCreated_Datetime()%></td>
							<td><%=memberinfo.getUpdate_Datetime()%></td>
							<td><a class="btn btn-outline-primary"
								href="EditMemberServlet?user_id=<%=memberinfo.getUser_Id()%>"><i
									class="bi bi-pencil"></i></a></td>
							<td><a class="btn btn-outline-danger" id="delete"
								style="border: 1px solid red;"
								href="DeleteMemberServlet?user_id=<%=memberinfo.getUser_Id()%>"
								onclick="deleteUserInfo()"><i class="bi bi-trash"
									style="color: red;"></i></a></td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
		<script>
			function deleteUserInfo() {
				if (confirm("削除しますか？")) {

				} else {
					document.getElementById("delete").href = "";
				}
			}
		</script>
	</section>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/Ad_footer.jsp" />
</body>
</html>