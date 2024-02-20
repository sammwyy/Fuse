@echo off
:loop
cls
java -jar ..\build\libs\Fuse-all.jar anvil ./anvil_world ./worlds/world.polar
pause
goto loop