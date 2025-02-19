RTSP Video Streaming Application

______________________________________________________________________________________________________________________________________

# Overview
This is an Android application that enables users to stream RTSP video streams with play, pause, and stop functionalities. The app provides a simple user interface where users can enter an RTSP URL to start streaming.

_________________________________________________________________________________________________________________________________________

# Project Setup Instructions

## Prerequisites:
- Android Studio (latest version recommended)
- Android SDK (API Level 24 or higher)
- An RTSP streaming server (e.g., VLC, Wowza, FFmpeg)

## Installation Steps:
1. Clone the repository:
   ```sh
   git clone https://github.com/YOUR_GITHUB_USERNAME/RTSP-Video-Streaming-App.git
   ```  
2. Navigate to the project folder:
   ```sh
   cd RTSP-Video-Streaming-App
   ```  
3. Open the project in **Android Studio**.  
4. Sync Gradle and build the project.  
5. Connect a physical device (or use an emulator with RTSP support).  
6. Run the application.  

____________________________________________________________________________________________________________________________________

# Features of the App
- RTSP Video Streaming: Users can enter an RTSP URL to stream videos.
- Playback Controls: Play, Pause, and Stop functionalities.
- Error Handling: Displays user-friendly messages for connection failures.
- Optimized Performance: Smooth streaming with minimal latency.
- Responsive UI: Simple and intuitive interface.

________________________________________________________________________________________________________________________

# Challenges Faced & Solutions

## 1. RTSP Streaming Support in Android
- **Issue:** Native Android VideoView does not support RTSP.
- **Solution:** Used `ExoPlayer` for RTSP playback with low latency.

## 2. Streaming Buffering Issues
- **Issue:** Video buffering caused delays in playback.
- **Solution:** Adjusted ExoPlayer buffer configurations for smoother streaming.

## 3. Error Handling for Invalid URLs
- **Issue:** App crashed when an invalid RTSP URL was entered.
- **Solution:** Implemented try-catch blocks and displayed error messages.

____________________________________________________________________________________________________________________________________

# How to Use the App
1. **Launch the App** on your Android device.
2. **Enter a valid RTSP URL** in the input field (Example: `rtsp://yourstreamurl`).
3. Tap **"Play"** to start streaming the video.
4. Use the **"Pause"** button to temporarily stop playback.
5. Tap **"Stop"** to completely stop the stream.

____________________________________________________________________________________________________________________________________

# Additional Notes
- The app requires **internet access** for streaming.
- Ensure your RTSP server is active before testing the app.

