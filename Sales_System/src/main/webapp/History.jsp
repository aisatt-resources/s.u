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
<jsp:include page="common/ShoppingPage_header.jsp" />
</head>
<body>
	<% 
	HttpSession statusSession = request.getSession();
	String str = (String) statusSession.getAttribute("LoginStatus");
   if(str.equals("Logout")){%>
   <script>location.href = 'Login.jsp';</script>
   <%}%>
	<!-- ロゴの表示・検索フォーム・カートへの遷移ボタン -->
	<jsp:include page="common/navi.jsp" />
	<%
	if (request.getAttribute("message") != null) {
	%>
	<h6 style="color: red; text-align: center;">
		<%=request.getAttribute("message")%></h6>
	<%
	}
	%>
	<!-- 商品リスト -->
	<section class="py-5">
		<div class="container">
			<table class="table table-hover">
				<!-- 見出し部分 -->
				<thead class="table-dark">
					<tr>
						<th class="col-md-1">注文番号</th>
						<th class="col-md-1">注文日</th>
						<th class="col-md-2">会員ID:会員名</th>
						<th class="col-md-2">支払方法</th>
						<th class="col-md-3">税込合計金額（うち消費税）</th>
						<th class="col-md-3">備考</th>
					</tr>
				</thead>
				<!-- 売上情報部分 -->
				<!-- 売上情報をリクエストスコープから取得 -->
				<tbody id="orderList">
					<%
					List<SalesInformation> OrderList = (List<SalesInformation>) request.getAttribute("OrderList");
					HashMap<String, List<SalesInformation>> OrderDetailHashMap = (HashMap<String, List<SalesInformation>>) request
							.getAttribute("OrderDetailHashMap");
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
							
							order_count++;
					%>
					<tr class="table-light table-striped">
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
						<!-- 支払方法 -->
						<td>
							<%
							if (orderinfo.getPayment_Method() != null && orderinfo.getPayment_Method().equals("0")) {
							%> 代引き <%
							} else if (orderinfo.getPayment_Method() != null && orderinfo.getPayment_Method().equals("1")) {
							%> クレジット <%
							} else if (orderinfo.getPayment_Method() != null && orderinfo.getPayment_Method().equals("2")) {
							%> 現金 <%
							} else {
							}
							%>
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
						<!-- 備考 -->
						<td><%=orderinfo.getRemarks()%></td>
					</tr>
					<tr></tr>
					<tr>
						<td colspan="3"></td>
						<td colspan="1" style="text-align: right;">購入内訳：</td>
						<td colspan="3" class="table">
							<%
							List<SalesInformation> OrderDetail = OrderDetailHashMap.get(orderinfo.getOrder_No());
							%> <%
							int i = 0;
							for (SalesInformation order_detail_info : OrderDetail) {
							%> <!-- 注文行番号 --> <%=i + 1%>. <%i++;%> <!-- 商品名 --> <%=order_detail_info.getItem_Name()%>
							<!-- 商品コード --> [商品コード：<%=order_detail_info.getItem_Cd()%>] <!-- 単価 -->￥<%=order_detail_info.getUnit_Price()%>、
							<!-- 数量 --><%=order_detail_info.getQuantity()%>点 <!-- 小計 --> 小計：￥<%=order_detail_info.getSubtotal()%>
							<br> <%
							}
							%>
						</td>
					</tr>
					<tr>
						<td></td>
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
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/footer.jsp" />
</body>
</html>