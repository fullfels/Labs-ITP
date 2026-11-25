import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class Lab13 {
    public static void main(String[] args) {
        System.out.println("Exercise 1");
        Library<Book> books = new Library<>();
        books.add(new Book("Effective Java"));
        System.out.println(books.get(0));

        MediaLibrary media = new MediaLibrary();
        media.add(books.get(0));
        media.add(new Video("Java Generics"));
        media.add(new Newspaper("Campus News"));
        System.out.println(media.get(1));
        System.out.println(media.get(2));

        System.out.println("\nExercise 2");
        Set<Animal> animals = new LinkedHashSet<>();
        animals.add(new Cat("Luna", 3));
        animals.add(new Dog("Rex", 5));

        Set<Cat> cats = new LinkedHashSet<>();
        cats.add(new Cat("Luna", 3));
        cats.add(new Cat("Milo", 2));

        Set<Dog> dogs = new LinkedHashSet<>();
        dogs.add(new Dog("Rex", 5));
        dogs.add(new Dog("Max", 4));

        Main.displayAnimals(cats);
        Main.makeTalk(dogs);
        Main.addAnimals(animals, cats);
        Main.addAnimals(animals, dogs);
        Main.displayAnimals(animals);
        Main.makeTalk(animals);

        System.out.println("\nExercise 3");
        Owner owner = new Owner("Anna", "Ivanova", 25);
        VeterinaryClinic clinic = new VeterinaryClinic();
        Map<Integer, Cat> firstVisit = Map.of(1, new Cat(1, "Luna", owner, 3));
        Map<Integer, Animal> secondVisit = new LinkedHashMap<>();
        secondVisit.put(1, new Snake(1, "Noodle", owner, 2));
        secondVisit.put(2, new Snake(2, "Sasha", owner, 4));
        secondVisit.put(3, new Rabbit(3, "Luna", owner, 12));

        clinic.addPets(firstVisit);
        clinic.addPets(secondVisit);
        clinic.displayPets();
    }

    // Exercise 1
    interface Media {
        String title();
    }

    record Book(String title) implements Media {}
    record Video(String title) implements Media {}
    record Newspaper(String title) implements Media {}

    static class Library<T extends Media> {
        private final List<T> items = new ArrayList<>();

        public void add(T item) {
            items.add(item);
        }

        public T get(int index) {
            return items.get(index);
        }
    }

    static class MediaLibrary {
        private Media[] items = new Media[4];
        private int size;

        public void add(Media item) {
            if (size == items.length) {
                items = java.util.Arrays.copyOf(items, items.length * 2);
            }
            items[size++] = item;
        }

        public Media get(int index) {
            return items[Objects.checkIndex(index, size)];
        }
    }

    // Exercise 2
    static class Main {
        static void displayAnimals(Set<? extends Animal> animals) {
            animals.forEach(System.out::println);
        }

        static void makeTalk(Set<? extends Animal> animals) {
            for (Animal animal : animals) {
                System.out.println(animal.nickname + ": " + animal.voice());
            }
        }

        static <T extends Animal> void addAnimals(Set<? super T> destination,
                                                  Set<? extends T> source) {
            destination.addAll(source);
        }
    }

    record Owner(String name, String surname, int age) {}

    static class Animal {
        private final Integer id;
        private final String nickname;
        private final Owner owner;

        Animal(String nickname) {
            this(null, nickname, null);
        }

        Animal(Integer id, String nickname, Owner owner) {
            this.id = id;
            this.nickname = Objects.requireNonNull(nickname);
            this.owner = owner;
        }

        public String voice() {
            return "...";
        }

        @Override
        public final boolean equals(Object other) {
            if (!(other instanceof Animal animal)) {
                return false;
            }
            if (id != null || animal.id != null) {
                return id != null && id.equals(animal.id);
            }
            return getClass() == animal.getClass() && nickname.equals(animal.nickname);
        }

        @Override
        public final int hashCode() {
            return id != null ? id.hashCode() : Objects.hash(getClass(), nickname);
        }

        @Override
        public String toString() {
            String description = getClass().getSimpleName() + " " + nickname;
            return id == null ? description : "#" + id + " " + description + ", " + owner;
        }
    }

    static final class Cat extends Animal {
        private final int purLoudness;

        Cat(String nickname, int purLoudness) {
            super(nickname);
            this.purLoudness = purLoudness;
        }

        Cat(int id, String nickname, Owner owner, int purLoudness) {
            super(id, nickname, owner);
            this.purLoudness = purLoudness;
        }

        @Override
        public String voice() {
            return "Purr (loudness " + purLoudness + ")";
        }
    }

    static final class Dog extends Animal {
        private final int barkingLoudness;

        Dog(String nickname, int barkingLoudness) {
            super(nickname);
            this.barkingLoudness = barkingLoudness;
        }

        @Override
        public String voice() {
            return "Woof (loudness " + barkingLoudness + ")";
        }
    }

    // Exercise 3
    static final class Snake extends Animal {
        private final int venomDanger;

        Snake(int id, String nickname, Owner owner, int venomDanger) {
            super(id, nickname, owner);
            this.venomDanger = venomDanger;
        }

        @Override
        public String toString() {
            return super.toString() + ", venom danger " + venomDanger;
        }
    }

    static final class Rabbit extends Animal {
        private final int earLength;

        Rabbit(int id, String nickname, Owner owner, int earLength) {
            super(id, nickname, owner);
            this.earLength = earLength;
        }

        @Override
        public String toString() {
            return super.toString() + ", ear length " + earLength + " cm";
        }
    }

    static class VeterinaryClinic {
        private final Map<Integer, Animal> pets = new LinkedHashMap<>();

        public void displayPets() {
            pets.values().forEach(System.out::println);
        }

        public void addPets(Map<Integer, ? extends Animal> incoming) {
            for (var entry : incoming.entrySet()) {
                Animal pet = entry.getValue();
                if (pet.id == null || !pet.id.equals(entry.getKey()) || pet.owner == null) {
                    throw new IllegalArgumentException("Each pet needs an owner and a matching ID");
                }
                if (!(pet instanceof Cat || pet instanceof Snake || pet instanceof Rabbit)) {
                    throw new IllegalArgumentException("Unsupported pet species");
                }
            }
            for (var entry : incoming.entrySet()) {
                if (pets.putIfAbsent(entry.getKey(), entry.getValue()) != null) {
                    System.out.println("Duplicate ID " + entry.getKey() + ": existing pet kept");
                }
            }
        }
    }

    // Exercise 4: generic LIFO API
    interface Stack<T> {
        void push(T element);
        T pop();
        boolean isEmpty();
    }

    // Exercise 5: generic dictionary API
    interface Dictionary<K, V> {
        V get(K key);
        V put(K key, V value);
        boolean isEmpty();
        Set<K> keys();
        Collection<V> values();
    }
}
