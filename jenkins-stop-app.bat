@echo off
REM ============================================================
REM Jenkins Stop App Script - Matikan app di port 8080
REM ============================================================
echo Mencari proses yang berjalan di port 8080...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
    echo Killing PID: %%a
    taskkill /PID %%a /F
)

echo Done - App dihentikan.
exit /b 0
