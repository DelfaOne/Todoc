package com.example.todoc.utils;

import java.util.concurrent.Executor;

public class TestExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
