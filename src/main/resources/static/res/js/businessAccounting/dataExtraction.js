var pageParams = {};

var DesignItem = function(){
   return new Object({
       houseName:'',
       confDesignItemName:'',
       materials:[],
       workingHours:[],
       delWorkingHours:[],
       delMaterials:[]
    });
}
var currentItemId = null;
var ztree;

layui.use(['authtree','laytpl','form'], function () {
    var authtree    = layui.authtree;
    var laytpl      = layui.laytpl;
    var form        = layui.form;
    var $           = layui.jquery;

    var setting = {
        check:{
            enable: true
        },
        callback: {
            onClick: zTreeOnCheck,
        },
        data : {
            simpleData : {
                enable : true,
                idKey : "id",
                pIdKey : "dss_id",
                rootPId : -1
            },
            key : {
                url : "nourl"
            }
        }
    };
    $.get('businessAccounting/designProjects?degignSchemeId='+degignSchemeId, function(ret) {
        ztree = $.fn.zTree.init($("#designProject"), setting, ret.data);
        var treeObj = $.fn.zTree.getZTreeObj("designProject");
        var node = treeObj.getNodesByParam("is_check", "1");
        for(var i=0;i<node.length;i++){
            treeObj.checkNode(node[i],true);
        }
        treeObj.expandAll(true);
    });
    function zTreeOnCheck(event, treeId, treeNode) {
        var Id = treeNode.id;
        var dss_id = treeNode.dss_id;
        if(dss_id!=0){
            currentItemId = null;
            $("input[name='tempNumber']").val("");
            active.loadDetailInfo(treeNode);
        }
    };

    var active={
        page:1,     //[1:材料，2:工时]
        loadDetailInfo:function(data){

            if(!data.project){
                currentItemId = null;
                return;
            }else{
                currentItemId = data.id;
            }

            var pNode = data.getParentNode();
            if(!pageParams[data.id]){
                pageParams[data.id] = new DesignItem();     //空间
                pageParams[data.id].houseName = pNode.name;
                pageParams[data.id].confDesignItemName = data.name;
            }

            if(pageParams[currentItemId].materials.length == 0){ active.loadMaterials(currentItemId); }

            if(pageParams[currentItemId].workingHours.length == 0){ active.loadWorkingHours(currentItemId); }

            if(active.page == 1){
                active.materialView();
            }else{
                active.laborTimePrice();
            }

            //刷新头部表格头部信息
            $("label.currentHouseSpace").text(pNode.name + " -> " + data.name);

        },
        loadMaterials:function(dssId){
            ajax({
                url:'businessAccounting/materials?dssId=' + dssId,
                success:function(data){
                    pageParams[dssId].materials = data.data;
                    active.refreshMaterials(pageParams[dssId].materials);
                }
            });
        },
        loadWorkingHours:function(dssId){
            ajax({
                url:'businessAccounting/workingHours?dssId=' + dssId,
                success:function(data){
                    pageParams[dssId].workingHours = data.data;
                    active.refreshWorkingHours(pageParams[dssId].workingHours);
                }
            });
        },
        refreshMaterials:function(data){
            //1.刷新表头
            var titleView   = $('table#dataFetch').find("thead");
            var titleTpl    = materialsHeadTpl.innerHTML;
            laytpl(titleTpl).render([],function(html){
                titleView.children().remove();
                titleView.append(html);
            });

            var getTpl = materialsTpl.innerHTML,view = $('table#dataFetch');
            laytpl(getTpl).render(data, function(html){
                var $htmlNode = $(html);
                view.find("tbody").children().remove();
                view.find("tbody").html($htmlNode);
                active.bindChange($htmlNode);
                form.render();
                initSelect(form,$);
                active.bindClick();

            });
            if(data.length != 0){
                active.refreshMamount();
            }
        },
        refreshWorkingHours:function(data){
            //1.刷新表头
            var titleView   = $('table#dataFetch').find("thead");
            var titleTpl    = workingHoursHeadTpl.innerHTML;
            laytpl(titleTpl).render([],function(html){
                titleView.children().remove();
                titleView.append(html);
            });

            var getTpl = workingHoursTpl.innerHTML,view = $('table#dataFetch');

            laytpl(getTpl).render(data, function(html){
                var $htmlNode = $(html);
                view.find("tbody").children().remove();
                view.find("tbody").html($htmlNode);
                active.bindChange($htmlNode);
                form.render();
                initSelect(form,$);
                active.bindClick();
            });
            if(data.length != 0){
                active.refreshTamount();
            }
        },
        materialView:function(o){
            active.page = 1;
            $("a[data-type='laborTimePrice']").parent().show();
            if(o){
                o.parent().hide();
            }

            if(currentItemId){
                if(pageParams[currentItemId].materials.length == 0){
                    active.loadMaterials(currentItemId);
                }else{
                    active.refreshMaterials(pageParams[currentItemId].materials);
                }
            }else{
                active.refreshMaterials([]);
            }
            active.refreshMaterials(currentItemId ? pageParams[currentItemId].materials : []);
        },
        laborTimePrice:function(o){
            active.page = 2;
            $("a[data-type='materialView']").parent().show();
            if(o){
                o.parent().hide();
            }
            if(currentItemId){
                if(pageParams[currentItemId].workingHours.length == 0){
                    active.loadWorkingHours(currentItemId);
                }else{
                    active.refreshWorkingHours(pageParams[currentItemId].workingHours);
                }
            }else{
                active.refreshWorkingHours([]);
            }
        },
        changeMemoryData:function($element,value){
            var attribute    = $element.data('type');
            if(!value){
                value = $element.val();
            }
            var arrayType   = attribute.split(".");
            if(arrayType.length == 1){

            }else{
                if(arrayType[0].indexOf("[") > 0){
                    var index = arrayType[0].substring(arrayType[0].indexOf("[") + 1,arrayType[0].indexOf("]"));
                    var attrName = arrayType[0].substring(0,arrayType[0].indexOf("["));
                    pageParams[currentItemId][attrName][index][arrayType[1]] = value;
                }
            }

            if(active.page == 1){
                active.refreshMamount($element);
            }else{
                active.refreshTamount($element);
            }
            var materialsIsAll = 0;
            var materialsIsAllLength = pageParams[currentItemId].materials.length;
            var workingHoursIsAll = 0;
            var workingHoursIsAllLength = pageParams[currentItemId].workingHours.length;

            for(var i=0;i<materialsIsAllLength;i++){
                if(pageParams[currentItemId].materials[i].material_usage_quantities!=null
                    &&pageParams[currentItemId].materials[i].material_usage_quantities!=""){
                    materialsIsAll++;
                }
            }

            for(var i=0;i<workingHoursIsAllLength;i++){
                if(pageParams[currentItemId].workingHours[i].work_hour_quantities != null
                    && pageParams[currentItemId].workingHours[i].work_hour_quantities != ""
                    && pageParams[currentItemId].workingHours[i].work_price_quantities!=null
                    && pageParams[currentItemId].workingHours[i].work_price_quantities!=""){
                    workingHoursIsAll++;
                }
            }
            if(materialsIsAll == materialsIsAllLength && workingHoursIsAll == workingHoursIsAllLength){
                var treeObj = $.fn.zTree.getZTreeObj("designProject");
                var node = treeObj.getNodeByParam("id",currentItemId);
                treeObj.checkNode(node,true);

                /*var Id = node.id;
                var dss_id = node.dss_id;
                var is_check = 1;
                if(dss_id!=0){
                    ajaxNoLoad({
                        url:'businessAccounting/setCheck?id='+Id+'&is_check='+is_check,
                        success:function(data){
                            treeObj.checkNode(node,true);
                        }
                    });
                }*/
            }else{
                /*var treeObj = $.fn.zTree.getZTreeObj("designProject");
                var node = treeObj.getNodeByParam("id",currentItemId);
                var Id = node.id;
                var dss_id = node.dss_id;
                var is_check = 0;
                if(dss_id!=0){
                    ajaxNoLoad({
                        url:'businessAccounting/setCheck?id='+Id+'&is_check='+is_check,
                        success:function(data){
                            treeObj.checkNode(node,false);
                        }
                    });
                }*/
            }
        },
        bindChange:function (html){
            html.find("input").change(function(){
                active.changeMemoryData($(this));
            });
        },
        refreshMamount:function (o) {       //材料横向刷新金额
            if(o){
                var attribute    = o.data('type');
                var arrayType   = attribute.split(".");
                var index = arrayType[0].substring(arrayType[0].indexOf("[") + 1,arrayType[0].indexOf("]"));
                var attrName = arrayType[0].substring(0,arrayType[0].indexOf("["));
                if(arrayType[1] == "material_usage_quantities"){
                    pageParams[currentItemId][attrName][index]['material_usage'] = numberMultiplicationNumber(pageParams[currentItemId][attrName][index][arrayType[1]],pageParams[currentItemId][attrName][index]['standard_material_usage'])
                    pageParams[currentItemId][attrName][index]['material_sum'] = numberMultiplicationNumber(numberMultiplicationNumber(pageParams[currentItemId][attrName][index][arrayType[1]],pageParams[currentItemId][attrName][index]['standard_material_usage']),
                        pageParams[currentItemId][attrName][index]['material_price']);

                    o.closest("td").next().html(pageParams[currentItemId][attrName][index]['material_usage']);
                    o.closest("td").next().next().next().html(pageParams[currentItemId][attrName][index]['material_sum']);

                }

            }
            var materialSum  = 0.00;
            for(var i = 0; i < pageParams[currentItemId].materials.length; i++){
                var m = pageParams[currentItemId].materials[i];
                materialSum     = mumberAddNumber(materialSum,numberMultiplicationNumber(numberMultiplicationNumber(m.standard_material_usage , m.material_usage_quantities),m.material_price));
            }
            $("td.materialSum").text(materialSum);
        },
        refreshTamount:function(o){         //任务横向刷新金额
            if(o){
                var attribute    = o.data('type');
                var arrayType   = attribute.split(".");
                var index = arrayType[0].substring(arrayType[0].indexOf("[") + 1,arrayType[0].indexOf("]"));
                var attrName = arrayType[0].substring(0,arrayType[0].indexOf("["));
                if(arrayType[1] == "work_hour_quantities"){
                    pageParams[currentItemId][attrName][index]['work_hour_sum'] = numberMultiplicationNumber(pageParams[currentItemId][attrName][index][arrayType[1]],pageParams[currentItemId][attrName][index]['standard_work_hour']);
                    o.closest("td").next().html(pageParams[currentItemId][attrName][index]['work_hour_sum']);
                }else if(arrayType[1] == "work_price_quantities"){
                    pageParams[currentItemId][attrName][index]['work_price_sum']            = numberMultiplicationNumber(pageParams[currentItemId][attrName][index][arrayType[1]],pageParams[currentItemId][attrName][index]['standard_work_price']);
                    pageParams[currentItemId][attrName][index]['standard_inspection_sum']   = numberMultiplicationNumber(pageParams[currentItemId][attrName][index][arrayType[1]],pageParams[currentItemId][attrName][index]['standard_inspection']);
                    pageParams[currentItemId][attrName][index]['standard_subsidy_sum']      = numberMultiplicationNumber(pageParams[currentItemId][attrName][index][arrayType[1]],pageParams[currentItemId][attrName][index]['standard_subsidy']);

                    o.closest("td").next().html(pageParams[currentItemId][attrName][index]['work_price_sum']);                          //工价小计
                    o.closest("td").next().next().html(pageParams[currentItemId][attrName][index]['standard_inspection_sum']);          //验收费小计
                    o.closest("td").next().next().next().html(pageParams[currentItemId][attrName][index]['standard_subsidy_sum']);      //补贴小计
                }

            }
            var workPriceSum  = 0.00;
            var workHourSum   = 0.00;
            var inspectionFeeSum = 0.00;
            var subsidyPriceSum = 0.00;

            for(var i = 0; i < pageParams[currentItemId].workingHours.length; i++){
                var w = pageParams[currentItemId].workingHours[i];
                workHourSum         = mumberAddNumber(workHourSum,numberMultiplicationNumber(w.work_hour_quantities , w.standard_work_hour));
                workPriceSum        = mumberAddNumber(workPriceSum,numberMultiplicationNumber(w.work_price_quantities , w.standard_work_price));
                inspectionFeeSum    = mumberAddNumber(inspectionFeeSum,numberMultiplicationNumber(w.work_price_quantities , w.standard_inspection));
                subsidyPriceSum     = mumberAddNumber(subsidyPriceSum,numberMultiplicationNumber(w.work_price_quantities , w.standard_subsidy));
            }
            $("td.workHourSum").text(workHourSum);
            $("td.workPriceSum").text(workPriceSum);
            $("td.inspectionFeeSum").text(inspectionFeeSum);
            $("td.subsidyPriceSum").text(subsidyPriceSum);
        },
        addWorkingHours:function(){
            if(!currentItemId){
                meMsg("请选择施工项目");
                return;
            }
            LayerOpen({
                area:['90%','80%'],
                title:'添加任务',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:'confConstructionTask/selectList?level=2',
                btn:['确定','取消'],
                yes:function(index,layero){
                    var data = $(layero).find("iframe")[0].contentWindow.getSelectData("decorationTask");
                    if(data.length == 0){
                        meAlert("请选择施工节点");
                        return;
                    }

                    for(var i = 0; i < data.length; i++){
                        var temp = new workingHoursTmp();
                        temp.node_name  = data[i].name;
                        temp.task_name  = data[i].upNme;
                        temp.node_no    = data[i].task_no;
                        temp.dssItemId  = currentItemId;
                        pageParams[currentItemId].workingHours.push(temp);
                    }
                    active.refreshWorkingHours(pageParams[currentItemId].workingHours);
                    LayerClose(index);
                }
            });
        },                              //新增任务
        addMaterial:function(o){
            var node        = o.data("node");
            var nodeName    = o.data("nodename");
            var taskName    = o.data("taskname");

            LayerOpen({
                area:['90%','80%'],
                title:'材料列表',
                type: 2,
                closeBtn: 1,
                anim: 2,
                content:'material/selectList',
                btn:['确定','取消'],
                yes:function(index,layero){
                    var data = $(layero).find("iframe")[0].contentWindow.getSelectData("materialInfos");
                    if(data.length == 0){
                        meAlert("请选择材料");
                        return;
                    }

                    for(var i = 0; i < data.length; i++){
                        var temp = new materialsTmp();

                        temp.dssItemId      = currentItemId;
                        temp.node_no        = node;
                        temp.material_no    = data[i].material_no;
                        temp.node_name      = nodeName;
                        temp.task_name      = taskName;
                        temp.material_name  = data[i].name;
                        temp.specifications = data[i].specifications;
                        temp.material_price = data[i].cost_price;
                        temp.rmiUnit        = data[i].unit;

                        pageParams[currentItemId].materials.push(temp);
                    }

                    LayerClose(index);
                }
            });
        },                                 //新增材料
        bindClick:function(){
            $('.site-demo-active').unbind("click").on('click', function(){
                var othis = $(this), type = othis.data('type');
                active[type] ? active[type].call(this, othis) : '';
            });
        },
        delIndividuationItem:function(o){
            var $tr = $(o).closest("tr");
            var index = $tr.parent().children().index($tr);
            if(pageParams[currentItemId].workingHours[index].id != ""){
                pageParams[currentItemId].delWorkingHours.push(pageParams[currentItemId].workingHours[index].id);
            }
            pageParams[currentItemId].workingHours.splice(index,1);
            active.refreshWorkingHours(pageParams[currentItemId].workingHours);
        },                        //删除任务
        delMaterial:function(o){
            var $tr = $(o).closest("tr");
            var index = $tr.parent().children().index($tr);
            if(pageParams[currentItemId].materials[index].id != ""){
                pageParams[currentItemId].delMaterials.push(pageParams[currentItemId].materials[index].id);
            }
            pageParams[currentItemId].materials.splice(index,1);
            active.refreshMaterials(pageParams[currentItemId].materials);
        },                                 //删除材料
        inputTemp:function(){
            $("input[name='tempNumber']").change(function(){
                var textNumber = $(this).val();
                if(isNaN(textNumber)){
                   return false;
                }
                if(!currentItemId){
                    return false;
                }
                for(var i = 0; i < pageParams[currentItemId].materials.length; i++){
                    pageParams[currentItemId].materials[i].material_usage_quantities = textNumber;
                    pageParams[currentItemId].materials[i].material_usage = numberMultiplicationNumber(pageParams[currentItemId].materials[i].material_usage_quantities,pageParams[currentItemId].materials[i].standard_material_usage);
                    pageParams[currentItemId].materials[i].material_sum = numberMultiplicationNumber(pageParams[currentItemId].materials[i].material_usage,pageParams[currentItemId].materials[i].material_price);
                }

                for(var i = 0; i < pageParams[currentItemId].workingHours.length; i++){
                    var w = pageParams[currentItemId].workingHours[i];
                    pageParams[currentItemId].workingHours[i].work_hour_quantities = textNumber;
                    pageParams[currentItemId].workingHours[i].work_price_quantities = textNumber;
                    pageParams[currentItemId].workingHours[i].work_hour_sum = numberMultiplicationNumber(pageParams[currentItemId].workingHours[i].work_hour_quantities , w.standard_work_hour);
                    pageParams[currentItemId].workingHours[i].work_price_sum = numberMultiplicationNumber(pageParams[currentItemId].workingHours[i].work_price_quantities , w.standard_work_price);
                    pageParams[currentItemId].workingHours[i].standard_inspection_sum = numberMultiplicationNumber(pageParams[currentItemId].workingHours[i].work_price_quantities , w.standard_inspection);
                    pageParams[currentItemId].workingHours[i].standard_subsidy_sum = numberMultiplicationNumber(pageParams[currentItemId].workingHours[i].work_price_quantities , w.standard_subsidy);
                }

                if(active.page == 1){
                    active.materialView();
                }else{
                    active.laborTimePrice();
                }

                var treeObj = $.fn.zTree.getZTreeObj("designProject");
                var node = treeObj.getNodeByParam("id",currentItemId);
                var Id = node.id;
                ajaxNoLoad({
                    url:'businessAccounting/setCheck?id='+Id+'&is_check='+1,
                    success:function(data){
                        treeObj.checkNode(node,true);
                    }
                });

            });
        }

    }

    active.bindClick();
    active.inputTemp();

    form.on('select', function(data){
        active.changeMemoryData($(data.elem),data.value);
    });



});


function workingHoursTmp(){
    return new Object({
        task_name: '',
        standard_inspection:'',
        standardWorkHourUnit:'',
        node_name:'',
        standardWorkPriceUnit:'',
        work_price_quantities:'',
        remark:'',
        standard_subsidy:'',
        work_hour_quantities:'',
        standard_work_price:'',
        work_price_sum:'',
        standard_work_hour:'',
        pro_unit:'',
        work_hour_sum:'',
        id:'',
        data_sources:2
    });
}

function materialsTmp(){
    return new Object({
        task_name: '',
        material_usage: '',
        standard_material_usage:'',
        material_usage_quantities: '',
        node_name: '',
        remark: '',
        specifications: '',
        material_no: '',
        standardMaterialUsageUnit:'',
        material_price: '',
        material_sum: '',
        id: '',
        material_name:'',
        data_sources:2
    });
}
