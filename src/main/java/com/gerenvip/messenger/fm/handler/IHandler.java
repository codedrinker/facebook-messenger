package com.gerenvip.messenger.fm.handler;

/**
 * @author wangwei on 2018/9/27.
 * wangwei@jiandaola.com
 */
public interface IHandler<T> {
    boolean canHandle(T t);

    void handle(T t);
}
