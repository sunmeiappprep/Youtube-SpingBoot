# YouTube Clone - Backend

This is the backend service for the YouTube Clone MVP project, built with Java Spring Boot. It handles all the server-side logic, including user authentication, video management, comments, playlists, subscriptions, and more.

## Features

### 1. **User Authentication & Registration**
   - Secure login and registration functionality.
   - JWT-based authentication for secure API requests.

### 2. **Video Management**
   - CRUD operations for videos.
   - Ability to embed and manage videos via YouTube links.
   - Anyone can delete videos due to potential YouTube link breakage.

### 3. **Comment System**
   - CRUD operations for comments.
   - Supports replies and nested replies, allowing for threaded discussions.

### 4. **Playlist Management**
   - Users can create, update, and delete playlists.
   - Videos can be added to or removed from playlists.

### 5. **Subscription System**
   - Users can subscribe to other users.
   - Retrieves videos from subscribed users to display in the subscription tab.

### 6. **Like & Dislike Functionality**
   - Users can like or dislike videos and comments.
   - Tracks user interactions to update like/dislike counts.

### 7. **Search Functionality**
   - Enables searching for videos.
   - Search is not dynamic and returns results after submission.
