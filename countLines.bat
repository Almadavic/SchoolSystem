@echo off
SET count=1
FOR /f "tokens=*" %%G IN ('dir "%CD%\src\*.java" /b /s') DO (type "%%G") >> lines.txt
SET count=1
FOR /f "tokens=*" %%G IN ('type lines.txt') DO (set /a lines+=1)
echo Your Project has currently totaled %lines% lines of code. 
del lines.txt
PAUSE