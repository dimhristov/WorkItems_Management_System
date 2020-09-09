package com.wim;

import com.wim.core.EngineImpl;

public class Startup {

    public static void main(String[] args) {
        EngineImpl engine = new EngineImpl();
        engine.start();
    }
}
