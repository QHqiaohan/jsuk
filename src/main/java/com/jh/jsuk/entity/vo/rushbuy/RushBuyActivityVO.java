package com.jh.jsuk.entity.vo.rushbuy;

import com.jh.jsuk.entity.ShopRushBuyActivity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class RushBuyActivityVO extends ShopRushBuyActivity {

    List<RushBuySizeVo> sizes;

}
