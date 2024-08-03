# TranquilMind

### The challenge was to create a comprehensive healthcare platform comprising four main components:
  1. **Q&A Forum** : Open for viewing to all users, with registered users able to post questions. Trained responders prepare responses, moderate and approved before publication. Users can flag inappropriate posts for review and deletion.
  2. **Useful Resources** : A dedicated space showing curated content aimed at supporting patients with their mental well-being, including videos and other resources. 
  3. **Self-assessment and self-help tools** : Integration of tools like GAD7 and PHQ9 for user self-assessment, modeled after Wellness Check.
  4. **Connect with Professionals** : Providing chat-like interactions with available experts and direct audio calls to listed helplines during emergencies.

### Tech stack
+ **Backend** : Spring Boot
+ **Frontend** : React
+ **Mobile App** : React Native
+ **Database** : MySQL
+ **Realtime database**(for chats): Firebase

### Security and technical safeguards
  Some key security aspects we have kept in mind and achieved are as follows:
  
    • Securing passwords using Bcrypt Password encoder with strength 10
    • Securing sensitive Patient data with AES encryption and decryption while storing and fetching the data from database.
    • Securing chat between patient and user by encrypting and decrypting the chat information while storing and fetching the messages from firebase database.
    • Utilizing JWT Token for secure authentication.

### Functionalities achieved
#### Common:
    1.  User Registration and Login:
        ◦ Implementing a user registration system for all roles.
        ◦ Utilizing JWT tokens for secure authentication.
        ◦ Encrypting user passwords to ensure data security.
    2. Chat Encryption:
        ◦ Implementing encryption mechanisms to secure chat conversations between users.
        ◦ Ensuring end-to-end encryption to protect sensitive information exchanged during chats.
    3. Data Encryption:
        ◦ Employing encryption techniques to safeguard sensitive information related to doctors and patients during data collection and storage in the database.

####  Patient:
    
    • Courses and Tasks
    • Assessment
    • Appointment Booking
    • One-on-One Chat
    • Community Forum
    • Q&A Section
    • Guest Login Feature
    • Emergency Calling Service
    • Post Flagging Feature
    • Multilingual Accessibility

#### Doctor:

    • Appointment Management
    • One-on-One Chat
    • Community Forum Participation
    • Access to Patient Test Scores
    • Post Flagging Feature
