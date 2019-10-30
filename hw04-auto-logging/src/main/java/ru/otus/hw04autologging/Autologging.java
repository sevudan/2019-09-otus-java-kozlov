package ru.otus.hw04autologging;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

class AutoLogging {

    final static Class ANNOTATE_LOG = Log.class;

    private static boolean checkLogged(Method m){
        return m.isAnnotationPresent(ANNOTATE_LOG);
    }

    public static TestClassIf loggingMethod(Class<?> sClass) {

        for (Method m: sClass.getMethods()) {
            if (checkLogged(m)){
                InvocationHandler handler = new ProxyClass(new TestClass());

                return (TestClassIf) Proxy.newProxyInstance(AutoLogging.class.getClassLoader(),
                        sClass.getInterfaces(),
                        handler);
            }
        }
        return new TestClass();
    }


    static class ProxyClass implements InvocationHandler {

        TestClassIf myClass;

        private final Set<String> loggedMethods = new HashSet<>();

        public ProxyClass(TestClassIf myClass) {
            this.myClass = myClass;

            for (Method m: this.myClass.getClass().getMethods()) {
                if (checkLogged(m)) loggedMethods.add(m.getName());
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            StringBuilder sb = new StringBuilder();

            if (loggedMethods.contains(method.getName())) {
                sb.append("executed method: ").append(method.getName()).append(", param: ");
                IntStream.range(0, args.length).forEach(i -> sb.append(args[i]).append(' '));
                System.out.println(sb.toString());
            }
            return method.invoke(myClass, args);
        }
    }
}