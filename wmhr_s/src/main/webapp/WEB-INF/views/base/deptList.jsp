<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">

</style>
<script>


var selectedDeptBean, updatedDeptBean = []; // 사원의 상세정보를 저장하는 객체들

var emptyBean = []; // 그리드에 새 행을 추가하기 위한 비어있는 객체들

var lastId = 0; // 마지막으로 선택한 그리드의 행 id (다른 행을 더블클릭 하였을 때, 해당 행을 닫기 상태로 만들기 위해 저장함)

   $(document).ready(function(){

         
      $("#deptList_tabs").tabs();

      deptListAjax();
      
      
      // 그리드의 행 추가, 삭제 버튼들
       $("#add_dept_Btn").click(addListGridRow);

      $("#del_dept_Btn").click(delListGridRow);
      
      
      /* 상세정보 탭의 저장 버튼 */
      $("#modifyDept_Btn").click(function(){
         if(updatedDeptBean == null){
            alert("저장할 내용이 없습니다");
         } else {
            var flag = confirm("변경한 내용을 서버에 저장하시겠습니까?");
            if(flag)
               modifyDept();
         }
      });
      
      
      
   });


function deptListAjax(){
      
      $.ajax({
         url:"${pageContext.request.contextPath}/base/deptList.do",
         data:{
            "method" : "findDeptList"
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

            selectedDeptBean = $.extend(true, [], data.list); // 취소버튼을 위한 임시 저장공간에 딥카피
            updatedDeptBean = $.extend(true, [], data.list); // 변경된 내용이 들어갈 공간에 딥카피
            
            
            
            
            showDeptListGrid();
         }
      });
      
   }
   
   /* 전역변수 초기화 함수 */
    function initField(){
         selectedDeptBean, updatedDeptBean = [];
         emptyBean = [];
         lastId = 0;
   } 
   
   
 
   
   
   
   
    /* 직급목록 그리드 띄우는 함수 */
    function showDeptListGrid(){
       

       $.jgrid.gridUnload("#deptList_grid");
       $("#deptList_grid").jqGrid({
          data : updatedDeptBean,
      
          datatype : "local",
          colNames : ['부서코드','부서명','부서전화번호', '상태'],
         colModel : [ {
            name : 'deptCode' }, {
            name : 'deptName',editable : true },{
            name : 'deptTel' ,editable : true},{
            name : 'status'
            } ],
         width : 500,
         height : 300,
         viewrecords : true,
         rowNum : 5,
         cellEdit : true,
         editurl : "clientArray",
         cellsubmit : "clientArray",
   ondblClickRow: function(id){
                  
   if(id && id!==lastId){
                     
   $(this).jqGrid('restoreRow',lastId);
   }
            
   $(this).jqGrid('editRow',id,true,function(){});
                  
   lastId=id;
                  
   var rowData = $(this).jqGrid("getRowData", id);
                  
   if (rowData.status == "normal") 

   $(this).setCell(id, "status", "update");

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
   
       var setCode = $("#deptList_grid").getCell(Seq,"deptCode");
      
       var number = setCode.substring(5,6);
       
       var number2 = parseInt(number)+1;
       
       var codeName = setCode.substring(0,5);

       var gg = codeName+number2;
       
       emptyBean.deptCode = gg;
       
      $("#" + key + "List_grid").addRowData(nextSeq, emptyBean);
   
      
    }
    
    
     
    /* 그리드에 행 삭제 (주석은 행 추가하는거 참조)*/
    function delListGridRow(){
          var id = $(this).attr("id");
          var key = id.substring(id.indexOf("_")+1,id.lastIndexOf("_"));
          var selectedRowId = $("#"+ key +"List_grid").getGridParam("selrow");
          // 내가 선택한 로우의 아이디 값을 들고옴 
          
          if(selectedRowId != null){
          // 내가 선택한 로우의 아디의 값이 널이 아니라면
             var data = $("#" + key + "List_grid").getRowData(selectedRowId);
          // 그 로우의 대한 데이터를 들고온다    
             if(data.status == "normal" || data.status == "update")
             // 삭제버튼을 눌렸을때 그 로우의 값이 normal 이거나 update일 경우      
                $("#" + key + "List_grid").setCell(selectedRowId, "status", "delete");
             // 그로우의 status 를 delete 로 바꾸겟다 
          
             else if(data.status == "delete")
             // 삭제 버튼 을 눌렸을 경우 그 로우의 status 의 값이 delete 인경우
                $("#" + key + "List_grid").setCell(selectedRowId, "status", "normal");
             // 그 로우의 status 의 값을 update로 바꾸겟다 
          
             else
                $("#" + key + "List_grid").delRowData(selectedRowId);
              // 나머지 insert인경우 추가버튼을 눌렸을경우만 나오기 때문에 
              // 더이상 추가할께 없는상황에서 로우가 만들어졌을때를 대비해서 
              // insert인경우는 그리드안에서 바로 로우가 삭제되게 됨 
          
          
          } else {
             alert("삭제할 행을 선택해 주세요!");
          }
    }

    
   /* 저장 버튼을 눌렀을 때 실행되는 함수 */
    function modifyDept(){
         
          var sendData = JSON.stringify(updatedDeptBean);
          
                    
       
          $.ajax({                            
             url : "${pageContext.request.contextPath}/base/deptList.do",
             data : {
                "method" : "batchDeptProcess",
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


   <div id="deptList_tabs" style="display: inline-block;">
      <ul>
         <li><a href="#deptList_tab">부서관리목록</a></li>
      </ul>
      <div id="deptList_tab">
         <input type="button" id="add_dept_Btn" class="ui-button ui-widget ui-corner-all" value="추가">
         <input type="button" id="del_dept_Btn" class="ui-button ui-widget ui-corner-all" value="삭제">
         <input type="button" id="modifyDept_Btn" class="ui-button ui-widget ui-corner-all" value="저장"><br />
         <br />
         <table id="deptList_grid"></table>
         <div id="deptList_pager"></div>
      </div>
   </div>




