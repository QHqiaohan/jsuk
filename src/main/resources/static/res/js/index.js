
layui.use(['element','layer'], function(){
    var tabs = {};
    var element = layui.element;
    var $  = layui.jquery;

    element.on('tab(tabViceWindow)',function(data){
        // console.log("选项卡被切换");
    });

    element.on('tabDelete(tabViceWindow)',function(data){
        delete tabs[$($(this).parent()[0]).attr("lay-id")];
    });

    element.on('nav(masterMenu)', function(elem){
        opMenu(elem.parent());
    });


    function opMenu(elem){
        var html = elem[0];
        var openId  = html.getAttribute("lay-data-mid");
        var htmlUrl = html.getAttribute("lay-data-murl");
        if(!htmlUrl){
            return;
        }
        if(tabs[openId]){

        }else{
            var  height = $('.layui-side').height() - $('#tabHeader').height() - 50 ;

            var iframe = "<iframe id='autoIframe' frameborder='0' width='100%' height='" + height + "'   src='"+ htmlUrl +"'></iframe>";
            // var iframe = "<iframe id='autoIframe' frameborder='0' width='100%' height='800'    src='"+ htmlUrl +"'></iframe>";

/*            console.log(
               // iframe
                $('.layui-side').height() - $('#tabHeader').height()
            )*/

            var tab = element.tabAdd("tabViceWindow",{
                title:html.innerText,
                content:iframe,
                id:html.getAttribute("lay-data-mid")
            });
            tabs[openId] = tab;
        }
        element.tabChange('tabViceWindow', openId);
    }

});









