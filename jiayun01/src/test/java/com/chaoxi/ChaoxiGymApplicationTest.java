package com.chaoxi;

import com.chaoxi.common.CodeUtils;
import com.chaoxi.common.SMSSendCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChaoxiGymApplicationTest {
  @Test
  public void sendMsg(){
    try {
      Integer code = CodeUtils.generateValidateCode(4);
      System.out.println(code);
      SMSSendCode.sendCode("13947711568",String.valueOf(code));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
