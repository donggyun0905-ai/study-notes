<!-- zipSearch.jsp -->
<%@page import="ch14.ZipcodeBean"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<%
	  String search = request.getParameter("search");
	  Vector<ZipcodeBean> vlist = null;
	  String area3 = null;
	  if (search!=null && search.equals("y")) {
		area3 = request.getParameter("area3");
		vlist = mgr.searchZipcode(area3);
	  }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>우편번호 검색</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript">
  function loadSearch() {
    var frm = document.zipFrm;
    if (frm.area3.value == "") {
      alert("도로명을 입력하세요.");
      return;
    }
    frm.action = "zipSearch.jsp";
    frm.submit();
  }

  function sendAdd(zipcode, adds) {
    opener.document.regFrm.zipcode.value = zipcode;
    opener.document.regFrm.address.value = adds;
    self.close();
  }
</script>
<style type="text/css">
 .zip-header {
    display: flex;
    align-items: baseline;
    gap: 10px;
    border-bottom: 2px solid var(--post-navy);
    padding-bottom: 10px;
    margin-bottom: 16px;
  }
  .zip-header .mark {
    width: 34px;
    height: 34px;
    flex: none;
    border: 2px dashed var(--post-red);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 15px;
    font-weight: 700;
    color: var(--post-red);
  }
  .zip-header h3 {
    margin: 0;
    font-size: 1.15rem;
    font-weight: 700;
    color: var(--post-navy);
  }
</style>
</head>
<body class="bg-light p-3">
<div class="container-fluid">
  <div class="zip-header">
    <span class="mark">우</span>
    <h3>우편번호 검색</h3>
  </div>
  <form name="zipFrm" method="post">
    <div class="input-group mb-3">
      <input type="text" name="area3" class="form-control" placeholder="도로명 입력" value="광평로">
      <button type="button" class="btn btn-primary" onclick="loadSearch()">검색</button>
    </div>
    <input type="hidden" name="search" value="y">

    <!-- 검색 결과 -->
    <%if (search!=null && search.equals("y")) {%>
      <%if (vlist != null && vlist.isEmpty()) {%>
      <div class="alert alert-warning text-center" role="alert">검색된 결과가 없습니다.</div>
      <%} else {%>
      <p class="text-muted small text-center mb-2">※ 아래 주소를 클릭하면 자동으로 입력됩니다.</p>
      <div class="list-group">
        <%
          for (ZipcodeBean bean : vlist) {
            String zipcode = bean.getZipcode();
            String adds = bean.getArea1() + " " + bean.getArea2() + " " + bean.getArea3() + " ";
        %>
        <a href="#" class="list-group-item list-group-item-action list-group-item-light small"
          onclick="sendAdd('<%=zipcode%>', '<%=adds%>')">
          <span class="badge bg-secondary me-2"><%=zipcode%></span><%=adds%>
        </a>
        <%}%>
      </div>
      <%}%>
    <%}%>

    <div class="text-center mt-3">
      <button type="button" class="btn btn-outline-secondary btn-sm" onclick="self.close()">닫기</button>
    </div>
  </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
