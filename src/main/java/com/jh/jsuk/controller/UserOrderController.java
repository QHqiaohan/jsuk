package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tj123.common.RedisUtils;
import com.jh.jsuk.conf.RedisKeys;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.dto.SubmitOrderDto;
import com.jh.jsuk.entity.rules.AccountRule;
import com.jh.jsuk.entity.vo.AfterSaleVo;
import com.jh.jsuk.entity.vo.UserOrderInfoVo;
import com.jh.jsuk.envm.OrderServiceStatus;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Slf4j
@Api(tags = "订单相关:")
@RestController
@RequestMapping("/userOrder")
public class UserOrderController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    Session session;

    @Autowired
    UserOrderService userOrderService;

    @Autowired
    UserOrderGoodsService userOrderGoodsService;
//
//    @Autowired
//    UserInvitationPayService userInvitationPayService;

    @Autowired
    DistributionUserService distributionUserService;

    @Autowired
    private RuleEngineService<AccountRule> ruleEngineService;

    @Autowired
    ShopService shopService;

    @Autowired
    UserService userService;

    @Autowired
    private ShopGoodsService shopGoodsService;

    @Autowired
    NewsService newsService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;

    @Autowired
    private ManagerUserService managerUserService;

    @Autowired
    private CouponService couponService;
    @Autowired
    private ShopSetService shopSetService;//获取是否使用积分；

    @GetMapping("/page")
    public R userOrderPage(Page page, String[] date, String kw, String status) throws Exception {
        OrderStatus orderStatus = null;
        if (status != null && !"all".equals(status)) {
            orderStatus = EnumUitl.toEnum(OrderStatus.class, status, "getShortKey");
        }
        Integer shopId = null;
        if (session.isShop()) {
            shopId = session.getShopId();
        }
        return R.succ(userOrderService.listPage(page, date == null ? null : Arrays.asList(date), kw, orderStatus, shopId));
    }

    @GetMapping("/get")
    public R orderDetail(Integer id) {
        return R.succ(userOrderService.selectById(id));
    }

    @PatchMapping
    public R orderDetail(UserOrder userOrder) {
        userOrder.setUserId(null);
        userOrderService.updateById(userOrder);
        return R.succ();
    }

    @ApiOperation(value = "用户端&商家端-订单详情/再次购买")
    @GetMapping("/detail")
    public R userOrderDetail(@RequestParam Integer orderId) {
        return R.succ(userOrderService.userOrderDetail(orderId));
    }

    @PostMapping("/close")
    public R userOrderClose(Integer orderId) {
        UserOrder order = new UserOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.CLOSED.getKey());
        order.updateById();
        return R.succ();
    }

    @PostMapping("/confirm")
    public R userOrderConfirm(Integer orderId) {
        UserOrder order = new UserOrder();
        order.setId(orderId);
        order.setStatus(OrderStatus.SUCCESS.getKey());
        order.updateById();
        return R.succ();
    }

    @GetMapping("/count")
    public R count() {
        Map<String, Object> map = new HashMap<>();
        OrderStatus[] statuses = OrderStatus.values();
        int all = 0;
        Integer shopId = null;
        if (session.isShop()) {
            shopId = session.getShopId();
        }
        for (OrderStatus status : statuses) {
            int cnt = userOrderService.statusCount(status, shopId);
            all += cnt;
            map.put(status.getShortKey(), cnt);
        }
        map.put("all", all);
        return R.succ(map);
    }

    //--------------------骑手端----------------------------------------------//

    /**
     * 待抢单
     */
//    @ApiOperation("骑手-显示待抢单列表")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "current", value = "当前页码",
//                    required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "size", value = "每页条数",
//                    required = false, paramType = "query", dataType = "integer"),
//            @ApiImplicitParam(name = "longitude", value = "经度",
//                    required = true, paramType = "query", dataType = "double"),
//            @ApiImplicitParam(name = "latitude", value = "纬度",
//                    required = true, paramType = "query", dataType = "double")
//    })
//    @GetMapping("/grabASingle")
//    public Result grabASingle(Page page, @RequestParam Double longitude, @RequestParam Double latitude) {
//        List<UserOrderVo> orderVoList = userOrderService.findVoByPage(page, new EntityWrapper()
//                .isNull(UserOrder.DISTRIBUTION_USER_ID)
//                .eq(UserOrder.STATUS, 2)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false)
//        );
//        if (orderVoList != null && orderVoList.size() > 0) {
//            for (UserOrderVo orderVo : orderVoList) {
//                orderVo.setDistance(longitude, latitude);
//            }
//            Collections.sort(orderVoList, new DistanceComparator());
//        }
//        return new Result().success(orderVoList);
//    }

    /**
     * 待抢单
     */
//    @ApiOperation("骑手-显示待抢单数量")
//    @GetMapping("/grabASingle/count")
//    public Result grabASingleCount() {
//        Wrapper wrapper = new EntityWrapper()
//                .isNull(UserOrder.DISTRIBUTION_USER_ID)
//                .eq(UserOrder.STATUS, 2)
//                .ne(UserOrder.IS_DEL, 4)
//                .ne(UserOrder.IS_DEL, 5)
//                .ne(UserOrder.IS_DEL, 6)
//                .ne(UserOrder.IS_DEL, 7)
//                .orderBy(UserOrder.CREAT_TIME, false);
//        return new Result().success(userOrderService.selectCount(wrapper));
//    }

//    @Autowired
//    RobbingOrderProducer producer;
    @ApiOperation(value = "商家端-删除订单")
    @PostMapping(value = "/deleteShopOrder")
    public Result deleteShopOrder(Integer orderId) {

        UserOrder uo = new UserOrder();
        UserOrder userOrder = uo.selectById(orderId);
        if (userOrder != null) {
            userOrder.setIsShopDel(1);
            userOrder.updateById();
            return new Result().success("删除成功");
        }
        return new Result().erro("数据异常");
    }
    @Autowired
    private UserIntegralService userIntegralService;
    @ApiOperation(value = "用户端-提交购物车计数价格")
    @PostMapping(value = "/Calculates")
    public Result Calculates(@RequestBody @Valid OrderList orderList,Integer userId){
        //查询用户的积分；
        //获取积分列表
        List<UserIntegral> userIntegrals = userIntegralService.selectList(new EntityWrapper<UserIntegral>()
            .eq(UserIntegral.USER_ID, userId));
        // 初始记录总积分数
        int sum = 0;
        for (UserIntegral integral : userIntegrals) {
            if (integral.getIntegralType() == 1) {
                sum += integral.getIntegralNumber();
                // 抵扣积分
            } else if (integral.getIntegralType() == -1) {
                sum -= integral.getIntegralNumber();
            }
        }
        //设置使用积分计数；
        Integer jifen = 0;
        //取店铺列表值
        ArrayList<OrderShops> shops = orderList.getShops();

        //获取总价格
        BigDecimal zongpraice = new BigDecimal(0);
        //循环的取店铺里的购买商品
        for(OrderShops os : shops){
            //获取商家的商品列表
            ArrayList<OrderShopGoods> shopGoods = os.getShopGoods();
            //计算店家的商品价格；
            //创建一个商家价格对象；
            BigDecimal shopPrice = new BigDecimal(0);
            //店铺商品数量
            Integer goodSum=0;
            //是否包邮
            ShopSets shopSet = shopSetService.getShopSetByShopId(os.getShopId());
            //商品的邮费
            BigDecimal you = new BigDecimal(0);
            for (OrderShopGoods osg :shopGoods){
                //获取商品信息
                ShopGoods sg1 = new ShopGoods();
                ShopGoods shopGoods1 = sg1.selectById(osg.getGoodId());
                if(shopGoods1!=null){
                    osg.setGoodName(shopGoods1.getGoodsName());//设置商品名称
                    osg.setGoodImg(shopGoods1.getMainImage());//设置商品图片
                }
                //根据规格id查询商品价格；
                Integer goodSizeId = osg.getGoodSizeId();
                ShopGoodsSize sgs = new ShopGoodsSize();
                ShopGoodsSize shopGoodsSize = sgs.selectById(goodSizeId);
                if(shopGoodsSize!=null){
                    //获取销售价格
                    String salesPrice = shopGoodsSize.getSalesPrice();
                    BigDecimal sales = new BigDecimal(salesPrice);//商品价格
                    BigDecimal sum1 = new BigDecimal(osg.getGoodSum());//商品数量
                    shopPrice=(sales.multiply(sum1)).add(shopPrice);
                    goodSum=goodSum+osg.getGoodSum();
                    String freight = shopGoodsSize.getFreight();//商品的邮费
                    you=you.add(new BigDecimal(freight));//邮费

                    osg.setGoodSizeName(shopGoodsSize.getSizeName());//设置商品规格名称
                }
            }
            //判断配送方式
            if(orderList.getDistributionType()==0){//快递
                //判断是否包邮
                if(shopSet==null){//如果为空添加一条数据
                    shopSet.setShopid(os.getShopId());
                    shopSet.setPackagemail(1);
                    shopSet.setIntegral(1);
                    shopSet.setMoney(0.0);
                    shopSet.insert();
                }else{
                    if(shopSet.getPackagemail()==2){
                        if(shopPrice.compareTo(new BigDecimal(shopSet.getMoney()))>=0){//判断支付价是否大于包邮
                            you=new BigDecimal(0);//设置邮费为0
                        }
                    }
                }
                os.setFreight(you); //设置快递费用
            }else if(orderList.getDistributionType()==1){//同城配送
                //计算同城邮费
                //根据店铺id查询店铺的经纬度；
                Shop sp = new Shop();
                Shop shop = sp.selectById(os.getShopId());
                Double shopwei=0.0;
                Double shopjing=0.0;
                if(shop!=null){
                   shopwei =   shop.getLatitude();//s商家维度
                   shopjing = shop.getLongitude();//商家经度
                }
                //获取用户地址的经纬度；
                UserAddress ua = new UserAddress();
                UserAddress uss = ua.selectById(orderList.getUserAddressId());
                Double userwei=0.0;
                Double userjing=0.0;
                if(uss!=null){
                   userwei=Double.parseDouble(uss.getLatitude());//用户维度
                   userjing= Double.parseDouble(uss.getLongitude());//用户经度
                }
                //获取计算规则
                RunningFee rf = new RunningFee();
                RunningFee runningFee = rf.selectById(1);
                Integer qiprace = 0;//起步费用
                Integer qiju = 0;//起步距离
                Integer chaofei =0;//超过费用
                if(runningFee!=null){
                    qiprace = runningFee.getStartFee();
                    qiju = runningFee.getStartDistance();
                    chaofei = runningFee.getAddFee();
                }
                //算邮费；
                double v = GetDistance(shopwei, shopjing, userwei, userjing);//距离
                DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
                String format = df.format(v);
                int i = Integer.parseInt(format);//距离
                if(i>qiju){
                    int i1 = (i - qiju) * chaofei + qiprace;
                    you = new BigDecimal(i1);
                }else{
                    you = new BigDecimal(qiprace);
                }
            }else{//到店自提
                you=new BigDecimal(0);
            }
            shopPrice=shopPrice.add(you);//原价加上邮费
            //设置满减值
            BigDecimal discount=new BigDecimal(0);
            //查询是否满减
            List<Coupon> lb = couponService.getListByShopId(os.getShopId());
            for(Coupon cu : lb){
                //获取满减值
                BigDecimal fullPrice = cu.getFullPrice();
                if(shopPrice.compareTo(fullPrice)>=0){
                    discount = cu.getDiscount();
                    break;
                }
            }
            shopPrice=shopPrice.subtract(discount);//满减后的价格；
            //获取会员折扣
            //获取用户会员级别
            os.setFullReduce(discount);//设置满减的值
            os.setFreight(you);//设置邮费
            os.setShopGoodSum(goodSum);//设置店铺商品数量
            os.setShopPrice(shopPrice);//设置店铺钱
            zongpraice = zongpraice.add(shopPrice);

            //获取店铺id
            Integer shopId = os.getShopId();
            //查询商家是否支持积分，包邮

            if(shopSet.getIntegral()==1){//不支持使用积分
            }else{
                //支持使用积分；
                jifen = shopPrice.intValue();//使用积分量
            }
        }
        User user = new User();
        User user1 = user.selectById(userId);
        BigDecimal zhe = new BigDecimal(1);//折扣
        if(user1!=null){
            Integer level = user1.getLevel();
            if(level!=0){
                Member m = new Member();
                Member mm = m.selectById(level);
                zhe = mm.getMemberDiscount();
                orderList.setMemberName(mm.getMemberName());//设置会员名称
            }
        }
        BigDecimal bigDecimal = (zongpraice.multiply(zhe)).setScale(2, BigDecimal.ROUND_HALF_UP);//折扣后总价

        //判断会员积分与交易金额的大小
        BigDecimal jif = new BigDecimal(0);//积分可抵换的钱
        //获取积分规则表；
        //获取积分规则列表
        IntegralRule ir = new IntegralRule();
        IntegralRule ie = ir.selectById(1);
        //设置可用积分量
        Integer ji = 0;
        if(ie!=null){
            Integer ii = ie.getIntegral();//多少积分
            BigDecimal mon = ie.getDeduction();//换多少
            //判断积分和钱
            if(jifen>sum){//订单积分大于用户积分
                ji = sum;
            }else{
                ji = jifen;
            }
            jif = ((new BigDecimal(ji)).multiply(mon)).divide(new BigDecimal(ii));//积分可换钱
        }
        orderList.setIntegralReduce(jif);//设置积分可抵扣的价格
        orderList.setIntegral(ji);//设置可用积分
        orderList.setMemberzZhe(zhe);//会员折扣
        orderList.setZongPrice(bigDecimal.subtract(jif));//设置总价折扣后总价

        return  new Result().success(orderList);
    }

    private static double EARTH_RADIUS = 6371.393;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
            Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }



    //--------------------骑手端----------------------------------------------//
    @ApiOperation(value = "用户端-提交订单")
    @PostMapping(value = "/submit")
    public Result submit(@RequestBody @Valid SubmitOrderDto orderDto, BindingResult result, Integer userId) throws Exception {
        Result res = new Result();
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        String key = RedisKeys.subKey(RedisKeys.PREVENT_RE_SUBMIT, session.userUid());
        if (redisUtils.hasKey(key)) {
            return res.erro("请勿重复提交");
        }
        redisUtils.setStr(key, "submit", 10);
        return res.success(userOrderService.submit(orderDto, userId));
    }

    @ApiOperation(value = "用户端-订单列表&订单关键字模糊搜索", notes = "不传=该用户全部订单")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "status", value = "0待付款,1待发货,2=待收货 3=售后,4=退款,5=退货,6=拒绝,7=取消",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "goodsName", value = "商品名称", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getOrderByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOrderByUserId(Page page, Integer status, String goodsName) throws Exception {
        MyEntityWrapper<UserOrderInfoVo> ew = new MyEntityWrapper<>();
        Page orderPage = userOrderService.getOrderByUserId(page, ew, session.lUserId(), status, goodsName);
        return new Result().success(orderPage);
    }

    /*@ApiOperation(value = "用户端&商家端-订单详情")
    @RequestMapping(value = "/getOrderInfoById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOrderInfoById(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
                .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().erro("没有该订单");
        }
        // 封装结果map
        Map<String, Object> map = MapUtil.newHashMap();
        // 收货地址ID
        Integer addressId = userOrder.getAddressId();
        // 收货地址
        UserAddress userAddress = userAddressService.selectOne(new EntityWrapper<UserAddress>()
                .eq(UserAddress.ID, addressId)
                .eq(UserAddress.IS_DEL, 0));
        UserOrderDetailVo userOrderDetailVo = new UserOrderDetailVo();
        return new Result().success(userOrderDetailVo);
    }*/

    @ApiOperation(value = "商家端-订单列表", notes = "不传=全部订单")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "status", value = "0待付款,1待发货,3=完成,7=售后",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "goodsName", value = "商品名称", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getShopOrderByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopOrderByUserId(Page page, Integer status, String goodsName) throws Exception {
//        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
//            .eq(ManagerUser.ID, userId));
//        if (managerUser == null)
//            return R.err("用户不存在");
//        Integer shopId = managerUser.getShopId();
//        MyEntityWrapper<UserOrderInfoVo> ew = new MyEntityWrapper<>();

        Page orderPage = userOrderService.getShopOrderByUserId(page, null, session.confirmShopId(), status, goodsName);

        return new Result().success(orderPage);
    }

    @ApiOperation(value = "用户端&商家端-取消订单")
    @RequestMapping(value = "/cancelOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result cancelOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        userOrder.setStatus(OrderStatus.CANCEL.getKey());
        userOrder.updateById();
        return new Result().success("取消成功!");
    }

    @ApiOperation(value = "获取物流类型")
    @RequestMapping(value = "/getLogisticstype", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getLogisticstype() {
        List<LogisticsType> li = new ArrayList<LogisticsType>();
        LogisticsType lt = new LogisticsType();
        LogisticsType lt55 = new LogisticsType();
        lt55.setName("申通");
        lt55.setPingying("shentong");
        li.add(lt55);
        LogisticsType lt56 = new LogisticsType();
        lt56.setName("顺丰");
        lt56.setPingying("shunfeng");
        li.add(lt56);
        LogisticsType lt19 = new LogisticsType();
        lt19.setName("国通快递");
        lt19.setPingying("guotongkuaidi");
        li.add(lt19);
        LogisticsType lt60 = new LogisticsType();
        lt60.setName("天地华宇");
        lt60.setPingying("tiandihuayu");
        li.add(lt60);
        LogisticsType lt61 = new LogisticsType();
        lt61.setName("天天快递");
        lt61.setPingying("tiantian");
        li.add(lt61);
        LogisticsType lt40 = new LogisticsType();
        lt40.setName("立即送");
        lt40.setPingying("lijisong");
        li.add(lt40);
        lt.setName("aae全球专递");
        lt.setPingying("aae");
        li.add(lt);
        LogisticsType lt1 = new LogisticsType();
        lt1.setName("安捷快递");
        lt1.setPingying("anjie");
        li.add(lt1);
        LogisticsType lt2 = new LogisticsType();
        lt2.setName("安信达快递");
        lt2.setPingying("anxindakuaixi");
        li.add(lt2);
        LogisticsType lt3 = new LogisticsType();
        lt3.setName("彪记快递");
        lt3.setPingying("biaojikuaidi");
        li.add(lt3);
        LogisticsType lt4 = new LogisticsType();
        lt4.setName("bht");
        lt4.setPingying("bht");
        li.add(lt4);
        LogisticsType lt5 = new LogisticsType();
        lt5.setName("百福东方国际物流");
        lt5.setPingying("baifudongfang");
        li.add(lt5);
        LogisticsType lt6 = new LogisticsType();
        lt6.setName("coe");
        lt6.setPingying("中国东方（COE）");
        li.add(lt6);
        LogisticsType lt7 = new LogisticsType();
        lt7.setName("长宇物流");
        lt7.setPingying("changyuwuliu");
        li.add(lt7);
        LogisticsType lt8 = new LogisticsType();
        lt8.setName("datianwuliu");
        lt8.setPingying("大田物流");
        li.add(lt8);
        LogisticsType lt9 = new LogisticsType();
        lt9.setName("德邦物流");
        lt9.setPingying("debangwuliu");
        li.add(lt9);
        LogisticsType lt10 = new LogisticsType();
        lt10.setName("dhl");
        lt10.setPingying("dhl");
        li.add(lt10);
        LogisticsType lt11 = new LogisticsType();
        lt11.setName("dpex");
        lt11.setPingying("dpex");
        li.add(lt11);
        LogisticsType lt12 = new LogisticsType();
        lt12.setName("d速快递");
        lt12.setPingying("dsukuaidi");
        li.add(lt12);
        LogisticsType lt13 = new LogisticsType();
        lt13.setName("递四方");
        lt13.setPingying("disifang");
        li.add(lt13);
        LogisticsType lt14 = new LogisticsType();
        lt14.setName("ems快递");
        lt14.setPingying("ems");
        li.add(lt14);
        LogisticsType lt15 = new LogisticsType();
        lt15.setName("fedex（国外）");
        lt15.setPingying("fedex");
        li.add(lt15);
        LogisticsType lt16 = new LogisticsType();
        lt16.setName("飞康达物流");
        lt16.setPingying("feikangda");
        li.add(lt16);
        LogisticsType lt17 = new LogisticsType();
        lt17.setName("凤凰快递");
        lt17.setPingying("fenghuangkuaidi");
        li.add(lt17);
        LogisticsType lt18 = new LogisticsType();
        lt18.setName("飞快达");
        lt18.setPingying("feikuaida");
        li.add(lt18);

        LogisticsType lt20 = new LogisticsType();
        lt20.setName("港中能达物流");
        lt20.setPingying("ganzhongnengda");
        li.add(lt20);
        LogisticsType lt21 = new LogisticsType();
        lt21.setName("广东邮政物流");
        lt21.setPingying("guangdongyouzhengwuliu");
        li.add(lt21);
        LogisticsType lt22 = new LogisticsType();
        lt22.setName("共速达");
        lt22.setPingying("gongsuda");
        li.add(lt22);
        LogisticsType lt23 = new LogisticsType();
        lt23.setName("汇通快运");
        lt23.setPingying("huitongkuaidi");
        li.add(lt23);
        LogisticsType lt24 = new LogisticsType();
        lt24.setName("恒路物流");
        lt24.setPingying("hengluwuliu");
        li.add(lt24);
        LogisticsType lt25 = new LogisticsType();
        lt25.setName("华夏龙物流");
        lt25.setPingying("huaxialongwuliu");
        li.add(lt25);
        LogisticsType lt26 = new LogisticsType();
        lt26.setName("海红");
        lt26.setPingying("haihongwangsong");
        li.add(lt26);
        LogisticsType lt27 = new LogisticsType();
        lt27.setName("海外环球");
        lt27.setPingying("haiwaihuanqiu");
        li.add(lt27);
        LogisticsType lt28 = new LogisticsType();
        lt28.setName("佳怡物流");
        lt28.setPingying("jiayiwuliu");
        li.add(lt28);
        LogisticsType lt29 = new LogisticsType();
        lt29.setName("京广速递");
        lt29.setPingying("jinguangsudikuaijian");
        li.add(lt29);
        LogisticsType lt30 = new LogisticsType();
        lt30.setName("急先达");
        lt30.setPingying("jixianda");
        li.add(lt30);
        LogisticsType lt31 = new LogisticsType();
        lt31.setName("佳吉物流");
        lt31.setPingying("jjwl");
        li.add(lt31);
        LogisticsType lt32 = new LogisticsType();
        lt32.setName("加运美物流");
        lt32.setPingying("jymwl");
        li.add(lt32);
        LogisticsType lt33 = new LogisticsType();
        lt33.setName("金大物流");
        lt33.setPingying("jindawuliu");
        li.add(lt33);
        LogisticsType lt34 = new LogisticsType();
        lt34.setName("嘉里大通");
        lt34.setPingying("jialidatong");
        li.add(lt34);
        LogisticsType lt35 = new LogisticsType();
        lt35.setName("晋越快递");
        lt35.setPingying("jykd");
        li.add(lt35);
        LogisticsType lt36 = new LogisticsType();
        lt36.setName("快捷速递");
        lt36.setPingying("kuaijiesudi");
        li.add(lt36);
        LogisticsType lt37 = new LogisticsType();
        lt37.setName("联邦快递（国内）");
        lt37.setPingying("lianb");
        li.add(lt37);
        LogisticsType lt38 = new LogisticsType();
        lt38.setName("联昊通物流");
        lt38.setPingying("lianhaowuliu");
        li.add(lt38);
        LogisticsType lt39 = new LogisticsType();
        lt39.setName("龙邦物流");
        lt39.setPingying("longbanwuliu");
        li.add(lt39);

        LogisticsType lt41 = new LogisticsType();
        lt41.setName("乐捷递");
        lt41.setPingying("lejiedi");
        li.add(lt41);
        LogisticsType lt42 = new LogisticsType();
        lt42.setName("民航快递");
        lt42.setPingying("minghangkuaidi");
        li.add(lt42);
        LogisticsType lt43 = new LogisticsType();
        lt43.setName("美国快递");
        lt43.setPingying("meiguokuaidi");
        li.add(lt43);
        LogisticsType lt44 = new LogisticsType();
        lt44.setName("门对门");
        lt44.setPingying("menduimen");
        li.add(lt44);
        LogisticsType lt45 = new LogisticsType();
        lt45.setName("OCS");
        lt45.setPingying("ocs");
        li.add(lt45);
        LogisticsType lt46 = new LogisticsType();
        lt46.setName("配思货运");
        lt46.setPingying("peisihuoyunkuaidi");
        li.add(lt46);
        LogisticsType lt47 = new LogisticsType();
        lt47.setName("全晨快递");
        lt47.setPingying("quanchenkuaidi");
        li.add(lt47);
        LogisticsType lt48 = new LogisticsType();
        lt48.setName("全峰快递");
        lt48.setPingying("quanfengkuaidi");
        li.add(lt48);
        LogisticsType lt49 = new LogisticsType();
        lt49.setName("全际通物流");
        lt49.setPingying("quanjitong");
        li.add(lt49);
        LogisticsType lt50 = new LogisticsType();
        lt50.setName("全日通快递");
        lt50.setPingying("quanritongkuaidi");
        li.add(lt50);
        LogisticsType lt51 = new LogisticsType();
        lt51.setName("全一快递");
        lt51.setPingying("quanyikuaidi");
        li.add(lt51);
        LogisticsType lt52 = new LogisticsType();
        lt52.setName("如风达");
        lt52.setPingying("rufengda");
        li.add(lt52);
        LogisticsType lt53 = new LogisticsType();
        lt53.setName("三态速递");
        lt53.setPingying("santaisudi");
        li.add(lt53);
        LogisticsType lt54 = new LogisticsType();
        lt54.setName("盛辉物流");
        lt54.setPingying("shenghuiwuliu");
        li.add(lt54);

        LogisticsType lt57 = new LogisticsType();
        lt57.setName("速尔物流");
        lt57.setPingying("sue");
        li.add(lt57);
        LogisticsType lt58 = new LogisticsType();
        lt58.setName("盛丰物流");
        lt58.setPingying("shengfeng");
        li.add(lt58);
        LogisticsType lt59 = new LogisticsType();
        lt59.setName("赛澳递");
        lt59.setPingying("saiaodi");
        li.add(lt59);

        LogisticsType lt62 = new LogisticsType();
        lt62.setName("tnt");
        lt62.setPingying("tnt");
        li.add(lt62);
        LogisticsType lt63 = new LogisticsType();
        lt63.setName("ups");
        lt63.setPingying("ups");
        li.add(lt63);
        return new Result().success(li);
    }

    /**
     * @param id
     * @param type          1.快递，2.同城跑腿
     * @param logisticsNype
     * @param logisticsNo
     * @return
     */
    @ApiOperation(value = "商家端-确认发货")
    @RequestMapping(value = "/sendOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result sendOrder(@ApiParam(value = "订单ID", required = true) Integer id, Integer type, String logisticsType,
                            String logisticsNo) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        if (type == 1) {
            userOrder.setStatus(OrderStatus.DELIVERED.getKey());
            userOrder.setLogisticsNo(logisticsNo);
            userOrder.setLogisticstype(logisticsType);
            userOrder.updateById();
            return new Result().success("发货成功!");
        } else if (type == 2) {
            userOrder.setStatus(OrderStatus.DELIVERED.getKey());
            userOrder.updateById();
            return new Result().success("发货成功");
        } else {
            userOrder.setStatus(OrderStatus.DELIVERED.getKey());
            userOrder.updateById();
            return new Result().success("发货成功");
        }


    }

    @ApiOperation(value = "用户端-删除订单")
    @RequestMapping(value = "/delOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delOrder(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        userOrder.setIsUserDel(1);
        userOrder.updateById();
        return new Result().success("删除成功!");
    }

    @ApiOperation(value = "用户端-申请售后")
    @RequestMapping(value = "/addOrderService", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addOrderService(@ModelAttribute com.jh.jsuk.entity.UserOrderService service) {
        UserOrder userOrder = userOrderService.selectById(service.getOrderId());
        userOrder.setStatus(OrderStatus.SERVICE.getKey());
//        if (service.getType() != 3) {
//            userOrder.setStatus(OrderStatus.REFUND_MONEY.getKey());
//        } else {
//            userOrder.setStatus(OrderStatus.REFUND_GOODS.getKey());
//        }
        userOrder.updateById();
        service.setServiceCode(OrderNumUtil.getOrderIdByUUId());
        User user = userService.selectById(userOrder.getUserId());
        service.setUserName(user.getNickName());
        service.setUserPhone(user.getPhone());
        service.setStatus(OrderServiceStatus.PENDING.getKey());
        service.insert();
        return new Result().success("操作成功!");
    }

    @ApiOperation(value = "用户端-申请售后-用户地址")
    @RequestMapping(value = "/userAddress", method = {RequestMethod.POST, RequestMethod.GET})
    public Result userAddress(Integer orderId) {
        AfterSaleVo saleVo = userOrderService.getAddressAndPhone(orderId);
        return new Result().success(saleVo);
    }

    @ApiOperation(value = "用户端-更换商品-选择商品型号")
    @RequestMapping(value = "/changeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result changeGoods(@ApiParam(value = "商品ID", required = true) Integer shopGoodsId) {
        List<ShopGoodsSize> goodsSizeList = shopGoodsSizeService.selectList(new EntityWrapper<ShopGoodsSize>()
            .eq(ShopGoodsSize.SHOP_GOODS_ID, shopGoodsId)
            .eq(ShopGoodsSize.IS_DEL, 0));
        return new Result().success(goodsSizeList);
    }

    @ApiOperation(value = "用户端-确认收货 ")
    @RequestMapping(value = "/confirmReceipt", method = {RequestMethod.POST, RequestMethod.GET})
    public Result confirmReceipt(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().success("没有该订单", null);
        } else {
            //交易成功
            userOrder.setStatus(OrderStatus.SUCCESS.getKey());
            userOrder.updateById();
            return new Result().success("操作成功!");
        }
    }

    @ApiOperation(value = "商家端-确认换货")
    @RequestMapping(value = "/enterChangeGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result enterChangeGoods(@ApiParam(value = "订单ID", required = true) Integer id) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
            .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().success("没有该订单", null);
        } else {
            /*userOrder.setStatus(OrderStatus.SUCCESS.getKey());
            userOrder.updateById();*/
            return new Result().success("操作成功!");
        }
    }

    @ApiOperation(value = "商家端-确认退款")
    @RequestMapping(value = "/enterTuiKuan", method = {RequestMethod.POST, RequestMethod.GET})
    public Result enterTuiKuan(@ApiParam(value = "订单ID", required = true) Integer id,
                               @ApiParam(value = "退款金额", required = true) String price) throws MessageException {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
            .eq(UserOrder.ID, id));
        if (userOrder == null) {
            return new Result().erro("订单信息为空");
        } else {
            //余额退款
            userOrderService.refund(id, price);
            return new Result().success("退款成功");
        }
    }

    @ApiOperation(value = "用户端-催一催")
    @RequestMapping(value = "/pushAPush", method = {RequestMethod.POST, RequestMethod.GET})
    public Result pushAPush(@ApiParam(value = "订单ID", required = true) Integer id) {
        return new Result().success(userOrderService.pushAPush(id));
    }


    @ApiOperation(value = "用户端物流信息")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "orderId", value = "订单id", paramType = "query", dataType = "integer"),
    })
    @GetMapping("/disInfo")
    public R disInfo(Integer orderId)  {
        try{
            UserOrder order = userOrderService.selectById(orderId);
            String val = LogisticsUtil.queryData(order.getLogisticstype(), order.getLogisticsNo());
            LogisticsResponse logisticsResponse = new ObjectMapper().readValue(val, LogisticsResponse.class);
            logisticsResponse.parseCom();
            return R.succ(logisticsResponse);
        }catch (Exception e){
            log.error("",e);
            return R.err("没有物流信息");
        }
    }


/*    @ApiOperation("商家端-订单列表")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "userId", value = "商家用户id", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "status", value = "0待付款,1待发货,3=完成,7=售后",
            paramType = "query", dataType = "integer"),

    })
    @RequestMapping(value="/getAllOrdersByShopId",method={RequestMethod.GET,RequestMethod.POST})
    public Result getAllOrdersByShopId(Integer userId, Integer status){
        //封装数据的map
        Map<String,Object> map=new HashMap<>();

        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        if(managerUser==null){
            return new Result().erro("系统错误,请稍后再试");
        }
        Integer shopId = managerUser.getShopId();
        //查询店铺
        Shop shop=shopService.selectOne(new EntityWrapper<Shop>().eq(Shop.ID,shopId));
        map.put("shop",shop);

        //先根据店铺Id查询所有订单
        //status   0待付款,1待发货,3=完成,7=售后
        List<UserOrder> userOrderList=userOrderService.selectList(new EntityWrapper<UserOrder>()
                                                                       .eq(UserOrder.SHOP_ID,shopId)
                                                                       .eq(status!=null,UserOrder.STATUS,status)
                                                                       .eq(UserOrder.IS_USER_DEL,0)
                                                                       .eq(UserOrder.IS_SHOP_DEL,0)
                                                                       .eq(UserOrder.IS_CLOSED,0)
        );
        if(userOrderList==null || userOrderList.size()==0){
            return new Result().success("没有相关订单");
        }

        for(UserOrder userOrder:userOrderList){
            //遍历集合,每一个订单userOrder对应一张优惠券coupon
            Integer couponId=userOrder.getCouponId();
            Coupon coupon=couponService.selectOne(new EntityWrapper<Coupon>().eq(Coupon.ID,couponId));
           // userOrder.setCoupon(coupon);

            //遍历集合,每一个订单userOrder对应多个userOrderGoods,一个订单里面有多个商品
            Integer orderId=userOrder.getId();    //订单id
            List<UserOrderGoods> orderGoodsList=userOrderGoodsService.selectList(new EntityWrapper<UserOrderGoods>()
                                                                                 .eq(UserOrderGoods.ORDER_ID,orderId)
            );
            for(UserOrderGoods userOrderGoods:orderGoodsList){
                //遍历集合,每一个userOrderGoods对应一个shopGoodsSize
                Integer goodsId = userOrderGoods.getGoodsId();   //商品
                Integer goodsSizeId = userOrderGoods.getGoodsSizeId();
                ShopGoods shopGoods=shopGoodsService.selectOne(new EntityWrapper<ShopGoods>().eq(ShopGoods.ID,goodsId));
                ShopGoodsSize shopGoodsSize=shopGoodsSizeService.selectOne(new EntityWrapper<ShopGoodsSize>()
                                                                               .eq(ShopGoodsSize.ID,goodsSizeId));
                ShopOrderGoods shopOrderGoods = new ShopOrderGoods();
                shopOrderGoods.setShopGoods(shopGoods);
                shopOrderGoods.setShopGoodsSize(shopGoodsSize);

                userOrderGoods.setShopOrderGoods(shopOrderGoods);
            }
            //userOrder.setUserOrderGoodsList(orderGoodsList);
        }
        map.put("userOrderList",userOrderList);

        return new Result().success(map);
    }*/

    //平台-查看订单详情
    @RequestMapping(value = "/getUserOrderById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getUserOrderById(@RequestParam Integer orderId) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>().eq(UserOrder.ID, orderId));
        return new Result().success(userOrder);
    }
}
