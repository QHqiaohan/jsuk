#数据库修改记录



### 消息表
2018-6-25 13:09:47
```sql
-- 关联评论id
ALTER TABLE `js_news`
ADD COLUMN `market_comment_id`  int(11) NULL COMMENT '系统消息不使用此字段' AFTER `send_user_id`;
ALTER TABLE `js_news_user`
-- 修改字段名称
CHANGE COLUMN `status` `is_read`  int(1) NULL DEFAULT 0 COMMENT '0:未读 1:已读' AFTER `received_id`,
-- 添加推送标志位
ADD COLUMN `is_pushed`  int(1) NULL DEFAULT 0 COMMENT '0:未推送  1:已经推送' AFTER `is_read`;
```

### 商品评价
2018-6-25 15:44:35
```sql
ALTER TABLE `js_goods_evaluate`
CHANGE COLUMN `id_del` `is_del`  int(1) NULL DEFAULT 0 COMMENT '0:否 1:是' AFTER `image`;
```