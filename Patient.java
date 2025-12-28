/**
 * Patient Class - Represents a patient in the clinic queue system
 * This class stores patient information and implements Comparable for priority ordering
 */
public class Patient implements Comparable<Patient> {
    private String patientId;
    private String name;
    private int age;
    private String condition;
    private int priority; // 1 = Critical/Emergency, 2 = Urgent, 3 = Regular
    private long arrivalTime; // Timestamp for FIFO within same priority
    private String status; // "Waiting", "In Treatment", "Completed"
    
    // Constructor
    public Patient(String patientId, String name, int age, String condition, int priority) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.priority = priority;
        this.arrivalTime = System.currentTimeMillis();
        this.status = "Waiting";
    }
    
    // Getters
    public String getPatientId() {
        return patientId;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public long getArrivalTime() {
        return arrivalTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Compare patients based on priority (lower number = higher priority)
    // If same priority, earlier arrival time gets priority (FIFO)
    @Override
    public int compareTo(Patient other) {
        if (this.priority != other.priority) {
            return Integer.compare(this.priority, other.priority);
        }
        return Long.compare(this.arrivalTime, other.arrivalTime);
    }
    
    // Get priority label
    public String getPriorityLabel() {
        switch (priority) {
            case 1: return "CRITICAL/EMERGENCY";
            case 2: return "URGENT";
            case 3: return "REGULAR";
            default: return "UNKNOWN";
        }
    }
    
    @Override
    public String toString() {
        return String.format("%-10s | %-20s | Age: %-3d | %-25s | Priority: %-18s | Status: %s",
                patientId, name, age, condition, getPriorityLabel(), status);
    }
    
    // Detailed display for individual patient
    public String toDetailedString() {
        return "\n========== PATIENT DETAILS ==========\n" +
               "Patient ID    : " + patientId + "\n" +
               "Name          : " + name + "\n" +
               "Age           : " + age + "\n" +
               "Condition     : " + condition + "\n" +
               "Priority      : " + getPriorityLabel() + "\n" +
               "Status        : " + status + "\n" +
               "Arrival Time  : " + new java.util.Date(arrivalTime) + "\n" +
               "=====================================\n";
    }
}
