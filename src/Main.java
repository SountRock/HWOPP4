import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Box<String> strings = new Box<>(); // не должно компилироваться

        Box<Orange> oranges = new Box<>();
        oranges.add(new Orange(1));
        //System.out.println(oranges.getWeight()); // 1
        oranges.add(new Orange(2));
        //System.out.println(oranges.getWeight()); // 3

        /*Box<Apple> apples = new Box<>();
        //apples.add(new Orange(3)); // не должно компилироваться!
        apples.add(new GoldenApple(5)); // это ок

        Box<GoldenApple> goldenApples = new Box<>();
        goldenApples.add(new GoldenApple(5)); // это ок
        //goldenApples.add(new Apple(3)); // не должно компилироваться!

        //oranges.move(apples); // не должно компилироваться!
        goldenApples.move(apples); // это ок
        //apples.move(goldenApples); // не должно компилироваться!*/

        Box<Orange> newOranges = new Box<>();
        oranges.move(newOranges);
        System.out.println(oranges.getWeight()); // 0 после пересыпания
        System.out.println(newOranges.getWeight()); // 3 после пересыпания

        for (Orange o: oranges) { // цикл компилируется, но не запускатся, потому oranges - пустой
            System.out.println(o.getWeight());
        }

        for (Orange o: newOranges) { // цикл компилируется, и запускается
            //Должно вывести 1 2 (или 2 1) - порядок неважен
            System.out.println(o.getWeight());
        }

        /*for (Apple a: apples) { // цикл компилируется, и запускается
            // Должно вывести 5 5
            System.out.println(a.getWeight());
        }*/
    }

    static class Box<T extends Fruit> implements Iterable<T> {
        // Реализовать данный класс!!!
        private List<T> box;

        public Box() {
            box = new LinkedList<>();
        }

        public void add(T fruit){
            box.add(fruit);
        }

        public int getWeight(){
            int length = 0;
            for (T item : box)
                length += item.getWeight();

            return length;
        }

        public List<T> getBox(){
            return box;
        }

        public void move(Box<? super T> box){
            box.getBox().addAll(this.box);
            this.box.clear();
        }

        @Override
        public Iterator<T> iterator() {
            return new BoxIterator<T>(box);
        }
    }

    static class BoxIterator<T> implements Iterator<T>{
        private int cursor;

        private List<T> box;

        public BoxIterator(List<T> box) {
            this.box = new LinkedList<>(box);
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < box.size();
        }

        @Override
        public T next() {
            return box.get(cursor++);
        }
    }

    static class Fruit {
        private final int weight;

        public Fruit(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }

    static class Orange extends Fruit {
        public Orange(int weight) {
            super(weight);
        }
    }

    static class Apple extends Fruit {
        public Apple(int weight) {
            super(weight);
        }
    }

    static class GoldenApple extends Apple {
        public GoldenApple(int weight) {
            super(weight);
        }
    }
}