
# Nirbhaya - Family Safety App  

Nirbhaya is a mobile safety application tailored to ensure the safety and well-being of families. It provides features such as real-time location tracking, SOS alerts, and group management to keep loved ones secure. This document serves as a complete guide for developers and users, explaining its features, technical implementation, workflows, and more.  

---

## Table of Contents  

1. **Introduction**  
2. **Features**  
3. **System Requirements**  
4. **Architecture Overview**  
   - High-Level Design  
   - Low-Level Design  
5. **Setup and Installation**  
6. **Code Walkthrough**  
   - MainActivity  
   - Database Implementation  
   - Adapters  
   - Maps Integration  
7. **Detailed Flowcharts**  
   - High-Level Flowchart  
   - Low-Level Flowchart  
8. **Screenshots and User Interface**  
9. **Workflows**  
   - SOS Alerts  
   - Adding and Managing Contacts  
   - Viewing Locations on Map  
10. **Privacy and Security**  
11. **Testing**  
    - Test Scenarios  
    - Test Cases  
12. **Future Enhancements**  
13. **Contributions**  
14. **License**  

---

## 1. Introduction  

**Nirbhaya** is a family safety application designed with a focus on providing peace of mind. It ensures that family members are always connected and prepared to handle emergencies effectively. The app combines Android’s features like location tracking, permissions, and database management to deliver a seamless experience.  

---

## 2. Features  

### **Core Features**  

#### 2.1 Real-Time Location Tracking  
- **Description:** Tracks the live location of family members.  
- **Technical Implementation:** Uses the Ola Maps SDK to fetch GPS coordinates.  

#### 2.2 SOS Alerts  
- **Description:** Enables users to send an emergency alert to trusted contacts.  
- **Technical Implementation:** Combines push notifications and live location sharing.  

#### 2.3 Contact Management  
- **Description:** Allows users to add, edit, and remove family members.  
- **Technical Implementation:** Uses Room database for efficient local storage.  

#### 2.4 Location Sharing  
- **Description:** Users can share their location manually.  

---

## 3. System Requirements  

### **Minimum Requirements:**  
- **OS Version:** Android 8.0 (Oreo) or higher  
- **RAM:** 2GB or more  
- **Storage:** 100MB free space  

### **Dependencies:**  
- Ola Maps SDK  
- Android Lifecycle components  
- Room Database  

---

## 4. Architecture Overview  

### **High-Level Design**  
The app uses an **MVVM (Model-View-ViewModel)** architecture to ensure modularity, scalability, and ease of testing.  

```plaintext
[UI Layer]
   |
   --> View (Fragments & Activities)  
   |
   --> ViewModel  
   |
   --> Model (Repository + Database)
```

### **Low-Level Design**  
**Modules:**  
1. **MainActivity:** Handles navigation and permissions.  
2. **Database:** Manages user and contact data.  
3. **Fragments:** Implements individual UI components (e.g., Home, Maps).  

---

## 5. Setup and Installation  

1. Clone the repository:  
   ```bash  
   git clone https://github.com/yourusername/nirbhaya.git  
   ```  
2. Open the project in Android Studio.  
3. Add the Ola Maps SDK API key in the appropriate file.  
4. Run the app on an Android device/emulator.  

---

## 6. Code Walkthrough  

### **MainActivity**  

Handles permissions, navigation, and fragment transactions.  

```kotlin  
class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val requestCode = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout and check permissions
    }

    private fun checkAndRequestPermissions() {
        // Request necessary permissions
    }
}
```  

### **Room Database Implementation**  

Manages CRUD operations for contacts.  

```kotlin  
@Dao
interface ContactDao {
    @Insert
    suspend fun insertContact(contact: ContactModel)

    @Query("SELECT * FROM contactList")
    suspend fun getAllContacts(): List<ContactModel>
}
```  

---

## 7. Detailed Flowcharts  

### **High-Level Flowchart**  

This flowchart illustrates the primary workflows:  

```plaintext  
[App Launch]  
    |  
    +---> [Permission Checks]  
             |  
             +---> [Load Main Screen]  
                      |  
                      +---> [User Actions: SOS, Location, Contacts]  
```  

### **Low-Level Flowchart: SOS Feature**  

```plaintext  
[User Presses SOS]  
    |  
    +---> [Fetch Location Data]  
             |  
             +---> [Send Data to Server]  
                      |  
                      +---> [Notify Family Members]  
```  

---

## 8. Screenshots and User Interface  

**Placeholder:** Add annotated screenshots of your app UI for Home Screen, Maps, SOS, etc.  

---

## 9. Workflows  

### **9.1 Sending SOS Alerts**  

1. User taps the SOS button.  
2. App fetches the current location using Ola Maps.  
3. A notification is sent to family members.  

### **9.2 Viewing Locations on Map**  

1. Navigate to the "Dashboard" tab.  
2. Select a family member to view their live location.  

---

## 10. Privacy and Security  

### **10.1 Location Data**  
- Location data is encrypted during transmission.  

### **10.2 User Permissions**  
- Users have full control over who can access their data.  

---

## 11. Testing  

### **11.1 Test Scenarios:**  
- Verify SOS alerts.  
- Validate location tracking functionality.  

### **11.2 Example Test Case:**  

| Test Case         | Expected Outcome                     | Result  |  
|--------------------|--------------------------------------|---------|  
| Send SOS Alert     | Family receives location notification | Pass    |  

---

## 12. Future Enhancements  

- Integration with wearable devices.  
- Voice-activated SOS feature.  

---

## 13. Contributions  

1. Fork the repository.  
2. Submit pull requests with proper documentation.  

---

## 14. License  

This project is licensed under the MIT License.  

