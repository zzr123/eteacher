<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<link href="${context}/js/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${context}/js/echarts.common.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('a[href="#scoreDiv"]').on('show.bs.tab', function (e) {
			var scoreType = $(e.target).data('type');
			var title = $(e.target).text();
			$.post('getScoreStatisticsData',{courseId:'${courseId?default("")}',scoreType:scoreType},function(data){
				scoreChart.setOption({title : {text: title},series: [{data:data}]});
			},'json');
		});
		$('a[href="#courseDiv"]').on('show.bs.tab', function (e) {
			if($(e.target).data('type') == 'record'){
				courseChart.setOption({
					title : {text: '课堂出勤统计'},
					series : [
						        {
						            data:[
						            	{value:2, name:'未签到'},
						                {value:22, name:'迟到'},
						                {value:44, name:'签到'}
						            ]
						        }
						    ]
				});
			}
			else{
				courseChart.setOption({
					title : {text: '课程出勤统计'},
					series : [
						        {
						            data:[
						            	{value:4, name:'未签到'},
						                {value:11, name:'迟到'},
						                {value:60, name:'签到'}
						            ]
						        }
						    ]
				});
			}
		});
	});
</script>
</head>
<body>
	<div class="maincont">
        <div class="title"><span>${courseName?default("")}统计</span></div>
        <div class="enter-message">
        	<ul class="nav nav-tabs">
        	  <li class="dropdown  active">
			    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
			      	出勤统计 <span class="caret"></span>
			    </a>
			    <ul class="dropdown-menu">
			      	<li><a data-type="record" href="#courseDiv" data-toggle="tab">课堂统计</a></li>
			  		<li><a data-type="all" href="#courseDiv" data-toggle="tab">课程统计</a></li>
			    </ul>
			  </li>
			  <li class="dropdown">
			    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
			      	成绩统计 <span class="caret"></span>
			    </a>
			    <ul class="dropdown-menu">
			    <#list CourseScoreList as courseScore> 
			      	<li><a href="#scoreDiv" data-type="${courseScore.csId}" data-toggle="tab">${courseScore.scoreName}</a></li>
			  	</#list>
			    </ul>
			  </li>
			  <button type="button" style="float:right" class="btn btn-default btn-sm" title="返回" onclick="history.back();">
		        <i class="glyphicon glyphicon-share-alt"></i> 返回
		      </button>
			</ul>
			<br />
			<div class="tab-content">
			    <div class="tab-pane" id="scoreDiv">
					<div id="scoreChart" style="width: 600px;height:400px;"></div>
				    <script type="text/javascript">
				        // 基于准备好的dom，初始化echarts实例
				        var scoreChart = echarts.init(document.getElementById('scoreChart'));
				        // 指定图表的配置项和数据
				        var option = {
				            title: {
				                x:'center'
				            },
				            tooltip : {
						        formatter: "{a} <br/>{b} : {c}人"
						    },
				            xAxis: {
				                data: ["0-60分","60-70分","70-80分","80-90分","90-100分"]
				            },
				            yAxis: {},
				            series: [{
				                name: '人数',
				                type: 'bar',
				                data: [5, 20, 36, 10, 10]
				            }]
				        };
				        // 使用刚指定的配置项和数据显示图表。
				        scoreChart.setOption(option);
				    </script>
				</div>
			    <div class="tab-pane active" id="courseDiv">
			    	<div id="courseChart" style="width: 600px;height:350px;"></div>
			    	<script type="text/javascript">
			    		var courseChart = echarts.init(document.getElementById('courseChart'));
						var option = {
						    title : {
						        text: '课堂出勤统计',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        orient: 'vertical',
						        x: 'left',
						        data: ['未签到','迟到','签到']
						    },
						    series : [
						        {
						            name: '人数',
						            type: 'pie',
						            data:[
						            	{value:2, name:'未签到'},
						                {value:22, name:'迟到'},
						                {value:44, name:'签到'}
						            ]
						        }
						    ]
						};
						courseChart.setOption(option);
					</script>
			    </div>
			</div>
        </div>
    </div>
	<#include "/footer.ftl" />
</body>
</html>