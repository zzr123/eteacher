<div id="scoreModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">成绩</h4>
      </div>
      <form id="scoreForm" method="post" action="saveScore">
      <input type="hidden" id="courseId" name="courseId" value="${courseId?default("")}" />
      <input type="hidden" id="stuId" name="stuId" />
      <div class="modal-body">
        	<#list CourseScoreList as courseScore> 
            <div class="message-group">
                <div class="message-left">${courseScore.scoreName}：</div>
                <div class="message-right">
                    <input id="score_${courseScore.csId}" name="score_${courseScore.csId}" type="text" class="mess-control scoreInput" value="" />
                    
                </div>                   	
            </div>
            </#list>
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
	var record;
	$(function(){
		$('#scoreForm').validatorInit(callback);
		$('.scoreInput').validatorSZ2('分数');
		function callback(){
			$('#scoreModal').modal('hide');
			scoreTable.bootstrapTable('refresh');
		}
	});
	//打开时初始化数据
	$('#scoreModal').on('show.bs.modal', function (e) {
		$('#scoreForm').json2form({data:record});
		$('#stuId').val(record.stuId);
	  	$('#scoreForm .input_error').removeClass('input_error');
	})
	//关闭后清空表单
	$('#scoreModal').on('hidden.bs.modal', function (e) {
	  	document.getElementById("scoreForm").reset();
	})
</script>
