<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>METRIC STATUS</title>
</head>
<script src="./js/moment.min.js"></script>
<script src="./js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="./css/metric_stat.css">
<script>
function goDetail(host,flow){
	var frm=document.test;
	test.action="metric_stat_detail?host_name="+host+"&flow_num="+flow;
	test.submit();
}
$(document).ready(function(){
	var list=new Array();
	var sDate=new Array();
    var eDate=new Array();
    var timeGap=new Array();
    var diffDate=new Array(); var diffHour=new Array(); var diffMin=new Array(); var diffSec=new Array();
    var i=0;var j=0; var st;
    var check_time;var s_st;var d_st;
    var k=0;var x=1;var host_name;var flow_num;
    var error_cnt=0; var dead_cnt=0;
    
	<c:forEach items="${StatList}" var="stat">
		list.push("${stat.host_name}");
		list.push("${stat.flow_num}");
		if("${stat.ch_fill_percentage}"!="")
			list.push("${stat.ch_fill_percentage}");
		else list.push("-");
		sDate[i] = new Date("${stat.start_time}"*1);
	    check_time="${stat.check_time}";
	    eDate[i]=check_time.substr(0,19);
	    eDate[i]  = new Date(moment(eDate[i]));
	    timeGap[i] = new Date(0, 0, 0, 0, 0, 0, eDate[i] - sDate[i]);
	    if(eDate[i].getDate() == sDate[i].getDate()){
	  	  diffDate[i] = 0;
	    }else{
	  	  diffDate[i] = timeGap[i].getDate();
	    }
	    diffHour[i] = timeGap[i].getHours();
	    diffMin[i] = timeGap[i].getMinutes();
	    diffSec[i] = timeGap[i].getSeconds();
	    
	    s_st=sDate[i].getFullYear()+'년 '+(sDate[i].getMonth()+1)+'월'+sDate[i].getDate()+'일'+sDate[i].getHours()+'시 ' + sDate[i].getMinutes()+'분';
	    d_st=diffDate[i]+'일'+diffHour[i] + '시간' + diffMin[i] + '분'+ diffSec[i]+ '초';
	    i++;
	    
	    list.push(s_st);
	    list.push(d_st);
		list.push("${stat.status}");
	</c:forEach>
    
	for(var i=0;i<list.length/6;i++){
       	host_name='"'+list[k]+'"';
       	flow_num='"'+list[x]+'"';
    	var data="<tr align='center'><td width='10%'><a href='javascript:goDetail("+host_name+","+flow_num+")'>"+list[j++]+"</a></td>"
		+"<td width='5%'><a href='javascript:goDetail("+host_name+","+flow_num+")'>"+list[j++]+"</a></td>"
		+"<td width='5%'><a href='javascript:goDetail("+host_name+","+flow_num+")'>"+list[j++]+"</a></td>"
		+"<td width='10%'><a href='javascript:goDetail("+host_name+","+flow_num+")'>"+list[j++]+"</a></td>"
		+"<td width='10%'><a href='javascript:goDetail("+host_name+","+flow_num+")'>"+list[j++]+"</a></td>"
		+"<td width='5%'><a href='javascript:goDetail("+host_name+","+flow_num+")'>";
		
    	if(list[j]=='y'){
    		data+="<img src='./images/yes.png'>"
    	}else if(list[j]=='i'){
    		data+="<img src='./images/ing.png'>"
    	}else if(list[j]=='e'){
    		error_cnt++;
    		data+="<img src='./images/error.png'>"
    	}else{
    		dead_cnt++;
    		data+="<img src='./images/dead.png'>"
    	}
    	data+="</a></td></tr>";
    	$("tbody").append(data);
		j++;k+=6;x+=6;
    }
	
	$("#all").text(list.length/6+" ");
	$("#error").text(" "+error_cnt+" ");
	$("#dead").text(" "+dead_cnt+" ");
});
</script>

<body>
<form name="test" id="test" method="post">
	<div align="right">
		<label>| Total flow : </label><label id="all"> </label>
		<label>| Error flow : </label><label id="error"></label>
		<label>| Dead flow : </label><label id="dead"></label>|
	</div>
	<table style="width:100%">
		<thead>
			<tr align="center">
				<td>Host name</td>
				<td>Flow</td>
				<td>Channel Fill Percentage</td>
				<td>Start Time</td>
				<td>Total Run Time</td>
				<td>Status</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</form>
</body>
</html>