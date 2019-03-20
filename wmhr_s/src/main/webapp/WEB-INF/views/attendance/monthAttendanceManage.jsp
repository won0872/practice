<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="width: 1579px; ">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>월근태관리</title>
<style type="text/css">
input[type=text] {
	width: 150px;
	height: 10px;
}

</style>
<script>
var monthAttdMgtList = [];

	$(document).ready(function(){

	    showMonthAttdMgtListGrid();
	    
		$("input:text").button();
		$(".small_Btn").button();

		$(".comment_div").css({fontSize : "0.7em"});

		$("#monthAttendance_tabs").tabs().css({margin:"10px"});

		$("#search_monthAttdMgtList_Btn").click(findMonthAttdMgtList);
		$("#finalize_monthAttdMgt_Btn").click(finalizeMonthAttdMgt);
		$("#cancel_monthAttdMgt_Btn").click(cancelMonthAttdMgt);
		
		$("#searchYearMonth").selectmenu();
		var year = (new Date()).getFullYear();
		for(var i=1; i<=12; i++)
			$("#searchYearMonth").append($("<option />").val(year+"-"+i).text(year+"년 "+i+"월"));
	});

	/* 월근태관리 목록 조회버튼 함수 */
	function findMonthAttdMgtList(){
		if($("#searchYearMonth").val()==""){
			alert("적용연월을 선택해 주세요");
			return;
		}

		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data:{
				"method" : "findMonthAttdMgtList",
				"applyYearMonth" : $("#searchYearMonth").val()
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

				monthAttdMgtList = data.monthAttdMgtList;

 				for(var index in monthAttdMgtList){
 					monthAttdMgtList[index].workHour = getRealKrTime(monthAttdMgtList[index].workHour);
 					monthAttdMgtList[index].overWorkHour = getRealKrTime(monthAttdMgtList[index].overWorkHour);
 					monthAttdMgtList[index].nightWorkHour = getRealKrTime(monthAttdMgtList[index].nightWorkHour);
 					monthAttdMgtList[index].holidayWorkHour = getRealKrTime(monthAttdMgtList[index].holidayWorkHour);
 					monthAttdMgtList[index].holidayOverWorkHour = getRealKrTime(monthAttdMgtList[index].holidayOverWorkHour);
 					monthAttdMgtList[index].holidayNightWorkHour = getRealKrTime(monthAttdMgtList[index].holidayNightWorkHour);
				}
				
				showMonthAttdMgtListGrid();
			}
		});
	}

    /* 월근태관리 목록 그리드 띄우는 함수 */
    function showMonthAttdMgtListGrid(){
    	$.jgrid.gridUnload("#monthAttdMgtList_grid");
    	$("#monthAttdMgtList_grid").jqGrid({
    		data : monthAttdMgtList,
    		datatype : "local",
    		colNames : ['사원코드','사원명','적용연월','기준근무일수','평일근무일수','기준근무시간','실제근무시간',
    			'연장근무시간','심야근무시간','휴일근무일수','휴일근무시간','지각일수',
    			'결근일수','반차일수','휴가일수','마감여부','상태'],
			colModel : [ {
				name : 'empCode',width : 75},{
				name : 'empName',width : 60}, {
				name : 'applyYearMonth',width : 75}, {
				name : 'basicWorkDays',width : 75}, {
				name : 'weekdayWorkDays',width : 75}, {
				name : 'basicWorkHour',width : 75}, {
				name : 'workHour',width : 80}, {
				name : 'overWorkHour',width : 80}, {
				name : 'nightWorkHour',width : 80}, {
				name : 'holidayWorkDays',width : 65}, {
				name : 'holidayWorkHour',width : 85}, {
				name : 'lateDays',width : 55}, {
				name : 'absentDays',width : 55}, {
				name : 'halfHolidays',width : 55}, {
				name : 'holidays',width : 55}, {
				name : 'finalizeStatus',width : 55}, {
				name : 'status',hidden : true} ],
			width:1420,
			height : 300,
			viewrecords : true,
			editurl : "clientArray",
			cellsubmit : "clientArray",
			rowNum : 10,
			pager : "#monthAttdMgtList_pager"
    	});
    }

    /* 마감처리 함수 */
    function finalizeMonthAttdMgt(){
		for(var index in monthAttdMgtList){
			monthAttdMgtList[index].finalizeStatus = "Y";
			monthAttdMgtList[index].status = "update";
		}

		var sendData = JSON.stringify(monthAttdMgtList);

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data : {
				"method" : "modifyMonthAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("마감을 실패했습니다");
				} else {
					alert("마감처리 되었습니다");
				}
				location.reload();
			}
		});
	}

    /* 마감취소 함수 */
    function cancelMonthAttdMgt(){
		for(var index in monthAttdMgtList){
			monthAttdMgtList[index].finalizeStatus = "N";
			monthAttdMgtList[index].status = "update";
		}

		var sendData = JSON.stringify(monthAttdMgtList);

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data : {
				"method" : "modifyMonthAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("마감취소를 실패했습니다");
				} else {
					alert("마감취소처리 되었습니다");
				}
				location.reload();
			}
		});
	}

	/* 0000단위인 시간을 (00시간00분) 형식으로 변환하는 함수 */
	function getRealKrTime(time){
		var hour = Math.floor(time/100);
		if(hour==25) hour=1;
		var min = time-(Math.floor(time/100)*100);
		if(min==0) min="00";
		if(min==50) min=30;
		return hour + "시간" + min + "분";
	}
</script>
</head>
<body>
	<div id="monthAttendance_tabs" >
		<ul>
			<li>
				<a href="#monthAttendance_tab">월근태관리</a>
			</li>
		</ul>
		<div id="monthAttendance_tab">
			조회월 <select id="searchYearMonth"></select>
			<button class="ui-button ui-widget ui-corner-all" id="search_monthAttdMgtList_Btn">조회하기</button>
			<br />
			<br />
			<button class="ui-button ui-widget ui-corner-all" id="finalize_monthAttdMgt_Btn">전체마감하기</button>
			<button class="ui-button ui-widget ui-corner-all" id="cancel_monthAttdMgt_Btn">전체마감취소</button>
			<br />
			<br />
			<table id="monthAttdMgtList_grid"></table>
			<div id="monthAttdMgtList_pager"></div>
		</div>
	</div>
</body>
</html>