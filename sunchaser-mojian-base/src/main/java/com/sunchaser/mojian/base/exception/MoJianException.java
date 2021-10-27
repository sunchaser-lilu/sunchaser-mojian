package com.sunchaser.mojian.base.exception;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/19
 */
public class MoJianException extends RuntimeException {
    private static final long serialVersionUID = -3792616129754882226L;

    public MoJianException(String message) {
        super(message);
    }
}
