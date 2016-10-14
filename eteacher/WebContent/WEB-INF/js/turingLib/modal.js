(function($) {
	//创建弹出框html代码
	$(function(){
		var html = '<div id="turingModal" class="modal fade">' + 
		  '<div class="modal-dialog">' + 
		    '<div class="modal-content">' + 
		      '<div class="modal-header">' + 
		        '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>' + 
		        '<h4 id="turingModalTitle" class="modal-title">弹出框</h4>' + 
		      '</div>' + 
		      '<div class="modal-body">' + 
		            '<iframe src="" id="turingModalIframe" name="turingModalIframe" frameborder="0" width="100%"></iframe>' + 
		      '</div>' +
		      /*
		      '<div class="modal-footer">' + 
		        '<button type="button" class="btn btn-default" data-dismiss="modal">关 闭</button>' + 
		        '<button id="turingModalSaveBtn" type="button" class="btn btn-default">保 存</button>' + 
		      '</div>' +
		      */ 
		    '</div><!-- /.modal-content -->' + 
		  '</div><!-- /.modal-dialog -->' + 
		'</div><!-- /.modal -->';
		$('body').append(html);
		//关闭后清空表单
		$('#turingModal').on('hidden.bs.modal', function (e) {
			$('#turingModalTitle').text('弹出框');
		  	$('#turingModalIframe').attr('src','');
		})
	});
	//封装弹出框的调用
	$.modal = {
		open : function(param){
			if(param){
				if(param.title){
					$('#turingModalTitle').text(param.title);
				}
				if(param.url){
					$('#turingModalIframe').attr('src',param.url);
				}
			}
			/*
			$('#turingModalSaveBtn').click(function(){
				if($('#turingModalIframe')[0].contentWindow.save){
					if($('#turingModalIframe')[0].contentWindow.save()){
						$('#turingModal').modal('hide');
					}
				}
			});
			*/
			$('#turingModal').modal('show');
		},
		close : function(){
			$('#turingModal').modal('hide');
		}
	};
})(jQuery);