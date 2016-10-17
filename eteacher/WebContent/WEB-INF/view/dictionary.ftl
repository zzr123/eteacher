<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<head>
<script type="text/javascript">
	
	function selectData(id,value){
		$('#titleSel',parent.document).val(value);
		parent.$.modal.close();
	}
	
	//删除数据
	function delData(dtype,did){
		alert("1");
		$.post('../dictionary/viewDictionaryDel',{type:dtype,id:did},function(data){
			if(data){
				window.location.reload();
			}else{
				alert("发生错误，删除数据失败");
			} 
		});
		/*
    	$.ajax({
        	type: "POST",
         	url: "../dictionary/viewDictionaryDel",
         	data: {type:dtype,id:did},
            //dataType: "json",
            success: function(data){ //成功 
				alert( "删除成功，返回： " + data ); 
			},
            error: function(a,b,c){       //失败 
				alert(b); 
			}
			});
		*/
		}
		
		//增加数据
		function addDate(dtype){
			var dvalue = $('#inputeData').val();
			$.post('../dictionary/viewDictionaryAdd',{type:dtype,value:dvalue},function(data){
			if(data){
				window.location.reload();
			}else{
				alert("发生错误，增加数据失败");
			}
		});
		}
</script>
</head>
<body style="background:#fff;">

	<div class="newtable">
		<div class="tabAdd">
        	<input id="inputeData" value="" class="tabinput" type="text" /><a class="tabAddbtn" onclick="addDate(${type})">增加</a>
        </div>
    	<div class="tabCont" id="dataList">
    	<#list titleList as dictionarys> 
			<div class="onetab">
				<span onclick="selectData(${dictionarys['id']},${dictionarys['content']})">${dictionarys['content']}</span>
				<a class="delet-img" onclick="delData(${type},${dictionarys['id']})" href="#" title="删除"></a>
			</div>
		</#list>
        </div>
  	</div>

</body>
</html>