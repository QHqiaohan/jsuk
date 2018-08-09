package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.Menu;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.MenuService;
import com.jh.jsuk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(value="/selectMenuManager",method = {RequestMethod.POST,RequestMethod.GET})
    public Result selectMenuManager(){
        List<ManagerUser> managerUserList=managerUserService.selectList(new EntityWrapper<ManagerUser>()
                                                                         .eq(ManagerUser.CAN_USE,1)
                                                                         .eq(ManagerUser.IS_DEL,0)
        );
        return new Result().success(managerUserList);
    }


    @RequestMapping(value="/selectMenuList",method = {RequestMethod.POST,RequestMethod.GET})
    public Result selectMenuList(){
        List<Menu> menuList=menuService.selectList(new EntityWrapper<Menu>()
                                                  .orderBy(Menu.ORDER_NUM,false)
        );
        return new Result().success(menuList);
    }


    //根据管理员id查询拥有的权限
    @RequestMapping(value="/queryMenuByUid",method = {RequestMethod.POST,RequestMethod.GET})
    public Result queryMenuByUid(@RequestParam(value="menuUserId") Integer userId){
        if(userId==null){
            return new Result().erro("请选择管理员");
        }
        List<Menu> managerUserMenList=menuService.queryMenuByUid(userId);
        System.out.println("size:"+managerUserMenList.size());
        return new Result().success(managerUserMenList);
    }


    //设置权限
    @RequestMapping(value="/setMenu",method = {RequestMethod.POST,RequestMethod.GET})
    public Result setMenu(@RequestParam Integer menuId,@RequestParam Integer userId){
        if(menuId==null || userId==null){
            return new Result().erro("请选择管理员");
        }
        try {
            menuService.setMenu(menuId, userId);
            return new Result().success("设置成功");
        }catch(Exception e){
            return new Result().erro("设置失败");
        }

    }



}

