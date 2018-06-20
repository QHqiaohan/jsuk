var form = null;
var $ = null;
var houseSpaces = [];
var currentDsAmount = null;

layui.use(['form','laytpl','laydate'], function(){
    form = layui.form;$ = layui.jquery;
    var laytpl = layui.laytpl;
    var laydate = layui.laydate;
    currentDsAmount = new dsAmount();


    bindPageBtn();
    initSelect(form,$);

    var active = {

        addMaterial:function(){
            LayerOpen({
                area:['80%','80%'],
                title:'新增设计方案',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:'designScheme/designScheme',
                btn:['确定','取消'],
                yes:function(index,layero){
                    $(layero).find("iframe")[0].contentWindow.submitForm("materialQuality",function(data){
                        if(data.success){
                            LayerClose(index);
                            meAlert(data.msg);
                            initSelect(form,$);
                        }
                    })
                }
            });
        },
        addHouseSpace:function(){
            if($("select[name='houseSpaceType']").val() != -1){
                for(var i = 0; i < houseSpaces.length; i++){
                    houseSpaces[i].currentShow = false;
                }

                //创建当前模块
                var houseSpaceItem = new houseSpace();
                houseSpaceItem.houseTypeId      =  $("select[name='houseSpaceType']").val();
                houseSpaceItem.houseTypeName    = $("select[name='houseSpaceType']").find("option:selected").text();
                houseSpaceItem.currentShow      = true;
                houseSpaceItem.inPkg            = true;
                houseSpaceItem.inPageSum = numberRideNumber(currentDsAmount.standardUnitPrice,houseSpaceItem.area);

                //加载所有当前模块下面的套餐内项目
                ajax({
                    url:'confDesignItem/confDesignItemInPkgByPackageAndHouse',
                    data:{pkgId:currentDsAmount.pkgId,houseId:houseSpaceItem.houseTypeId},
                    success:function(data){
                        for(var i = 0; i < data.data.length; i++){
                            var individuationItem = new individuation();
                            individuationItem.dItemId           = data.data[i].id;
                            individuationItem.projectTypeName   = data.data[i].project_type_name;
                            individuationItem.projectTypeCode   = data.data[i].project_type_code;
                            individuationItem.dItemName         = data.data[i].name;
                            individuationItem.detailParams      = data.data[i].item_detail_param;
                            individuationItem.unit              = data.data[i].unit;
                            individuationItem.unitName          = data.data[i].unit_name;
                            individuationItem.price             = data.data[i].price;
                            individuationItem.house             = houseSpaceItem.houseTypeId;
                            individuationItem.designItemPrice   = data.data[i].price;
                            houseSpaceItem.individuationList.push(individuationItem);
                        }

                        houseSpaces.push(houseSpaceItem);
                        active.sumCost();
                        $("label[name='currentHouseSpace']").text(houseSpaceItem.houseTypeName);
                    }
                });
            }
        },
        refreshHouseSpaceItem:function(){                     //全部刷新
            var $table = $($("table.houseSpaces")[0]);
            if(houseSpaces.length == 0){
                $table.find("tbody").children().remove();
                return;
            }

            var getTpl = houseSpaceTpl.innerHTML;
            laytpl(getTpl).render(houseSpaces, function(html){
                $table.find("tbody").children().remove();
                var $htmlNode = $(html);
                $table.find("tbody").append($htmlNode);
                form.render();
                bindPageBtn();
                bindChange($htmlNode);
            });
        },
        delHouseSpace:function(o,j){
            var $tr = $(o).closest("tr");
            var index = $tr.parent().children().index($tr);
            houseSpaces.splice(index,1);
            active.sumCost();
            active.refreshHouseSpaceItem();
            var $table = $("table.designItem");
            $table.find("tbody").children().remove();
        },
        houstItemClick:function(o){
            var $tr = $(o);
            var $trs = $tr.parent().children();
            var index = $trs.index($tr);
            for(var i = 0; i < houseSpaces.length; i++){
                houseSpaces[i].currentShow = false;
            }
            houseSpaces[index].currentShow = true;
            for(var i = 0; i < $trs.length; i++){
                $($trs[i]).removeClass("selectTr");
            }
            $tr.addClass("selectTr");
            $("label[name='currentHouseSpace']").text(houseSpaces[index].houseTypeName);
            active.refreshHouseSpaceDesignItem();
        },
        addIndividuationItem:function(o){
            var webContent = "confDesignItem/individuationItemSelect?1=1";
            if(currentDsAmount.pkgId) {
                webContent += "&pkgId=" + currentDsAmount.pkgId;
            }
            LayerOpen({
                area:['80%','85%'],
                title:'选择个性化装修项目',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:webContent,
                btn:['确定','取消'],
                yes:function(index,layero){
                    var data = $(layero).find("iframe")[0].contentWindow.getSelectData("confDesignItem");
                    if(!data){
                        return;
                    }
                    var currentHouseSpace = null;
                    for(var i = 0; i < houseSpaces.length; i++){
                        if(houseSpaces[i].currentShow){
                            currentHouseSpace = houseSpaces[i];
                        }
                    }

                    for(var i = 0; i < data.length; i++){
                        var individuationItem = new individuation();
                        individuationItem.dItemId           = data[i].id;
                        individuationItem.projectTypeName   = '个性化项目';
                        individuationItem.projectTypeCode   = 0;
                        individuationItem.dItemName         = data[i].name;
                        individuationItem.detailParams      = data[i].item_detail_param;
                        individuationItem.unit              = data[i].unit;
                        individuationItem.unitName          = data[i].unit;
                        individuationItem.price             = data[i].price;
                        individuationItem.house             = currentHouseSpace.houseTypeId;
                        individuationItem.designItemPrice   = data[i].price;
                        currentHouseSpace.individuationList.push(individuationItem);
                    }
                    active.sumCost();
                    LayerClose(index);
                    active.refreshHouseSpaceDesignItem();
                }
            });
        },
        adDcustomItem:function(o){
            var webContent = "confDesignItem/dcustomItemSelect?1=1";
            if(currentDsAmount.pkgId) {
                webContent += "&pkgId=" + currentDsAmount.pkgId;
            }
            LayerOpen({
                area:['80%','85%'],
                title:'选择定制装修项目',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:webContent,
                btn:['确定','取消'],
                yes:function(index,layero){
                    var data = $(layero).find("iframe")[0].contentWindow.getSelectData("confDesignItem");
                    if(!data){
                        return;
                    }
                    var currentHouseSpace = null;
                    for(var i = 0; i < houseSpaces.length; i++){
                        if(houseSpaces[i].currentShow){
                            currentHouseSpace = houseSpaces[i];
                        }
                    }

                    for(var i = 0; i < data.length; i++){
                        var individuationItem = new individuation();
                        individuationItem.dItemId           = data[i].id;
                        individuationItem.projectTypeName   = '定制项目';
                        individuationItem.dItemName         = data[i].name;
                        individuationItem.detailParams      = data[i].item_detail_param;
                        individuationItem.unit              = data[i].unit;
                        individuationItem.unitName          = data[i].unit;
                        individuationItem.price             = data[i].price;
                        individuationItem.projectTypeCode   = 2;
                        individuationItem.house             = currentHouseSpace.houseTypeId;
                        individuationItem.designItemPrice   = data[i].price;

                        currentHouseSpace.individuationList.push(individuationItem);
                    }
                    active.sumCost();
                    LayerClose(index);
                    active.refreshHouseSpaceDesignItem();
                }
            });
        },
        refreshHouseSpaceDesignItem:function(o){

            var $table = $("table.designItem");
            var getTpl = individuationTpl.innerHTML;
            var currentHouseSpace = null;
            for(var i = 0; i < houseSpaces.length; i++){
                if(houseSpaces[i].currentShow){currentHouseSpace = houseSpaces[i]}
            }

            if(!currentHouseSpace || currentHouseSpace.length == 0){
                return;
            }


            $table.find("tbody").children().remove();

            var param = {
                inPkg:currentHouseSpace.inPkg,
                datas:currentHouseSpace ? currentHouseSpace.individuationList : [],
                house:currentHouseSpace.houseTypeId
            };
            laytpl(getTpl).render(param, function(html){
                var $htmlNode = $(html);
                $table.find("tbody").append($htmlNode);
                form.render();
                bindPageBtn();
                bindChange($htmlNode);
                active.initConfDesignItemSelect($htmlNode);
            });
        },
        delIndividuationItem:function(o){
            var $tr = $(o).closest("tr");
            var index = $tr.parent().children().index($tr);
            var currentHouseSpace = null;
            for(var i = 0; i < houseSpaces.length; i++){
                if(houseSpaces[i].currentShow){currentHouseSpace = houseSpaces[i]}
            }
            currentHouseSpace.individuationList.splice(index,1);
            active.sumCost();
            active.refreshHouseSpaceDesignItem();
        },
        selectPackgeStyle:function (o) {
            LayerOpen({
                area:['80%','70%'],
                title:'选择装修套餐-设计风格',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:'decorationStyle/selectList',
                btn:['确定','取消'],
                yes:function(index,layero){
                    var data = $(layero).find("iframe")[0].contentWindow.getSelectData("decorationStyle");
                    if(!data){
                        return;
                    }
                    currentDsAmount.standardUnitPrice = data[0].price;
                    $("input[name='pkg_name']").val(data[0].decorName);
                    $("input[name='dcrt_style_name']").val(data[0].decorationPackageName);
                    $("input[name='pkg_dcrt_style_id']").val(data[0].id);
                    $("div.showPackagePrice").text(currentDsAmount.standardUnitPrice);

                    currentDsAmount.pkgId = data[0].id;

                    active.sumCost();
                    LayerClose(index);
                }
            });
        },
        selectCustomer:function(o){
            LayerOpen({
                area:['60%','76%'],
                title:'选择客户',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:'designCustomer/selectList',
                btn:['确定','取消'],
                yes:function(index,layero){
                    var data = $(layero).find("iframe")[0].contentWindow.getSelectData("designCustomers");
                    if(!data){
                        return;
                    }

                    console.log(data);
                    $("input[name='customerNo']").val(data[0].customer_no);
                    $("div.customerName").text(data[0].real_name);
                    $("input[name='customerName']").val(data[0].real_name);

                    LayerClose(index);
                }
            });
        },
        initConfDesignItemSelect:function(html){
            var selects = html.find("select");
            selects.each(function(i){
                var $select = $(this);
                var value = $select.attr("lay-data");
                if(value){
                    var data = JSON.parse(value);
                    if(data){
                        $select.find("option").remove();
                        data.success = function(ret){
                            if(ret.success){
                                if(ret.data.length == 1){
                                    $select.closest("td").html(ret.data[0].item_detail_param);
                                }else{
                                    var $tr = $select.closest("tr");
                                    var index = $tr.parent().children().index($tr);
                                    var currentHouseSpace = null;
                                    for(var i = 0; i < houseSpaces.length; i++){
                                        if(houseSpaces[i].currentShow){currentHouseSpace = houseSpaces[i]}
                                    }

                                    //寻找是否存在套餐内项目
                                    var currentItemDefaultPrice = null;
                                    for(var i = 0; i < ret.data.length; i++){
                                        if(ret.data[i].project_code == 1){
                                            currentItemDefaultPrice = ret.data[i].price;
                                        }
                                    }

                                    if(currentItemDefaultPrice){
                                        for(var i = 0; i < ret.data.length; i++){
                                            var tep = ret.data[i].price;
                                            if(ret.data[i].price > currentItemDefaultPrice && currentHouseSpace.inPkg == 1){
                                                ret.data[i].price = numberReduceNumber(ret.data[i].price,currentItemDefaultPrice)
                                            }else if(ret.data[i].price < currentItemDefaultPrice && currentHouseSpace.inPkg == 1){
                                                ret.data[i].price = 0;
                                            }

                                            if(ret.data[i].project_code == 1){      //套餐内
                                                $select.append("<option data-designitem='"+ JSON.stringify(ret.data[i]) +"' value='" + ret.data[i].id + "' " + isChecked(ret.data[i].id,data.default) + ">" + ret.data[i].item_detail_param + "</option>");
                                            }else{
                                                $select.append("<option data-designitem='"+ JSON.stringify(ret.data[i]) +"' value='" + ret.data[i].id + "' " + isChecked(ret.data[i].id,data.default) + ">"
                                                    + ret.data[i].item_detail_param + ((tep > currentItemDefaultPrice) ? " +" + ret.data[i].price + "元" : "") +
                                                    "</option>");
                                            }
                                        }
                                    }else{
                                        for(var i = 0; i < ret.data.length; i++){
                                            $select.append("<option data-designitem='"+ JSON.stringify(ret.data[i]) +"' value='" + ret.data[i].id + "' " + isChecked(ret.data[i].id,data.default) + ">" + ret.data[i].item_detail_param + "</option>");
                                        }
                                    }
                                    form.render('select','confDesignItems');
                                    $select.siblings("div.layui-form-select").find('dl').find("dd[lay-value='"+ data.default +"']").click();
                                }
                            }
                        }
                        ajaxNoLoad(data);
                    }
                }
            });
            function isChecked(code,code2){
                if(code == code2){
                    return "selected=''";
                }else{ return ""; }
            }
        },
        confDesignItemSwitching:function(){
            form.on('select(confDesignItemName)', function(data){
                var selectData = $(data.elem).find(":selected").data('designitem');
                //对当前所选的装修项目进行修改
                var $tr = $(data.elem).closest("tr");
                var index = $tr.parent().children().index($tr);
                var currentHouseSpace = null;
                for(var i = 0; i < houseSpaces.length; i++){
                    if(houseSpaces[i].currentShow){currentHouseSpace = houseSpaces[i]}
                }

                currentHouseSpace.individuationList[index].price            = selectData.price;
                currentHouseSpace.individuationList[index].dItemId          = selectData.id;
                currentHouseSpace.individuationList[index].detailParams     = selectData.item_detail_param;
                currentHouseSpace.individuationList[index].projectTypeCode  = selectData.project_code;
                currentHouseSpace.individuationList[index].projectTypeName  = selectData.projectType;
                currentHouseSpace.individuationList[index].designItemPrice  = selectData.price;

                active.sumCost();

                //刷新自己页面
                if(currentHouseSpace.individuationList[index].projectTypeCode == 1 && currentHouseSpace.inPkg){
                    $tr.find("td:eq(1)").html(currentHouseSpace.individuationList[index].projectTypeName);
                    $tr.find("td:eq(5)").html("--");
                    $tr.find("td:eq(6)").html("--");
                    $tr.find("td:eq(7)").html("--");
                }else{
                    $tr.find("td:eq(1)").html(currentHouseSpace.individuationList[index].projectTypeName);
                    $tr.find("td:eq(5)").html(currentHouseSpace.individuationList[index].price);
                    $tr.find("td:eq(6)").html('<div class="layui-inline" style="width:50px">' +
                        '<input type="tel" name="quantities" placeholder="" data-type="individuationList['+ index +'].quantities" value="'+ currentHouseSpace.individuationList[index].quantities +'" autocomplete="off" class="layui-input">' +
                        '</div>');
                    $tr.find("td:eq(7)").html(showDouble(currentHouseSpace.individuationList[index].subtotal));

                    $tr.find("td:eq(6)").find("input").change(function(){
                        changeMemoryData($(this));
                    });

                }

                var meData = JSON.parse($(data.elem).attr("lay-data"));
                meData.default = currentHouseSpace.individuationList[index].dItemId;
                $(data.elem).attr("lay-data",JSON.stringify(meData));
                // active.initConfDesignItemSelect($tr);

            });
        },
        sumCost:function(){         //计算页面所有值
            //1.计算所有的项目金额

            currentDsAmount.outTotalAmount = 0;
            currentDsAmount.programmeTotalAmount = 0;

            for(var i = 0;i < houseSpaces.length; i++){
                houseSpaces[i].outPageSum = 0;                                                                                                                                  //清零
                for(var j = 0; j < houseSpaces[i].individuationList.length; j++){
                    houseSpaces[i].individuationList[j].subtotal = numberRideNumber(houseSpaces[i].individuationList[j].price,houseSpaces[i].individuationList[j].quantities);  //所有项目的小计
                    if(houseSpaces[i].inPkg && houseSpaces[i].individuationList[j].projectTypeCode == 1){

                    }else{
                        houseSpaces[i].outPageSum += houseSpaces[i].individuationList[j].subtotal;                                                                                  //空间的套外小计
                        currentDsAmount.outTotalAmount          += houseSpaces[i].individuationList[j].subtotal;
                    }
                }

                houseSpaces[i].inPageSum = numberRideNumber(currentDsAmount.standardUnitPrice,houseSpaces[i].area);                                                             //空间的套内小计

                //计算方案的金额
                currentDsAmount.programmeTotalAmount    = numberAddNumber(currentDsAmount.inTotalAmount,currentDsAmount.outTotalAmount);
            }
            currentDsAmount.inTotalAmount = numberMultiplicationNumber(currentDsAmount.houseArea,currentDsAmount.standardUnitPrice);

            //2.刷新页面所有金额
            $("div.standardUnitPrice").text(currentDsAmount.standardUnitPrice);
            $("div.inTotalAmount").text(currentDsAmount.inTotalAmount.toFixed(2));
            $("div.outTotalAmount").text(currentDsAmount.outTotalAmount.toFixed(2));
            $("div.programmeTotalAmount").text(currentDsAmount.programmeTotalAmount.toFixed(2));

            active.refreshHouseSpaceItem();
            // active.refreshHouseSpaceDesignItem();
        },
        showMap:function(){
            var addressNum = $('#addressNum').val()
            LayerOpen({
                area:['60%','70%'],
                title:'选择地址',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:'designScheme/showMap',
                btn:['确定','取消'],
                yes:function(index,layero){
                   var x_y = $(layero).find("iframe")[0].contentWindow.getLocalInfo();
                    $("input[name='y_locate']").val(x_y.longitude);
                    $("input[name='x_locate']").val(x_y.latitude);
                    LayerClose(index);
                },
                success:function(layero,index){
                    var x_y = $(layero).find("iframe")[0].contentWindow.getLocalInfo(addressNum);
                }
            });
        },
        upHouseArea:function (o) {
            var thisValue = o.val();
            if(isNaN(thisValue)){
                topLayer().alert("请输入正确房屋面积");
                return false;
            }
            currentDsAmount.houseArea = Number(thisValue);
            active.sumCost();
        }

    }

    //刷新页面数据
    if(dataHouseSpaces && dataHouseSpaces.length > 0){
        houseSpaces = dataHouseSpaces;
        active.refreshHouseSpaceItem();
    }

    function bindChange(html){
        // html.find("input[name='area']").on("input",function(){
        //     $(this).closest("tr").click();
        // });

        html.find("input").on("click",function(event){
            event.stopPropagation();
        })
        html.find("input").change(function(){
            changeMemoryData($(this));
        });
        html.find("textarea").change(function(){
            changeMemoryData($(this));
        });
    }

    function changeMemoryData($element,value){
        if($element && $element.attr("name") == "area"){
            $element.closest("tr").click();
        }

        //1.获取当前选中被选中的房屋面积
        var houseIndex = -1;
        for(var i = 0;i < houseSpaces.length; i++){
            if(houseSpaces[i].currentShow){
                houseIndex = i;
            }
        }

        var type    = $element.data('type');
        if(!houseSpaces || !type){
            return;
        }
        if(!value){
            value = $element.val();
        }

        var arrayType   = type.split(".");
        if(arrayType.length == 1){
            houseSpaces[houseIndex][arrayType[0]] = value;
        }else{
            if(arrayType[0].indexOf("[") > 0){
                var index = arrayType[0].substring(arrayType[0].indexOf("[") + 1,arrayType[0].indexOf("]"));
                var attrName = arrayType[0].substring(0,arrayType[0].indexOf("["));
                houseSpaces[houseIndex][attrName][index][arrayType[1]] = value;
            }else{
                houseSpaces[houseIndex][arrayType[0]][arrayType[1]] = value;
            }
        }

        if(arrayType[0] == "houseTypeName"){
            $("label[name='currentHouseSpace']").text(value);
        }

        if(arrayType[1] == "quantities"){       //需要计算小计
            var index = arrayType[0].substring(arrayType[0].indexOf("[") + 1,arrayType[0].indexOf("]"));
            $element.closest("tr").find("td:eq(7)").html(showDouble(numberRideNumber(houseSpaces[houseIndex].individuationList[index].price,houseSpaces[houseIndex].individuationList[index].quantities)));

            //刷新左边套餐外小计
            for(var i = 0; i < houseSpaces.length; i++){
                if(houseSpaces[i].currentShow){
                    for(var j = 0; j < houseSpaces[i].individuationList.length; j++){
                        if(houseSpaces[i].inPkg && houseSpaces[i].individuationList[j].projectTypeCode == 1){
                        }else{
                            houseSpaces[i].outPageSum += houseSpaces[i].individuationList[j].subtotal;                                                                                  //空间的套外小计
                        }
                    }
                    $("table.houseSpaces").find("tr:eq(" + i + ")").find("td:eq(4)").html(showDouble(houseSpaces[i].outPageSum));
                }
            }
        }

        if(Number(value) || Number(value) == 0){      //按钮有0的情况
            active.sumCost();
        }
    }

    function bindPageBtn(){
        $('.site-demo-active').unbind().on('click', function(){
            var othis = $(this), type = othis.data('type');
            active[type] ? active[type].call(this, othis) : '';
        });

        $(".site-house-input").unbind().on('blur',function(event){
            event.stopPropagation();
            var othis = $(this), type = othis.data('type');
            active[type] ? active[type].call(this, othis) : '';
        });

    }

    form.on('select(province)', function(data){
        var $city = $($("select[lay-filter='city']")[0]);
        $city.attr("lay-data",'{"url":"sysArea/location?parentId=' + data.value + '"}');
        initSelect2(form,$,$city,"city_div");

        var $county = $($("select[lay-filter='county']")[0]);
        $county.attr("lay-data",'{"url":"sysArea/location?parentId=' + -200 + '"}');
        initSelect2(form,$,$county,"county_div");
    });         //选择省份
    form.on('select(city)', function(data){
        var $county = $($("select[lay-filter='county']")[0]);
        $county.attr("lay-data",'{"url":"sysArea/location?parentId=' + data.value + '"}');
        initSelect2(form,$,$county,"county_div");
    });             //选择市

    form.on('switch(inPkg)', function(data){
        var currentTr = $(data.elem).closest("tr");
        var index  = currentTr.parent().children().index(currentTr);

        houseSpaces[index].inPkg = data.elem.checked;

        active.sumCost();
        active.refreshHouseSpaceDesignItem();
    });             //是否套餐内

    form.on('switch(startOnce)', function(data){
        if(!data.elem.checked){
            $(data.elem).val(1);
            $("#designSchemeStartTime").show();
        }else{
            $(data.elem).val(0);
            $("#designSchemeStartTime").hide();
        }
    });                                                     //是否立即开工

    function numberRideNumber(param1,param2){
        var number1 = Number(Number(param1).toFixed(2));
        var number2 = Number(Number(param2).toFixed(2));
        number1 = number1 ? number1 : 0;
        number2 = number2 ? number2 : 0;
        return number1  * number2;
    }

    active.confDesignItemSwitching();


    laydate.render({
        elem: '#designSchemeStartTime'
    });



    form.verify({
        startTime: function(value, item){ //value：表单的值、item：表单的DOM对象
            if($("input[name='start_once']").val() == 1 && !value){
                return "请输入开工时间";
            }
        }
    });
});




var dsAmount = function(){
    var obj = new Object({
        standardUnitPrice: currentDsAmountPrice ? Number(currentDsAmountPrice) : 0.00,
        inTotalAmount:inpageAmount ? Number(inpageAmount) : 0.00,
        outTotalAmount:0.00,
        programmeTotalAmount:0.00,
        houseArea:houseArea,
        pkgId:currentSelectPkg ? currentSelectPkg : -1
    });
    return obj;
}

var houseSpace = function(){
    var obj = new Object({
        id:'',
        hid:'',
        houseTypeId:'',
        houseTypeName:'',
        area:'',
        inPageSum:0.00,
        outPageSum:0.00,
        inPkg:true,
        individuationList:[],
        currentShow:true,
        space_no:''
    });
    return obj;
};

var individuation = function(){
    var obj = new Object({
        id:'',
        projectTypeName:'',       //项目类别
        dItemId:'',             //项目编号
        dItemName:'',           //项目名称
        detailParams:'',
        unit:'',
        unitName:'',
        price:'',
        quantities:'',
        subtotal:0.00,
        remark:'',
        projectTypeCode:'',
        house:'',
        designItemPrice:''
    });
    return obj
}

function numberAddNumber(param1,param2){
    return Number(Number(param1).toFixed(2)) + Number(Number(param2).toFixed(2));
}

function numberReduceNumber(param1,param2){
    return Number(Number(param1).toFixed(2)) - Number(Number(param2).toFixed(2));
}

