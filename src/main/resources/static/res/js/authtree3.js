/*
* @Author: 94468
* @Date:   2018-03-16 18:24:47
* @Last Modified by:   94468
* @Last Modified time: 2018-03-23 17:03:38
*/
// 节点树
layui.define(['jquery', 'form'], function(exports){
    $ = layui.jquery;
    form = layui.form;

    obj = {
        // 渲染 + 绑定事件
        /**
         * 渲染DOM并绑定事件
         * @param  {[type]} dst       [目标ID，如：#test1]
         * @param  {[type]} trees     [数据，格式：{}]
         * @param  {[type]} inputname [上传表单名]
         * @return {[type]}           [description]
         */
        render: function(dst, trees, inputname,fun1){
            var inputname = inputname ? inputname : 'menuids[]';
            $(dst).html(obj.renderAuth(trees, 0, inputname));
            form.render();

            /*动态绑定展开事件*/
            $(dst).unbind('click').on('click', '.auth-icon', function(){
                var origin = $(this);
                var child = origin.parent().parent().find('.auth-child:first');
                if(origin.is('.active')){
                    origin.removeClass('active').html('');     /*收起*/
                    child.slideUp('fast');
                } else {
                    origin.addClass('active').html('');        /*展开*/
                    child.slideDown('fast');
                }
                return false;
            });


            $(dst).find("div.auth-status").on('click', function(){
                var origin = $(this);
               // fun1(origin);
                return true;
            })

        },
        // 递归创建格式
        renderAuth: function(tree, dept, inputname){
            var str = '<div class="auth-single">';
            layui.each(tree, function(index, item){
                var hasChild = item.list ? 1 : 0;
                var append = hasChild ? obj.renderAuth(item.list, dept+1, inputname) : '';
                str += '<div>' +
                    '<div class="auth-status" data-me=' + JSON.stringify(item) + '> '
                    +(hasChild?'<i class="layui-icon auth-icon" style="cursor:pointer;"></i>':'')
                    +(dept > 0 ? '<span>├─ </span>':'')
                    +'<div class="layui-unselect layui-form-checkbox" lay-skin="primary"><span>'+ item.name +'</span><i class="layui-icon"></i></div>' +
                    '</div> ' +
                    '<div class="auth-child" style="display:none;padding-left:20px;"> '+append+'</div></div>'
            });
            str += '</div>';
            return str;
        }
    }
    exports('authtree', obj);
});

