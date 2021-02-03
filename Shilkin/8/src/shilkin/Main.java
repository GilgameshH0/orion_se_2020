package shilkin;

import shilkin.employees.Trainee;
import shilkin.employees.Tutor;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Tutor andrey = new Tutor("Андрей", "Тарасов", 35, 15, "12345678andrey");
        Trainee oleg = new Trainee("Олег", "Шилкин", 21, "oleg12", "Социальная сеть");
        Utils utils = new Utils();
        String xmlAndrey = utils.toSerializeObject(andrey);
        System.out.println(xmlAndrey);
        System.out.println(utils.toDeserializeObject(xmlAndrey, Tutor.class));
        String xmlOleg = utils.toSerializeObject(oleg);
        System.out.println(xmlOleg);
        Trainee trainee = utils.toDeserializeObject(xmlOleg, Trainee.class);
        System.out.println(trainee.toString());
    }
}
