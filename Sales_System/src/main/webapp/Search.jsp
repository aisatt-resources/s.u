<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.SalesInformation"%>
<%@ page import="controller.SearchServlet"%>
<%@ page import="dao.MemberDao"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.HashMap"%>

<!DOCTYPE html>
<html>
<head>
<!-- 共通パーツ（ヘッダー）読み込み -->
<jsp:include page="common/header.jsp" />
</head>
<h1 style="color:red; border:2px solid; width: 250px; text-align:center;">管理者画面</h1>
<body>
	<!-- 検索条件 -->
	<section class="py-5">
		<div class="container">
			<!-- 一覧部分 -->
			<form action="SearchServlet" method="post">
				<div class="row">
					<div class="col-md-1 form-group">注文日付</div>
					<div class="col-md-2 form-group">
						<input type="date" name="order_date_start"
							id="order_date_start_form" class="form-control">
					</div>
					<div class="col-md-1 form-group">～</div>
					<div class="col-md-2 form-group">
						<input type="date" name="order_date_end" id="order_date_end_form"
							class="form-control">
					</div>
					<div class="col-md-2 form-group">会員コード</div>
					<div class="col-md-3 form-group">
						<input type="number" name="member_id" id="member_id_form"
							class="form-control" placeholder="会員コードを入力">
					</div>
					<div class="col-md-1 form-group"></div>
					<div class="col-md-1 form-group">支払方法</div>
					<div class="col-md-5 form-group">
						<select name="payment_method" id="payment_method">
							<option>全て</option>
							<option value="0">代引き</option>
							<option value="1">クレジット</option>
							<option value="2">現金</option>
						</select>
					</div>
					<div class="col-md-2 form-group">納品</div>
					<div class="col-md-1 form-group">
						<input type="radio" name="delivery_date_flag" id="all" value="全て"
							checked>全て
					</div>
					<div class="col-md-1 form-group">
						<input type="radio" name="delivery_date_flag" id="not_registed"
							value="未納品">未納品
					</div>
					<div class="col-md-2 form-group">
						<input type="radio" name="delivery_date_flag" id="registed"
							value="納品済"> 納品済
					</div>
					<div class="col-md-1 form-group">商品コード</div>
					<div class="col-md-2 form-group">
						<input type="number" name="item_cd" id="item_cd_form"
							class="form-control" placeholder="商品コードを入力">
					</div>
					<div class="col-md-4 form-group" id="item_name">
						<!-- 商品名をリクエストスコープから取得 -->
						<%
						if (request.getAttribute("item_name") != null) {
						%>
						商品名：<span class="fs-5 fw-bold" style="color:red;"><%=request.getAttribute("item_name")%></span>
						<%
						}
						%>
					</div>
					<div class="col-md-3 form-group"></div>
					<div class="col-md-2 form-group">
						<!-- ボタン押下時にclearText()関数の呼び出し -->
						<input class="btn btn-primary btn-form display-4" type="button"
							value="検索条件クリア" onclick="clearText()" />
					</div>
					<div class="col-md-1 form-group"></div>
					<div class="col-md-3 form-group">
						<!-- ボタン押下時に入力内容をサーブレットへ送信 -->
						<button class="btn btn-primary btn-form display-4" type="submit">検索</button>
					</div>
					<div class="col-md-8 form-group"></div>
				</div>
			</form>
		</div>
	</section>
	<section class="py-5">
		<div class="container">
			<table class="table table-hover">
				<!-- 見出し部分 -->
				<thead class="table-light">
					<tr>
						<th class="col-md-1">注文番号</th>
						<th class="col-md-1">注文日</th>
						<th class="col-md-2">会員ID:会員名</th>
						<th class="col-md-2">支払方法</th>
						<th class="col-md-3">税込合計金額（うち消費税）</th>
						<th class="col-md-1">納品</th>
						<th class="col-md-2">備考</th>
					</tr>
				</thead>
				<!-- 売上情報部分 -->
				<!-- 売上情報をリクエストスコープから取得 -->
				<tbody id="orderList">
					<%
					List<SalesInformation> OrderList = (List<SalesInformation>) request.getAttribute("OrderList");
					HashMap<String, List<SalesInformation>> OrderDetailHashMap = (HashMap<String, List<SalesInformation>>) request.getAttribute("OrderDetailHashMap");
					%>
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
					// 集計消費税金額
					int sum_Total = 0;

					// 集計金額
					int sum_Total_Tax = 0;

					// 注文数カウンター
					int order_count = 0;

					if (OrderList != null) {
						
						//拡張for文
						for (SalesInformation orderinfo : OrderList) {
							if(order_count==Integer.parseInt(orderinfo.getOrder_No())){
								continue;
							}
							order_count++;
					%>
					
					<tr class="table-success">
						<td>
							<!-- 注文番号 --> <%=orderinfo.getOrder_No()%> 
						</td>
						<!-- 注文日 -->
						<td>
							<%
							SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
							String date = s.format(orderinfo.getOrder_Date());
							%> <%=date%>
						</td>
						<!-- 会員ID：ユーザー名 -->
						<td><%=orderinfo.getMember_Id()%>: <%=orderinfo.getUser_Name()%></td>
						<td>
							<!-- 支払方法 --> 
							<%if (orderinfo.getPayment_Method() != null && orderinfo.getPayment_Method().equals("0")) {%>
							代引き 
							<%
							} else if (orderinfo.getPayment_Method() != null && orderinfo.getPayment_Method().equals("1")) {
							%>
							クレジット
							<%
							} else if (orderinfo.getPayment_Method() != null && orderinfo.getPayment_Method().equals("2")) {
							%>
							現金 
							<%} else {}%>
						</td>
						<!-- 税込合計金額 -->
						<td>￥<%=(int) (Integer.parseInt(orderinfo.getTotal_Amount()) * 1.1)%>
							<!-- うち消費税 --> (￥<%=(int) (Integer.parseInt(orderinfo.getTotal_Amount()) * 0.1)%>)
						</td>
						<%
						// 集計金額に加算
						sum_Total += Integer.parseInt(orderinfo.getTotal_Amount()) * 1.1;
						// 集計消費税金額に加算
						sum_Total_Tax += Integer.parseInt(orderinfo.getTotal_Amount()) * 0.1;
						%>
						<td>
							<!-- 納品済or未納 -->
							<%
							if (orderinfo.getDelivery_Date() != null) {
							%> 
							済 
							<%
							} else {
							%> 
							未納 
							<%
							}
							%>
						</td>
						<!-- 備考 -->
						<td><%=orderinfo.getRemarks()%></td>
					</tr>
					<tr>
						<td colspan="3"></td>
						<td colspan="1">購入内訳</td>
						<td colspan="3" class="table-danger">
						<% List<SalesInformation> OrderDetail = OrderDetailHashMap.get(orderinfo.getOrder_No()); %>
						<%
						int i=0;
						for(SalesInformation order_detail_info : OrderDetail){ 
						%>
							<!-- 注文行番号 --><%=i+1%><%i++;%>、
							<!-- 商品名 --> <%=order_detail_info.getItem_Name()%>
							 <!-- 商品コード --> [<%=order_detail_info.getItem_Cd()%>]
							<!-- 単価 --> ￥<%=order_detail_info.getUnit_Price()%>、
							<!-- 数量 --> <%=order_detail_info.getQuantity()%>個 <!-- 小計 --> 小計：￥<%=order_detail_info.getSubtotal()%>
							<br> 
							<% } %>
						</td>
					</tr>
					<%
					}
					%>
					<tr>
						<td colspan="4">合計注文数：<span class="fs-4"><%=order_count%></span></td>
						<td colspan="8">集計金額： ￥<span class="fs-4"><%=sum_Total%></span>
						 (うち消費税：￥<%=sum_Total_Tax%>)
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</section>
	<div class="container">
		<div class="row">
			<div class="col-md-12" style="text-align: right;">
				<!-- ボタン押下時にclearOrderList()関数の呼び出し -->
				<button class="btn btn-warning btn-form display-4"
					onclick="clearOrderList()">結果クリア</button>
			</div>
		</div>
	</div>
	<script>
		// 「検索条件クリア」ボタン押下時に実行
		function clearText() {

			// 入力内容を空文字に変更
			var order_date_start_form = document
					.getElementById("order_date_start_form");
			var order_date_end_form = document
					.getElementById("order_date_end_form");
			var member_id_form = document.getElementById("member_id_form");
			var item_cd_form = document.getElementById("item_cd_form");
			order_date_start_form.value = '';
			order_date_end_form.value = '';
			member_id_form.value = '';
			item_cd_form.value = '';
			var payment_method = document.getElementById("payment_method");
			payment_method.value = '';

			// ラジオボタンのチェック箇所を「全て」に変更
			var all = document.getElementById("all");
			var registed = document.getElementById("registed");
			var not_registed = document.getElementById("not_registed");
			all.checked = true;
			registed.checked = false;
			not_registed.checked = false;
		}

		// 「結果クリア」ボタン押下時に実行
		function clearOrderList() {

			// 表示された商品名を空文字に変更
			var item_name = document.getElementById("item_name");
			item_name.innerHTML = '';

			// テーブルの要素を全削除
			var orderList_form = document.getElementById("orderList");
			orderList_form.remove();
		}
	</script>
</body>
</html>