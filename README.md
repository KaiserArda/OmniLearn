# English Version

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
 │      ├─ AndroidManifest.xml                                  // ID card for my app
 │      │
 │      ├─ java/com/example/medquiz/
 │      │      │
 │      │      ├─ MainActivity.kt    
 │      │      │
 │      │      ├─ data/                                          // Data Layer
 │      │      │      ├─ local/
 │      │      │      │      ├─ dao/
 │      │      │      │      │      ├─ CategoryDao.kt
 │      │      │      │      │      └─ QuestionDao.kt
 │      │      │      │      ├─ entity/
 │      │      │      │      │      ├─ CategoryEntity.kt
 │      │      │      │      │      └─ QuestionEntity.kt
 │      │      │      │      └─ AppDatabase.kt
 │      │      │      │
 │      │      │      └─ repository/
 │      │      │             └─ MedicalRepository.kt
 │      │      │
 │      │      ├─ ui/                                          // UI Layer
 │      │      │      ├─ Navigation/
 │      │      │      │      └─ NavGraphs.kt
 │      │      │      ├─ Screens/
 │      │      │      │      ├─ AddQuestionScreen.kt
 │      │      │      │      ├─ CategoryListScreen.kt
 │      │      │      │      ├─ QuestionDetailScreen.kt
 │      │      │      │      ├─ QuestionListScreen.kt
 │      │      │      │      └─ WelcomeScreen.kt
 │      │      │      ├─ theme/
 │      │      │      │      ├─ Color.kt
 │      │      │      │      ├─ Theme.kt
 │      │      │      │      └─ Type.kt
 │      │      │      ├─ ClickHelper
 │      │      │      ├─ Extensions.kt
 │      │      │      └─ UiUtils.kt
 │      │      │
 │      │      └─ vm/                                              // ViewModels
 │      │             ├─ AddQuestionViewModel.kt
 │      │             ├─ CategoryViewModel.kt
 │      │             ├─ QuestionDetailViewModel.kt
 │      │             ├─ QuestionListViewModel.kt
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
- [ ] Closed beta with fellow medical students  
- [ ] Public release on Google Play (target: late 2025)  

Feedback, bug reports, and especially medical question submissions are very welcome. This project is a work in progress, and I’m happy to collaborate with fellow developers to make this a tool that actual medical students and residents use every day!
Thanks for checking out the project!

~ Arda  
Computer Engineering – Osmaniye Korkut Ata University

# 日本語版

MedQuiz – 医学生のための医学クイズアプリ

こんにちは！  
コンピュータ工学科3年のArdaです。  
2025年末のGoogle Play公開を目指して、医学生向けの本格的なクイズアプリ「MedQuiz」を開発しています。

■ 作った理由
既存の医学クイズアプリには、
・広告が多すぎる
・動作が重い
・オフラインで使えない
・解説が薄い or 古い
といった不満が多くあります。

MedQuizは「本当に医学生が毎日使いたくなるアプリ」をコンセプトに、ゼロから作り直しています。  
広告ゼロ、完全オフライン、軽快な動作、丁寧な解説に徹底的にこだわっています。

■ 現在の進捗（まだ開発初期です）
- Kotlin + Jetpack Compose
- クリーンなMVVMアーキテクチャ
- Roomデータベース構築済み
- Navigation Componentで画面遷移完成

■ これから実装予定の主な機能
- 講義科目ごとの体系的なカテゴリ分け
- 数千問の高品質問題＋詳しい解説＆根拠
- 間違えた問題の自動記録・復習機能
- ブックマーク＆強力な検索・フィルター
- ユーザー投稿機能（医学生による審査付き）
- ライト/ダークテーマ + Material You対応
- 完全オフラインファースト

■ 使っている技術
Kotlin 100% / Jetpack Compose / Room / Hilt / Coroutines+Flow  
Google推奨のモダンアーキテクチャで開発中

### プロジェクトの構造
```
app/
 ├─ src/main/
 │      ├─ AndroidManifest.xml                                  // ID card for my app
 │      │
 │      ├─ java/com/example/medquiz/
 │      │      │
 │      │      ├─ MainActivity.kt    
 │      │      │
 │      │      ├─ data/                                          // Data Layer
 │      │      │      ├─ local/
 │      │      │      │      ├─ dao/
 │      │      │      │      │      ├─ CategoryDao.kt
 │      │      │      │      │      └─ QuestionDao.kt
 │      │      │      │      ├─ entity/
 │      │      │      │      │      ├─ CategoryEntity.kt
 │      │      │      │      │      └─ QuestionEntity.kt
 │      │      │      │      └─ AppDatabase.kt
 │      │      │      │
 │      │      │      └─ repository/
 │      │      │             └─ MedicalRepository.kt
 │      │      │
 │      │      ├─ ui/                                          // UI Layer
 │      │      │      ├─ Navigation/
 │      │      │      │      └─ NavGraphs.kt
 │      │      │      ├─ Screens/
 │      │      │      │      ├─ AddQuestionScreen.kt
 │      │      │      │      ├─ CategoryListScreen.kt
 │      │      │      │      ├─ QuestionDetailScreen.kt
 │      │      │      │      ├─ QuestionListScreen.kt
 │      │      │      │      └─ WelcomeScreen.kt
 │      │      │      ├─ theme/
 │      │      │      │      ├─ Color.kt
 │      │      │      │      ├─ Theme.kt
 │      │      │      │      └─ Type.kt
 │      │      │      ├─ ClickHelper
 │      │      │      ├─ Extensions.kt
 │      │      │      └─ UiUtils.kt
 │      │      │
 │      │      └─ vm/                                              // ViewModels
 │      │             ├─ AddQuestionViewModel.kt
 │      │             ├─ CategoryViewModel.kt
 │      │             ├─ QuestionDetailViewModel.kt
 │      │             ├─ QuestionListViewModel.kt
 │      │             └─ ViewModelFactories.kt
 │
 └─ build.gradle (project)
```
■ ビルド方法（すぐ試せます）
1. リポジトリをクローン
2. Android Studio Ladybug以降で開く
3. Gradle同期 → 実機/エミュでそのまま実行OK！

■ ロードマップ
- [ ] クイズ画面・フローの完成
- [ ] 初期500～1000問の投入
- [ ] 投稿・審査システム
- [ ] 医学生向けクローズドβテスト
- [ ] 2025年末 Google Play公開

医学の問題提供、バグ報告、デザインや機能のフィードバック…なんでも大歓迎です！  
まだまだ未完成ですが、みなさんと一緒に「医学生の毎日の相棒」になるアプリに育てていきたいです。

見てくれてありがとうございました！  
～Arda（オスマニエ・コルクト・アタ大学 コンピュータ工学科）
