package com.mioto.pms.component;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件加密
 * @author mioto-qinxj
 * @date 2020/7/29
 */
@Component("encryptablePropertyResolver")
public class BasicEncryptablePropertyResolver implements EncryptablePropertyResolver {

    private final PooledPBEStringEncryptor encryptor;
    /**
     * 标识配置文件中需要解密的值前缀
     */
    private final String prefix = "ENC[";
    /**
     * 标识配置文件中需要解密的值后缀
     */
    private final String suffix = "]";

    public BasicEncryptablePropertyResolver() {
        encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        //加密盐
        config.setPassword("mioto-project");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
    }

    @Override
    public String resolvePropertyValue(String value) {
        if (value != null && value.startsWith(prefix) && value.endsWith(suffix)) {
            return encryptor.decrypt(value.substring(prefix.length() - 1));
        }
        return value;
    }

    public static void main(String[] args) {
        BasicEncryptablePropertyResolver encryptablePropertyResolver = new BasicEncryptablePropertyResolver();
        System.out.println(encryptablePropertyResolver.encryptor.encrypt("qgafaQPI"));
        System.out.println(encryptablePropertyResolver.resolvePropertyValue("ENC[fXOYcT7fnjzhwTan6+ztt9AmfQA6tjEX2JRlpvbd+Rc=]"));
    }
}
