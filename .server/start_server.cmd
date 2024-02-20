@echo off
:loop
cls
copy ..\example_plugin\build\\libs\example_plugin.jar .\plugins /Y
java -jar ..\fuse_server\build\libs\Fuse_server-all.jar server
pause
goto loop