<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공제기준관리</title>
<style type="text/css">
#baseDeductionList_tabs {
width : 550px;
margin: auto;
}
</style>
<script>
	$(document).ready(function(){
		/* css 전역 설정 */
		// 작은 버튼들의 css 별도 생성
		$(".small_Btn").css({
			width : "auto",
			height : "auto",
			fontSize : "15px",
			fontFamily : "MD개성체"
		}).button();

		// tab css
		$("#baseDeductionList_tabs").tabs();

		// event handling
		$("#add_Btn").click(addListGridRow);
		$("#del_Btn").click(delListGridRow);
		$("#submit_Btn").click(batchBaseDeductionProcess);

		// grid setting
		$.ajax({
			url:"${pageContext.request.contextPath}/salary/baseDeductionManage.do",
			data:{
				"method" : "findBaseDeductionList"
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

				baseDeductionList = data.baseDeductionList;
				emptyBean = data.emptyBean;
				showBaseDeductionListGrid();
			}
		});
	});

	var baseDeductionList = [];
	var emptyBean;
	var lastId;

	function batchBaseDeductionProcess(){
		var sendData = JSON.stringify(baseDeductionList);

		$.ajax({
			url : "${pageContext.request.contextPath}/salary/baseDeductionManage.do",
			data : {
				"method" : "batchBaseDeductionProcess",
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

    /* 공제기준목록 그리드 띄우는 함수 */
    function showBaseDeductionListGrid(){
    	$.jgrid.gridUnload("#baseDeductionList_grid");
    	$("#baseDeductionList_grid").jqGrid({
    		data : baseDeductionList,
    		datatype : "local",
    		colNames : ['공제항목코드','공제항목명','공제율','상태'],
			colModel : [ {
				name : 'deductionCode',
				cellEdit : false			//수정불가
			}, {
				name : 'deductionName',
				cellEdit : false			//수정불가
			}, {
				name : 'ratio',
				cellEdit : false			//수정불가
			}, {
				name : 'status'
			} ],
			width : 500,
			height : 300,
			viewrecords : true,
			rowNum : 20,
			// grid 조작하는 로직
			editurl : "clientArray",
			cellsubmit : "clientArray",
			ondblClickRow: function(id){
				if(id && id!==lastId){ // id 값이 존재하고 id 의 값이 마지막 선택한 id가 아닐 경우
					// 즉, 수정작업이 진행중이지 않은 다른 행을 더블클릭했을 경우
					$(this).jqGrid('restoreRow',lastId); // 수정하던 창을 수정불가 상태로 만들고
				}
				$(this).jqGrid('editRow',id,true);
				lastId=id; // 현재 행이 수정작업이 진행중인 행으로 바뀌었으므로 lastId에 현재 행의 id를 삽입

				var rowData = $(this).jqGrid("getRowData", id);
				if (rowData.status == "select") { // 서버에서 불러온 자료일 경우 상태를 update로 변경
					$(this).setCell(id, "status", "update");
				}
			}
    	});
    }

	/* 그리드에 행 추가하는 함수 */
	function addListGridRow(){
		var nextSeq = Number($("#baseDeductionList_grid").getGridParam("records")) + 1;
		$("#baseDeductionList_grid").addRowData(nextSeq, emptyBean);
	}

	/* 그리드에 행 삭제(멀티셀렉트 미적용 함수라서 1개씩만 삭제됨) */
	function delListGridRow(){
		var selectedRowId = $("#baseDeductionList_grid").getGridParam("selrow");
		if(selectedRowId != null){
			var data = $("#baseDeductionList_grid").getRowData(selectedRowId);
			if(data.status == "select" || data.status == "update")
				$("#baseDeductionList_grid").setCell(selectedRowId, "status", "delete");
			else if(data.status == "delete")
				$("#baseDeductionList_grid").setCell(selectedRowId, "status", "update");
			else
				$("#baseDeductionList_grid").delRowData(selectedRowId);
		} else {
			alert("삭제할 행을 선택해 주세요!");
		}
	}
</script>
</head>
<body>
	<div id="baseDeductionList_tabs">
		<ul>
			<li><a href="#baseDeductionList_tab">공제기준관리</a></li>
		</ul>
		<div id="baseDeductionList_tab">
			<button class="ui-button ui-widget ui-corner-all" id="add_Btn">추가</button>
			<button class="ui-button ui-widget ui-corner-all" id="del_Btn">삭제/삭제취소</button>
			<button class="ui-button ui-widget ui-corner-all" id="submit_Btn">변경확정</button><br />
			<br />
			<table id="baseDeductionList_grid"></table>
			<div id="baseDeductionList_pager"></div>
		</div>
	</div>
</body>
</html>