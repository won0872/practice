<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
	$(document).ready(function(){
		codeList = [];
		detailCodeList = [];
		
		// tab css
		$("#codeList_tabs").tabs();
		$("#detailCodeList_tabs").tabs();
		
		showDetailCodeListGrid();
		showGrid();

		
		
		
	});
	

    /* 코드목록 그리드 띄우는 함수 */
    function showCodeGrid(){
    	$.jgrid.gridUnload("#codeList_grid");
    	$("#codeList_grid").jqGrid({
    		data : codeList,
    		datatype : "local",
    		colNames : ['코드번호','코드이름','수정여부','상태'],
			colModel : [ {
				name : 'codeNumber'
			}, {
				name : 'codeName'
			}, {
				name : 'modifiable',
				editable : false
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
				var codeNumber = $(this).getRowData(id).codeNumber;
				
				$.ajax({
					url:"${pageContext.request.contextPath}/base/codeList.do",
					data:{
						"method" : "detailCodelist",
						"code"	: codeNumber
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

						detailCodeList = data.detailCodeList;
						showDetailCodeListGrid();
					}
				});
			}
    	});
    }
	
	//코드리스트 정보 불러오기
	function showGrid(){
		$.ajax({
			url:"${pageContext.request.contextPath}/base/codeList.do",
			data:{
				"method" : "codelist"
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

				codeList = data.codeList;
				showCodeGrid();
			}
		});
	}
	
	//상세 코드 그리드 띄우기
    function showDetailCodeListGrid(){
    	$.jgrid.gridUnload("#detailCodeList_grid");
    	$("#detailCodeList_grid").jqGrid({
    		data : detailCodeList,
    		datatype : "local",
    		colNames : ['상세코드번호','코드번호','상세코드이름','사용가능여부','상태'],
			colModel : [ {
				name : 'detailCodeNumber'
			}, {
				name : 'codeNumber'
			}, {
				name : 'detailCodeName',
				editable : false
			},{
				name : 'detailCodeNameusing'
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
				
				
			}
    	});
    }
	
	
	
	

    
    
    
    
</script>

</head>
<body>
	<div id="codeList_tabs" style="display: inline-block;">
		<ul>
			<li><a href="#codeList_tab">코드관리</a></li>
		</ul>
		<div id="codeList_tab">
			<br /> <br />
			<table id="codeList_grid"></table>
		</div>
	</div>
	
	<div id="detailCodeList_tabs" style="display: inline-block;">
		<ul>
			<li><a href="#detailCodeList_tab">상세코드관리</a></li>
		</ul>
		<div id="detailCodeList_tab">
			<br /> <br />
			<table id="detailCodeList_grid"></table>
		</div>
	</div>
</body>
</html>