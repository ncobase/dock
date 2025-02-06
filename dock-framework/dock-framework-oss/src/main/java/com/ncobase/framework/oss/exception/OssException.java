package com.ncobase.framework.oss.exception;

import java.io.Serial;

/**
 * OSS 异常类
 *
 * @author Lion Li
 */
public class OssException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OssException(String msg) {
        super(msg);
    }

}
