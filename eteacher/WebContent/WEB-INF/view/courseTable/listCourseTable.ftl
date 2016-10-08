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
<link href="${context}/js/chosen/chosen.css" rel="stylesheet"  type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${context}/js/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript">
	var courseTableTable;
	$(function(){
		courseTableTable = $('#courseTableTable').bootstrapTable({
		    url: 'getCourseTableListData?courseId=${courseId}',
		    striped : true,
		    toolbar : '#toolbar',
		    idField : 'ctId',
		    columns: [{
		        title: '序号',
		        align: 'center',
		        formatter : function (value, row, index){
		        	return index + 1;
		        }
		    },{
		        halign: 'center',
		        title: '起止周',
		        formatter : function (value, row, index){
		        	return row.startWeek + ' ~ ' + row.endWeek;
		        }
		    }, {
		        halign: 'center',
		        title: '重复',
		        formatter : function (value, row, index){
		        	return '每' + (row.repeatNumber==1?'':row.repeatNumber) + (row.repeatType=='01'?'天':('周 星期' + row.weekday));
		        }
		    }, { 
		        halign: 'center',
		        title: '课程安排',
		        formatter : function (value, row, index){
		        	return '第' + row.lessonNumber + '节';
		        }
		    }, {
		        title: '操作',
		        align: 'center',
		        formatter : function (value, row, index){
		        	return '<i style="cursor: pointer;" title="编辑" class="glyphicon glyphicon-edit"></i> &nbsp;'
		        		 + '<i style="cursor: pointer;" title="删除" class="glyphicon glyphicon-trash"></i>';
		        },
		        events : {
		        	'click .glyphicon-edit': function (e, value, row, index) {
		        		courseTableRecord = row;
			            $('#courseTablekModal').modal('show');
			        },
			        'click .glyphicon-trash': function (e, value, row, index) {
			        	if(confirm("确定删除")){
			            	$.post('deleteCourseTable',{ctId : row.ctId},function(data){
				            	courseTableTable.bootstrapTable('refresh');
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
		<div class="title"><span>课程课表</span></div>
		<div class="enter-message">
			<div id="toolbar">
				<button type="button" class="btn btn-default btn-sm" title="新增" data-toggle="modal" data-target="#courseTablekModal">
			        <i class="glyphicon glyphicon-plus"></i> 新增
			    </button>
			    <button type="button" class="btn btn-default btn-sm" title="返回" onclick="history.back();">
			        <i class="glyphicon glyphicon-share-alt"></i> 返回
			    </button>
			</div>
			<table id="courseTableTable"></table>
        </div>
	</div>
	<#include "/footer.ftl" />
</body>
<#include "courseTableModal.ftl" />
</html>