@echo off
setlocal enabledelayedexpansion
 
 
rem ++++++++++++++++++++++++++++++++++++
rem pp：pre process，预处理
rem
rem 处理git忽略空目录的问题
rem 注意：
rem 1、该文件放在git项目根目录，在git add之前执行该文件
rem 2、在空目录下会新增文件，新增.gitignore会忽略平级所有文件的提交
rem 3、处理过程：首先删除所有.gitignore，然后查找所有空目录并新增文件
rem
rem ++++++++++++++++++++++++++++++++++++
 
(
 
for /r %%i in (*.gitignore) do (
    if not %%~fi == %cd%\.gitignore (
     
        echo %%~fi  -- prepare to delete
        del "%%~fi"
        if ERRORLEVEL 0 (
            echo %%~fi -- success to delete
        ) else (
            echo %%~fi -- fail to delete
        )
    )
)
 
del pp_dir_all > nul 2>nul
del pp_dir_filter > nul 2>nul
 
 
for /d %%i in (*) do (
    for /f "delims=" %%j in ('dir /s /b /ad "%%~fi"') do (
        echo %%~fj -- prepare to collect
        echo %%j>> pp_dir_all
    )
)
 
if exist pp_dir_all (
    for /f "delims=" %%i in (pp_dir_all) do (
        echo %%i -- prepare to check children count
        set /a children_count=0
        for /f "delims=" %%j in ('dir /b "%%~fi"') do (
            echo %%~fj -- print children
            set /a children_count+=1
        )
        echo %%i -- child_count: !children_count!
        if !children_count! == 0 (
        echo %%i -- none children
        echo %%~fi>> pp_dir_filter
     )
    )
)
 
if exist pp_dir_filter (
    for /f "delims=" %%i in (pp_dir_filter) do (
        echo %%i -- prepare to create .gitignore file
        echo * > "%%i\.gitignore"
        if ERRORLEVEL 0 ( 
            echo %%~fi -- success to create 
        ) else ( 
            echo %%~fi -- fail to create 
        )
    )
)
 
del pp_dir_all > nul 2>nul
del pp_dir_filter > nul 2>nul
 
rem echo. & pause 
 
 
rem ) > nul 2>nul
) > pplog.txt
 
 
echo. >> pplog.txt
echo. >> pplog.txt
echo. >> pplog.txt
echo. >> pplog.txt
echo. ** current .gitignore list>> pplog.txt
echo. >> pplog.txt
 
for /r %%i in (*.gitignore) do (
    echo %%~fi >> pplog.txt
)