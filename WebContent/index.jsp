<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.jushi.pojo.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<title>�г�ʱ�̲�ѯ</title>
<head>
<script type="text/javascript">
  		
	function query_code() {
		if (document.form1.traincode.value == '') {
			alert("������һ������!!");
			return;
		}
		var traincode = document.form1.traincode.value;
		document.getElementById("loading").style.display = "block";
		document.form1.action = "TrainService?traincode=" + traincode;
		document.form1.submit();
	}

	function _showDivs(obj) {
		if (obj.value == '0') {
			document.getElementById("loading").style.diplay = "none";
			showstation.style.display = "none";
			showcode.style.display = "block";
		} else {
			document.getElementById("loading").style.diplay = "block";
			showstation.style.display = "block";
			showcode.style.display = "none";
		}
	}

	function query_station() {
		if (document.form1.firststation.value == ''
				|| document.form1.laststation.value == '') {
			alert("����д����ʼ��վ���յ�վ!!");
			return;
		}
		var firststation = document.form1.firststation.value;
		var laststation = document.form1.laststation.value;
		document.getElementById("loading").style.display = "block";
		document.form1.action = "TrainService?firststation=" + firststation + "&laststation=" + laststation;
		document.form1.submit();
	}
</script>
</head>

<body>
	<%
		request.setCharacterEncoding("GBK");
		ArrayList trainList = null, trainListStation = null;
		response.setCharacterEncoding("GBK");
		if (request.getAttribute("trainlist") != null) {
			trainList = (ArrayList) request.getAttribute("trainlist");
		}

		if (request.getAttribute("trainlistStation") != null) {
			trainListStation = (ArrayList) request.getAttribute("trainlistStation");
		}
	%>
	<center>
		<h2>�г���ѯ</h2>
		<select name="queryfor" id="queryfor" onchange="_showDivs(this)">
			<option value="1" selected="selected">--��ʼ���յ�վ--</option>
			<option value="0">--������--</option>
		</select> <br />
		<form name="form1">
			<div id='showcode' name="showcode">
				<table id="tab" align="center" border="1" style="border: thin; display: none;">
					<tr>
						<td>�����복�Σ�<input type="text" name="traincode" id="traincode" size="20" /></td>
						<td>
							<button id="btok" name="btok" type="button" onclick="query_code()">�� ѯ</button>
						</td>
					</tr>
				</table>
			</div>
			<span id="loading" style="display:none; color: red;font-size: 14px;">��ѯ��... ...</span>
			<div id="showstation" name="showstation" style="display: block">
				<table id="tab" align="center" border="1" style="border: thin;">
					<tr>
						<td>ʼ��վ��<input type="text" name="firststation" id="firststation" size="20" /> �յ�վ��<input type="text" name="laststation" id="laststation" size="20" /></td>
						<td>
							<button id="btok" name="btok" type="button" onclick="query_station()">��ѯ</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<hr />
		<table id="tab" align="center" border="1" bordercolor="green"
			style="border: thick;">
			<%if(trainList!=null){ %>
			<tr>
				<td colspan="4" align="center" bgcolor="#FFF000">�� �г�ʱ�̱� ��</td>
			</tr>
			<tr>
				<td colspan="4" align="center"><font color=red>��ѯ���
						(��������Ϊ��ʼ��վ-�յ�վ) ���£�</font></td>
			</tr>
			<%
  	 		for(int i=0; i<trainList.size(); i++){
  	 			Train train = (Train) trainList.get(i);
  	 	%>
			<tr>
				<td bgcolor="#FFCCFF" align="center">��վ</td>
				<td bgcolor="#FFCCFF" align="center">����ʱ��</td>
				<td bgcolor="#FFCCFF" align="center">��վʱ��</td>
				<td bgcolor="#FFCCFF" align="center">·��(KM)</td>
			</tr>
			<tr>
				<td align="center"><%=train.getTrainStation().equals("")?"--":train.getTrainStation() %></td>
				<td align="center"><%=train.getStartTime().equals("")?"--":train.getStartTime() %></td>
				<td align="center"><%=train.getArriveTime().equals("")?"--":train.getArriveTime() %></td>
				<td align="center"><%=train.getKm().equals("")?"--":train.getKm() %></td>
			</tr>
			<%
				}
				}
			%>
			<%
				if (trainListStation != null) {
			%>
			<tr>
				<td colspan="9" align="center" bgcolor="#FFF000">�� �г�ʱ�̱� ��</td>
			</tr>
			<%
				for (int i = 0; i < trainListStation.size(); i++) {
						Train train = (Train) trainListStation.get(i);
			%>
			<tr>
				<td bgcolor="#FFCCCC" align="center">����</td>
				<td bgcolor="#FFCCCC" align="center">ʼ��վ</td>
				<td bgcolor="#FFCCCC" align="center">�յ�վ</td>
				<td bgcolor="#FFCCCC" align="center">·��(KM)</td>
				<td bgcolor="#FFCCCC" align="center">����վ</td>
				<td bgcolor="#FFCCCC" align="center">����ʱ��</td>
				<td bgcolor="#FFCCCC" align="center">����վ</td>
				<td bgcolor="#FFCCCC" align="center">����ʱ��</td>
				<td bgcolor="#FFCCCC" align="center">��ʱ</td>
			</tr>
			<tr>
				<td align="center"><%=train.getTrainCode().equals("")?"--":train.getTrainCode() %></td>
				<td align="center"><%=train.getFirstStation().equals("")?"--":train.getFirstStation() %></td>
				<td align="center"><%=train.getLastStation().equals("")?"--":train.getLastStation() %></td>
				<td align="center"><%=train.getKm().equals("")?"--":train.getKm() %></td>
				<td align="center"><%=train.getStartStation().equals("")?"--":train.getStartStation() %></td>
				<td align="center"><%=train.getStartTime().equals("")?"--":train.getStartTime() %></td>
				<td align="center"><%=train.getArriveStation().equals("")?"--":train.getArriveStation() %></td>
				<td align="center"><%=train.getArriveTime().equals("")?"--":train.getArriveTime() %></td>
				<td align="center"><%=train.getUseDate().equals("")?"--":train.getUseDate() %></td>
			</tr>
			<%}} %>
		</table>
	</center>
</body>
</html>
<%
	request.removeAttribute("trainlist");
	request.removeAttribute("trainlistStation");	
%>