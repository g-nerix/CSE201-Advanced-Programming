package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        portal p = new portal();
        p.menu();

    }
}
class Vaccine {

    static int count = 0;

    String name;
    int reqDoses;
    int gap;
    int id;

    Vaccine(String name, int rd, int gap){
        this.name = name;
        this.reqDoses = rd;
        this.gap = gap;
        this.id = count++;
    }

}
class Hospital {
    //    Input: Name, Pincode
    //    Output: Display the added hospital details along with the generated unique hospital
    //    ID (A 6 digit number)

    ArrayList<Slot> slotArr = new ArrayList<>();

    static int count = 100000;

    int ID;
    String name;
    int pincode;

    Hospital(String name, int pincode){
        this.ID = count++;
        this.name = name;
        this.pincode = pincode;
        System.out.println("\nHospital Name: "+name+", PinCode: "+pincode+", Unique ID: "+this.ID);
    }
}

class Citizen {
    //    Input: Name, Age, Unique ID (A 12 digit ID like Aadhar ID)
    //    Output: Display the citizen details and set his/her vaccination status as "REGISTERED."

    long ID;
    String name;
    int age;
    String status;
    int doseGiven;
    int dueDate;
    String vaccine;

    Citizen(long ID, String name, int age){
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.status = "REGISTERED";
        this.doseGiven = 0;
        this.dueDate = -1;
        this.vaccine = "";
    }
}

class Slot {
    //    Create Slots:
    //    Input: Hospital ID followed by the number of slots that the hospital wants to add. For
    //    each slot, enter the day number and quantity followed by selecting the vaccine for
    //    that slot.
    //    Output: Display the details of the added slot.

    int day;
    int hospitalID;
    int vaccineID;
    int qty;

    Slot(int day, int hospitalID,int vaccineID, int qty){
        this.day = day;
        this.hospitalID = hospitalID;
        this.vaccineID = vaccineID;
        this.qty = qty;
    }
}

class portal {

    ArrayList<Vaccine> vaccineLst = new ArrayList<>();
    ArrayList<Hospital> hospitalLst = new ArrayList<>();
    ArrayList<Citizen> citizenLst = new ArrayList<>();

    void menu(){
        System.out.println("CoWin Portal initialized....");

        int inp = 0;

        Scanner sc = new Scanner(System.in);

        while(inp != 8){

            System.out.println("\n---------------------------------\n" +
                    "1. Add Vaccine\n" +
                    "2. Register Hospital\n" +
                    "3. Register Citizen\n" +
                    "4. Add Slot for Vaccination\n" +
                    "5. Book Slot for Vaccination\n" +
                    "6. List all slots for a hospital\n" +
                    "7. Check Vaccination Status\n" +
                    "8. Exit\n" +
                    "---------------------------------");

            inp = sc.nextInt();

            switch(inp) {

                case 1:
                    // ADD VACCINE

                    System.out.print("\nVaccine Name : ");
                    String vName = sc.next();

                    System.out.print("\nNumber of Doses : ");
                    int nDose = sc.nextInt();

                    System.out.print("\nGap between Doses : ");
                    int gap = sc.nextInt();

                    addVaccine(vName,nDose,gap);

                    break;

                case 2:
                    // Register Hospital

                    System.out.print("\nHospital Name: ");
                    String hospitalName = sc.next();

                    System.out.print("\nPIN Code: ");
                    int pin = sc.nextInt();

                    registerHospital(hospitalName,pin);

                    break;

                case 3:
                    // Register Citizen

                    System.out.print("\nCitizen Name: ");
                    String name = sc.next();

                    System.out.print("\nAge: ");
                    int age = sc.nextInt();

                    System.out.print("\nUnique ID: ");
                    long id = sc.nextLong();

                    System.out.println("\nCitizen Name: "+name+", Age: "+age+", Unique ID: "+id);

                    registerCitizen(name,age,id);

                    break;

                case 4:
                    // Add Slot for Vaccination

                    System.out.print("\nEnter Hospital ID: ");
                    int hID = sc.nextInt();

                    boolean hflag = false;
                    for (int i = 0; i < hospitalLst.size(); i++){
                        if (hID == hospitalLst.get(i).ID){
                            hflag = true;
                        }
                    }
                    if (hflag == false){
                        System.out.print("\nHospital ID not found");
                        break;
                    }

                    System.out.print("\nEnter number of Slots to be added: ");
                    int n = sc.nextInt();

                    while (n>0){
                        n--;

                        System.out.print("\nEnter Day Number: ");
                        int day = sc.nextInt();

                        System.out.print("\nEnter Quantity: ");
                        int qty = sc.nextInt();

                        System.out.print("\nSelect Vaccine");
                        for (int i = 0; i < vaccineLst.size(); i++){
                            System.out.print("\n->"+vaccineLst.get(i).id+". "+vaccineLst.get(i).name);
                        }
                        System.out.println();
                        int vID = sc.nextInt();

                        if (vID>vaccineLst.size()){
                            System.out.println("\nINVALID Vaccine code !!");
                        }
                        else{
                            addSlot(hID,day,qty,vID);

                            System.out.println("\nSlot added by Hospital "+hID+" for Day: "+day+", Available Quantity: "+qty+" of Vaccine "+vaccineLst.get(vID).name);
                        }
                    }

                    break;

                case 5:
                    // Book slots for vaccination

                    System.out.print("Enter patient Unique ID: ");
                    long pID = sc.nextLong();

                    bookSlot(pID);

                    break;

                case 6:
                    // List all slots for a hospital

                    System.out.print("Enter Hospital Id: ");
                    int idH = sc.nextInt();
                    listSlot(idH);
                    break;
                case 7:
                    // Check Vaccination status

                    System.out.print("Enter Patient ID: ");
                    long personID = sc.nextLong();
                    checkVaccineStatus(personID);
                    break;
                case 8:
                    // EXIT

                    System.out.print("\n{End of Test Case}\nEXITING.....");
                    break;
                default:
                    System.out.println("Invalid selection. Try again.\n");
            }
        }
    }

    boolean exist(String n){
        // Find if vaccine name exist in the vaccine List

        for (int i = 0; i < vaccineLst.size();i++) {
            if (n.equals(vaccineLst.get(i).name)){
                return true;
            }
        }
        return false;
    }

    boolean isValidPincode(int pincode){
        // Find if the PIN Code is a 6 digit number

        if ((pincode < 100000)||(pincode > 999999)){
            System.out.print("\nInvalid PIN code !!");
            return false;
        }
        return true;
    }

    boolean isHospitalRegistered(String name,int pincode){
        // Check if the hospital is already registered on the portal

        for (int i = 0; i < hospitalLst.size();i++) {
            if (name.equals(hospitalLst.get(i).name)){
                if (hospitalLst.get(i).pincode == pincode){
                    System.out.print("\nHospital already registered !!");
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkCitizenID(long n){
        // Check if the citizen ID is a unique 12 digit Number

        if (n > 999999999999L){
            System.out.print("\nInvalid Citizen ID ");
            return false;
        }
        else if (n < 100000000000L){
            System.out.print("\nInvalid Citizen ID ");
            return false;
        }

        for (int i = 0; i < citizenLst.size();i++) {
            if (n == (citizenLst.get(i).ID)){
                System.out.print("\nCitizen ID already exists");
                return false;
            }
        }
        return true;
    }

    void addVaccine(String n, int rD, int g){
        // ADD VACCINE
        // Input: Name, Number of total doses required, Gap Between Doses
        // Output: Display the added vaccine details

        if (!exist(n)){
            vaccineLst.add(new Vaccine(n,rD,g));
            System.out.println("\nVaccine Name: "+n+", Number of Doses: "+rD+", Gap Between Doses: "+g);
        }
        else{
            System.out.println("Vaccine already exists !!");
        }
    }

    void registerHospital(String name, int pincode){
        // Register Hospital:
        // Input: Name, Pincode
        // Output: Display the added hospital details along with the generated unique hospital
        // ID (A 6 digit number)

        if(!isHospitalRegistered(name, pincode)){
            if(isValidPincode(pincode)){
                hospitalLst.add(new Hospital(name, pincode));
            }
        }
    }

    void registerCitizen(String name,int age,long ID){
        // Register Citizen:
        // Input: Name, Age, Unique ID (A 12 digit numeric ID like Aadhar ID)
        // Output: Display the citizen details and set his/her vaccination status as
        // "REGISTERED."

        if(checkCitizenID(ID)){
            if (age < 18){
                System.out.println("[Under Age] Minimum Age for Vaccination is 18 years !!");
                return;
            }
            citizenLst.add(new Citizen(ID, name, age));
        }
    }

    void addSlot(int hospitalID, int day, int qty, int vaccineID){
        // Add Slots:
        // Input: Hospital ID followed by the number of slots that the hospital wants to add. For
        // each slot, enter the day number and quantity followed by selecting the vaccine for
        // that slot.
        // Output: Display the details of the added slot.

        Hospital h = null;

        for (int i = 0; i < hospitalLst.size(); i++){
            if (hospitalID == hospitalLst.get(i).ID){
                h = hospitalLst.get(i);
            }
        }
        if (h == null){
            System.out.print("\nHospital ID not found");
            return;
        }
        h.slotArr.add(new Slot(day,hospitalID,vaccineID,qty));
    }

    void bookSlot(long id){
        // Book a Slot:
        // Input: Unique ID of the citizen followed by 2 options to search: {By Pincode, By
        // Vaccine}. A successful search should show a list of possible hospitals. Upon selecting
        // the chosen hospital, their available slots must be reflected, and a chosen slot must be
        // booked.
        // Output: Display the citizen vaccinated along with the vaccine. Change the status of
        // the citizen to PARTIALLY VACCINATED/FULLY VACCINATED accordingly.

        Scanner sc = new Scanner(System.in);

        boolean found = false;
        int option = 0;

        for (int i = 0; i < citizenLst.size(); i++){
            if (citizenLst.get(i).ID == id){

                found = true;

                if(citizenLst.get(i).status.equals("FULLY VACCINATED")){
                    System.out.print("\nAlready vaccinated !!");
                    return;
                }
                else{
                    System.out.println("\n1. Search by PIN Code\n2. Search by Vaccine\nChoose from the above options [1/2]: ");

                    option = sc.nextInt();

                    if (option == 1){

                        System.out.print("\nEnter PIN Code : ");

                        int pinCode = sc.nextInt();

                        bookSlotByPincode(id,pinCode);

                        return;
                    }
                    else if (option == 2){

                        System.out.print("Enter Vaccine name : ");

                        String name = sc.next();

                        for (int k = 0; k < vaccineLst.size(); k++){
                            if (vaccineLst.get(k).name.equals(name)){
                                bookSlotByVaccine(id,k);
                                return;
                            }
                        }
                        System.out.println("Vaccine Not in Database !!");
                        return;
                    }
                }
            }
        }
        if (!found){
            System.out.println("Citizen not registered !!");
            return;
        }
    }

    void bookSlotByVaccine(long id,int code){
        // Book Slot By Vaccine Name
        // Input : Citizen ID, Vaccine ID
        // Output : Prints the name and the vaccine administered to the given person.

        Citizen c = null;
        Hospital h = null;

        ArrayList<Integer> slotIndex = new ArrayList<>();
        ArrayList<Integer> hArr = new ArrayList<>();

        for(int i = 0; i < hospitalLst.size(); i++){
            for(int j = 0; j < hospitalLst.get(i).slotArr.size();j++){
                if(hospitalLst.get(i).slotArr.get(j).vaccineID == code){
                    if (hospitalLst.get(i).slotArr.get(j).qty>0){

                        System.out.print("\n"+hospitalLst.get(i).ID+"   "+hospitalLst.get(i).name);
                        hArr.add(hospitalLst.get(i).ID);
                        break;

                    }
                }
            }
        }

        for (int i = 0; i < citizenLst.size(); i++){
            if (citizenLst.get(i).ID == id){
               c =  citizenLst.get(i);
               break;
            }
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter Hospital id: ");

        int hID = sc.nextInt();

        boolean validHospitalFlag = false;

        for (int i = 0; i < hArr.size(); i++){
            if (hID == hArr.get(i)){

                validHospitalFlag = true;

            }
        }

        if (!validHospitalFlag){

            System.out.println("Hospital id not in the above Hospital id list !!");
            return;

        }

        boolean flag = false;
        boolean availableSlot = false;

        for (int i = 0; i < hospitalLst.size(); i++){
            if (hID == hospitalLst.get(i).ID){

                h = hospitalLst.get(i);
                flag = true;

                for (int j = 0; j < h.slotArr.size(); j++){
                    if (h.slotArr.get(j).day >= c.dueDate){
                        if (h.slotArr.get(j).vaccineID == code){

                            availableSlot = true;

                            System.out.println("\n"+j+"-> Day: "+h.slotArr.get(j).day+" Vaccine: "+
                                    vaccineLst.get(h.slotArr.get(j).vaccineID).name+" Available Quantity: "+
                                    h.slotArr.get(j).qty);

                            slotIndex.add(j);

                        }
                    }
                }
            }
        }

        if (availableSlot == false){
            System.out.println("No Slot available");
            return;
        }

        if (flag == false){
            System.out.println("\nHospital with given ID not found !!");
            return;
        }

        System.out.print("\nChoose Slot: ");

        int slot = sc.nextInt();
        boolean indexFlag = false;

        for (int i = 0; i < slotIndex.size(); i++) {
            if (slot == slotIndex.get(i)) {
                indexFlag = true;
            }
        }

        if (indexFlag == false) {
            System.out.print("\nInvalid Slot Number !!");
            return;
        }

        if (c.status.equals("PARTIALLY VACCINATED")){
            if (c.vaccine != vaccineLst.get(code).name){
                System.out.print("\nCan't book slot for different vaccine ["+c.vaccine+" allowed only]");
                return;
            }
        }

        if (c.dueDate<=h.slotArr.get(slot).day){

            h.slotArr.get(slot).qty--;

            if (c.dueDate == -1) {
                c.vaccine = vaccineLst.get(code).name;
            }

            c.dueDate = h.slotArr.get(slot).day+vaccineLst.get(code).gap;
            c.doseGiven++;

            if (c.doseGiven < vaccineLst.get(code).reqDoses) {
                c.status = "PARTIALLY VACCINATED";
            }
            else if (c.doseGiven == vaccineLst.get(code).reqDoses) {
                c.status = "FULLY VACCINATED";
            }
            else
                System.out.println("\nERROR: exceeding max doses...");

            System.out.println(c.name+" vaccinated with "+c.vaccine);

        }
        else {
            System.out.println("Can't register slot before due date !!");
        }

        if (h.slotArr.get(slot).qty == 0) {
            h.slotArr.remove(h.slotArr.get(slot));
        }
    }

    void bookSlotByPincode(long id,int pin){
        // Book Slot by PIN Code
        // Input : Citizen Id, PIN Code
        // Output : Print the name and the vaccine administered to the given person.

        Scanner sc = new Scanner(System.in);

        ArrayList<Integer> hArr = new ArrayList<>();
        boolean flag1 = false;

        for (int i = 0; i < hospitalLst.size(); i++){
            if (hospitalLst.get(i).pincode == pin){

                flag1 = true;

                System.out.print("\n"+hospitalLst.get(i).ID+"   "+hospitalLst.get(i).name);

                hArr.add(hospitalLst.get(i).ID);

            }
        }

        if (flag1 == false){
            System.out.print("\nNo hospital with PIN Code "+pin);
            return;
        }

        System.out.print("\nEnter hospital id: ");

        int hID = sc.nextInt();
        boolean validHospitalFlag = false;

        for (int i = 0; i < hArr.size(); i++){
            if (hID == hArr.get(i)){
                validHospitalFlag = true;
            }
        }

        if (!validHospitalFlag){
            System.out.println("Hospital id not in the above Hospital id list !!");
            return;
        }

        boolean flag = false;
        boolean availableSlot = false;
        Hospital h = null;
        Citizen c = null;

        for (int i = 0; i < citizenLst.size(); i++){
            if (citizenLst.get(i).ID == id){
                c =  citizenLst.get(i);
                break;
            }
        }

        ArrayList<Integer> slotIndex = new ArrayList<>();

        for (int i = 0; i < hospitalLst.size(); i++){
            if (hID == hospitalLst.get(i).ID){
                h = hospitalLst.get(i);
                flag = true;
                break;
            }
        }

        if (flag == false){
            System.out.println("\nHospital with given ID not found !!");
            return;
        }

        for (int j = 0; j < h.slotArr.size(); j++){
            if (h.slotArr.get(j).day >= c.dueDate){

                availableSlot = true;

                System.out.println("\n"+j+"-> Day: "+h.slotArr.get(j).day+" Vaccine: "+
                        vaccineLst.get(h.slotArr.get(j).vaccineID).name+" Available Quantity: "+
                        h.slotArr.get(j).qty);

                slotIndex.add(j);

            }
        }

        if (availableSlot == false){
            System.out.println("No Slot available");
            return;
        }

        System.out.print("\nChoose Slot: ");

        int slot = sc.nextInt();
        boolean indexFlag = false;

        for (int i = 0; i < slotIndex.size(); i++) {
            if (slot == slotIndex.get(i)) {
                indexFlag = true;
            }
        }

        if (indexFlag == false) {
            System.out.print("\nInvalid Slot Number !!");
            return;
        }

        if (c.status.equals("PARTIALLY VACCINATED")){
            if (c.vaccine != vaccineLst.get(h.slotArr.get(slot).vaccineID).name){
                System.out.print("\nCan't book slot for different vaccine ["+c.vaccine+" allowed only]");
                return;
            }
        }

        int code = h.slotArr.get(slot).vaccineID;

        if (c.dueDate<=h.slotArr.get(slot).day){

            h.slotArr.get(slot).qty--;

            if (c.dueDate == -1) {
                c.vaccine = vaccineLst.get(code).name;
            }

            c.dueDate = h.slotArr.get(slot).day+vaccineLst.get(code).gap;
            c.doseGiven++;

            if (c.doseGiven < vaccineLst.get(code).reqDoses) {
                c.status = "PARTIALLY VACCINATED";
            }
            else if (c.doseGiven == vaccineLst.get(code).reqDoses) {
                c.status = "FULLY VACCINATED";
            }
            else
                System.out.println("\nERROR: exceeding max doses...");

            System.out.println(c.name+" vaccinated with "+c.vaccine);
        }
        else {
            System.out.println("Can't register slot before due date !!");
        }

        if (h.slotArr.get(slot).qty == 0) {
            h.slotArr.remove(h.slotArr.get(slot));
        }
    }

    void listSlot(int hID){
        // Slots available with a hospital
        // Input: Hospital ID
        // Output: List all slots for the chosen hospital

        boolean flag = false;

        for (int i = 0; i < hospitalLst.size(); i++){
            if (hID == hospitalLst.get(i).ID){
                for (int j = 0; j < hospitalLst.get(i).slotArr.size(); j++){

                    System.out.println("\nDay: "+hospitalLst.get(i).slotArr.get(j).day+" Vaccine: "+
                    vaccineLst.get(hospitalLst.get(i).slotArr.get(j).vaccineID).name+" Available Quantity: "+
                    hospitalLst.get(i).slotArr.get(j).qty);

                    flag = true;

                }
            }
        }

        if (flag == false){
            System.out.println("\nHospital with given ID not found !!");
        }
    }

    void checkVaccineStatus(long id){
        // Check vaccination status:
        // Input: Citizen inputs his/her Unique ID to check current status
        // Output: Display the current vaccination status: REGISTERED/PARTIALLY
        // VACCINATED/FULLY VACCINATED along with the vaccine administered, number of
        // doses given, and the due date of next vaccination (in case of partial vaccination).

        Citizen c = null;
        boolean isCitizenFound = false;

        for (int i = 0; i < citizenLst.size(); i++){
            if (id == citizenLst.get(i).ID){
                c = citizenLst.get(i);
                isCitizenFound = true;
                break;
            }
        }

        if (!isCitizenFound){
            System.out.print("\nCitizen ID is not in Database !!");
            return;
        }

        System.out.print("\n"+c.status);
        System.out.print("\nVaccine Given: "+c.vaccine);
        System.out.print("\nNumber of Dose given: "+c.doseGiven);
        System.out.print("\nNext Dose Due date: "+c.dueDate);
    }
}



