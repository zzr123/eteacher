<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/json2form.js"></script>
<script type="text/javascript" src="${context}/js/turingLib/alert.js"></script>
<script type="text/javascript" src="${context}/js/turingLib/validator.js"></script>
<script type="text/javascript">
	function addRecord(type, value){
		var title = type=='email'?'邮箱':((type=='phone')?'联系电话':'IM');
		var value = value?value:'';
		var recordDiv = '';
		if(title == 'IM'){         
            recordDiv += '<div class="message-wrap">';
            recordDiv += '<div class="mess-input">';
            recordDiv += '<select style="width:40%">';
            recordDiv += '<option>--请选择IM类型--</option>';
            recordDiv += '</select>';
            recordDiv += ('<input name="'+type+'Text" type="text" class="mess-control" placeholder="请添加您的'+title+'" style=" width:58.7%;" value="'+value+'" />');
         	recordDiv += '</div>';
         	recordDiv += '<div class="mess-img">';
            recordDiv += '<a href="javascript:void(0);" class="delet-message" onclick="delRecord(this)"></a>';
            recordDiv += '</div>';
            recordDiv += '</div>';
            $('#'+type+'Div').append(recordDiv);	
		}else{
			recordDiv += '<div class="message-wrap">';
			recordDiv += '<div class="mess-input">';
	        recordDiv += ('<input name="'+type+'Text" type="text" class="mess-control" placeholder="请添加您的'+title+'" style=" width:100%;" value="'+value+'" />');
	        recordDiv += '</div>';
	        recordDiv += '<div class="mess-img">';
	        recordDiv += '<a href="javascript:void(0);" class="delet-message" onclick="delRecord(this)"></a>';
	        recordDiv += '</div>';
	        recordDiv += '</div> ';
			$('#'+type+'Div').append(recordDiv);
		}
	}
	
	function delRecord(btn){
		$(btn).parent().parent().remove();
	}
	
	function init(){
		var emails = $('#email').val();
		if(emails != ''){
			var emailsArr = emails.split('||');
			if(emailsArr.length>0){
				$('#emailText').val(emailsArr[0]);
			}
			for(var i=1;i<emailsArr.length;i++){
				addRecord('email',emailsArr[i]);
			}
		}
		
		var phones = $('#phone').val();
		if(phones != ''){
			var phonesArr = phones.split('||');
			if(phonesArr.length>0){
				$('#phoneText').val(phonesArr[0]);
			}
			for(var i=1;i<phonesArr.length;i++){
				addRecord('phone',phonesArr[i]);
			}
		}
	}

	$(function(){
		$('#userInfoForm').json2form({url:'getCurrentTeacherJson'});
		$('#userInfoForm').validatorInit(callback, prepareData);
		function callback(data){
			JAlert('保存成功');
		}
		function prepareData(){
			var emailArray = [];
			$("input[name='emailText']").each(function(){
				if($(this).val() != ''){
					emailArray.push($(this).val());
				}
			});
			$('#email').val(emailArray.join('||'));
			var phoneArray = [];
			$("input[name='phoneText']").each(function(){
				if($(this).val() != ''){
					phoneArray.push($(this).val());
				}
			});
			$('#phone').val(phoneArray.join('||'));
			return true;
		}
		init();
	});
	//职称下拉列表，选项选择后的操作
	$(document).ready(function () { 
 		$("#postSel").bind("change",function(){ 
   		if($(this).val()=="addPost"){
      		alert("触发新增职称事件"); 
   		}else{return;} 
  		}); 
	});
	
</script>
</head>

<body>
	<div class="maincont">
	<div class="title"><span>用户信息</span></div>
	<div class="enter-message">
    	<form id="userInfoForm" method="post" action="updateUserInfo">
    		<div class="message-group">
        		<div class="message-left">教工号：</div>
                <div class="message-right">
                	<input id="teacherNo" name="teacherNo" type="text" class="mess-control" placeholder="请输入您的教工号" />
                    
                </div>                   	
        	</div>
            <div class="message-group">
        		<div class="message-left">姓名：</div>
                <div class="message-right">
                	<input id="name" name="name" type="text" class="mess-control" placeholder="请输入您的姓名" />
                    
                </div>                   	
        	</div>
            <div class="message-group">
        		<div class="message-left">性别：</div>
                <div class="message-right">
                	<input name="sex" type="radio" value="0" checked="checked"  style=" margin-top:10px;"/> 男
                	<input name="sex" type="radio" value="1" style=" margin:12px 0 0 20px;" /> 女	
                </div>                   	
        	</div>
            <div class="message-group">
        		<div class="message-left">职称：</div>
                <div class="message-right" >       
                    <input id="titleSel" type="text" class="mess-control" placeholder="请选择职称" />
                </div>                   	
        	</div>
            <div class="message-group">
        		<div class="message-left">职务：</div>
                <div class="message-right">
                	<select id="postSel">
                    	<option selected="selected">--请选择您的职务--</option>
                        	<#list titleList as title> 
								<option value="${title.id}" <#if title.id == titleId?default("")>selected="selected"</#if>>${title.value}</option>
							</#list>
                        <option value="addPost" class="CustomAdd">新增职务</option>
                    </select>
        		</div>
            <div class="message-group">
        		<div class="message-left">学校：</div>
                <div class="message-right">
                	<input id="school" name="school" type="text" class="mess-control" placeholder="请输入您的学校" />                    
                </div>                   	
        	</div>
            <div class="message-group">
        		<div class="message-left">院系：</div>
                <div class="message-right">
                	<input id="department" name="department" type="text" class="mess-control" placeholder="请输入您的院系" />                    
                </div>                   	
        	</div>                                  
            <div class="message-group">
        		<div class="message-left">电子邮箱：</div>
        		<input id="email" name="email" type="hidden" />
                <div class="message-right" id="emailDiv">
                	<div class="message-wrap">
                        <div class="mess-input">
                            <input id="emailText" name="emailText" type="text" class="mess-control" placeholder="请添加您的邮箱" style=" width:100%;" />   
                        </div>
                        <div class="mess-img">
                            <a href="javascript:void(0);" class="add-message" onclick="addRecord('email')"></a>
                        </div>
                    </div>        
                </div>                                   	
        	</div>
        	    	
            <div class="message-group">
        		<div class="message-left">联系电话：</div>
        		<input id="phone" name="phone" type="hidden" />
                <div class="message-right" id="phoneDiv">
                	<div class="message-wrap">
                        <div class="mess-input">
                            <input id="phoneText" name="phoneText" type="text" class="mess-control" placeholder="请添加您的联系电话" id="" style=" width:100%;" />   
                        </div>
                        <div class="mess-img">
                            <a href="javascript:void(0);" class="add-message" onclick="addRecord('phone')"></a>
                        </div>
                    </div>               
                </div>
                                   	
        	</div>
                     	
        	<div class="message-group">
        		<div class="message-left">I M：</div>
        		<input id="IM" name="IM" type="hidden" />
                <div class="message-right" id="IMDiv">
                	<div class="message-wrap">
                        <div class="mess-input">
 							<select style="width:40%">
 								<option>--请选择IM类型--</option>
 								<option>Q Q</option>
 								<option>微信</option>
 								<option>GitHub</option>
 							</select>                       
                            <input id="IMText" name="IMText" type="text" class="mess-control" placeholder="请添加您的IM" id="" style=" width:58.7%;" />   
                        </div>
                        <div class="mess-img">
                            <a href="javascript:void(0);" class="add-message" onclick="addRecord('IM')"></a>
                        </div>
                    </div>               
                </div>
        	</div>
            <div class="message-group">
        		<div class="message-left">教师简介：</div>
                <div class="message-right">
                	
                    <textarea id="introduction" name="introduction" placeholder="教师简介（不超过200字）"></textarea>                    
                </div>                   	
        	</div>  
            <div class="btnsub" style="width:140px">
                <button type="submit" class="btn-submit">保存</button>
            </div>
        </form> 
    </div>
    </div>
	<#include "/footer.ftl" />
</body>
</html>
