<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<!-- 共通パーツ（ヘッダー）読み込み -->
<jsp:include page="common/ShoppingPage_header.jsp" />
</head>
<body>
	<!-- ロゴの表示・検索フォーム・カートへの遷移ボタン -->
	<jsp:include page="common/navi.jsp" />
	<h1>セール中</h1>
	<!-- 商品リスト -->
	<section class="py-5">
		<div class="container">
			<!-- 商品陳列部分 -->
			<div class="row" style="text-align: center;">
				<div class="col-md-3 form-group">
					<a href="OrderServlet?item_cd=1"> <!-- 画像 --> <img
						src="images/item_1.png" alt="商品１" width="200" height="200">
						<p class="fs-4">ジュース</p></a>
					<p>￥<span class="fs-4 fw-bold">200</span>/個</p>
				</div>
				<div class="col-md-3 form-group">
					<a href="OrderServlet?item_cd=2"> <!-- 画像 --> <img
						src="images/item_2.png" alt="商品２" width="200" height="200">
						<p class="fs-4">ハンバーガー</p></a>
					<p>￥<span class="fs-4 fw-bold">500</span>/個</p>
				</div>
				<div class="col-md-3 form-group">
					<a href="OrderServlet?item_cd=3"> <!-- 画像 --> <img
						src="images/item_3.png" alt="商品３" width="200" height="200">
						<p class="fs-4">フライドポテト</p></a>
					<p>￥<span class="fs-4 fw-bold">250</span>/個</p>
				</div>
				<div class="col-md-3 form-group">
					<a href="OrderServlet?item_cd=4"> <!-- 画像 --> <img
						src="images/item_4.png" alt="商品４" width="200" height="200">
						<p class="fs-4">フライドチキン</p></a>
					<p>￥<span class="fs-4 fw-bold">300</span>/個</p>
				</div>

			</div>
		</div>
	</section>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/footer.jsp" />
</body>
</html>