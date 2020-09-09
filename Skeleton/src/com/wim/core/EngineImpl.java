package com.wim.core;

import com.wim.commands.contracts.Command;
import com.wim.core.contracts.*;
import com.wim.core.factories.CommandFactoryImpl;
import com.wim.core.factories.ItemManagementFactoryImpl;
import com.wim.core.providers.CommandParserImpl;
import com.wim.core.providers.ConsoleReader;
import com.wim.core.providers.ConsoleWriter;

import java.util.List;

public class EngineImpl implements Engine {

    private static final String TERMINATION_COMMAND = "Exit";

    private Reader reader;
    private Writer writer;
    private ItemManagementFactory itemManagementFactory;
    private CommandFactory commandFactory;
    private CommandParser commandParser;
    private ItemManagementRepository itemManagementRepository;

    public EngineImpl() {
        this.reader = new ConsoleReader();
        this.writer = new ConsoleWriter();
        this.itemManagementFactory = new ItemManagementFactoryImpl();
        this.commandFactory = new CommandFactoryImpl();
        this.commandParser = new CommandParserImpl();
        this.itemManagementRepository = new ItemManagementRepositoryImpl();
    }

    public void start() {
        while (true) {
            try {
                String commandAsString = reader.readLine();
                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(commandAsString);

            } catch (Exception ex) {
                writer.writeLine(ex.getMessage() != null && !ex.getMessage().isEmpty() ? ex.getMessage() : ex.toString());
            }
        }
    }

    private void processCommand(String commandAsString) {
        if (commandAsString == null || commandAsString.trim().equals("")) {
            throw new IllegalArgumentException("Command cannot be null or empty.");
        }

        String commandName = commandParser.parseCommand(commandAsString);
        Command command = commandFactory.createCommand(commandName, itemManagementFactory, itemManagementRepository);
        List<String> parameters = commandParser.parseParameters(commandAsString);
        String executionResult = command.execute(parameters);
        writer.writeLine(executionResult);
    }
}
