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
	<p>
	<h3 style="border:2px solid; width: 200px; text-align:center;">カートに追加</h3>
	</p>
	<!-- 商品リスト -->
	<section class="py-5">
		<%
		if (request.getAttribute("message") != null) {
		%>
		<div class="row justify-content-center">
		<h2 style="text-align: center; color: red;">
			<%=request.getAttribute("message")%>
		</h2>
		<a href="Top.jsp" style="text-align:center;">
		買い物を続ける</a>
		</div>
		<%
		} else {
		%>
		<form action="OrderServlet" method="post">
			<!-- 商品陳列部分 -->
			<div style="text-align: center;">
				<!-- 画像 -->
				<img src="images/<%=request.getAttribute("url")%>.png"
					alt="<%=request.getAttribute("item_name")%>" width="200"
					height="200"><br> <span id="item_name"><%=request.getAttribute("item_name")%></span>
				<div>
					<span class="fs-5">￥<%=request.getAttribute("price")%></span>/個</div>
				<div class="counter" id="counter" style="text-align: center;">
					<button type="button" id="minus">－</button>
					<input type="text" pattern="[0-9]*" min="1" max="99" value="1"
						class="quantity" name="quantity" id="quantity" size="1">
					<button type="button" id="plus">＋</button>
					<div>
					<u>
						小計：￥<span class="fs-3" id="subTotal"><%=request.getAttribute("price")%></span></div>
				</u>
				</div>
				<br>
				<!-- セッションへ注文データを保存し、サーブレットに処理を投げる -->
				<button class="btn btn-warning btn-form display-4" onclick="addItem()">カートに追加</button>

			</div>
		</form>
		<%
		}
		%>
	</section>
	<script>
	//注文数カウンター
	  const MinusButton_element = document.getElementById('minus');
	  const PlusButton_element = document.getElementById('plus');
	  const quantity_element = document.getElementById('quantity');
	// 小計(数量×単価)を表示
	  const price = <%=request.getAttribute("price")%>;
	  const subTotal_element = document.getElementById('subTotal');
	  const item_name = document.getElementById('item_name').textContent;
	  const item_cd = <%=request.getAttribute("item_cd")%>;
	  
	//ボタンが押されたら減算
	  MinusButton_element.addEventListener('click', (event) => {
	//0未満にならないようにする
	  if(quantity_element.value >= 2) {
	    quantity_element.value--;
		const subTotal = price*quantity_element.value;
	    subTotal_element.innerHTML = subTotal;
	  }
	  });

	//ボタンが押されたら加算
	  PlusButton_element.addEventListener('click', (event) => {
	    quantity_element.value++;
		  const subTotal = price*quantity_element.value;
		  //小計タグに出力
		  subTotal_element.innerHTML = subTotal;
	  });
	  
	  // 商品コードと購入数量をセッションに保存
	  function addItem(){
		  const quantity = quantity_element.value;
		  //数量
		  sessionStorage.setItem('quantity:'+item_cd, quantity);
		  //商品名
		  sessionStorage.setItem('item_name:'+item_cd, item_name);
		  //商品コード
		  sessionStorage.setItem('item_cd:'+item_cd, item_cd);
		  //単価
		  sessionStorage.setItem('price:'+item_cd, price);
		  
	  }
	  
	</script>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/footer.jsp" />
</body>
</html>