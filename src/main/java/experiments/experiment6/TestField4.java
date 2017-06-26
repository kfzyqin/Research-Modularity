package experiments.experiment6;

/**
 * Created by zhenyueqin on 25/6/17.
 */
class Animal {
    public void makeVoice() {
        aVoice();
    }

    protected void aVoice() {
        System.out.println("A voice generated");
    }
}

class Cat extends Animal {
    @Override
    protected void aVoice() {
        System.out.println("A cat voice generated");
    }
}

public class TestField4 {
    public static void main(String[] args) {
        Cat aCat = new Cat();
        aCat.makeVoice();
    }
}
