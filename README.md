

🛠️ AndOn – Weldshop App

Overview

AndOn is an Android application built in Java using Android Studio, designed to streamline problem reporting, acknowledgement, and resolution in a weldshop. It follows lean manufacturing principles to minimize downtime and improve communication on the shop floor.

Operators can raise alerts when issues arise, supervisors can acknowledge them, and maintenance or quality teams can resolve them while documenting the actions taken.



📲 Key Features

Raise a Problem
Welders or operators can easily raise issues by selecting a problem type, adding a description, and optionally attaching photos.

Acknowledge the Problem
Supervisors or team leads can acknowledge problems to indicate that the issue is being investigated or resolved.

Resolve and Document
The issue can be marked as resolved with notes on the corrective actions taken.

Problem History Log
View the full list of past issues, including status (raised, acknowledged, resolved), timestamps, and user actions. 




🧭 Workflow Example

1. Operator reports a broken welding gun at Station A.


2. Supervisor acknowledges the problem from the app.


3. Technician resolves the issue and logs "Replaced welding torch head – tested OK".


4. System marks the problem as resolved and stores the complete history.




⚙️ Tech Stack

Platform: Android

Language: Java

IDE: Android Studio

Database: Google Sheets AppScripts API

UI: Material Design components



🚀 Getting Started

Prerequisites

Android Studio (latest stable version)

Java 8+

Android SDK


Installation

1. Clone the repository:

git clone https://github.com/your-org/andon-weldshop-android.git

2. Open the project in Android Studio.


3. Build the project and run it on an emulator or physical device.



🔐 Permissions

To function properly, the app may request:

Internet (for syncing if using online backend)



📄 License

MIT License
© 2025 MADHAV DAVE

