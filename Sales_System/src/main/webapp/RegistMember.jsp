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
	<!-- 商品リスト -->
	<section class="py-5">
		<p>
			<h3 style="text-align: center;">＊＊＊会員登録＊＊＊</h3>
		</p>
		<%
		if (request.getAttribute("message") != null) {
		%>
		
		<h2 style="text-align: center; color: red;">
			<%=request.getAttribute("message")%>
		</h2>
		<%
		} else {
		%>
		<div class="container">
			<form action="RegistMemberServlet" method="post">
				<div class="row">
					<div class="row justify-content-center">
						<div class="col-md-6" style="color: red; text-align: left;">
							*は必須</div>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">ユーザー名 <span
								style="color: red;">*</label>
						</div>
						<div class="col-md-4 form-group">
							<input type="text" name="user_name" required
								placeholder="ユーザー名を入力" minlength="1" maxlength="10"
								class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">メールアドレス</label>
						</div>
						<div class="col-md-4 form-group">
							<input type="text" name="email" placeholder="メールアドレスを入力"
								maxlength="255" class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">電話番号 <span
								style="color: red;">*</span></label>
						</div>
						<div class="col-md-4 form-group">
							<input type="text" name="tel" required placeholder="電話番号を入力"
								pattern="0[1-9]0[0-9]{8}|0[1-9]{3}[0-9]{6}"
								oninput="value = NumberOnly(value)" title="ハイフンなしで入力してください。"
								class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">住所</label>
						</div>
						<div class="col-md-4 form-group">
							<input type="text" name="address" placeholder="住所を入力"
								maxlength="50" class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">生年月日</label>
						</div>
						<div class="col-md-4 form-group">
							<input type="date" name="birthday" class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">職業</label>
						</div>
						<div class="col-md-4 form-group">
							<input type="text" name="job" placeholder="職業を入力" maxlength="50"
								class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-2 form-group">
							<label class="form-control-label">パスワード <span
								style="color: red;">*</span></label>
						</div>
						<div class="col-md-4 form-group">
							<input type="password" name="password" required="required"
								minlength="5" maxlength="10" class="form-control">
						</div>
						</p>
					</div>
					<div class="row justify-content-center">
						<p>
						
						<div class="col-md-1">
							<button class="btn btn-primary btn-form display-4" type="submit">登録</button>
						</div>
						</p>
					</div>
				</div>
			</form>
			<%
			}
			%>
		</div>
	</section>
	<!-- 共通パーツ（フッター） -->
	<jsp:include page="common/footer.jsp" />
</body>
</html>