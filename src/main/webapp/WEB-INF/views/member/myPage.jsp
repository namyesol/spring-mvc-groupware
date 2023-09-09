<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

function phoneNumber() {
    var textBox = document.createElement("input");
    textBox.setAttribute("type", "text");
    textBox.setAttribute("placeholder", "새로운 전화번호를 입력하세요");
    document.getElementById("textToChange").innerHTML = '';
    document.getElementById("textToChange").appendChild(textBox);
}

</script>

<form action="/loginCheck/myPage">
	<h3>나의 정보</h3>
	<div class="mypage-section">
		<div class="mypage-image">
			<img src="./memberphoto/${ dto.getMember_num() }.png" width="300" height="300">
			<br><br>
			<a>프로필사진 변경</a><br> 
			<a>비밀번호 변경</a><br>
		</div>
		<div class="mypage-profile">
			<div class="horizontal-container">
				<h2 class="member-name">${ dto.getMember_name() }</h2>
				&nbsp; <span class="rank">${ dto.getRank() }</span>
			</div>
			사번&nbsp;&nbsp;${ dto.getMember_num() }<br> 
			소속부서&nbsp;&nbsp;${ dto.getDiv_name() }<br>
			주소&nbsp;&nbsp;${ dto.getAddress() }<br> 
			휴대폰번호&nbsp;&nbsp;<span id="textToChange">${ dto.getPhone() }</span>&nbsp;
				<a href="#" onclick="phoneNumber()">수정</a><br> 
			메일주소&nbsp;&nbsp;${ dto.getMail() }&nbsp;<a href="#">수정</a><br> 
			입사일&nbsp;&nbsp;${ dto.getHire_date() }<br> 
			잔여연차 : ${ dto.getAnnual_leave() }<br>
		</div>
	</div>
</form>
