<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<!-- By Designscrazed.com , just a structure for easy usage. -->

<html lang='en'>
<head>
<meta charset="UTF-8" />
<title>MStore</title>

<link rel="stylesheet" type="text/css" href="css/confirm_style.css" />
<link rel="icon" type="image/png" href="img/favicon.png" />
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,700,600'
	rel='stylesheet' type='text/css'>

</head>


<body>
	<div id="wrap">
		<div id="accordian">
			<form action="Controller" method="post">
			<input type="hidden" name="command" value="to-basket" />
			<button type="submit">Back</button>
			</form>	

			<div class="step" id="step4">
				<div class="number">
					<span>1</span>
				</div>
				<div class="title">
					<h1>Payment Information</h1>
				</div>
			</div>
			<div class="content" id="payment">
				<div class="left credit_card">
					<form class="go-right" id="confirm" action="Controller"
						method="post">
						<input type="hidden" name="command" value="make-order" />
						<div>
							<input required type="text" name="card_number" value=""  id="card_number"
								 pattern="[0-9]{16}" /><label
								for="card_number">Card Number</label>
						</div>
						<div>
							<div class="expiry">
								<div class="month_select">
									<select name="exp_month" value="" id="exp_month" placeholder=""
										required>
										<option value="1">01 (Jan)</option>
										<option value="2">02 (Feb)</option>
										<option value="3">03 (Mar)</option>
										<option value="4">04 (Apr)</option>
										<option value="5">05 (May)</option>
										<option value="6">06 (Jun)</option>
										<option value="7">07 (Jul)</option>
										<option value="8">08 (Aug)</option>
										<option value="9">09 (Sep)</option>
										<option value="10">10 (Oct)</option>
										<option value="11">11 (Nov)</option>
										<option value="12">12 (Dec)</option>
									</select>
								</div>
								<span class="divider">-</span>
								<div class="year_select">
									<select name="exp_year" value="" id="exp_year" placeholder=""
										required>
										<option value="3">16</option>
										<option value="4">17</option>
										<option value="5">18</option>
										<option value="6">19</option>
										<option value="7">20</option>
										<option value="8">21</option>
										<option value="9">22</option>
										<option value="10">23</option>
										<option value="11">24</option>
										<option value="12">25</option>
									</select>
								</div>
							</div>
							<label class="exp_date" for="Exp_Date">Exp Date</label>
						</div>
						<div class="sec_num">
							<div>
								<input type="text" name="ccv" value="" id="ccv"
									pattern="[0-9]{3}" required /> <label
									for="ccv">Security Code</label>
							</div>
						</div>
					</form>
				</div>
				<div class="right">
					<div class="accepted">
						<span><img src="img/Z5HVIOt.png"></span> <span><img
							src="img/Le0Vvgx.png"></span> <span><img
							src="img/D2eQTim.png"></span> <span><img
							src="img/Pu4e7AT.png"></span> <span><img
							src="img/ewMjaHv.png"></span> <span><img
							src="img/3LmmFFV.png"></span>
					</div>
				</div>


			</div>
			<div class="step" id="step5">
				<div class="number">
					<span>2</span>
				</div>
				<div class="title">
					<h1>Finalize Order</h1>
				</div>
			</div>
			<div class="content" id="final_products">
				<div class="left" id="ordered">
					<c:forEach var="basket" items="${basket}">
						<div class="products">
							<div class="product_image">
								<img src="img/albums/${basket.image}" />
							</div>
							<div class="product_details">
								<span class="product_name">${basket.name}</span> <span
									class="price">${basket.price}</span>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="right" id="reviewed">

					<div id="complete">
						<button type="submit" form="confirm" class="big_button"
							id="complete">Complete Order</button>
						<span class="sub">By selecting this button you agree to the
							purchase and subsequent payment for this order.</span>

					</div>


				</div>
			</div>
</body>
</html>
