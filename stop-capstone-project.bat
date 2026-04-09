@echo off
echo ========================================
echo Stopping Capstone Project Services
echo ========================================

echo.
echo Stopping Customer Service (port 9091)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :9091 ^| findstr LISTENING') do taskkill /PID %%a /F 2>nul

echo Stopping Product Service (port 9093)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :9093 ^| findstr LISTENING') do taskkill /PID %%a /F 2>nul

echo Stopping Order Service (port 9094)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :9094 ^| findstr LISTENING') do taskkill /PID %%a /F 2>nul

echo Stopping Gateway API Service (port 8080)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080 ^| findstr LISTENING') do taskkill /PID %%a /F 2>nul

echo Stopping Config Server (port 8888)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8888 ^| findstr LISTENING') do taskkill /PID %%a /F 2>nul

echo.
echo ========================================
echo All services stopped
echo ========================================
pause
