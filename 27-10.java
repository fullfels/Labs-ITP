//Exercise 1
class Exercise1 {
    public static void main(String[] args) {
        System.out.println("Exercise 1");
        Animal[] animals = {
            new Cow("Burenka", 1.5, 500, "brown"),
            new Cat("Murka", 0.25, 4, "black"),
            new Dog("Rex", 0.6, 25, "white")
        };

        for (Animal animal : animals) {
            animal.eat();
            animal.sleep();
            animal.makeSound();
            System.out.println();
        }
    }
}

class Animal {
    protected final String name;
    private final double height;
    private final double weight;
    private final String color;

    public Animal(String name, double height, double weight, String color) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.color = color;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }

    public void makeSound() {
        System.out.println(name + " makes a sound.");
    }

}

class Cow extends Animal {
    public Cow(String name, double height, double weight, String color) {
        super(name, height, weight, color);
    }

    @Override
    public void eat() {
        System.out.println(name + " eats grass.");
    }

    @Override
    public void makeSound() {
        System.out.println(name + ": Moo!");
    }
}

class Cat extends Animal {
    public Cat(String name, double height, double weight, String color) {
        super(name, height, weight, color);
    }

    @Override
    public void eat() {
        System.out.println(name + " eats fish.");
    }

    @Override
    public void makeSound() {
        System.out.println(name + ": Meow!");
    }
}

class Dog extends Animal {
    public Dog(String name, double height, double weight, String color) {
        super(name, height, weight, color);
    }

    @Override
    public void eat() {
        System.out.println(name + " eats meat.");
    }

    @Override
    public void makeSound() {
        System.out.println(name + ": Woof!");
    }
}



//Exercise 2
class Exercise2 {
    public static void main(String[] args) {
        System.out.println("Exercise 2");
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 4, 5),
            new Square(4),
            new Ellipse(5, 3)
        };

        for (Shape shape : shapes) {
            System.out.printf("%s: area = %.2f, perimeter = %.2f%n",
                    shape.getClass().getSimpleName(), shape.area(), shape.perimeter());
        }
    }
}

abstract class Shape {
    public abstract double area();

    public abstract double perimeter();

}

class Ellipse extends Shape {
    protected final double semiAxisA;
    protected final double semiAxisB;

    public Ellipse(double semiAxisA, double semiAxisB) {
        this.semiAxisA = semiAxisA;
        this.semiAxisB = semiAxisB;
    }

    @Override
    public double area() {
        return Math.PI * semiAxisA * semiAxisB;
    }

    @Override
    public double perimeter() {
        double h = Math.pow((semiAxisA - semiAxisB) / (semiAxisA + semiAxisB), 2);
        return Math.PI * (semiAxisA + semiAxisB)
                * (1 + 3 * h / (10 + Math.sqrt(4 - 3 * h)));
    }
}

class Circle extends Ellipse {
    public Circle(double radius) {
        super(radius, radius);
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * semiAxisA;
    }
}

class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }
}

class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }
}

class Triangle extends Shape {
    private final double a;
    private final double b;
    private final double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;

    }

    @Override
    public double area() {
        double s = perimeter() / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double perimeter() {
        return a + b + c;
    }
}
