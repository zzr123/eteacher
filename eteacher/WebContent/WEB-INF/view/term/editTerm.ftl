<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/turingLib/validator.js"></script>
<script type="text/javascript" src="${context}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		$('#termForm').validatorInit(function(data){
			window.location.href = 'viewEditTerm';
		});
		$('#termName').validatorEmpty('学期名称');
		$('#startDate').validatorEmpty('起始日期');
		$('#endDate').validatorEmpty('终止日期');
		$('#weekCount').validatorZZS('学期时长');
	});
	
</script>
</head>
<body>
	<div class="maincont">
		<div class="title"><span>编辑学期</span></div>
        <div class="enter-message">
          <#list term as term>
            <form id="termForm" method="post" action="updateTerm">
            	<input name="termId" type="hidden" value="${term.termId}" />
                <div class="message-group">
                    <div class="message-left">学期名称：</div>
                    <div class="message-right">
                        <input id="termName" name="termName" type="text" maxlength="20" class="mess-control" value="${term.termName}" />
                        
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">起始日期：</div>
                    <div class="message-right">
                        <input id="startDate" name="startDate" readonly type="text" value="${term.startDate}" class="mess-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择起始时间" />
                        
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">终止日期：</div>
                    <div class="message-right">
                        <input id="endDate" name="endDate" readonly type="text" value="${term.endDate}" class="mess-control" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" placeholder="请选择终止时间" />
                        
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">学期时长：</div>
                    <div class="message-right">
                        <input id="weekCount" name="weekCount" maxlength="3" type="text" style="width:80px" class="mess-control" value="${term.weekCount}" /> 周
                    </div>                   	
                </div>
                <div class="btnsub">
                    <button type="submit" class="btn-submit" style="float:left;">保存</button>
                    <button type="button" class="btn-submit" style="float:right;" onclick="history.back();">返回</button>
                </div>
            </form> 
           </#list> 
        </div>
	</div>
	<#include "/footer.ftl" />
</body>
</html>