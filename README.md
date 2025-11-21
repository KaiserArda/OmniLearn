# MedQuiz – Medical Quiz App for Students

Hi!  
I'm a 3rd-year Computer Engineering student currently developing MedQuiz as a serious, production-ready Android application that I plan to publish on Google Play late 2025.

## Purpose
Most medical question apps available today suffer from excessive ads, poor UI/UX, outdated content, or performance issues. MedQuiz is being built from the ground up to solve these problems: clean design, fast, fully offline-capable, and focused on real learning.

## Current Progress (early development)
- Kotlin + Jetpack Compose codebase  
- Clean MVVM architecture (data / ui / vm packages)  
- Room database schema and DAOs implemented  
- Navigation Component with basic screen structure  

## Planned Core Features
- Organized categories (Medical course topics)  
- Thousands of high-quality questions with detailed explanations  
- Instant feedback and answer rationales  
- User-contributed questions (with moderation)  
- Powerful search and filter system  
- Bookmark & mistake tracking  
- Light / Dark theme + Material You support  
- Completely offline-first 
  
## Technical Stack
- 100% Kotlin  
- Jetpack Compose  
- Room Persistence Library  
- Navigation Component  
- Kotlin Coroutines + Flow  
- Hilt Dependency Injection (will be added soon)  
- Google’s recommended modern Android architecture

### Project Structure
```
app/
 ├─ src/main/
 │      ├─ AndroidManifest.xml       // ID card for my app
 │      │
 │      ├─ java/com/example/medquiz/
 │      │      │
 │      │      ├─ MainActivity.kt
 │      │      │
 │      │      ├─ data/              // Data Layer
 │      │      │      ├─ local/
 │      │      │      │      ├─ AppDatabase.kt
 │      │      │      │      ├─ PrePopulate.kt
 │      │      │      │      ├─ dao/
 │      │      │      │      │      ├─ CategoryDao.kt
 │      │      │      │      │      └─ QuestionDao.kt
 │      │      │      │      └─ entity/
 │      │      │      │             ├─ CategoryEntity.kt
 │      │      │      │             └─ QuestionEntity.kt
 │      │      │      │
 │      │      │      └─ repository/
 │      │      │             └─ MedicalRepository.kt
 │      │      │
 │      │      ├─ ui/                // UI Layer
 │      │      │      ├─ theme/
 │      │      │      ├─ navigation/
 │      │      │      │      └─ NavGraphs.kt
 │      │      │      └─ screens/
 │      │      │             ├─ CategoryListScreen.kt
 │      │      │             ├─ QuestionListScreen.kt
 │      │      │             ├─ QuestionDetailScreen.kt
 │      │      │             └─ AddQuestionScreen.kt
 │      │      │
 │      │      └─ vm/                // ViewModels
 │      │             ├─ CategoryViewModel.kt
 │      │             ├─ QuestionListViewModel.kt
 │      │             └─ AddQuestionViewModel.kt
 │
 └─ build.gradle (project)
```
## How to Build
1. Clone the repository  
2. Open with Android Studio Ladybug or newer  
3. Sync Gradle  
4. Run on device/emulator – no additional setup required  

## Roadmap
- [ ] Complete quiz flow and UI screens  
- [ ] Populate initial question bank (500–1000 questions)  
- [ ] Implement contribution & moderation system  
- [ ] Closed beta with fellow medical students  
- [ ] Public release on Google Play (target: late 2025)  

Feedback, bug reports, and especially medical question submissions are very welcome. This project is a work in progress, and I’m happy to collaborate with fellow developers to make this a tool that actual medical students and residents use every day!
Thanks for checking out the project!

~ Arda  
Computer Engineering – Osmaniye Korkut Ata University
