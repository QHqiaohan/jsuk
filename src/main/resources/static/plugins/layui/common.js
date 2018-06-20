
function submitForm(formId,fun,err,subData){
    form.on('submit('+ formId +')', function(data){
        var loadIndex = topLayer().load(2);
        var fromData    = subData ? subData : data.field;
        var action      = data.form.action;
        ajax({
            url:action,
            data:fromData,
            success:function(rData){
                fun ? fun(rData) : "";
                topLayer().close(loadIndex);
            },error:function () {
                err ? err(this) : "";
                topLayer().close(loadIndex);
            }
        });
        return false;
    });
    $("button[lay-filter='" + formId + "']").click();
}

function submitForm2(formId,fun,err,subData){
    form.on('submit('+ formId +')', function(data){
        var loadIndex = topLayer().load(2);
        var fromData    = data.field;
        fromData.params = subData;
        var action      = data.form.action;
        ajax({
            url:action,
            data:fromData,
            success:function(rData){
                fun ? fun(rData) : "";
                topLayer().close(loadIndex);
            },error:function () {
                err ? err(this) : "";
                topLayer().close(loadIndex);
            }
        });
        return false;
    });
    $("button[lay-filter='" + formId + "']").click();
}


function open(obj){
    return window.top.layui.layer.open(obj);
}

function close(index) {
    return window.top.layui.layer.close(index);
}

function meAlert(msg){
    return window.top.layui.layer.alert(msg);
}

function meMsg(msg){
    return window.top.layui.layer.msg(msg);
}

function topLayer(){
    return  window.top.layui.layer;
}

function ajax(obj){
    var loadIndex = topLayer().load(2);
    var $ = window.top.layui.jquery;
    $.ajax({
        url:obj.url,
        data:obj.data ? obj.data : {},
        dataType:obj.dataType ? obj.dataType : "json",
        type:obj.type ? obj.type : "POST",
        success:function(data){
            obj.success ? obj.success(data) : "";
            topLayer().close(loadIndex);
        },
        error:function(){
            topLayer().close(loadIndex);
            meAlert("操作失败");
            obj.error ? obj.error() : "";
        }
    });
}

function ajaxNoLoad(obj){
    var $ = window.top.layui.jquery;
    $.ajax({
        url:obj.url,
        data:obj.data ? obj.data : {},
        dataType:obj.dataType ? obj.dataType : "json",
        type:obj.type ? obj.type : "POST",
        success:obj.success,
        error:obj.error ? obj.error : function(){
            meAlert("操作失败");
        }
    });
}

function initSelect(form,$){
    if(form){
        $("select").each(function(i){
            var select = $(this);
            if(!select.hasClass("initOk")){
                select.addClass("initOk");
                var value = $(this).attr("lay-data");
                if(value){
                    var data = JSON.parse($(this).attr("lay-data"));
                    if(data){
                        $(select).find("option").not(":first").remove();
                        data.success = function(ret){
                            for(var i = 0; i < ret.data.length; i++){
                                select.append("<option value='" + ret.data[i].name + "' " + isChecked(ret.data[i].name,data.default) + ">" + ret.data[i].value + "</option>");
                            }
                            form.render('select');
                        }
                        ajaxNoLoad(data);
                    }
                }
            }
        });
    }
    function isChecked(code,code2){
        if(code == code2){
            return "selected=''";
        }
    }
}

function initSelect2(nowForm,$,$select,filter){
    if(nowForm){
        var value = $select.attr("lay-data");
        if(value){
            var data = JSON.parse(value);
            if(data){
                $select.find("option").not(":first").remove();
                data.success = function(ret){
                    for(var i = 0; i < ret.data.length; i++){
                        $select.append("<option value='" + ret.data[i].name + "' " + isChecked(ret.data[i].name,data.default) + ">" + ret.data[i].value + "</option>");
                    }
                    nowForm.render('select',filter);
                }
                ajaxNoLoad(data);
            }
        }
    }
    function isChecked(code,code2){
        if(code == code2){
            return "selected=''";
        }else{
            return '';
        }
    }
}

function getSelectData(tableId){
    var checkStatus = table.checkStatus(tableId);
    if(checkStatus.data.length < 1){
        meMsg("请选择一行数据");
        return false;
    }
    return checkStatus.data;
}


function showInteger(obj){
    var inte = Math.floor(obj);
    return inte == obj ? inte : obj;
}

function objToJson(obj){
    return JSON.stringify(obj);
}

function mumberAddNumber(param1,param2){
    return Number(Number(Number(param1).toFixed(2)) + Number(Number(param2).toFixed(2))).toFixed(2);
}

function numberMultiplicationNumber(param1,param2){
    return Number((Number(Number(param1).toFixed(2)) * Number(Number(param2).toFixed(2))).toFixed(2));
}

function number_format(number, decimals, dec_point, thousands_sep) {
    /*
    * 参数说明：
    * number：要格式化的数字
    * decimals：保留几位小数
    * dec_point：小数点符号
    * thousands_sep：千分位符号
    * */
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.ceil(n * k) / k;
        };

    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    var re = /(-?\d+)(\d{3})/;
    while (re.test(s[0])) {
        s[0] = s[0].replace(re, "$1" + sep + "$2");
    }

    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}


function showDouble(p){
    if(isNaN(p)){
        return p;
    }else{
        return Number(p).toFixed(2);
    }
}



