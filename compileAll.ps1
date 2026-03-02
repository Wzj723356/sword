# 构建classpath
$dependencies = Get-Content "dependencies.txt"
$classpath = [string]::Join(";", $dependencies)

# 创建输出目录
New-Item -ItemType Directory -Force -Path "build\classes\java\main"

# 编译所有Java源文件
$sourceFiles = Get-Content "sourceFiles.txt"
javac -cp $classpath -d "build\classes\java\main" $sourceFiles

Write-Host "Compilation completed!"