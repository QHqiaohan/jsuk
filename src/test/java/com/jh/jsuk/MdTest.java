package com.jh.jsuk;

import com.github.tj123.common.enum2md.MarkdownUtil;
import org.junit.Test;

/**
 * markdown 测试
 */
public class MdTest {

    @Test
    public void test() throws Exception {
        MarkdownUtil.create("com.jh.jsuk.envm","E:\\work\\jushang\\jsuk-doc\\src\\status_gen.md");
    }

}
