![Size](https://img.shields.io/github/repo-size/totoledao/manual-de-brincadeiras)
![Platform](https://img.shields.io/badge/platform-Android-78C257)

[![LinkedIn][linkedin-shield]][linkedin-url]

[![Kotlin][kotlin-shield]][kotlin-url][![Android][android-shield]][android-url]

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/totoledao/manual-de-brincadeiras">
    <!-- <img src="web\src\assets\logo.svg" alt="SpaceTime Logo" width="250"> -->
  </a>

  <p align="center">
    MVP for Kids' Game Manual
    <br />
    <a href="https://github.com/totoledao/manual-de-brincadeiras"><strong>Explore the docs »</strong></a>    
  </p>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>      
        <li><a href="#features">Features</a></li>
        <li><a href="#technical-goals">Technical Goals</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>    
    <li><a href="#usage">Usage</a></li>
    <!-- <li><a href="#license">License</a></li> -->
    <li><a href="#contact">Contact</a></li>    
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

**The Ultimate Offline Companion for Kids’ Activities!**

This app is a collaborative project designed to be the go to resource for children's entertainment and education. Created in partnership with a professional kids' animator, this app serves as a comprehensive compendium of games, plays, songs, workshops, and activities that have been tried and tested in various settings.

### Built With

- [Kotlin][kotlin-url]
- [Android][android-url]
- [SQLite](https://www.sqlite.org/)

### Features

- Extensive Collection: It will include a wide range of activities, from interactive games and creative workshops to engaging plays and sing-alongs. (Currently there are just a few games serving as placeholder)
- User-Friendly Interface: Designed with simplicity and ease of use in mind.
- Advanced Search: Quickly find specific activities or browse through different categories to discover new ones.
- Favorites: Save favorite activities for quick access and future reference.
- Fully Offline: The app is designed to work completely offline, ensuring accessibility without the need for an internet connection.

### Technical Goals

This app, while solving a real problem, was also created to consolidate my knowledge of Kotlin and the native Android platform while implementing advanced search queries with SQLite.
<br>One of the primary technical challenges involves rendering the content of the game's descriptions as a web view. This decision was made to ensure ease of customization for each individual description and to facilitate the display of images preloaded in the app's assets using `<img>` tags, as it can be stored as text/html. This ensures that visual content is readily available without relying on an internet connection. The web view also features a bridge call from the JavaScript to the native code, which triggers the display of a bottom drawer that searches for and shows a term from the glossary.

<!-- GETTING STARTED -->

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

- [Android Studio](https://developer.android.com/studio)

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/totoledao/manual-de-brincadeiras.git
   ```
2. Open the project folder in Android Studio and Run ▶️

<!-- USAGE EXAMPLES -->

## Usage

https://github.com/totoledao/manual-de-brincadeiras/assets/40635662/43c214bf-b3ad-4d5c-a793-b65e19c0de51

Advanced search and add to favorites usage example

<!-- LICENSE -->

<!-- ## License

Distributed under the MIT License. See [`LICENSE`][license-url] for more information. -->

<!-- CONTACT -->

## Contact

Guilherme Toledo - guilherme-toledo@live.com

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/guilhermemtoledo/)[![Instagram](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/totoledao)[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=whit)](https://www.github.com/totoledao)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=0e76a8
[linkedin-url]: http://www.linkedin.com/in/guilhermemtoledo
[android-shield]: https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white
[android-url]: https://developer.android.com/
[kotlin-shield]: https://img.shields.io/badge/Kotlin-8473ff?&style=for-the-badge&logo=kotlin&logoColor=white
[kotlin-url]: https://kotlinlang.org/
