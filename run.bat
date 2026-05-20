@echo off
chcp 65001 > nul
title Plants vs Zombies - OOP Lab

:: Định nghĩa thư mục
set SRC_DIR=src
set OUT_DIR=out
set LIB_DIR=lib

:: Tạo thư mục out nếu chưa tồn tại
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

echo ========================================
echo   Plants vs Zombies - Compiler
echo ========================================
echo.
echo [1/3] Dang tim file nguon...

:: Tìm tất cả file .java trong thư mục src
dir /s /b "%SRC_DIR%\*.java" > sources.txt

echo [2/3] Dang bien dich...

:: Biên dịch vào thư mục out
javac -cp "%SRC_DIR%" -d "%OUT_DIR%" @sources.txt 2> compile_error.txt

:: Kiểm tra lỗi biên dịch
if %errorlevel% neq 0 (
    echo.
    echo ====== LOI BIEN DICH ======
    echo.
    type compile_error.txt
    del sources.txt compile_error.txt 2> nul
    echo.
    echo Nhan phim bat ky de thoat...
    pause > nul
    exit /b 1
)

del sources.txt compile_error.txt 2> nul

echo [3/3] Bien dich thanh cong!
echo.
echo Dang chay game...

:: Chạy game từ thư mục out
java -cp "%OUT_DIR%" Main

:: Kiểm tra lỗi khi chạy
if %errorlevel% neq 0 (
    echo.
    echo ====== LOI KHI CHAY ======
    echo.
    echo Nhan phim bat ky de thoat...
    pause > nul
)

:: Xóa thư mục out? (tùy chọn, bỏ comment dòng dưới nếu muốn)
:: rmdir /s /q "%OUT_DIR%" 2> nul