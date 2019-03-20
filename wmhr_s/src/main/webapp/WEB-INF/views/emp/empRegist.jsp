<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	font {
		font-family: "바탕";							
		font-weight: bold;
	 }
	 input[type=text]{
	 	text-align: center;
	 	width: 300px;
	 }
	 /* td{
	 	border: 1px solid green;
	 } */
</style>
<script>
	$(document).ready(function(){
		$("#txt_dept").click(function(){
			getCode('CO-07',"txt_dept","deptCode");
		});
		$("#txt_posi").click(function(){
			getCode('CO-04',"txt_posi","position");
		});
		$("#txt_gend").click(function(){
			getCode('CO-01',"txt_gend","gender");
		});
		$("#txt_lasc").click(function(){
			getCode('CO-02',"txt_lasc","last_shcool");
		});
		$("#btn_crenum").click(function(){
			creatEmpCode();
		});
		
		/* 등록시 이벤트 */
		$("#regist_btn").click(function(){
			 $("#regist_form").submit(); 
		});
		
		
		
		/*  tab 구현 */
		$( "#registTab" ).tabs();
	
		/* 주소 검색 버튼 */
		$(function() { $("#addr_btn").postcodifyPopUp(); });
		
		/* 사진찾기 버튼 */
		$("#findPhoto").button().click(function(){
		    $("#emp_img_file").click(); //사진찾기 버튼을 누르면 숨겨진 file 태그를 클릭
		});
		
		
	});
	
	/* 부서,직급 선택창 띄우기 */
	function getCode(code,inputText,inputCode){
		option="width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open("${pageContext.request.contextPath}/base/codeWindow.html?code="
						+code+"&inputText="+inputText+"&inputCode="+inputCode,"newwins",option);
	}
	
	/* 달력 띄우기 */
	$(function(){
		$("#txt_birth").datepicker({
			changeMonth : true,
			changeYear : true,
			showOn:"button",
			buttonImage:"${pageContext.request.contextPath}/image/cal.png",
			buttonImageOnly:true,
			dateFormat : "yy/mm/dd",
			yearRange: "1900:2030",
		})
	})
	
	/* 사번생성 */
	   function creatEmpCode(){
			$.ajax({
				url : "${pageContext.request.contextPath}/emp/empRegist.do",
				data:{
					"method":"findLastEmpCode"
				},
				dataType:'json',
				success:function(data){
					if(data.errorCode < 0){
						var str = "내부 서버 오류가 발생했습니다\n";
						str += "관리자에게 문의하세요\n";
						str += "에러 위치 : " + $(this).attr("id");
						str += "에러 메시지 : " + data.errorMsg;
						alert(str);
						return;
					}
		
					var lastEmpCode = data.lastEmpCode;
					var lastNumber = lastEmpCode.substring(3);
					var newCode = "A" + ((560000)+(Number(lastNumber)+1));
					$("#txt_code").val(newCode);
					$("#emp_img_empCode").val(newCode);
				}
			});
		}
	
	/* 사진찾기 버튼 눌렀을 때 실행되는 함수 */
	function readURL(input){
		var imgEx = "";
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function (e) {
					// 이미지 Tag의 SRC속성에 읽어들인 File내용을 지정 (아래 코드에서 읽어들인 dataURL형식)
					$("#emp_img").attr("src", e.target.result);
				}
			reader.readAsDataURL(input.files[0]); //File내용을 읽어 dataURL형식의 문자열로 저장
			}
			imgEx = $("#emp_img_file").val();
			$("#img_extend").val(imgEx.split(".")[1]);
	}
	
	
	
	
</script>

<script> 
	
</script>
</head>
<body>
<div style="width:500px; margin-top: 50px;">
	
	
	
	
	<div id="registTab">
	
	
	
	<ul>
		<li><a href="#registTab1">사원등록</a></li>
	</ul>
	<br>
	
	<div id="registTab1">
	<form id="regist_form" action="<%=request.getContextPath()%>/emp/empRegist.do" method="post">
	<input type="hidden" name="method" value="registEmployee">
	<hr>
	<table style="text-align: center">
	<tr><td><font>사원번호</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_code' type="text" name="emp_code" style="width:195px">
	<input type="button" class="ui-button ui-widget ui-corner-all" id='btn_crenum' value="사번 생성"></td></tr>
	<tr><td><font>이름</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_name' type="text" name="emp_name"></td></tr>
	<tr><td><font>부서</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_dept' type="text" name="dept_name" readonly></td></tr>
	<tr><td><font>직급</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_posi' type="text" name="position" readonly></td></tr>	
	<tr><td><font>성별</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_gend' type="text" name="gender" readonly></td></tr>
	<tr><td><font>생년월일</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_birth' type="text" name="birthday" style="width:275px"readonly>
	</td></tr>
	<tr><td><font>주소</font></td><td><input id='txt_addr' class="postcodify_address ui-button ui-widget ui-corner-all" type="text" name="address" style="width:230px;"  readonly>
	<input type="button" class="ui-button ui-widget ui-corner-all" id="addr_btn" value="검색" ></td></tr>
	<tr><td><font>상세주소</font></td><td><input id='txt_daddr' class="ui-button ui-widget ui-corner-all postcodify_details" type="text" name="detail_address"></td></tr>
	<tr><td><font>우편번호</font></td><td><input class="ui-button ui-widget ui-corner-all postcodify_postcode5" id='txt_pnum' type="text" name="post_number" readonly></td></tr>
	<tr><td><font>전화번호</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_mnum' type="text" name="mobile_number"></td></tr>
	<tr><td><font>이메일</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_emain' type="text" name="email"></td></tr>
	<tr><td><font>최종학력</font></td><td><input class="ui-button ui-widget ui-corner-all" id='txt_lasc' type="text" name="last_school"></td></tr>
	</table>
	<input type="hidden" name="deptCode" id="deptCode">
	<input type="hidden" name="position" id="position">
	<input type="hidden" name="img_extend" id="img_extend">
	<input type="button" id="regist_btn" class="ui-button ui-widget ui-corner-all" value="가입">
	<input type="reset"  class="ui-button ui-widget ui-corner-all" value="취소">
	</form>
	</div>
	
	</div>
	
	
</div>
</body>
</html>