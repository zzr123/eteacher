<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="${context}/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${context}/js/jquery.min.js"></script>
<head>
	<script type="text/javascript">
		function selectData(id, value){
			$('#titleSel',parent.document).val(value);
			parent.$.modal.close();
		}
	</script>
</head>
<body style="background:#fff;">
	<div class="newtable">
    	<div class="tabCont">
    	<#list titleList as title> 
			<div class="onetab"><span onclick="selectData('${title.id}','${title.value}')" id="${title.id}">${title.value}</span><a class="delet-img" href="#" title="删除"></a></div>
		</#list>
        </div>
        <div class="tabAdd">
        	<input value="" class="tabinput" type="text" /><a class="tabAddbtn" href="#">增加</a>
        </div> 
  	</div>
</body>
</html>