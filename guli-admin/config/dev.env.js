"use strict";
const merge = require("webpack-merge");
const prodEnv = require("./prod.env");

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // nginx 反向代理地址
  BASE_API: '"http://localhost:8160"',
  // 阿里云 OSS bucket 地址
  OSS_PATH: '"https://guli-edu-vectorx.oss-cn-shanghai.aliyuncs.com"'
});
