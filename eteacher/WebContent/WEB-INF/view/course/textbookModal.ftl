<div id="textbookModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="textbookModalTitle">教材</h4>
      </div>
      <form id="textbookForm" method="post">
      <div class="modal-body">
        	<input type="hidden" name="textbookId" />
            <div class="message-group">
                <div class="message-left">名称：</div>
                <div class="message-right">
                    <input id="textbookName" name="textbookName"  type="text" class="mess-control" value="" />
                    
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">作者：</div>
                <div class="message-right">
                    <input name="author"  type="text" class="mess-control" value="" />
                    
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">出版社：</div>
                <div class="message-right">
                    <input name="publisher"  type="text" class="mess-control" value="" />
                    
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">版次：</div>
                <div class="message-right">
                    <input name="edition"  type="text" class="mess-control" value="" />
                    
                </div>                   	
            </div>
            <div class="message-group">
                <div class="message-left">ISBN：</div>
                <div class="message-right">
                    <input name="isbn"  type="text" class="mess-control" value="" />
                    
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
<script>
	var textbookType = '01';//教材
	var textbook;
	var textbookOthers = [];
	var index;
	var srcBtn;
	$(function(){
		$('#textbookForm').validatorNormalInit(saveTextbook);
		$('#textbookName').validatorEmpty('名称');
	});
	//打开时初始化数据
	$('#textbookModal').on('show.bs.modal', function (e) {
		srcBtn = $(e.relatedTarget);
		textbookType = srcBtn.data('type');
	  	if(textbookType == '01'){
	  		$('#textbookModalTitle').text('教材');
	  		if(textbook){
	  			$('#textbookForm').json2form({data:textbook});
	  		}
	  	}
	  	else{
	  		$('#textbookModalTitle').text('教辅');
	  		index = $('#textbookOthersDiv > div').index(srcBtn.parent());
	  		if(index >= 0){//'请添加教辅..'没有用div包裹,index为-1
	  			$('#textbookForm').json2form({data:textbookOthers[index]});
	  		}
	  	}
	  	$('#textbookForm .input_error').removeClass('input_error');
	})
	//关闭后清空表单
	$('#textbookModal').on('hidden.bs.modal', function (e) {
	  	document.getElementById("textbookForm").reset();
	})
	function saveTextbook(){
		var textbookJsonObj = $('#textbookForm').serializeJsonObj();
		if(textbookType == '01'){
			textbook = textbookJsonObj;
			$('#textbookSpan').text(textbook.textbookName);
		} 
		else{
			if(index >= 0){//编辑
				textbookOthers[index] = textbookJsonObj;
				srcBtn.prev().text(textbookJsonObj.textbookName);
			}
			else{
				addTextbookOtherHtml(textbookJsonObj.textbookName);
				textbookOthers.push(textbookJsonObj);
			}
		}
		$('#textbookModal').modal('hide');
		return false;
	}
	function delTextBookOther(btn){
		var idx = $('#textbookOthersDiv > div').index($(btn).parent());
		textbookOthers.splice(idx,1);
		$(btn).parent().remove();
	}
	function addTextbookOtherHtml(textbookName){
		var html = '<div><span>'+textbookName+'</span> &nbsp;'
				+ '<i style="cursor: pointer;" data-toggle="modal" data-target="#textbookModal" '
				+ 'data-type="02" data-index="'+textbookOthers.length+'" title="编辑" class="glyphicon glyphicon-edit"></i> &nbsp;'
				+ '<i style="cursor: pointer;" onclick="delTextBookOther(this)" title="删除" class="glyphicon glyphicon-trash"></i>'
				+ '</div>';
		$('#textbookOthersDiv').append(html);
	}
</script>
