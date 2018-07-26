# 巨商U客

### 页面原型
[商家端UI](https://pro.modao.cc/app/4b9730dcafbba9861f60f8469d6de9dbfa1cec7a#screen=s06cdf9a12c5114bb6951e9)
[配送端UI](https://pro.modao.cc/app/406c907c2b9e719eb8135b501eed043fdb75d87d#screen=s2b6bd5b66cb5e46d556887)
[客户端UI](https://pro.modao.cc/app/b62c84dcd99da3783975a947f8a851212b9a8ebe#screen=s63018c19297b37a10f978f)

### 后台文档
[后台文档](https://shimo.im/sheet/pCRSwqIezSUgzgZr/cQc6n/)

### 正式环境
[正式环境swagger](http://47.99.45.67/api/v1/swagger-ui.html#/)

### 后台
#   http://47.99.45.67/jsuk-demo2/#g=1&p=系统首页

### 时间安排
[时间安排](https://docs.qq.com/sheet/BIvRjE1Otl8A4YzDgC14WVct0H08qE1Bvz6g0)

### 快速打包

```bash
npm install -g yarn
yarn

#打包
yarn pkg
#上传服务器
yarn ci upload -e prod -p (服务器密码)
#运行部署
yarn ci exe -e prod -p (服务器密码) deploy_prod.sh

```

### 数据库命名规范


> 数据库命名 所有 bool值已 is_ 开始 1 表示true 0 表示 false 