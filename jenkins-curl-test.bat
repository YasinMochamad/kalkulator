@echo off
REM ============================================================
REM Jenkins cURL Test Script - Calculator API
REM Dipanggil oleh Jenkins Pipeline
REM ============================================================

SET LOGDIR=D:\AI\test1\sonar test\calculator\jenkins-logs
SET LOGFILE=%LOGDIR%\curl-test-result.log
SET BASE_URL=http://localhost:8080

IF NOT EXIST "%LOGDIR%" mkdir "%LOGDIR%"

echo ============================================================ > "%LOGFILE%"
echo  Calculator API - cURL Test Result >> "%LOGFILE%"
echo  Date   : %DATE% %TIME% >> "%LOGFILE%"
echo  Base URL: %BASE_URL% >> "%LOGFILE%"
echo ============================================================ >> "%LOGFILE%"
echo. >> "%LOGFILE%"

echo [01] POST /api/calculator/add >> "%LOGFILE%"
echo Request Body: {"a": 10, "b": 5} >> "%LOGFILE%"
echo Response: >> "%LOGFILE%"
curl -s -X POST %BASE_URL%/api/calculator/add -H "Content-Type: application/json" -d "{\"a\": 10, \"b\": 5}" >> "%LOGFILE%"
echo. >> "%LOGFILE%"
echo. >> "%LOGFILE%"

echo [02] POST /api/calculator/subtract >> "%LOGFILE%"
echo Request Body: {"a": 10, "b": 5} >> "%LOGFILE%"
echo Response: >> "%LOGFILE%"
curl -s -X POST %BASE_URL%/api/calculator/subtract -H "Content-Type: application/json" -d "{\"a\": 10, \"b\": 5}" >> "%LOGFILE%"
echo. >> "%LOGFILE%"
echo. >> "%LOGFILE%"

echo [03] POST /api/calculator/multiply >> "%LOGFILE%"
echo Request Body: {"a": 10, "b": 5} >> "%LOGFILE%"
echo Response: >> "%LOGFILE%"
curl -s -X POST %BASE_URL%/api/calculator/multiply -H "Content-Type: application/json" -d "{\"a\": 10, \"b\": 5}" >> "%LOGFILE%"
echo. >> "%LOGFILE%"
echo. >> "%LOGFILE%"

echo [04] POST /api/calculator/divide >> "%LOGFILE%"
echo Request Body: {"a": 10, "b": 5} >> "%LOGFILE%"
echo Response: >> "%LOGFILE%"
curl -s -X POST %BASE_URL%/api/calculator/divide -H "Content-Type: application/json" -d "{\"a\": 10, \"b\": 5}" >> "%LOGFILE%"
echo. >> "%LOGFILE%"
echo. >> "%LOGFILE%"

echo [04-ERR] POST /api/calculator/divide (divide by zero - expected 400) >> "%LOGFILE%"
echo Request Body: {"a": 10, "b": 0} >> "%LOGFILE%"
echo Response: >> "%LOGFILE%"
curl -s -X POST %BASE_URL%/api/calculator/divide -H "Content-Type: application/json" -d "{\"a\": 10, \"b\": 0}" >> "%LOGFILE%"
echo. >> "%LOGFILE%"
echo. >> "%LOGFILE%"

echo ============================================================ >> "%LOGFILE%"
echo  cURL Test SELESAI >> "%LOGFILE%"
echo ============================================================ >> "%LOGFILE%"

echo.
echo === Hasil cURL (preview) ===
type "%LOGFILE%"

exit /b 0
