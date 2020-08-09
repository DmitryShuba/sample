## Sample - 2019

### Requirements provided along with the assignment:
- The user should be able to navigate to all sections returned from the API
- The user should be able to see the title and description of the page they have navigated to.
- The app should work online and offline


### System Architecture
It was decided to use an architecture for the application in order to make it:
- highly resilient;
- fault tolerant;
- scalable;
- easily maintainable in the future;

Hence the decision was to use the MVVM Architecture which is also recommended to use by official [guideline](https://developer.android.com/jetpack/docs/guide).
MVVM Architecture facilitates inter-layer communication and is lifecycle-aware.
The following flow illustrates the core MVVM Pattern:
![MVVM Architecture](https://cdn.journaldev.com/wp-content/uploads/2018/04/android-mvvm-pattern.png)

There are a couple of advantages using ViewModel over MVP's Presenter:
- The Presenter holds references to the View. The ViewModel doesn’t.
- The Presenter updates the View using the classical way (triggering methods).
- The ViewModel sends data streams.
- The Presenter and View are in a 1 to 1 relationship.
- The View and the ViewModel are in a 1 to many relationship.
- The ViewModel does not know that the View is listening to it.


### Inter-layer communication
LiveData is an observable data holder class which is also lifecycle-aware meaning that it respects Android Lifecycle. Therefore it was decided to utilize it for inter-layer communication.
It is also worth mentioning the following advantages of using LiveData:
- Ensures UI matches your data state;
- No memory leaks;
- No crashes due to stopped activities;
- No manual lifecycle handling;
- Always up to date data;
- Proper configuration changes;
- Sharing resources.


### API usage
As one of the requirements goes: “The user should be able to navigate to all sections returned from the API”.
In order to provide safe and efficient networking for the application it was decided to use Retrofit along with its GSON converter and Kotlin Coroutines adapter. It makes managing of a network layer easier providing high maintainability and scalability.
Every time a request response data is handled by the app it is saved to the database storage.

### Data storage
The Room persistence library provides an abstraction layer over SQLite to allow more robust database access while harnessing the full power of SQLite.
The library is utilized to create cache of the app's data. This allows users to view a consistent copy of key information within your app, regardless of whether users have an internet connection or not.
The mechanism of passing necessary data to UI has the following view:
1. Repository layer process network model and converts it to a database entity.
2. Database entity LiveData is observed and hence adding new data will automatically trigger database to emit data to its observers.
3. As a consequence UI consumes data from Database which is updated and cached just after request response is successfully handled.


### Dependency injection
In order to manage dependencies efficiently a dependency injection framework was added to the project.
Kodein DI has following advantages:
- Lazily instantiate your dependencies when needed
- Stop caring about dependency initialization order
- Easily bind classes or interfaces to their instance or provider
- Easily debug your dependency bindings and recursions


### Navigation
The Navigation component is added to the project and provides a number benefits to make navigation between fragments obvious and ease to manage.
It is wothwhile to mention some other advantages of the Navigation component:
- It handles fragment transactions;
- It handles Up and Back actions correctly by default;
- It provides standardized resources for animations and transitions;
- It Implements and handling deep linking;
- It includes Navigation UI patterns, such as navigation drawers and bottom navigation, with minimal additional work;
- Safe Args - a Gradle plugin that provides type safety when navigating and passing data between destinations;
- ViewModel support - you can scope a ViewModel to a navigation graph to share UI-related data between the graph's destinations.
