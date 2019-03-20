<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="dept" value="" />
<c:if test="${ not empty sessionScope.id}">
   <c:set var="dept" value="${sessionScope.dept}" />
</c:if>

<c:set var="position" value="" />
<c:if test="${ not empty sessionScope.id}">
   <c:set var="position" value="${sessionScope.position}" />
</c:if>
<c:set var="name" value="Guest" />
<c:if test="${ not empty sessionScope.id}">
   <c:set var="name" value="${sessionScope.id}" />
</c:if>
<div id = "loginInfo" >${dept} ${position}</div>
<div id = "loginInfo" >성명 : ${name}</div>
<div id = "loginInfo" >
   <c:if test="${empty sessionScope.id}">
      <a id ="loginaTag" href='${pageContext.request.contextPath}/loginform.html'>로그인</a>
      <br>
   </c:if>
   <c:if test="${not empty sessionScope.id}">
      <a id ="logoutaTag" href='${pageContext.request.contextPath}/logout.do?method=empLogout'>로그아웃</a>
   </c:if>
</div>

<script>
   $(document).ready(function() {
      var id = "${sessionScope.id}";
      var position = "${sessionScope.position}"
      
       /* showMenuList();  */
      $('nav li').hover(function() {
         $('ul', this).stop().toggle();
      });
       
       /* 로그인이 되지 않으면 기능 메뉴기능 사용불가 */
         $("a").click(function(){
          if(position == ""){
             if($(this).attr("id")!="loginaTag" && $(this).attr("id")!="logoutaTag"){
                alert("로그인을 해주세요");
                return false;
             }
          }
          else if(position != "팀장" ){
             if($(this).attr("id")=="emp1" || $(this).attr("id")=="emp2" || $(this).attr("id")=="emp0" ||  $(this).attr("id")!="loginaTag" || 
                   $(this).attr("id")=="emp3" || $(this).attr("id")=="emp4" || $(this).attr("id")!="logoutaTag" ||
                   $(this).attr("id")=="emp5" || $(this).attr("id")=="emp6" || $(this).attr("id")=="emp7"){
             }else{
                alert("권한이 없습니다");
                return false;
             }
          }
       })  
       
   });
   
   

    function showMenuList() {
      $.ajax({
         url : "${pageContext.request.contextPath}/base/menuList.do",
         dataType : "json",
         success : function(dataSet) {
            var menuList = dataSet.menuList;
            $.each(menuList, function(index, menu) {

               if (menu.superMenuCode == "") {
                  $("<li />", {
                     id : menu.menuCode + "_li"
                  }).appendTo($(".menu_ul"));

                  $("<a />", {
                     id : menu.menuCode,
                     text : menu.menuName,
                     href : "#"
                  }).appendTo($("#" + menu.menuCode + "_li"));

                  $("<ul />", {
                     id : menu.menuCode + "_ul"
                  }).appendTo($("#" + menu.menuCode + "_li"));

               } else {
                  $("<li />", {
                     id : menu.menuCode + "_li"
                  }).appendTo($("#" + menu.superMenuCode + "_ul"));

                  $("<a />", {
                     href : "#",
                     text : menu.menuName
                  }).click(function() {
                      var permitted = false;
                     for(var index in userMenuList)
                        if(userMenuList[index].menuCode==menu.menuCode)
                            permitted = true;
                     if(permitted)
                        location.href = "${pageContext.request.contextPath}"+menu.menuUrl;
                     else
                        alert("해당 메뉴를 이용하실 권한이 없습니다");  
                  }).appendTo($("#" + menu.menuCode + "_li"));

               }
            });

         },
         error : function(a, b, c) {
            alert(b);
         }

      });
   } 
</script>
<center>
   <nav>
      <div class="container">

         <ul class="menu_ul">
            <li><a href="${pageContext.request.contextPath}/main.html" id = "emp0" >메인</a></li>
            <li><a href="#">인사관리</a>
               <ul>
                  <li><a href="${pageContext.request.contextPath}/emp/empRegist.html">사원등록</a></li>
                  <li><a href="${pageContext.request.contextPath}/emp/empDetailedView.html">사원상세조회</a></li>
               </ul></li>

            <li><a href="#">사원메뉴</a>
               <ul>
                  <li><a href="${pageContext.request.contextPath}/emp/empFind.html" id = "emp1">사원기본조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/dayAttendance.html" id = "emp2">일근태기록/조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/restAttendance.html" id = "emp3">근태외신청/조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/break.html" id = "emp4" >휴가신청/조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/travel.html" id = "emp5" >출장/교육신청/조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/overwork.html" id = "emp6" >초과근무신청/조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/employment.html" id = "emp7" >재직증명서신청</a></li>
               </ul></li>

            <li><a href="#">근태관리</a>
               <ul>
                  <li><a href="${pageContext.request.contextPath}/attendance/dayAttendanceManage.html">일근태관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/monthAttendanceManage.html">월근태관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/attendance/attendanceApploval.html">결재승인관리</a></li>
               </ul></li>

            <li><a href="#">급여조회</a>
               <ul>
                  <li><a href="${pageContext.request.contextPath}/salary/monthSalary.html">월급여조회</a></li>
               </ul></li>

            <li><a href="#">급여관리</a>
               <ul>
                  <li><a href="${pageContext.request.contextPath}/salary/baseSalaryManage.html">급여기준관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/salary/baseDeductionManage.html">공제기준관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/salary/baseExtSalManage.html">초과수당관리</a></li>
               </ul></li>

            <li><a href="#">시스템관리</a>
               <ul>
                  <li><a href="${pageContext.request.contextPath}/base/deptList.html">부서관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/base/positionList.html">직급관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/base/authorityList.html">권한관리</a></li>
                  <li><a href="${pageContext.request.contextPath}/base/codeList.html">코드조회</a></li>
                  <li><a href="${pageContext.request.contextPath}/base/holidayList.html">휴일조회</a></li>
               </ul></li>

         </ul> 
      </div>
   </nav>
</center>

<hr>