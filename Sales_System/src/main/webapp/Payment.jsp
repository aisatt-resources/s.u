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
	<h1>購入画面</h1>
	<!-- 商品リスト -->
	<section class="py-5">
		<div class="container">
			<div id="itemList" class="fs-2" style="color: red;"></div>
			<%
			if (request.getAttribute("message") != null) {
			%>
			<h3 style="color: red;"><%=request.getAttribute("message")%></h3>
			<%
			} else {
			%>
			<!-- 商品陳列部分 -->
			<form action="ShoppingCartServlet" method="post">
				<script>
					var k = 0;
					const quantity = [];
					const item_name = [];
					const item_cd = [];
					const price = [];
					const item_element = [];
					const image = [];
					var subTotal = 0;
					var Total = 0;
					const quantity_element = [];
				</script>
				<%
				for (int i = 1; i <= 4; i++) {
				%>
				<div class="row justify-content-center border border-dotted"
					id="item<%=i%>"
					style="border: 2px solid black; margin: 10px; padding: 10px;">
					<!-- 画像 -->
					<div class="col-md-2 form-group">
						<a href="OrderServlet?item_cd=<%=i%>" style="text-align: center;"><img
							src="" alt="リンク切れ" width="100" height="100" id="image<%=i%>">
						</a>
					</div>
					<!-- 詳細 -->
					<div class="col-md-3 form-group">
						<!-- 商品名 -->
						<span id="item_name<%=i%>"></span> <br>
						<!-- 数量 -->
						<span>個数：<input class="fs-5" type="text"
							id="quantity<%=i%>" name="quantity<%=i%>" style="border: none;"
							readonly></span><br>
						<!-- 小計 -->
						<span>小計(￥)</span>
						<h3 id="subTotal<%=i%>"></h3>
					</div>
					<br>
				</div>
				<!-- 商品コード取得用(ユーザーからは不可視) -->
				<input id="item_cd<%=i%>" name="item_cd<%=i%>" hidden></input>
				<script>
					//セッションから購入情報を取得
					quantity
							.push(sessionStorage.getItem('quantity:' + (k + 1)));
					item_name.push(sessionStorage.getItem('item_name:'
							+ (k + 1)));
					item_cd.push(sessionStorage.getItem('item_cd:' + (k + 1)));
					price.push(sessionStorage.getItem('price:' + (k + 1)));

					image.push(document.getElementById("image" + (k + 1)));
					item_element
							.push(document.getElementById('item' + (k + 1)));

					//画像URL
					image[k].src = 'images/item_' + item_cd[k] + '.png';
					//商品名の表示
					document.getElementById("item_name" + (k + 1)).innerHTML = "商品名："
							+ item_name[k];
					//小計の表示
					subTotal = price[k] * quantity[k];
					document.getElementById("subTotal" + (k + 1)).innerHTML = subTotal;
					quantity_element.push(document.getElementById('quantity'
							+ (k + 1)));

					//初期値は注文画面からセッションで取得
					quantity_element[k].value = quantity[k];

					//合計金額の初期値		
					Total += subTotal;

					//商品コードの出力
					document.getElementById("item_cd" + (k + 1)).value = item_cd[k];

					//数量0の商品は非表示
					if (quantity[k] == null) {
						item_element[k].remove();
					}

					k++;
				</script>
				<%
				}
				%>
				<div class="row justify-content-center">
					<div class="col-md-3">
						<p>
							合計金額：￥<span class="fs-2 fw-bold" id="Total"></span>
						</p>
						<!-- 会員ID -->
						<p>
							会員ID：<input type="text" name="member_id"
								value="<%=(String) session.getAttribute("user_id")%>"
								readonly></input>
						</p>
						<!-- ユーザー名 -->
						<p>
							ユーザー名：<%=(String) session.getAttribute("user_name")%>
						</p>
						<!-- 決済方法 -->
						決済方法：<select name="payment_method" id="payment_method"
							style="margin: 5px; padding: 5px;">
							<option value="0">代引き</option>
							<option value="1">クレジット</option>
							<option value="2">現金</option>
						</select> <br>
						<!-- 備考 -->
						備考:<input type="text" name="remarks" placeholder="空欄でも構いません。"
							style="margin: 5px; padding: 5px;"></input>
						<!-- 購入ボタン -->
						<h4 style="margin: 20px; padding: 20px;">
							<button class="btn btn-warning btn-form display-4 fw-bold"
								style="border: 2px solid black;" onclick="deleteItemInfo()">注文確定</button>
						</h4>
					</div></form>
			<%
			}
			%>
		</div>
	</section>
	<script>
		const Total_element = document.getElementById("Total");
		Total_element.innerHTML = Total;
		//合計金額格納変数を0に戻す
		Total = 0;
		for (let l = 0; l < 4; l++) {
			Total += price[l] * quantity_element[l].value;
		}
		Total_element.innerHTML = Total;

		//セッションに保存されているデータを削除
		function deleteItemInfo() {
			confirm("購入を確定しますか？");
			for (let i = 1; i <= 4; i++) {
				sessionStorage.removeItem('quantity:' + i);
				sessionStorage.removeItem('item_name:' + i);
				sessionStorage.removeItem('item_cd:' + i);
				sessionStorage.removeItem('price:' + i);
			}
		}
	</script>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/footer.jsp" />
</body>
</html>