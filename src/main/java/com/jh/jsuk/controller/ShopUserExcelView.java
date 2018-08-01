package com.jh.jsuk.controller;

import com.jh.jsuk.entity.vo.ShopUserVo;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Author:xyl
 * Date:2018/8/1 10:36
 * Description:商户信息导出视图
 */
@Component("shopUserExcelView")
public class ShopUserExcelView extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.获取Controller中设置的数据
        List<ShopUserVo> vos = (List<ShopUserVo>) model.get("shopUserVos");
        // 2.创建sheet页
        Sheet sh = workbook.createSheet("商户列表");
        // 3.创建标题行
        // 3.1.创建一行
        Row rHeader = sh.createRow(0);
        Cell cHeader = rHeader.createCell(0, CellType.STRING);
        cHeader.setCellValue("店铺名称");
        cHeader = rHeader.createCell(1, CellType.STRING);
        cHeader.setCellValue("用户账号");
        cHeader = rHeader.createCell(2, CellType.STRING);
        cHeader.setCellValue("法人姓名");
        cHeader = rHeader.createCell(3, CellType.STRING);
        cHeader.setCellValue("身份证号");
        cHeader = rHeader.createCell(4, CellType.STRING);
        cHeader.setCellValue("移动电话");
        cHeader = rHeader.createCell(5, CellType.STRING);
        cHeader.setCellValue("专题栏");
        cHeader = rHeader.createCell(6, CellType.STRING);
        cHeader.setCellValue("状态");

        // 4.创建数据行
        for (int i = 0; i < vos.size(); i++) {
            ShopUserVo vo = vos.get(i);
            // 5.设置数据行的数据
            Row r = sh.createRow(i + 1);
            Cell c = r.createCell(0, CellType.STRING);
            c.setCellValue(vo.getShopName());
            c = r.createCell(1, CellType.STRING);
            c.setCellValue(vo.getUserName());
            c = r.createCell(2, CellType.STRING);
            c.setCellValue(vo.getLegalPersonName());
            c = r.createCell(3, CellType.STRING);
            c.setCellValue(vo.getIdCardNo());
            c = r.createCell(4, CellType.STRING);
            c.setCellValue(vo.getPhone());
            c = r.createCell(5, CellType.STRING);
            StringBuffer modularPortalName = new StringBuffer();
            vo.getModularPortals().forEach(modularPortal -> {
                modularPortalName.append(modularPortal.getName()).append("-");
            });
            c.setCellValue(modularPortalName.toString());
            c = r.createCell(6, CellType.STRING);
            if (vo.getIsCheck() == 0) {
                c.setCellValue("待审核");
            } else if (vo.getIsCheck() == 1) {
                c.setCellValue("审核通过");
            } else {
                c.setCellValue("驳回");
            }
        }
    }
}