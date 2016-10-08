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
<script type="text/javascript" src="${context}/js/turingLib/alert.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript">
	$(function(){
		var termTable = $('#termTable').bootstrapTable({
		    url: 'getTermListData',
		    striped : true,
		    toolbar : '#toolbar',
		    idField : 'termId',
		    columns: [{
		        title: '序号',
		        align: 'center',
		        formatter : function (value, row, index){
		        	return index + 1;
		        }
		    },{
		        field: 'termName',
		        halign: 'center',
		        title: '学期名称',
		        formatter : function (value, row, index){
		        	//return '<a href="${context}/course/viewListCourse?termId='+row.termId+'">'+value+'</a>';
		        	var url = '${context}/course/viewListCourse?termId=' + row.termId;
		        	return '<a href="#" onclick="parent.jump(\''+url+'\',parent.document.getElementById(\'courseMenu\'))">'+value+'</a>';
		        }
		    }, {
		        field: 'startDate',
		        halign: 'center',
		        title: '开始日期'
		    }, {
		        field: 'endDate',
		        halign: 'center',
		        title: '结束日期'
		    }, {
		        title: '操作',
		        field: 'termId',
		        align: 'center',
		        formatter : function (value, row, index){
		        	return '<i style="cursor: pointer;" title="编辑" class="glyphicon glyphicon-edit"></i> &nbsp;'
		        		 + '<i style="cursor: pointer;" title="删除" class="glyphicon glyphicon-trash"></i>';
		        },
		        events : {
		        	'click .glyphicon-edit': function (e, value, row, index) {
			            window.location.href = 'viewEditTerm?termId=' + value;
			        },
			        'click .glyphicon-trash': function (e, value, row, index) {
			        	if(confirm("确定删除")){
			            	//$.post('deleteTerm1',{termId : value},function(data){
			            	//	if(data!='success'){
			            	//		alert(data);
			            	//	}
				            //	termTable.bootstrapTable('refresh');
				            //});
				            $.ajax({
				            	url : 'deleteTerm1',
				            	type : 'post',
				            });
			            }
			        }
		        }
		    } ]
		});
	});
</script>
</head>
<body>
	<div class="maincont">
		<div class="title"><span>学期列表</span></div>
		<div class="enter-message">
			<div id="toolbar" class="btn-group">
			    <button type="button" class="btn btn-default  btn-sm" title="新增" onclick="window.location.href='viewAddTerm'">
			        <i class="glyphicon glyphicon-plus"></i> 新增
			    </button>
			</div>
			<table id="termTable"></table>
        </div>
	</div>
	<#include "/footer.ftl" />
</body>
</html>