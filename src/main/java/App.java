import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld bean =
                (HelloWorld) applicationContext.getBean("helloworld");
        System.out.println(bean.getMessage());
        HelloWorld newBeanHello =
                (HelloWorld) applicationContext.getBean("helloworld");
        CatInterface cat = applicationContext.getBean(CatInterface.class);
        CatInterface otherCat = applicationContext.getBean(CatInterface.class);
        cat.setMeow("Meow-Meow");
        cat.sayMeow();
        cat.printRepeat();
        otherCat.setMeow("Vlad -- Nigga");
        otherCat.sayMeow();
        otherCat.printRepeat();
        System.out.println(bean.equals(newBeanHello));
        System.out.println(cat.equals(otherCat));
    }
}