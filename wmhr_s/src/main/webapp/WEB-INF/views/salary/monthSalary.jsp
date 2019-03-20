<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>개인급여조회</title>
<style>
</style>
<script>
var monthSalary = {};
var yearSalary = {};
var monthSalaryGridData = [];
var empCode = "${sessionScope.code}";

	$(document).ready(function(){

	    showYearSalaryGrid();
	    showMonthDeductionListGrid();
		showMonthExtSalListGrid();
		showMonthSalaryGrid();

		
		$("#request_Btn").click(printWorkInfoReport);
		
		$("#yearSalary_col").click(function(){ //연급여조회 탭을 누르면
			$("#deduction_tabs").hide(); //공제, 초과수당 grid를 숨긴다
		})

		$("#monthSalary_col").click(function(){ //월급여조회 탭을 누르면
			$("#deduction_tabs").show(); //공제, 초과수당 grid를 보여준다
		})
		
		
		// input:text에 버튼 형식의 css 씌움
		$("input:text").button().css({
			width:"150px",
			height:"10px"
		});


		// tabs css
		$("#salary_tabs").tabs().css({
			width:"1300px",
			height:"400px",
			padding:"10px",
			margin:"10px",
		});    


		$("#deduction_tabs").tabs().css({
			width:"1300px",
			height:"300px",
			margin:"10px 10px 10px 0px",
		});

		// 적용연월 셀렉터
		$("#searchYearMonth").selectmenu();
		var year = (new Date()).getFullYear();
		for(var i=1; i<=12; i++)
			$("#searchYearMonth").append($("<option />").val(year+"-"+i).text(year+"년 "+i+"월"));

		
		$("#searchYear").selectmenu();
		for(var i=-10; i<10; i++) // 현재 연도에 -10~+10년까지 조회 가능하게함
			$("#searchYear").append($("<option />").val(year+i).text(year+i+"년"));
		
		// 월급여 조회하기 버튼
		$("#monthSearch_Btn").click(findMonthSalary);

		// 연급여 조회하기 버튼
		$("#yearSearch_Btn").click(findYearSalary);
		
		// 마감 버튼
		$("#finalize_Btn").click(finalizeMonthSalary);

		// 마감취소 버튼
		$("#cancel_Btn").click(cancelMonthSalary);

	});
	
	
	/* 급여명세서 */
	function printWorkInfoReport(){
		applyMonth = $("#searchYearMonth").val();
		
	      window.open(
	            "${pageContext.request.contextPath }/base/empReport.do?method=requestMonthSalary&format=pdf&empCode="+empCode+"&applyMonth="+applyMonth,
	            "급여명세서",
	            "width=1280, height=1024");
	}


	/* 월급여 조회하기 */
	function findMonthSalary(){
		if($("#searchYearMonth").val()==""){
			alert("적용 연월을 선택해 주세요");
			return;
		}

		$.ajax({
			url:"${pageContext.request.contextPath}/salary/monthSalary.do",
			data:{
				"method" : "findMonthSalary",
				"applyYearMonth" : $("#searchYearMonth").val(),
				"empCode" : empCode
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
				
 				monthSalary = data.monthSalary;

 				/* 가져온 데이터들중에 금액과 관련된 데이터에 콤마,'원'을 붙임 */
				$.each(monthSalary.monthDeductionList, function(i, elt) {
					monthSalary.monthDeductionList[i].price = numberWithCommas(monthSalary.monthDeductionList[i].price)+"원";
				});

				$.each(monthSalary.monthExtSalList, function(i, elt) {
					monthSalary.monthExtSalList[i].price = numberWithCommas(monthSalary.monthExtSalList[i].price)+"원";
				});

				monthSalary.realSalary = numberWithCommas(monthSalary.realSalary)+"원";
				monthSalary.salary = numberWithCommas(monthSalary.salary)+"원";
				monthSalary.totalDeduction = numberWithCommas(monthSalary.totalDeduction)+"원";
				monthSalary.totalExtSal = numberWithCommas(monthSalary.totalExtSal)+"원";
				monthSalary.totalPayment = numberWithCommas(monthSalary.totalPayment)+"원";

				/* monthSalary 통째로 그리드에 넣으면 인식이 안됨 그래서 따로 빼서 객체화시켜 집어넣음 */
				monthSalaryGridData.push({
					"empCode":monthSalary.empCode, 
				    "applyYearMonth":monthSalary.applyYearMonth,
				    "salary":monthSalary.salary,
				    "totalExtSal":monthSalary.totalExtSal,
				    "totalPayment":monthSalary.totalPayment,
				    "totalDeduction":monthSalary.totalDeduction,
				    "realSalary":monthSalary.realSalary,
				    "cost":monthSalary.cost,
				    "unusedDaySalary":monthSalary.unusedDaySalary,
				    "finalizeStatus":monthSalary.finalizeStatus
				});

				/* 데이터를 가져와서 일련의 작업 후 바로 그리드 재호출 */
				showMonthSalaryGrid(); 
				showMonthDeductionListGrid();
				showMonthExtSalListGrid();
			}
		});
		
	}

	/* 연급여 조회하기 */
	function findYearSalary(){
		if($("#searchYear").val()==""){
			alert("적용 연도를 선택해 주세요");
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/salary/monthSalary.do",
			data:{
				"method" : "findYearSalary",
				"applyYear" : $("#searchYear").val(),
				"empCode" : empCode
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
				if(data.yearSalary==""){
					alert("해당 연도의 마감 완료된 급여 정보가 존재하지 않습니다");
				}else{
					yearSalary = data.yearSalary;
					
					$.each(yearSalary, function(i, elt) {
					    yearSalary[i].realSalary = numberWithCommas(yearSalary[i].realSalary)+"원";
					    yearSalary[i].salary = numberWithCommas(yearSalary[i].salary)+"원";
					    yearSalary[i].totalDeduction = numberWithCommas(yearSalary[i].totalDeduction)+"원";
					    yearSalary[i].totalExtSal = numberWithCommas(yearSalary[i].totalExtSal)+"원";
					    yearSalary[i].totalPayment = numberWithCommas(yearSalary[i].totalPayment)+"원";
					});
					
					showYearSalaryGrid();
				}
			}
		});
	}

	/* 숫자 3자리마다 콤마를 찍는 정규표현식 */
	function numberWithCommas(won) {
	    return won.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

    /* 마감처리 함수 */
    function finalizeMonthSalary(){
		monthSalary.finalizeStatus = "Y";
		var sendData = JSON.stringify(monthSalary);

		$.ajax({
			url : "${pageContext.request.contextPath}/salary/monthSalary.do",
			data : {
				"method" : "modifyMonthSalary",
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
    
    /* 마감취소 처리 함수 */
    function cancelMonthSalary(){
		monthSalary.finalizeStatus = "N";
		var sendData = JSON.stringify(monthSalary);

		$.ajax({
			url : "${pageContext.request.contextPath}/salary/monthSalary.do",
			data : {
				"method" : "modifyMonthSalary",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("마감취소에 실패했습니다");
				} else {
					alert("취소처리 되었습니다");
				}
				location.reload();
			}
		});
	}
    
    /* 월급여조회 그리드 */
	function showMonthSalaryGrid(){
	 	$.jgrid.gridUnload("#monthSalary_grid");
	    	$("#monthSalary_grid").jqGrid({
	    		data : monthSalaryGridData,
	    		datatype : "local",
	    		colNames : ['사원코드','적용연월','본 급여','초과수당합계','총 급여','공제금액합계','차인지급액','경비지급액','연차미사용수당','마감여부'],
				colModel : [ {
					name : 'empCode',
					hidden : true
				}, {
					name : 'applyYearMonth',
					hidden : true
				}, {
					name : 'salary'
				}, {
					name : 'totalExtSal'
				}, {
					name : 'totalPayment'
				}, {
					name : 'totalDeduction'
				}, {
					name : 'realSalary'
				}, {
					name : 'cost',
					width : 130
				}, {
					name : 'unusedDaySalary',
					width : 130
				}, {
					name : 'finalizeStatus',
					width : 100
				}],
				width : 800,
				height : 30,
				rowNum : 1
	    	});
			
	}

	/* 연급여조회 그리드 */
    function showYearSalaryGrid() {
    	$.jgrid.gridUnload("#yearSalary_grid");
    	$("#yearSalary_grid").jqGrid({
    		data : yearSalary,
    		datatype : "local",
    		colNames : ['사원코드','적용연월','본 급여','초과수당합계','총 급여','공제금액합계','차인지급액'],
			colModel : [ {
				name : 'empCode',
				hidden : true
			}, {
				name : 'applyYearMonth'
			}, {
				name : 'salary'
			}, {
				name : 'totalExtSal'
			}, {
				name : 'totalPayment'
			}, {
				name : 'totalDeduction'
			}, {
				name : 'realSalary',
			}],
			width : 1000,
			height : 250,
			viewrecords : true,
			rowNum : 12
    	});

	}

    
	/* 공제내역 그리드 */
	function showMonthDeductionListGrid(){
    	$.jgrid.gridUnload("#monthDeductionList_grid");
    	$("#monthDeductionList_grid").jqGrid({
    		data : monthSalary.monthDeductionList,
    		datatype : "local",
    		colNames : ['사원코드','적용연월','공제항목코드','공제항목명','공제금액'],
			colModel : [ {
				name : 'empCode',
				hidden : true
			}, {
				name : 'applyYearMonth'
			}, {
				name : 'deductionCode',
				hidden : true
			}, {
				name : 'deductionName'
			}, {
				name : 'price'
			} ],
			width : 500,
			height : 200,
			viewrecords : true,
			rowNum : 10
    	});
	}

	/* 초과수당내역 그리드 */
	function showMonthExtSalListGrid(){
    	$.jgrid.gridUnload("#monthExtSalList_grid");
    	$("#monthExtSalList_grid").jqGrid({
    		data : monthSalary.monthExtSalList,
    		datatype : "local",
    		colNames : ['사원코드','적용연월','초과수당코드','초과수당명','금액'],
			colModel : [ {
				name : 'empCode',
				hidden : true
			}, {
				name : 'applyYearMonth'
			}, {
				name : 'extSalCode',
				hidden : true
			}, {
				name : 'extSalName'
			}, {
				name : 'price'
			} ],
			width : 500,
			height : 200,
			viewrecords : true,
			rowNum : 10
    	});
	}
</script>
</head>
<body>
<div id="salary_tabs">
		<ul>
			<li id = "monthSalary_col">
				<a href="#monthSalary_tab">월급여조회</a>
			</li>
			<li id = "yearSalary_col">
				<a href="#yearSalary_tab">연급여조회</a>
			</li>
		</ul>
		<div id="monthSalary_tab">
			조회 월 <select id="searchYearMonth"></select><br /><br />
			<input type="button" class="ui-button ui-widget ui-corner-all" id="monthSearch_Btn" value="조회하기"/>
			<input type="button" class="ui-button ui-widget ui-corner-all" id="finalize_Btn" value="마감"/>
			<input type="button" class="ui-button ui-widget ui-corner-all" id="cancel_Btn" value="마감취소"/>
			<input type="button" class="ui-button ui-widget ui-corner-all" id="request_Btn" value="발급"/><br /><br />
			<table id="monthSalary_grid"></table>
		</div>
		<div id="yearSalary_tab">
			조회 연도 <select id="searchYear"></select><input type="button" class="small_Btn" id="yearSearch_Btn" value="조회하기"/>
			<div>마감 완료된 월의 급여 정보만 출력됩니다</div>
			<table id="yearSalary_grid"></table>
		</div>
</div>

<div id="deduction_tabs">
		<ul>
			<li>
				<a href="#deduction_tabs">공제내역</a>
			</li>
			<li>
				<a href="#ext_sal_tabs">초과수당내역</a>
			</li>
		</ul>
		<div id="deduction_tabs">
			<table id="monthDeductionList_grid"></table>
			<div id="monthDedcutionList_pager"></div>
		</div>
		<div id="ext_sal_tabs">
			<table id="monthExtSalList_grid"></table>
			<div id="monthExtSalList_pager"></div>
		</div>
</div>
</body>
</html>