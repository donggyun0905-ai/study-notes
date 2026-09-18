<!-- memberUpdate.jsp -->
<%@page import="ch14.MemberBean"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<%
		String id = (String)session.getAttribute("idKey");
		if(id==null){
			response.sendRedirect("login.jsp");
			return;
		}
		MemberBean bean = mgr.getMember(id);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원수정</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="script.js"></script>
<script type="text/javascript">
  function zipCheck() {
    var url = "zipSearch.jsp?search=n";
    window.open(url, "ZipCodeSearch", "width=520, height=320, top=100, left=100, scrollbars=yes");
  }
</script>
</head>
<body class="bg-light" onload="regFrm.id.focus()">
<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-lg-7 col-md-9">
      <div class="card shadow-sm">
        <div class="card-header bg-warning text-dark py-3">
          <h5 class="mb-0 fw-bold">회원 수정</h5>
        </div>
        <div class="card-body p-4">
          <form name="regFrm" method="post" action="memberUpdateProc.jsp">

            <!-- 아이디 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">아이디</label>
              <div class="col-sm-9">
                <input type="text" name="id" class="form-control bg-light" readonly
                  value="<%=bean.getId()%>">
                <div class="form-text text-muted">아이디는 변경할 수 없습니다.</div>
              </div>
            </div>

            <!-- 비밀번호 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">비밀번호</label>
              <div class="col-sm-9">
                <input type="password" name="pwd" class="form-control" value="<%=bean.getPwd()%>">
              </div>
            </div>

            <!-- 이름 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">이름</label>
              <div class="col-sm-9">
                <input type="text" name="name" class="form-control" value="<%=bean.getName()%>">
              </div>
            </div>

            <!-- 성별 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">성별</label>
              <div class="col-sm-9 pt-2">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="gender" value="1" id="upGenderM"
                    <%=bean.getGender().equals("1") ? "checked" : ""%>>
                  <label class="form-check-label" for="upGenderM">남</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="gender" value="2" id="upGenderF"
                    <%=bean.getGender().equals("2") ? "checked" : ""%>>
                  <label class="form-check-label" for="upGenderF">여</label>
                </div>
              </div>
            </div>

            <!-- 생년월일 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">생년월일</label>
              <div class="col-sm-9">
                <input type="text" name="birthday" class="form-control"
                  value="<%=bean.getBirthday()%>" placeholder="예: 830815">
              </div>
            </div>

            <!-- Email -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">Email</label>
              <div class="col-sm-9">
                <input type="email" name="email" class="form-control" value="<%=bean.getEmail()%>">
              </div>
            </div>

            <!-- 우편번호 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">우편번호</label>
              <div class="col-sm-9">
                <div class="input-group">
                  <input type="text" name="zipcode" class="form-control" readonly
                    value="<%=bean.getZipcode()%>">
                  <button type="button" class="btn btn-outline-secondary" onclick="zipCheck()">우편번호찾기</button>
                </div>
              </div>
            </div>

            <!-- 주소 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">주소</label>
              <div class="col-sm-9">
                <input type="text" name="address" class="form-control" value="<%=bean.getAddress()%>">
              </div>
            </div>

            <!-- 취미 -->
            <div class="mb-3 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">취미</label>
              <div class="col-sm-9 pt-2">
                <div class="d-flex flex-wrap gap-3">
                  <%
                    String[] lists = {"인터넷","여행","게임","영화","운동"};
                    String[] hb = bean.getHobby();
                    for (int i = 0; i < lists.length; i++) {
                  %>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="hobby"
                      value="<%=lists[i]%>" id="upHobby_<%=i%>"
                      <%=hb[i].equals("1") ? "checked" : ""%>>
                    <label class="form-check-label" for="upHobby_<%=i%>"><%=lists[i]%></label>
                  </div>
                  <%}%>
                </div>
              </div>
            </div>

            <!-- 직업 -->
            <div class="mb-4 row align-items-start">
              <label class="col-sm-3 col-form-label fw-semibold">직업</label>
              <div class="col-sm-9">
                <select name="job" class="form-select">
                  <option value="0">선택하세요</option>
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
                <script>
                  document.regFrm.job.value = "<%=bean.getJob()%>";
                </script>
              </div>
            </div>

            <!-- 버튼 -->
            <div class="d-flex justify-content-center gap-2 pt-2 border-top">
              <button type="submit" class="btn btn-warning fw-semibold">수정완료</button>
              <button type="reset" class="btn btn-outline-secondary">다시쓰기</button>
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