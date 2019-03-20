<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">

</style>
<script>


var selectedAuthorityBean, updatedAuthorityBean = []; // 사원의 상세정보를 저장하는 객체들

var emptyBean = []; // 그리드에 새 행을 추가하기 위한 비어있는 객체들

var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함)

   $(document).ready(function(){

         
      $("#authorityList_tabs").tabs();

      showAuthorityListGrid();
      
      
      
      
      // 그리드의 행 추가, 삭제 버튼들
       $("#add_authority_Btn").click(addListGridRow);

      $("#del_authority_Btn").click(delListGridRow);
      
      $("#find_authority_Btn").click(authorityListAjax);
      
      $("#deptName").click(function(){
    	  getCode('CO-07',"deptName","deptCode")
      })
      $("#positionName").click(function(){
    	  getCode('CO-04',"positionName","positionCode")
      })
      
      
      /* 상세정보 탭의 저장 버튼 */
      $("#modifyAuthority_Btn").click(function(){
         if(updatedAuthorityBean == null){
            alert("저장할 내용이 없습니다");
         } else {
            var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
            if(flag)
               modifyAuthority();
         }
      });
      
      
      
   });


   /* 코드 선택창 띄우기 */
	  function getCode(code,inputText,inputCode){
			option="width=220; height=200px; left=300px; top=300px; titlebar=no; toolbar=no,status=no,menubar=no,resizable=no, location=no";
			window.open("${pageContext.request.contextPath}/base/codeWindow.html?code="
							+code+"&inputText="+inputText+"&inputCode="+inputCode,"newwins",option);
	}
   
   

function authorityListAjax(){
      
      $.ajax({
         url:"${pageContext.request.contextPath}/base/authorityList.do",
         data:{
            "method" : "findAuthorityList",
            "dept" : $("#deptCode").val(),
            "position" : $("#positionCode").val()
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

            
            
            initField(); // 전역변수 초기화
            
            
               
            setAllEmptyBean(data); // 비어있는 객체들 생성

            selectedAuthorityBean = $.extend(true, [], data.authorityList); // 취소버튼을 위한 임시 저장공간에 딥카피
            updatedAuthorityBean = $.extend(true, [], data.authorityList); // 변경된 내용이 들어갈 공간에 딥카피
            
            
            
            
            showAuthorityListGrid();
         }
      });
      
   }
   
   /* 전역변수 초기화 함수 */
    function initField(){
         selectedAuthorityBean, updatedAuthorityBean = [];
         emptyBean = [];
         lastId = 0;
   } 
   
   
 
   
   
   
   
    /* 권한목록 그리드 띄우는 함수 */
    function showAuthorityListGrid(){
       

       $.jgrid.gridUnload("#authorityList_grid");
       $("#authorityList_grid").jqGrid({
          data : updatedAuthorityBean,
          datatype : "local",
          colNames : ['메뉴코드','사용가능한 메뉴','직급코드','직급명','부서코드','부서명', '상태'],
         colModel : [ {
	            name : 'menuCode', hidden : true,width:350
	        },{
            name : 'menuName' ,editoptions:{
				dataInit:function(cell,col){
				    getCode("CO-18",col.id,"menuCode"); 
				    lastId = col.id.split('_')[0];
				    alert(lastId)
			}}}, {
		            name : 'positionCode', hidden : true
	        }, {
		            name : 'positionName', hidden : true
	        }, {
		            name : 'deptCode', hidden : true
	        }, {
		            name : 'deptName', hidden : true
	        }, {
            name : 'status',width:150
            } ],
         width : 500,
         height : 300,
         rowNum : 300,
         viewrecords : true,
         cellEdit : true,
         editurl : "clientArray",
         cellsubmit : "clientArray",
         ondblClickRow: function (rowid, iRow,iCol) {
				if(iCol == 1){
					var colModels = $(this).getGridParam('colModel')
					var colName = colModels[iCol].name;
					$(this).setColProp(colName, { editable: true });					        
				}
			}
      
       });
       
       
    }
    

    /* 새로운 정보들을 추가하기 위한 빈 객체 세팅 */
    function setAllEmptyBean(data){
          
       emptyBean = data.emptyBean;
      
       emptyBean.status = "insert";
       
       
          
          
    }


     // 그리드에 행 추가하는 함수 
    function addListGridRow(){
    
      var id = $(this).attr("id");
      
      var key = id.substring(id.indexOf("_")+1,id.lastIndexOf("_"));
   
       var Seq = Number($("#" + key + "List_grid").getGridParam("records"));
       var nextSeq = Number($("#" + key + "List_grid").getGridParam("records")) + 1;
   
       var setCode = $("#authorityList_grid").getCell(Seq,"menuName");
       
       
      
       var number = setCode.substring(5,6);
       
       var number2 = parseInt(number)+1;
       
       var codeName = setCode.substring(0,5);

       var gg = codeName+number2;
       
       emptyBean.authorityCode = gg;
       
      $("#" + key + "List_grid").addRowData(nextSeq, emptyBean);
   
      
    }
    
    
     
    /* 그리드에 행 삭제 (주석은 행 추가하는거 참조)*/
    function delListGridRow(){
          var id = $(this).attr("id");
          var key = id.substring(id.indexOf("_")+1,id.lastIndexOf("_"));
          var selectedRowId = $("#"+ key +"List_grid").getGridParam("selrow");
          
          if(selectedRowId != null){
             var data = $("#" + key + "List_grid").getRowData(selectedRowId);
             if(data.status == "normal" || data.status == "update")
                $("#" + key + "List_grid").setCell(selectedRowId, "status", "delete");
          
             else if(data.status == "delete")
                $("#" + key + "List_grid").setCell(selectedRowId, "status", "normal");
          
             else
                $("#" + key + "List_grid").delRowData(selectedRowId);
          
          
          } else {
             alert("삭제할 행을 선택해 주세요!");
          }
    }

    
   /* 저장 버튼을 눌렀을 때 실행되는 함수 */
    function modifyAuthority(){
         
	   if(lastId != 0 ){
		    updatedAuthorityBean[lastId-1].menuCode=$("#menuCode").val()
	    	updatedAuthorityBean[lastId-1].positionCode=$("#positionCode").val()
	    	updatedAuthorityBean[lastId-1].deptCode=$("#deptCode").val()
	   }
	   
          var sendData = JSON.stringify(updatedAuthorityBean);
       
          $.ajax({                            
             url : "${pageContext.request.contextPath}/base/authorityList.do",
             data : {
                "method" : "modifyAuthority",
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
     
    
     
     
   
   
    
</script>


   <div id="authorityList_tabs" style="display: inline-block;">
      <ul>
         <li><a href="#authorityList_tab">권한관리목록</a></li>
      </ul>
      <div id="authorityList_tab">
         <input type="text" id="deptName" class="ui-button ui-widget ui-corner-all" placeholder="부서선택">
         <input type="text" id="positionName" class="ui-button ui-widget ui-corner-all" placeholder="직급선택">
         <input type="button" id="find_authority_Btn" class="ui-button ui-widget ui-corner-all" value="검색">
         <br />
         <input type="hidden" id="menuCode">
         <input type="hidden" id="positionCode">
         <input type="hidden" id="deptCode">
         <br />
         <table id="authorityList_grid"></table>
         <div id="authorityList_pager"></div>
         <br />
         <input type="button" id="add_authority_Btn" class="ui-button ui-widget ui-corner-all" value="추가">
         <input type="button" id="del_authority_Btn" class="ui-button ui-widget ui-corner-all" value="삭제">
         <input type="button" id="modifyAuthority_Btn" class="ui-button ui-widget ui-corner-all" value="저장">
      </div>
   </div>




