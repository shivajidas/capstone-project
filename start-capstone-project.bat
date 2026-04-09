@echo off
echo ========================================
echo Starting Capstone Project Services
echo ========================================

echo.
echo Starting Config Server...
wt -w 0 nt --title "Config Server" cmd /k "cd /d %~dp0confg-server && call build-and-run.bat"
call :wait_for_port 8888 "Config Server"

echo Starting Gateway API Service...
wt -w 0 nt --title "Gateway API Service" cmd /k "cd /d %~dp0gateway-api-service && call build-and-run.bat"
call :wait_for_port 8080 "Gateway API Service"

echo Starting Order Service...
wt -w 0 nt --title "Order Service" cmd /k "cd /d %~dp0order-service && call build-and-run.bat"
call :wait_for_port 9094 "Order Service"

echo Starting Product Service...
wt -w 0 nt --title "Product Service" cmd /k "cd /d %~dp0product-service && call build-and-run.bat"
call :wait_for_port 9093 "Product Service"

echo Starting Customer Service...
wt -w 0 nt --title "Customer Service" cmd /k "cd /d %~dp0customer-service && call build-and-run.bat"
call :wait_for_port 9091 "Customer Service"

echo.
echo ========================================
echo All services started successfully
echo ========================================
goto :eof

:wait_for_port
set PORT=%1
set SERVICE=%~2
echo Waiting for %SERVICE% to start on port %PORT%...
timeout /t 15 /nobreak > nul
:check_port
netstat -an | findstr ":%PORT%" | findstr "LISTENING" > nul
if errorlevel 1 (
    echo   Still waiting for %SERVICE%...
    timeout /t 10 /nobreak > nul
    goto :check_port
)
echo   %SERVICE% is now running on port %PORT%
echo.
goto :eof
