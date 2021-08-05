package com.mioto.pms.utils;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.weixin.model.MiniProgramUser;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author mioto-qinxj
 * @date 2020/7/1
 */
@Slf4j
public class BaseUtil {
    /**
     * 获取登录用户信息
     * @return
     */
    public static User getLoginUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof User){
            return (User)object;
        }else if (object instanceof MiniProgramUser){
            MiniProgramUser miniProgramUser = (MiniProgramUser)object;
            User user = new User();
            user.setId(miniProgramUser.getUserId());
            return user;
        }
        return new User();
    }

    /**
     * 当前登录用户角色id  - 当前只需要判断是否为房东（id:2）即可,如果是房东加入数据筛选条件
     * @return
     */
    public static int getLoginUserRoleId() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (object instanceof  Map) {
            Map<String, String> map = (Map<String, String>) object;
            return Integer.parseInt(map.values().stream().findFirst().get());
        }
        return  0;
    }

    public static Integer getLogonUserId(){
        Integer userId = null;
        if (getLoginUserRoleId() == 2){
            userId = getLoginUser().getId();
        }
        return userId;
    }


    /**
     * 判断除id外其他属性是否全部为null
     * @param object
     * @param superClass true - 计算父类
     * @return
     */
    public static boolean checkObjIsNullIgnoreId(Object object,boolean superClass) {
        if (null == object) {
            return true;
        }
        try {
            Field[] fields;
            if (superClass){
                fields = object.getClass().getSuperclass().getDeclaredFields();
            }else {
                fields = object.getClass().getDeclaredFields();
            }
            for (Field f : fields) {
                if (StrUtil.equals(f.getName(),"serialVersionUID") || StrUtil.equals(f.getName(),"id") ){
                    continue;
                }
                f.setAccessible(true);
                if (f.get(object) != null) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * aes解密
     * @param data
     * @param key
     * @param iv
     * @param encodingFormat
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv, String encodingFormat){
        //被加密的数据
        byte[] dataByte = Base64.getDecoder().decode(data);
        //加密秘钥
        byte[] keyByte = Base64.getDecoder().decode(key);
        //偏移量
        byte[] ivByte = Base64.getDecoder().decode(iv);
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, encodingFormat);
            }
        }catch (Exception e){
            Console.log(e);
        }
        return StrUtil.EMPTY;
    }


    /**
     * 创建抄表任务时间表达式 - dd HH:mm:ss
     * @param day
     * @param time
     * @return
     */
    public static String createSchedulingPattern(String day,String time){
        StrBuilder dateStr = StrBuilder.create();
        if (StrUtil.isNotEmpty(day)){
            if (day.length() == 1){
                dateStr.append("0");
            }
            dateStr.append(day).append(" ");
            if (StrUtil.isNotEmpty(time)){
                dateStr.append(time);
            }else {
                dateStr.append("00:00:00");
            }
        }
        return dateStr.toString();
    }

    /**
     * 创建账单编号
     * @return
     */
    public static synchronized String createBillNumber(){
        String str = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN);
        String random = String.valueOf(RandomUtil.randomInt(1000,9999));
        return str + random;
    }

    /**
     * 获取当前月抄表日期
     * @param day
     * @param time
     * @return
     */
    public static Date toDate(String day,String time){
        DateTime dateTime = DateUtil.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        calendar.set(Calendar.HOUR_OF_DAY, dateTime.hour(true));
        calendar.set(Calendar.MINUTE, dateTime.minute());
        calendar.set(Calendar.SECOND, dateTime.second());
        return DateUtil.date(calendar).toJdkDate();
    }

    /**
     * 计算间隔天数 忽略时分秒
     * @param start
     * @param end
     * @return
     */
    public static long intervalDay(Date start,Date end){
        return DateUtil.between(DateUtil.beginOfDay(start),DateUtil.beginOfDay(end), DateUnit.DAY);
    }
    /**
     * 请求签名
     * @param appId 商户号
     * @param body 请求报文主体
     * @return
     */
    public static String getPaySign(String appId, String body) {
        String nonceStr = IdUtil.simpleUUID().toUpperCase();
        long timestamp = System.currentTimeMillis()/1000;

        String message = buildSignStr(appId, String.valueOf(timestamp), nonceStr, body);
        String signature = "";
        try {
            signature = sign(message.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("签名错误 - {}",e);
        }
        return JSONUtil.createObj().append("appId",appId)
                .append("paySign",signature)
                .append("timeStamp",timestamp)
                .append("nonceStr",nonceStr).toString();
    }

    /**
     * 请求签名
     * @param mchid 商户号
     * @param serialNo 商户API证书serial_no
     * @param method HTTP请求的方法
     * @param url 获取请求的绝对URL
     * @param body 请求报文主体
     * @return
     */
    public static String getRequestSign(String mchid,String serialNo,String method, String url, String body) {
        String nonceStr = IdUtil.simpleUUID().toUpperCase();
        String schema = "WECHATPAY2-SHA256-RSA2048";
        long timestamp = System.currentTimeMillis() / 1000;

        String message = buildMessage(method, URLUtil.url(url), timestamp, nonceStr, body);
        String signature = null;
        try {
            signature = sign(message.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("签名错误 - {}",e);
        }
        return StrBuilder.create(schema).append(StrUtil.SPACE)
                .append("mchid=").append("\"").append(mchid).append("\"").append(StrUtil.COMMA)
                .append("nonce_str=").append("\"").append(nonceStr).append("\"").append(StrUtil.COMMA)
                .append("timestamp=").append("\"").append(timestamp).append("\"").append(StrUtil.COMMA)
                .append("serial_no=").append("\"").append(serialNo).append("\"").append(StrUtil.COMMA)
                .append("signature=").append("\"").append(signature).append("\"").toString();
    }

    /**
     * 计算签名值
     * @param message
     * @return
     * @throws Exception
     */
    private static String sign(byte[] message) throws Exception{
        Signature sign = Signature.getInstance("SHA256withRSA");
        final String filePath = "F:/work/pms/apiclient_key.pem";
        sign.initSign(getPrivateKey(filePath));
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    /**
     * 生成签名串
     * @param method
     * @param url
     * @param timestamp
     * @param nonceStr
     * @param body
     * @return
     */
   private static String buildMessage(String method, URL url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.getPath();
        if (url.getQuery() != null) {
            canonicalUrl += "?" + url.getQuery();
        }
       return buildSignStr(method,canonicalUrl,String.valueOf(timestamp),nonceStr,body);
    }

    private static String buildSignStr(String... strings){
        StrBuilder strBuilder = StrBuilder.create();
        for (String string : strings) {
            strBuilder.append(string).append("\n");
        }
        return strBuilder.toString();
    }

    /**
     * 获取私钥
     * @param filename
     * @return
     * @throws IOException
     */
    private static PrivateKey getPrivateKey(String filename) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }
}
