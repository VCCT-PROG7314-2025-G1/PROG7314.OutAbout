# Out&About  
---

## Table of Contents
1. [Overview](#-overview)
2. [How the Application Works](#-how-the-application-works)
3. [Features Summary](#-features-summary)
4. [Changes from Planning & Design](#-changes-from-planning--design)
5. [Technical Architecture](#-technical-architecture)
6. [Testing & GitHub Actions](#-testing--github-actions)
7. [How to Run the Application](#-how-to-run-the-application)
8. [Purpose of the Application](#-purpose-of-the-application)
9. [Release Notes](#-release-notes)
10. [Preparation for Publication](#-preparation-for-publication)
11. [AI Usage Declaration](#-ai-usage-declaration)
12. [Demonstration Video](#-demonstration-video)
13. [References](#-references)

---

## Overview
**Out&About** is an Android mobile application that helps users **discover restaurants, coffee shops, markets, and events** in their area.  
Whether you’re a **local resident** or **tourist**, the app offers a user-friendly way to explore, plan outings, and stay updated on local happenings. Built with modern Android development practices, 
the app provides an intuitive interface for finding restaurants, shopping centers, entertainment venues, 
and various activities based on user preferences and location.


Out&About integrates:
- **Firebase** for authentication, real-time sync and storage
- **Google Places API** for live event and location data  
- **Biometric login** and **multi-language support** for accessibility and security (FINAL) 

---

## How the Application Works

### 1. Startup & Login
- The **Landing Page** allows users to register or log in.
- Sign in via **Google/Facebook (SSO)** or email/password.
- Returning users can log in instantly with **fingerprint or face ID**.

### 2. Home / Search
- Displays the user’s **current city** (editable via dropdown).  
- Categories include: _Food & Drinks_, _Shopping_, and _Things To Do_.  
- The **Top 5 Picks** carousel shows trending local activities.

### 3. Event Browsing
- Fetches data from the **Google Places API**.
- Displays event name, image, location, rating, and description.
- Users can **favourite** places for later viewing.

### 4️. Offline Mode
- Favourites remain available offline.
- Data automatically **syncs** once internet connection is restored.

### 5️. Notifications
- Users receive **push notifications** for updates, cancellations, or new events.
- Notifications are **context-aware** and **personalized**.

### 6️. Settings & Account
- Edit personal details, enable/disable notifications, or switch languages.
- Supports **English**, **Afrikaans**, and **isiXhosa**.

---

## Features Summary for PART 2

| Feature | Description |
|----------|-------------|
| **Single Sign-On (SSO)** | Secure login using Google/Facebook OAuth 2.0 |
| **Settings Menu** | Adjust language, notifications, and country preferences |
| **REST API Integration** | Fetches live event and venue data |
| **Offline Mode with Sync** | Cached data viewable without internet |
| **Favourites Page** | Save and access preferred events and places |
| **Modern Branding** | Friendly UI with elegant green-and-cream palette |
| **Firebase Backend** | Authentication, Firestore, Cloud Functions & Storage |
| **Automated Testing** | GitHub Actions for build & test automation |

---

## Changes from Planning & Design

| Aspect | Original Plan | Final Implementation |
|--------|----------------|----------------------|
| **API** | Custom REST API | Firebase + Google Places API |
| **Database** | SQL backend | Firebase Firestore (SQLite, offline support) |
| **UI/UX** | Static mockups | Fully implemented Material Design UI |
| **Notifications** | Planned | Implemented with Firebase Cloud Messaging |
| **Languages** | English only | Added Afrikaans + isiXhosa |
| **Offline Mode** | Optional | Fully functional with Firestore cache |
| **Branding** | Draft concept | Refined with professional colour palette (#12372A, #ADBC9F, #FBFADA) |
| **AI Usage** | Proposed support | Used for API structuring and logic refinement |

---

## Technical Architecture

### Backend Services
- **Firebase Authentication:** Manages users and biometrics.
- **Firebase Firestore:** Stores users, favourites, and settings.
- **Firebase Cloud Functions:** Fetches event data from Google Places API.
- **Google Places API:** Provides local event and venue data.

### Database Models

| Table | Key Fields | Description |
|--------|-------------|-------------|
| **Users** | user_id, email, language, biometric_enabled | User profiles |
| **Events** | event_id, name, category, location, rating | Event and place data |
| **Favourites** | user_id, event_id | User-saved events |
| **Notifications** | notification_id, title, message | Push alerts |
| **Settings** | settings_id, preferred_language | User preferences |

---

## Testing & GitHub Actions
Continuous integration via **GitHub Actions** ensures:
1. Automated Gradle build verification  
2. Lint and dependency checks  
3. Unit testing for login, navigation, and API calls  
4. APK generation upon successful tests  

---

## How to Run the Application

### Prerequisites
- Android Studio **Flamingo or higher**
- Android SDK (API 26+)  
- Emulator or physical Android device  

### Clone the Repository
```bash
git clone https://github.com/maanie-waanie/OutAndAbout.git
cd OutAndAbout
```

### Open in Android Studio
File → Open → Select project folder  
Sync Gradle (`build.gradle.kts`)  
Confirm SDK path (in `local.properties`):
```
sdk.dir=C:\Users\<YourName>\AppData\Local\Android\Sdk
```

### Build & Run
Select a device → click **Run**  
To build from terminal:
```bash
./gradlew assembleDebug
```

### Dependencies
- com.google.firebase:firebase-auth  
- com.google.firebase:firebase-firestore  
- com.google.firebase:firebase-messaging  
- com.google.android.material:material  
- androidx.biometric:biometric  
- com.google.android.gms:play-services-location  

### Testing
Run local unit tests:
```bash
./gradlew test
```

---

## Purpose of the Application
**Out&About** was developed to:
- Promote local discovery and community engagement  
- Support small businesses (restaurants, markets, venues)  
- Offer a secure, inclusive experience for diverse users  
- Enhance user convenience through offline sync and biometric login  

---

## Release Notes

| Version | Updates |
|----------|----------|
| **v1.0 Prototype** | Core UI, login/registration, initial API connection |
| **v1.1 (Final POE)** | Added biometrics, offline sync, push notifications, multi-language |
| **v1.2 (Release)** | UI improvements, performance optimization, Play Store build prep |

---

## Preparation for Publication
Signed release APK generated  
Target SDK: 34 (Android 14)  
Minimum SDK: 26 (Android 8.0)  
Assets: Icon, screenshots, and descriptions for Play Store  
Tested on emulator and physical device  

---

## AI Usage Declaration
AI tools such as **ChatGPT (OpenAI)** were used responsibly for:
- Structuring API endpoints and Firebase integration  
- Refining UI text and improving code clarity  
- Researching Kotlin patterns and Android SDK usage  

All AI-generated suggestions were reviewed and implemented manually, ensuring compliance with academic integrity policies.

---

## Demonstration Video
**Watch the Out&About Demo Video:**  
(https://www.youtube.com/watch?v=3M6D5RCYpsE)

The video showcases:
•	Registration & login
•	Event browsing and favourites
•	Navigation between pages
•	API Usage
•	Multi-language interface
 

---

## References
- ColorHunt (2025). *Palette 12372A – Design Inspiration.*  
- Google Developers (2025). *Places API Documentation.*  
- Petrenko, V. (2025). *How to Write a Mobile App Requirements Document.*  
- PROG7314 POE (2025). *Programming 3D Portfolio of Evidence Guidelines.*  

---

## Author
**Developed by:** Aman Adams (Group Leader), Debby Delport, Zoe Apriland Khanyi Mabuza
**Module:** PROG7314 – Programming 3D 


---

## Features

### User Authentication
- **User Registration**: Create new accounts with personal information  
- **Secure Login**: Username/password authentication  
- **Profile Management**: Edit and update user profiles  
- **Local Database**: SQLite integration for user data storage  

### Main Navigation
- **Landing Page**: Welcome screen with login/register options  
- **Home Dashboard**: Central hub with quick access to all features  
- **Bottom Navigation**: Easy access to Browse, Favourites, and Account sections  

### Location-Based Discovery
- **Google Maps Integration**: Interactive maps with location services  
- **Google Places API**: Real-time place data and ratings  
- **Location Permissions**: Fine location access for accurate results  
- **Current Location**: Find nearby places automatically  

### Category-Based Browsing
- **Food & Drink**: Restaurants, cafes, bars, and dining options  
- **Shopping**: Malls, stores, markets, and retail locations  
- **Things to Do**: Entertainment, activities, and attractions  
- **Services**: Professional services and utilities  
- **Available Activities**: Dynamic activity listings  

### Personalization
- **Favorites System**: Save and manage favorite places  
- **User Preferences**: Customized recommendations  
- **Profile Customization**: Personal account settings  

## Technical Stack

### **Core Technologies**
- **Language**: Kotlin  
- **Platform**: Android (API 24+)  
- **Architecture**: Fragment-based with Navigation Component  
- **UI**: Material Design Components  

### **Key Dependencies**
- **AndroidX Libraries**: Core, AppCompat, Fragment, Navigation  
- **Google Services**: Maps, Places API, Firebase  
- **Database**: SQLite with custom helper  
- **UI Components**: Material Design, ConstraintLayout  
- **Location Services**: Google Play Services  

### **Firebase Integration**
- **Authentication**: User management and security  
- **Firestore**: Cloud database for scalable data storage  
- **Analytics**: User behavior tracking  
- **Storage**: File and media storage  

## Prerequisites
Before running this application, ensure you have:
- **Android Studio**: Latest stable version  
- **Android SDK**: API level 24 or higher  
- **Google Maps API Key**: Required for location services  
- **Firebase Project**: Set up with Authentication and Firestore  
- **Java Development Kit**: JDK 11 or higher  

## Installation & Setup

### 1. Clone the Repository
```bash
https://github.com/maanie-waanie/PROG7314.OutAbout.git
cd PROG7314.OutAbout-Aman
```

### 2. Configure Google Services
1. Download your `google-services.json` file from Firebase Console  
2. Place it in the `app/` directory  
3. Ensure the package name matches your Firebase project  

### 3. Set Up Google Maps API
1. Create a `local.properties` file in the project root  
2. Add your Google Maps API key:  
```properties
MAPS_API_KEY=your_google_maps_api_key_here
```

### 4. Build and Run
1. Open the project in Android Studio  
2. Sync the project with Gradle files  
3. Build the project (Build → Make Project)  
4. Run on an emulator or physical device  

## App Structure

### **Main Activities & Fragments**
- `MainActivity`: Main activity container with navigation  
- `LandingFragment`: Welcome screen with authentication options  
- `LoginFragment`: User login interface  
- `RegisterFragment`: User registration form  
- `HomeFragment`: Main dashboard with category buttons  
- `BrowseFragment`: Place discovery and search  
- `FavouritesFragment`: Saved places management  
- `AccountFragment`: User profile and settings  

### **Category Fragments**
- `FoodDrinkFragment`: Restaurant and dining options  
- `ShoppingFragment`: Retail and shopping locations  
- `ThingsToDoFragment`: Entertainment and activities  
- `ServicesFragment`: Professional services  
- `AvailableActivitiesFragment`: Dynamic activity listings  

### **Database & Data Management**
- `OutnAboutDBHelper`: SQLite database operations (offline database)
- `Place.kt`: Place data model  
- `PlaceData.kt`: Place data management  
- `PlacesAdapter.kt`: RecyclerView adapter for place listings  

## UI/UX Design

### **Design Principles**
- **Material Design**: Following Google's design guidelines  
- **Responsive Layout**: Optimized for various screen sizes  
- **Intuitive Navigation**: Easy-to-use fragment-based navigation  
- **Consistent Theming**: Cohesive color scheme and typography  

### **Key UI Components**
- Custom rounded buttons and input fields  
- Montserrat font family for modern typography  
- Green color scheme (#12372A primary, #FBFADA secondary)  
- Card-based layouts for place listings  
- Bottom navigation for main sections  

## Configuration

### **API Keys Setup**
1. **Google Maps API**: Add to `local.properties`  
2. **Firebase Configuration**: Via `google-services.json`  
3. **Places API**: Configured through Google Cloud Console  

## Database Schema

### **Users Table**
```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    surname TEXT,
    mobile_number TEXT,
    email TEXT,
    country TEXT,
    username TEXT UNIQUE,
    password TEXT
)
```

## Testing
The project includes comprehensive testing setup:
- **Unit Tests**: JUnit 4 for business logic testing  
- **Instrumented Tests**: Espresso for UI testing  
- **Test Runner**: AndroidJUnitRunner for integration tests  


---
**Note**: This application requires proper API key configuration and Firebase setup to function correctly. 
Ensure all dependencies are properly installed before running the project.
