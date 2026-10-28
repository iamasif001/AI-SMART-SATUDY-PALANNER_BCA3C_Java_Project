# AI-Based Smart Study Planner

A Java Swing desktop application for organizing student study activities. The application uses Oracle Database through JDBC and includes study planning, subject and exam records, study sessions, progress tracking, notes, study-material management, and an AI-generated practice quiz flow.

## Technology Stack
- Java (Swing desktop UI)
- Oracle Database 21c XE (PDB/service used by this project: `XEPDB1`)
- JDBC / Oracle JDBC driver
- Apache Tika for extracting text from supported study materials
- Gemini API integration through an environment variable (AI features require a valid API key and compatible model access)

## Main Modules
- Student registration and login
- Subject management
- Exam management
- Study plans and study sessions
- Progress tracking
- Notes
- Study material upload/metadata and text extraction
- AI practice quiz and quiz-history/results

The feature list reflects the Java source files present in the repository. Actual behavior can depend on database setup, API credentials, and the runtime environment.

## Database
The project connects to the local Oracle service `localhost:1521/XEPDB1` using the `STUDYPLANNER` schema. The schema script is in `Database/schema.sql` and also copied to `src/resources/schema.sql`.

> Important: The provided schema script documents the project's tables and constraints. Run it only in a new/empty schema after reviewing it; do not run it against a populated database because the tables may already exist.

## Configure the Database Password
The application reads the database password from the environment variable `STUDYPLANNER_DB_PASSWORD`. Do not hard-code or commit passwords.

Windows Command Prompt (for the current terminal session):
```bat
set STUDYPLANNER_DB_PASSWORD=your_database_password
```

PowerShell (for the current terminal session):
```powershell
$env:STUDYPLANNER_DB_PASSWORD = "your_database_password"
```

## Configure Gemini API Access
Set `GEMINI_API_KEY` in the environment before running AI features. Never commit a real API key to GitHub. AI functionality may fail if the key is missing, the model identifier is unavailable to the account, or network access is blocked.

## Build / Run
This GitHub ZIP contains Java source code, not a ready-to-run packaged application. The repository ZIP does not include the local JAR files that may be needed for compilation.

1. Install a compatible JDK.
2. Install/configure Oracle Database and create/use the `STUDYPLANNER` schema.
3. Configure the Oracle JDBC driver and Apache Tika libraries in the IDE/build path.
4. Set `STUDYPLANNER_DB_PASSWORD` and, if using AI functionality, `GEMINI_API_KEY`.
5. Open the project in an IDE such as IntelliJ IDEA or Eclipse, or configure the classpath in your chosen Java environment.
6. Run `com.smartstudy.app.Main`.

## Project Structure
- `src/main/java/com/smartstudy/app/Main.java` — application entry point
- `config/` — database configuration
- `dao/` — database access objects
- `model/` — data models
- `service/` — service layer
- `ai/` — Gemini integration
- `ui/` — Swing forms
- `src/resources/schema.sql` — database schema script

## Security Notes
- Use environment variables for database passwords and API keys.
- Do not publish database credentials, API keys, personal student records, or populated database exports.
- The application currently expects a local Oracle Database service; update `DatabaseConfig.java` if your setup differs.
