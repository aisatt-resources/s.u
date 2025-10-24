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
				data-bs-toggle="dropdown" aria-expanded="false">language</a>
			<ul class="dropdown-menu" id="language">
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
		<ul class="navbar-nav ml-auto" style="text-align: center;">
			<li><a class="nav-link" href="ShoppingCart.jsp"><span
					class="fs-6">🛒</span><br>カート</a></li>
			<%
			String user_name = (String) session.getAttribute("user_name");
			if (user_name != null) {
			%>
			<li><a class="nav-link" href="SearchServlet"><span
					class="fs-6">📄</span><br>購入履歴</span></a></li>
			<li><a class="nav-link" onclick="Logout()">
					<div style="text-align: center;">
						<i class="bi bi-box-arrow-right"></i><br>
					</div> <%=user_name%></a></li>
			<script>
			setTimeout(() => {
				alert('セッションがタイムアウトしました。ログイン画面に遷移します。');
				location.href = 'LoginServlet';
				}, 10 * 60 * 1000);
			</script>
			<%
			} else {
			%>
			<li><a class="nav-link" href="Login.jsp"><span>👤</span> <br>
					ログイン </a></li>
			<%
			}
			%>
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
		function Logout() {
			if (confirm("ログアウトしますか？")) {
				location.href = "LoginServlet";
			} else {
			}
		}
		addEventListener('pageshow', () => {
			  // キャッシュから復元された場合（ブラウザバック時など）
			  if (event.persisted) {
			    	location.href = "Top.jsp";
				    } else {
			    console.log('ページが新規に読み込まれました');
			  }
			});
	</script>
</nav>