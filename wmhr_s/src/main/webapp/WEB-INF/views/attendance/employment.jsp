<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<script>
var empCode = "${sessionScope.code}";
var usage = "";
var requestDay = "";
var useDay = "";
$(document).ready(function(){
   
   $("#deptName").val("${sessionScope.dept}")
   
   $("#usage").click(function(){
		getCode('CO-16',"usage","");
	});
	
	
   $("#employment").tabs();
   
   $("#employmentrequest").val(getDay());
   
    getDatePicker($("#employmentrequest"));
      $("#employmentrequest").change(function(){
         $("#endDate").datepicker("option","minDate",$(this).val());
         if($("#endDate").val()!="")
            calculateNumberOfDays();   
      }); // 시작일
      getDatePicker($("#endDate"));
      $("#endDate").change(function(){
         $("#employmentrequest").datepicker("option","maxDate",$(this).val());
         if($("#employmentrequest").val()!="")
            calculateNumberOfDays();
         
      }); // 종료일
   
      getDatePicker($("#search_startDate"));
      $("#search_startDate").change(function(){ // 근태외조회 시작일
         $("#search_endDate").datepicker("option","minDate",$(this).val());
      }); 
      
      getDatePicker($("#search_endDate"));
      $("#search_endDate").change(function(){ // 근태외조회 종료일
         $("#search_startDate").datepicker("option","maxDate",$(this).val());
      }); 
   
      
      
      /* 재직증명서 버튼  */
      $("#issuance_employmen_Btn").click(printWorkInfoReport);

   
   
   
});


/* 일수 계산하는 함수 */
function calculateNumberOfDays(){
   var startStr = $("#employmentrequest").val();
   var endStr = $("#endDate").val();
   var startMs = (new Date(startStr)).getTime();
   var endMs = (new Date(endStr)).getTime();
   var numberOfDays = (endMs-startMs)/(1000*60*60*24)+1;
   $("#numberOfDays").val(numberOfDays);
}



/* 오늘 날자를 RRRR-MM-DD 형식으로 리턴하는 함수 */
function getDay(){
    var today = new Date();
    var rrrr = today.getFullYear();
    var mm = today.getMonth()+1;
    var dd = today.getDate();
    var searchDay = rrrr+"/"+mm+"/"+dd;
   return searchDay;
}




/* DatePicker 함수 */
function getDatePicker($CellId) {
          $CellId.datepicker({
             changeMonth : true,
             changeYear : true,
             dateFormat : "yy/mm/dd",
             showAnim: "slide",
             dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
             monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
             yearRange: "1970:2020",
                });
   }
   
   
   
   

 /* 코드 선택창 띄우기 */
	  function getCode(code,inputText,inputCode){
			option="width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
			window.open("${pageContext.request.contextPath}/base/codeWindow.html?code="
							+code+"&inputText="+inputText+"&inputCode="+inputCode,"newwins",option);
	}


/* 재직증명서 ireport */
function printWorkInfoReport(){
	usage = $("#usage").val();
	requestDay = $("#employmentrequest").val();
	useDay = $("#endDate").val();
	
      window.open(
            "${pageContext.request.contextPath }/base/empReport.do?method=findEmpReport&format=pdf&empCode="+empCode+"&usage="+usage+"&requestDay="+requestDay+"&useDay="+useDay,
            "재직증명서",
            "width=1280, height=1024");
}




</script>
<div id="employment"  style="width:1200px; position: relative; display: inline-block;">
      <ul>
         <li><a href="#employmentRequest_tab">재직증명서 신청</a></li>
         <li><a href="#employmentList_tab">재직증명서 조회/발급/취소</a></li>
      </ul>
   <div id="employmentRequest_tab">
      <table>
      <tr>
       <td>신청 구분</td>
       <td><input type="text" class="ui-button ui-widget ui-corner-all" value="재직증명서 신청" readonly>
               
       </td>
       
      <td>부서</td><td><input type="text" id="deptName" class="ui-button ui-widget ui-corner-all" value="${emp.deptName}" readonly></td>
      </tr>   
      
      <tr>
       <td>신청일</td><td><input type="text" class="ui-button ui-widget ui-corner-all"  id="employmentrequest" readonly></td>
       <td>사원명</td><td>
       <input type="text" class="ui-button ui-widget ui-corner-all" value="${sessionScope.id}" readonly>
        <input type="hidden" id="employmentEmpCode" value="${ssesionScope.code}">
      
       </td>
      
      </tr>

      

      <tr>
       <td>용도</td><td><input class="ui-button ui-widget ui-corner-all" id="usage" type="text"></td>
       <td>사용일</td><td><input class="ui-button ui-widget ui-corner-all" type="text" id="endDate"></td>
      </tr>
   
   
      <tr>
      
      
       <td>일수</td><td><input class="ui-button ui-widget ui-corner-all" type="text" id="numberOfDays" readonly></td>
       <td>비고</td><td><input class="ui-button ui-widget ui-corner-all" type="text"></td>
      </tr> 
      <tr>
       <td colspan="3"><input type="button" class="ui-button ui-widget ui-corner-all" id="registRestAttd_Btn" value="신청하기">
       <input class="ui-button ui-widget ui-corner-all" type="button" value="취소">
       <input type="button" class="ui-button ui-widget ui-corner-all" id="issuance_employmen_Btn" value="발급"></td>
      </tr>   
         
      
      
      
   </table>
      
   </div>
      <div id="employmentList_tab">
            <div>신청일자</div>
      <br />
      <input type=text class="ui-button ui-widget ui-corner-all" id="search_startDate" readonly> 부터
      <input type=text class="ui-button ui-widget ui-corner-all" id="search_endDate" readonly> 까지<br/><br/>
      <input type="button" class="ui-button ui-widget ui-corner-all" id="search_employmentList_Btn" value="조회">
      <input type="button" class="ui-button ui-widget ui-corner-all" id="delete_employmen_Btn" value="취소">
      <br/><br/>
      <br />
      <table id="employmentList_grid"></table>
      </div>
   </div>