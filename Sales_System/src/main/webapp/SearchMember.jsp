<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.UserInformation"%>
<!DOCTYPE html>
<html>
<!-- 共通パーツ（ヘッダー） -->
<jsp:include page="common/header.jsp" />
<body>
	<!-- 共通パーツ（ナビ） -->
	<jsp:include page="common/Ad_navi.jsp" />
	<section class="py-5 fw-bold" style="font-size: 12px;">
		<div class="container">
			<h4 class="my-4" style="text-align: center;">＊＊＊＊＊会員検索＊＊＊＊＊</h4>
			<form action="SearchMemberServlet" method="post">
				<!-- ユーザーID -->
				<div class="row justify-content-center">
					<div class="col-md-2" style="text-align: right;">ユーザーID</div>
					<div class="col-md-6">
						<input type="text" id="id" name="user_id" class="form-control">
					</div>
				</div>
				<br>
				<!-- ユーザー名 -->
				<div class="row justify-content-center">
					<div class="col-md-2" style="text-align: right;">ユーザー名</div>
					<div class="col-md-6">
						<input type="password" id="password" name="user_name"
							class="form-control">
					</div>
				</div>
				<br>
				<div class="row justify-content-center">
					<div class="col-md-8" style="text-align: center;">
						<button class="btn btn-primary" type="submit">検索</button>
					</div>
				</div>
			</form>
		</div>
		</div>
		</div>
		<div class="align-items-center py-5">
			<div class="container" id="result">
				<%
				List<UserInformation> memberList = (List<UserInformation>) request.getAttribute("memberList");
				%>
				<%
				if (memberList != null) {
				%>
				<h4>検索結果</h4>
				<!-- 会員一覧 -->
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
					<%
					//拡張for文
					for (UserInformation memberinfo : memberList) {
					%>
					<tbody>
						<tr>
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
							<td><button class="btn btn-outline-danger" id="delete"
									style="border: 1px solid red;"
									href="DeleteMemberServlet?user_id=<%=memberinfo.getUser_Id()%>"
									onclick="deleteUserInfo()">
									<i class="bi bi-trash" style="color: red;"></i>
								</button></td>
						</tr>
					</tbody>
					<%
					}
					%>
				</table>
				<div style="text-align: center;">
					<button class="btn btn-warning" type="button"
						onclick="clearResult()">結果クリア</button>
				</div>
				<%
				}
				%>
			</div>
		</div>
		<script>
			function deleteUserInfo() {
				if (confirm("削除しますか？")) {

				} else {
					document.getElementById("delete").href = "";
					document.getElementById("delete").type = "button";
				}
			}

			function clearResult() {
				const result = document.getElementById("result");
				result.remove();
			}
		</script>
	</section>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/Ad_footer.jsp" />
</body>
</html>