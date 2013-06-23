package org.minetweak.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ThreadedEventExecutor implements EventExecutor {
    @Override
    public void execute(Listener listener, Event event) {
        Thread thread = new Thread(new ExecutorThread(listener, event));
        thread.start();
    }

    class ExecutorThread implements Runnable {
        private Listener listener;
        private Event event;

        public ExecutorThread(Listener listener, Event event) {
            this.listener = listener;
            this.event = event;
        }

        @Override
        public void run() {
            Class<? extends Event> type = null;
            for (Class<? extends Event> clazz : EventInfo.eventTypes) {
                if (clazz.isInstance(event)) {
                    type = clazz;
                    break;
                }
            }
            if (type==null) {
                return;
            }
            String methodName = "on" + type.getSimpleName().replace("Event", "");
            try {
                Method method = listener.getClass().getMethod(methodName, type);
                method.invoke(event);
            } catch (NoSuchMethodException ignored) {

            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
