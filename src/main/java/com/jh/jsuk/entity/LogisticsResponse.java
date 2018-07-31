package com.jh.jsuk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
public class LogisticsResponse implements Serializable {

    private String message;

    private String nu;

    private String ischeck;

    private String condition;

    private String com;

    private String comName;

    private String status;

    private String state;

    private ArrayList<LogisticsResponse.LogisticsResponseData> data;

    @Setter
    @Getter
    @ToString
    public static class LogisticsResponseData {

        private String time;

        private String ftime;

        private String context;

        private String location;
    }

    private final static Map<String, String> coms;

    static {
        coms = new HashMap<>();
        coms.put("aae", "aae全球专递");
        coms.put("anjie", "安捷快递");
        coms.put("anxindakuaixi", "安信达快递");
        coms.put("biaojikuaidi", "彪记快递");
        coms.put("bht", "bht");
        coms.put("baifudongfang", "百福东方国际物流");
        coms.put("coe", "中国东方（COE）");
        coms.put("changyuwuliu", "长宇物流");
        coms.put("datianwuliu", "大田物流");
        coms.put("debangwuliu", "德邦物流");
        coms.put("dhl", "dhl");
        coms.put("dpex", "dpex");
        coms.put("dsukuaidi", "d速快递");
        coms.put("disifang", "递四方");
        coms.put("ems", "ems快递");
        coms.put("fedex", "fedex（国外）");
        coms.put("feikangda", "飞康达物流");
        coms.put("fenghuangkuaidi", "凤凰快递");
        coms.put("feikuaida", "飞快达");
        coms.put("guotongkuaidi", "国通快递");
        coms.put("ganzhongnengda", "港中能达物流");
        coms.put("guangdongyouzhengwuliu", "广东邮政物流");
        coms.put("gongsuda", "共速达");
        coms.put("huitongkuaidi", "汇通快运");
        coms.put("hengluwuliu", "恒路物流");
        coms.put("huaxialongwuliu", "华夏龙物流");
        coms.put("haihongwangsong", "海红");
        coms.put("haiwaihuanqiu", "海外环球");
        coms.put("jiayiwuliu", "佳怡物流");
        coms.put("jinguangsudikuaijian", "京广速递");
        coms.put("jixianda", "急先达");
        coms.put("jjwl", "佳吉物流");
        coms.put("jymwl", "加运美物流");
        coms.put("jindawuliu", "金大物流");
        coms.put("jialidatong", "嘉里大通");
        coms.put("jykd", "晋越快递");
        coms.put("kuaijiesudi", "快捷速递");
        coms.put("lianb", "联邦快递（国内）");
        coms.put("lianhaowuliu", "联昊通物流");
        coms.put("longbanwuliu", "龙邦物流");
        coms.put("lijisong", "立即送");
        coms.put("lejiedi", "乐捷递");
        coms.put("minghangkuaidi", "民航快递");
        coms.put("meiguokuaidi", "美国快递");
        coms.put("menduimen", "门对门");
        coms.put("ocs", "OCS");
        coms.put("peisihuoyunkuaidi", "配思货运");
        coms.put("quanchenkuaidi", "全晨快递");
        coms.put("quanfengkuaidi", "全峰快递");
        coms.put("quanjitong", "全际通物流");
        coms.put("quanritongkuaidi", "全日通快递");
        coms.put("quanyikuaidi", "全一快递");
        coms.put("rufengda", "如风达");
        coms.put("santaisudi", "三态速递");
        coms.put("shenghuiwuliu", "盛辉物流");
        coms.put("shentong", "申通");
        coms.put("shunfeng", "顺丰");
        coms.put("sue", "速尔物流");
        coms.put("shengfeng", "盛丰物流");
        coms.put("saiaodi", "赛澳递");
        coms.put("tiandihuayu", "天地华宇");
        coms.put("tiantian", "天天快递");
        coms.put("tnt", "tnt");
        coms.put("ups", "ups");
        coms.put("wanjiawuliu", "万家物流");
        coms.put("wenjiesudi", "文捷航空速递");
        coms.put("wuyuan", "伍圆");
        coms.put("wxwl", "万象物流");
        coms.put("xinbangwuliu", "新邦物流");
        coms.put("xinfengwuliu", "信丰物流");
        coms.put("yafengsudi", "亚风速递");
        coms.put("yibangwuliu", "一邦速递");
        coms.put("youshuwuliu", "优速物流");
        coms.put("youzhengguonei", "邮政包裹挂号信");
        coms.put("youzhengguoji", "邮政国际包裹挂号信");
        coms.put("yuanchengwuliu", "远成物流");
        coms.put("yuantong", "圆通速递");
        coms.put("yuanweifeng", "源伟丰快递");
        coms.put("yuanzhijiecheng", "元智捷诚快递");
        coms.put("yunda", "韵达快运");
        coms.put("yuntongkuaidi", "运通快递");
        coms.put("yuefengwuliu", "越丰物流");
        coms.put("yad", "源安达");
        coms.put("yinjiesudi", "银捷速递");
        coms.put("zhaijisong", "宅急送");
        coms.put("zhongtiekuaiyun", "中铁快运");
        coms.put("zhongtong", "中通速递");
        coms.put("zhongyouwuliu", "中邮物流");
        coms.put("zhongxinda", "忠信达");
        coms.put("zhimakaimen", "芝麻开门");
    }

    public void parseCom() {
        comName = coms.get(com);
    }

}
