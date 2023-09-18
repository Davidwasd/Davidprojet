/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Lim Yek Sean
 */
import entity.Tutor;
import java.util.*;
public class tutorLinkedList <T> implements TutorInterface<T> {
    
    private Node headNode = null;
    private Node tailNode = null;

    @Override
    public boolean checkExist(String test) {
        Node now = headNode;

        while (now != null) {
            if (test.equals(now.tutor.getTutorID())) {
                return true;
            } else if (!test.equals(now.tutor.getTutorID()) && now.next == null) {
                return false;
            }
            now = now.next;
        }
        return false;
    }

    @Override
public void addTutor(Tutor tutor) {
    while (true) {//use a while loop to keep prompting the user until a valid and unique tutor ID is provided
        Scanner scanner = new Scanner(System.in);
        Node now = headNode;
        Node newNode = new Node(tutor);
        boolean tutorExists = false;

        while (now != null) {
            if (now.tutor.getTutorID().equals(tutor.getTutorID())) {
                System.out.println("Tutor with the same ID already exists!");
                tutorExists = true;
                break;
            }
            now = now.next;
        }

        if (!tutorExists) {
            if (headNode == null) {
                headNode = newNode;
                tailNode = newNode;
            } else {
                tailNode.next = newNode;
                tailNode = newNode;
            }
            System.out.println("Tutor Added Successfully.");
            break; // Exit the loop if a valid and unique tutor ID is provided
        } else {
            System.out.print("Please enter a different Tutor ID: ");
            String newID = scanner.nextLine();
            tutor.setTutorID(newID);
        }
    }
}

    @Override
    public void editTutor(String ID, String newName, String newPhoneNo, String newEmail, double newSalaryRate) {
        Node now = headNode;

        while (now != null) {
            if (ID.equals(now.tutor.getTutorID())) {
                if (newName != null) {
                    now.tutor.setName(newName);
                }
                if (newPhoneNo != null) {
                    now.tutor.setPhoneNo(newPhoneNo);
                }
                if (newEmail != null){
                    now.tutor.setEmail(newEmail);
                }
                if (newSalaryRate != 0){
                    now.tutor.setSalaryRate(newSalaryRate);
                }
                System.out.println("Edited Successfully!\n");
                return;
            } else if (!ID.equals(now.tutor.getTutorID()) && now.next == null) {
                System.out.print("Tutor Not Found\n");
                return;
            }
            now = now.next;
        }
    }

    @Override
public void removeTutor(String tutorID) {
    if (headNode == null) {
        System.out.println("No tutors in the list. Cannot remove.");
        return;
    }

    if (tutorID.equals(headNode.tutor.getTutorID())) {
        headNode = headNode.next;
        System.out.println("Tutor with ID " + tutorID + " has been removed.");
        return;
    }

    Node now = headNode;
    Node previousNode = null;

    while (now != null) {
        if (tutorID.equals(now.tutor.getTutorID())) {
            previousNode.next = now.next;
            System.out.println("Tutor with ID " + tutorID + " has been removed.");
            return;
        } else {
            previousNode = now;
            now = now.next;
        }
    }

    System.out.println("Tutor with ID " + tutorID + " not found in the list.");
}

    @Override
    public void view() {
        Node now = headNode;
        System.out.println("\nTutor\n");
        while (now != null) {
            System.out.println(("Tutor ID : " + now.tutor.getTutorID())
                    + ("\nTutor Name : " + now.tutor.getName())
                    + ("\nPhone No : " + now.tutor.getPhoneNo())
                    + ("\nEmail :"+ now.tutor.getEmail())
                    + ("\nSalary Rate: RM" + now.tutor.getSalaryRate()));
            now = now.next;
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }
    }

    @Override
    public void editName(String tutorID, String newName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void generateSalaryReport() {
    double totalSalary = 0.0;
    double minSalary = Double.MAX_VALUE; // Initialize to a high value
    double maxSalary = Double.MIN_VALUE; // Initialize to a low value
    int tutorCount = 0;

    Node now = headNode;

    while (now != null) {
        double salaryRate = now.tutor.getSalaryRate();

        totalSalary += salaryRate;
        tutorCount++;

        if (salaryRate < minSalary) {
            minSalary = salaryRate;
        }

        if (salaryRate > maxSalary) {
            maxSalary = salaryRate;
        }

        now = now.next;
    }

    if (tutorCount == 0) {
        System.out.println("No tutors found.");
    } else {
        System.out.println("Salary Report");
        System.out.println("==================");
        System.out.println("Total Number of Tutors: " + tutorCount);
        System.out.println("Total Salary for All Tutors: RM" + totalSalary);
        System.out.println("Average Salary: RM" + (totalSalary / tutorCount));
        System.out.println("Minimum Salary: RM" + minSalary);
        System.out.println("Maximum Salary: RM" + maxSalary);
    }
}
    
    private class Node {
        private Node next;
        Tutor tutor;

        public Node(Tutor tutor) {
            this.tutor = tutor;
            this.next = null;
        }
    }
}

