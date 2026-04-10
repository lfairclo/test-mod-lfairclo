package com.testmod.util;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {
    private static final List<ScheduledTask> TASKS = new ArrayList<>();

    // A simple internal class to hold the task and its timer
    private static class ScheduledTask {
        Runnable action;
        int remainingTicks;

        ScheduledTask(Runnable action, int delay) {
            this.action = action;
            this.remainingTicks = delay;
        }
    }

    // Call this to queue a function
    public static void schedule(Runnable action, int delayInTicks) {
        TASKS.add(new ScheduledTask(action, delayInTicks));
    }

    // This is the "Engine" that must be called every tick
    public static void tick() {
        if (TASKS.isEmpty()) return;

        // Use removeIf to countdown and execute tasks safely
        TASKS.removeIf(task -> {
            task.remainingTicks--;
            if (task.remainingTicks <= 0) {
                task.action.run();
                return true; // Removes it from the list
            }
            return false;
        });
    }
}