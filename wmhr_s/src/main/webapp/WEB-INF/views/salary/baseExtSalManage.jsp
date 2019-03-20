<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>초과수당관리</title>
<style type="text/css">
#baseExtSalList_tabs {
margin : auto;
width : 550px;
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
		$("#baseExtSalList_tabs").tabs();

		// event handling
		$("#submit_Btn").click(modifyBaseExtSalList);

		// grid setting
		$.ajax({
			url:"${pageContext.request.contextPath}/salary/baseExtSalManage.do",
			data:{
				"method" : "findBaseExtSalList"
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

				baseExtSalList = data.baseExtSalList;
				showBaseExtSalListGrid();
			}
		});
	});

	// 전역변수 선언
	var baseExtSalList = [];

	/* do submit */
	function modifyBaseExtSalList(){
		var sendData = JSON.stringify(baseExtSalList);
		// alert(sendData);

		$.ajax({
			url : "${pageContext.request.contextPath}/salary/baseExtSalManage.do",
			data : {
				"method" : "modifyBaseExtSalList",
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

    /* 초과수당목록 그리드 띄우는 함수 */
    function showBaseExtSalListGrid(){
    	$.jgrid.gridUnload("#baseExtSalList_grid");
    	$("#baseExtSalList_grid").jqGrid({
    		data : baseExtSalList,
    		datatype : "local",
    		colNames : ['초과수당코드','초과수당명','배율','상태'],
			colModel : [ {
				name : 'extSalCode'
			}, {
				name : 'extSalName'
			}, {
				name : 'ratio',
				editable : true
			}, {
				name : 'status'
			} ],
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
	<div id="baseExtSalList_tabs">
		<ul>
			<li><a href="#baseExtSalList_tab">초과수당관리</a></li>
		</ul>
		<div id="baseExtSalList_tab">
			<button class="ui-button ui-widget ui-corner-all" id="submit_Btn">변경확정</button><br />
			<br />
			<table id="baseExtSalList_grid"></table>
			<div id="baseExtSalList_pager"></div>
		</div>
	</div>
</body>
</html>