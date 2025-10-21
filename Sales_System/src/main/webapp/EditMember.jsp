<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.UserInformation"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報編集</title>
</head>
<!-- 共通パーツ（ヘッダー） -->
<jsp:include page="common/header.jsp" />
<body>
	<div class="sidebar" />
	<!-- 共通パーツ（ナビ） -->
	<jsp:include page="common/Ad_navi.jsp" />
	</div>
	<!-- 詳細部分 -->
	<section class="py-5" style="font-size:12px;">
		<div class="row justify-content-center">
			<div class="media-container-column col-lg-8">
				<h4 class="my-4" style="text-align:center;">＊＊＊会員情報編集＊＊＊</h4>
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
				<%
				List<UserInformation> memberList = (List<UserInformation>) request.getAttribute("memberList");
				if (memberList != null) {
					//拡張for文
					for (UserInformation memberinfo : memberList) {
				%>

				<form action="EditMemberServlet" method="post">
					<div class="row">
						<div class="col-md-12 form-group">
							<label class="form-control-label">ユーザーID</label> <input
								type="text" name="user_id" value="<%=memberinfo.getUser_Id()%>"
								class="form-control" readonly>
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">ユーザー名</label> <input
								type="text" name="user_name" required="required"
								placeholder="山田太郎" value="<%=memberinfo.getUser_Name()%>"
								class="form-control">
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">メールアドレス</label> <input
								type="text" name="email" placeholder="sample@sample.jp"
								value="<%=memberinfo.getEmail()%>" class="form-control">
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">電話番号</label> <input type="text"
								name="tel" required="required" placeholder="09012345678"
								value="<%=memberinfo.getTel()%>"
								pattern="0[1-9]0[0-9]{8}|0[1-9]{3}[0-9]{6}"
								title="ハイフンなしで入力してください。" class="form-control">
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">住所</label> <input type="text"
								name="address" placeholder="東京都千代田区霞が関"
								value="<%=memberinfo.getAddress()%>" class="form-control">
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">生年月日</label> <input type="date"
								name="birthday" required="required"
								value="<%=memberinfo.getBirthday()%>" class="form-control">
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">職業</label> <input type="text"
								name="job" placeholder="エンジニア" value="<%=memberinfo.getJob()%>"
								class="form-control">
						</div>
						<div class="col-md-12 form-group">
							<label class="form-control-label">パスワード</label> <input
								type="password" name="password" required="required"
								value="<%=memberinfo.getPassword()%>" class="form-control">
						</div>
						<%
						}
						}
						%>
						<div class="row justify-content-center">
							<p>
							<div class="col-md-2" style="text-align: center;">
								<button class="btn btn-primary btn-form display-4" type="submit">確定</button>
							</div>
							</p>
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/Ad_footer.jsp" />
</body>

</html>