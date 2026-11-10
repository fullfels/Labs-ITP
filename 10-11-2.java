class Bill{
    private int id;
    private String name;
    private int amount;
    private boolean paid;

    public Bill(int id, String name, int amount){
        this.id = id;
        this.name = name;
        this.amount = amount;
        paid = false;
    }
    
    public void markAsPaid(){
        paid = true;
    }
}

class Doctor{
    private int id;
    private String name;

    public Doctor(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void checkPatient(Patient patient){
        System.out.println("Врач осмотрел пациента.");
    }
}

class Receptionist{
    private int id;
    private String name;

    Receptionist(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void giveAppointment(Patient patient){
        patient.setReceptionist(this);
        System.out.println("Пациент записан на приём.");
    }

    public Bill generateBill(Patient patient, int id, 
        String name, int amount){
            Bill bill = new Bill(id, name, amount);
            patient.setBill(bill);
            return bill;

        }
}

class Patient{
    private int id;
    private String name;
    private Bill bill;
    private Doctor doctor;
    private Receptionist receptionist;

    public Patient(int id, String name, Bill bill,
        Doctor doctor, Receptionist receptionist){
            this.id = id;
            this.name = name;
            this.bill = bill;
            this.doctor = doctor;
            this.receptionist = receptionist;
        }
    public void payBill(){
        bill.markAsPaid();
    }

    public void setBill(Bill bill){
        this.bill = bill;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;

    }
}

class HospitalMain{
    public static void main(String[] args){
        Doctor doctor = new Doctor(1, "Ivan");
        Receptionist receptionist  = new Receptionist(1, "Olga");

        Patient patient = new Patient(1, "Anna", null, doctor, receptionist);

        receptionist.giveAppointment(patient);

        Bill bill = receptionist.generateBill(patient, 1, "Консультация", 1500);

        doctor.checkPatient(patient);
        patient.payBill();

        System.out.println("Счёт оплачен.");
    }
}