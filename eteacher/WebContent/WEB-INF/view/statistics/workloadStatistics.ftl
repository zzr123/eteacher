<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<link href="${context}/js/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${context}/js/bootstrap-table/bootstrap-table.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript">
	$(function(){
		var workloadTable = $('#workloadTable').bootstrapTable({
		    url: 'getWorkloadListData',
		    queryParams : function(params){
		    	params.year = $('#yearSelect').val();
		    	return params;
		    },
		    striped : true,
		    columns: [{
		        halign: 'center',
		        title: '名称',
		        formatter : function (value, row, index){
		        	return (row.workload?'<a href="javascript:void(0)" onclick="showhide(this)">':'')+row.workloadName+(row.workload?'</a>':'')
		        	+ (row.workload?'<div style="display:none">'+row.workload+'</div>':'');
		        }
		    }, {
		        field: 'workloadNumber',
		        halign: 'center',
		        title: '工作量'
		    }]
		});
	});
	function showhide(btn){
		if($(btn).next()){
			$(btn).next().toggle();
		}
	}
</script>
</head>
<body>
	<div class="maincont">
		<div class="title"><span>工作量统计</span></div>
		<div class="enter-message">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td width="8%" align="left" valign="middle"><label for="xueqi" style="line-height:36px; margin-bottom:0;">学年：</label></td>
                        <td>
                        	<select id="yearSelect" class="select-control" style="width:30%" onchange="workloadTable.bootstrapTable('refresh');">   
                        		<option value="2016">2015-2016学年</option>                 
                            </select>                                
                        </td>                    
                </tbody>
            </table>
			<table id="workloadTable"></table>
        </div>
	</div>
	<#include "/footer.ftl" />
</body>
</html>