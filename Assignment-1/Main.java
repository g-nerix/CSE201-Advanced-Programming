import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("CoWin Portal initialized....\n" +
                "---------------------------------\n" +
                "1. Add Vaccine\n" +
                "2. Register Hospital\n" +
                "3. Register Citizen\n" +
                "4. Add Slot for Vaccination\n" +
                "5. Book Slot for Vaccination\n" +
                "6. List all slots for a hospital\n" +
                "7. Check Vaccination Status\n" +
                "8. Exit\n" +
                "---------------------------------\n");

    }
}
class Vaccine {
    // Input: Name, Number of total doses required, Gap Between Doses
    // Output: Display the added vaccine details
    String name;
    int reqDoses;
    int gap;
    // add vaccines

    void add(String n, int rD, int g){
        if()
    }

}
class Hospital {
    //    Input: Name, Pincode
    //    Output: Display the added hospital details along with the generated unique hospital
    //    ID (A 6 digit number)
    int ID;
    String name;
    int pincode;
    ArrayList<Slot> slotArr = new ArrayList<>();
    // add vaccines
    // create slot

}
class Citizen {
    //    Input: Name, Age, Unique ID (A 12 digit ID like Aadhar ID)
    //    Output: Display the citizen details and set his/her vaccination status as
    //    "REGISTERED."
    long ID;
    String name;
    int age;
    int status;
    // 0 = unvaccinated
    boolean checkID(long id){
        if (id >= 100000000000L){
            return true;
        }
        return false;
    }

}
class Slot {
    //    Create Slots:
    //    Input: Hospital ID followed by the number of slots that the hospital wants to add. For
    //    each slot, enter the day number and quantity followed by selecting the vaccine for
    //    that slot.
    //    Output: Display the details of the added slot.
    int day;
    int id;

}
class portal {
    ArrayList<Vaccine> vaccineLst = new ArrayList<>();
    void menu(){

        System.out.println("CoWin Portal initialized....\n" +
                "---------------------------------\n" +
                "1. Add Vaccine\n" +
                "2. Register Hospital\n" +
                "3. Register Citizen\n" +
                "4. Add Slot for Vaccination\n" +
                "5. Book Slot for Vaccination\n" +
                "6. List all slots for a hospital\n" +
                "7. Check Vaccination Status\n" +
                "8. Exit\n" +
                "---------------------------------\n");
        int inp = 0;
        Scanner sc = new Scanner(System.in);
        while(inp != 8){
            inp = sc.nextInt();
            switch(inp) {
                case 1:
                    // code block
                    break;
                case 2:
                    // code block
                    break;
                case 3:
                    // code block
                    break;
                case 4:
                    // code block
                    break;
                case 5:
                    // code block
                    break;
                case 6:
                    // code block
                    break;
                case 7:
                    // code bloc
                    break;
                case 8:
                    // code block
                    break;
                default:
                    System.out.println("Invalid selection. Try again.\n");
            }

        }


    }
    void addVaccine(String n, int rD, int g){
        boolean exist(String n){

        }
//            for(int i = 0; i < )
        }
    }



}



