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
	var studentTable;
	var courseId = '${courseId?default("")}';
	$(function(){
		studentTable = $('#studentTable').bootstrapTable({
		    url: 'getStudentListData',
		    queryParams : function(params){
		    	params.termId = $('#termSelect').val();
		    	params.courseId = $('#courseSelect').val();
		    	courseId = params.courseId;
		    	return params;
		    },
		    striped : true,
		    toolbar : '#toolbar',
		    search : true,
		    showRefresh : true,
		    columns: [{
		        field: 'stuNo',
		        halign: 'center',
		        title: '学号'
		    }, {
		        field: 'stuName',
		        halign: 'center',
		        title: '姓名'
		    }, {
		        align: 'center',
		        valign: 'middle',
		        title: '是否出勤',
		        formatter : function (value, row, index){
		        	return '<span class="label label-success">签到</span>';
		        }
		    }, {
		        align: 'center',
		        title: '出勤率'
		    }, {
		    	field: 'normalScoreCount',
		        align: 'center',
		        title: '提问次数'
		    },  {
		        title: '操作',
		        align: 'center',
		        formatter : function (value, row, index){
		        	return '<i style="cursor: pointer;" title="课堂提问打分" class="glyphicon glyphicon-check"></i>';
		        },
		        events : {
		        	'click .glyphicon-check': function (e, value, row, index) {
		        		$.post('isCourseTime',{courseId:courseId},function(data){
		        			if(data.startTime){
		        				$('#courseScorekModal').modal('show');
			          	 		$('#stuId').val(row.stuId);
		        			}
		        			else{
		        				alert('该功能只能在上课时间使用');
		        			}
		        		},'json')
			            
			        }
		        }
		    } ]
		});
	});
	function updateCourseSelect(termId){
		$.post('${context}/course/getCourseListData',{termId:termId},function(data){
			var list = data.data;
			$('#courseSelect').empty();
			$.each(list,function(i,n){
				$('#courseSelect').append('<option value="'+n.courseId+'">'+n.courseName+'</option>');
			});
		},'json')
	}
	function viewScore(){
		//window.location.href = 'viewAddWork?termId=' + $('#termSelect').val() + '&courseId=' + $('#courseSelect').val();
		if(courseId == null){
			return;
		}
		window.location.href = '${context}/score/viewListScore?courseId=' + courseId;
	}
	function viewStatistics(){
		if(courseId == null){
			return;
		}
		window.location.href = '${context}/statistics/viewCourseStatistics?courseId=' + courseId;
	}
</script>
</head>
<body>
	<div class="maincont">
        <div class="title"><span>学生列表</span></div>
        <div class="enter-message">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
            	<tbody>
                	<tr>
                    	<td width="12%" align="left" valign="middle"><label for="xueqi" style="line-height:36px; margin-bottom:0;">学期名称：</label></td>
                        <td width="30%">
                        	<select id="termSelect" class="select-control" onchange="updateCourseSelect(this.value)">                            
                                <#list termList as term> 
									<option value="${term.termId}" <#if term.termId == termId?default("")>selected="selected"</#if>>${term.termName}</option>
								</#list>   
                            </select>                                
                        </td>
                        <td width="12%" align="left" valign="middle"><label for="kecheng" style="line-height:36px; margin-bottom:0;">课程名称：</label></td>
                        <td width="36%">
                        	<select id="courseSelect" class="select-control">
                        		<#list courseList as course> 
									<option value="${course.courseId}" <#if course.courseId == courseId?default("")>selected="selected"</#if>>${course.courseName}</option>
								</#list>
                        	</select>                                
                        </td>
                        <td width="10%">
                        	<button type="button" class="btn btn-default btn-sm" onclick="studentTable.bootstrapTable('refresh');">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span> 搜索
							</button>
						</td>
                    </tr>                        
                </tbody>
            </table>
            <div id="toolbar">
			    <button type="button" class="btn btn-default btn-sm" title="成绩" onclick="viewScore();">
			        <i class="glyphicon glyphicon-book"></i> 成绩
			    </button>
			    <button type="button" class="btn btn-default btn-sm" title="成绩" onclick="viewStatistics();">
			        <i class="glyphicon glyphicon-th-large"></i> 统计
			    </button>
			</div>                          
            <table id="studentTable"></table>
        </div>
    </div>
	<#include "/footer.ftl" />
	<#include "courseScoreModal.ftl" />
</body>
</html>