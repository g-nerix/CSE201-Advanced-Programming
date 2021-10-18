import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
    LMS c1 = new LMS();
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
    Scanner sc;

    int id;
    Course c;

    Instructor(int id,Course c){
        sc = new Scanner(System.in);
        this.id = id;
        this.c = c;
    }
    void uploadLecture(){
        System.out.print("\n1. Add Lecture Slide\n" + "2. Add Lecture Video\n");
        int selection = sc.nextInt();
        if (selection == 1){
            Slide s = new Slide();
            s.upload();
            s.uploadedBy = "I"+this.id;
            c.lectureList.add(s);
        }
        else if (selection == 2){
            Video v = new Video();
            v.upload();
            v.uploadedBy = "I"+this.id;
            c.lectureList.add(v);
        }
        else {
            System.out.print("\nInvalid Selection!!");
            return;
        }

    }
    void viewLecture(){
        for(int i = 0; i < c.lectureList.size(); i++){
            c.lectureList.get(i).view();
            System.out.print("\n-----------------------");
        }
    }

    void uploadAssessment(){
        System.out.print("\n1. Add Assignment\n" + "2. Add Quiz\n");
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
            return;
        }
    }
    void viewAssessment(){
        for (int i = 0; i < c.assessmentList.size(); i++){
            if (c.assessmentList.get(i).isAssignment){
                System.out.print("\nID: "+c.assessmentList.get(i).id
                        +" Assignment: "+((Assignment)c.assessmentList.get(i)).problemStatement
                        +" Max Marks: "+((Assignment)c.assessmentList.get(i)).maxMarks);
            }
            else{
                System.out.print("\nID: "+c.assessmentList.get(i).id+" Question: "+((Quiz)c.assessmentList.get(i)).question);
            }
            System.out.print("\n-----------------------------");
        }
    }
    void closeAssessment(){
        System.out.print("\nList of Open Assignments:");
        for (int i = 0; i < c.assessmentList.size(); i++){
            if (c.assessmentList.get(i).isCloseFlag == false){
                if (c.assessmentList.get(i).isAssignment){
                    System.out.print("\nID: "+c.assessmentList.get(i).id
                            +" Assignment: "+((Assignment)c.assessmentList.get(i)).problemStatement
                            +" Max Marks: "+((Assignment)c.assessmentList.get(i)).maxMarks);
                }
                else{
                    System.out.print("\nID: "+c.assessmentList.get(i).id+" Question: "+((Quiz)c.assessmentList.get(i)).question);
                }
                System.out.print("\n-----------------------------");
            }
        }
        System.out.print("\nEnter id of assignment to close: ");
        int id = sc.nextInt();
        for (int i = 0; i < c.assessmentList.size(); i++){
            if (c.assessmentList.get(i).id == id){
                c.assessmentList.get(i).isCloseFlag = true;
                return;
            }
        }
        System.out.print("\nCan't find the given Assessment ID to close");
    }
    void gradeAssessment(){
        System.out.print("\nList of Assessment : ");
        viewAssessment();
        System.out.print("\nEnter ID of assessment to view submissions: ");
        int id = sc.nextInt();
        boolean assessmentFound = false;
        System.out.print("\nChoose ID from these ungraded submissions");
        for (int i = 0; i < c.studentList.size(); i++){
            for (int j = 0; j <c.studentList.get(i).submissionList.size(); j++){
                if (c.studentList.get(i).submissionList.get(j).assessmentID == id){
                    if (c.studentList.get(i).submissionList.get(j).grade == -1){
                        System.out.print("\n"+i+". S"+c.studentList.get(i).id);
                        assessmentFound = true;
                    }
                }
            }
        }
        System.out.print("");
        boolean studentFound = false;
        Submission submit = null;
        if (assessmentFound){
            int input = sc.nextInt();
            for (int i = 0; i < c.studentList.size(); i++){
                if (c.studentList.get(i).id == input){
                    studentFound = true;
                    System.out.print("\nSubmission:");
                    for(int j = 0; j < c.studentList.get(i).submissionList.size(); j++){
                        if (c.studentList.get(i).submissionList.get(j).assessmentID==id){
                            submit = c.studentList.get(i).submissionList.get(j);
                        }
                    }
                    System.out.print("\n");
                }
            }
            if (!studentFound){
                System.out.print("\nNo student with id "+input);
                return;
            }
            if(c.assessmentList.get(submit.assessmentID).isAssignment){
                System.out.print("\nAssignment: "+submit.fileName);
                System.out.print("\nMax Marks :"+((Assignment)(c.assessmentList.get(submit.assessmentID))).maxMarks);
            }
            else {
                System.out.print("\nQuiz: "+submit.answer);
                System.out.print("\nMax Marks : 1");
            }
            System.out.print("\nMarks Scored: ");
            int marks = sc.nextInt();
            submit.grade = marks;
            submit.gradedBy = "I"+this.id;
        }
        else{
            System.out.print("\nNo Student Submission Found with Assessment id "+id);
            return;
        }

    }


    void addComment(){
        Comment c1 = new Comment();
        c1.add();
        c1.addedBy = "I"+this.id;
        c.commentList.add(c1);
    }
    void viewComment(){
        for (int i = 0; i < c.commentList.size(); i++){
            c.commentList.get(i).view();
        }
    }

}

class Student{
    Scanner sc;
    int id;
    Course c;
    ArrayList<Submission> submissionList = new ArrayList<>();

    Student(int id, Course c){
        this.id = id;
        this.c = c;
        sc = new Scanner(System.in);
    }
    void viewLecture(){
        for(int i = 0; i < c.lectureList.size(); i++){
            c.lectureList.get(i).view();
            System.out.print("\n-----------------------");
        }
    }

    void viewAssessment(){
        for (int i = 0; i < c.assessmentList.size(); i++){
            if (!c.assessmentList.get(i).isCloseFlag){
                if (c.assessmentList.get(i).isAssignment){
                    System.out.print("\nID: "+c.assessmentList.get(i).id
                            +" Assignment: "+((Assignment)c.assessmentList.get(i)).problemStatement
                            +" Max Marks: "+((Assignment)c.assessmentList.get(i)).maxMarks);
                }
                else{
                    System.out.print("\nID: "+c.assessmentList.get(i).id+" Question: "+((Quiz)c.assessmentList.get(i)).question);
                }
                System.out.print("\n-----------------------------");
            }
        }
    }
    void uploadSubmission(){
        System.out.print("\nPending assessments: ");
        ArrayList<Integer> idList = new ArrayList<>();
        boolean isPending = true;
        for (int i = 0; i < c.assessmentList.size(); i++){
            isPending = true;
            for (int j = 0; j < submissionList.size(); j++){
                if (c.assessmentList.get(i).id == submissionList.get(j).assessmentID){
                    isPending = false;
                    break;
                }
            }
            if (isPending){
                if (!c.assessmentList.get(i).isCloseFlag){
                    idList.add(c.assessmentList.get(i).id);
                    if (c.assessmentList.get(i).isAssignment){
                        System.out.print("\nID: "+c.assessmentList.get(i).id
                                +" Assignment: "+((Assignment)c.assessmentList.get(i)).problemStatement
                                +" Max Marks: "+((Assignment)c.assessmentList.get(i)).maxMarks);
                    }
                    else{
                        System.out.print("\nID: "+c.assessmentList.get(i).id+" Question: "+((Quiz)c.assessmentList.get(i)).question);
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
            }
        }
        if (idInList){
            for (int i = 0; i < c.assessmentList.size(); i++){
                if (id == c.assessmentList.get(i).id){
                    if(c.assessmentList.get(i).isAssignment){
                        System.out.print("\nEnter filename of assignment:");
                        sc.nextLine();
                        String s = sc.nextLine();
                        if (s.length()>4){
                            if(s.charAt(s.length()-1)=='p'&&s.charAt(s.length()-2)=='i'&&s.charAt(s.length()-3)=='z'&&s.charAt(s.length()-4)=='.'){
                                Submission submit = new Submission(id);
                                submit.fileName = s;
                                submit.isPending = false;
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
                        System.out.print("\n"+((Quiz)(c.assessmentList.get(i))).question);
//                        sc.nextLine();
                        String s = sc.next();
                        Submission submit = new Submission(id);
                        submit.answer = s;
                        submit.isPending = false;
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
            if (submissionList.get(i).grade!=-1){
                if(c.assessmentList.get(submissionList.get(i).assessmentID).isAssignment){
                    System.out.print("\nAnswer for assessment"+i+"(Assignment): "+submissionList.get(i).fileName);
                }
                else{
                    System.out.print("\nAnswer for assessment"+i+"(Quiz): "+submissionList.get(i).answer);
                }
                System.out.print("\nMarks Scored: "+submissionList.get(i).grade);
                System.out.print("\nGraded By: "+submissionList.get(i).gradedBy);
            }

        }
        System.out.print("\nUngraded Submission: ");
        for (int i = 0; i < submissionList.size(); i++){
            if (submissionList.get(i).isPending==false&&submissionList.get(i).grade==-1){
                if(c.assessmentList.get(submissionList.get(i).assessmentID).isAssignment){
                    System.out.print("\nAnswer for assessment"+i+"(Assignment): "+submissionList.get(i).fileName);
                }
                else{
                    System.out.print("\nAnswer for assessment"+i+"(Quiz): "+submissionList.get(i).answer);
                }
            }
        }

    }

//    void addComment(){}
//    void viewComment(){}
    void addComment(){
        Comment c1 = new Comment();
        c1.add();
        c1.addedBy = "S"+this.id;
        c.commentList.add(c1);
    }
    void viewComment(){
        for (int i = 0; i < c.commentList.size(); i++){
            c.commentList.get(i).view();
        }
    }
}

interface LectureMaterial{
    void upload();
    void view();
}

//class lecture{
//    void addVideo(){
//
//    }
//}
class Video implements LectureMaterial{
    Scanner sc;
    String fileName;
    String topic;
    java.util.Date date ;
    String uploadedBy ;
    Video(){
        sc = new Scanner(System.in);
        this.fileName = null;
        this.topic = null;
        this.uploadedBy = null;
//Enter topic of video: Lecture 1
//Enter filename of video: lecture1.mp4
    }
    @Override
    public void upload(){
        System.out.print("\nEnter topic of video: ");
        String tempTopic = sc.nextLine();
        System.out.print("\nEnter filename of video: ");
        String tempFileName = sc.nextLine();
        if (tempFileName.endsWith(".mp4")){
            this.fileName = tempFileName;
            this.topic = tempTopic;
            this.date = new java.util.Date();
        }
        else{
            System.out.print("Invalid File Extension [*.mp4 only]!!");
        }

    }
    @Override
    public void view(){
//        Title of video: Lecture 1
//Video file: lecture1.mp4
//Date of upload: Thu Oct 14 23:25:39 IST 2021
//Uploaded by: I0
        System.out.print("\nTitle of video: "+this.topic);
        System.out.print("\nVideo file: "+this.fileName);
        System.out.print("\nDate of upload: "+this.date);
        System.out.print("\nUploaded by: "+this.uploadedBy);
    }
}
class Slide implements LectureMaterial{
    String content[];
    String topic;
    Scanner sc;
    java.util.Date date;
    String uploadedBy;

    Slide(){
        sc = new Scanner(System.in);
        uploadedBy = null;
//        upload();
    }
    @Override
    public void upload(){
        System.out.print("\nEnter topic of slides:");
//        sc.nextLine();
        this.topic = sc.nextLine();
        System.out.print("\nEnter number of slides:");
        int n = sc.nextInt();
        this.content = new String[n];
        System.out.print("\nEnter content of slides");
        sc.nextLine();
        for (int i = 0; i < n; i++){
            System.out.print("\nContent of slide "+(i+1)+":");

            content[i] = sc.nextLine();
        }
        this.date = new java.util.Date();
    }
    @Override
    public void view(){
//        Title: Slide 1
//Slide 1: Content 1
//Slide 2: Content 2
//Number of slides: 2
//Date of upload: Thu Oct 14 23:25:25 IST 2021
//Uploaded by: I0
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
    Scanner sc;
    static int count = 0;
    int id;
    boolean isCloseFlag;
    boolean isAssignment;
    Assessment(){
        this.id = count++;
        this.isCloseFlag = false;
        sc = new Scanner(System.in);
    }
    void setisCloseFlag(boolean b){
        isCloseFlag = b;
    }
    boolean getisCloseFlag(){
        return isCloseFlag;
    }
}
class Assignment extends Assessment{
    int maxMarks;
    String problemStatement;
    Assignment(int m, String p){
        this.maxMarks = m;
        this.problemStatement = p;
        this.isAssignment = true;
    }
    int getMaxMarks(){
        return maxMarks;
    }
}
class Quiz extends Assessment{
    int maxMarks;
    String question;
    Quiz (String q){
        this.maxMarks = 1;
        this.question = q;
        this.isAssignment = false;
    }
    int getMaxMarks(){
        return maxMarks;
    }
}

class Submission{
    int assessmentID;
    int grade;
    String answer;
    String fileName;
    boolean isPending;
    String gradedBy;
    Submission(int id){
        this.assessmentID = id;
        this.grade = -1;
        this.answer = null;
        this.fileName = null;
        this.isPending = true;
        this.gradedBy = null;
    }
}

class Comment{
    Scanner sc;
    String com;
    String addedBy;
    java.util.Date date;
    Comment(){
        sc = new Scanner(System.in);
    }
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
    Scanner sc;
    Course c;
    LMS(){
        sc = new Scanner(System.in);
        c = new Course();
        initialize();
        mainMenu();

    }
    void initialize(){
        c.addInstructor(0,c);
        c.addInstructor(1,c);
        c.addStudent(0,c);
        c.addStudent(1,c);
        c.addStudent(2,c);
    }
    void mainMenu(){
        int in1;
        System.out.print("\nWelcome to Backpack\n" +
                "1. Enter as instructor\n" +
                "2. Enter as student\n" +
                "3. Exit\n");
        in1 = sc.nextInt();
        if (in1 == 1){
            System.out.print("\nInstructors: ");
            for (int i = 0; i < c.instructorList.size(); i++){
                System.out.print("\n"+i+"- I"+c.instructorList.get(i).id);
            }
            System.out.print("\nChoose id: ");
            int input = sc.nextInt();
            boolean idFound = false;
            Instructor instruct = null;
            for (int i = 0; i < c.instructorList.size(); i++){
                if (input == c.instructorList.get(i).id){
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
            System.out.print("\nStudents: ");
            for (int i = 0; i < c.studentList.size(); i++){
                System.out.print("\n"+i+"- S"+c.studentList.get(i).id);
            }
            System.out.print("\nChoose id: ");
            int input = sc.nextInt();
            boolean idFound = false;
            Student s1 = null;
            for (int i = 0; i < c.instructorList.size(); i++){
                if (input == c.studentList.get(i).id){
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
    void instructorMenu(Instructor instruct){
        System.out.print("\nWelcome I"+instruct.id);
        System.out.print("\nINSTRUCTOR MENU\n" +
                "1. Add class material\n" +
                "2. Add assessments\n" +
                "3. View lecture materials\n" +
                "4. View assessments\n" +
                "5. Grade assessments\n" +
                "6. Close assessment\n" +
                "7. View comments\n" +
                "8. Add comments\n" +
                "9. Logout\n");
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
                return;
        }
    }
    void studentMenu(Student stud){
        System.out.print("\nWelcome S"+stud.id);
        System.out.print("\nSTUDENT MENU\n" +
                "1. View lecture materials\n" +
                "2. View assessments\n" +
                "3. Submit assessment\n" +
                "4. View grades\n" +
                "5. View comments\n" +
                "6. Add comments\n" +
                "7. Logout\n");
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
                return;
        }
    }
}




