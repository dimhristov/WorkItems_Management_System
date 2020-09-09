package com.wim.core.contracts;

import com.wim.commands.contracts.Command;

public interface CommandFactory {

    Command createCommand(String commandTypeAsString, ItemManagementFactory itemManagementFactory,
                          ItemManagementRepository itemManagementRepository);
}
