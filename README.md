# Instigrim
> [!NOTE]
> KMP is growing rapidly. By the time you access this repo, some of the dependencies might be deprecated.

This is a Kotlin Multiplatform (KMP) project targeting Android, iOS. This project was created by using [Kotlin Multiplatform Wizard](https://kmp.jetbrains.com/).

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

> [!TIP]
> Learn more about [Kotlin Multiplatform (KMP)](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).

# Dependencies
1. ### [Voyager](https://github.com/adrielcafe/voyager)
   - voyager-navigator
   - voyager-tab-navigator
  
2. ### [Ktor](https://ktor.io/docs/client-create-multiplatform-application.html)
   - ktor-client-core
   - ktor-client-okhttp
   - ktor-client-darwin
  
3. ### [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/)
   - koin-core
   - koin-android
   - koin-compose
  
4. ### [Firebase Kotlin SDK](https://github.com/GitLiveApp/firebase-kotlin-sdk)
   - firebase-bom
   - firebase-firestore
   - firebase-common
   - firebase-common-ktx

# Screenshots
<img width="1470" alt="Screenshot 2024-06-06 at 23 33 21" src="https://github.com/adityatri/Instigrim/assets/59854661/e2eeb0c3-99a4-4945-a65d-430331e30312">
<img width="1470" alt="Screenshot 2024-06-06 at 23 39 25" src="https://github.com/adityatri/Instigrim/assets/59854661/054202ce-72dc-4422-a115-3370d56e8559">
<img width="1470" alt="Screenshot 2024-06-06 at 23 42 01" src="https://github.com/adityatri/Instigrim/assets/59854661/85933533-002e-4194-843f-b75ab53e61ef">
<img width="1470" alt="Screenshot 2024-06-06 at 23 40 28" src="https://github.com/adityatri/Instigrim/assets/59854661/6b48f473-239b-4789-a112-cdc4d18c382f">
<img width="1470" alt="Screenshot 2024-06-06 at 23 41 04" src="https://github.com/adityatri/Instigrim/assets/59854661/235b7692-5800-4cbb-88dc-d8e7044353f3">


