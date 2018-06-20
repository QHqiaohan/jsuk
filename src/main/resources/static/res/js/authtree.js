/*
* @Author: 94468
* @Date:   2018-03-16 18:24:47
* @Last Modified by:   94468
* @Last Modified time: 2018-03-30 12:24:37
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
         * @param  {[type]} layfilter [lay-filter的值]
         * @return {[type]}           [description]
         */
        render: function(dst, trees, inputname, layfilter,fun1){
            var inputname = inputname ? inputname : 'menuids[]';
            var layfilter = layfilter ? layfilter : 'checkauth';
            var layfilter = layfilter ? layfilter : 'checkauth';
            $(dst).html(obj.renderAuth(trees, 0, inputname, layfilter));
            form.render();

            $(dst).find('.auth-single:first').unbind('click').on('click', '.layui-form-checkbox', function(){
                var elem = $(this).prev();
                var checked = elem.is(':checked');
                var childs = elem.parent().next().find('input[type="checkbox"]').prop('checked', checked);
                if(checked){
                    elem.parents('.auth-child').prev().find('input[type="checkbox"]').prop('checked', true);
                }
                form.render('checkbox');
            });

            /*动态绑定展开事件*/
            $(dst).unbind('click').on('click', '.auth-icon', function(){
                var origin = $(this);
                var child = origin.parent().parent().find('.auth-child:first');
                if(origin.is('.active')){
                    /*收起*/
                    origin.removeClass('active').html('');
                    child.slideUp('fast');
                } else {
                    /*展开*/
                    origin.addClass('active').html('');
                    child.slideDown('fast');
                }
                return false;
            })

            $(dst).find("div.auth-status").on('click', function(){
                var origin = $(this);
                if(fun1){
                    fun1(origin);
                }
                return true;
            })

        },
        // 递归创建格式
        renderAuth: function(tree, dept, inputname, layfilter){
            var str = '<div class="auth-single">';
            layui.each(tree, function(index, item){
                var hasChild = item.list ? 1 : 0;

                var append = hasChild ? obj.renderAuth(item.list, dept+1, inputname, layfilter) : '';

                str += '<div><div style="height: 30px" class="auth-status" data-me=' + JSON.stringify(item) + '> '+(hasChild?'<i class="layui-icon auth-icon" style="cursor:pointer;"></i>':'<i class="layui-icon auth-leaf" style="opacity:0;"></i>')+(dept > 0 ? '<span>├─ </span>':'')+'<input type="checkbox" name="'+inputname+'" title="'+item.name+'" value="'+item.value+'" lay-skin="primary" lay-filter="'+layfilter+'" '+(item.checked?'checked="checked"':'')+'> </div> <div class="auth-child" style="display:none;padding-left:20px;"> '+append+'</div></div>'
            });
            str += '</div>';
            return str;
        },

        // 获取选中叶子结点
        getLeaf: function(dst){
            var leafs = $(dst).find('.auth-leaf').parent().find('input[type="checkbox"]:checked');
            var data = [];
            layui.each(leafs, function(index, item){
                data.push(item.value);
            });
            return data;
        }
    }
    exports('authtree', obj);
});