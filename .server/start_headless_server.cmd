@echo off
:loop
cls
set FUSE_QUERY_MOTD="&dA Fuse Server in headless mode"
java -jar ..\fuse_server\build\libs\Fuse_server-all.jar server --headless
pause
goto loop