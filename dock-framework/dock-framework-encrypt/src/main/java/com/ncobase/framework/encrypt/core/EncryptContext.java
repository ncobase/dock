package com.ncobase.framework.encrypt.core;

import com.ncobase.framework.encrypt.enumd.AlgorithmType;
import com.ncobase.framework.encrypt.enumd.EncodeType;
import lombok.Data;

/**
 * 加密上下文 用于 encryptor 传递必要的参数。
 *
 * @author 老马
 * @version 4.6.0
 */
@Data
public class EncryptContext {

    /**
     * 默认算法
     */
    private AlgorithmType algorithm;

    /**
     * 安全秘钥
     */
    private String password;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 编码方式，base64/hex
     */
    private EncodeType encode;

}
