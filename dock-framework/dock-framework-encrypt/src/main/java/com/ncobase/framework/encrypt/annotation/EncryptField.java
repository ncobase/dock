package com.ncobase.framework.encrypt.annotation;

import com.ncobase.framework.encrypt.enumd.AlgorithmType;
import com.ncobase.framework.encrypt.enumd.EncodeType;

import java.lang.annotation.*;

/**
 * 字段加密注解
 *
 * @author 老马
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {

    /**
     * 加密算法
     */
    AlgorithmType algorithm() default AlgorithmType.DEFAULT;

    /**
     * 秘钥。AES、SM4 需要
     */
    String password() default "";

    /**
     * 公钥。RSA、SM2 需要
     */
    String publicKey() default "";

    /**
     * 私钥。RSA、SM2 需要
     */
    String privateKey() default "";

    /**
     * 编码方式。对加密算法为 BASE64 的不起作用
     */
    EncodeType encode() default EncodeType.DEFAULT;

}
