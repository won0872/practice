<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일근태기록/조회</title>
<style type="text/css">
#dayAttdrequest_tabs {
	width: 450px;
	height: 500px;
	display: inline-block;
	position: absolute;
	top: 300px; left: 300px;
}

#dayAttdList_tabs {
	width: 480px;
	height: 500px;
	display: inline-block;
	position: absolute;
	top: 300px; left: 800px;
}

input[type=text]:not(#timePicker) {
	width: 180px;
	height: 10px;
}

.small_Btn{
	width : auto;
	height : auto;
	fontSize : 15px;
}

#clock {
	margin: auto;
}

</style>
<script>

var empCode = "${sessionScope.code}";
var holidayList = [];
var dayAttdList = [];
var currentHours = 0; //시계의 시간
var currentMinute = 0; //시계의 분
var amPm;				//시계의 AM,PM
var conversionDate = 0; //timePicker의 :를 제거하고 자정 이후의 시간을 변환한 값
var restApplyTime = 0;
var restPublicApplyTime = 0;
var restPrivateApplyTime = 0;

$(document).ready(function(){
    	
	    printClock(); //시계 표시
	    findDayAttdList("today"); //시작하자마자 오늘 날짜의 근태목록을 조회목록 grid에 띄움
		showDayAttdListGrid(); // 일근태정보목록 그리드
		findHoliday(); //휴일 목록 셋팅

		$(".comment_div").css({fontSize : "0.7em"});
		    
		$("input:text:not(#timePicker)").button();
		$("#timePicker").button().css({width : "70px",height : "15px"});;
		$(".small_Btn").button();

		$("#dayAttdrequest_tabs").tabs();
		$("#dayAttdList_tabs").tabs();

		getDatePicker($("#applyDay")); // 적용일자
		getDatePicker($("#searchDay")); // 조회일자

		$("#dayAttdrequest_col").click(function(){ //일근태기록 탭을 클릭하면 조회목록 grid의 row값을 오늘 근태목록으로 바꿈
			findDayAttdList("today");
		})

		$("#attdTypeName").click(function(){ // 근태명 입력창
			getCode("CO-09","attdTypeName","attdTypeCode");
		});

		$("#registDayAttd_Btn").click(registDayAttd); // 기록하기 버튼
		
		$("#search_dayAttdList_Btn").click(findDayAttdList); // 조회버튼

		$("#delete_dayAttd_Btn").click(function(){ // 삭제버튼
			var flag = confirm("선택한 근태신청을 정말 삭제하시겠습니까?");
			if(flag)
				removeDayAttdList();
		});
		
		$("#timePicker").click(function(){ // timePicker
		    $(this).timepicker({
				step: 5,            //시간간격 : 5분
				timeFormat: "H:i",    //시간:분 으로표시
				minTime: "00:00am",
				maxTime: "24:00pm"	
			});
		})

		$("#timeCheck_Btn").click(function(){ //시계를 보여주고 기록하기를 누르면 그 시간에 맞춰 시간등록하게 하기
			
			var checkHours = $("#clock").text().split(" ")[0].substring(0,2);	
			var checkMinute = $("#clock").text().split(" ")[0].substring(3,5);
			
			if(checkHours == 00){
			  checkHours == 24
			}
			registDayAttd("Clock",checkHours+checkMinute);

		})
		
	});

	/* timePicker시간을 변경하는 함수 */
	function convertTimePicker(){
	    conversionDate = $("#timePicker").val().replace(":","");
 		if(conversionDate.indexOf("00")==0){
 		   conversionDate = $("#timePicker").val().replace(":","").replace("00","24");
		}else if(conversionDate.indexOf("01")==0){
		    conversionDate = $("#timePicker").val().replace(":","").replace("01","25");
		}
	}


	/* 휴일 목록  조회 함수 */
	function findHoliday(){
		$.ajax({
			url:"${pageContext.request.contextPath}/base/holidayList.do",
			data:{
				"method" : "findHolidayList"
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

				holidayList = data.holidayList;
			}
		});
	}

	/* 일근태목록 조회버튼 함수 */
	function findDayAttdList(check){
		var searchDay = $("#searchDay").val();
		if(check=="today"){ //해당 함수를 부를때에 today라는 글자가 변수로 넘어오면 오늘 날짜를 searchDay변수에 담아 ajax실행
		    var today = new Date();
		    var rrrr = today.getFullYear();
		    var mm = today.getMonth()+1;
		    var dd = today.getDate();
		    searchDay = rrrr+"-"+mm+"-"+dd;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/attendance/dayAttendance.do",
			data:{
				"method" : "findDayAttdList",
				"empCode" : empCode,
				"applyDay" : searchDay
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

				dayAttdList = data.dayAttdList;

				for(var index in dayAttdList){
					dayAttdList[index].time = getRealTime(dayAttdList[index].time);
				}
 
				showDayAttdListGrid();
			}
		});
	}

    /* 일근태정보 그리드 띄우는 함수 */
    function showDayAttdListGrid(){
    	$.jgrid.gridUnload("#dayAttdList_grid");
    	$("#dayAttdList_grid").jqGrid({
    		data : dayAttdList,
    		datatype : "local",
    		colNames : ['사원코드','일련번호','적용일','근태구분코드','근태구분명','시각'],
			colModel : [ {
				name : 'empCode',hidden : true}, {
				name : 'dayAttdCode',hidden : true}, {
				name : 'applyDay'}, {
				name : 'attdTypeCode',hidden : true}, {
				name : 'attdTypeName'}, {
				name : 'time'}],
			width : 420,
			height : 280,
			multiselect : true,
			multiboxonly : true,
			viewrecords : true,
			rowNum : 10,
			pager : "#dayAttdList_pager"
    	});
    }

    /* 삭제버튼 눌렀을 때 실행되는 함수 */
    function removeDayAttdList(){
		var selectedRowIds = $("#dayAttdList_grid").getGridParam("selarrrow");
		
		if(selectedRowIds.length == 0){
			alert("삭제할 근태목록을 선택해 주세요");
			return;
		}

		var selectedRowData = [];
		$.each(selectedRowIds, function(index, id){
			var data=$("#dayAttdList_grid").getRowData(id);
			selectedRowData.push(data);
		});

		var sendData = JSON.stringify(selectedRowData);
		$.ajax({
			url : "${pageContext.request.contextPath}/attendance/dayAttendance.do",
			data : {
				"method" : "removeDayAttdList",
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

    /* 기록버튼 눌렀을 때 실행되는 함수 */
    function registDayAttd(clock,clockTime){
    	var alertMsg = "조퇴나 외출을 원하시면 근태외 신청을해주세요";
	    var clockCheck = clock; //timepicker로 기록할것인지 시계로 기록할것인지 여부를 가릴 변수
	    
	    
	    
	    convertTimePicker(); //선택한 시간을 변환
	    
	    
	    
		var restTypeCode = 0; //restTypeCode 저장 변수
		var checkVar = false; //근태기록 ajax실행 여부를 가릴 변수
		var today = new Date($("#applyDay").val()); //오늘 요일을 가져오기위한 Date객체
		var dayAttd = {};
		if($("#applyDay").val().trim()==""){
			alert("적용일을 입력해 주세요");
			return false;
		}
		if($("#attdTypeName").val().trim()==""){
			alert("근태구분을 입력해 주세요");
			return false;
		}
		
		if(clockCheck=="Clock"){
		    dayAttd = {
				"empCode" : empCode,
				applyDay : $("#applyDay").val(),
				attdTypeCode : $("#attdTypeCode").val(),
				attdTypeName : $("#attdTypeName").val(),
				time : clockTime
			};
		}else{

		    dayAttd = {
				"empCode" : empCode,
				applyDay : $("#applyDay").val(),
				attdTypeCode : $("#attdTypeCode").val(),
				attdTypeName : $("#attdTypeName").val(),
				time : conversionDate
			};
		}
    	
	
    	$.ajax({ //기록할때의 적용일자에 해당하는 근태외신청 목록중에 승인처리가된 근태외신청 목록만 가져온다
			url:"${pageContext.request.contextPath}/attendance/restAttendance.do",
			data:{
			    "method" : "findRestAttdListByToday",
				"empCode" : empCode,
				"toDay" : $("#applyDay").val()
			},
			async: false, //혹시라도 데이터를 가져오기 전에 일련의 작업이 이뤄지는것을 방지하기 위해 동기처리
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
				if(data.restAttdList){ // 가져온 데이터가 존재한다면
					restTypeCode = data.restAttdList.restTypeCode; //데이터를 변수에 할당한다
					if(restTypeCode=='DAC004'){
						restApplyTime = data.restAttdList.endTime;
					}
					if(restTypeCode=='ADC003'){
						restPublicApplyTime = data.restAttdList.startTime;
					}
					if(restTypeCode=='ADC005'){
						restPrivateApplyTime = data.restAttdList.startTime;
					}
					
				}
				//위의 if문을 거치지 않으면 결과값이 없는 데이터를 받았을경우 restTypeCode가 존재하지 않기 때문에 javascript쪽의 exception이 발생함
			}
		});
		

    	if(clockCheck!="Clock"){
    		alert(conversionDate)
    		if(Number(conversionDate) < 1800 && $("#attdTypeCode").val() == 'ADC002'){ //선택 시간이 18시보다 빠르고 퇴근이라면
				if(restTypeCode == 'DAC004'){ //금일 확정된 조퇴 또는 오후반차 정보가 존재한다면
					
				    if(Number(conversionDate) > restApplyTime){
				    	checkVar = true;
				    }else{
				    	alertMsg= "조퇴신청 시간 이후에 퇴근이 가능합니다."
				    }
				
				}
    			
    			if(restTypeCode == 'ASC007'){					//오후반차일 경우 12시 이후 퇴근 가능하게 만듬
    				if(Number(conversionDate) > 1200){
				    	checkVar = true;
				    }else{
				    	alertMsg= "오전업무 종료시간 후 퇴근이 가능합니다."
				    }
    			}
    		
			}else if(Number(conversionDate) < 1800 && $("#attdTypeCode").val() == 'ADC003'){ //선택 시간이 18시보다 빠르고 공외출이라면
				alert("여기까지 들어와야됨"+restPublicApplyTime)
				alert(restTypeCode)
				if(restTypeCode == 'ADC003'){ //금일 확정된 공외출 정보가 존재한다면
					
				    if(Number(conversionDate) >= restPublicApplyTime){
				    	checkVar = true;
				    }else{
				    	alertMsg= "외출 신청 시간 이후에 퇴근이 가능합니다."
				    }
				
				}
			}else if(Number(conversionDate) < 1800 && $("#attdTypeCode").val() == 'ADC005'){ //선택 시간이 18시보다 빠르고 사외출이라면
				if(restTypeCode == 'ADC005'){ //금일 확정된 사외출 정보가 존재한다면
				    if(Number(conversionDate) >= restPrivateApplyTime){
				    	checkVar = true;
				    }else{
				    	alertMsg= "외출 신청 시간 이후에 퇴근이 가능합니다."
				    }
				
				}
			}else{
			    checkVar = true;
			}
    		
    		
		 	$.each(holidayList, function(i, elt) {
			 	if((today.getDay()==0 || today.getDay()==6) || $("#applyDay").val() == holidayList[i].applyDay){ //토,일요일 또는 공휴일이라면
			 		alertMsg="휴일에는 사용할 수 없습니다 (구현중...)"
					 
			 		checkVar = false;
					return;
				}
		 		
		    });
		   
		    
    	}else{ 
    		if(Number(clockTime) < 1800 && $("#attdTypeCode").val() == 'ADC002'){ //선택 시간이 18시보다 빠르고 퇴근이라면
				if(restTypeCode == 'DAC004'){ //금일 확정된 조퇴 또는 오후반차 정보가 존재한다면
				    if(Number(clockTime) > restApplyTime){
				    	checkVar = true;
				    }else{
				    	alertMsg= "조퇴신청 시간 이후에 퇴근이 가능합니다."
				    }
				}
    			if(restTypeCode == 'ASC007'){
    				if(Number(clockTime) > 1200){
				    	checkVar = true;
				    }else{
				    	alertMsg= "오전업무 종료시간 후 퇴근이 가능합니다."
				    }
    			}
    		
			}else if(Number(conversionDate) < 1800 && $("#attdTypeCode").val() == 'ADC003'){ //선택 시간이 18시보다 빠르고 공외출이라면
				
				if(restTypeCode == 'ADC003'){ //금일 확정된 공외출 정보가 존재한다면
				    if(Number(clockTime) > restPublicApplyTime){
				    	checkVar = true;
				    }else{
				    	alertMsg= "외출 신청 시간 이후에 퇴근이 가능합니다."
				    }
				
				}
			}else if(Number(conversionDate) < 1800 && $("#attdTypeCode").val() == 'ADC005'){ //선택 시간이 18시보다 빠르고 사외출이라면
				
				if(restTypeCode == 'ADC005'){ //금일 확정된 사외출 정보가 존재한다면
				    if(Number(clockTime) > restPublicApplyTime){
				    	checkVar = true;
				    }else{
				    	alertMsg= "외출 신청 시간 이후에 퇴근이 가능합니다."
				    }
				
				}
			}else{
			    checkVar = true;
			}
    		
    		
		 	$.each(holidayList, function(i, elt) {
			 	if((today.getDay()==0 || today.getDay()==6) || $("#applyDay").val() == holidayList[i].applyDay){ //토,일요일 또는 공휴일이라면
			 		alertMsg="휴일에는 사용할 수 없습니다 (구현중...)"
					 
			 		checkVar = false;
					return;
				}
		 		
		    }); 
		   
		    
        }
    	
    	
	 	if(checkVar){ //위의 사항에 만족한다면
			$.ajax({
				url:"${pageContext.request.contextPath}/attendance/dayAttendance.do",
				data:{
					"method" : "findDayAttdList",
					"empCode" : empCode,
					"applyDay" : $("#applyDay").val()
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
					
					//항상 출근이 먼저 찍히기 위한 조건
					if(data.dayAttdList.length==0 && dayAttd.attdTypeCode != "ADC001"){
						alert("출근 이후 등록가능합니다");
						return;
					}

					// 근태신청 내역이 중복되지 않도록 체크
					for(var index in data.dayAttdList){
						if(data.dayAttdList[index].attdTypeCode == "ADC001" && dayAttd.attdTypeCode == "ADC001"){
							alert("출근 내역이 이미 등록되어 있습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC002" && dayAttd.attdTypeCode == "ADC002"){
							alert("퇴근 내역이 이미 등록되어 있습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC003" && dayAttd.attdTypeCode == "ADC003"){
							alert("공외출 내역이 이미 등록되어 있습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC005" && dayAttd.attdTypeCode == "ADC005"){
							alert("사외출 내역이 이미 등록되어 있습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC004" && dayAttd.attdTypeCode == "ADC004"){
							alert("귀사 내역이 이미 등록되어 있습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC002" && dayAttd.attdTypeCode == "ADC001"){
							alert("퇴근 후에는 사용할 수 없습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC002" && dayAttd.attdTypeCode == "ADC003"){
							alert("퇴근 후에는 사용할 수 없습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC002" && dayAttd.attdTypeCode == "ADC004"){
							alert("퇴근 후에는 사용할 수 없습니다");
							return;
						}
						if(data.dayAttdList[index].attdTypeCode == "ADC002" && dayAttd.attdTypeCode == "ADC005"){
							alert("퇴근 후에는 사용할 수 없습니다");
							return;
						}
						
					}
					

					var sendData = JSON.stringify(dayAttd);
		 			$.ajax({
		 				url : "${pageContext.request.contextPath}/attendance/dayAttendance.do",
		 				data : {
		 					"method" : "registDayAttd",
		 					"sendData" : sendData
		 				},
		 				dataType : "json",
		 				success : function(data) {
		 					if(data.errorCode < 0){
		 						alert("기록에 실패했습니다");
		 					} else {
		 						alert("기록되었습니다");
		 					}
		 					location.reload();
		 				}
		 			});
				}
			});
		}else{
			alert(alertMsg);	
		}
	}

	/* 시계 만드는 함수 */
	function printClock() {
	    
	    var clock = $("#clock");            // 출력할 장소 선택
	    var currentDate = new Date();                                     // 현재시간
	    var calendar = currentDate.getFullYear() + "-" + (currentDate.getMonth()+1) + "-" + currentDate.getDate() // 현재 날짜
	    amPm = 'AM'; // 초기값 AM
	    currentHours = addZeros(currentDate.getHours(),2); 
	    currentMinute = addZeros(currentDate.getMinutes() ,2);
	    var currentSeconds =  addZeros(currentDate.getSeconds(),2);
	    
	    if(currentHours >= 12){ // 시간이 12보다 클 때 PM으로 세팅, 12를 빼줌
	    	amPm = 'PM';
	    	currentHours = addZeros(currentHours,2);
	    }

	    
	    currentSeconds = '<span >'+currentSeconds+'</span>';
	    
	    clock.html(currentHours+":"+currentMinute+":"+currentSeconds +" <span style='font-size:50px;'>"+ amPm+"</span>"); //날짜를 출력해 줌
	    
	    setTimeout("printClock()",1000); // 1초마다 printClock() 함수 호출
	}

	function addZeros(num, digit) { // 시계 자릿수 맞춰주기
		  var zero = '';
		  num = num.toString();
		  if (num.length < digit) {
		    for (i = 0; i < digit - num.length; i++) {
		      zero += '0';
		    }
		  }
		  return zero + num;
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
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
				monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
				yearRange: "1980:2020",
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
<div id="dayAttdrequest_tabs">
	<ul>
		<li id="dayAttdrequest_col"><a href="#dayAttdrequest_tab_1">일근태기록</a></li>
		<li><a href="#dayAttdrequest_tab_2">일근태조회</a></li>
	</ul>
	<div id="dayAttdrequest_tab_1">
	<div style="width:300px; height:60px; font-size:30px; text-align:center;" id="clock">
	</div>
			<table>
					<tr>
						<td>적용일자</td>
						<td><input type=text id="applyDay" readonly></td>
					</tr>
					<tr>
						<td>근태구분</td>
						<td>
							<input type=text id="attdTypeName" readonly>
							<input type=hidden id="attdTypeCode">
						</td>
					</tr>
					<tr>
						<td>시각</td>
						<td><input type="text" name="timePicker" placeholder="시간선택"  id="timePicker" size="10"><input type="button" id="registDayAttd_Btn" class="small_Btn" value="기록하기"/></td>
					</tr>
					<tr>
						<td></td><td><input type="button" id="timeCheck_Btn" class="small_Btn" value="시계기록"/></td>
					</tr>
			</table>
		<br />
	</div>
	<div id="dayAttdrequest_tab_2">
		조회일자 <input type=text id="searchDay" readonly>
		<button class="small_Btn" id="search_dayAttdList_Btn">조회하기</button><br />
		<br />
	</div>
</div>
<div id="dayAttdList_tabs">
	<ul>
		<li><a href="#dayAttdList_tab_1">조회목록</a></li>
	</ul>
	<div id="dayAttdList_tab_1">
		<br />
		<button class="small_Btn" id="delete_dayAttd_Btn">삭제하기</button><br />
		<br />
		<table id="dayAttdList_grid"></table>
		<div id="dayAttdList_pager"></div>
	</div>
</div>
</body>
</html>