package com.fm.example.demo.commands;

import com.gerenvip.messenger.fm.FMClient;
import com.gerenvip.messenger.fm.command.FMCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangwei on 2019/2/19.
 * wangwei@jiandaola.com
 */
@Component("commands")
public class Commands {

    private List<FMCommand> commands;

    @Autowired
    public Commands(List<FMCommand> commands) {
        this.commands = commands;
        if (this.commands != null && !this.commands.isEmpty()) {
            FMCommand[] fmCommands = this.commands.toArray(new FMCommand[0]);
            FMClient.getInstance().withFMCommands(fmCommands);
        }
    }
}
