package com.github.codedrinker.fm.command;

import com.github.codedrinker.fm.FMClient;

/**
 * @author wangwei on 2019/2/19.
 * wangwei@jiandaola.com
 */
public abstract class AbsDefaultCommand extends FMAbstractCommand {

    public static final String DEFAULT_COMMAND_NAME = "DEFAULT";

    @Override
    public final String command(String... params) {
        return super.command(params);
    }

    @Override
    String commandInternal(String... params) {
        return FMClient.getInstance().getFmCommandParser().toCommand(DEFAULT_COMMAND_NAME, params);
    }
}
