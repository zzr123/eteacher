/*	jQuery json2form Plugin 
 *	version: 1.0 (2011-03-01)
 *
 * 	Copyright (c) 2011, Crystal, shimingxy@163.com
 * 	Dual licensed under the MIT and GPL licenses:
 * 	http://www.opensource.org/licenses/mit-license.php
 * 	http://www.gnu.org/licenses/gpl.html
 * 	Date: 2011-03-01 rev 1
 */
 ;(function ($) {
$.json2form = $.json2form||{};
$.fn.json2form = function(config) { 
	var config=$.extend({
			url		:null,
			elem	:this.attr("id"),
			type	:'POST'
		}, config || {});

	if(config.url){
		$.ajax({type: config.type,url: config.url,data:$.extend({json2form:config.elem},config.data||{}),dataType: "json",async: false,
			success: function(data){
				config.data=data;
			}
		});
	}
	
	if(!$("#"+config.elem).attr("loadedInit")){//init checkbox radio and select element ,label
		if(config.data.init){
			for (var elem in config.data.init){
				var arrayData=config.data.init[elem];
				if($("#"+config.elem+" input[name='"+elem+"']")){
					var elemType=$("#"+config.elem+" input[name='"+elem+"']").attr("type");
					var elemName=$("#"+config.elem+" input[name='"+elem+"']").attr("name");
					var initElem=$("#"+config.elem+" input[name='"+elem+"']");
					switch(elemType){
						case "checkbox":
						case "radio":
							for (var initelem in arrayData){
								initElem.after('<input type="'+elemType+'"  name="'+elemName+'" value="'+arrayData[initelem].value+'" />'+arrayData[initelem].display);
							}
							initElem.remove();
							break;
					}
				} 
				if($("#"+config.elem+" select[name='"+elem+"']")){
					for (var initelem in arrayData){
						$("#"+config.elem+" select[name='"+elem+"']").append("<option value='"+arrayData[initelem].value+"'>"+arrayData[initelem].display+"</option>");
					}
				}
			}
		}
		if(config.data.label){//label
			$("#"+config.elem+" label").each(function(){
				var labelFor=$(this).attr("for");
				if(config.data.label[labelFor]){
					$(this).html(config.data.label[labelFor]);
				}
			});
		}
	}
	
	if(config.data){//input text password hidden button reset submit checkbox radio select textarea
		//span..  set the span's innerText
		$("#"+config.elem+" span").each(function(){
			var elemName=$(this).attr("name");
			if(!elemName){
				return true;
			}
			var elemData=config.data[elemName];
			$(this).empty();
			if(elemData||elemData==0){
				if(typeof(elemData)=="string"){
					if(elemData.toUpperCase()=="NULL"){
						$(this).text("");
					}
					else{
						elemData = elemData.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/ /g,'&nbsp;').replace(/\n/g,'<br />');
						$(this).html(elemData);
					}
				}else{
					$(this).text(elemData+"");
				}
			}
		});
		//----
		$("#"+config.elem+" input,select,textarea").each(function(){
			var elemType=$(this).attr("type")==undefined?this.type:$(this).attr("type");
			var elemName=$(this).attr("name");
			var elemData=config.data[elemName];
			if(!$("#"+config.elem).attr("loadedInit")&&$(this).attr("loadurl")){
				switch(elemType){
					case "checkbox":
					case "radio":
					case "select":
					case "select-one":
					case "select-multiple":{
						var _this =this;
						$.ajax({type: config.type,url: $(this).attr("loadurl"),dataType: "json",async: false,success: function(data){	
							if(elemType=="select"||elemType=="select-one"||elemType=="select-multiple"){
								$(_this).empty();
							}
							for (var elem in data){
									if(elemType=="select"||elemType=="select-one"||elemType=="select-multiple"){
										$(_this).append("<option value='"+data[elem].value+"'>"+data[elem].display+"</option>");
									}else{
										$(_this).after('<input type="'+elemType+'"  name="'+elemName+'" value="'+data[elem].value+'" />'+data[elem].display);
									}
								}
								if(elemType=="checkbox"||elemType=="radio")$(_this).remove();
							}
						});
						break;
					}
				}
			}
			
			if(elemData||elemData==0){
				switch(elemType){
					case undefined:
					case "text":
					case "password":
					case "hidden":
					case "button":
					case "reset":
					case "textarea":
					case "submit":{
						/** Null data from oracledb is changed into empty by, rem ed by gzw on 2014/09/15
						if(typeof(elemData)=="string"){
							$(this).val(elemData.toUpperCase()=="NULL"?"":elemData);
						}else{ } **/
						$(this).val(elemData+"");
						break;
					}
					case "checkbox":
					case "radio":{
						$(this).attr("checked",false);
						if(elemData.constructor==Array){//checkbox multiple value is Array
							for (var elem in elemData){
								if(elemData[elem]==$(this).val()){
									$(this).prop("checked",true);
								}
							}
						}else{//radio or checkbox is a string single value
							if(elemData==$(this).val()||(elemData+'')==$(this).val()){
								$(this).prop("checked",true);
							}
						}
						break;
					}
					case "select":
					case "select-one":
					case "select-multiple":{
						$(this).find("option:selected").attr("selected",false);
						if(elemData.constructor==Array){
							for (var elem in elemData){
								$(this).find("option[value='"+elemData[elem]+"']").prop("selected",true);
							}
						}else{
							$(this).val(elemData);
							$(this).find("option[value='"+elemData+"']").prop("selected",true);
						}
						break;
					}
				}
			}
		});
	}

	$("#"+config.elem).attr("loadedInit","true");//loadedInit is true,next invoke not need init checkbox radio and select element ,label
};
})(jQuery);

//form2json
(function($){  
    $.fn.serializeJsonObj=function(){  
        var serializeObj={};  
        var array=this.serializeArray();  
        var str=this.serialize();  
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    };  
})(jQuery);  