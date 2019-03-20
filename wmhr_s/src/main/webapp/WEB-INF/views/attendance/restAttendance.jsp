<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#restAttd_tabs {
	display: inline-block;
}/* 
td{
	border:1px solid navy;
} */
table{
	z-index: 100;
}
input{
	z-index: 500;
}

</style>
<script>
	var empCode = "${sessionScope.code}";
	var startTime = 0;
	var endTime = 0;
	var restAttdList = [];
	var requestDate = getDay();
	$(document).ready(function() {
		$("#restAttd_tabs").tabs();
		showRestAttdListGrid();
		$("#selectRestAttd").click(function() {
			getCodeRest("DAC004","ADC003","ADC005", "selectRestAttd", "selectRestAttdCode");
		})
		
		$("#selectRestAttdType").click(function() {
			getCodeRest("DAC004","ADC003","ADC005", "selectRestAttdType", "selectRestAttdTypeCode");
		})
		
		
		/* 시간 선택 함수 */
		$("#restAttd_startT").timepicker({
			step: 5,            
			timeFormat: "H:i",    
			minTime: "8:00am",
			maxTime: "11:59pm"	
		});
		$("#restAttd_endT").timepicker({
			step: 5,            
			timeFormat: "H:i",    
			minTime: "8:00am",
			maxTime: "11:59pm"	
		});
	  	
	  
		
		$("#search_startD").click(getDatePicker($("#search_startD")));
		$("#search_endD").click(getDatePicker($("#search_endD")));
		
		$("#search_restAttdList_Btn").click(findrestAttdList); // 조회버튼
		
		$("#delete_restAttd_Btn").click(function(){ // 근태외 삭제버튼
			var flag = confirm("선택한 근태외신청을 정말 삭제하시겠습니까?");
			if(flag)
				removeRestAttdList();
		});
		
		
		$("#restAttd_startD").click(getDatePicker($("#restAttd_startD")));
		
		$("#restAttd_startD").change(function(){ // 근태외신청 시작일 
			if($("#selectRestAttdCode").val().trim()=="ASC006"||$("#selectRestAttdCode").val().trim()=="ASC007"){
			  	// 반차라면
			    $("#restAttd_endD").val($(this).val()); // 시작일과 종료일을 같게한다
			    toDay = $("#restAttd_startD").val();
			};
			$("#restAttd_endD").datepicker("option","minDate",$(this).val());
			if($("#restAttd_endD").val()!="")
				calculateNumberOfDays();
		}); 
		
		
		$("#restAttd_endD").click(getDatePicker($("#restAttd_endD")));
		$("#restAttd_endD").change(function(){ // 근태외신청 종료일
			$("#restAttd_startD").datepicker("option","maxDate",$(this).val());
			if($("#restAttd_startD").val()!="")
				calculateNumberOfDays();
		}); 
		
		$("#btn_regist").click(registrestAttd);
		
		/* 사용자 기본 정보 넣기 */
		$("#restAttd_emp").val("${sessionScope.id}");
		$("#restAttd_dept").val("${sessionScope.dept}");
		$("#restAttd_position").val("${sessionScope.position}");
	})
	
	
	
	/* timePicker시간을 변경하는 함수 */
	function convertTimePicker(){
		startTime = $("#restAttd_startT").val().replace(":","");
		endTime = $("#restAttd_endT").val().replace(":","");
		
 		if(startTime.indexOf("00")==0){
 			startTime = startTime.replace("00","24");
		}
 		if(endTime.indexOf("00")==0){
			endTime = endTime.replace("00","24");
		}
	}
	
	/* 오늘 날자를 RRRR-MM-DD 형식으로 리턴하는 함수 */
	function getDay(){
	    var today = new Date();
	    var rrrr = today.getFullYear();
	    var mm = today.getMonth()+1;
	    var dd = today.getDate();
	    var searchDay = rrrr+"-"+mm+"-"+dd;
		return searchDay;
	}
	
	
	/* 근태외목록 조회버튼  */
	function findrestAttdList(){		
		var startVar = $("#search_startD").val();
		var endVar = $("#search_endD").val();
		var code = $("#selectRestAttdTypeCode").val();
		 $.ajax({
			url:"${pageContext.request.contextPath}/attendance/restAttendance.do",
			data:{
				"method" : "findRestAttdList",
				"empCode" : empCode,
				"startDate" : startVar,
				"endDate" : endVar,
				"code":code
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

				
				/* 시간형태변경포문부분 */
				for(var index in restAttdList){
					restAttdList[index].startTime = getRealTime(restAttdList[index].startTime);
					restAttdList[index].endTime = getRealTime(restAttdList[index].endTime);
				}  

				showRestAttdListGrid();
			}
		}); 
	}
	
	/* 숫자로 되있는 시간을 시간형태로  */
	function getRealTime(time){
		var hour = Math.floor(time/100);
		if(hour==25) hour=1; 
		var min = time-(Math.floor(time/100)*100);
		if(min.toString().length==1) min="0"+min; //분이 1자리라면 앞에0을 붙임
		if(min==0) min="00";
		return hour + ":" + min;
	}
	
	
	
	
	/* 삭제버튼 눌렀을 때 실행되는 함수 */
    function removeRestAttdList(){
		var selectedRowIds = $("#restAttdList_grid").getGridParam("selarrrow"); 
		if(selectedRowIds.length == 0){
			alert("삭제할 근태외목록을 선택해 주세요");
			return;
		}

		var selectedRowData = [];

		$.each(selectedRowIds, function(index, id){ //클릭한 회원들을 each로 풀어서 id를 얻음
			var data = $("#restAttdList_grid").getRowData(id); //얻은 아이디로 조회목록 해당아이디 직원의 모든데이터를 가져옴
			if(data.applovalStatus!='승인')	//가져온 데이터의 승인여부가 '승인'이 아닐경우에 배열에 담음 
				selectedRowData.push(data);
		});
		if(selectedRowData.length == 0){ //배열에 담긴 데이터가 없을때에 발생하는 alert
			alert("승인되지 않은 삭제할 근태외정보가 없습니다\n삭제할 근태외정보를 선택해 주세요");
			return;
		}

		var sendData = JSON.stringify(selectedRowData); //삭제할 목록들이 담긴 배열

		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/restAttendance.do",
			data : {
				"method" : "removeRestAttdList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("정상적으로 삭제되지 않았습니다");
				} else {
					alert("삭제되었습니다");
				}
				location.reload();
			}
		});
    }
	
	
	/* 근태외신청 그리드 띄우는 함수 */
    function showRestAttdListGrid(){
    	$.jgrid.gridUnload("#restAttdList_grid");
    	$("#restAttdList_grid").jqGrid({
    		data : restAttdList,
    		datatype : "local",
    		colNames : ['사원코드','일련번호','근태외구분코드','근태외구분명','신청일자',
    			'시작일','종료일','일수','시작시간','종료시간','사유','승인여부','반려사유'],
			colModel : [ {
				name : 'empCode', hidden : true }, {
				name : 'restAttdCode', hidden : true }, {
				name : 'restTypeCode', hidden : true }, {
				name : 'restTypeName' }, {
				name : 'requestDate' }, {
				name : 'startDate' }, {
				name : 'endDate' }, {
				name : 'numberOfDays' }, {
				name : 'startTime' },{
				name : 'endTime' },{
				name : 'cause' }, {
				name : 'applovalStatus' }, {
				name : 'rejectCause' } ],
			width : 800,
			height : 230,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			rowNum : 20,
			rowList : [ 5, 10, 20 ],
			pager : "#restAttdList_pager"
    	});
    }
	
	
	
	/* 일수 계산 함수  */
	function calculateNumberOfDays(){
	    var startStr = $("#restAttd_startD").val();
		var endStr = $("#restAttd_endD").val();
		if($("#selectRestAttdCode").val().trim()=="ASC006"||$("#selectRestAttdCode").val().trim()=="ASC007"){
		    $("#restAttd_day").val(0.5); //반차라면 무조건 0.5일
		}else if($("#selectRestAttdCode").val().trim()=="ASC001"||$("#selectRestAttdCode").val().trim()=="ASC004"||$("#selectRestAttdCode").val().trim()=="ASC005"){
			
			$.ajax({ //경조사 및 연차라면 주말, 공휴일을 제외한 계산일수를 반환하는 함수 사용
				url:"${pageContext.request.contextPath}/base/holidayList.do",
				data:{
					"method" : "findWeekDayCount",
					"startDate" : startStr,
					"endDate" : endStr
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
					$("#restAttd_day").val(data.weekdayCount);
				}
			});
			
		}else{ // 그 외에는 주말 및 공휴일을 포함한 일자를 가져온다
			var startMs = (new Date(startStr)).getTime();
			var endMs = (new Date(endStr)).getTime();
			var numberOfDays = (endMs-startMs)/(1000*60*60*24)+1;
			$("#restAttd_day").val(numberOfDays);
		}
	}
	

	/* 코드선택 창 띄우기 */
	function getCodeRest(code1,code2,code3, inputText, inputCode) {
		option = "width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
		window.open(
				"${pageContext.request.contextPath}/base/codeWindow.html?code1="
						+ code1 +"&code2="+code2+"&code3="+code3+ "&inputText=" + inputText + "&inputCode="
						+ inputCode, "newwins", option);
	}
	
	/* 달력띄우기 */
	function getDatePicker($Obj) {
		$Obj.datepicker({
			defaultDate : "d",
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy/mm/dd",
			dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
			monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9",
					"10", "11", "12" ],
			yearRange : "1980:2020"
		});
	}
	
	/* 신청 버튼  */
	function registrestAttd(){
		convertTimePicker();
		
		var restAttdList = {
				"empCode" : empCode,
				"restTypeCode" : $("#selectRestAttdCode").val(),
				"restTypeName" : $("#selectRestAttd").val(),
				"requestDate" : requestDate,
				"startDate" : $("#restAttd_startD").val(),
				"endDate" : $("#restAttd_endD").val(),
				"numberOfDays" : $("#restAttd_day").val(),
				"cause" : $("#restAttd_cause").val(),
				"applovalStatus" : '승인대기',
				"startTime" : startTime,
				"endTime" : endTime
			};
		
		
		var sendData = JSON.stringify(restAttdList);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/restAttendance.do",
			data : {
				"method" : "registRestAttd",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("신청을 실패했습니다");
				} else {
					alert("신청되었습니다");
				}
				location.reload();
			}
		});
	}
	
</script>
</head>
<body>

	<div id="restAttd_tabs" style="width: 900px; height: 600px">
		<ul>
			<li><a href="#restAttdAttd_tab">근태외신청</a></li>
			<li><a href="#restAttdSerach_tab">근태외조회</a></li>
		</ul>
		<div id="restAttdAttd_tab">
			<font>근태외 구분 </font><input id="selectRestAttd"	class="ui-button ui-widget ui-corner-all" readonly> 
				<input type="hidden" id="selectRestAttdCode" name="restAttdCode">
			<hr>
			<table>
				<tr>
					<td><font>신청자 </font></td>
					<td><input id="restAttd_emp"	class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr><td><h3> </h3></td></tr>
				<tr>
					<td><font>부서 </font></td>
					<td><input id="restAttd_dept"	class="ui-button ui-widget ui-corner-all" readonly></td>
					
					<td><font>직급</font></td>
					<td><input id="restAttd_position"	class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr><td><h3> </h3></td></tr>
				<tr>
					<td><font>시작일</font></td>
					<td><input id="restAttd_startD" class="ui-button ui-widget ui-corner-all" readonly></td>
				
					<td><font>종료일</font></td>
					<td><input id="restAttd_endD"	class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr><td><h3> </h3></td></tr>
				<tr>
					<td><font>시작시간</font></td>
					<td><input id="restAttd_startT" class="ui-button ui-widget ui-corner-all" name="timePicker1" placeholder="시간선택"></td>
				
					<td><font>종료시간</font></td>
					<td><input id="restAttd_endT"	class="ui-button ui-widget ui-corner-all" name="timePicker2" placeholder="시간선택"></td>
				</tr>
				<tr><td><h3> </h3></td></tr>
				<tr>
					<td><font>일수</font></td>
					<td><input id="restAttd_day" class="ui-button ui-widget ui-corner-all" readonly></td>
					<td><font>증명서</font></td>
					<td><input id="restAttd_certi" class="ui-button ui-widget ui-corner-all" readonly></td>
				</tr>
				<tr><td><h3> </h3></td></tr>
				<tr>
					<td><font>사유</font></td>
					<td colspan="3" ><input id="restAttd_cause" style="width:490px" class="ui-button ui-widget ui-corner-all" ></td>
				</tr>
			</table>
			<hr>
			<input type="button" class="ui-button ui-widget ui-corner-all" id="btn_regist" value="신청">
			<input type="reset" class="ui-button ui-widget ui-corner-all" value="취소">
		</div>
		
		<div id="restAttdSerach_tab">
		<table>
		<tr><td colspan="2"><center><h3>조회범위 선택</h3></center></td></tr>
		<tr><td><h3>구분</h3></td><td><input id="selectRestAttdType" class="ui-button ui-widget ui-corner-all" readonly>
			<input type="hidden" id="selectRestAttdTypeCode">
		</td></tr>
		<tr>
			<td>
				<input type=text class="ui-button ui-widget ui-corner-all" id="search_startD" readonly>~
			</td>
			<td>
				<input type=text class="ui-button ui-widget ui-corner-all" id="search_endD" readonly>
			</td>
		</tr>
		<tr><td><h3> </h3></td></tr>
		<tr>
			<td colspan="2">
				<center>
				<button class="ui-button ui-widget ui-corner-all" id="search_restAttdList_Btn">조회하기</button>
				<button class="ui-button ui-widget ui-corner-all" id="delete_restAttd_Btn">삭제하기</button>
				</center>
			</td>
		</tr>
		</table>
		<hr>
		<table id="restAttdList_grid"></table>
		<div id="restAttdList_pager"></div>
		</div>
	</div>
</body>
</html>