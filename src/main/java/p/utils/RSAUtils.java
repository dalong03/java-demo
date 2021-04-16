package p.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSONObject;

public class RSAUtils {
	
	private final static Logger log = LoggerFactory.getLogger(RSAUtils.class);

    private static final String ALGORITHM = "RSA";

    private static final int KEY_SIZE = 1024;

    private static final int MAX_DECRYPT_BLOCK = 172;
    
    private static String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCgqXbhZ8QXjTbaxCZ0G4Ld2VfeNNDBKcOF1yBzcwflcldTnVk1qTolNw0cHLqBJVVG+3pKUDsgBPZxcL/qWAyxy/VEHwOdg8Xv5eHL4wS46ZCrsOJVQsJbTFXVyKfVNzr96DpvzOOBstSAjECao/EVM1/jnwlFmxtAI1p6BboBQIDAQAB";

    private static String pri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIKCpduFnxBeNNtrEJnQbgt3ZV9400MEpw4XXIHNzB+VyV1OdWTWpOiU3DRwcuoElVUb7ekpQOyAE9nFwv+pYDLHL9UQfA52Dxe/l4cvjBLjpkKuw4lVCwltMVdXIp9U3Ov3oOm/M44Gy1ICMQJqj8RUzX+OfCUWbG0AjWnoFugFAgMBAAECgYAD1+kef97hUoTn+U0x2muDNu7rUtmV3as1xQZaZD8gpv6ZGthmMdQFbWzWtoEL1zq2mO32sdpRnKv4+9P0R36ntVskFdvqFGdWCewIHcm8l//QiZVSaps6qn2duptIXhG9UPyysTwKc+rFKbxBh5TqznVQPW2z1m6Qkli62n2OQQJBAMer5OTDMQ1AJZdofkFJHV6X9twAGmQCzbLC7IyNZLUY3DZVn1XTTrnOoszVpDAXoc78wmjKnrgDLFOY+WfIzfkCQQCnU/7k5B1NB+xrSp6TSc5K535Oq47JBWlk+N3lPhYZyGM5M3UhptrDaD8w7JOSfBAJHZQx53meBrLkffaS0h1tAkAaJyGr9QUPvz8tPbqLwo/eO/lRh5HtV5CpwW5KWpq+PataUoy0KSE+pRodyj3fLrk661BreV6gq4m6yWaheWPBAkAvweCBUb4vKzDGGqER5sHTTSkWqQ5yv3GqWQF0GXlTWIdBXzdyKF5N4WYPv6YK7A/jmiae67MhcEYEwxLqKsAtAkABJGiOjSlls3nHT3r7GhjjYlO4E61kLX86rMx+RKB8aI8CWJ7vx3gS7AonaRPXzIGY+7taTEVIf4cwQ87hzC4J";

    private static PublicKey publicKey = null;
    
    private static PrivateKey privateKey = null;
    
    static {
        try {
            List<String> lines = Files.readAllLines(Paths.get(""));
            if (lines != null && lines.size() > 0) {
                pub = lines.get(0);
            }
            
            lines = Files.readAllLines(Paths.get(""));
            if (lines != null && lines.size() > 0) {
                pri = lines.get(0);
            }
        } catch (IOException e) {
        	log.error("加载私钥、公钥文件有误", e);
        }
        
        try {
			publicKey = getPublicKey(pub);
			privateKey = getPrivateKey(pri);
		} catch (Exception e) {
			log.error("私钥、公钥有误", e);
		}
    }

    /**
     * 随机生成密钥对（包含公钥和私钥）
     */
    public static KeyPair generateKeyPair() throws Exception {
        // 获取指定算法的密钥对生成器:
        KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);
        // 初始化密钥对生成器（指定密钥长度, 使用默认的安全随机数源）
        gen.initialize(KEY_SIZE);
        // 随机生成一对密钥（包含公钥和私钥）
        return gen.generateKeyPair();
    }
    /**
     * 根据私钥的 Base64 文本创建私钥对象
     */
    private static PrivateKey getPrivateKey(String priKeyBase64) throws Exception {
        // 把 私钥的Base64文本 转换为已编码的 私钥bytes
        byte[] encPriKey = Base64Utils.decodeFromString(priKeyBase64);
        // 创建 已编码的私钥规格
        PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(encPriKey);
        // 获取指定算法的密钥工厂, 根据 已编码的私钥规格, 生成私钥对象
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(encPriKeySpec);
    }

    /**
     * 根据公钥的 Base64 文本创建公钥对象
     */
    private static PublicKey getPublicKey(String pubKeyBase64) throws Exception {
        // 把 公钥的Base64文本 转换为已编码的 公钥bytes
        byte[] encPubKey = Base64Utils.decodeFromString(pubKeyBase64);
        // 创建 已编码的公钥规格
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(encPubKey);
        // 获取指定算法的密钥工厂, 根据 已编码的公钥规格, 生成公钥对象
        return KeyFactory.getInstance(ALGORITHM).generatePublic(encPubKeySpec);
    }
    
    /**
     * 公钥加密数据
     */
    @SuppressWarnings("unused")
	private static byte[] encrypt(byte[] plainData) throws Exception {
        PublicKey pubKey = getPublicKey(pub);
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化密码器（公钥加密模型）
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        // 加密数据, 返回加密后的密文
        return cipher.doFinal(plainData);
    }

    /**
     * 私钥解密数据
     */
    @SuppressWarnings("unused")
    private static byte[] decrypt(byte[] cipherData) throws Exception {
		PrivateKey priKey = getPrivateKey(pri);
		// 获取指定算法的密码器
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 初始化密码器（私钥解密模型）
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		// 解密数据, 返回解密后的明文
		return cipher.doFinal(cipherData);
	}
	
    /**
     * 私钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPriKey(byte[] data) throws Exception {
        return encryptByPriKey(privateKey, data);
    }

    private static byte[] encryptByPriKey(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(1, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for (int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPriKey(byte[] encryptedData) throws Exception {
        return decryptByPriKey(privateKey, encryptedData);
    }

    private static byte[] decryptByPriKey(PrivateKey privateKey, byte[] encryptedData) throws Exception {

        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(2, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for (int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPubKey(byte[] data) throws Exception {
        return encryptByPubKey(publicKey, data);
    }

    private static byte[] encryptByPubKey(PublicKey publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(1, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for (int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 公钥解密
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPubKey(byte[] encryptedData) throws Exception {
        return decryptByPubKey(publicKey, encryptedData);
    }

    private static byte[] decryptByPubKey(PublicKey publicKey, byte[] encryptedData) throws Exception {

        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(2, publicKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for (int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static int getDecryptBlock() {
        return MAX_DECRYPT_BLOCK;
    }

    private static String filterEncryptString(String encrypt) {
        return encrypt.replaceAll("\\s*", "");
    }

    private static boolean isContainsCh(String string) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(string);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 私钥加密
     */
    public static String mutiEncryptByPriKey(String string) {
        int encryptLength = 117;
        if (isContainsCh(string)) encryptLength = 33;
        int length = string.length();
        int loop = 0;
        if (length % encryptLength > 0) {
            loop = length / encryptLength + 1;
        } else {
            loop = length / encryptLength;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (length <= encryptLength) {
                byte[] encryptByPublicKey = encryptByPriKey(string.getBytes());
                String encodeToString = Base64Utils.encodeToString(encryptByPublicKey);
                return filterEncryptString(encodeToString);
            } else {
                for (int i = 0; i < loop; i++) {
                    int end = ((i + 1) * encryptLength) < length ? ((i + 1) * encryptLength) : length;
                    String substring = string.substring(encryptLength * i, end);
                    byte[] encryptByPublicKey = encryptByPriKey(substring.getBytes());
                    String encodeToString = Base64Utils.encodeToString(encryptByPublicKey);
                    stringBuffer.append(encodeToString);
                }
                return filterEncryptString(stringBuffer.toString());
            }
        } catch (Exception e) {
            log.error("加密失败", e);
            return null;
        }
    }

    /**
     * 私钥解密
     */
    public static String mutiDecryptByPriKey(String message) {

        if (message.length() % 172 != 0) {
        	log.error("密文长度有误");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int cnt = message.length() / 172;
        int i = 0;
        do {
            try {
                byte[] bytes = Base64Utils.decodeFromString(message.substring(172 * i, 172 * (i + 1)));
                byte[] decryptByPrivateKey = decryptByPriKey(RSAUtils.getPrivateKey(pri), bytes);
                sb.append(new String(decryptByPrivateKey));
                i++;
            } catch (Exception e) {
            	log.error("解密失败", e);
            	return null;
            }
        } while(i < cnt);
        return sb.toString();
    }

    /**
     * 公钥加密
     */
    public static String mutiEncryptByPubKey(String string) {
        int encryptLength = 117;
        if (isContainsCh(string)) encryptLength = 33;
        int length = string.length();
        int loop = 0;
        if (length % encryptLength > 0) {
            loop = length / encryptLength + 1;
        } else {
            loop = length / encryptLength;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            if (length <= encryptLength) {
                byte[] encryptByPublicKey = encryptByPubKey(string.getBytes());
                String encodeToString = Base64Utils.encodeToString(encryptByPublicKey);
                return filterEncryptString(encodeToString);
            } else {
                for (int i = 0; i < loop; i++) {
                    int end = ((i + 1) * encryptLength) < length ? ((i + 1) * encryptLength) : length;
                    String substring = string.substring(encryptLength * i, end);
                    byte[] encryptByPublicKey = encryptByPubKey(substring.getBytes());
                    String encodeToString = Base64Utils.encodeToString(encryptByPublicKey);
                    stringBuffer.append(encodeToString);
                }
                return filterEncryptString(stringBuffer.toString());
            }
        } catch (Exception e) {
            log.error("解密失败", e);
            return null;
        }
    }
    
    /**
     * 公钥解密
     */
    public static String mutiDecryptByPubKey(String message) {

        if (message.length() % 172 != 0) {
        	log.error("密文长度有误");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int cnt = message.length() / 172;
        int i = 0;
        do {
            try {
                byte[] bytes = Base64Utils.decodeFromString(message.substring(172 * i, 172 * (i + 1)));
                byte[] decryptByPrivateKey = decryptByPubKey(RSAUtils.getPublicKey(pub), bytes);
                sb.append(new String(decryptByPrivateKey));
                i++;
            } catch (Exception e) {
            	log.error("解密失败", e);
            	return null;
            }
        } while( i < cnt);
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
    	JSONObject m = new JSONObject();
    	m.put("openid", "170797d05138f3cf775d311e8d41c283ec841989");
    	m.put("sig", "170797d05138f3cf775d311e8d41c283ec841989");
    	String cont = m.toString();
    	String cipher = mutiEncryptByPubKey(cont);
    	System.out.println(cipher);
    	
    	
    	String src = mutiDecryptByPubKey("YBC1wIHkyPGQhjDTJm68Dhoq3GOBeCNLo4LEXN+r3rfz5w9z+FB+sijWNK+h+GzmIV9wXwJFCSJjt0WKlx34MurlflnN7sFi1DXTROy0Fw+/CXF8i5OM3Z/D7g+V9mTWsFoUi34G5DDvvMHrDX1wsQlihfT9VJqrt23KYgCXTlw=Dbh/jlsTNRGB8kSlUWMj+EebwqRbM29qb9msvB+xdID9OjOvgCv7pTPmV+9LbRws5HqrJ/u01QKPaIGusaGfIlQ2fSWcThhZGZyBYpjDZd+ACPkvBF9O/FR2dl04HICvyrQVBinLRDTwgVipjEN0i19RFS259FxacRDHqjZmCF0=");
    	System.out.println(src);
    	
    }

}