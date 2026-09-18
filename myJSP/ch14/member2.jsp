<!-- member2.jsp -->
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">

  var idChecked = false;

  function idCheck(id) {
    if (id == "") {
      alert("아이디를 입력하세요.");
      document.regFrm.id.focus();
      return;
    }
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "idCheck2.jsp?id=" + encodeURIComponent(id), true);//true: 비동기화 통신
    xhr.onreadystatechange = function () {
    	  //readyState: 0~4
    	  //0: 요청 시작전, 1: 서버랑 연결, 2: 서버가 요청 받음, 3: 응답 오는중, 4: 완료
    	  //200: 성공, 404: 페이지 없음, 500: 서버 에러
      if (xhr.readyState == 4 && xhr.status == 200) {
        var result = xhr.responseText.trim();
        var msgEl = document.getElementById("idCheckMsg");
        //alert(result);
        if (result == "duplicate") {
          msgEl.innerHTML = '<span class="text-danger"><strong>✗</strong> 이미 사용 중인 아이디입니다.</span>';
          idChecked = false;
          document.regFrm.id.focus();
        } else {
          msgEl.innerHTML = '<span class="text-success"><strong>✓</strong> 사용 가능한 아이디입니다.</span>';
          idChecked = true;
        }
      }
    };
    xhr.send();
  }

  function zipSearch() {
    var url = "zipSearch.jsp?search=n";
    window.open(url, "우편번호검색", "width=520, height=320, top=100, left=100, scrollbars=yes");
  }

  function inputCheck() {
    if (document.regFrm.id.value == "") {
      alert("아이디를 입력해 주세요.");
      document.regFrm.id.focus();
      return;
    }
    if (!idChecked) {
      alert("아이디 중복확인을 해주세요.");
      return;
    }
    if (document.regFrm.pwd.value == "") {
      alert("비밀번호를 입력해 주세요.");
      document.regFrm.pwd.focus();
      return;
    }
    if (document.regFrm.repwd.value == "") {
      alert("비밀번호를 확인해 주세요.");
      document.regFrm.repwd.focus();
      return;
    }
    if (document.regFrm.pwd.value != document.regFrm.repwd.value) {
      alert("비밀번호가 일치하지 않습니다.");
      document.regFrm.repwd.value = "";
      document.regFrm.repwd.focus();
      return;
    }
    if (document.regFrm.name.value == "") {
      alert("이름을 입력해 주세요.");
      document.regFrm.name.focus();
      return;
    }
    if (document.regFrm.birthday.value == "") {
      alert("생년월일을 입력해 주세요.");
      document.regFrm.birthday.focus();
      return;
    }
    if (document.regFrm.email.value == "") {
      alert("이메일을 입력해 주세요.");
      document.regFrm.email.focus();
      return;
    }
    if (document.regFrm.zipcode.value == "") {
      alert("우편번호를 검색해 주세요.");
      return;
    }
    if (document.regFrm.job.value == "0") {
      alert("직업을 선택해 주세요.");
      document.regFrm.job.focus();
      return;
    }
    document.regFrm.submit();
  }

</script>
</head>
<body class="bg-light" onload="regFrm.id.focus()">
<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-lg-7 col-md-9">
      <div class="card shadow-sm">
        <div class="card-header bg-danger text-white py-3">
          <h5 class="mb-0 fw-bold">회원 가입 <small class="fw-normal fs-6">(Ajax ID 중복확인)</small></h5>
        </div>
        <div class="card-body p-4">
          <form name="regFrm" method="post" action="memberProc.jsp">

            <!-- 아이디 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">아이디</label>
              <div class="col-sm-9">
                <div class="input-group">
                  <input type="text" name="id" class="form-control" placeholder="아이디"
                    onkeyup="idChecked=false; document.getElementById('idCheckMsg').innerHTML='';">
                  <button type="button" class="btn btn-outline-secondary"
                    onclick="idCheck(this.form.id.value)">중복확인</button>
                </div>
                <div id="idCheckMsg" class="form-text mt-1"></div>
                <div class="form-text">아이디를 적어 주세요.</div>
              </div>
            </div>

            <!-- 비밀번호 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">비밀번호</label>
              <div class="col-sm-9">
                <input type="password" name="pwd" class="form-control">
                <div class="form-text">비밀번호를 적어주세요.</div>
              </div>
            </div>

            <!-- 비밀번호 확인 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">비밀번호 확인</label>
              <div class="col-sm-9">
                <input type="password" name="repwd" class="form-control">
                <div class="form-text">비밀번호를 확인합니다.</div>
              </div>
            </div>

            <!-- 이름 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">이름</label>
              <div class="col-sm-9">
                <input type="text" name="name" class="form-control">
                <div class="form-text">이름을 적어주세요.</div>
              </div>
            </div>

            <!-- 성별 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">성별</label>
              <div class="col-sm-9 pt-2">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="gender" value="1" id="genderM2" checked>
                  <label class="form-check-label" for="genderM2">남</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="gender" value="2" id="genderF2">
                  <label class="form-check-label" for="genderF2">여</label>
                </div>
                <div class="form-text">성별을 선택하세요.</div>
              </div>
            </div>

            <!-- 생년월일 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">생년월일</label>
              <div class="col-sm-9">
                <input type="text" name="birthday" class="form-control" placeholder="예: 830815">
                <div class="form-text">생년월일을 적어 주세요.</div>
              </div>
            </div>

            <!-- Email -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">Email</label>
              <div class="col-sm-9">
                <input type="email" name="email" class="form-control">
                <div class="form-text">이메일을 적어 주세요.</div>
              </div>
            </div>

            <!-- 우편번호 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">우편번호</label>
              <div class="col-sm-9">
                <div class="input-group">
                  <input type="text" name="zipcode" class="form-control" readonly placeholder="우편번호">
                  <button type="button" class="btn btn-outline-secondary" onclick="zipSearch()">우편번호찾기</button>
                </div>
                <div class="form-text">우편번호를 검색하세요.</div>
              </div>
            </div>

            <!-- 주소 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">주소</label>
              <div class="col-sm-9">
                <input type="text" name="address" class="form-control">
                <div class="form-text">주소를 적어 주세요.</div>
              </div>
            </div>

            <!-- 취미 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">취미</label>
              <div class="col-sm-9 pt-2">
                <div class="d-flex flex-wrap gap-3">
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="hobby" value="인터넷" id="h2_inet">
                    <label class="form-check-label" for="h2_inet">인터넷</label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="hobby" value="여행" id="h2_travel">
                    <label class="form-check-label" for="h2_travel">여행</label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="hobby" value="게임" id="h2_game">
                    <label class="form-check-label" for="h2_game">게임</label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="hobby" value="영화" id="h2_movie">
                    <label class="form-check-label" for="h2_movie">영화</label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="hobby" value="운동" id="h2_sport">
                    <label class="form-check-label" for="h2_sport">운동</label>
                  </div>
                </div>
                <div class="form-text">취미를 선택하세요.</div>
              </div>
            </div>

            <!-- 직업 -->
            <div class="mb-4 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">직업</label>
              <div class="col-sm-9">
                <select name="job" class="form-select">
                  <option value="0" selected>선택하세요</option>
                  <option value="회사원">회사원</option>
                  <option value="연구전문직">연구전문직</option>
                  <option value="교수학생">교수학생</option>
                  <option value="일반자영업">일반자영업</option>
                  <option value="공무원">공무원</option>
                  <option value="의료인">의료인</option>
                  <option value="법조인">법조인</option>
                  <option value="종교,언론,에술인">종교·언론/예술인</option>
                  <option value="농,축,수산,광업인">농/축/수산/광업인</option>
                  <option value="주부">주부</option>
                  <option value="무직">무직</option>
                  <option value="기타">기타</option>
                </select>
                <div class="form-text">직업을 선택하세요.</div>
              </div>
            </div>

            <!-- 버튼 -->
            <div class="d-flex justify-content-center gap-2 pt-2 border-top">
              <button type="button" class="btn btn-danger" onclick="inputCheck()">회원가입</button>
              <button type="reset" class="btn btn-outline-secondary"
                onclick="idChecked=false; document.getElementById('idCheckMsg').innerHTML='';">다시쓰기</button>
              <button type="button" class="btn btn-outline-dark"
                onclick="location.href='login.jsp'">로그인</button>
            </div>

          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
