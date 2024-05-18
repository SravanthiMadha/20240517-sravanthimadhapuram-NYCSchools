NYC School App
=======================

![NYC School App]

This is an Android app that displays information about schools in New York City. It uses data from
the following APIs:

- [DOE High School Directory 2017](https://data.cityofnewyork.us/Education/DOE-High-School-Directory-2017/s3k6-pzi2)
- [SAT Results](https://data.cityofnewyork.us/Education/SAT-Results/f9bf-2cp4)

The app is built using modern Android development technologies and practices:

- MVVM architecture pattern
- Jetpack Compose for building UI components
- Kotlin coroutines for asynchronous programming
- MutableStateFlow, Live Data for reactive programming
- Retrofit for making API requests
- Koin for dependency injection

Features
--------

- View a list of NYC schools with basic information such as school name, address, and phone number
- Search schools functionality
- Refresh Functionality
- Continues downloading schools data while end of the list.
- View detailed information about a selected school, including SAT scores and program offerings

Getting started
---------------

To run the app locally, you will need to do the following:

1. Clone this repository
2. Open the project in Android Studio
3. Build and run the app on an emulator or physical device
