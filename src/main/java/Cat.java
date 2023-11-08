
@Profiling
//@Qualifier("cat")
public class Cat implements CatInterface {

    @InjectRandomInt(min = 100, max = 100000)
    private int repeat;

    private String meow;

    public Cat() {
    }

    public String getMeow() {
        return meow;
    }

    public void sayMeow() {
        for (int i = 1; i <= repeat; i++) {
            System.out.print(meow);
        }
    }

    public void printRepeat() {
        System.out.println(repeat);
    }



    public void setMeow(String meow) {
        this.meow = meow;
    }
}
