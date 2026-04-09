@echo off
echo ========================================
echo Deleting target folder...
echo ========================================
if exist target (
    rmdir /s /q target
    echo Target folder deleted.
) else (
    echo Target folder does not exist.
)

:check_deleted
if exist target (
    echo Waiting for target folder to be deleted...
    timeout /t 1 /nobreak > nul
    goto :check_deleted
)
echo Target folder cleanup complete.

echo.
echo ========================================
echo Running mvn clean install...
echo ========================================
call mvn clean install -DskipTests
if %ERRORLEVEL% neq 0 (
    echo Maven build failed!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Starting application...
echo ========================================
call mvn spring-boot:run
