// This file is auto-generated, don't edit it. Thanks.
package com.chaoxi.common;

import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.*;
import com.aliyun.teautil.models.*;
import com.aliyun.dysmsapi20170525.Client;


public class SMSSendCode {


  private static final String ACCESS_KEY_ID = System.getenv("temp1env");//秘钥ID

  private static final String ACCESS_KEY_SECRET = System.getenv("temp2env");//秘钥

  
  private static final String signName= "阿里云短信测试";//短信模板
  private static final String template_Code= "SMS_154950909";//短信签名
  private static final String endpoint="dysmsapi.aliyuncs.com";// 访问的域名 


  public static void sendCode(String phone,String code) throws Exception {
    Client client = SMSSendCode.createClient(
        ACCESS_KEY_ID,//秘钥ID：ACCESS_KEY_ID
        ACCESS_KEY_SECRET);//秘钥：ACCESS_KEY_SECRET
    SendSmsRequest sendSmsRequest = new SendSmsRequest()
        .setSignName(signName)//短信签名
        .setTemplateCode(template_Code)//模板code
        .setPhoneNumbers(phone)//手机号
        .setTemplateParam("{\"code\":"+code+"}");//验证码填充
    RuntimeOptions runtime = new RuntimeOptions();
    SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
    com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
  }

  /**
   * 使用AK&SK初始化账号Client
   * @param accessKeyId
   * @param accessKeySecret
   * @return Client
   * @throws Exception
   */
  public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
    Config config = new Config()
        // 您的 AccessKey ID
        .setAccessKeyId(accessKeyId)
        // 您的 AccessKey Secret
        .setAccessKeySecret(accessKeySecret);
    // 访问的域名
    config.endpoint = endpoint;
    return new Client(config);
  }
}
