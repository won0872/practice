<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일근태관리</title>
<style type="text/css">
input[type=text] {
	width: 150px;
	height: 10px;
	z-index: 100;
}

table{
	z-index: 90;
}     


</style>
<script>
var dayAttdMgtList = [];

	$(document).ready(function(){

		$(".small_Btn").button();

		$(".comment_div").css({fontSize : "0.7em"});

		$("#dayAttendance_tabs").tabs().css({
			margin:"10px"
		});

		getDatePicker($("#searchDay"));
		$("#search_dayAttdMgtList_Btn").click(findDayAttdMgtList);
		$("#finalize_dayAttdMgt_Btn").click(finalizeDayAttdMgt);
		$("#cancel_dayAttdMgt_Btn").click(cancelDayAttdMgt);
		showDayAttdMgtListGrid();
	});

	

	/* 일근태관리 목록 조회버튼 함수 */
	function findDayAttdMgtList(){
		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/dayAttendanceManage.do",
			data:{
				"method" : "findDayAttdMgtList",
				"applyDay" : $("#searchDay").val(),
				"dept" : "${sessionScope.dept}"
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

				dayAttdMgtList = data.dayAttdMgtList;

 				for(var index in dayAttdMgtList){
					dayAttdMgtList[index].attendTime = getRealTime(dayAttdMgtList[index].attendTime);
					dayAttdMgtList[index].quitTime = getRealTime(dayAttdMgtList[index].quitTime);
					dayAttdMgtList[index].lateHour = getRealKrTime(dayAttdMgtList[index].lateHour);
					dayAttdMgtList[index].leaveHour = getRealKrTime(dayAttdMgtList[index].leaveHour);
					dayAttdMgtList[index].workHour = getRealKrTime(dayAttdMgtList[index].workHour);
					dayAttdMgtList[index].overWorkHour = getRealKrTime(dayAttdMgtList[index].overWorkHour);
					dayAttdMgtList[index].nightWorkHour = getRealKrTime(dayAttdMgtList[index].nightWorkHour);
				}

				showDayAttdMgtListGrid();
			}
		});
	}

	
    /* 일근태관리 목록 그리드 띄우는 함수 */
    function showDayAttdMgtListGrid(){
    	$.jgrid.gridUnload("#dayAttdMgtList_grid");
    	$("#dayAttdMgtList_grid").jqGrid({
    		data : dayAttdMgtList,
    		datatype : "local",
    		colNames : ['사원코드','사원명','적용일','일근태구분코드','일근태구분명','출근시각','퇴근시각',
    			'지각여부','외출시간','정상근무시간','연장근무시간','심야근무시간','마감여부','상태'],
			colModel : [ {
				name : 'empCode',width : 30},{
				name : 'empName',width : 30}, {
				name : 'applyDays',width : 40}, {
				name : 'dayAttdCode',hidden : true}, {
				name : 'dayAttdName',width : 40}, {
				name : 'attendTime',width : 30}, {
				name : 'quitTime',width : 30}, {
				name : 'lateWhether',width : 30}, {
				name : 'leaveHour',width : 30}, {
				name : 'workHour',width : 40}, {
				name : 'overWorkHour',width : 40}, {
				name : 'nightWorkHour',width : 40}, {
				name : 'finalizeStatus',width : 30}, {
				name : 'status',hidden : true} ],
			width : 1100,
			height : 300,
			viewrecords : true,
			autowidth:true,
			editurl : "clientArray",
			cellsubmit : "clientArray",
			rowNum : 10,
			pager : "#dayAttdMgtList_pager"
    	});
    }

    /* 마감처리 함수 */
    function finalizeDayAttdMgt(){
	    var dayAttdMonthData = 0; // 일근태관리의 월 데이터 
	    var monthAttdFinalizeStatus = 0; // 해당 일근태 관리의 월에 따른 월근태관리의 마감 여부 
	    $("#dayAttdMgtList_grid").find("tr:not(:first)").each(function(i, element) {
		    var dateData = $(this).find("td").eq(2).text();
		    dayAttdMonthData = dateData.substring(0,dateData.lastIndexOf("-"));
		    if(dateData.substring(5,6)==0) // 월에 0이 들어가 있다면 
				dayAttdMonthData = replaceLast(dayAttdMonthData,0,"") // 0을 제거한다 
				
	    })
	    
		$.ajax({ // 월근태 목록을 가져온다
			url:"${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data:{
				"method" : "findMonthAttdMgtList",
				"applyYearMonth" : dayAttdMonthData
			},
			dataType:"json",
			async: false, // 동기처리를 하지 않으면 전역 변수 monthAttdFinalizeStatus에 값이 할당되기 전에 취소 작업이 일어남
			success : function(data){
				if(data.errorCode < 0){
					var str = "내부 서버 오류가 발생했습니다\n";
					str += "관리자에게 문의하세요\n";
					str += "에러 위치 : " + $(this).attr("id");
					str += "에러 메시지 : " + data.errorMsg;
					alert(str);
					return;
				}
	
				$.each(data.monthAttdMgtList, function(i, elt) {
				    monthAttdFinalizeStatus = elt.finalizeStatus;
				    return false;
				})
				
			}
		});
 		
	 	if(monthAttdFinalizeStatus=="N"){ // 월근태 관리의 마감 상태가 N과 같다면 일근태 마감 취소 작업을 한다
	 		for(var index in dayAttdMgtList){
				dayAttdMgtList[index].finalizeStatus = "Y";
				dayAttdMgtList[index].status = "update";
			}

			var sendData = JSON.stringify(dayAttdMgtList);

			$.ajax({
				url : "${pageContext.request.contextPath}/attendance/dayAttendanceManage.do",
				data : {
					"method" : "modifyDayAttdList",
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
	 	}else if(monthAttdFinalizeStatus=="Y"){
			alert("해당 월의 근태관리가 마감되었습니다\n데이터의 마감을 하시고자 하는 경우에는\n해당 월의 근태관리 마감을 취소하시고 시도해주세요")
		}
	}

    /* 마감취소 함수 */
    function cancelDayAttdMgt(){
	    var dayAttdMonthData = 0; // 일근태관리의 월 데이터 
	    var monthAttdFinalizeStatus = 0; // 해당 일근태 관리의 월에 따른 월근태관리의 마감 여부 
	    $("#dayAttdMgtList_grid").find("tr:not(:first)").each(function(i, element) {
		    var dateData = $(this).find("td").eq(2).text();
		    dayAttdMonthData = dateData.substring(0,dateData.lastIndexOf("-"));
		    if(dateData.substring(5,6)==0) // 월에 0이 들어가 있다면 
				dayAttdMonthData = replaceLast(dayAttdMonthData,0,"") // 0을 제거한다 
				
	    })
	  
		$.ajax({ // 월근태 목록을 가져온다
			url:"${pageContext.request.contextPath}/attendance/monthAttendanceManage.do",
			data:{
				"method" : "findMonthAttdMgtList",
				"applyYearMonth" : dayAttdMonthData
			},
			dataType:"json",
			async: false, // 동기처리를 하지 않으면 전역 변수 monthAttdFinalizeStatus에 값이 할당되기 전에 취소 작업이 일어남
			success : function(data){
				if(data.errorCode < 0){
					var str = "내부 서버 오류가 발생했습니다\n";
					str += "관리자에게 문의하세요\n";
					str += "에러 위치 : " + $(this).attr("id");
					str += "에러 메시지 : " + data.errorMsg;
					alert(str);
					return;
				}
	
				$.each(data.monthAttdMgtList, function(i, elt) {
				    monthAttdFinalizeStatus = elt.finalizeStatus;
				    return false;
				})
				
			}
		});
 
	 	if(monthAttdFinalizeStatus=="N"){ //월근태 관리의 마감 상태가 N과 같다면 일근태 마감 취소 작업을 한다 
		 	for(var index in dayAttdMgtList){
				dayAttdMgtList[index].finalizeStatus = "N";
				dayAttdMgtList[index].status = "update";
			}
	
			var sendData = JSON.stringify(dayAttdMgtList);
	
			$.ajax({
				url : "${pageContext.request.contextPath}/attendance/dayAttendanceManage.do",
				data : {
					"method" : "modifyDayAttdList",
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
	 	}else if(monthAttdFinalizeStatus=="Y"){
			alert("해당 월의 근태관리가 마감되었습니다\n데이터의 마감을 취소하시고자 하는 경우에는\n해당 월의 근태관리 마감을 취소하시고 시도해주세요")
		}
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

	/* 0000단위인 시간을 (00시간00분) 형식으로 변환하는 함수 */
	function getRealKrTime(time){
		var hour = Math.floor(time/100);
		if(hour==25) hour=1;
		var min = time-(Math.floor(time/100)*100);
		if(min==0) min="00";
		return hour + "시간" + min + "분";
	}

	/* 날짜 조회창 함수 */
	function getDatePicker($Obj, maxDate) {
			$Obj.datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
				monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
				yearRange: "1980:2020",
				maxDate : (maxDate==null? "+100Y" : maxDate)
			});
	}

	/* replacefirst는 있는데 last가 없어서 구현 */
	function replaceLast(str, regex, replacement) {
	        var regexIndexOf = str.lastIndexOf(regex);
	        if(regexIndexOf == -1){
	            return str;
	        }else{
		        /* 넘어오는 regex가 number타입이기 때문에 length가 안먹힘 그래서 toString으로 문자열로 변경후 사용 */
	            return str.substring(0, regexIndexOf) + replacement + str.substring(regexIndexOf + regex.toString().length);
	        }
	}

</script>
</head>
<body>
	<div id="dayAttendance_tabs">
		<ul>
			<li>
				<a href="#dayAttendance_tab">일근태관리</a>
			</li>
		</ul>
		<div id="dayAttendance_tab">
			조회일자 <input type=text id="searchDay" class="small_Btn" readonly>
			<button class="small_Btn" id="search_dayAttdMgtList_Btn">조회하기</button>
			<br />
			<br />
			<button class="small_Btn" id="finalize_dayAttdMgt_Btn">전체마감하기</button>
			<button class="small_Btn" id="cancel_dayAttdMgt_Btn">전체마감취소</button>
			<br />
			<br />
			<table id="dayAttdMgtList_grid"></table>
			<div id="dayAttdMgtList_pager"></div>
		</div>
	</div>
</body>
</html>