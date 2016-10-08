<div id="courseTablekModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">课程课表</h4>
      </div>
      <form id="courseTableForm" method="post" action="addCourseTable">
      <input type="hidden" name="ctId" />
      <input type="hidden" name="courseId" value="${courseId}" />
      <div class="modal-body">
            <div class="message-group">
                <div class="message-left">起止周：</div>
                <div class="message-right">
                                                     第 <input name="startWeek" style="width:58px;" type="text" class="mess-control" value="" /> 周 
                    ~
                    <input name="endWeek" style="width:58px;" type="text" class="mess-control" value="" /> 周 
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">重复：</div>
                <div class="message-right">
                   	每 <input id="repeatNumber" name="repeatNumber" style="width:58px;" type="text" class="mess-control" value="" />
                    <select id="repeatType" name="repeatType" style="width:58px;" onchange="$('#repeatNumber').toggle();$('#weekdaySpan').toggle();">
                        <option value="02">周</option>
                        <option value="01">天</option>
                    </select>
                    <span id="weekdaySpan">星期 <input name="weekday" style="width:58px;" type="text" class="mess-control" value="" />(多个英文逗号分隔如2,4)</span>
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">课程安排：</div>
                <div class="message-right">
                   	第
                   	<select id="lessonNumber" name="lessonNumber" style="width:78px;">
                   		<option>1,2</option>
                   		<option>3,4</option>
                   		<option>5,6</option>
                   		<option>7,8</option>
                   		<option>9,10</option>
                   		<option>11,12</option>
                   	</select>
                   	 节          
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">上课地点：</div>
                <div class="message-right">
                    <input name="location" type="text" class="mess-control" value="" />
                    
                </div>                   	
            </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
        <button type="submit" class="btn btn-default">保 存</button>
      </div>
      </form> 
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript" src="${context}/js/turingLib/validator.js"></script>
<script type="text/javascript" src="${context}/js/json2form.js"></script>
<script>
	var courseTableRecord = null;
	//打开时初始化数据
	$('#courseTablekModal').on('show.bs.modal', function (e) {
		if(courseTableRecord){//编辑
			$('#courseTableForm').json2form({data:courseTableRecord});
			if(courseTableRecord.repeatType=='01'){
				$('#repeatNumber').hide();
				$('#weekdaySpan').hide();
			}
			else{
				$('#repeatNumber').show();
				$('#weekdaySpan').show();
			}
			$('#courseTableForm').attr('action', 'updateCourseTable');
		}
		else{
			$('#repeatNumber').show();
			$('#weekdaySpan').show();
			$('#courseTableForm').attr('action', 'addCourseTable');
		}
	})
	//关闭后清空表单
	$('#courseTablekModal').on('hidden.bs.modal', function (e) {
	  	document.getElementById("courseTableForm").reset();
	  	$('#repeatType').val('02');//reset不好使？？？？
	  	$('#weekdaySpan').show();
	  	courseTableRecord = null;
	})
	$(function(){
		$('#courseTableForm').validatorInit(function(){
			courseTableTable.bootstrapTable('refresh');
			$('#courseTablekModal').modal('hide');
		});
		//$('#lessonNumber').chosen();
	});
</script>
