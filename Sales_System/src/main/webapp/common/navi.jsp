<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- ナビゲーション -->
<nav
	class="navbar navbar-expand-lg navbar-light bg-danger-subtle shadow">
	<div class="container">
		<!-- Topページへのリンク -->
		<a class="navbar-brand fs-3 fw-bold" href="Top.jsp"
			style="color: red;">楽〇市場</a>
		<div class="nav-item dropdown">
			<a class="btn btn-secondary dropdown-toggle" href="#" role="button"
				data-bs-toggle="dropdown" aria-expanded="false">Language</a>
			<ul class="dropdown-menu">
				<li><a class="dropdown-item" href="#" onclick="Message();">Japanese</a></li>
				<li><a class="dropdown-item" href="#" onclick="Message();">English</a></li>
				<li><a class="dropdown-item" href="#" onclick="Message();">French</a></li>
			</ul>
		</div>
		<div>
			<div class="input-group">
				<button class="input-group-text bg-dark text-white">検索</button>
				<input type="text" class="input-text" placeholder="商品を検索"></input>
			</div>
		</div>
		<!-- カートへのリンク -->
		<ul class="navbar-nav ml-auto">
			<li><a class="nav-link" href="ShoppingCart.jsp"><span
					class="fs-6">🛒</span><br>カート</a></li>
			<li><a class="nav-link" href=""><span class="fs-6">📄</span><br>購入履歴</span></a></li>
		</ul>
	</div>
	<!-- CSS（bootstrap）の読み込み ※これがないとドロップダウンが有効化されない-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
		crossorigin="anonymous">
		
	</script>
	<!-- CSS（bootstrap）の読み込み ※ゴミ箱アイコンなどを使用可能にする。 -->
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css"
		rel="stylesheet">
	<!-- 未実装機能使用時ののメッセージ出力 -->
	<script>
		function Message() {
			alert("まだこの機能はありません。");
		}
	</script>
</nav>