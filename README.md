# English Version

# OmniLearn – The Ultimate Learning Companion for Students

Hi!  
II'm a 3rd-year Computer Engineering student currently developing **OmniLearn** as a serious, production-ready Android application that I plan to publish on Google Play in late 2025.

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

 │      │      │      │      │      └─ QuestionDao.kt

 │      │      │      │      │

 │      │      │      │      ├─ datastore/

 │      │      │      │      │      └─ SettingsDataStore.kt

 │      │      │      │      │     

 │      │      │      │      ├─ entity/

 │      │      │      │      │      ├─ CategoryEntity.kt

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
- [ ] Public release on Google Play (target: late 2025)  

Feedback, bug reports, and especially question submissions are very welcome. This project is a work in progress, and I’m happy to collaborate with fellow developers to make this a tool that students from all disciplines use every day!

Thanks for checking out the project!

~ Arda  
Computer Engineering – Osmaniye Korkut Ata University

# 日本語版

## OmniLearn – 学生のための究極の学習コンパニオン

こんにちは！
現在、コンピュータ工学科3年生で、2025年後半にGoogle Playで正式リリース予定の本格的なAndroidアプリケーション **OmniLearn** を開発しています。

## 開発の目的
現在の多くのクイズ・教育アプリは、過剰な広告、使いにくいUI/UX、古くなった内容、パフォーマンス問題などに悩まされています。
**OmniLearn** はこれらの問題を根本から解決するためにゼロから構築されています。クリーンなデザイン、高速動作、完全オフライン対応、そしてあらゆる学問分野に適応可能なアプリを目指しています。

## 現在の進捗（開発初期段階）

- Kotlin + Jetpack Compose で実装
- クリーンなMVVMアーキテクチャ（data / ui / vm パッケージ構成）
- RoomデータベースのスキーマとDAOを実装済み
- Navigation Componentによる基本画面構成
- 動的なカテゴリシステム

## 予定しているコア機能

- プログラミング、数学、科学など、整理された豊富なカテゴリ
- すべての問題に詳細な解説付きのスマートクイズシステム
- 即時フィードバックとわかりやすい正解の根拠表示
- ユーザー投稿問題機能（品質管理のためのモデレーション付き）
- 高機能な検索・フィルタリング
- お気に入り登録＆間違い追跡＋間隔反復学習（Spaced Repetition）
- ライト／ダークテーマ対応、Material You完全サポート
- 完全オフライン優先設計

## 技術スタック

100% Kotlin
Jetpack Compose
Room Persistence Library
Navigation Component
Kotlin Coroutines + Flow
Hilt 依存性注入
Google推奨の最新Androidアーキテクチャ準拠

### プロジェクト構成
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
## すぐに試すには
1. リポジトリをクローンする
2. Android Studio Ladybug 以降で開く
3. Gradleを同期する
4. デバイスまたはエミュレーターで実行 – 追加の設定は一切不要です

## ロードマップ
- [ ] クイズの流れと全UI画面を完成させる
- [ ] 初期問題集を追加（500～1000問）
- [ ] 問題投稿＆モデレーションシステムを実装
- [ ] 同学年の学生たちとクローズドβテスト
- [ ] Google Playで正式リリース（目標：2025年後半）

フィードバック、バグ報告はもちろん、特に問題の投稿を大歓迎しています！
このプロジェクトはまだ開発途中で、他の開発者の方と一緒に協力しながら、全ての学部の学生が毎日使ってくれるツールに育てていきたいと思っています！

プロジェクトを見てくれて本当にありがとう！

～ Arda
コンピュータ工学科 – オスマニエ・コルクト・アタ大学
