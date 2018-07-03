package com.jh.jsuk.utils.wx;

import com.alibaba.druid.util.StringUtils;
import com.jh.jsuk.utils.MSGUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by LUTAO on 2018/1/16.
 */
public class WxPay {
    /**
     * 微信统一下单接口路径
     */
    private static final String UNIFORMORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信退款路径
     */
    private static final String WXREFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 微信商户号
     */
    public static final String MCHID = "1489533372";
    /**
     * 微信交易类型
     */
    public static final String TRADETYPE = "APP";
    /**
     * 微信APIKEY
     */
    public static final String APIKEY = "8ff0ef743c7d71d47fb7496a1636278a";

    /**
     * 微信APPID
     */
    public static final String APPID = "wx7b306f3324f3ecd4";
    /**
     * 微信操作密码
     */
    public static final String caozuoPsd = "swycjj18@";
    /**
     * 微信操作密码
     */
    public static final String P12FILE = "/usr/local/wxR/apiclient_cert.p12";//服务器
    //public static final String P12FILE = "G:\\apiclient_cert.p12";//本地

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //uniformorder("211.149.159:9999/",0.1,"451561141541");
    }

    /**
     * 微信统一下单
     *
     * @return
     * @throws
     */
    public static MSGUtil uniformorder(HttpServletRequest request, String nofityUrl, Double money, String orderNum) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        //ResultObject result = new ResultObject();// 返回数据结果集合
        request.setCharacterEncoding("UTF-8");
        MSGUtil rt = new MSGUtil(MSGUtil.OK, "");
        try {
            Map<String, Object> rtMap = new HashMap<>();
            SortedMap<Object, Object> parame = new TreeMap<Object, Object>();
            parame.put("appid", APPID);

            parame.put("mch_id", MCHID);// 商家账号。
            String randomStr = getRandomString(18).toUpperCase();
            parame.put("nonce_str", randomStr);// 随机字符串
            parame.put("body", "妈妈烩功夫订单");// 商品描述
            parame.put("attach", "附加数据");
            parame.put("fee_type", "CNY");

            // TODO
            parame.put("out_trade_no", orderNum);// 商户订单编号
            //支付金额
            // TODO
            parame.put("total_fee", (new Double(money * 100)).intValue());// 消费金额
            //String ip = getIpAddr(request);
            // TODO
            String ip = "39.107.100.154";
            if (StringUtils.isEmpty(ip)) {
                parame.put("spbill_create_ip", "127.0.0.1");// 消费IP地址
            } else {
                parame.put("spbill_create_ip", ip);// 消费IP地址
            }
            parame.put("notify_url", nofityUrl);// 回调地址
            parame.put("trade_type", TRADETYPE);// 交易类型APP
            String sign = createSign(parame);
            parame.put("sign", sign);// 数字签证
            String xml = getRequestXML(parame);

            String content = HttpUtil.sendPost(UNIFORMORDER, xml);
            System.out.println(content);
            Map<String, String> renturn = readStringXmlOut(content);
            //JSONObject result_xml = JSONObject.parseObject(renturn.get("xml"));
            //JSONArray result_code =  result_xml.getJSONArray("result_code");
            String code = renturn.get("result_code");
            System.out.println("code : " + code);
            List<String> data = new ArrayList<String>();
            if (code.equalsIgnoreCase("FAIL")) {
                rt.setState(MSGUtil.ERRO);
                rt.setMsg("微信统一订单下单失败");
                rt.setData(data);
            } else if (code.equalsIgnoreCase("SUCCESS")) {
                String prepayId = renturn.get("prepay_id");
                String noncestr = renturn.get("nonce_str");
                String timestamp = System.currentTimeMillis() / 1000 + "";
                SortedMap<Object, Object> params = new TreeMap<Object, Object>();
                params.put("appid", APPID);
                params.put("partnerid", MCHID);
                params.put("prepayid", prepayId);
                params.put("package", "Sign=WXPay");
                params.put("noncestr", noncestr);
                params.put("timestamp", timestamp);
                String sign1 = createSign(params);
                params.put("sign", sign1);
                rt.setData(params);
                rt.setState(MSGUtil.OK);
                rt.setMsg("微信统一订单下单成功!");
            }
            return rt;
        } catch (Exception e) {
            e.printStackTrace();
            rt.setMsg(e.getMessage());
            rt.setState(MSGUtil.ERRO);
            return rt;
        }
    }

    /**
     * 微信退款
     */
    public static MSGUtil wxPayRefund(Double money, String orderNum) throws UnsupportedEncodingException {
        //request.setCharacterEncoding("UTF-8");
        MSGUtil rt = new MSGUtil(MSGUtil.OK, "");
        try {
            SortedMap<Object, Object> parame = new TreeMap<Object, Object>();
            parame.put("appid", "wx7b306f3324f3ecd4");
            parame.put("mch_id", "1489533372");// 商家账号。
            String randomStr = getRandomString(18).toUpperCase();
            parame.put("nonce_str", randomStr);// 随机字符串
            parame.put("fee_type", "CNY");
            // TODO
            parame.put("out_trade_no", orderNum);// 商户订单编号
            parame.put("out_refund_no", orderNum);// 商户退款编号
            //money=0.01;
            parame.put("total_fee", (new Double(money * 100)).intValue());// 消费金额
            parame.put("refund_fee", (new Double(money * 100)).intValue());// 消费金额
            parame.put("notify_url", "http:///39.107.100.154:8080/repair/sys/order/tuikuanOK");// 回调地址
            //parame.put("notify_url", "http://xcr.free.ngrok.cc/sys/order/tuikuanOK");// 本地测试地址
            String sign = createSign(parame);
            parame.put("sign", sign);// 数字签证
            String xml = getRequestXML(parame);
            String content = HttpUtil.sendWXPost(WXREFUND, xml);
            System.out.println(content);
            Map<String, String> renturn = readStringXmlOut(content);
            String code = renturn.get("return_code");
            System.out.println("code : " + code);
            List<String> data = new ArrayList<String>();
            if (code.equalsIgnoreCase("FAIL")) {
                rt.setState(MSGUtil.ERRO);
                rt.setMsg("微信退款失败");
                data.add(renturn.get("NOTENOUGH"));
                data.add(renturn.get("USER_ACCOUNT_ABNORMAL"));
                data.add(renturn.get("REQUIRE_POST_METHOD"));
                data.add(renturn.get("FREQUENCY_LIMITED "));
                rt.setData(data);
            } else if (code.equalsIgnoreCase("SUCCESS")) {
                // 退款单号
                String outRefundNo = renturn.get("out_refund_no");
                // 保存到数据库
                //Db.update("update `repair-order` set tuikuannum = ? where dd_id = ?", outRefundNo, orderNum);
                rt.setState(MSGUtil.OK);
                rt.setMsg("微信退款成功!");
            }
            return rt;
        } catch (Exception e) {
            e.printStackTrace();
            rt.setMsg(e.getMessage());
            rt.setState(MSGUtil.ERRO);
            return rt;
        }
    }

    public static Map<String, String> readStringXmlOut(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> list = rootElt.elements();//获取根节点下所有节点
            for (Element element : list) {  //遍历节点
                map.put(element.getName(), element.getText()); //节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 随机字符串生成
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //拼接xml 请求路径
    public static String getRequestXML(SortedMap<Object, Object> parame) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");
        Set set = parame.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = entry.getValue().toString();
            //过滤相关字段sign
            if ("sign".equalsIgnoreCase(key)) {
                buffer.append("<" + key + ">" + "<![CDATA[" + value + "]]>" + "</" + key + ">");
                //buffer.append("<"+key+">"+value+"</"+key+">");
            } else {
                buffer.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        buffer.append("</xml>");
        System.out.println(buffer.toString());
        return buffer.toString();
    }

    //创建md5 数字签证
    public static String createSign(SortedMap<Object, Object> parame) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuffer buffer = new StringBuffer();
        Set set = parame.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue() + "";
            if (null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {
                buffer.append(key + "=" + value + "&");
            }
        }
        buffer.append("key=" + "8ff0ef743c7d71d47fb7496a1636278a");
        System.out.println("签名参数after ：" + buffer.toString());
        String sign = MD5Util.getDigest(buffer.toString()).toUpperCase();
        System.out.println("签名参数：" + sign);

        return sign;

    }


}
