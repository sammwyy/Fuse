@echo off
:loop
cls
java -jar ..\fuse_server\build\libs\Fuse_server-all.jar server
pause
goto loop