<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>근태외승인</title>
<style type="text/css">
input[type=text] {
	width: 150px;
	height: 10px;
}

.small_Btn {
	width: auto;
	height: auto;
	font-size: 15px;
}

input{
	z-index: 100;
}
table{
	z-index: 90;
}
</style>
<script>
var restAttdList = [];

	$(document).ready(function(){

		$("input:text").button();
		$(".small_Btn").button();

		$(".comment_div").css({fontSize : "0.7em"});
		$("#attdList_tabs").tabs().css({margin:"10px"});

		getDatePicker($("#search_restAttd_startDate"));
		getDatePicker($("#search_restAttd_endDate"));
		
		showRestAttdListGrid(); // 근태외신청목록 그리드

		findRestAttdList("모든부서"); //신청일자가 오늘인 근태외신청을 바로 보여준다
		
		$("#search_restAttd_deptName").click(function(){
			getCode("CO-07","search_restAttd_deptName","search_restAttd_deptCode");
		}); // 부서선택
		
		$("#search_restAttd_startDate").change(function(){
			$("#search_restAttd_endDate").datepicker("option","minDate",$(this).val());
		}); // 시작일
		
		$("#search_restAttd_endDate").change(function(){
			$("#search_restAttd_startDate").datepicker("option","maxDate",$(this).val());
		}); // 종료일
		
		$("#search_restAttdList_Btn").click(findRestAttdList); // 조회버튼
		$("#apploval_restAttd_Btn").click(applovalRestAttd); // 승인버튼
		$("#cancel_restAttd_Btn").click(cancelRestAttd); // 승인취소버튼
		$("#reject_restAttd_Btn").click(rejectRestAttd); // 반려버튼
		$("#update_restAttd_Btn").click(modifyRestAttd); // 확정버튼
	});

    /* 근태외신청 그리드 띄우는 함수 */
    function showRestAttdListGrid(){
    	$.jgrid.gridUnload("#restAttdList_grid");
    	$("#restAttdList_grid").jqGrid({
    		data : restAttdList,
    		datatype : "local",
    		colNames : ['사원코드','사원명','일련번호','근태외구분코드','근태외구분','신청일자','시작일',
        				'종료일','시작시간','종료시간','일수','경비','사유','승인여부','반려사유','상태'],
			colModel : [ {
				name : 'empCode',hidden : true},{
				name : 'empName',width : 40}, {
				name : 'restAttdCode',hidden : true}, {
				name : 'restTypeCode',hidden : true}, {
				name : 'restTypeName',width : 40}, {
				name : 'requestDate',width : 50}, {
				name : 'startDate',width : 50}, {
				name : 'endDate',width : 50}, {
				name : 'startTime',width : 30}, {
				name : 'endTime',width : 30}, {
				name : 'numberOfDays',width : 20}, {
				name : 'cost',width : 50}, {
				name : 'cause',width : 80}, {
				name : 'applovalStatus',width : 40}, {
				name : 'rejectCause',editable : true,width : 80}, {
				name : 'status',hidden : true} ],
			width : 900,
			height : 300,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			cellEdit : true,
			autowidth:true,
			editurl : "clientArray",
			cellsubmit : "clientArray",
			rowNum : 12,
			pager : "#restAttdList_pager"
    	});
    }

	/* 근태외목록 조회버튼 함수 */
	function findRestAttdList(allDept){
	    var deptName = $("#search_restAttd_deptName").val();
	    var startDate = $("#search_restAttd_startDate").val();
	  
		if(allDept=="모든부서"){ //넘어온값이 '모든부서'와 같다면 
		    deptName = allDept; //deptName를 모든부서로 바꾸고
		  
		    var today = new Date();
		    var rrrr = today.getFullYear();
		    var mm = today.getMonth()+1;
		    var dd = today.getDate();
		    startDate= rrrr+"-"+mm+"-"+dd; //시작일을 오늘날짜로 넘긴다
		}
		
		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/attendanceApploval.do",
			data:{
				"method" : "findRestAttdListByDept",
				"deptName" : deptName,
				"startDate" : startDate,
				"endDate" : $("#search_restAttd_endDate").val()
			},
			dataType:"json",
			success : function(data){
				if(data.errorCode < 0){
					var str = "내부 서버 오류가 발생했습니다\n";
					str += "관리자에게 문의하세요\n";
					str += "에러 위치 : " + $(this).attr("id");
					str += "에러 메시지 : " + data.errorMsg;
					alert(str);
					return;
				}

				restAttdList = data.restAttdList;
				
				for(var index in restAttdList){
					restAttdList[index].time = getRealTime(restAttdList[index].time);
				}

				showRestAttdListGrid();
			}
		});
	}
	
	/* 근태외 확정버튼 눌렀을 때 실행되는 함수 */
	function modifyRestAttd(){
		var sendData = JSON.stringify(restAttdList);

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/attendanceApploval.do",
			data : {
				"method" : "modifyRestAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("변경에 실패했습니다");
				} else {
					alert("확정되었습니다");
				}
				location.reload();
			}
		});
	}

	/* 근태외 승인버튼 함수 */
	function applovalRestAttd(){
		var selectedRowIds = $("#restAttdList_grid").getGridParam("selarrrow");
		if(selectedRowIds.length == 0){
			alert("승인할 근태외 항목을 선택해 주세요");
			return;
		}

		$.each(selectedRowIds, function(index, id){
			$("#restAttdList_grid").setCell(id, "rejectCause", "");
			$("#restAttdList_grid").setCell(id, "applovalStatus", "승인");
			$("#restAttdList_grid").setCell(id, "status", "update");
		});
	}

	/* 근태외 승인취소버튼 함수 */
	function cancelRestAttd(){
		var selectedRowIds = $("#restAttdList_grid").getGridParam("selarrrow");
		if(selectedRowIds.length == 0){
			alert("취소할 근태외 항목을 선택해 주세요");
			return;
		}

		$.each(selectedRowIds, function(index, id){
			$("#restAttdList_grid").setCell(id, "applovalStatus", "취소");
			$("#restAttdList_grid").setCell(id, "status", "update");
		});
	}

	/* 근태외 반려버튼 함수 */
	function rejectRestAttd(){
		var selectedRowIds = $("#restAttdList_grid").getGridParam("selarrrow");
		if(selectedRowIds.length == 0){
			alert("반려할 근태외 항목을 선택해 주세요");
			return;
		}

		$.each(selectedRowIds, function(index, id){
			$("#restAttdList_grid").setCell(id, "applovalStatus", "반려");
			$("#restAttdList_grid").setCell(id, "status", "update");
		});
	}

	/* 0000단위인 시간을 00:00(실제시간)으로 변환하는 함수 */
	function getRealTime(time){
		var hour = Math.floor(time/100);
		if(hour==25) hour=1; //데이터 베이스에 넘길때는 25시로 받고나서 grid에 표시하는건 1시로
		var min = time-(Math.floor(time/100)*100);
		if(min.toString().length==1) min="0"+min; //분이 1자리라면 앞에0을 붙임
		if(min==0) min="00";
		return hour + ":" + min;
	}

	/* 날짜 조회창 함수 */
	function getDatePicker($Obj, maxDate) {
			$Obj.datepicker({
				defaultDate : "d",
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
				monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
				yearRange: "1980:2020",
				showOptions: "up",
				maxDate : (maxDate==null? "+100Y" : maxDate)
			});
	}

	/* 코드 선택창 띄우는 함수 */
	  function getCode(code,inputText,inputCode){
		option="width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open("${pageContext.request.contextPath}/base/codeWindow.html?code="
						+code+"&inputText="+inputText+"&inputCode="+inputCode,"newwins",option);
}
</script>
</head>
<body>
	<div id="attdList_tabs">
		<ul>
			<li><a href="#restAttdList_tab">근태외신청목록</a></li>
		</ul>
		<div id="restAttdList_tab">
			조회부서 <input type=text id="search_restAttd_deptName" readonly>
			<input type=hidden id="search_restAttd_deptCode">
			신청일자 <input type=text id="search_restAttd_startDate" readonly>
			~ <input type=text id="search_restAttd_endDate" readonly>
			<button class="small_Btn" id="search_restAttdList_Btn">조회하기</button><br />
			<br />
			<br /><br />
			<button class="small_Btn" id="apploval_restAttd_Btn">승인</button>
			<button class="small_Btn" id="cancel_restAttd_Btn">승인취소</button>
			<button class="small_Btn" id="reject_restAttd_Btn">반려</button>
			<button class="small_Btn" id="update_restAttd_Btn">확정</button>
			<br /><br /><br />
			<table id="restAttdList_grid"></table>
			<div id="restAttdList_pager"></div>
		</div>
	</div>
</body>
</html>