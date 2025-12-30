import java.util.*;

/**
 * Clinic Queue Management System
 * Main Program - Interactive User Interface
 * 
 * @author [Your Group Members' Names Here]
 * @version 1.0
 * @date December 28, 2025s
 * 
 * This system demonstrates the use of Priority Queue data structure
 * for managing patient queues in a clinic environment.
 */
public class ClinicQueueSystem {
    private static ClinicQueue clinicQueue;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        clinicQueue = new ClinicQueue();
        scanner = new Scanner(System.in);
        
        // Add some sample data for demonstration
        addSampleData();
        
        // Main program loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewPatient();
                    break;
                case 2:
                    callNextPatient();
                    break;
                case 3:
                    removePatient();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    searchPatient();
                    break;
                case 6:
                    clinicQueue.displayQueue();
                    break;
                case 7:
                    viewNextPatient();
                    break;
                case 8:
                    clinicQueue.displayStatistics();
                    break;
                case 9:
                    displayAbout();
                    break;
                case 0:
                    running = exitSystem();
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display the main menu
     */
    private static void displayMainMenu() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║        🏥 CLINIC QUEUE MANAGEMENT SYSTEM 🏥                       ║");
        System.out.println("║                    Priority Queue Implementation                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│  📝 PATIENT MANAGEMENT                                             │");
        System.out.println("│    [1] Add New Patient (INSERT)                                    │");
        System.out.println("│    [2] Call Next Patient (DELETE)                                  │");
        System.out.println("│    [3] Remove Patient from Queue (DELETE)                          │");
        System.out.println("│    [4] Update Patient Information (UPDATE)                         │");
        System.out.println("│    [5] Search Patient (SEARCH)                                     │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  📋 QUEUE OPERATIONS                                               │");
        System.out.println("│    [6] Display Current Queue                                       │");
        System.out.println("│    [7] View Next Patient (Peek)                                    │");
        System.out.println("│    [8] Display Statistics                                          │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("│  ℹ️  SYSTEM                                                         │");
        System.out.println("│    [9] About This System                                           │");
        System.out.println("│    [0] Exit                                                        │");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        System.out.println();
    }
    
    /**
     * Add a new patient to the queue (INSERT operation)
     * Patient ID is auto-generated by the system
     */
    private static void addNewPatient() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ➕ ADD NEW PATIENT                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        System.out.println("\n📝 Patient ID will be auto-generated by the system.\n");
        
        String name = getStringInput("Enter Patient Name: ");
        int age = getIntInput("Enter Age: ");
        String condition = getStringInput("Enter Medical Condition: ");
        
        System.out.println("\n📌 Priority Levels:");
        System.out.println("   [1] CRITICAL/EMERGENCY - Life-threatening conditions");
        System.out.println("   [2] URGENT - Serious conditions requiring prompt attention");
        System.out.println("   [3] REGULAR - Standard medical consultation");
        int priority = getIntInput("Select Priority (1-3): ");
        
        while (priority < 1 || priority > 3) {
            System.out.println("❌ Invalid priority! Please enter 1, 2, or 3.");
            priority = getIntInput("Select Priority (1-3): ");
        }
        
        clinicQueue.addPatient(name, age, condition, priority);
    }
    
    /**
     * Call the next patient (DELETE operation - highest priority)
     */
    private static void callNextPatient() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  🔔 CALL NEXT PATIENT                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        Patient nextPatient = clinicQueue.callNextPatient();
        
        if (nextPatient != null) {
            System.out.println("\n✅ Please proceed to the consultation room.");
        }
    }
    
    /**
     * Remove a specific patient from queue (DELETE operation)
     */
    private static void removePatient() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ➖ REMOVE PATIENT FROM QUEUE                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        clinicQueue.displayQueue();
        
        String patientId = getStringInput("\nEnter Patient ID to remove: ");
        clinicQueue.removePatient(patientId);
    }
    
    /**
     * Update patient information (UPDATE operation)
     */
    private static void updatePatient() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ✏️  UPDATE PATIENT INFORMATION                                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        String patientId = getStringInput("\nEnter Patient ID to update: ");
        Patient patient = clinicQueue.searchByID(patientId);
        
        if (patient == null) {
            System.out.println("❌ Patient not found!");
            return;
        }
        
        System.out.println("\n📄 Current Information:");
        System.out.println(patient.toDetailedString());
        
        System.out.println("Enter new information (press Enter to keep current value):");
        
        String newName = getStringInput("New Name [" + patient.getName() + "]: ");
        if (newName.isEmpty()) newName = patient.getName();
        
        String ageInput = getStringInput("New Age [" + patient.getAge() + "]: ");
        int newAge = ageInput.isEmpty() ? patient.getAge() : Integer.parseInt(ageInput);
        
        String newCondition = getStringInput("New Condition [" + patient.getCondition() + "]: ");
        if (newCondition.isEmpty()) newCondition = patient.getCondition();
        
        String priorityInput = getStringInput("New Priority (1-3) [" + patient.getPriority() + "]: ");
        int newPriority = priorityInput.isEmpty() ? patient.getPriority() : Integer.parseInt(priorityInput);
        
        clinicQueue.updatePatient(patientId, newName, newAge, newCondition, newPriority);
    }
    
    /**
     * Search for patients (SEARCH operation)
     */
    private static void searchPatient() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  🔍 SEARCH PATIENT                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\nSearch by:");
        System.out.println("  [1] Patient ID");
        System.out.println("  [2] Patient Name");
        System.out.println("  [3] Priority Level");
        
        int searchChoice = getIntInput("\nEnter choice: ");
        
        switch (searchChoice) {
            case 1:
                searchByID();
                break;
            case 2:
                searchByName();
                break;
            case 3:
                searchByPriority();
                break;
            default:
                System.out.println("❌ Invalid choice!");
        }
    }
    
    /**
     * Search patient by ID
     */
    private static void searchByID() {
        String patientId = getStringInput("Enter Patient ID: ");
        Patient patient = clinicQueue.searchByID(patientId);
        
        if (patient != null) {
            System.out.println("\n✅ Patient found:");
            System.out.println(patient.toDetailedString());
        } else {
            System.out.println("\n❌ Patient not found!");
        }
    }
    
    /**
     * Search patients by name
     */
    private static void searchByName() {
        String name = getStringInput("Enter Patient Name (partial match allowed): ");
        List<Patient> results = clinicQueue.searchByName(name);
        
        if (results.isEmpty()) {
            System.out.println("\n❌ No patients found with name: " + name);
        } else {
            System.out.println("\n✅ Found " + results.size() + " patient(s):");
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
            for (Patient p : results) {
                System.out.println(p);
            }
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        }
    }
    
    /**
     * Search patients by priority
     */
    private static void searchByPriority() {
        System.out.println("\nPriority Levels:");
        System.out.println("  [1] CRITICAL/EMERGENCY");
        System.out.println("  [2] URGENT");
        System.out.println("  [3] REGULAR");
        
        int priority = getIntInput("Enter priority level (1-3): ");
        List<Patient> results = clinicQueue.searchByPriority(priority);
        
        if (results.isEmpty()) {
            System.out.println("\n❌ No patients found with this priority level.");
        } else {
            System.out.println("\n✅ Found " + results.size() + " patient(s):");
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
            for (Patient p : results) {
                System.out.println(p);
            }
            System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        }
    }
    
    /**
     * View next patient without removing (Peek operation)
     */
    private static void viewNextPatient() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  👁️  VIEW NEXT PATIENT (PEEK)                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        Patient nextPatient = clinicQueue.peekNextPatient();
        
        if (nextPatient != null) {
            System.out.println("\n📌 Next patient to be called:");
            System.out.println(nextPatient.toDetailedString());
        } else {
            System.out.println("\n❌ Queue is empty! No patients waiting.");
        }
    }
    
    /**
     * Display system information
     */
    private static void displayAbout() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ℹ️  ABOUT THIS SYSTEM                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        System.out.println("\n📚 Project: Clinic Queue Management System");
        System.out.println("🎯 Purpose: DSA Mini Project - Priority Queue Implementation");
        System.out.println("\n🔧 Main Data Structure: PRIORITY QUEUE (Min-Heap)");
        System.out.println("\n📝 Core Operations Implemented:");
        System.out.println("   ✓ INSERT    - Add patient to queue (O(log n))");
        System.out.println("   ✓ DELETE    - Remove patient from queue (O(log n))");
        System.out.println("   ✓ UPDATE    - Modify patient information (O(n))");
        System.out.println("   ✓ SEARCH    - Find patient by ID/Name/Priority (O(1) to O(n))");
        System.out.println("\n🎨 Key Features:");
        System.out.println("   • Priority-based patient queuing (Emergency > Urgent > Regular)");
        System.out.println("   • FIFO ordering within same priority level");
        System.out.println("   • Real-time queue statistics and reporting");
        System.out.println("   • Patient status tracking (Waiting/In Treatment/Completed)");
        System.out.println("   • Multiple search options (ID, Name, Priority)");
        System.out.println("   • Interactive user interface with input validation");
        System.out.println("\n💡 Priority Queue Benefits:");
        System.out.println("   • Automatic ordering based on priority");
        System.out.println("   • Efficient O(log n) insertion and deletion");
        System.out.println("   • Perfect for emergency/urgent case handling");
        System.out.println("   • Fair treatment within same priority (FIFO)");
        System.out.println("\n👥 Group Members: \n Muhammad Fahmi Azhar \n");
        System.out.println("\n📅 Date: December 28, 2025");
        System.out.println("🏫 Course: Data Structures and Algorithms");
    }
    
    /**
     * Exit the system
     */
    private static boolean exitSystem() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║  👋 EXIT SYSTEM                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        
        clinicQueue.displayStatistics();
        
        System.out.println("\n⚠️  Warning: " + clinicQueue.getSize() + " patient(s) still in queue!");
        String confirm = getStringInput("\nAre you sure you want to exit? (yes/no): ");
        
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            System.out.println("\n✅ Thank you for using the Clinic Queue Management System!");
            System.out.println("👋 Goodbye!");
            return false;
        } else {
            System.out.println("\n↩️  Returning to main menu...");
            return true;
        }
    }
    
    /**
     * Add sample data for demonstration
     */
    private static void addSampleData() {
        clinicQueue.addPatient("John Doe", 45, "Chest Pain", 1);
        clinicQueue.addPatient("Sarah Smith", 28, "Fever and Cough", 3);
        clinicQueue.addPatient("Mike Johnson", 62, "Difficulty Breathing", 2);
        clinicQueue.addPatient("Emma Wilson", 35, "Regular Checkup", 3);
        clinicQueue.addPatient("David Brown", 50, "Severe Headache", 2);
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Get string input from user
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Get integer input from user with validation
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }
    
    /**
     * Clear screen (platform-independent)
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Alternative for Windows: print multiple newlines
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
//my name is areez
