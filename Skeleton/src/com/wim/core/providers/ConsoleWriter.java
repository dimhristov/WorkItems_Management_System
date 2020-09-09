package com.wim.core.providers;

import com.wim.core.contracts.Writer;

public class ConsoleWriter implements Writer {

    public void write(String message) {
        System.out.print(message);
    }

    public void writeLine(String message) {
        System.out.println(message);
    }
}
