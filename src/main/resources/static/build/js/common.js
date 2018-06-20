/**
 * Created by Administrator on 2016/9/1.
 */


function dialog(opt){
    var tar = {
        showBtn: '',
        close: '.x-dialog-close',
        back: '.x-dialog-back',
        //dialog: '.x-dialog',
        clostime: 260,
        animate: 'bottom',
        backclose: false,
        callback: function(close,show){}
    };

    for(var p in opt){
        tar[p] = opt[p];
    }
    var back = $(tar.back);
    var dialog = back.find('.x-dialog');
    if(tar.dialog){
        dialog = $(tar.dialog);
    }
    var show = $(tar.showBtn);
    var close = back.find(tar.close);
    show.on('touchstart',function(){
        var _this = $(this);
        showdialog(_this);
    });
    close.on('touchstart',function(e){
        e.stopPropagation();
        e.preventDefault();
        closed(tar.clostime);
    });
    if(tar.backclose){
        back.on('touchstart',function(e){
            e.stopPropagation();
            e.preventDefault();
            if(e.target == $(this)[0]){
                closed();
            }
        })
    }
    function showdialog(ths){
        var _this = ths ? $(ths) : null ;
        setTimeout(function(){
            back.addClass('backshow').removeClass('backhide');
            dialog.addClass('dialog'+tar.animate+'show').removeClass('dialog'+tar.animate+'hide');
            tar.callback(closed,_this);
        },200);
        //$("body,html").css({"position":"fixed","left":0,"top":0,"width":"100%"});
    }
    function  closed(time){
        time = time ? time : 200;
        setTimeout(function(){
            dialog.addClass('dialog'+tar.animate+'hide').removeClass('dialog'+tar.animate+'show');
            setTimeout(function(){
                back.addClass('backhide').removeClass('backshow');
                tar.callback();
            },150)
        },time);
        //$("body,html").css({"position":"static"});
    }
    return {
        show: showdialog,
        close: closed
    };
}
function  tip(ms,time){
    if($('.x-dialog-tip').length){
        $('.x-dialog-tip').remove();
    }
    var dialog_tip = $('<div class="x-dialog-tip"></div>');
    var val = $('<span class="transition02">'+ms+'</span>');
    var tim = time ? time : 1500;
    dialog_tip.append(val);
    $('body').append(dialog_tip);
    setTimeout(function(){
        dialog_tip.addClass('on');
    },0);

    setTimeout(function(){
        dialog_tip.remove();
    },tim)
}
//倒计时
function daojishi(time,call){
    var hour = get_hour(time);
    var minute = get_minute(time);
    var second = get_second(time);

    var timer = setInterval(function(){
        if(second > 0){
            second--;
        }else{
            if(minute > 0){
                minute--;
                second = 59;
            }else{
                if(hour > 0){
                    hour--;
                    minute = second = 59;
                }else{
                    clearInterval(timer);
                    return;
                }
            }
        }
        call(hour,minute,second);
    },1000);

    function get_hour(alts){
        return Math.floor(alts / 60 / 60);
    }
    function get_minute(alts){
        return Math.floor(alts / 60);
    }
    function get_second(alts){
        var h = get_hour(alts);
        var m = get_minute(alts);

        return alts - h *3600 - m * 60;
    }
    //function get_hour(alts){
    //    return Math.ceil(alts / 60 / 60);
    //}
}
// 加减计数器
function jia_jian_computer(tars,call){
    var cal = call ? call : function(val){};
    var tar = $(tars);
    var allpri = 0, allnum = 0;

    tar.each(function(){
        var max = $(this).attr('data-max') - 0;
        var jia = $(this).find('.jia');
        var jian = $(this).find('.jian');
        var input = $(this).find('input');
        var price = $(this).attr('data-price') - 0;
        var _this = $(this);
        var sprev_val = 0;

        jia.on('click',function(e){
            e.stopPropagation();
            e.preventDefault();

            var val = input.val() - 0;
            val++;

            if(jian.hasClass('on')){
                jian.removeClass('on');
            }
            if(val >= max){
                val = max;
            }
            if(iscur(_this)){
                allpri += price;
                allnum += 1;
            }else {
                allpri += val * price;
                allnum += val;
            }

            input.val(val);
            cal(allnum,allpri);
        });
        jian.on('click',function(e){
            e.stopPropagation();
            e.preventDefault();
            var val = input.val() - 0;
            val--;
            if(!jian.hasClass('on')){
                if(iscur(_this)){
                    allpri -= price;
                    allnum -= 1;
                }else {
                    allpri -= val * price;
                    allnum -= val;
                }
            }

            if(val <= 0){
                val = 0;
                jian.addClass('on');
            }
            input.val(val);
            cal(allnum,allpri);
        });
        input.on('focus',function(){
            if(iscur(_this)){
                sprev_val = $(this).val() - 0;
            }else {
                sprev_val = 0;
            }

            })
            .on('input',function(){
                var val = input.val();
                if(/[.-]/g.test(val)){
                    val = val.replace(/[.-]/,'');
                }
                input.val(val);
            })
            .on('blur',function(){
                var val = input.val() - 0;
                if(val > max){
                    val = max;
                }else if(val <= 0){
                    val = 0;
                    if(!jian.hasClass('on')){
                        jian.addClass('on');
                    }
                }else if(val > 0){
                    if(jian.hasClass('on')){
                        jian.removeClass('on');
                    }
                }
                input.val(val);
                if(val > sprev_val){
                    if(iscur(_this)){
                        allpri += price * (val - sprev_val);
                        allnum += (val - sprev_val);
                    }else {
                        allpri += val * price;
                        allnum += val;
                    }
                }else {
                    if(iscur(_this)){
                        allpri -= price * (sprev_val - val);
                        allnum -= (val - sprev_val);
                    }else {
                        allpri -= val * price;
                        allnum -= val;
                    }
                }
                cal(allnum,allpri);
            })

    });

    function iscur(targ){

        if(targ.hasClass('on')){
            return true;
        }else {
            //tar.removeClass('on');
            targ.addClass('on');
            return false;
        }
    }

}
//------------登录注册的通用函数--------------
function login(opt){
    var tars = {
        time: 30,
        yanBtn: '.get_code',
        submitBtn: '.sub_btn',
        is_yan: true,
        reg_callback: regphone,
        yan_callback: function(){alert('这里执行验证码点击后的操作');},
        submit: function(){alert('这里执行验证后的操作');}
    };

    for(var p in opt){
        tars[p] = opt[p]
    }
    // 验证码倒计时
    code(tars.time,tars.yan_callback);
    function code(i,call){
        var is = i, time = null;
        var tar = $(tars.yanBtn);

        tar.on('click',function(e){
            if(!tars.reg_callback(tars.is_yan)){
                return;
            }
            var txt = $(this).text();
            var cell = arguments.callee;

            tar.addClass('on').off('click');
            tar.text(is + 's');
            call(recode);
            time = setInterval(function(){
                if(is <= 0){
                    recode(cell);
                    return;
                }
                is--;
                tar.text(is + 's');

            },1000);
            function recode(){
                clearInterval(time);
                is = i;
                tar.text(txt);
                tar.removeClass('on').on('click',cell);
            }
        })
    }
    // 输入值判断
    function regphone(){
        var phone = $('.reg_phone');
        var reg1 = /^1[35789]\d{9}/i;

        if(phone.val() == ''){
            tip('请输入手机号码');
            return false;
        }else if(!reg1.test(phone.val())){

            tip('手机号码格式错误');
            return false;
        }

        return true;
    }

    // 点击操作
    $(tars.submitBtn).on('touchend',function(){

        var cell = arguments.callee;
        if(!tars.reg_callback()){
            return false;
        }
        $(this).off();
        tars.submit($(this),cell);

    })
}
//-------------上传图片显示-----------
function upimg(opt){
    var tars = {
        one: false,
        input: null,
        size: '50kb',
        maxNum: null,
        type: 'image',
        isoriginal: false,
        callback: function(){}
    };

    for(var p in opt){
        tars[p] = opt[p]
    }

    var input = $(tars.input);
    var allfile = [],allsize = 0;

    input.on('change',function(e){
        var file = tars.one ? [this.files[0]] : Array.prototype.slice.call(this.files);

        allfile = allfile.concat(file);

        var imgarr = [];
        var _this = $(this);

        if(tars.maxNum && allfile.length > tars.maxNum){
            tip('超过最大上传数');
            return ;
        }

        for(var i = 0; i < file.length; i++){
            if(file[i].type.indexOf(tars.type) == -1){
                tip('上传文件不符合格式');
                return;
            }

            allsize += file[i].size;

            if(allsize > getsize(tars.size)){
                tip('上传文件超过大小');
                return;
            }

            !function(j){
                readfile(file[i],function(src){
                    if(tars.isoriginal){
                        imgarr.push(src)
                    }else {
                        imgarr.push(ceimg(src));
                    }

                    if( j == file.length - 1){
                        tars.callback(imgarr,_this);
                    }
                });
            }(i);
        }

    });
    function readfile(file,call){
        var fileReader = new FileReader();

        fileReader.readAsDataURL(file);
        fileReader.onload = function(){
            call(fileReader.result);
        }
    }
    function ceimg(src){
        return $(
            '<div class="imgClu">'+
            '<img src="'+src+'" alt="">'+
            +'</div>');
    }

    function getsize(t){
        var dan = ['b','kb','mb','gb'];
        var  sd = t.replace(parseInt(t),'');
        var size;

        for(var d = 0; d < dan.length; d++){
            if(sd.toLowerCase() == dan[d]){
                size = (parseInt(t) * Math.pow(1024, d));
                break;
            }
        }

        return size;
    }

}
//-------------日期选择插件-----------
(function($,win){
    function calendar(opt){
        var tar = {
            tdclick:function(td){}
        };
        for(var p in opt){
            tar[p] = opt[p];
        }
        this.init(tar);
    }
    calendar.prototype = {
        init: function(opt){
            this.date = new Date();
            this.year = this.date.getFullYear();
            this.month = this.date.getMonth();
            this.tdclick = opt.tdclick
        },
        monthinit:function(start){

            var setdate = new Date();
            var setyear = this.year;
            var setmonth = this.month;
            var cur_day_all = null;
            setdate.setFullYear(setyear);
            setdate.setMonth(setmonth+1);
            setdate.setDate(0);


            cur_day_all = setdate.getDate();

            if(start){
                cur_day_all = cur_day_all - start + 1;
            }
            var fillarr = [],c = 1;
            setdate.setMonth(this.month);
            if(start){
                setdate.setDate(start);
                c = start;
            }else {
                setdate.setDate(1);
            }

            var week = setdate.getDay(),cell =0;
            if((cur_day_all + week) % 7 == 0){
                cell = (cur_day_all + week) / 7;
            }else {
                cell = Math.floor((cur_day_all + week) / 7) + 1;
            }
            for(var i = 0; i < cell; i++){
                var arr = [];
                for(var j = 1; j < 8; j++){
                    if((i * 7 + j) < week + 1 || (i * 7 + j) > (cur_day_all + week)){
                        arr.push(null);
                    }else {
                        arr.push(c++);
                    }
                }
                fillarr.push(arr);
            }
            return fillarr;
        },
        createItem:function(fillarr){
            var table = $("<table></table>");
            var _this = this;
            for(var i = 0; i < fillarr.length; i++){
                var tr = $('<tr></tr>');
                for(var j = 0; j < 7; j++){
                    if(fillarr[i][j]){
                        var td = $('<td data-date="'+this.year+'-'+(this.month + 1)+'-'+fillarr[i][j]+'"><span>'+fillarr[i][j]+'</span></td>');
                        td.on('click',function(){
                            _this.tdclick($(this));
                        });
                    }else {
                        var td = $('<td class="empty">&nbsp;</td>');
                    }
                    tr.append(td);
                }
                table.append(tr);
            }

            return table;
        },
        xr:function(){
            var _this = this,start = null;

            var year = this.year, month = this.month;
            if(this.year == this.date.getFullYear() && this.month == this.date.getMonth()){
                start = this.date.getDate();
            }
            var montharr = this.monthinit(start);
            var table = this.createItem(montharr);

            this.month += 1;
            if(this.month == 12){
                this.year += 1;
                this.month = 0;
            }
            return {
                year: year,
                month: month,
                table: table
            }
        }
    };
    win.calendar = function(opt){
        return new calendar(opt);
    }
})(jQuery,window);