@echo off
chcp 65001 > nul
cd /d "%~dp0"
title Plants vs Zombies - OOP Lab

:: Định nghĩa thư mục
set SRC_DIR=src
set OUT_DIR=compiled_classes

:: Tạo thư mục compiled_classes nếu chưa tồn tại
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

echo ========================================
echo   Plants vs Zombies - Compiler
echo ========================================
echo.
echo [1/3] Dang tim file nguon...

:: Kiểm tra tồn tại thư mục src
if not exist "%SRC_DIR%\Main.java" (
    echo Khong tim thay %SRC_DIR%\Main.java
    echo Vui long chay file bat trong thu muc goc cua project
    pause
    exit /b 1
)

:: Tìm tất cả file .java
dir /s /b "%SRC_DIR%\*.java" > sources.txt 2> nul

:: Kiểm tra file sources.txt có trống không
for %%A in (sources.txt) do if %%~zA==0 (
    echo Khong tim thay file .java nao!
    del sources.txt 2> nul
    pause
    exit /b 1
)

echo [2/3] Dang bien dich vao thu muc %OUT_DIR%...

:: Biên dịch
javac -cp "%SRC_DIR%" -d "%OUT_DIR%" @sources.txt 2> compile_error.txt

if %errorlevel% neq 0 (
    echo.
    echo ====== LOI BIEN DICH ======
    echo.
    type compile_error.txt
    del sources.txt compile_error.txt 2> nul
    echo.
    pause
    exit /b 1
)

del sources.txt compile_error.txt 2> nul

echo [3/3] Bien dich thanh cong!
echo.
echo Dang chay game...

:: Chạy game
java -cp "%OUT_DIR%" Main

if %errorlevel% neq 0 (
    echo.
    echo ====== LOI KHI CHAY ======
    echo.
    pause
)