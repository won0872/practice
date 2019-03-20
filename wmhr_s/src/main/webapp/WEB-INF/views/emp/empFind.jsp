<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
font{
	font-size: 15px;
	font-weight: bold; 
}

</style>
<script type="text/javascript">
	
	$(document).ready(function(){
		$( "#dept" ).selectmenu();
		$( "#detail_tab" ).tabs();
		$( "#findtab" ).tabs();
		
		$("#find").click(function(){
			findgrid($("#dept").val());
		})

		function findgrid(dept){
			
					//그리드 리로드
			$.jgrid.gridUnload("#findgrid");
			$('#findgrid').jqGrid({
				url:'${pageContext.request.contextPath}/emp/list.do',
				postData:{
					value:dept,
					method:"emplist"
				},
				datatype:'json',
				jsonReader:{
					root:'list'
				},
				colNames:['사원코드','사원명','부서','직급','성별','전화번호','이메일','거주지','최종학력','사진'],
				colModel:[
					{name:'empCode',width:0, editable:false},
					{name:'empName',width:100, editable:false},
					{name:'deptName',width:100, editable:false},
					{name:'position',width:100, editable:false},
					{name:'gender',width:0, editable:false},
					{name:'mobileNumber',width:0, editable:false},
					{name:'email',width:200, editable:false},
					{name:'address',width:0, editable:false},
					{name:'lastSchool',width:0, editable:false},
					{name:'imgExtend',width:0, editable:false}
				],
				width:"600px",
				height:"260px",
				viewrecords: true,
				loadError:function(xhr){
					alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
					if(xhr.status==500){
						alert("Internal Server Error");
					}
				},onSelectRow : function(data) {
					$("#fN").html($("#findgrid").getCell(data , "empName"));
					$("#fD").html($("#findgrid").getCell(data , "deptName"));
					$("#fP").html($("#findgrid").getCell(data , "position"));
					$("#fE").html($("#findgrid").getCell(data , "email"));
					$("#fM").html($("#findgrid").getCell(data , "mobileNumber"));
					$("#fB").html($("#findgrid").getCell(data , "birthdate"));
					$("#fA").html($("#findgrid").getCell(data , "address"));
					$("#fS").html($("#findgrid").getCell(data , "lastSchool"));
					var empCode = $("#findgrid").getCell(data , "empCode");
					var profile = $("#findgrid").getCell(data , "imgExtend");
					
					$("#profileImg").attr("src","/profile/"+empCode+"."+profile);
					
				}
			});
			
			 $("#findgrid").jqGrid("hideCol", ['empCode','mobileNumber','address','lastSchool','gender','imgExtend']);
			
			
		}
		
		
		 
	});
</script>

</head>
<body>
<table>
<tr>
	<td >
		
		
		
			
			<div id="findtab" style="width:600px; height: 400px;">
				<ul>
					<li><a href="#findtab1">사원검색</a></li>
				</ul>
				<div id="findtab1">
				<select id="dept">
					<option value="전체부서">전체부서</option>
					<option value="회계팀">회계팀</option>
					<option value="인사팀">인사팀</option>
					<option value="전산팀">전산팀</option>
				</select>
				<input type="button" class="ui-button ui-widget ui-corner-all" value="조회" id="find">
				<hr>
				<center><table id="findgrid"></table></center>
			</div>
			</div>
		
			

	</td>
	<td>
		
			<div id="detail_tab" style="width:600px; height: 400px;">
				<ul>
					<li><a href="#detal_tap1">상세정보</a></li>
				</ul>
				<div id="detal_tap1">
				<div style="display: inline-block;" >
					 <img id="profileImg" src="/profile/profile.png" width="200px" height="250px">
				</div>
				<div style=" display: inline-block; position:absolute; margin-left: 50px; margin-top: 30px;" >
					<font>이름 : </font> <font id="fN"></font><br/><br/>
					<font>부서 : </font> <font id="fD"></font><br/>
					<!-- <font style="font-size: 10px">전화번호 : </font> <font id="fDP" style="font-size: 10px">1001</font> <br/>--><br/>
					<font>직급 : </font> <font id="fP"></font><br/><br/> 
					<font>e-mail : </font> <font id="fE"></font><br/>
					<font>휴대전화 : </font> <font id="fM"></font><br/>
					<font>생년월일 : </font> <font id="fB"></font><br/>
					<font>거주지 : </font> <font id="fA"></font><br/>
					<font>최종학력 : </font> <font id="fS"></font><br/>
					
					<!-- IMG_EXTEND -->
					
				</div>
				</div>
			</div>
		
	</td>
</tr>
</table>
	
</body>
	
</html>