# Unipi Audio Stories
<p align="center">
  <img width="100" src="https://user-images.githubusercontent.com/64270931/229101809-2fba8511-4c71-410e-b629-cf73d072ed14.png" alt="audio_book_logo_round"><br>
  <b>Unipi Audio Stories is a simple greek fairytales app</b> developed for my MSc studies.
</p>

<video width="320" height="240" controls>
  <source src="" type="video/mp4">
  <source src="movie.ogg" type="video/ogg">
  Your browser does not support the video tag.
</video>

[![Demo  Unipi Audio Stories](https://user-images.githubusercontent.com/64270931/229106173-4910610a-0162-425b-bbda-96a25ecd181c.gif)](https://user-images.githubusercontent.com/64270931/229104760-ebc5a0dc-1609-424e-a9f6-061d04ac5cdc.mp4)

<br><br>

The user can:
1. Register/Login
2. Browse a list of stories
3. Read about a story
4. Have a TextToSpeech engine read it out loud for him
5. Add story as his favorite
6. View his favorites or stories statistics

### Programming Language 

![Kotlin](https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white) 

## Architecture
Model - View - ViewModel (MVVM)

## Technologies Used
- Navigation Component <br/>
- Kotlin Coroutines <br/>
- Data Binding

### Libraries

[Hilt]() for dependency Injection <br/>
[Firebase Authentication]() for the users authentication <br/>
[Firebase Realtime Database]() as an online database <br/>
[Room]() as a local database <br/>
[Firebase Storage]() to store any necessary stories media content <br/>
[Gson](https://github.com/google/gson) for serialization/deserialization </br>
[Glide](https://github.com/bumptech/glide) to load images <br/> 
[Shimmer Loading](https://facebook.github.io/shimmer-android/) to implement loading effect <br/>
[Timber](https://github.com/JakeWharton/timber) for logging across the app
Built with [Material Design](https://material.io/)
