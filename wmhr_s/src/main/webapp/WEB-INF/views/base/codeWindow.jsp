<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CODE</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/script/jqgrid/css/ui.jqgrid.css" /> 
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/script/jqgrid/plugins/ui.multiselect.css" />
	<script src="${pageContext.request.contextPath}/script/jquery/jquery-3.3.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/script/jqgrid/js/i18n/grid.locale-kr.js"></script>
 	<script src="${pageContext.request.contextPath}/script/jqgrid/js/jquery.jqGrid.min.js"></script>
<script>




	$(document).ready(function(){
		if("${param.code}"==""){
			showCode();
		}else{
			showdept();
		}
		var pre = opener.document;
		
		function showdept(){
			$('#grid').jqGrid({
				url:'${pageContext.request.contextPath}/base/codeList.do',
				postData:{
					code:"${param.code}",
					method:"detailCodelist"
				},
				datatype:'json',		
				jsonReader:{			
					root:'detailCodeList'
				},
				colNames:['코드번호','코드명'],
				colModel:[
					{name:'detailCodeNumber',width:0, editable:true},
					{name:'detailCodeName',width:300, editable:true},
				],
				width:'300',
				rowNum : 50,
				viewrecords: true,
				ondblClickRow:function(rowId){
					if("${param.inputCode}"!=""){
						var codeNumber = $("#grid").getCell(rowId, "detailCodeNumber");
						$("#${param.inputCode}", opener.document).val(codeNumber);
					}

					var codeName = $("#grid").getCell(rowId, "detailCodeName");
						$("#${param.inputText}", opener.document).val(codeName);
						window.close();

				},
				
				loadError:function(xhr){
					//alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
					if(xhr.status==500){
						alert("Internal Server Error");
					}
				}
			});
			$("#grid").jqGrid("hideCol", ['detailCodeNumber']);
		}
		
		function showCode(){
			$('#grid').jqGrid({
				url:'${pageContext.request.contextPath}/base/codeList.do',
				postData:{
					code1:"${param.code1}",
					code2:"${param.code2}",
					code3:"${param.code3}",
					method:"detailCodelistRest"
				},
				datatype:'json',		
				jsonReader:{			
					root:'detailCodeList'
				},
				colNames:['코드번호','코드명'],
				colModel:[
					{name:'detailCodeNumber',width:0, editable:true},
					{name:'detailCodeName',width:300, editable:true},
				],
				width:'300',
				viewrecords: true,
				ondblClickRow:function(rowId){
					if("${param.inputCode}"!=""){
						var codeNumber = $("#grid").getCell(rowId, "detailCodeNumber");
						$("#${param.inputCode}", opener.document).val(codeNumber);
					}

					var codeName = $("#grid").getCell(rowId, "detailCodeName");
						$("#${param.inputText}", opener.document).val(codeName);
						window.close();

				},
				/* ondblClickRow:function(rowId){
					if("${param.inputCode}"!=""){
						var codeNumber = $("#codeList_grid").getCell(rowId, "detailCodeNumber");
						$("#${param.inputCode}", opener.document).val(codeNumber)
					}

					var codeName = $("#codeList_grid").getCell(rowId, "detailCodeName");
						$("#${param.inputName}", opener.document).val(codeName)	
						
						window.close();

				} */
				loadError:function(xhr){
					//alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
					if(xhr.status==500){
						alert("Internal Server Error");
					}
				}
			});
			$("#grid").jqGrid("hideCol", ['detailCodeNumber']);
		}
		
		
		
	});
	
</script>
</head>
<body>
<div>
	<table id="grid"></table>
</div>
</body>
</html>