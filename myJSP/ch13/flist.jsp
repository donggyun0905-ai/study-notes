<!-- flist.jsp -->
<%@page import="ch13.MUtil"%>
<%@page import="ch13.FileloadBean"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch13.FileloadMgr"/>
<%Vector<FileloadBean> vlist = mgr.listFile();%>
<%out.print(vlist.size());%>
<!doctype html>
<html>
<head>
<link href="style.css" rel="stylesheet" type="text/css">
<script>
	function allChk() {
		const f= document.frm;
		const chks = f.fch;
		const isAllChecked = f.allCh.checked;
		if(chks.length > 1){
			//2개 이상일때
			for(i=0;i<chks.length;i++){
				chks[i].checked = isAllChecked;
			}
		}else{
			//1개 일때
			chks.checked = isAllChecked;
		}
		f.btn.disabled = !isAllChecked;
		f.btn.style.color = isAllChecked ? "blue" : "gray";
	}

	function chk() {
		const f= document.frm;
		const chks = f.fch;
		isAnyChecked = false;//하나라도 체크되었는지 확인

		if(chks.length > 1){
			for(i=0;i<chks.length;i++){
				if(chks[i].checked){
					isAnyChecked = true;
					break;
				}
			}
		}else{
			if(chks.checked){
				isAnyChecked = true;
			}
		}

		if(isAnyChecked){
			f.allCh.checked = false;
		}

		f.btn.disabled = !isAnyChecked;
		f.btn.style.color = isAnyChecked ? "blue" : "gray";
	}
	
	function down(upFile) {
		document.downFrm.upFile.value = upFile;
		document.downFrm.submit();
	}
</script>
</head>
<body>
<div align="center">
<h2>File List</h2>
<form name="frm" action="fdeleteProc.jsp">
<table border="1" width="400">
	<tr align="center"> 
		<td><input type="checkbox" name="allCh" onclick="allChk()"></td>
		<td width="30">번호</td>
		<td>파일명1</td>
		<td>파일명2</td>
		<td>파일크기</td>
	</tr>
	<%
		for(int i =0;i<vlist.size();i++){
			FileloadBean bean = vlist.get(i);
			int num = bean.getNum();
			String upFile = bean.getUpFile();
	%>
	<tr align="center">
		<td><input type="checkbox" name="fch" onclick="chk()" value="<%=num%>"></td>
		<td><%=i+1%></td>
		<td><a href="storage/<%=upFile%>"download><%=upFile%></a></td>
		<td><a href="javascript:down('<%=upFile%>%>')"><%=upFile%></a></td>
		<td><%=MUtil.monFormat(bean.getSize())%>byte</td>
	</tr>
<% } %>
	<tr><td colspan="5">
		<input type="submit" name="btn" value="DELETE" disabled>
	</td></tr>
</table>
</form><p>
<a href="fupload.jsp">입력폼</a>
<form name="downFrm" method="post" action="fdownload.jsp">
	<input type="hidden" name="upFile">
</form>
</div>
</body>
</html>




