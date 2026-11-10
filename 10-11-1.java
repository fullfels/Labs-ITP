import java.util.Scanner;

enum Drinks {
    DOBRI_COLA("Dobri Cola", 150),
    DOBRI_LIME("Dobri Lime", 140),
    DOBRI_APELSIN("Dobri Apelsin", 145);

    private final String name;
    private final int price;

    Drinks(String name, int price){
        this.name = name;
        this.price = price;
    }
    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }
}

enum Money{
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    FIVE_HUNDRED(500);

    private final int money;

    Money(int money){
        this.money=money;
    }

    public int getMoney(){
        return money;
    }



}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Drinks[] drinks = Drinks.values();

        System.out.println("Меню напитков:");
        for (int i = 0; i < drinks.length; i++) {
            System.out.println((i + 1) + ". " + drinks[i].getName()
                    + " — " + drinks[i].getPrice() + " руб.");
        }

        int balance = 0;
        while (true) {
            System.out.println("Внесите купюру: 10, 50, 100 или 500 руб."
                    + " Введите 0 для перехода к выбору напитка.");
            // Если ввод закончился, возвращаем уже внесённые деньги.
            if (!scanner.hasNext()) {
                returnMoney(balance);
                return;
            }
            if (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести целое число.");
                scanner.next();
                continue;
            }

            int amount = scanner.nextInt();
            if (amount == 0) {
                break;
            }

            boolean accepted = false;
            for (Money banknote : Money.values()) {
                if (banknote.getMoney() == amount) {
                    accepted = true;
                    break;
                }
            }

            if (!accepted) {
                System.out.println("Такую купюру автомат не принимает.");
            } else if (balance > Integer.MAX_VALUE - amount) {
                System.out.println("Достигнут предел баланса. Купюра возвращена.");
            } else {
                balance += amount;
                System.out.println("Баланс: " + balance + " руб.");
            }
        }

        while (true) {
            System.out.println("Выберите напиток: 1–" + drinks.length
                    + ". Введите 0 для возврата денег.");
            if (!scanner.hasNext()) {
                returnMoney(balance);
                return;
            }
            if (!scanner.hasNextInt()) {
                System.out.println("Нужно ввести целое число.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            if (choice == 0) {
                returnMoney(balance);
                return;
            }
            if (choice < 1 || choice > drinks.length) {
                System.out.println("Такого напитка нет.");
                continue;
            }

            Drinks selected = drinks[choice - 1];
            if (balance < selected.getPrice()) {
                System.out.println("Недостаточно денег. Не хватает "
                        + (selected.getPrice() - balance) + " руб."
                        + " Выберите другой напиток или возврат денег.");
                continue;
            }

            System.out.println("Ваш напиток: " + selected.getName());
            returnMoney(balance - selected.getPrice());
            return; // За один запуск продаём только один напиток.
        }
    }

    static void returnMoney(int amount) {
        System.out.println("Сумма к возврату: " + amount + " руб.");
        Money[] banknotes = Money.values();
        int remaining = amount;

        // Номиналы в enum расположены по возрастанию: идём от крупных к мелким.
        for (int i = banknotes.length - 1; i >= 0; i--) {
            int denomination = banknotes[i].getMoney();
            int count = remaining / denomination;
            if (count > 0) {
                System.out.println(denomination + " руб. × " + count);
                remaining %= denomination;
            }
        }

        System.out.println("Возвращено: " + (amount - remaining) + " руб.");
        if (remaining > 0) {
            System.out.println("Остаток " + remaining
                    + " руб. выдать невозможно: автомат выдаёт только купюры."
                    + " Сумма возврата округлена вниз.");
        }
    }
}
