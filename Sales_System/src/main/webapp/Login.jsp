<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<!-- 共通パーツ（ヘッダー）読み込み -->
<jsp:include page="common/ShoppingPage_header.jsp" />
</head>
<body>
	<header>
		<!-- ロゴの表示・検索フォーム・カートへの遷移ボタン -->
		<jsp:include page="common/navi.jsp" />
	</header>
	<main style="min-height: 100vh;position: relative;padding-bottom: 120px;box-sizing: border-box;">
		<section class="py-5">
			<p>
			<h3 style="text-align: center;">＊＊＊ログイン＊＊＊</h3>
			</p>
			<div class="container">
				<form action="LoginServlet" method="post">
					<div class="row justify-content-center">
						<p>
						<div class="col-md-2" style="text-align: right;">会員ID：</div>
						<div class="col-md-5">
							<input name="user_id" class="form-control" type="number"
								placeholder="会員IDを入力">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						<div class="col-md-2" style="text-align: right;">パスワード：</div>
						<div class="col-md-5">
							<input name="password" class="form-control" type="password"
								placeholder="パスワードを入力">
						</div>
						</p>
					</div>
					<%
					if (request.getAttribute("message") != null) {
					%>
					<h6 style="color: red; text-align: center;">
						※<%=request.getAttribute("message")%></h6>
					<%
					}
					%>
					<div class="row justify-content-center">
						<p>
						<div class="col-md-2" style="text-align: center;">
							<button class="btn btn-warning">ログイン</button>
						</div>
						</p>
					</div>
				</form>
				<div class="row justify-content-center">
					<p>
					<div class="col-md-4" style="text-align: center;">
						会員登録はお済みですか？ <br> <a href="RegistMember.jsp">無料会員登録</a>
					</div>
					</p>
				</div>
			</div>
		</section>
	</main>
	<footer>
		<!-- 共通パーツ（フッター） -->
		<jsp:include page="common/footer.jsp" />
	</footer>
</body>
</html>