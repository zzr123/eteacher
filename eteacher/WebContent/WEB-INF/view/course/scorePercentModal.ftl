<div id="courseScorekModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">成绩组成</h4>
      </div>
      <form id="courseScoreForm" method="post" action="addNormalScore">
      <div class="modal-body" id="scoreDiv">
            
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>
        <button type="button" class="btn btn-default" onclick="saveScore();">保 存</button>
      </div>
      </form> 
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
	var courseScoreArr = [{scoreName:'平时'},{scoreName:'期中'},{scoreName:'期末'}];
	//打开时初始化数据
	$('#courseScorekModal').on('show.bs.modal', function (e) {
		$.each(courseScoreArr,function(i,n){
			addScore(n.scoreName,n.csId,n.scorePercent);
		});
	})
	//关闭后清空表单
	$('#courseScorekModal').on('hidden.bs.modal', function (e) {
	  	$('#scoreDiv').html('');
	})
	$(function(){
		
	});
	function addScore(val,id,per){
		var value = val?val:'';
		var csid = id?id:'';
		var percent = per?per:'';
		var html =  '<div class="message-group" style=" margin:0 auto; width:326px;">' + 
		            '     <input name="" class="mess-control scorePercent" data-percent="'+percent+'" data-csid="'+csid+'" style="width:220px;float:left;" value="'+value+'" /> ' + 
		            '     <div id="toolbar" class="btn-group" style="float:left;">' + 
				    '	    <button type="button" class="btn btn-default" style="height:34px;" title="新增" onclick="addScore();">' + 
					'	        <i class="glyphicon glyphicon-plus"></i>' + 
					'	    </button>' + 
					'	    <button type="button" class="btn btn-default" style="height:34px;" title="删除" onclick="delScore(this);">' + 
					'	        <i class="glyphicon glyphicon-remove"></i>' + 
					'	    </button>' + 
					'	</div>' +                    	
		            '</div>';
		$('#scoreDiv').append(html);
	}
	function delScore(btn){
		if($('#scoreDiv').children().length > 1){
			$(btn).parent().parent().remove();
		}
		else{
			$(btn).parent().parent().children(':input').val('');
		}
	}
	function saveScore(){
		courseScoreArr = [];
		$('.scorePercent').each(function(i,n){
			courseScoreArr.push({csId:$(n).data('csid'),scoreName:n.value,scorePercent:$(n).data('percent')});
		});
		initScore();
		$('#courseScorekModal').modal('hide');
		return false;
	}
	function initScore(){
		$('#courseScoreDiv').children('span').remove();
		var html = '';
		$.each(courseScoreArr,function(i,n){
			var value = n.scorePercent?n.scorePercent:'';
			html += '<span>' + n.scoreName + '<input maxlength="3" type="text" class="mess-control percent" value="'+value+'" style="width:48px;" /> %&nbsp;&nbsp;&nbsp;&nbsp</span>';
		});
		$('#courseScoreDiv').prepend(html);
	}
</script>
