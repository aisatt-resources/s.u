<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<!-- 共通パーツ（ヘッダー）読み込み -->
<jsp:include page="common/header.jsp" />
</head>
<body>
	<!-- ロゴの表示・検索フォーム・カートへの遷移ボタン -->
	<jsp:include page="common/Ad_navi.jsp" />
	<p>
		<br>
	<h3 style="text-align: center;">＊＊＊商品登録＊＊＊</h3>
	</p>
	<!-- 商品リスト -->
	<section class="py-5">
		<%
		if (request.getAttribute("message") != null) {
		%>
		<h2 style="text-align: center; color: red;">
			<%=request.getAttribute("message")%>
		</h2>
		<%
		} else {
		%>
		<form action="RegisterServlet" method="post">
			<!-- 商品陳列部分 -->
			<div class="fw-bold">
				<div class="row justify-content-center">
					<p>
					<div class="col-md-2" style="text-align: right;">
						<label>商品名：</label>
					</div>
					<div class="col-md-6">
						<input class="form-control" type="text" name="item_name"
							placeholder="商品名を入力" required>
					</div>
					</p>
				</div>
				<div class="row justify-content-center">
					<p>
					<div class="col-md-2" style="text-align: right;">
						<label>カテゴリ：</label>
					</div>
					<div class="col-md-6 form-group">
						<select name="product_category" required>
							<option>カテゴリを選択</option>
							<option value="1">1.おもちゃ</option>
							<option value="2">2.家電</option>
							<option value="3">3.飲料</option>
							<option value="4">4.食品</option>
						</select>
					</div>
					</p>
				</div>
				<div class="row justify-content-center">
					<p>
					<div class="col-md-2" style="text-align: right;" type="number"
						placeholder="商品価格を入力" required>
						<label>価格：</label>
					</div>
					<div class="col-md-6">
						<input class="form-control" type="text" name="price"
							placeholder="価格を入力">
					</div>
					</p>
				</div>
				<div class="row justify-content-center">
					<p>
					<div class="col-md-2" style="text-align: right;">
						<label>商品説明：</label>
					</div>
					<div class="col-md-6">
						<input class="form-control" type="text" name="description"
							style="height: 75px;" placeholder="商品説明を入力">
					</div>
					</p>
				</div>
				<!-- セッションへ注文データを保存し、サーブレットに処理を投げる -->
				<div class="row justify-content-center">
					<p>
					<div class="col-md-2">
						<button class="btn btn-warning fw-bold">商品登録</button>
					</div>
					</p>
				</div>
			</div>
		</form>
		<%
		}
		%>
	</section>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/Ad_footer.jsp" />
</body>
</html>