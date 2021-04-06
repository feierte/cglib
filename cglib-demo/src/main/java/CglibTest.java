import demo.bean.User;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Jie Zhao
 * @date 2021/4/5 15:56
 */
public class CglibTest {

    public static void main(String[] args) {
        System.out.println("Hello Cglib!");


        // 这样cglib会将动态生成的每个class都输出到文件中，然后我们可以通过decomp进行反编译查看源码。
        //String output = "D:\\Program Files\\JavaTools\\IdeaProjects\\cglib\\cglib-demo\\classes";
        String output = "cglib-demo/classes";
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, output);

        final User user = new User("张三", 18);

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(User.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                System.out.println("代理之前");
                Object result = method.invoke(user, args);
                System.out.println("代理之后");
                return result;
            }
        });

        User userProxy = (User) enhancer.create();
        userProxy.eat();
    }
}
