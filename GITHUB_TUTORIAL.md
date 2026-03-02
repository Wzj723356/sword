# GitHub上传教程：修仙模组项目

## 1. 准备工作

### 1.1 安装Git
- **Windows用户**：
  - 访问 [Git官网](https://git-scm.com/downloads)
  - 下载并运行Git for Windows安装程序
  - 按照默认选项安装（可勾选"Use Git from the Windows Command Prompt"）
  - 安装完成后，打开命令提示符验证：`git --version`

- **Mac用户**：
  - 打开终端，执行：`xcode-select --install`
  - 或使用Homebrew：`brew install git`

- **Linux用户**：
  - Ubuntu/Debian：`sudo apt install git`
  - CentOS/Fedora：`sudo yum install git`

### 1.2 注册GitHub账户
- 访问 [GitHub官网](https://github.com)
- 点击"Sign up"注册新账户
- 验证邮箱并设置账户信息

## 2. 项目准备

### 2.1 检查项目状态
- 确保你的修仙模组项目已经完成并构建成功
- 确认所有必要文件都已包含在项目中

### 2.2 创建.gitignore文件
在项目根目录创建`.gitignore`文件，添加以下内容：
```gitignore
# Gradle
.gradle/
build/

# IDE
.idea/
*.iml
*.ipr
*.iws

# OS
.DS_Store
Thumbs.db

# Local files
local.properties

# Logs
logs/
*.log
```

## 3. 初始化Git仓库

### 3.1 进入项目目录
```bash
cd C:\Users\wangz\MCreatorWorkspaces\sword
```

### 3.2 初始化仓库
```bash
git init
```

### 3.3 添加文件
```bash
git add .
```

### 3.4 初始提交
```bash
git commit -m "初始提交：修仙模组 v1.1.2"
```

## 4. 在GitHub上创建仓库

### 4.1 登录GitHub
- 访问 [GitHub官网](https://github.com) 并登录

### 4.2 创建新仓库
- 点击右上角的 `+` 按钮
- 选择 "New repository"
- 填写仓库信息：
  - **Repository name**：sword-mod（或其他名称）
  - **Description**：Minecraft 修仙主题模组，包含完整的修仙系统、技能系统、武器系统等
  - **Visibility**：选择 "Public" 或 "Private"
  - 勾选 "Add a README file"（可选）
  - 点击 "Create repository"

## 5. 关联远程仓库

### 5.1 复制仓库URL
- 在新创建的GitHub仓库页面，点击 "Code" 按钮
- 复制HTTPS或SSH链接

### 5.2 关联远程仓库
```bash
# 使用HTTPS


```

### 5.3 推送代码
```bash
git push -u origin master
```

**注意**：首次推送时，Git会提示你输入GitHub账户的用户名和密码。如果使用HTTPS且遇到认证问题，建议使用SSH密钥。

## 6. 使用SSH密钥（推荐）

### 6.1 生成SSH密钥
```bash
ssh-keygen -t ed25519 -C "your_email@example.com"
```
- 按Enter键保持默认路径
- 设置密码（可选）

### 6.2 添加SSH密钥到GitHub
- 复制SSH公钥内容：
  - Windows：`cat C:\Users\你的用户名\.ssh\id_ed25519.pub`
  - Mac/Linux：`cat ~/.ssh/id_ed25519.pub`
- 登录GitHub，进入 "Settings" → "SSH and GPG keys"
- 点击 "New SSH key"
- 粘贴公钥内容，设置标题
- 点击 "Add SSH key"

### 6.3 使用SSH URL关联仓库
```bash
git remote remove origin
git remote add origin git@github.com:你的用户名/sword-mod.git
git push -u origin master
```

## 7. 上传构建产物

### 7.1 创建Releases页面
- 在GitHub仓库页面，点击 "Releases" 标签
- 点击 "Draft a new release"

### 7.2 填写发布信息
- **Tag version**：v1.1.2
- **Release title**：修仙模组 v1.1.2
- **Description**：添加发布说明，包括新功能、修复内容等

### 7.3 上传构建文件
- 滚动到 "Attach binaries by dropping them here or selecting them"
- 点击选择文件，上传 `sword-1.1.2.jar` 文件
- 点击 "Publish release"

## 8. 后续维护

### 8.1 提交新修改
```bash
# 查看修改

git status

# 添加修改

git add .

# 提交修改

git commit -m "更新：添加新功能"

# 推送修改

git push
```

### 8.2 分支管理
```bash
# 创建分支
git checkout -b feature/new-skill

# 切换分支
git checkout master

# 合并分支
git merge feature/new-skill

# 删除分支
git branch -d feature/new-skill
```

## 9. 常见问题

### 9.1 Git命令不被识别
- **原因**：Git未安装或未添加到环境变量
- **解决方案**：重新安装Git，确保勾选"Add Git to PATH"

### 9.2 推送失败，提示认证错误
- **原因**：HTTPS认证失败或SSH密钥未正确设置
- **解决方案**：
  - 使用SSH密钥认证
  - 或使用GitHub CLI：`gh auth login`

### 9.3 推送失败，提示权限被拒绝
- **原因**：GitHub账户没有仓库的写入权限
- **解决方案**：检查仓库权限设置，确保你是仓库所有者或有写入权限

### 9.4 大文件推送失败
- **原因**：GitHub对单个文件大小有限制（最大100MB）
- **解决方案**：使用Git LFS（Large File Storage）或只推送必要文件

## 10. 高级技巧

### 10.1 使用GitHub Desktop
- 下载 [GitHub Desktop](https://desktop.github.com)
- 图形化界面操作，更适合初学者
- 支持可视化提交历史和分支管理

### 10.2 使用GitHub Actions
- 在仓库中创建 `.github/workflows/build.yml` 文件
- 配置自动构建和测试
- 示例配置：
```yaml
name: Build

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'adopt'
    - name: Build with Gradle
      run: ./gradlew build
    - name: Upload build artifacts
      uses: actions/upload-artifact@v2
      with:
        name: sword-mod
        path: build/libs/*.jar
```

### 10.3 添加README文件
在项目根目录创建 `README.md` 文件，添加项目介绍：
```markdown
# Let's Cultivate immortality

Minecraft 修仙主题模组，为游戏添加完整的修仙系统。

## 功能特性
- ✅ 完整的修仙等级系统
- ✅ 灵力自动恢复系统
- ✅ 多种武器和功法
- ✅ 灵力驱动的技能系统
- ✅ 丹药系统
- ✅ 阵法系统
- ✅ 修仙HUD界面

## 安装要求
- Minecraft 1.20.1
- Fabric Loader 0.14.21+
- Fabric API 0.92.2+1.20.1

## 下载
[最新版本](https://github.com/你的用户名/sword-mod/releases)

## 开发
### 构建项目
```bash
./gradlew build
```

### 运行测试
```bash
./gradlew test
```

## 贡献
欢迎提交Issue和Pull Request！

## 许可证
MIT License
```

## 11. 总结

通过以上步骤，你已经成功将修仙模组项目上传到GitHub，并创建了完整的发布版本。现在其他开发者和玩家可以：
- 浏览你的代码
- 下载最新版本的模组
- 提交问题和建议
- 甚至参与项目开发

GitHub不仅是代码托管平台，也是展示你项目的窗口。一个良好的GitHub仓库可以吸引更多人关注你的作品，获得更多反馈和贡献。

祝你在GitHub上的项目越来越成功！🧙‍♂️✨