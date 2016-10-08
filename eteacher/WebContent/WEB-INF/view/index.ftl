<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var rootPath = '${context}';
</script>
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript">
	function jump(url,a){
		$('.navcurrent').removeClass();
		if(a){
			$(a).addClass('navcurrent');
		}
		$('iframe').attr('src',url);
	}
</script>
</head>

<body>
<!--头部开始-->
<div class="header">
	<div class="top">
    	<div class="container">
            <div class="welcome">欢迎您登录使用教学助手！</div>
            <ul class="icon-menu">
                <li class="iname"><a href="#" onclick="jump('user/viewEidtUserInfo')">${current_user.account}</a></li>
                <li class="imessage"><a href="#">信息</a></li>
                <li class="iset"><a href="#">设置</a></li>
                <li class="iexit"><a href="loginOut">退出</a></li>
            </ul>
        </div>
    </div>
    <div class="navtap">
    	<div class="container">
    	<div class="logo"><img src="${context}/images/logo.png" width="163" height="72" alt="" title="" /></div>
    	<!--导航栏开始-->
    	<ul class="navul">
        	<li><a href="#" class="navcurrent" title="首页" onclick="jump('viewToday',this)">首页</a></li>
        	<li><a href="#" class="" title="学期" onclick="jump('term/viewListTerm',this)">学期</a></li>
        	<li><a href="#" class="" title="课程" onclick="jump('course/viewListCourse',this)" id="courseMenu">课程</a></li>
        	<li><a href="#" class="" title="作业" onclick="jump('work/viewListWork',this)" id="workMenu">作业</a></li>
            <li><a href="#" class="" title="学生" onclick="jump('student/viewListStudent',this)">学生</a></li>
            <li><a href="#" class="" title="课表" onclick="jump('courseTable/viewCourseTable',this)">课表</a></li>
            <li><a href="#" class="" title="班级" onclick="jump('class/viewListClass',this)">班级</a></li>
            <li><a href="#" class="" title="通知" onclick="jump('notice/viewListNotice',this)">通知</a></li>
            <!--<li><a href="#" class="" title="学生提问" onclick="jump('statistics/viewWorkloadStatistics',this)">学生提问</a></li>-->    
            <li><a href="#" class="" title="教学统计" onclick="jump('statistics/viewWorkloadStatistics',this)">教学统计</a></li>       
        </ul> 
        </div>       
    </div>
</div><!--头部结束-->
<div class="content">
<iframe src="${context}/viewToday" id="main" frameborder="0" scrolling="auto" class="contiframe"></iframe>
</div>	

</body>
</html>
