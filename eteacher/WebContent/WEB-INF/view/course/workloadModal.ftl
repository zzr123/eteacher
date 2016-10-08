<div id="courseWorkloadModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">课程工作量</h4>
      </div>
      <form id="courseWorkloadForm" method="post" action="">
      <div class="modal-body" id="workloadDiv">
            
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
        <button type="button" class="btn btn-default" onclick="saveWorkload();">保 存</button>
      </div>
      </form> 
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
	var courseWorkloadArr = [{workloadName:'学时'},{workloadName:'学生数'}];
	initWorkload();
	//打开时初始化数据
	$('#courseWorkloadModal').on('show.bs.modal', function (e) {
		$.each(courseWorkloadArr,function(i,n){
			addWorkload(n.workloadName,n.cwId,n.workloadPercent);
		});
	})
	//关闭后清空表单
	$('#courseWorkloadModal').on('hidden.bs.modal', function (e) {
	  	$('#workloadDiv').html('');
	})
	$(function(){
		
	});
	function addWorkload(val,id,per){
		var value = val?val:'';
		var cwid = id?id:'';
		var percent = per?per:'';
		var html =  '<div class="message-group" style=" margin:0 auto; width:326px;">' + 
		            '     <input name="" class="mess-control workloadPercent" data-percent="'+percent+'" data-cwid="'+cwid+'" style="width:220px;float:left;" value="'+value+'" /> ' + 
		            '     <div id="toolbar" class="btn-group" style="float:left;">' + 
				    '	    <button type="button" class="btn btn-default" style="height:34px;" title="新增" onclick="addWorkload();">' + 
					'	        <i class="glyphicon glyphicon-plus"></i>' + 
					'	    </button>' + 
					'	    <button type="button" class="btn btn-default" style="height:34px;" title="删除" onclick="delWorkload(this);">' + 
					'	        <i class="glyphicon glyphicon-remove"></i>' + 
					'	    </button>' + 
					'	</div>' +                    	
		            '</div>';
		$('#workloadDiv').append(html);
	}
	function delWorkload(btn){
		if($('#workloadDiv').children().length > 1){
			$(btn).parent().parent().remove();
		}
		else{
			$(btn).parent().parent().children(':input').val('');
		}
	}
	function saveWorkload(){
		courseWorkloadArr = [];
		$('.workloadPercent').each(function(i,n){
			courseWorkloadArr.push({cwId:$(n).data('cwid'),workloadName:n.value,workloadPercent:$(n).data('percent')});
		});
		initWorkload();
		$('#courseWorkloadModal').modal('hide');
		return false;
	}
	function initWorkload(){
		$('#courseWorkloadDiv').children('span').remove();
		var html = '';
		$.each(courseWorkloadArr,function(i,n){
			var value = n.workloadPercent?n.workloadPercent:'';
			html += '<span>' + n.workloadName + '<input maxlength="3" type="text" class="mess-control workload" value="'+value+'" style="width:48px;" /> %&nbsp;&nbsp;&nbsp;&nbsp</span>';
		});
		$('#courseWorkloadDiv').prepend(html);
	}
</script>
