<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<title>教学系统教师pc端</title>
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<link href="${context}/js/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${context}/js/chosen/chosen.css" rel="stylesheet"  type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<script type="text/javascript" src="${context}/js/turingLib/validator.js"></script>
<script type="text/javascript" src="${context}/js/json2form.js"></script>
<script type="text/javascript" src="${context}/js/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${context}/js/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript">
	var editFlag = '${editFlag?default("")}';
	$(function(){
		$('#courseForm').validatorNormalInit(prepareData);
		$('#courseName').validatorEmpty('课程名称');
		$('#classHours').validatorZZS('学时');
		$('#studentNumber').validatorZZS('学生数');
		//班级多选下拉
		$.post('${context}/class/getClassListData',function(data){
			$.each(data.data,function(i,r){
				$('#classes').append('<option value="'+r.classId+'">'+r.className+'</option>');
			});
			var classIdsJson = '${classIdsJson?default("")}';
			if(classIdsJson){
				var classIds = $.parseJSON(classIdsJson);
				$('#classes').val(classIds);
			}
			$('#classes').chosen();
		},'json');
		init();
	});
	function init(){
		if(editFlag){
			var courseJson = $.parseJSON('${courseJson?default("")}');
			$('#courseForm').json2form({data:courseJson});
			//专业
			loadMajorData($('#specialty1'),'0',courseJson.majorId.substring(0,2));
			loadMajorData($('#specialty2'),courseJson.majorId.substring(0,2),courseJson.majorId.substring(0,4));
			loadMajorData($('#specialty3'),courseJson.majorId.substring(0,4),courseJson.majorId);
			//工作量组成
//			var courseWorkloadsJson = '${courseWorkloadsJson?default("")}';
//			if(courseWorkloadsJson){
//				courseWorkloadArr = $.parseJSON(courseWorkloadsJson);
//				initWorkload();
//			}
			//授课方式
			
			//成绩组成
			var courseScoresJson = '${courseScoresJson?default("")}';
			if(courseScoresJson){
				courseScoreArr = $.parseJSON(courseScoresJson);
				initScore();
			}
			//教材
			var textbookJson = '${textbookJson?default("")}';
			if(textbookJson){
				textbook = $.parseJSON(textbookJson);
				$('#textbookSpan').text(textbook.textbookName);
			}
			//教辅
			var textbookOthersJson = '${textbookOthersJson?default("")}';
			if(textbookOthersJson){
				textbookOthers = $.parseJSON(textbookOthersJson);
				for(var i=0;i<textbookOthers.length;i++){
					addTextbookOtherHtml(textbookOthers[i].textbookName);
				}
			}
			//资源
			var courseFilesJson = '${courseFilesJson?default("")}';
			if(courseFilesJson){
				$.each($.parseJSON(courseFilesJson),function(i,n){
					$('#filesDiv').prepend('<div>' + n.fileName + '&nbsp;&nbsp;<i style="cursor: pointer;" title="删除" class="glyphicon glyphicon-trash" onclick="removeFile(this,\''+n.cfId+'\');"></i></div>');
				});
			}
		}
		else{
			//专业
			loadMajorData($('#specialty1'),'0');
		}
	}
	function loadMajorData(select,parentId,value){
		select.html('<option value="">--请选择专业--</option>');
		if(parentId||parent=='0'){
			$.post('${context}/class/getMajorSelectData',{parentId:parentId},function(data){
				$.each(data,function(i,r){
					select.append('<option value="'+r.majorId+'">'+r.majorName+'</option>');
				});
				if(value){
					select.val(value);
				}
			},'json');
		}
	}
	function addFile(btn){
		$(btn).parent().parent().clone().appendTo('#filesDiv');
	}
	function delFile(btn){
		if($('#filesDiv').children().length > 1){
			$(btn).parent().parent().remove();
		}
		else{
			$(btn).parent().parent().children(':file').val('');
		}
	}
	function removeFile(btn,cfId){
		if(confirm("确定删除")){
			$.post('deleteCourseFile',{cfId:cfId},function(data){
				$(btn).parent().remove();
			});
		}
	}
	function prepareData(){
		$('.workload').each(function(i,n){
			courseWorkloadArr[i].workloadPercent = n.value;
		});
		$('.percent').each(function(i,n){
			courseScoreArr[i].scorePercent = n.value;
		});
		$('#courseWorkloadArr').val(JSON.stringify(courseWorkloadArr));
		$('#courseScoreArr').val(JSON.stringify(courseScoreArr));
		$('#textbook').val(JSON.stringify(textbook));
		$('#textbookOthers').val(JSON.stringify(textbookOthers));
		return true;
	}
</script>
</head>
<body>
	<div class="maincont">
		<div class="title"><span>课程信息</span></div>
        <div class="enter-message">
            <form id="courseForm" method="post" action="saveCourse" enctype="multipart/form-data">
            	<input type="hidden" name="courseId" />
            	<input type="hidden" name="termId" value="${termId?default("")}" />
                <div class="message-group">
                    <div class="message-left">课程名称：</div>
                    <div class="message-right">
                        <input id="courseName" name="courseName" maxlength="40" type="text" class="mess-control" value="" placeholder="请输入课程名称" />                        
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">课程简介：</div>
                    <div class="message-right">
                        <textarea id="introduction" name="introduction" maxlength="200" placeholder="课程简介（不超过200字）"></textarea>                         
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">学时：</div>
                    <div class="message-right">
                        <input id="classHours" name="classHours" maxlength="5" type="text" class="mess-control" placeholder="请输入学时" />                        
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">专业：</div>
                    <div class="message-right">
                        <select id="specialty1" onchange="loadMajorData($('#specialty2'),this.value);loadMajorData($('#specialty3'));">
                            <option value="">--请选择专业--</option>
                        </select>    
                        <select id="specialty2" onchange="loadMajorData($('#specialty3'),this.value);" style="margin-top:5px">
                            <option value="">--请选择专业--</option>
                        </select>      
                        <select id="specialty3" name="specialty" style="margin-top:5px">
                            <option value="">--请选择专业--</option>
                        </select>                 
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">授课方式：</div>
                    <div class="message-right">                	
                        <select id="teachingMethod" name="teachingMethod">
                            <option value="">--请选择授课方式--</option>
                            <option>理论</option>
                            <option>实践</option>
                        </select>
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">课程类型：</div>
                    <div class="message-right">
                        <select id="courseType" name="courseType">
                            <option value="">--请选择课程类型--</option>
                            <option>公共选修课</option>
                            <option>公共必修课</option>
                            <option>专业基础课</option>
                            <option>专业主干课</option>
                        </select>                      
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">考核方式：</div>
                    <div class="message-right">                	
                        <select id="examinationMode" name="examinationMode">
                            <option selected="selected">--请选择考核方式--</option>
                            <option>考试课</option>
                            <option>考查课</option>
                        </select>
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">工作量统计公式：</div>
                    <div class="message-right">
                        <input id="courseWorkloadDiv" name="formula" maxlength="5" type="text" class="mess-control" placeholder="请输入工作量统计公式" />                        
                    </div> 
 <!--                   <div class="message-left">工作量统计公式：</div>
                    <div class="message-right" id="courseWorkloadDiv">
                    	<input type="hidden" id="courseWorkloadArr" name="courseWorkloadArr" />
                   		<i style="cursor: pointer;" data-toggle="modal" data-target="#courseWorkloadModal" title="设置" class="glyphicon glyphicon-edit"></i>
                    </div>  -->                   	
                </div>
                <div>
                    <div class="message-left">授课班级：</div>
                    <div style="margin-left:10px;float:left;">      
                    	<select id="classes" name="classes" data-placeholder="请选择班级" class="" multiple style="width:567px;">
                    	</select> 
                    </div>                   	
                </div>
<!--                 <div class="message-group">
                    <div class="message-left">学生数：</div>
                    <div class="message-right">
                        <input id="studentNumber" name="studentNumber" maxlength="5" type="text" class="mess-control" placeholder="请输入学生数" />                        
                    </div>                   	
                </div> -->
                <div class="message-group">
                    <div class="message-left">成绩组成：</div>
                    <div class="message-right" id="courseScoreDiv">
                    	<input type="hidden" id="courseScoreArr" name="courseScoreArr" />
                   		<i style="cursor: pointer;" data-toggle="modal" data-target="#courseScorekModal" title="设置" class="glyphicon glyphicon-edit"></i>
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">教材：</div>
                    <div class="message-right">  
                    	<input type="hidden" id="textbook" name="textbook" />
                    	<span id="textbookSpan">请设置教材..</span>              	
                        <i style="cursor: pointer;" data-toggle="modal" data-target="#textbookModal" data-type="01" title="添加" class="glyphicon glyphicon-edit"></i>
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">教辅：</div>
                    <div class="message-right" id="textbookOthersDiv">  
                    	<input type="hidden" id="textbookOthers" name="textbookOthers" />
                    	<span>添加教辅..</span>              	
                        <i style="cursor: pointer;" data-toggle="modal" data-target="#textbookModal" data-type="02" title="添加" class="glyphicon glyphicon-plus"></i>
                    </div>                   	
                </div>
                <div class="message-group">
                    <div class="message-left">资源：</div>
                    <div class="message-right" id="filesDiv">
                        <div class="message-wrap">
                            <select name="fileTypes" style="width:120px;float:left;" title="选择资源类型">
                                <option value="01">大纲</option>
                                <option value="02">日历</option>
                                <option value="03">教案</option>
                                <option value="04">课件</option>
                            </select>
                            <input name="files" type="file" class="mess-control" placeholder="" id="" style="width:200px;float:left;" />                      		
                            <select name="fileAuths" style="width:100px;float:left;" title="选择是否公开">
                                <option value="01">公开</option>
                                <option value="02">不公开</option>                            
                            </select>
                            <div id="toolbar" class="btn-group" style="float:left;">
							    <button type="button" class="btn btn-default" style="height:34px;" title="新增" onclick="addFile(this);">
							        <i class="glyphicon glyphicon-plus"></i>
							    </button>
							    <button type="button" class="btn btn-default" style="height:34px;" title="删除" onclick="delFile(this);">
							        <i class="glyphicon glyphicon-remove"></i>
							    </button>
							</div>
                        </div>                                 
                    </div>       	
                </div>
                <div class="btnsub">
                    <button type="submit" class="btn-submit" style="float:left;">保存</button>
                    <button type="button" class="btn-submit" style="float:right;" onclick="history.back();">返回</button>
                </div>
            </form> 
        </div>
	</div>
	<#include "/footer.ftl" />
</body>
<#include "textbookModal.ftl" />
<#include "scorePercentModal.ftl" />
<#include "workloadModal.ftl" />
</html>