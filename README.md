# Adyen Android Assignment

## Overview
This repository contains the coding challenge for Android developer candidates at Adyen. The task is to create an app using NASA's Astronomy Picture Of the Day (APOD) API.

## Setup
1. Register for an [API key](https://api.nasa.gov/).
2. Add your API key to `app/ local.gradle` (see `local.gradle.example` for details).
3. Verify your API key by running `PlanetaryServiceTest`.
4. Create a local Git repository.

## Time Limit
We expect you to take around 4 hours to complete the assignment.
It doesn't need to be done in one sitting, but try to use the time limit to guide your decisions and prioritise what to focus on.

## Features
1. APOD List Screen:

    <img src="screens/ListScreen.png" alt="APOD List Screen" width="300"/>     <img src="screens/ReorderDialog.png" alt="Dialog Sorting" width="300"/>
- Create a list of APODs (images only, no videos)
- Implement ordering
  * By title (ascending)
  * By date (descending)
2. APOD Details Screen:

    <img src="screens/DetailScreen.png" alt="Detail Screen" width="300"/>
- Show detailed information about a single APOD
3. Error Handling:

    <img src="screens/ErrorScreen.png" alt="Error Screen" width="300"/>
- Implement error screens (e.g., API errors)

## Resources
- Color schemes and icons are available in the resources folder
- Screen mockups are provided in the `screens` folder

## Evaluation Criteria
We will assess:
* Feature functionality and user experience
  - Portrait and landscape orientations
  - API call optimizations
  - Adherence to provided designs
* Architecture & Code style
* Test coverage and quality
* Rationale / prioritization behind implementation decisions

## Submission
Include your Git repository (with history intact) in the final zip file.

## Prioritization
Please provide a summary of your decision-making process while working on this assignment:
- I focused on Successful API call with error handling(network error) and UI implementation.
- Used MVVM as architecture
- Completed UI screens of the app (List screen, Detail Screen, Error Screen) using Jetpack Compose
- Used Jetpack Navigation 
- Retry functionality
- Fetched List of 20 items (with async image using coil)
- Sorting (date wise and Title wise)

If given more time, I would be able to work on following things
- Will Design dialog for sorting
- Will implement the Hilt as Dependency Injection (which will also resolve lot other problems as well)
- Will implement Favourite functionality using local database
- Will implement Pagination 
- Will try to cover test cases and code coverage

Please find the screenshots of what I've completed.

  <img src="screens/implimented/ListScreen.png" alt="APOD List Screen" width="300"/>   
  <img src="screens/implimented/DetailScreen.png" alt="Detail Screen" width="300"/>
  <img src="screens/implimented/NetworkError.png" alt="Error Screen" width="300"/>  

Demo video:
<video src="screens/implimented/demo.mp4" width="300/>

