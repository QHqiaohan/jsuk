
if(top.location != self.location){
    top.location = "login";
}

layui.use('form',function(){
    var form = layui.form ,jq = layui.jquery;
    //监听提交
    form.on('submit(login)', function(data){
        var loading     = topLayer().load(2);
        var fromData    = data.field;
        var action      = data.form.action;
        ajax({
            url:action,
            data:fromData,
            success:function(rData){
                topLayer().close(loading);
                meMsg(rData.msg);
                if(rData.success){
                    location.href = 'home/index';
                }
            },error:function () {
                meAlert("登录失败")
                topLayer().close(loading);
            }
        });
        return false;
    });
});