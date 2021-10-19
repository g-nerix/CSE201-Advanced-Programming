package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new LMS();
    }
}

class Course{

    ArrayList<Instructor> instructorList = new ArrayList<>();
    ArrayList<Student> studentList = new ArrayList<>();
    ArrayList<LectureMaterial> lectureList = new ArrayList<>();
    ArrayList<Assessment> assessmentList = new ArrayList<>();
    ArrayList<Comment> commentList = new ArrayList<>();

    void addInstructor(int id, Course c){
        Instructor i = new Instructor(id,c);
        instructorList.add(i);
    }

    void addStudent(int id, Course c){
        Student s = new Student(id,c);
        studentList.add(s);
    }

}

class Instructor{

    private final Scanner sc;
    private final int id;
    private final Course c;

    Instructor(int id,Course c){
        sc = new Scanner(System.in);
        this.id = id;
        this.c = c;
    }

    int getID(){
        return this.id;
    }

    void uploadLecture(){
        System.out.print("\n1. Add Lecture Slide\n2. Add Lecture Video\n");
        int selection = sc.nextInt();
        if (selection == 1){
            Slide s = new Slide();
            if(s.upload()){
                s.setUploadedBy("I"+this.id);
                c.lectureList.add(s);
            }
        }
        else if (selection == 2){
            Video v = new Video();
            if(v.upload()){
                v.setUploadedBy("I"+this.id);
                c.lectureList.add(v);
            }
        }
        else {
            System.out.print("\nInvalid Selection!!");
        }

    }

    void viewLecture(){
        for(int i = 0; i < c.lectureList.size(); i++){
            c.lectureList.get(i).view();
            System.out.print("\n--------------------------------");
        }
    }

    void uploadAssessment(){
        System.out.print("\n1. Add Assignment\n2. Add Quiz\n");
        int n = sc.nextInt();
        String s;
        if (n == 1){
            System.out.print("\nEnter problem statement: ");
            sc.nextLine();
            s = sc.nextLine();

            System.out.print("\nEnter max marks: ");
            int m = sc.nextInt();
            Assignment a = new Assignment(m,s);
            c.assessmentList.add(a);
        }
        else if (n == 2){
            System.out.print("\nEnter Quiz Question: ");
            sc.nextLine();
            String q = sc.nextLine();
            Quiz q1 = new Quiz(q);
            c.assessmentList.add(q1);
        }
        else {
            System.out.print("Invalid Entry [1/2] only !!");
        }
    }

    boolean viewAssessment(){
        boolean isAssessment = false;
        for (int i = 0; i < c.assessmentList.size(); i++){

            isAssessment = true;

            if (c.assessmentList.get(i).isAssignment()){
                System.out.print("\n--------------------------------");
                System.out.print("\nID: "+c.assessmentList.get(i).getId()
                        +" Assignment: "+((Assignment)c.assessmentList.get(i)).getProblemStatement()
                        +" Max Marks: "+((Assignment)c.assessmentList.get(i)).getMaxMarks());

            }
            else{
                System.out.print("\n--------------------------------");
                System.out.print("\nID: "+c.assessmentList.get(i).getId()+" Question: "+((Quiz)c.assessmentList.get(i)).getQuestion());

            }
            System.out.print("\n-----------------------------");
        }
        return isAssessment;
    }

    void closeAssessment(){

        System.out.print("\nList of Open Assignments:");

        boolean isEmpty = true;

        for (int i = 0; i < c.assessmentList.size(); i++){
            if (c.assessmentList.get(i).getIsCloseFlag() == false){

                isEmpty = false;

                if (c.assessmentList.get(i).isAssignment()){

                    System.out.print("\nID: "+c.assessmentList.get(i).getId()
                            +" Assignment: "+((Assignment)c.assessmentList.get(i)).getProblemStatement()
                            +" Max Marks: "+((Assignment)c.assessmentList.get(i)).getMaxMarks());

                }
                else{

                    System.out.print("\nID: "+c.assessmentList.get(i).getId()+" Question: "+((Quiz)c.assessmentList.get(i)).getQuestion());

                }
                System.out.print("\n-----------------------------");
            }
        }
        if (isEmpty){
            System.out.print("\nEmpty Assessment List!!");
            return;
        }
        System.out.print("\nEnter id of assignment to close: ");

        int id = sc.nextInt();

        for (int i = 0; i < c.assessmentList.size(); i++){
            if (c.assessmentList.get(i).getId() == id){

                c.assessmentList.get(i).setIsCloseFlag(true);
                return;

            }
        }
        System.out.print("\nCan't find the given Assessment ID to close");
    }

    void gradeAssessment(){
        System.out.print("\nList of Assessment : ");

        if(!viewAssessment()){
            System.out.print("\nNo Assessment Left to be graded");
            return;
        }

        System.out.print("\nEnter ID of assessment to view submissions: ");
        int id = sc.nextInt();

        boolean assessmentFound = false;
        System.out.print("\nChoose ID from these ungraded submissions");

        for (int i = 0; i < c.studentList.size(); i++){
            for (int j = 0; j <c.studentList.get(i).submissionList.size(); j++){
                if (c.studentList.get(i).submissionList.get(j).getAssessmentID() == id){
                    if (c.studentList.get(i).submissionList.get(j).getGrade() == -1){

                        System.out.print("\n"+i+". S"+c.studentList.get(i).getId());
                        assessmentFound = true;

                    }
                }
            }
        }
        System.out.print("\n");

        boolean studentFound = false;
        Submission submit = null;

        if (assessmentFound){
            int input = sc.nextInt();
            for (int i = 0; i < c.studentList.size(); i++){
                if (c.studentList.get(i).getId() == input){

                    studentFound = true;
                    System.out.print("\nSubmission:");

                    boolean submissionFound = false;

                    for(int j = 0; j < c.studentList.get(i).submissionList.size(); j++){
                        if (c.studentList.get(i).submissionList.get(j).getAssessmentID()==id){

                            submit = c.studentList.get(i).submissionList.get(j);
                            submissionFound = true;
                        }
                    }
                    System.out.print("\n");
                    if(!submissionFound){
                        System.out.print("Submission ID not Found!!");
                        return;
                    }
                }
            }
            if (!studentFound){
                System.out.print("\nNo student with id "+input);
                return;
            }
            if(c.assessmentList.get(submit.getAssessmentID()).isAssignment()){
                System.out.print("\nAssignment: "+submit.getFileName());
                System.out.print("\nMax Marks :"+((Assignment)(c.assessmentList.get(submit.getAssessmentID()))).getMaxMarks());
            }
            else {
                System.out.print("\nQuiz: "+submit.getAnswer());
                System.out.print("\nMax Marks : 1");
            }
            System.out.print("\nMarks Scored: ");
            submit.setGrade(sc.nextInt());
            submit.setGradedBy("I"+this.id);
        }
        else{
            System.out.print("\nNo Student Submission Found with Assessment id "+id);
        }

    }

    void addComment(){
        Comment c1 = new Comment();
        c1.add();
        c1.setAddedBy("I"+this.id);
        c.commentList.add(c1);
    }

    void viewComment(){
        for (int i = 0; i < c.commentList.size(); i++){
            c.commentList.get(i).view();
        }
    }

}

class Student{
    private final Scanner sc;
    private final int id;
    private final Course c;
    ArrayList<Submission> submissionList = new ArrayList<>();

    Student(int id, Course c){
        this.id = id;
        this.c = c;
        sc = new Scanner(System.in);
    }
    int getId(){
        return this.id;
    }
    void viewLecture(){
        for(int i = 0; i < c.lectureList.size(); i++){
            c.lectureList.get(i).view();
            System.out.print("\n--------------------------------");
        }
    }

    void viewAssessment(){
        for (int i = 0; i < c.assessmentList.size(); i++){
            if (!c.assessmentList.get(i).getIsCloseFlag()){
                if (c.assessmentList.get(i).isAssignment()){
                    System.out.print("\nID: "+c.assessmentList.get(i).getId()
                            +" Assignment: "+((Assignment)c.assessmentList.get(i)).getProblemStatement()
                            +" Max Marks: "+((Assignment)c.assessmentList.get(i)).getMaxMarks());
                }
                else{
                    System.out.print("\nID: "+c.assessmentList.get(i).getId()+" Question: "+((Quiz)c.assessmentList.get(i)).getQuestion());
                }
                System.out.print("\n-----------------------------");
            }
        }
    }
    void uploadSubmission(){
        System.out.print("\nPending assessments: ");
        ArrayList<Integer> idList = new ArrayList<>();
        boolean isPending;
        for (int i = 0; i < c.assessmentList.size(); i++){
            isPending = true;
            for (int j = 0; j < submissionList.size(); j++){
                if (c.assessmentList.get(i).getId() == submissionList.get(j).getAssessmentID()){
                    isPending = false;
                    break;
                }
            }
            if (isPending){
                if (!c.assessmentList.get(i).getIsCloseFlag()){
                    idList.add(c.assessmentList.get(i).getId());
                    if (c.assessmentList.get(i).isAssignment()){
                        System.out.print("\nID: "+c.assessmentList.get(i).getId()
                                +" Assignment: "+((Assignment)c.assessmentList.get(i)).getProblemStatement()
                                +" Max Marks: "+((Assignment)c.assessmentList.get(i)).getMaxMarks());
                    }
                    else{
                        System.out.print("\nID: "+c.assessmentList.get(i).getId()+" Question: "+((Quiz)c.assessmentList.get(i)).getQuestion());
                    }
                    System.out.print("\n-----------------------------");
                }
            }
        }
        System.out.print("\nEnter ID of assessment: ");
        int id = sc.nextInt();
        boolean idInList = false;
        for (int i = 0; i < idList.size(); i++){
            if (id == idList.get(i)){
                idInList = true;
                break;
            }
        }
        if (idInList){
            for (int i = 0; i < c.assessmentList.size(); i++){
                if (id == c.assessmentList.get(i).getId()){
                    if(c.assessmentList.get(i).isAssignment()){

                        System.out.print("\nEnter filename of assignment:");
                        sc.nextLine();
                        String s = sc.nextLine();

                        if (s.length()>4){
                            if(s.charAt(s.length()-1)=='p'&&s.charAt(s.length()-2)=='i'&&s.charAt(s.length()-3)=='z'&&s.charAt(s.length()-4)=='.'){
                                Submission submit = new Submission(id);
                                submit.setFileName(s);
                                submit.setIsPending(false);
                                submissionList.add(submit);
                            }
                            else{
                                System.out.print("\nInvalid Extension of the File!! ");
                            }
                        }
                        else {
                            System.out.print("\nInvalid Name of the File!! ");
                        }
                    }
                    else{
                        System.out.print("\n"+((Quiz)(c.assessmentList.get(i))).getQuestion());
                        String s = sc.next();
                        Submission submit = new Submission(id);
                        submit.setAnswer(s);
                        submit.setIsPending(false);
                        submissionList.add(submit);
                    }
                }
            }
        }

        else{
            System.out.print("\nInvalid Selection of id !!");
        }

    }
    void checkGrades(){
        System.out.print("\nGraded Submission: ");
        for (int i = 0; i < submissionList.size(); i++){
            if (submissionList.get(i).getGrade()!=-1){
                if(c.assessmentList.get(submissionList.get(i).getAssessmentID()).isAssignment()){
                    System.out.print("\nAnswer for assessment"+i+"(Assignment): "+submissionList.get(i).getFileName());
                }
                else{
                    System.out.print("\nAnswer for assessment"+i+"(Quiz): "+submissionList.get(i).getAnswer());
                }
                System.out.print("\nMarks Scored: "+submissionList.get(i).getGrade());
                System.out.print("\nGraded By: "+submissionList.get(i).getGradedBy());
            }

        }
        System.out.print("\nUngraded Submission: ");
        for (int i = 0; i < submissionList.size(); i++){
            if (submissionList.get(i).getIsPending()==false&&submissionList.get(i).getGrade()==-1){
                if(c.assessmentList.get(submissionList.get(i).getAssessmentID()).isAssignment()){
                    System.out.print("\nAnswer for assessment"+i+"(Assignment): "+submissionList.get(i).getFileName());
                }
                else{
                    System.out.print("\nAnswer for assessment"+i+"(Quiz): "+submissionList.get(i).getAnswer());
                }
            }
        }

    }

    void addComment(){
        Comment c1 = new Comment();
        c1.add();
        c1.setAddedBy("S"+this.id);
        c.commentList.add(c1);
    }
    void viewComment(){
        for (int i = 0; i < c.commentList.size(); i++){
            c.commentList.get(i).view();
        }
    }
}

interface LectureMaterial{
    boolean upload();
    void view();
}

class Video implements LectureMaterial{

    private final Scanner sc;
    private String fileName;
    private String topic;
    private java.util.Date date ;
    private String uploadedBy ;

    Video(){
        sc = new Scanner(System.in);
        this.fileName = null;
        this.topic = null;
        this.uploadedBy = null;
    }

    void setUploadedBy(String uploadedBy){
        this.uploadedBy = uploadedBy;
    }

//    String getUploadedBy(){
//        return this.uploadedBy;
//    }

    @Override
    public boolean upload(){
        System.out.print("\nEnter topic of video: ");
        String tempTopic = sc.nextLine();
        System.out.print("\nEnter filename of video: ");
        String tempFileName = sc.nextLine();

        if (tempFileName.endsWith(".mp4")){
            this.fileName = tempFileName;
            this.topic = tempTopic;
            this.date = new java.util.Date();
            return true;
        }
        else{
            System.out.print("Invalid File Extension [*.mp4 only]!!");
            return false;
        }

    }
    @Override
    public void view(){

        System.out.print("\nTitle of video: "+this.topic);
        System.out.print("\nVideo file: "+this.fileName);
        System.out.print("\nDate of upload: "+this.date);
        System.out.print("\nUploaded by: "+this.uploadedBy);
    }
}
class Slide implements LectureMaterial{

    private final Scanner sc;
    private String[] content;
    private String topic;
    private java.util.Date date;
    private String uploadedBy;

    Slide(){
        sc = new Scanner(System.in);
        uploadedBy = null;

    }
    void setUploadedBy(String uploadedBy){
        this.uploadedBy = uploadedBy;
    }

    @Override
    public boolean upload(){
        System.out.print("\nEnter topic of slides:");
        this.topic = sc.nextLine();

        System.out.print("\nEnter number of slides:");
        int n = sc.nextInt();
        if (n < 0){
            System.out.print("\nNumber of Slides Should be Positive!!");
            return false;
        }

        this.content = new String[n];
        System.out.print("\nEnter content of slides");
        sc.nextLine();

        for (int i = 0; i < n; i++){
            System.out.print("\nContent of slide "+(i+1)+":");
            content[i] = sc.nextLine();
        }
        this.date = new java.util.Date();
        return true;
    }

    @Override
    public void view(){
        System.out.print("\nTitle: "+this.topic);

        for (int i = 0; i < this.content.length; i++){
            System.out.print("\nSlide "+(i+1)+": "+content[i]);
        }

        System.out.print("\nNumber of slides: "+content.length);
        System.out.print("\nDate of upload: "+this.date);
        System.out.print("\nUploaded by: "+this.uploadedBy);
    }

}

class Assessment{

//    private final Scanner sc;
    private static int count = 0;
    private final int id;
    private boolean isCloseFlag;
    private boolean isAssignment;
    private int maxMarks;

    protected Assessment(){
        this.id = count++;
        this.isCloseFlag = false;
//        sc = new Scanner(System.in);
    }

    protected int getMaxMarks(){
        return maxMarks;
    }

    protected void setMaxMarks(int marks){
        this.maxMarks = marks;
    }

    protected int getId(){
        return this.id;
    }

    protected void setIsCloseFlag(boolean b){
        this.isCloseFlag = b;
    }

    protected boolean getIsCloseFlag(){
        return this.isCloseFlag;
    }

    protected boolean isAssignment(){
        return this.isAssignment;
    }

    protected void setIsAssignment(boolean b){
        this.isAssignment =b;
    }
}

class Assignment extends Assessment{

//    private int maxMarks;
    private final String problemStatement;

    Assignment(int m, String p){
        this.setMaxMarks(m);
        this.problemStatement = p;
        this.setIsAssignment(true);
    }

    String getProblemStatement(){
        return this.problemStatement;
    }

//    void setProblemStatement(String problemStatement){
//        this.problemStatement = problemStatement;
//    }

//    int getMaxMarks(){
//        return maxMarks;
//    }

//    void setMaxMarks(int marks){
//        this.maxMarks = marks;
//    }
}

class Quiz extends Assessment{

//    int maxMarks;
    private final String question;

    Quiz (String q){
        this.setMaxMarks(1);
        this.question = q;
        this.setIsAssignment(false);
    }

//    void setQuestion(String question){
//        this.question = question;
//    }

    String getQuestion(){
        return this.question;
    }

//    int getMaxMarks(){
//        return maxMarks;
//    }
}

class Submission{

    private int assessmentID;
    private int grade;
    private String answer;
    private String fileName;
    private boolean isPending;
    private String gradedBy;

    Submission(int id){
        this.assessmentID = id;
        this.grade = -1;
        this.answer = null;
        this.fileName = null;
        this.isPending = true;
        this.gradedBy = null;
    }

    int getAssessmentID(){
        return this.assessmentID;
    }

    void setAssessmentID(int id){
        this.assessmentID = id;
    }

    int getGrade(){
        return this.grade;
    }

    void setGrade(int grade){
        this.grade = grade;
    }

    String getAnswer(){
        return this.answer;
    }

    void setAnswer(String answer){
        this.answer = answer;
    }

    String getFileName(){
        return this.fileName;
    }

    void setFileName(String fileName){
        this.fileName = fileName;
    }

    boolean getIsPending(){
        return this.isPending;
    }

    void setIsPending(boolean b){
        this.isPending = b;
    }

    String getGradedBy(){
        return this.gradedBy;
    }

    void setGradedBy(String gradedBy){
        this.gradedBy = gradedBy;
    }
}

class Comment{

    private final Scanner sc;
    private String com;
    private String addedBy;
    private java.util.Date date;

    Comment(){
        sc = new Scanner(System.in);
    }
    void setAddedBy(String addedBy){
        this.addedBy = addedBy;
    }

//    String getAddedBy(){
//        return this.addedBy;
//    }

    void add(){
        System.out.print("\nEnter comment: ");
        com = sc.nextLine();
        this.date = new java.util.Date();
    }

    void view(){
        System.out.print("\n"+this.com+" -"+this.addedBy);
        System.out.print("\n"+this.date+"\n----------------------------");
    }
}

class LMS{

    private final Scanner sc;
    private final Course c;

    LMS(){
        sc = new Scanner(System.in);
        c = new Course();
        initialize();
        mainMenu();
    }

    private void initialize(){
        c.addInstructor(0,c);
        c.addInstructor(1,c);
        c.addStudent(0,c);
        c.addStudent(1,c);
        c.addStudent(2,c);
    }

    private void mainMenu(){
        int in1;
        System.out.print("\n================================");
        System.out.print("\nWelcome to Backpack\n================================\n1. Enter as instructor\n2. Enter as student\n3. Exit\n");

        in1 = sc.nextInt();

        if (in1 == 1){
            System.out.print("\n================================");
            System.out.print("\nInstructors: ");
            System.out.print("\n================================");
            for (int i = 0; i < c.instructorList.size(); i++){
                System.out.print("\n"+i+"- I"+c.instructorList.get(i).getID());
            }

            System.out.print("\nChoose id: ");

            int input = sc.nextInt();
            boolean idFound = false;
            Instructor instruct = null;

            for (int i = 0; i < c.instructorList.size(); i++){
                if (input == c.instructorList.get(i).getID()){
                    idFound = true;
                    instruct = c.instructorList.get(i);
                }
            }

            if (idFound){
                instructorMenu(instruct);
            }
            else{
                System.out.print("\nInvalid ID Selection!!");
            }
        }
        else if (in1 == 2){
            System.out.print("\n================================");
            System.out.print("\nStudents: ");
            System.out.print("\n================================");
            for (int i = 0; i < c.studentList.size(); i++){
                System.out.print("\n"+i+"- S"+c.studentList.get(i).getId());
            }

            System.out.print("\nChoose id: ");

            int input = sc.nextInt();
            boolean idFound = false;
            Student s1 = null;

            for (int i = 0; i < c.studentList.size(); i++){
                if (input == c.studentList.get(i).getId()){
                    idFound = true;
                    s1 = c.studentList.get(i);
                }
            }
            if (idFound){
                studentMenu(s1);
            }
            else {
                System.out.print("\nInvalid ID Selection!!");
            }
        }
        else if (in1 == 3){
            System.out.print("\n==========Exiting==========");
            return;
        }
        else{
            System.out.print("\nWrong Selection Please Try Again !!");
        }
        mainMenu();
    }
    private void instructorMenu(Instructor instruct){
        System.out.print("\n*********  Welcome I"+instruct.getID()+"  *********");
        System.out.print("\n================================");
        String st = "\nINSTRUCTOR MENU\n================================\n1. Add class material\n2. Add assessments\n3. View lecture materials\n4. View assessments\n5. Grade assessments\n6. Close assessment\n7. View comments\n8. Add comments\n9. Logout\n";//);
        System.out.print(st);
        int in = sc.nextInt();
        switch(in) {

            case 1:
                instruct.uploadLecture();
                instructorMenu(instruct);
                break;

            case 2:
                instruct.uploadAssessment();
                instructorMenu(instruct);
                break;

            case 3:
                instruct.viewLecture();
                instructorMenu(instruct);
                break;

            case 4:
                instruct.viewAssessment();
                instructorMenu(instruct);
                break;

            case 5:
                instruct.gradeAssessment();
                instructorMenu(instruct);
                break;

            case 6:
                instruct.closeAssessment();
                instructorMenu(instruct);
                break;

            case 7:
                instruct.viewComment();
                instructorMenu(instruct);
                break;

            case 8:
                instruct.addComment();
                instructorMenu(instruct);
                break;

            case 9:
                break;

            default:
                System.out.print("\nInvalid Selection!!");
        }
    }
    private void studentMenu(Student stud){

        System.out.print("\n*********  Welcome S"+stud.getId()+"  *********");
        System.out.print("\n================================");
        System.out.print("\nSTUDENT MENU\n================================\n1. View lecture materials\n2. View assessments\n3. Submit assessment\n4. View grades\n5. View comments\n6. Add comments\n7. Logout\n");

        int in = sc.nextInt();

        switch(in) {

            case 1:
                stud.viewLecture();
                studentMenu(stud);
                break;

            case 2:
                stud.viewAssessment();
                studentMenu(stud);
                break;

            case 3:
                stud.uploadSubmission();
                studentMenu(stud);
                break;

            case 4:
                stud.checkGrades();
                studentMenu(stud);
                break;

            case 5:
                stud.viewComment();
                studentMenu(stud);
                break;

            case 6:
                stud.addComment();
                studentMenu(stud);
                break;

            case 7:
                break;

            default:
                System.out.print("Invalid Selection!!");
        }
    }
}
