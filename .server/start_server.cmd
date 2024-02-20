@echo off
:loop
cls
java -jar ..\build\libs\Fuse-all.jar server
pause
goto loop