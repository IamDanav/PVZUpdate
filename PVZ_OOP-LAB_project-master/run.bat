@echo off
chcp 65001 > nul
cd /d "%~dp0"
title Plants vs Zombies - OOP Lab

:: Define folders
set SRC_DIR=src
set OUT_DIR=compiled_classes

:: Create compiled_classes folder if it doesn't exist
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

echo ========================================
echo   Plants vs Zombies - Compiler
echo ========================================
echo.
echo [1/3] Finding source files...

:: Check if src folder exists
if not exist "%SRC_DIR%\Main.java" (
    echo Cannot find %SRC_DIR%\Main.java
    echo Please run this batch file in the project root folder
    pause
    exit /b 1
)

:: Find all .java files
dir /s /b "%SRC_DIR%\*.java" > sources.txt 2> nul

:: Check if sources.txt is empty
for %%A in (sources.txt) do if %%~zA==0 (
    echo No .java files found!
    del sources.txt 2> nul
    pause
    exit /b 1
)

echo [2/3] Compiling into %OUT_DIR% folder...

:: Compile
javac -cp "%SRC_DIR%" -d "%OUT_DIR%" @sources.txt 2> compile_error.txt

if %errorlevel% neq 0 (
    echo.
    echo ====== COMPILATION ERROR ======
    echo.
    type compile_error.txt
    del sources.txt compile_error.txt 2> nul
    echo.
    pause
    exit /b 1
)

del sources.txt compile_error.txt 2> nul

echo [3/3] Compilation successful!
echo.
echo Running game...

:: Run game
java -cp "%OUT_DIR%" Main

if %errorlevel% neq 0 (
    echo.
    echo ====== RUNTIME ERROR ======
    echo.
    pause
)