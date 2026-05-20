@echo off
chcp 65001 > nul
title Plants vs Zombies - OOP Lab

echo Compiling game...

cd /d "C:\Users\thedu\Downloads\PVZUpdate\PVZ_OOP-LAB_project-master"

dir /s /b src\*.java > sources.txt

javac -cp ".;src" -d . @sources.txt 2> compile_error.txt

if %errorlevel% neq 0 (
    echo.
    echo ====== COMPILATION ERROR ======
    type compile_error.txt
    del sources.txt compile_error.txt 2> nul
    echo.
    pause
    exit /b 1
)

del sources.txt compile_error.txt 2> nul

echo Compilation successful!
echo Running game...

java -cp . Main

if %errorlevel% neq 0 (
    echo.
    echo Game encountered an error while running!
    pause
)