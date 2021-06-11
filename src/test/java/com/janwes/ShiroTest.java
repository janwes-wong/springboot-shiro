package com.janwes;

import com.janwes.mapper.UserInfoMapper;
import com.janwes.pojo.UserInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes
 * @date 2021/5/26 15:30
 * @description
 */
@SpringBootTest(classes = ShiroApplication.class)
@RunWith(SpringRunner.class)
public class ShiroTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testPassword() {
        // 加密算法
        String algorithmName = "MD5";
        // 密码原值
        Object source = "qaz258963.";
        // 密码盐
        Object salt = "ef297rvre249br#-/=!";
        // 加密次数（hash迭代次数）
        int hashIterations = 5;
        String password = new SimpleHash(algorithmName, source, ByteSource.Util.bytes(salt), hashIterations).toHex();
        System.out.println(">>> MD5加密---HEX十六进制编号方式：" + password);

        String base64 = new SimpleHash(algorithmName, source, ByteSource.Util.bytes(salt), hashIterations).toBase64();
        System.out.println(">>> MD5加密---BASE64编码方式：" + base64);

        String s = new SimpleHash("SHA-256", source, ByteSource.Util.bytes(salt), hashIterations).toString();
        System.out.println(">>> SHA-256加密后密码：" + s);
    }

    @Test
    public void getUserList() {
        UserInfo userInfo = userInfoMapper.getUserList("janwes");
        System.out.println(userInfo.toString());
    }
}
