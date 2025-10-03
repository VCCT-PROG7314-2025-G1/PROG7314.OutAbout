# API Setup Guide

This guide will help you set up Google Platform API and Eventbrite integration for your Android app.

## Prerequisites

1. Google Cloud Console account
2. Eventbrite developer account
3. Android Studio with your project

## Google Platform API Setup

### 1. Google Cloud Console Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the following APIs:
   - Maps SDK for Android
   - Places API
   - Geocoding API
   - Directions API

### 2. Create API Keys

1. Go to "Credentials" in the Google Cloud Console
2. Click "Create Credentials" → "API Key"
3. Create separate keys for:
   - Maps SDK for Android
   - Places API
4. Restrict the keys to your app's package name and SHA-1 fingerprint

### 3. Configure API Keys

1. Open `app/src/main/res/values/api_keys.xml`
2. Replace the placeholder values:
   ```xml
   <string name="google_maps_api_key">YOUR_ACTUAL_GOOGLE_MAPS_API_KEY</string>
   <string name="google_places_api_key">YOUR_ACTUAL_GOOGLE_PLACES_API_KEY</string>
   ```

## Eventbrite API Setup

### 1. Create Eventbrite Developer Account

1. Go to [Eventbrite Developer](https://www.eventbrite.com/platform/api-keys/)
2. Sign up or log in to your Eventbrite account
3. Go to "My Apps" and create a new app
4. Copy your Private Token (API Key)

### 2. Configure Eventbrite API Key

1. Open `app/src/main/res/values/api_keys.xml`
2. Replace the placeholder value:
   ```xml
   <string name="eventbrite_api_key">YOUR_ACTUAL_EVENTBRITE_API_KEY</string>
   ```

## Security Considerations

### For Production:

1. **Never commit API keys to version control**
2. Use environment variables or build configs
3. Restrict API keys with proper restrictions
4. Use ProGuard/R8 to obfuscate your code
5. Consider using a backend service to proxy API calls

### Recommended Security Setup:

1. Create a `local.properties` file (add to `.gitignore`):
   ```properties
   GOOGLE_MAPS_API_KEY=your_actual_key_here
   GOOGLE_PLACES_API_KEY=your_actual_key_here
   EVENTBRITE_API_KEY=your_actual_key_here
   ```

2. Update `build.gradle.kts` to read from `local.properties`:
   ```kotlin
   android {
       defaultConfig {
           buildConfigField("String", "GOOGLE_MAPS_API_KEY", "\"${project.findProperty("GOOGLE_MAPS_API_KEY") ?: ""}\"")
           buildConfigField("String", "GOOGLE_PLACES_API_KEY", "\"${project.findProperty("GOOGLE_PLACES_API_KEY") ?: ""}\"")
           buildConfigField("String", "EVENTBRITE_API_KEY", "\"${project.findProperty("EVENTBRITE_API_KEY") ?: ""}\"")
       }
   }
   ```

## Usage Examples

### Search for Events
```kotlin
val repository = EventbriteRepository(context)
val result = repository.searchEventsInCapeTown(
    query = "food",
    categories = "110"
)
```

### Get Event Details
```kotlin
val result = repository.getEventDetails("123456789")
```

### Use Google Maps
```kotlin
if (GoogleMapsUtils.isGooglePlayServicesAvailable(context)) {
    // Initialize Google Maps
    GoogleMapsUtils.moveToCapeTown(googleMap)
}
```

## Testing

1. Build and run your app
2. Check the logs for any API errors
3. Test the Eventbrite integration by searching for events
4. Test Google Maps functionality

## Troubleshooting

### Common Issues:

1. **API Key not working**: Check if the key is correctly set and has proper restrictions
2. **Network errors**: Ensure internet permission is granted
3. **Google Play Services not available**: Check if Google Play Services is installed on the device
4. **Eventbrite API errors**: Verify your API key and check the API documentation

### Debug Steps:

1. Check the logs for detailed error messages
2. Verify API keys are correctly configured
3. Test API calls using tools like Postman
4. Check network connectivity

## Additional Resources

- [Google Maps Android SDK Documentation](https://developers.google.com/maps/documentation/android-sdk)
- [Eventbrite API Documentation](https://www.eventbrite.com/platform/api-keys/)
- [Google Places API Documentation](https://developers.google.com/maps/documentation/places)
