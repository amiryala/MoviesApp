# Movies App

A Kotlin Android application that displays a list of movies with filtering by genre, built with Jetpack Compose.

## Features

- Display a list of movies with pagination for efficient loading
- Filter movies by genre with visual indicators for selected genres
- Movie cards showing title, release year, overview, and genres
- Click on a movie to open its IMDB page

## Architecture

This application uses MVVM architecture with the following components:
- **ViewModel**: Manages UI-related data and communicates with the repository
- **Repository**: Handles data operations and abstracts the data sources
- **Paging 3**: For efficient loading of large datasets
- **Jetpack Compose**: For the UI implementation
- **Retrofit**: For API calls
- **Hilt**: For dependency injection

## Screenshots

[Include screenshots here once the app is complete]

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application

## Dependencies

- Jetpack Compose for UI
- Retrofit for network requests
- Hilt for dependency injection
- Paging 3 for handling large datasets
- Coroutines for asynchronous operations

## API

The application uses the following API endpoints:

- `/api/genres` - List all available genres with counts
- `/api/movies` - List movies with optional filtering by genre

## Testing

The repository includes unit tests for the data layer to ensure proper functioning of the API integration.

## Future Improvements

- Add offline support with Room database
- Implement search functionality
- Add sorting options (by year, alphabetically)
- Enhance UI with animations and transitions
- Add movie details screen

## License

MIT License