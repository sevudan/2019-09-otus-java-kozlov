package ru.otus.hw04autologging;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class AutoLogging {

    final static Class ANNOTATE_LOG = Log.class;

    private static boolean isLogAnnotationPresent(Method method){
        return method.isAnnotationPresent(ANNOTATE_LOG);
    }

    static <T> T loggingMethod(Class<T> clazz)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        Constructor<T> constructor = clazz.getConstructor();

        for (Method method: clazz.getMethods()) {
            if (isLogAnnotationPresent(method)) {
                InvocationHandler handler = new LoggingInvocationHandler(constructor.newInstance());

                return (T) Proxy.newProxyInstance(AutoLogging.class.getClassLoader(),
                        clazz.getInterfaces(), handler);
            }
        }
        return constructor.newInstance();
    }

    static class LoggingInvocationHandler<T> implements InvocationHandler {

        private final T underlying;

        private final Set<String> loggedMethods = new HashSet<>();

        public LoggingInvocationHandler(T underlying) {
            this.underlying = underlying;
            for (Method method: this.underlying.getClass().getMethods()) {
                if (isLogAnnotationPresent(method)) loggedMethods.add(method.getName());
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws IllegalAccessException, InvocationTargetException{

            StringBuilder sb = new StringBuilder();

            if (loggedMethods.contains(method.getName())) {
                sb.append("executed method: ").append(method.getName()).append(", param: ");
                IntStream.range(0, args.length).forEach(i -> sb.append(args[i]).append(' '));
                System.out.println(sb.toString());
            }
            return method.invoke(underlying, args);
        }
    }
}