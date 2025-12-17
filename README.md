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

Feedback, bug reports, and especially question submissions are very welcome. This project is a work in progress, and I’m happy to collaborate with fellow developers to make this a tool that students from all disciplines use every day!

Thanks for checking out the project!

~ Arda  
Computer Engineering – Osmaniye Korkut Ata University

# 中文版本

## OmniLearn – 学生的终极学习伴侣

你好！
我是一名大三计算机工程专业的学生，目前正在全力开发一款名为 OmniLearn 的严肃、生产级 Android 应用，计划于 2025 年底在 Google Play 上正式发布。

## 项目目的
目前市面上的大多数测验和教育类 App 都存在广告过多、界面体验差、内容过时或性能不佳等问题。OmniLearn 从头开始设计，就是为了彻底解决这些痛点：界面简洁清爽、运行极速、完全支持离线使用，并且能够灵活适配各种学科领域。

## 当前进度（早期开发阶段）

- 代码库采用 Kotlin + Jetpack Compose 构建
- 干净的 MVVM 架构（data / ui / vm 包结构清晰）
- 已完成 Room 数据库的 schema 和 DAO 实现
- 使用 Navigation Component 搭建基础页面导航结构
- 动态分类系统已初步就位

## 计划核心功能

- 井然有序的学科分类（编程、数学、科学等多个领域）
- 智能测验系统，每道题都配有详细解析
- 即时反馈与清晰的答案解释
- 支持用户提交题目（经过审核确保质量）
- 强大的搜索和筛选工具
- 书签功能 + 错题追踪，结合间隔重复算法帮助巩固记忆
- 支持明暗主题切换，完整适配 Material You 动态配色
- 彻底的离线优先设计，所有功能无需网络即可流畅使用

## 技术栈

- 100% Kotlin
- Jetpack Compose（现代声明式 UI 框架）
- Room 持久化数据库
- Navigation Component
- Kotlin Coroutines + Flow（异步与响应式编程）
- Hilt 依赖注入
- 完全遵循 Google 推荐的现代 Android 架构规范

### 项目结构
```
app/

 ├─ src/main/

 │      ├─ AndroidManifest.xml                                  // 应用内 ID 卡

 │      │

 │      ├─ java/com/example/omnilearn/

 │      │      │

 │      │      ├─ MainActivity.kt    

 │      │      │

 │      │      ├─ data/                                          // 数据层

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

 │      │      ├─ ui/                                          // 界面层

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

 │      │      └─ vm/                                              // 视图模型层

 │      │             ├─ AddQuestionViewModel.kt

 │      │             ├─ CategoryViewModel.kt

 │      │             ├─ QuestionListViewModel.kt

 │      │             ├─ SettingsViewModel.kt

 │      │             └─ ViewModelFactories.kt

 │

 └─ build.gradle (project)
```
## 如何构建项目
1. 克隆仓库
2. 使用 Android Studio Ladybug（或更高版本）打开项目
3. 同步 Gradle（Android Studio 会自动提示）
4. 在真实设备或模拟器上直接运行 — 无需任何额外配置！
   
## 路线图（Roadmap）
- [ ] 完成完整的测验流程和所有 UI 界面
- [ ] 填充初始题库（目标 500–1000 道优质题目）
- [ ] 实现用户贡献题目与审核系统
- [ ] 与同学进行小范围封闭测试（Closed Beta）
- [ ] 计划于 2026年初 在 Google Play (面向全球用户) 正式上架。
      
非常欢迎大家提供反馈、报告 Bug，特别是提交优质题目！这个项目目前仍在积极开发中，我很乐意与各位开发者一起协作，让 OmniLearn 成为各专业学生每天都会用到的学习工具！

感谢你来看这个项目！

~ Arda
计算机工程专业 – 奥斯曼尼耶科尔库特阿塔大学


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
- [ ] Google Playで正式リリース（目標日期：2026年初）

フィードバック、バグ報告はもちろん、特に問題の投稿を大歓迎しています！
このプロジェクトはまだ開発途中で、他の開発者の方と一緒に協力しながら、全ての学部の学生が毎日使ってくれるツールに育てていきたいと思っています！

プロジェクトを見てくれて本当にありがとう！

～ Arda
コンピュータ工学科 – オスマニエ・コルクト・アタ大学
