# 📋 TODO - Features to Implement

## ✅ Completed Features

### Core System
- ✅ Patient class with Comparable interface
- ✅ Priority Queue implementation (Min-Heap)
- ✅ HashMap for O(1) ID lookup
- ✅ Auto-generated Patient IDs

### CRUD Operations
- ✅ **INSERT:** Add Patient - `addPatient()`
- ✅ **DELETE:** Call Next Patient - `callNextPatient()`
- ✅ **UPDATE:** Update Patient - `updatePatient()`
- ✅ **SEARCH:** Search by ID - `searchByID()`

### Other Features
- ✅ Display Queue - `displayQueue()`
- ✅ Basic Statistics - `displayStatistics()` (partial)
- ✅ Complete UI and Menu System
- ✅ Input Validation
- ✅ Sample Data

---

## ⏳ Missing Features (5 total)

### 1. Search by Name
**File:** ClinicQueue.java  
**Method:** `searchByName(String name)`  
**What:** Find patients by name (case-insensitive, partial match)

### 2. Search by Priority
**File:** ClinicQueue.java  
**Method:** `searchByPriority(int priority)`  
**What:** Find all patients with specific priority level (1, 2, or 3)

### 3. Remove Patient
**File:** ClinicQueue.java  
**Method:** `removePatient(String patientId)`  
**What:** Remove a specific patient from the queue by their ID

### 4. Peek Next Patient
**File:** ClinicQueue.java  
**Method:** `peekNextPatient()`  
**What:** View the next patient without removing them from queue

### 5. Statistics Breakdown
**File:** ClinicQueue.java  
**Method:** `displayStatistics()`  
**What:** Add count of patients by priority (Critical, Urgent, Regular)

---

## How to Find TODOs

Search for `TODO:` in ClinicQueue.java - all 5 locations are marked.

---

## Testing

After implementing each feature:
1. Compile: `javac *.java`
2. Run: `java ClinicQueueSystem`
3. Test the specific menu option
4. Verify no "Feature not yet implemented!" messages

---

**Status:** 5 features pending implementation
