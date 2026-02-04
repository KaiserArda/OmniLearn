
## Purpose
Most quiz and educational apps available today suffer from excessive ads, poor UI/UX, outdated content, or performance issues. **OmniLearn** is being built from the ground up to solve these problems: clean design, fast, fully offline-capable, and adaptable to any field of study.

## Current Progress (early development)
- Kotlin + Jetpack Compose codebase  
- Clean MVVM architecture (data / ui / vm packages)  
- Room database schema and DAOs implemented  
- Navigation Component with basic screen structure  
- Dynamic category system
- 
## Planned Core Features
- Well-organized categories (Programming, Math, Science, and many more)
- Smart quiz system with detailed explanations for every question
- Instant feedback and clear answer rationales
- User-contributed questions (moderated for quality)
- Powerful search and filtering tools
- Bookmarking and mistake tracking with spaced repetition logic
- Light / Dark theme with full Material You support
- Completely offline-first design
  
## Technical Stack
- 100% Kotlin  
- Jetpack Compose  
- Room Persistence Library  
- Navigation Component  
- Kotlin Coroutines + Flow  
- Hilt Dependency Injection 
- Google’s recommended modern Android architecture

### Project Structure
```
app/

 ├─ src/main/

 │      ├─ AndroidManifest.xml                                  // ID card for my app

 │      │

 │      ├─ java/com/example/omnilearn/

 │      │      │

 │      │      ├─ MainActivity.kt    

 │      │      │

 │      │      ├─ data/                                          // Data Layer

 │      │      │      ├─ local/

 │      │      │      │      ├─ dao/

 │      │      │      │      │      ├─ CategoryDao.kt

 │      │      │      │      │      ├─ StatsDao.kt

 │      │      │      │      │      └─ QuestionDao.kt

 │      │      │      │      │

 │      │      │      │      ├─ datastore/

 │      │      │      │      │      └─ SettingsDataStore.kt

 │      │      │      │      │     

 │      │      │      │      ├─ entity/

 │      │      │      │      │      ├─ CategoryEntity.kt

 │      │      │      │      │      ├─ DailyStatsEntity.kt

 │      │      │      │      │      └─ QuestionEntity.kt

 │      │      │      │      └─ AppDatabase.kt

 │      │      │      │

 │      │      │      └─ repository/

 │      │      │             └─ QuizRepository.kt

 │      │      │

 │      │      ├─ ui/                                          // UI Layer

 │      │      │      ├─ Navigation/

 │      │      │      │      └─ NavGraphs.kt

 │      │      │      ├─ Screens/

 │      │      │      │      ├─ AddQuestionScreen.kt

 │      │      │      │      ├─ CategoryListScreen.kt

 │      │      │      │      ├─ QuestionDetailScreen.kt

 │      │      │      │      ├─ QuestionListScreen.kt

 │      │      │      │      ├─ StatisticsScreen.kt

 │      │      │      │      └─ WelcomeScreen.kt

 │      │      │      ├─ theme/

 │      │      │      │      ├─ Color.kt

 │      │      │      │      ├─ Theme.kt

 │      │      │      │      └─ Type.kt

 │      │      │      └─ UiUtils.kt

 │      │      │

 │      │      └─ vm/                                              // ViewModels

 │      │             ├─ AddQuestionViewModel.kt

 │      │             ├─ CategoryViewModel.kt

 │      │             ├─ QuestionListViewModel.kt

 │      │             ├─ SettingsViewModel.kt

 │      │             └─ ViewModelFactories.kt

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
- [ ] Closed beta with fellow students  
- [ ] Public release on Google Play (target: early 2026)  
