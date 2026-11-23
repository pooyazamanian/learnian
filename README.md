<div align="center">

  # 🦉 Learnian (لرنین)
  ### *Next-Gen AI-Powered & Scientifically Backed Vocabulary Learning Platform*

  [![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/docs/multiplatform.html)
  [![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
  [![Android Target SDK](https://img.shields.io/badge/Android-SDK%2037-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
  [![iOS Support](https://img.shields.io/badge/iOS-15.0%2B-000000?style=for-the-badge&logo=apple&logoColor=white)](https://developer.apple.com/ios/)
  [![Architecture](https://img.shields.io/badge/Architecture-MVI%20%2B%20Clean-FF6F00?style=for-the-badge)](https://developer.android.com/topic/architecture)
  [![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

  <br/>

  <p align="center">
    <b>Learnian</b> is a modern, cross-platform language learning application designed to revolutionize vocabulary acquisition using <b>Free Spaced Repetition Scheduling (FSRS)</b>, cognitive mnemonics, AI-driven coaching, and a custom 3D Claymorphism design system.
  </p>

  [Explore Features](#-key-features) •
  [Architecture](#-architecture--module-structure) •
  [Tech Stack](#%EF%B8%8F-technology-stack) •
  [Sponsorship & Grants](#-sponsorship--funding-proposal) •
  [Getting Started](#-getting-started)

</div>

---

## 🌟 Executive Overview & Mission

Traditional vocabulary learning apps rely on rigid flashcards or outdated SuperMemo (SM-2) algorithms that result in high drop-off rates and inefficient study sessions. 

**Learnian** bridges cognitive neuroscience with cutting-edge mobile engineering. Built from the ground up using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, Learnian delivers native multi-platform experiences on Android and iOS while sharing 90%+ of its codebase—including business logic, UI components, data persistence, and networking.

### Why Learnian?
* **Scientific Spaced Repetition (FSRS)**: Uses the modern Free Spaced Repetition Scheduler algorithm, dynamically adjusting review intervals according to individual memory retention curves.
* **AI Owl Coach**: An intelligent, adaptive study assistant that provides real-time tips, adaptive streak management, and personalized motivation.
* **Mnemonic & Etymological Insights**: Every word includes etymology (word roots/derivatives), stories/hooks (رمزگردانی), audio pronunciations, and real-world collocations.
* **Custom Claymorphism UI**: A pixel-perfect, tactile 3D UI component library (`core-view`) crafted exclusively for Compose Multiplatform.

---

## ✨ Key Features

| Feature | Description |
| :--- | :--- |
| 🧠 **FSRS Review Engine** | Smart flashcard queue driven by FSRS ratings (*Again, Hard, Good, Easy*), reducing study time while maximizing long-term memory. |
| 🦉 **Owl Coach AI** | Interactive AI companion analyzing user performance to offer tailored recommendations and daily goals. |
| 📖 **Deep Word Breakdowns** | Detailed vocabulary screens containing mnemonics, etymology trees, part-of-speech tags, collocations, and contextual example sentences. |
| 🎨 **Clay Design System** | Rich suite of custom 3D UI elements (`ClayCard`, `ClayButton`, `ClayTopBar`, `ClayBottomBar`, `ClayToast`, `ClayStatCard`, `ClaySegmentedSlider`). |
| ⚡ **Offline-First & Sync** | SQLDelight SQLite storage guarantees immediate responsiveness offline, seamlessly syncing with Ktor 3 backend services. |
| 🛡️ **Clean Architecture & MVI** | Strict unidirectional data flow (Intent → State → SideEffect) for bulletproof state management and testability. |

---

## 📱 Visual Showcase & UI Gallery

> *Experience the custom Claymorphism design system across key application screens.*

| Intro & Onboarding | Home Dashboard | Interactive FSRS Review | Word Detail Deep-Dive |
| :---: | :---: | :---: | :---: |
| ![Intro Screen](docs/screenshots/intro.png) | ![Home Dashboard](docs/screenshots/home.png) | ![Review Screen](docs/screenshots/review.png) | ![Word Detail Screen](docs/screenshots/detail.png) |
| *Adaptive introduction & feature highlights* | *Daily streak, Owl Coach tip & progress stats* | *Interactive FSRS card rating interface* | *Etymology, mnemonics & audio breakdown* |

---

## 🏗 Architecture & Module Structure

Learnian follows **Clean Architecture** principles decoupled into feature modules and shared core components.

```text
learnian/
├── androidApp/               # Native Android Application Entrypoint
├── iosApp/                   # Xcode iOS Project & Entrypoint
├── shared/                   # Shared Multiplatform Integration
│
├── core-view/                # Custom Claymorphism UI Library & Design System
├── core-network/             # Ktor 3 HTTP Client, Auth Interceptors & Token Refresher
├── core-database/            # SQLDelight Multiplatform SQLite Database & Drivers
├── core-datastore/           # Encrypted Multiplatform User Settings & Preferences
├── core-system/              # Platform System APIs (Location, Permissions, App Info)
│
└── features/                 # Isolated Feature Modules (MVI Architecture)
    ├── auth/                 # OTP & Phone/Password Authentication
    ├── home/                 # Dashboard, Daily Goals & Owl Coach Cards
    ├── intro/                # Onboarding Carousel & Feature Walkthrough
    ├── profile/              # User Settings, Dark Mode & Learning Preferences
    ├── review/               # FSRS Card Review System & Word Details
    └── splash/               # App Startup, Remote Config & Initialization
```

---

## ⚙️ Technology Stack

| Domain | Technology / Library | Version | Description |
| :--- | :--- | :--- | :--- |
| **Language** | [Kotlin](https://kotlinlang.org/) | `2.4.20` | Modern, concise, type-safe multiplatform language |
| **UI Toolkit** | [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) | `1.12.0` | Declarative UI sharing across Android & iOS |
| **DI** | [Koin](https://insert-koin.io/) | `4.0.0` | Lightweight dependency injection framework |
| **Networking** | [Ktor Client](https://ktor.io/) | `3.6.0` | Asynchronous multiplatform HTTP client |
| **Database** | [SQLDelight](https://cashapp.github.io/sqldelight/) | `2.4.0` | Type-safe SQLite database generation |
| **State Management**| Multiplatform Settings | `1.3.0` | Key-value settings storage across platforms |
| **Build System** | Gradle (AGP) | `9.1.1` | Latest Android Gradle Plugin with JDK 17+ |
| **Android Targets** | SDK 37 (Min 26) | Android 17 | Optimized for modern Android platforms |

---

## 🤝 Sponsorship & Funding Proposal

We are actively seeking **Sponsors, Educational Partners, and Open-Source Grants** to support the continued development and scaling of **Learnian**.

### Value Proposition for Sponsors
1. **EdTech Innovation**: Driving the next generation of cognitive language learning tools accessible globally.
2. **KMP Ecosystem Pioneer**: Learnian serves as a benchmark real-world reference implementation for Kotlin Multiplatform and Compose Multiplatform development.
3. **Open-Source Impact**: Empowering thousands of language learners with free, scientifically proven learning methods.

### How Sponsorship Funds Are Allocated
- 🚀 **On-Device AI Integration**: Expanding local LLM integration (LiteRT / Gemini Nano) for instant context generation and voice practice.
- 🌐 **Platform Expansion**: Bringing Learnian to Desktop (macOS, Windows, Linux) and Web (Wasm).
- 📚 **Curated Content Decks**: Expanding comprehensive vocabulary lists (IELTS, TOEFL, GRE, Business English).
- 🛠️ **Infrastructure & CI/CD**: High-performance multiplatform testing pipelines and cloud sync backend.

### Sponsorship Tiers

| Tier | Monthly Investment | Sponsor Benefits |
| :--- | :---: | :--- |
| **🥉 Bronze Sponsor** | `$50 / mo` | Logo on README & Project Website |
| **🥈 Silver Sponsor** | `$200 / mo` | Logo + Link in App Credits & README |
| **🥇 Gold Sponsor** | `$500 / mo` | Prominent branding, Dedicated Feature Priority & Monthly Update Calls |
| **💎 Enterprise / Main Sponsor** | Custom | Custom Co-Branding, Strategic Partnership & Core Roadmap Influence |

> 📬 **Interested in Sponsoring or Partnering?**  
> Contact us directly at **[pooya.developer@example.com](mailto:pooya.developer@example.com)** or open a GitHub Discussion.

---

## 🚀 Getting Started

### Prerequisites
* **JDK**: Version `17` or higher
* **Android Studio**: `2024.1+` (Ladybug / Jellyfish or newer with KMP Plugin)
* **Xcode**: `15.0+` (Required for iOS builds on macOS)

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/learnian.git
cd learnian
```

### 2. Build & Run Android App
```bash
# Assemble Debug APK
./gradlew :androidApp:assembleDebug

# Install on connected device/emulator
./gradlew :androidApp:installDebug
```

### 3. Run iOS App
Open the `./iosApp` folder in **Xcode** and hit **Run** (`Cmd + R`), or run via Gradle:
```bash
./gradlew :shared:iosSimulatorArm64Test
```

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  <sub>Built with ❤️ using Kotlin Multiplatform & Compose Multiplatform</sub>
</div>
