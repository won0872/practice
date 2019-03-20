<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>급여기준관리</title>
<style type="text/css">
#baseSalaryList_tabs {
margin : auto;
width : 550px;
}
</style>

<script>
	$(document).ready(function(){
	    getBaseSalaryListFunc();

	    $("#baseSalaryList_grid").find("tr").each(function(){
		 $(this).find("td").eq(1).bind("mouseover", function(){
		  alert(1);
		 });
		});
		
		$(".small_Btn").css({
			width : "auto",
			height : "auto",
			fontSize : "15px",
			fontFamily : "MD개성체"
		}).button();

		$("#baseSalaryList_tabs").tabs().css({
	
		});

		$("#submit_Btn").click(modifyBaseSalaryList);

	
	});

	function getBaseSalaryListFunc(){
			$.ajax({
				url:"${pageContext.request.contextPath}/salary/baseSalaryManage.do",
				data:{
					"method" : "findBaseSalaryList"
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
	
					baseSalaryList = data.baseSalaryList;
					showBaseSalaryListGrid();
				}
			});

		}


	// 전역변수 선언
	var baseSalaryList = [];

	function modifyBaseSalaryList(){
		var sendData = JSON.stringify(baseSalaryList);
		$.ajax({
			url : "${pageContext.request.contextPath}/salary/baseSalaryManage.do",
			data : {
				"method" : "modifyBaseSalaryList",
				"sendData" : sendData
			},
			dataType : "json",
			success : function(data) {
				if(data.errorCode < 0){
					alert("저장에 실패했습니다");
				} else {
					alert("저장되었습니다");
				}
				location.reload();
			}
		});
	}

    /* 급여기준목록 그리드 띄우는 함수 */
    function showBaseSalaryListGrid(){
    	$.jgrid.gridUnload("#baseSalaryList_grid");
    	$("#baseSalaryList_grid").jqGrid({
    		data : baseSalaryList,
    		datatype : "local",
    		colNames : ['직급코드','직급','기본급','호봉인상율','상태'],
			colModel : [ {
				name : 'positionCode', hidden : true }, {
				name : 'position' }, {
				name : 'baseSalary', editable : true }, {
				name : 'hobongRatio', editable : true, editoptions:{dataInit:function(cell){getDatePicker($(cell));}} }, {
				name : 'status' } ],
			width : 500,
			height : 300,
			viewrecords : true,
			rowNum : 20,
			editurl : "clientArray",
			cellsubmit : "clientArray",
			cellEdit : false,			//수정불가
			onCellSelect : function(id, col, cellcontent, e) {
				var rowData = $(this).jqGrid("getRowData", id);
				if (rowData.status == "select") {
					$(this).setCell(id, "status", "update");
				}
			}
    	});
    	
    }

</script>
</head>
<body>
	<div id="baseSalaryList_tabs">
		<ul>
			<li><a href="#baseSalaryList_tab">급여기준관리</a></li>
		</ul>
		<div id="baseSalaryList_tab">
			<button class="ui-button ui-widget ui-corner-all" id="submit_Btn">변경확정</button><br />
			<br />
			<table id="baseSalaryList_grid"></table>
			<div id="baseSalaryList_pager"></div>
		</div>
	</div>
</body>
</html>