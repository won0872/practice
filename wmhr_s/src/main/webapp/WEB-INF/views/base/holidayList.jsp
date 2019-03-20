<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>

   // 휴일목록을 담을 배열 
   var holidayList = [];

   var emptyInfo = {};
   
   var lastId = 0 ; 
   
   $(document).ready(function(){
      

      
      
      
      // 휴일목록 탭 등록   

      $("#holidayListTabs").tabs()
      
      
      $("#add_Btn").click(addGridRow);
      $("#remove_Btn").click(removeGirdRow);
      $("#save_Btn").click(saveGridRow);
      
      
      $.ajax({
         
         url:"${pageContext.request.contextPath}/base/holidayList.do",
         
         dataType : "json" , 
         
         data : {
            
            method : "findHolidayList"
            
         },
         
         success : function(data){  
            
            holidayList = data.holidayList;
               
            emptyInfo = data.emptyHoilday;
            
            $("#holidayListGrid").jqGrid({
               
               data : holidayList,
               
               datatype : "local",
               
               colNames : ["일자","휴일명","비고" ,"상태"] ,
               
               colModel : [
               
                {name : "applyDay" , align:"center" , editable : true},
                
                /* formatter: "date" , formatoptions :{srcformat:"ISO8601Long",newformat:"Y/m/d"} */   
                     
               {name : "holidayName" , align:"center" , editable : true},
               
               {name : "note" , align:"center" , editable : true} , 
               
               {name : "status" , align : "center" , editable : false }
                  
               ],
               
               width : 500 ,
               
               height: 300 , 
               
               rowNum: 100,
               
               multiboxonly : true ,
               
               editurl : "clientArray" ,  
               
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
         
         
      });
      
   });


   

   
function addGridRow(){
   
      emptyInfo.status = "insert";
      
      var nextSeq = Number($("#holidayListGrid").getGridParam("records")) + 1;
            
      
         $("#holidayListGrid").addRowData(nextSeq, emptyInfo);
      
   }   



function removeGirdRow(){
   
   var id = $(this).attr("id");
   
   var key = id.substring(id.indexOf("_")+1,id.lastIndexOf("_")); 
   
   var selectedRowId = $("#holidayListGrid").getGridParam( "selrow" ); 

   if(selectedRowId != null){

      var data = $("#holidayListGrid").getRowData(selectedRowId);
   
      if(data.status == "normal" || data.status == "update")
      
         $("#holidayListGrid").setCell(selectedRowId, "status", "delete");
   
      else if(data.status == "delete")
   
         $("#holidayListGrid").setCell(selectedRowId, "status", "update");

      else
         $("#holidayListGrid").delRowData(selectedRowId);
   }
    else {
         alert("삭제할 행을 선택해 주세요");
    }
   
   
}


function saveGridRow(){
   
       var qus = confirm("변경한 내용을 서버에 저장하시겠습니까?");
       
       if(qus){
          
          var RowData = $("#holidayListGrid").getRowData();
            
          var sendData = JSON.stringify(RowData);
          
          $.ajax({
             
             url:"${pageContext.request.contextPath}/base/holidayList.do" , 
             
             dataType : "json" , 
             
             data : {
                
                method : "regitCodeList" , 
                
                sendData : sendData 
                
             } , 
             
             success : function(data){
                
                if(data.errorCode < 0 ){
                   
                   var str = "내부 서버 오류가 발생했습니다\n";
                     str += "관리자에게 문의하세요\n";
                     str += "에러 위치 : " + $(this).attr("data");
                     str += "에러 메시지 : " + data.errorMsg;
                     alert(str);
                   
                }
                
                alert("저장되었습니다.");
                
                location.href = "${pageContext.request.contextPath}/base/holidayList.html"
                
             }
             
             
          });
          
            
       }else{
          
          alert("취소하셨습니다.");
          
       }
      
   
}



</script>
</head>
<body>
<div id="holidayListTabs" style="display: inline-block;">
      <ul>
         <li><a href="#holidayList_tab">휴일목록</a></li>
      </ul>   
      <div id="holidayList_tab">
		<input type="button" value="추가" class="ui-button ui-widget ui-corner-all" id="add_Btn">
		<input type="button" value="수정/삭제" class="ui-button ui-widget ui-corner-all" id="remove_Btn">
		<input type="button" value="저장" class="ui-button ui-widget ui-corner-all" id="save_Btn">
		<br />
		<table id="holidayListGrid">
		</table>
</div>
</div>
</body>
</html>