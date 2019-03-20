<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Tabs - Default functionality</title>
  
  <style>
  #divImg{
  	display: inline-block;
  }
  #divEmpinfo{
  	display: inline-block;
  }
  #divEmpDinfo{
  	display: inline-block;
  }
  </style>
  
  
  <script>
  var empList=[];
  var empDList=[];
  empName="";
  
  var selectedEmpBean, updatedEmpBean = {}; // 사원의 상세정보를 저장하는 객체들
      emptyFamilyInfoBean = {};
  	  emptyCareerInfoBean = {};
  	  emptyWorkInfoBean = {};
  	  emptyEducationInfoBean = {};
  	  emptyLicenseInfoBean = {}; // 그리드에 새 행을 추가하기 위한 비어있는 객체들
  var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함)


  
 
  
  $(document).ready(function(){
	  $("#address").postcodifyPopUp();
	  
	  /* 사진찾기 버튼 */
		$("#findPhoto").button().click(function(){
		    $("#emp_img_file").click(); //사진찾기 버튼을 누르면 숨겨진 file 태그를 클릭
		});
	  
		// 사진 등록 form의 ajax 부분
 		$("#emp_img_form").ajaxForm({
			dataType: "json",
			success: function(responseText, statusText, xhr, $form){
				alert(responseText.errorMsg);
				location.reload();
			}
		});
		

	  
	  
	  /* 코드 선택창 띄우기 */
	  function getCode(code,inputText,inputCode){
			option="width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
			window.open("${pageContext.request.contextPath}/base/codeWindow.html?code="
							+code+"&inputText="+inputText+"&inputCode="+inputCode,"newwins",option);
	}
	  /* 전역변수 초기화 함수 */
	  function initField(){
	  		selectedEmpBean, updatedEmpBean = {};
	  		emptyFamilyInfoBean = {};
	  		emptyCareerInfoBean = {};
	  		emptyWorkInfoBean = {};
	  		emptyEducationInfoBean = {};
	  		emptyLicenseInfoBean = {};
	  		lastId = 0;
	  }
	  
	  
		/* 현재 표시된 모든 정보를 비우는 함수 */
	  function clearEmpInfo(){
	  		// 찾았던 사진을 기본 사진으로 되돌린다
	  		$("#profileImg").attr("src", "${pageContext.request.contextPath }/profile/profile.png");

	  		$("input:text").each(function() {
	  			$(this).val("") //모든 input text타입의 value값을 비운다
	  		})
	  }
		

	  
	  
	  
	/* 	검색버튼 이벤트  */
	  function makeEmpList(grid,value){
		  $.ajax({
				url : "${pageContext.request.contextPath}/emp/list.do",
				data:{
					"method":"emplist",
					"value":value
				},
				dataType : "json",
				success : function(data){
					if(data.errorCode < 0){
						var str = "내부 서버 오류가 발생했습니다\n";
						str += "관리자에게 문의하세요\n";
						str += "에러 위치 : " + $(this).attr("id");
						str += "에러 메시지 : " + data.errorMsg;
						alert(str);
						return;
					}
					
					empList = data.list;
					if(grid=="dept")
						showEmpListDeptGrid();
					else{
						showEmpListNameGrid();
					}
					
				},
				error:function(a,b,c){
					alert(b);
				}
			});
		  
	  }
	  
	  //부서별 사원 기본 정보 조회
	  function showEmpListDeptGrid(){
		  /* $("#deptfindgrid").jqGrid('GridUnload'); */ 
		  $.jgrid.gridUnload("#deptfindgrid");
		  $("#deptfindgrid").jqGrid({
				data : empList,
				datatype : "local",
				colNames:['사원코드','사원명','부서','직급','성별','전화번호','이메일','거주지','최종학력','사진'],
				colModel:[
					{name:'empCode',width:0, editable:false},
					{name:'empName',width:100, editable:false},
					{name:'deptName',width:100, editable:false},
					{name:'position',width:100, editable:false},
					{name:'gender',width:0, editable:false},
					{name:'mobileNumber',width:0, editable:false},
					{name:'email',width:200, editable:false},
					{name:'address',width:0, editable:false},
					{name:'lastSchool',width:0, editable:false},
					{name:'imgExtend',width:0, editable:false}
				],
				height: 250,
				viewrecords : true,
				multiselect : true,
				multiboxonly : true,
				onSelectRow : function(id){
					 
					var empCode = $(this).getRowData(id).empCode;
					emp = $(this).getRowData(id);
					

					
					$.ajax({
						url:"${pageContext.request.contextPath}/emp/empDetail.do",
						data:{
							"method":"findAllEmployeeInfo",
							"empCode":empCode
						},
						dataType : "json",
						success : function(data){
							if(data.errorCode < 0){
								var str = "내부 서버 오류가 발생했습니다\n";
								str += "관리자에게 문의하세요\n";
								str += "에러 위치 : " + $(this).attr("id");
								str += "에러 메시지 : " + data.errorMsg;
								alert(str);
								return;
							} 
							empDList = data.empBean;
							
						 	initField(); // 전역변수 초기화
							clearEmpInfo(); // 상세정보, 재직정보 칸 초기화
							
							setAllEmptyBean(data);
							
							selectedEmpBean = $.extend(true, {}, data.empBean); // 취소버튼을 위한 임시 저장공간에 딥카피
							updatedEmpBean = $.extend(true, {}, data.empBean); // 변경된 내용이 들어갈 공간에 딥카피
							// 객체를 딥카피 하는 이유는 객체 내에 저장된 주소타입의 변수들이 제대로 복사되지 않기 때문임 

							/* 회원정보를 불러와야 기타정보들의 객체에 제대로 값이 들어가기 때문에 이곳에서 부름   */
							showDetailInfo();
							showWorkInfoListGrid();
							showCareerInfoListGrid();
							showEducationInfoListGrid();
							showLicenseInfoListGrid();
							showFamilyInfoListGrid();
						
						}
					});
				}
			});
		  $("#deptfindgrid").jqGrid("hideCol", ['empCode','mobileNumber','address','lastSchool','gender','imgExtend']);
	  }
	  
	//이름별 사원 기본 정보 조회
	  function showEmpListNameGrid(){
		  /* $("#namefindgrid").jqGrid('GridUnload');  */
		  $.jgrid.gridUnload("#namefindgrid");
		  $("#namefindgrid").jqGrid({
				data : empList,
				datatype : "local",
				colNames:['사원코드','사원명','부서','직급','성별','전화번호','이메일','거주지','최종학력','사진'],
				colModel:[
					{name:'empCode',width:0, editable:false},
					{name:'empName',width:100, editable:false},
					{name:'deptName',width:100, editable:false},
					{name:'position',width:100, editable:false},
					{name:'gender',width:0, editable:false},
					{name:'mobileNumber',width:0, editable:false},
					{name:'email',width:200, editable:false},
					{name:'address',width:0, editable:false},
					{name:'lastSchool',width:0, editable:false},
					{name:'imgExtend',width:0, editable:false}
				],
				height: 250,
				viewrecords : true,
				multiselect : true,
				multiboxonly : true,
				onSelectRow : function(id){
					
					var empCode = $(this).getRowData(id).empCode;
					emp = $(this).getRowData(id);
					
					$.ajax({
						url:"${pageContext.request.contextPath}/emp/empDetail.do",
						data:{
							"method":"findAllEmployeeInfo",
							"empCode":empCode
						},
						dataType : "json",
						success : function(data){
							if(data.errorCode < 0){
								var str = "내부 서버 오류가 발생했습니다\n";
								str += "관리자에게 문의하세요\n";
								str += "에러 위치 : " + $(this).attr("id");
								str += "에러 메시지 : " + data.errorMsg;
								alert(str);
								return;
							} 
							empDList = data.empBean;
							
							initField(); // 전역변수 초기화
							clearEmpInfo(); // 상세정보, 재직정보 칸 초기화
							
							
							
							setAllEmptyBean(data);
							
							selectedEmpBean = $.extend(true, {}, data.empBean); // 취소버튼을 위한 임시 저장공간에 딥카피
							updatedEmpBean = $.extend(true, {}, data.empBean); // 변경된 내용이 들어갈 공간에 딥카피
							// 객체를 딥카피 하는 이유는 객체 내에 저장된 주소타입의 변수들이 제대로 복사되지 않기 때문임 

							/* 회원정보를 불러와야 기타정보들의 객체에 제대로 값이 들어가기 때문에 이곳에서 부름   */
							showDetailInfo();
							showWorkInfoListGrid();
							showCareerInfoListGrid();
							showEducationInfoListGrid();
							showLicenseInfoListGrid();
							showFamilyInfoListGrid();
							
						
						}
					});
				}
			});
		  $("#namefindgrid").jqGrid("hideCol", ['empCode','mobileNumber','address','lastSchool','gender','imgExtend']);
	  }
	  
	  
	  /* 새로운 정보들을 추가하기 위한 빈 객체 세팅 */
	  function setAllEmptyBean(data){
	  		

	  		emptyFamilyInfoListBean = data.emptyFamilyInfoBean;
	  		emptyCareerInfoListBean = data.emptyCareerInfoBean;
	  		emptyWorkInfoListBean = data.emptyWorkInfoBean;
	  		emptyEducationInfoListBean = data.emptyEducationInfoBean;
	  		emptyLicenseInfoListBean = data.emptyLicenseInfoBean;
	  		
	  		
	  		emptyFamilyInfoListBean.status = "insert";
	  		emptyFamilyInfoListBean.empCode = data.empBean.empCode;
	  		emptyCareerInfoListBean.status = "insert";
	  		emptyCareerInfoListBean.empCode = data.empBean.empCode;
	  		emptyWorkInfoListBean.status = "insert";
	  		emptyWorkInfoListBean.empCode = data.empBean.empCode;
	  		emptyEducationInfoListBean.status = "insert";
	  		emptyEducationInfoListBean.empCode = data.empBean.empCode;
	  		emptyLicenseInfoListBean.status = "insert";
	  		emptyLicenseInfoListBean.empCode = data.empBean.empCode;
	  }
	  

	  
	  /* DatePicker 달력함수 */
	  function getDatePicker(CellId) {
	      			CellId.datepicker({
	      				changeMonth : true,
	      				changeYear : true,
	      				dateFormat : "yy/mm/dd",
	      				showAnim: "slide",
	      				dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
	      				monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
	      				yearRange: "1900:2050",
	      			});
	  }
	  
	  function getDatePickerNumber($CellId) {
			$CellId.datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "ymmdd",
				showAnim: "slide",
				dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
				monthNamesShort : [ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" ],
				yearRange: "1900:2050",
			});
}
	  
	////////////////////////////////////////////////////그리드이벤트////////////////////////////////////////////////////////	  
	  
	  /* 그리드에 행 추가하는 함수 */
	  function addListGridRow(){
	  		var key = $(this).attr("id").split("_")[1];
	  		var emptyBean = {
	  			"family" : emptyFamilyInfoListBean,
	  			"career" : emptyCareerInfoListBean,
	  			"work" : emptyWorkInfoListBean,
	  			"education" : emptyEducationInfoListBean,
	  			"license" : emptyLicenseInfoListBean
	  		}
	  		/* 
	  		 * appRowData가 아닌 addRow로 cell을 추가하면 굉장히 편하지만 이 방법을 사용하는 이유는
	  		 * appRow로 cell을 추가하면 editable에 따라 textbox가 자동으로 생성된 상태로 cell이 추가됨
	  		 * 이걸 일일이 처리하는것보다 빈 객체를 가져와 처리하는게 편하기 때문에 addRowData를 사용
	  		 */
	  		var nextSeq = Number($("#" + key + "InfoListGrid").getGridParam("records")) + 1;
	  		$("#" + key + "InfoListGrid").addRowData(nextSeq, emptyBean[key]);
	  }
	  
	  /* 그리드에 행 삭제 */
	  function delListGridRow(){
	  		var key = $(this).attr("id").split("_")[1];
	  		var selectedRowId = $("#"+ key +"InfoListGrid").getGridParam("selrow");
	  		if(selectedRowId != null){
	  			var data = $("#" + key + "InfoListGrid").getRowData(selectedRowId);
	  			if(data.status == "select" || data.status == "update")
	  				$("#" + key + "InfoListGrid").setCell(selectedRowId, "status", "delete");
	  			else if(data.status == "delete")
	  				$("#" + key + "InfoListGrid").setCell(selectedRowId, "status", "update");
	  			else
	  				$("#" + key + "InfoListGrid").delRowData(selectedRowId);
	  		} else {
	  			alert("삭제할 행을 선택해 주세요!");
	  		}
	  }
	  
	  
	  /* 상세정보 탭의 저장 버튼 */
		$("#modifyEmp_Btn").click(function(){
			if(updatedEmpBean == null){
				alert("저장할 내용이 없습니다");
			} else {
				var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
				if(flag)
					modifyEmp();
			}
		});
	  
	  /* 사진 찾기 버튼 */
	  $("#findImg_btn").click(function(){
			if(updatedEmpBean == null){
				alert("저장할 내용이 없습니다");
			} else {
				var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
				if(flag)
					modifyEmp();
			}
		});
	  
	  
	  /* 변경된 정보들을 저장하는 함수 */
	  function saveEmpInfo(){
		  
		  
			
			updatedEmpBean.status = "update";
			updatedEmpBean.empCode = $("#empCode").val();
			updatedEmpBean.empName = $("#empName").val();
			updatedEmpBean.birthdate = $("#birthdate").val();
			updatedEmpBean.deptName = $("#deptName").val();
			updatedEmpBean.position = $("#position").val();
			updatedEmpBean.email = $("#email").val();
			updatedEmpBean.gender = $("#gender").val();
			updatedEmpBean.mobileNumber = $("#mobileNumber").val();
			updatedEmpBean.lastSchool = $("#lastSchool").val();
			updatedEmpBean.address = $("#address").val();
			updatedEmpBean.detailAddress = $("#detailAddress").val();
			updatedEmpBean.postNumber = $("#postNumber").val();
			
			
			/* 사진, 생년월일  추가해줘야함*/

	}
	  
	  /* 저장 */
	  function modifyEmp(){
			saveEmpInfo();
			var sendData = JSON.stringify(updatedEmpBean);
		
			$.ajax({
				url : "${pageContext.request.contextPath}/emp/empDetail.do",
				data : {
					"method" : "modifyEmployee",
					"sendData" : sendData
				},
				dataType : "json",
				success : function(data) {
					if(data.errorCode < 0){
						alert("저장에 실패했습니다");
					} else {
						/* 선택한 사진이 있다면 저장 버튼을 눌렀을때에 사진 저장도 같이 되게함 */
						 if($("#emp_img_file").val()!=""){
						    $("#emp_img_form").submit();
						}
						alert("저장되었습니다"); 
					}
					
					location.reload();
				}
			});
	}
	  
	  function rollBackEmpInfo(){
			clearEmpInfo(); // 모든 정보를 지운 후

			// 서버에서 불러온 미리 저장한 EmpBean의 정보를 수정하던 정보에 엎어씌움
			updatedEmpBean = $.extend(true, {}, selectedEmpBean);
			// 딥카피하는 이유는 주소타입의 변수가 제대로 카피되지 않기 때문임

			//상제정보와 사진을 다시 띄운다
			showDetailInfo();
			showEmpImg();
	}
	  
	  
	  /* 저장된 사진 불러오는 함수 */
	  function showEmpImg(){
	  		var path = "${pageContext.request.contextPath }/profile/profile.png";

	  		if(selectedEmpBean.imgExtend!=null){
	  			if(selectedEmpBean.detailInfo.imgExtend!=null){
	  				path = "/profile/";
	  				path += selectedEmpBean.empCode;
	  				path += "." + selectedEmpBean.imgExtend;
	  			}
	  		}
	  		$("#emp_img").attr("src", path);
	  }
	  
	  
	  
	  
	  /* 상세정보, 재직정보 띄워주는 함수 *///////////////////////////////////////////////////////////////////////여기수정해야될듯
	  function showDetailInfo(){

	  		$("#empCode").val(selectedEmpBean.empCode);
	  		$("#empName").val(selectedEmpBean.empName);
	  		$("#deptName").val(selectedEmpBean.deptName);
	  		$("#position").val(selectedEmpBean.position);
	  		$("#birthdate").val(selectedEmpBean.birthdate);
	  		$("#gender").val(selectedEmpBean.gender);
	  		$("#mobileNumber").val(selectedEmpBean.mobileNumber);
	  		$("#address").val(selectedEmpBean.address);
	  		$("#detailAddress").val(selectedEmpBean.detailAddress);
	  		$("#postNumber").val(selectedEmpBean.postNumber);
	  		$("#email").val(selectedEmpBean.email);
	  		$("#lastSchool").val(selectedEmpBean.lastSchool);
	  		$("#profileImg").attr("src","/profile/"+selectedEmpBean.empCode+"."+selectedEmpBean.imgExtend);

	  }
	  
	  
	  /* 사원 삭제 버튼 눌렀을 때 실행되는 함수 */
	  function removeEmpList() {
	  		var selectedRowIds = $("#deptfindgrid").getGridParam("selarrrow");
	  		
	  		if(selectedRowIds.length==0){
	  		alert("삭제할 사원을 선택해 주세요");
	  		
	  			return;
	  		} 

	  		var selectedRowData = [];

	  		$.each(selectedRowIds, function(index, id){
	  			var data = $("#deptfindgrid").getRowData(id);
	  			selectedRowData.push(data);
	  		});

	  		var sendData = JSON.stringify(selectedRowData);

	  		$.ajax({
	  			url : "${pageContext.request.contextPath}/emp/empDetail.do",
	  			data : {
	  				"method" : "removeEmployeeList",
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
	  
	  
	 
	  
	  
	/*////////////////////////////////////////////////// 세부상세정보 그리드 띄우기 //////////////////////////////////////////////*/
	  
	  //재직정보 그리드 호출
	  function showWorkInfoListGrid(){
			/* $("#workInfoListGrid").jqGrid('GridUnload');  */		//그리드 리로드
			 $.jgrid.gridUnload("#workInfoListGrid");
			$("#workInfoListGrid").jqGrid({
					data : updatedEmpBean.workInfoList,
					datatype : "local",
					colNames:['사원코드','적용일','입사일','퇴사일','직종','고용형태','호봉','직급','부서','상태'],
					colModel:[
						{name:'empCode',width:50, editable:false},
						{name:'workInfoDays',width:100, editable:true, editoptions:{
							dataInit:function(cell){
								getDatePickerNumber($(cell)); // DatePicker를 띄움
							}} },
						{name:'hiredate',width:100, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); 
							    $(cell).attr("readonly","true");
							}} },
						{name:'retireDate',width:100, editable:false,editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); 
							}} },
						{name:'occupation',width:50, editable:false,
									editoptions:{
										dataInit:function(cell,col){
										    getCode("CO-03",col.id); // Code 선택창띄우기
										}}},
						{name:'employmentType',width:50, editable:false ,
									editoptions:{
										dataInit:function(cell,col){
											getCode("CO-05",col.id);
										}}},
						{name:'hobong',width:50,editable:false,
									editoptions:{
										dataInit:function(cell,col){
											getCode("CO-12",col.id);
										}}},
						{name:'position',width:50, editable:false,
									editoptions:{
										dataInit:function(cell,col){
								    		getCode("CO-04",col.id); 
										}}},
						{name:'deptName',width:50, editable:false,
									editoptions:{
										dataInit:function(cell,col){
											getCode("CO-07",col.id);
										}}},
						{name : 'status', width:50,editable : false }
					],
					height:"250px",
					cellEdit : true,
					editurl : "clientArray",
					cellsubmit : "clientArray",
					loadError:function(xhr){
						alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
						if(xhr.status==500){
							alert("Internal Server Error");
						}
					},ondblClickRow: function (rowid, iRow,iCol) {						
						if(iCol > 1 && iCol < 9){
							var status = $(this).getCell(rowid, 'status');
							if (status == "normal") {
								$(this).setCell(rowid, "status", "update");
							}
							
							var colModels = $(this).getGridParam('colModel')
							 var colName = colModels[iCol].name;
							$(this).setColProp(colName, { editable: true });					        
						}
					},afterEditCell: function (rowid, cellname, value, iRow, iCol) {
						$(this).setColProp(cellname, { editable: false }); 
					}
				});
			}
	 		
	  //가족정보 그리드 호출
	  function showFamilyInfoListGrid(){
			/* $("#familyInfoListGrid").jqGrid('GridUnload'); 	 */	//그리드 리로드
			$.jgrid.gridUnload("#familyInfoListGrid");
			$("#familyInfoListGrid").jqGrid({
					data : updatedEmpBean.familyInfoList,
					datatype : "local",
					colNames:['사원코드','일련번호','성명','관계','생년월일','동거여부','상태'],
					colModel:[
						{name:'empCode',width:50, editable:false},
						{name:'familyCode',width:50, editable:false},
						{name:'familyName',width:50, editable:false},
						{name:'relation',width:50, editable:false,
							editoptions:{
								dataInit:function(cell,col){
								    getCode("CO-06",col.id);
								}}},
						{name:'birthdate',width:100, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
							}} },
						{name:'liveTogether',width:50, editable:false},
						{name : 'status', width:50,editable : false }
					],
					height:"250px",
					editurl : "clientArray",
					cellsubmit : "clientArray",
					cellEdit : true,
					viewrecords: true,
					loadError:function(xhr){
						alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
						if(xhr.status==500){
							alert("Internal Server Error");
						}
					},ondblClickRow: function (rowid, iRow,iCol) {						
						if(iCol > 1 && iCol < 6){
							var status = $(this).getCell(rowid, 'status');
							if (status == "normal") {
								$(this).setCell(rowid, "status", "update");
							}
							
							var colModels = $(this).getGridParam('colModel')
							 var colName = colModels[iCol].name;
							$(this).setColProp(colName, { editable: true });					        
						}
					},afterEditCell: function (rowid, cellname, value, iRow, iCol) {
						$(this).setColProp(cellname, { editable: false }); 
					}
				});
		}
	  
		//경력정보 그리드 호출
	  function showCareerInfoListGrid(){
			/* $("#careerInfoListGrid").jqGrid('GridUnload');  */		//그리드 리로드
			$.jgrid.gridUnload("#careerInfoListGrid");
			$("#careerInfoListGrid").jqGrid({
					data : updatedEmpBean.careerInfoList,
					datatype : "local",
					colNames:['사원코드','일련번호','회사명','직종','담당업무','입사일','퇴사일','상태'],
					colModel:[
						{name:'empCode',width:50, editable:false},
						{name:'careerCode',width:50, editable:false},
						{name:'companyName',width:50, editable:false},
						{name:'occupation',width:50, editable:false,
							editoptions:{
								dataInit:function(cell,col){
								    getCode("CO-03",col.id);
									$(cell).attr("readonly","true");}}},
						{name:'assignmentTask',width:50, editable:false},
						{name:'exHiredate',width:100,editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
							}} },
						{name:'exRetirementDate',width:100, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
							}} },
						{name : 'status', width:50,editable : false }
					],
					height:"250px",
					editurl : "clientArray",
					cellsubmit : "clientArray",
					cellEdit : true,					
					viewrecords: true,
					loadError:function(xhr){
						alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
						if(xhr.status==500){
							alert("Internal Server Error");
						}
					},ondblClickRow: function (rowid, iRow,iCol) {						
						if(iCol > 1 && iCol < 7){
							var status = $(this).getCell(rowid, 'status');
							if (status == "normal") {
								$(this).setCell(rowid, "status", "update");
							}
							var colModels = $(this).getGridParam('colModel')
							 var colName = colModels[iCol].name;
							$(this).setColProp(colName, { editable: true });					        
						}
					},afterEditCell: function (rowid, cellname, value, iRow, iCol) {
						$(this).setColProp(cellname, { editable: false }); 
					}
				});
			}
		
	//학력정보 그리드 호출
	  function showEducationInfoListGrid(){
			$.jgrid.gridUnload("#educationInfoListGrid");
			$("#educationInfoListGrid").jqGrid({
					data : updatedEmpBean.educationInfoList,
					datatype : "local",
					colNames:['사원코드','일련번호','학교명','전공','입학일','졸업일','학점','상태'],
					colModel:[
						{name:'empCode',width:50, editable:false},
						{name:'educationCode',width:100, editable:false},
						{name:'shcoolName',width:100, editable:false},
						{name:'major',width:100, editable:false},
						{name:'entranceDate',width:50, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
							}} },
						{name:'graduateDate',width:50, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
							}} },
						{name:'grade',width:50,editable:false},
						{name : 'status', width:50,editable : false }
					],
					height:"250px",
					editurl : "clientArray",
					cellsubmit : "clientArray",
					cellEdit : true,					
					viewrecords: true,
					loadError:function(xhr){
						alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
						if(xhr.status==500){
							alert("Internal Server Error");
						}
					},ondblClickRow: function (rowid, iRow,iCol) {
						if(iCol > 1 && iCol < 7){
							var colModels = $(this).getGridParam('colModel')
							var colName = colModels[iCol].name;
							$(this).setColProp(colName, { editable: true });
							
					        var status = $(this).getCell(rowid, 'status');
							if (status == "normal") {
								$(this).setCell(rowid, "status", "update");
							}
						}
					},afterEditCell: function (rowid, cellname, value, iRow, iCol) {
						$(this).setColProp(cellname, { editable: false }); 
					}
				});
			}

	//자격증정보 그리드 호출
	  function showLicenseInfoListGrid(){
			/* $("#licenseInfoListGrid").jqGrid('GridUnload'); */ 		//그리드 리로드
			$.jgrid.gridUnload("#licenseInfoListGrid");
			$("#licenseInfoListGrid").jqGrid({
					data : updatedEmpBean.licenseInfoList,
					datatype : "local",
					colNames:['사원코드','일련번호','자격명','취득일','만료일','급수','발행기관','발급번호','상태'],
					colModel:[
						{name:'empCode',width:50, editable:false},
						{name:'licenseCode',width:100, editable:false},
						{name:'licenseName',width:100, editable:false},
						{name:'getDate',width:100, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
								$(cell).attr("readonly","true");}} },
						{name:'expireDate',width:50, editable:false, editoptions:{
							dataInit:function(cell){
							    getDatePicker($(cell)); // DatePicker를 띄움
								$(cell).attr("readonly","true");}} },
						{name:'licenseLevel',width:50, editable:false},
						{name:'licenseCenter',width:50,editable:false},
						{name:'issueNumber',width:50, editable:false},
						{name : 'status', width:50,editable : false }
					],
					height:"250px",
					editurl : "clientArray",
					cellsubmit : "clientArray",
					cellEdit : true,					
					viewrecords: true,
					loadError:function(xhr){
						alert(xhr.readyStatus+","+xhr.status+","+xhr.statusText);
						if(xhr.status==500){
							alert("Internal Server Error");
						}
					},ondblClickRow: function (rowid, iRow,iCol) {
						if(iCol > 1 && iCol < 8){
							var status = $(this).getCell(rowid, 'status');
							if (status == "normal") {
								$(this).setCell(rowid, "status", "update");
							}
							
							var colModels = $(this).getGridParam('colModel')
							 var colName = colModels[iCol].name;
							$(this).setColProp(colName, { editable: true });
							
					        
						}
					},afterEditCell: function (rowid, cellname, value, iRow, iCol) {
						$(this).setColProp(cellname, { editable: false }); 
					}

				});
			}
		
		
	  
	  $( "#tabs" ).tabs();
	  $( "#tabs1" ).tabs();
	  $( "#sel_dept" ).selectmenu();
	    
	  
	  
	  
	  $("#btn_name_search").click(function(){
		  makeEmpList("name",$("#txt_name").val()); 
	  })
	  $("#btn_dept_search").click(function(){
		  makeEmpList("dept",$("#sel_dept").val()); 
	  })
	  
	  
	  
	  
	  $("#add_work_btn").click(addListGridRow);
	  $("#add_career_btn").click(addListGridRow);
	  $("#add_education_btn").click(addListGridRow);
	  $("#add_license_btn").click(addListGridRow);
	  $("#add_family_btn").click(addListGridRow);
	  
	  $("#del_work_btn").click(delListGridRow);
	  $("#del_career_btn").click(delListGridRow);
	  $("#del_education_btn").click(delListGridRow);
	  $("#del_license_btn").click(delListGridRow);
	  $("#del_family_btn").click(delListGridRow);
	  
	
	  
	  
	  
	  
	  $("#deptName").click(function(){
		  getCode("CO-07","deptName");
	  });
	  $("#position").click(function(){
		  getCode("CO-04","position");
	  });
	  $("#gender").click(function(){
		  getCode("CO-01","gender");
	  });
	  $("#lastSchool").click(function(){
		  getCode("CO-02","lastSchool");
	  });
	  
	  $("#birthdate").click(getDatePicker($("#birthdate")));
	  
	  
	  $("#btn_dept_del").click(function(){
		  removeEmpList();
	  });
	  
	  showEmpListDeptGrid();
	  showEmpListNameGrid();
	  showWorkInfoListGrid();
	  showCareerInfoListGrid();
	  showEducationInfoListGrid();
	  showLicenseInfoListGrid();
	  showFamilyInfoListGrid();
	  
	  
	 
  });
  
  
  /* 사진찾기 버튼 눌렀을 때 실행되는 함수 */
	function readURL(input){
			$("#emp_img_empCode").val(updatedEmpBean.empCode);
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function (e) {
					// 이미지 Tag의 SRC속성에 읽어들인 File내용을 지정 (아래 코드에서 읽어들인 dataURL형식)
					$("#profileImg").attr("src", e.target.result);
				}
			reader.readAsDataURL(input.files[0]); //File내용을 읽어 dataURL형식의 문자열로 저장
			}
	}
  

  </script>

</head>
<body>
 
<div id="tabs" style="width:600px; height:400px; position: absolute; top:250px; left:100px;">
  <ul>
  <li><a href="#tabs-11">부서명 검색</a></li>
    <li><a href="#tabs-1">사원명 검색</a></li>
  </ul>
  
  <div id="tabs-1">
  	<input type="text" id="txt_name" class="ui-button ui-widget ui-corner-all">
	<button id="btn_name_search" class="ui-button ui-widget ui-corner-all">검색</button>
	<button id="btn_name_del" class="ui-button ui-widget ui-corner-all">삭제</button><br/><br/>
	<table id="namefindgrid"></table>
  </div>
    <div id="tabs-11">
  	<select id="sel_dept">
					<option value="전체부서">전체부서</option>
					<option value="회계팀">회계팀</option>
					<option value="인사팀">인사팀</option>
					<option value="전산팀">전산팀</option>
				</select>
	<button id="btn_dept_search" class="ui-button ui-widget ui-corner-all">검색</button>
	<button id="btn_dept_del" class="ui-button ui-widget ui-corner-all">삭제</button><br/><br/>
	<table id="deptfindgrid"></table>
  </div>
  
</div>


<div id="tabs1" style="width:750px; height:700px; position: absolute; top:250px; left:730px;">
  <ul>
    <li><a href="#tabs-0">기본 정보</a></li>
    <li><a href="#tabs-2">재직 정보</a></li>
    <li><a href="#tabs-3">경력 정보</a></li>
    <li><a href="#tabs-4">학력 정보</a></li>
    <li><a href="#tabs-5">자격증 정보</a></li>
    <li><a href="#tabs-6">가족 정보</a></li>
  </ul>
   <div id="tabs-0" align="left" >
   

    			<div id="divImg">
					 <img id="profileImg" src="/profile/profile.png" width="180px" height="200px"><br>
					 <form id="emp_img_form" action="/base/empImg.do" enctype="multipart/form-data" method="post">
						<input type="hidden" name="empCode" id="emp_img_empCode">
						<input type="file" name="empImgFile" style="display: none;" id="emp_img_file" onChange="readURL(this)">
						<button type="button" style="width: 150px;" class="ui-button ui-widget ui-corner-all" id="findPhoto" >사진찾기</button><br />
					</form><br /> 
				</div>
				<div id="divEmpInfo" style=" display: inline-block; position:absolute; margin-left: 50px; " >
				<br/>
				<table>
				<tr>
					<td><font>사원코드</font></td>
					<td><input class="ui-button ui-widget ui-corner-all" id="empCode" readonly></td>
				</tr>
				<tr>
					<td><font>이름</font></td>
					<td><input class="ui-button ui-widget ui-corner-all" id="empName"></td>
				</tr>
				<tr>
					<td><font>부서</font></td>
					<td><input class="ui-button ui-widget ui-corner-all" id="deptName" readonly></td>
				</tr>
				<tr>
					<td><font>직급</font></td>
					<td><input class="ui-button ui-widget ui-corner-all" id="position" readonly></td>
				</tr>
				</table>
					<!-- <font style="font-size: 10px">전화번호 </font><br/>--><br/> 
					<!-- IMG_EXTEND -->
					
				</div>
				<hr>
				<div id="divEmpDinfo" style="display:block; position:relative; width: 700px; height: 300px; margin-left: 230px; margin-top: 50px" >
				
				<table>
				<tr><td><font>e-mail</font></td>
				<td><input class="ui-button ui-widget ui-corner-all" id="email"></td></tr>
				<tr><td><font>휴대전화 </font></td>
				<td><input class="ui-button ui-widget ui-corner-all" id="mobileNumber"></td></tr>
				<tr><td><font>생년월일</font></td>
				<td><input class="ui-button ui-widget ui-corner-all" id="birthdate" readonly></td></tr>
				<tr><td><font>성별</font></td>
				<td><input class="ui-button ui-widget ui-corner-all" id="gender" readonly></td></tr>
				<tr><td><font>최종학력</font></td>
				<td><input class="ui-button ui-widget ui-corner-all" id="lastSchool" readonly></td></tr>
				<tr><td><font>주소</font></td>
				<td><input class="postcodify_address ui-button ui-widget ui-corner-all" id="address" readonly></td></tr>
				<tr><td><font>상세주소</font></td>
				<td><input class="postcodify_details ui-button ui-widget ui-corner-all" id="detailAddress" ></td></tr>
				<tr><td><font>우편번호</font></td>
				<td><input class="postcodify_postcode5 ui-button ui-widget ui-corner-all" id="postNumber" readonly></td></tr>
				
				</table>
					<input type="hidden" id="imgExtend">
				</div>
     
    
  </div>
  <div id="tabs-2">
  	<input type="button" id="add_work_btn" class="ui-button ui-widget ui-corner-all" value="추가">
  	<input type="button" id="del_work_btn" class="ui-button ui-widget ui-corner-all" value="수정/삭제"><br/><br/>
    <table id="workInfoListGrid"></table>
  </div>
  <div id="tabs-3">
  	<input type="button" id="add_career_btn" class="ui-button ui-widget ui-corner-all" value="추가">
  	<input type="button" id="del_career_btn" class="ui-button ui-widget ui-corner-all" value="수정/삭제"><br/><br/>
    <table id="careerInfoListGrid"></table>
  </div>
  <div id="tabs-4">
  	<input type="button" id="add_education_btn" class="ui-button ui-widget ui-corner-all" value="추가">
  	<input type="button" id="del_education_btn" class="ui-button ui-widget ui-corner-all" value="수정/삭제"><br/><br/>
  	<table id="educationInfoListGrid"></table>
  </div>
  <div id="tabs-5">
  	<input type="button" id="add_license_btn" class="ui-button ui-widget ui-corner-all" value="추가">
  	<input type="button" id="del_license_btn" class="ui-button ui-widget ui-corner-all" value="수정/삭제"><br/><br/>
   	<table id="licenseInfoListGrid"></table>
  </div>
  <div id="tabs-6">
  	<input type="button" id="add_family_btn" class="ui-button ui-widget ui-corner-all" value="추가">
  	<input type="button" id="del_family_btn" class="ui-button ui-widget ui-corner-all" value="수정/삭제"><br/><br/>
   	<table id="familyInfoListGrid"></table>
  </div>
  <input type="button" id="modifyEmp_Btn" class="ui-button ui-widget ui-corner-all" value="저장">
  <input type="button" id="can_work_btn" class="ui-button ui-widget ui-corner-all" value="취소">
</div>
 
 
</body>
</html>