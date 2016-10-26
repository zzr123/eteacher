<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var rootPath = '${context}';
</script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/turingLib/alert.js"></script>
<script type="text/javascript" src="${context}/js/turingLib/validator.js"></script>
<script type="text/javascript">
	$(function(){
		$('#loginForm').validatorInit(function(data){
			if(data == 'success'){
				window.location.href = 'index';
			}
			else{
				JAlert(data);
			}
		});
		$('#account').formValidator({
			onFocus : '请输入手机号',
			onCorrect : '手机号输入格式正确'
		}).regexValidator( {
			regExp : '^[1][3578][0-9]{9}$',
			onError : '请输入正确手机号'
		});
		$('#account').validatorEmpty('账号');
		$('#password').validatorEmpty('密码');
	});
</script>
</head>

<body>
<div class="register-box">
	<div class="panel">
    	<div class="panel-body">
        	<div class="reheader">
            	<img src="images/relogo.png" width="240" height="125" alt="" title="" />
                <p>用户登录</p>
            </div>
            <form id="loginForm" method="post" action="loginOk" class="register">
            	<div class="register-body">
            		<div class="form-group">
                    	<label>账号：</label>
                        <input id="account" onkeyup="value=value.replace(/[^\d{1,}\d{1,}|\d{1,}]/g,'')" maxlength="11" name="account" type="text" class="form-control" placeholder="手机号" />
                    </div>
                    
                    <div class="form-group">
                    	<label>密码：</label>
                        <input id="password" maxlength="20" onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" name="password" type="password" class="form-control" placeholder="密码" />
                    </div>                   
                    
                    <div class="checkbox-custom">
                    	<div class="agreeterm">
                        	<a href="#">忘记密码？</a>
                        </div>
                        <div class="register-btn">
                        	<button type="submit" class="btn-primary">登录</button>
                        </div>
                    </div>
                    
                    <div class="textad">
                    	<p>还没有账号？ <a href="register">马上注册</a></p>
                    </div>
                </div>
            </form>
        </div>
        
    </div>
    <div class="register-bottom">
    	    版权所有 河北图灵 
    </div>
</div>

</body>
</html>
