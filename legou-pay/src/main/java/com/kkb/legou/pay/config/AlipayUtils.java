package com.kkb.legou.pay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayUtils {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000118619500";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCN3kzV0KCzzd4vjjBjiP/uO6Npd9/H1Y4P7kzpTv78tnckPwrOE61OH+owlgN4Bzvj8/AvaPoA/YVUlIIa6eBFKrBI8WPnLf4yizfcRj/JFZJ+lThSQJOpmwaOpDJ2FpbheX8bcklf5pkHwhbPZrjFF9rUOuUhtf6lIgNLgGUR9l9B6l9MyTkWSpIhaSyGy4pYZjOtq3KgsM7wpMX6UDDbFRNDRzelEaD9Vd5iw98ZaugQBn2qIrbPuIBa2fQqm1R32AY2gBqxlNv3MOYkq1xoZ6uYDCIN/W51JeTxtVWtBE55TDaCxEnWzJlyxTCPYNvaeEGui/x0tRUnpFXwzHjlAgMBAAECggEALibLSCN+o/+s8DuJclf3KkEsf/sHSbMna/dR/fQ872lMiPYZRcEQ+AFZ5kRnDH0N9rI8yK+V5QyUe1d0m9zJoLaqcpBUt7g2oaq4mWghCAGEjrlsgRWQ8/QCxhSUdeYTX3Zu6zbat+AEmImDciUwpq2D+tbOA5F5La9K8xzpxFWtdUbcwt4WgK9aAipAYK64PF9zbZvBrXf07xhuB/yqyyjsvpE4VByPjRshIQotcyN/N9XzPHxedFEuMdnNn4BzLMlOf4tNsD7bFJVGAAWpTHBevvQGEdYgdRkWltQn6zfp5UcgqJCIjQKKKeSizQFvFnES4glDgCW7nlrjZCkzYQKBgQDOS8UJVpGKC38I6Twf5F7kbnMrjGZA7OJcWI5/GxbKsUXJKyrMq3fDrDQwVlrORm7/Pr7mwWWG5ccYUiuyh0TBnJe+UC1IN9duJ+WfAl2/2YZZKgiVQtPbEDfhDw0b+mNFy0que2AGlkiGfo/uqb5NpsHq6QaOwbl2xp3zGVfdLwKBgQCwDKsG4jPrxFARB7XnhSMOGhRFWwMdCOVgi9KvYVppDBUgUG03tgMaMu+/9i5LjI+GghBqBRTay+FuJTXmRO8uUJUokuv5oBwCFcxBZyJeYc7XGMZ91XyLSVeKd2vRk4Hx8nwIXmhhQJMqVuBIspOFXbBFcYPTN7kZky+rwzpOKwKBgQC+uIQgPv1sn3ZBmcdxQKOOEqK9Vx7N1XLspx6OWk/28m8vLY5zC+88Yr8ZFSz8WVeS+MJq+c9QKKypSoaFldM2H/yWAO/sPVwYxh/eosRjcspnMpoezFqqw1K16kFXZWRUCT8xEnTTgiro1KdioWGDzrCm6eqbHnTFX5h1QC/gFwKBgBDyl0BJZGAs/Nw21s8b5P0Fi7AFi+4ung5GR5j0kUSNkjcTJwB2+CN8hdH//ALv3B1Bxhy+snxBpkj5MhAh0LivrzxBsI/OVwPoX0poiEl3tvbD/s+8TooGHHRK/MknHxpQkponpHrAQaqQxHSbJFJxa3ez7tHVim8o2Sz61VETAoGATFYqULaTWjVYhehFmTVfXXE7L8PyQO9E53IXfZ72AZEezGIAWsOIVde53mDR/VPFhulNWFwaCZhIENB09bf+KRXnvsBGrL9O+f3+ElUR0lGpY4N3Tz0yR0uaGGJ/zSjDkn3+d185Uu+kgKI+o4lg2o3vsvyVZpk3f2ywxUFohkA=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsVIYICtvOa/tpzr2ST0W0YF5zmPqbwz9UgYl91Rd1bgLleZWB9OoZ8y+rd+8G2AOynv9at2/ExoIjfzQ7EAesoTginusqsZJvXdhZYdnwnEjYC8J8d7I/XdpaHa9NE5UDESvuh2QnI8/6CDOJPgI6EG4mJjBQlAEmVQVd0hKScDCfdVINsG0SB94Xj4eeiqwGUUJergAg1taxFyUmaPrDuzSfYH5mBtSbZMjdcb6uvpalqgSoSAdnql3WRDwYe5YloRK1+W5NMsUIwcid5OSbRQoan6GsiSgY/xcF1EQQK4OBGV/4dfxeA6SxE0/tcHtGSn392Ery8Bixvqd7Ei7cwIDAQAB";

    // 服务器异步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://ephm72.natappfree.cc/getnotify";

    // 页面跳转同步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://ephm72.natappfree.cc/getreturn";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关,注意这些使用的是沙箱的支付宝网关，与正常网关的区别是多了dev
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "";


    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
