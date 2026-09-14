<!-- teamInsert.jsp -->
<%@ page import="java.util.Vector" %>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch09.TeamMgr"/>
<%Vector<String> vlist = mgr.teamList();%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Team Insert</title>
<script src="https://cdn.tailwindcss.com"></script>
<script type="text/javascript">
	var nameChecked = false;   // 중복확인 버튼을 눌러서 "사용 가능" 판정을 받았는지

	function selectTeam(team){
		document.frm.team.value=team;
	}

	// 이름 입력칸을 고치면, 그 전에 받아둔 중복확인 결과는 무효화
	function resetNameCheck(){
		nameChecked = false;
		document.getElementById('nameCheckMsg').textContent = '';
	}

	// 중복확인 버튼 클릭 → checkName.jsp 를 AJAX로 호출해서 결과만 받아옴 (페이지 이동 없음)
	function checkNameDup(){
		var name = document.frm.name.value;
		if(name == ""){
			alert("이름을 입력하세요");
			document.frm.name.focus();
			return;
		}
		fetch('checkName.jsp?name=' + encodeURIComponent(name))
			.then(function(res){ return res.text(); })
			.then(function(result){
				var msg = document.getElementById('nameCheckMsg');
				if(result.trim() === 'dup'){
					msg.textContent = '이미 사용 중인 이름입니다';
					msg.className = 'text-xs mt-1 text-red-600';
					nameChecked = false;
				} else {
					msg.textContent = '사용 가능한 이름입니다';
					msg.className = 'text-xs mt-1 text-green-600';
					nameChecked = true;
				}
			})
			.catch(function(){
				alert('중복 확인 중 오류가 발생했습니다');
			});
	}

	function check() {
		f = document.frm;
		if(f.name.value==""){
			alert("이름을 입력하세요");
			f.name.focus();
			return;
		}
		if(!nameChecked){
			alert("이름 중복 확인을 먼저 해주세요");
			return;
		}
		if(f.city.value==""){
			alert("사는곳을 입력하세요");
			f.city.focus();
			return;
		}
		if(f.age.value==""){
			alert("나이를 입력하세요");
			f.age.focus();
			return;
		}
		if(f.team.value==""){
			alert("팀을 입력하세요");
			f.team.focus();
			return;
		}
		f.submit();
	}

	function check2() {
		if(!nameChecked){
			alert("이름 중복 확인을 먼저 해주세요");
			return;
		}
		document.frm.action = "teamInsertProc2.jsp";
		document.frm.submit();
	}
</script>
</head>
<body class="bg-gray-50 min-h-screen py-10">
<div class="max-w-md mx-auto px-4">
<h1 class="text-2xl font-bold text-gray-800 mb-6">Team Insert</h1>

<form name="frm" method="post" action="teamInsertProc.jsp"
      class="bg-white rounded-lg shadow divide-y divide-gray-100">
    <div class="px-5 py-3">
        <div class="flex items-center gap-2">
            <label class="w-24 text-gray-500 text-sm">이름</label>
            <input name="name" value="홍길동" onchange="resetNameCheck()" oninput="resetNameCheck()"
                   class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400">
            <button type="button" onclick="checkNameDup()"
                    class="px-3 py-1.5 rounded bg-gray-700 text-white text-xs font-medium hover:bg-gray-800 whitespace-nowrap">중복확인</button>
        </div>
        <p id="nameCheckMsg" class="text-xs mt-1 ml-[104px]"></p>
    </div>
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">사는곳</label>
        <input name="city" value="부산"
               class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400">
    </div>
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">나이</label>
        <input name="age" value="27"
               class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400">
    </div>
    <div class="flex items-center gap-2 px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">팀명</label>
        <input name="team" value="산적" size="5"
               class="w-24 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400">
        <select onchange="selectTeam(this.value)"
                class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm bg-white focus:outline-none focus:ring-2 focus:ring-blue-400">
            <option value="">팀을 선택하세요</option>
<%for(String team: vlist){%>
            <option value="<%=team%>"><%=team%></option>
<%}%>
        </select>
    </div>
    <div class="px-5 py-4 flex gap-2">
        <input type="button" value="SAVE" onclick="check()"
               class="flex-1 py-2 rounded bg-blue-600 text-white text-sm font-medium hover:bg-blue-700 cursor-pointer">
        <input type="button" value="SAVE2" onclick="check2()"
               class="flex-1 py-2 rounded bg-gray-600 text-white text-sm font-medium hover:bg-gray-700 cursor-pointer">
    </div>
</form>

<div class="mt-4">
    <a href="teamList.jsp"
       class="px-4 py-2 rounded bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200 inline-block">LIST</a>
</div>
</div>
<%@ include file="/common/themeToggle.jspf" %>
</body>
</html>
