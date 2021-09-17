package com.mioto.pms.netty.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author admin
 * @date 2021-09-04 17:19
 */
@Component
public class WssConfig {
    @Value("${server.ssl.key-store-type}")
    private String type;
    @Value("${server.ssl.key-store-password}")
    private String password;
    @Value("${server.ssl.key-store}")
    private String path;

    @Bean
    public SSLContext createSSLContext() throws Exception {
        SSLContext sslContext;
        KeyStore ks = KeyStore.getInstance(type);
        InputStream ksInputStream = new FileInputStream(path);
        ks.load(ksInputStream, password.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }
}
