@echo off
:loop
cls
java -jar ..\fuse_server\build\libs\Fuse_server-all.jar anvil ./anvil_world ./worlds/world.polar
pause
goto loop