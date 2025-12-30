import java.util.*;

/**
 * ClinicQueue Class - Manages the clinic patient queue using Priority Queue
 * Main Data Structure: Priority Queue (Heap-based implementation)
 * 
 * This class demonstrates core operations:
 * - INSERT: Add patient to queue
 * - DELETE: Remove patient from queue
 * - UPDATE: Modify patient information
 * - SEARCH: Find patient by ID or name
 */
public class ClinicQueue {
    // Main data structure: Priority Queue (Min-Heap based on priority)
    private PriorityQueue<Patient> patientQueue;
    
    // Supporting data structure for quick search by Patient ID
    private HashMap<String, Patient> patientMap;
    
    // Statistics tracking
    private int totalPatientsServed;
    private int totalPatientsAdmitted;
    private int patientIdCounter; // For auto-generating patient IDs
    
    // Constructor
    public ClinicQueue() {
        // PriorityQueue uses natural ordering (Comparable interface from Patient class)
        this.patientQueue = new PriorityQueue<>();
        this.patientMap = new HashMap<>();
        this.totalPatientsServed = 0;
        this.totalPatientsAdmitted = 0;
        this.patientIdCounter = 1; // Start from P001
    }
    
    // ========== INSERT OPERATION ==========
    /**
     * Add a new patient to the queue with auto-generated ID
     * Time Complexity: O(log n) for heap insertion
     */
    public boolean addPatient(String name, int age, String condition, int priority) {
        // Auto-generate patient ID in format P001, P002, etc.
        String patientId = String.format("P%03d", patientIdCounter);
        patientIdCounter++;
        
        // Create new patient with auto-generated ID
        Patient patient = new Patient(patientId, name, age, condition, priority);
        
        patientQueue.offer(patient); // O(log n)
        patientMap.put(patient.getPatientId(), patient);
        totalPatientsAdmitted++;
        
        System.out.println("✅ Patient added successfully!");
        System.out.println("   Auto-generated ID: " + patient.getPatientId());
        System.out.println("   Patient: " + patient.getName());
        System.out.println("   Priority: " + patient.getPriorityLabel());
        System.out.println("   Current queue size: " + patientQueue.size());
        return true;
    }
    
    // ========== DELETE OPERATION ==========
    /**
     * Remove and return the highest priority patient (next to be treated)
     * Time Complexity: O(log n) for heap removal
     */
    public Patient callNextPatient() {
        if (patientQueue.isEmpty()) {
            System.out.println("❌ Queue is empty! No patients waiting.");
            return null;
        }
        
        Patient nextPatient = patientQueue.poll(); // O(log n) - removes root of heap
        patientMap.remove(nextPatient.getPatientId());
        nextPatient.setStatus("In Treatment");
        totalPatientsServed++;
        
        System.out.println("\n🏥 CALLING NEXT PATIENT:");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println(nextPatient);
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Remaining patients in queue: " + patientQueue.size());
        
        return nextPatient;
    }
    
    /**
     * Remove a specific patient by ID
     * Time Complexity: O(n) for searching in queue, O(log n) for removal
     */
    public boolean removePatient(String patientId) {
        Patient patient = patientMap.get(patientId);

        if (patient == null) {
            System.out.println("❌ Patient ID not found!");
            return false;
        }

        // Remove from both data structures
        boolean removedFromQueue = patientQueue.remove(patient); // O(n)
        patientMap.remove(patientId); // O(1)

        if (removedFromQueue) {
            System.out.println("✅ Patient " + patientId + " removed from queue.");
            return true;
        } else {
            System.out.println("⚠️ Patient was in map but not in queue (inconsistent state).");
            return false;
        }
    }
    
    // ========== UPDATE OPERATION ==========
    /**
     * Update patient information
     * Time Complexity: O(n) to rebuild queue after update
     */
    public boolean updatePatient(String patientId, String newName, int newAge, 
                                 String newCondition, int newPriority) {
        Patient patient = patientMap.get(patientId);
        
        if (patient == null) {
            System.out.println("❌ Patient ID not found!");
            return false;
        }
        
        // Store old values for display
        String oldName = patient.getName();
        int oldPriority = patient.getPriority();
        
        // Remove from queue temporarily
        patientQueue.remove(patient);
        
        // Update patient information
        patient.setName(newName);
        patient.setAge(newAge);
        patient.setCondition(newCondition);
        patient.setPriority(newPriority);
        
        // Re-add to queue (will be reordered based on new priority)
        patientQueue.offer(patient);
        
        System.out.println("✅ Patient information updated successfully!");
        System.out.println("   Patient ID: " + patientId);
        if (!oldName.equals(newName)) {
            System.out.println("   Name: " + oldName + " → " + newName);
        }
        if (oldPriority != newPriority) {
            System.out.println("   Priority: " + getPriorityLabel(oldPriority) + " → " + patient.getPriorityLabel());
        }
        
        return true;
    }
    
    // ========== SEARCH OPERATIONS ==========
    /**
     * Search for a patient by ID
     * Time Complexity: O(1) using HashMap
     */
    public Patient searchByID(String patientId) {
        return patientMap.get(patientId);
    }
    
    /**
     * Search for patients by name (partial match)
     * Time Complexity: O(n)
     */
    public List<Patient> searchByName(String name) {
     if (name == null || name.trim().isEmpty()) {
            System.out.println("❌ Search name cannot be empty.");
            return new ArrayList<>();
        }
        String lowerName = name.toLowerCase().trim();
        List<Patient> results = new ArrayList<>();

        for (Patient patient : patientMap.values()) {
            if (patient.getName().toLowerCase().contains(lowerName)) {
                results.add(patient);
            }
        }

        if (results.isEmpty()) {
            System.out.println("🔍 No patients found with name containing: " + name);
        } else {
            System.out.println("🔍 Found " + results.size() + " patient(s) matching: " + name);
        }
        return results;
    }
    
    /**
     * Search for patients by priority level
     * Time Complexity: O(n)
     */
    public List<Patient> searchByPriority(int priority) {
        List<Patient> results = new ArrayList<>();
        for (Patient patient : patientMap.values()) {
            if (patient.getPriority() == priority) {
                results.add(patient);
            }
        }

        if (results.isEmpty()) {
            System.out.println("🔍 No patients found with priority: " + getPriorityLabel(priority));
        } else {
            System.out.println("🔍 Found " + results.size() + " patient(s) with priority: " + getPriorityLabel(priority));
        }
        return results;
    }
    
    // ========== DISPLAY OPERATIONS ==========
    /**
     * Display all patients in queue (ordered by priority)
     */
    public void displayQueue() {
        if (patientQueue.isEmpty()) {
            System.out.println("\n📋 Queue is currently empty.");
            return;
        }
        
        System.out.println("\n📋 CURRENT QUEUE STATUS:");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Patient ID | Name                 | Age | Condition                 | Priority           | Status");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        
        // Create a copy to display without removing from actual queue
        PriorityQueue<Patient> tempQueue = new PriorityQueue<>(patientQueue);
        int position = 1;
        
        while (!tempQueue.isEmpty()) {
            Patient patient = tempQueue.poll();
            System.out.printf("%-2d. %s\n", position++, patient);
        }
        
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Total patients in queue: " + patientQueue.size());
    }
    
    /**
     * Display queue statistics
     */
    public void displayStatistics() {
        System.out.println("\n📊 CLINIC QUEUE STATISTICS:");
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("Total Patients Admitted Today    : " + totalPatientsAdmitted);
        System.out.println("Total Patients Served Today       : " + totalPatientsServed);
        System.out.println("Current Patients in Queue         : " + patientQueue.size());
        System.out.println("────────────────────────────────────────────────────────────────");
        
        // Priority breakdown
        int critical = 0, urgent = 0, regular = 0;
        for (Patient p : patientMap.values()) {
            switch (p.getPriority()) {
                case 1: critical++; break;
                case 2: urgent++; break;
                case 3: regular++; break;
            }
        }
        System.out.println("Priority Breakdown:");
        System.out.println("  • CRITICAL/EMERGENCY (1): " + critical);
        System.out.println("  • URGENT (2):             " + urgent);
        System.out.println("  • REGULAR (3):            " + regular);
        
        System.out.println("════════════════════════════════════════════════════════════════");
    }
    
    /**
     * Peek at the next patient without removing
     */
    public Patient peekNextPatient() {
        if (patientQueue.isEmpty()) {
            System.out.println("❌ No patient in queue to peek.");
            return null;
        }
        Patient next = patientQueue.peek();
        System.out.println("🩺 NEXT PATIENT (peek):");
        System.out.println(next);
        return next;
    }
    
    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return patientQueue.isEmpty();
    }
    
    /**
     * Get queue size
     */
    public int getSize() {
        return patientQueue.size();
    }
    
    // Helper method to get priority label
    private String getPriorityLabel(int priority) {
        switch (priority) {
            case 1: return "CRITICAL/EMERGENCY";
            case 2: return "URGENT";
            case 3: return "REGULAR";
            default: return "UNKNOWN";
        }
    }
}
