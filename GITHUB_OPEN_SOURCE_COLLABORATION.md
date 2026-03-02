# GitHub开源共创教程：修仙模组项目

## 1. 开源共创概述

### 1.1 什么是开源共创
- **开源**：代码公开，任何人都可以查看、使用、修改
- **共创**：多人协作开发，共同改进项目
- **优势**：
  - 获得更多创意和想法
  - 更快地发现和修复bug
  - 分散开发工作负担
  - 建立活跃的社区

### 1.2 为什么选择GitHub
- 全球最大的开源平台
- 完善的协作工具
- 强大的版本控制
- 丰富的社区功能

## 2. 项目准备

### 2.1 完善项目结构
确保项目包含以下文件：
- **README.md**：项目介绍和使用说明
- **CONTRIBUTING.md**：贡献指南
- **LICENSE**：开源许可证
- **CODE_OF_CONDUCT.md**：行为准则
- **ISSUE_TEMPLATE.md**：issue模板
- **PULL_REQUEST_TEMPLATE.md**：PR模板

### 2.2 选择开源许可证
推荐选择：
- **MIT License**：最宽松，允许商业使用
- **GNU GPL v3**：要求衍生作品也开源
- **Apache License 2.0**：包含专利授权

对于游戏模组，**MIT License** 是最常用的选择。

## 3. 设置开源仓库

### 3.1 创建GitHub仓库
- 登录GitHub
- 点击 "New repository"
- 填写信息：
  - **Repository name**：sword-mod
  - **Description**：Minecraft修仙主题模组，欢迎大家共同开发！
  - **Visibility**：选择 **Public**
  - 勾选 "Add a README file"
  - 选择 "MIT License"
  - 点击 "Create repository"

### 3.2 推送初始代码
```bash
# 进入项目目录
cd C:\Users\wangz\MCreatorWorkspaces\sword

# 初始化Git
git init

# 添加远程仓库
git remote add origin git@github.com:你的用户名/sword-mod.git

# 添加文件
git add .

# 初始提交
git commit -m "初始提交：开源修仙模组项目"

# 推送到GitHub
git push -u origin master
```

## 4. 编写贡献指南

### 4.1 创建CONTRIBUTING.md文件
```markdown
# 贡献指南

非常感谢你对修仙模组项目的关注和支持！以下是参与贡献的指南。

## 如何贡献

### 1. 报告问题
- 在GitHub上创建新的Issue
- 使用清晰的标题和详细的描述
- 提供复现步骤和环境信息
- 如有可能，附上截图或日志

### 2. 提交代码

#### 步骤1：Fork仓库
- 点击GitHub页面右上角的 "Fork" 按钮
- 将仓库Fork到你的个人账户

#### 步骤2：克隆仓库
```bash
git clone git@github.com:你的用户名/sword-mod.git
cd sword-mod
```

#### 步骤3：创建分支
```bash
git checkout -b feature/你的功能名称
# 或
git checkout -b fix/你的bug修复
```

#### 步骤4：修改代码
- 遵循项目的代码风格
- 编写清晰的代码注释
- 确保代码能够正常构建

#### 步骤5：提交修改
```bash
git add .
git commit -m "简短描述你的修改"
```

#### 步骤6：推送到GitHub
```bash
git push origin 你的分支名称
```

#### 步骤7：创建Pull Request
- 在GitHub页面点击 "Compare & pull request"
- 填写PR标题和描述
- 选择合适的标签
- 点击 "Create pull request"

### 3. 代码规范

#### 命名规范
- 类名：PascalCase（如CultivationManager）
- 方法名：camelCase（如addExperience）
- 变量名：camelCase（如spiritualPower）
- 常量名：SNAKE_CASE（如MAX_LEVEL）

#### 代码风格
- 使用4个空格缩进
- 每行不超过100个字符
- 方法之间空一行
- 逻辑块之间空一行

#### 注释规范
- 类和方法使用Javadoc注释
- 复杂逻辑添加行注释
- 注释使用中文（项目主要面向中文用户）

### 4. 测试要求
- 确保你的修改不会破坏现有功能
- 测试新功能在不同场景下的表现
- 确保构建能够成功完成

## 5. 行为准则

- 使用友好和包容的语言
- 尊重不同的观点和经验
- 优雅地接受建设性批评
- 专注于对社区最有利的事情
- 对其他社区成员表示同理心

## 6. 联系方式

- **GitHub Issues**：项目问题和讨论
- **Discord**：实时交流（如果有）
- **Email**：维护者邮箱（可选）

再次感谢你的贡献！🧙‍♂️✨
```

### 4.2 创建行为准则
创建 `CODE_OF_CONDUCT.md` 文件：
```markdown
# 行为准则

## 我们的承诺

为了创建一个开放和欢迎的环境，我们作为贡献者和维护者承诺，参与我们的项目和社区的所有人都能感受到尊重，无论年龄、体型、残疾、种族、性别认同和表达、经验水平、国籍、个人外貌、种族、宗教或性取向。

## 我们的标准

有助于创建积极环境的行为示例包括：

- 使用友好和包容的语言
- 尊重不同的观点和经验
- 优雅地接受建设性批评
- 专注于对社区最有利的事情
- 对其他社区成员表示同理心

不可接受的行为包括：

- 使用性化的语言或图像，以及性挑逗或性骚扰
- 嘲讽、侮辱或贬损性评论，以及个人或政治攻击
- 公开或私下的骚扰
- 未经明确许可，发布他人的私人信息，如物理或电子邮件地址
- 其他在专业环境中被合理认为不适当的行为

## 维护者的责任

项目维护者负责澄清可接受行为的标准，并应采取适当和公平的纠正措施，以应对任何不可接受的行为实例。

维护者有权利和责任删除、编辑或拒绝与本行为准则不一致的评论、提交、代码、wiki编辑、问题和其他贡献，或暂时或永久禁止任何贡献者从事他们认为不适当、威胁、冒犯或有害的其他行为。

## 适用范围

本行为准则适用于项目空间内的所有互动，以及在公共空间中代表项目或其社区的个人。

代表项目或社区的例子包括使用官方项目电子邮件地址，通过官方社交媒体账号发布，或在在线或离线活动中担任指定代表。

## 执行

可以通过联系项目维护者来报告辱骂、骚扰或其他不可接受的行为。所有投诉将被审查和调查，并将导致被认为必要和适当的回应。

项目团队有义务对事件的记者保密。具体执行政策的更多细节可能会单独发布。

不真诚地遵守或执行行为准则的项目维护者可能面临其他维护者决定的临时或永久影响。

## 归因

本行为准则改编自 [Contributor Covenant][homepage]，版本 2.0，可在 https://www.contributor-covenant.org/version/2/0/code_of_conduct.html 获取。

[homepage]: https://www.contributor-covenant.org

有关此行为准则的常见问题，请参阅 https://www.contributor-covenant.org/faq
```

### 4.3 创建Issue和PR模板

**ISSUE_TEMPLATE.md**：
```markdown
# Issue模板

## 类型
- [ ] Bug报告
- [ ] 新功能请求
- [ ] 改进建议
- [ ] 其他

## 描述
请清晰描述你的问题或建议。

## 复现步骤（Bug报告）
1. 
2. 
3. 

## 期望行为

## 实际行为

## 环境信息
- Minecraft版本：
- 模组版本：
- Fabric API版本：
- 其他相关模组：

## 附加信息
（截图、日志等）
```

**PULL_REQUEST_TEMPLATE.md**：
```markdown
# Pull Request

## 类型
- [ ] Bug修复
- [ ] 新功能
- [ ] 代码优化
- [ ] 文档更新
- [ ] 其他

## 描述
请描述你的修改内容。

## 相关Issue
（如果有，链接相关的issue）

## 修改内容
- 
- 
- 

## 测试情况
- [ ] 本地构建成功
- [ ] 功能测试通过
- [ ] 无回归问题

## 附加信息
（其他需要说明的内容）
```

## 5. 分支策略

### 5.1 推荐分支结构
- **master**：主分支，保持稳定版本
- **develop**：开发分支，集成新功能
- **feature/**：功能分支，开发新特性
- **fix/**：修复分支，修复bug
- **release/**：发布分支，准备新版本

### 5.2 分支管理流程
1. 从 `develop` 分支创建功能分支
2. 在功能分支上开发
3. 开发完成后，创建PR到 `develop` 分支
4. 经过代码审查后合并
5. 准备发布时，从 `develop` 创建 `release` 分支
6. 在 `release` 分支上进行最后的调整
7. 发布后，将 `release` 分支合并到 `master` 和 `develop` 分支

## 6. 社区建设

### 6.1 吸引贡献者
- **完善文档**：清晰的README和贡献指南
- **创建Good First Issues**：为新手准备的简单任务
- **积极回应**：及时回复issues和PR
- **展示贡献者**：在README中列出主要贡献者
- **举办活动**：如代码马拉松、功能征集等

### 6.2 维护社区活跃度
- **定期更新**：保持项目活跃开发
- **发布版本**：定期发布新版本
- **社区互动**：在Discord或其他平台与用户交流
- **庆祝成就**：当项目达到里程碑时庆祝
- **感谢贡献者**：公开感谢所有贡献者

## 7. 版本管理

### 7.1 语义化版本
- **格式**：X.Y.Z
- **X**：主版本号，不兼容的API变更
- **Y**：次版本号，向后兼容的新功能
- **Z**：补丁版本号，向后兼容的bug修复

### 7.2 发布流程
1. 确保所有功能都已完成并测试通过
2. 更新版本号（在build.gradle和fabric.mod.json中）
3. 更新CHANGELOG.md
4. 创建release分支
5. 进行最终测试
6. 构建发布版本
7. 在GitHub上创建Release
8. 上传构建产物
9. 合并到master和develop分支
10. 发布公告

## 8. 处理贡献

### 8.1 代码审查
- **审查内容**：
  - 代码质量和风格
  - 功能实现是否正确
  - 是否有潜在的bug
  - 性能影响
  - 安全性

- **审查流程**：
  1. 分配审查者
  2. 审查者检查代码
  3. 提出修改建议
  4. 贡献者进行修改
  5. 再次审查
  6. 批准合并

### 8.2 处理Issues
- **分类**：使用标签对issues进行分类
- **优先级**：设置优先级（高、中、低）
- **分配**：将issues分配给合适的贡献者
- **跟踪**：使用Projects或Milestones跟踪进度
- **关闭**：问题解决后及时关闭

## 9. 高级功能

### 9.1 使用GitHub Actions
创建 `.github/workflows/build.yml` 文件：
```yaml
name: 构建测试

on:
  push:
    branches: [ master, develop ]
  pull_request:
    branches: [ master, develop ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: 设置JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'adopt'
    - name: 构建项目
      run: ./gradlew build
    - name: 测试
      run: ./gradlew test
    - name: 上传构建产物
      uses: actions/upload-artifact@v4
      with:
        name: sword-mod
        path: build/libs/*.jar
```

### 9.2 使用GitHub Projects
- 在GitHub仓库页面点击 "Projects"
- 创建新的Project
- 设置看板：
  - To Do：待处理的任务
  - In Progress：进行中的任务
  - Review：需要审查的任务
  - Done：已完成的任务
- 将issues和PR拖放到相应的列中

### 9.3 使用Dependabot
- 自动检查依赖项更新
- 在GitHub仓库设置中启用Dependabot
- 配置 `.github/dependabot.yml`：
```yaml
version: 2
updates:
  - package-ecosystem: "gradle"
    directory: "/"
    schedule:
      interval: "weekly"
  - package-ecosystem: "github-actions"
    directory: "/"
    schedule:
      interval: "weekly"
```

## 10. 常见问题

### 10.1 如何处理冲突
- **发生冲突**：当多人修改同一文件时
- **解决方法**：
  1. 拉取最新代码：`git pull`
  2. 查看冲突文件：`git status`
  3. 手动编辑冲突文件，解决冲突
  4. 添加解决后的文件：`git add .`
  5. 提交解决：`git commit -m "解决冲突"`
  6. 推送：`git push`

### 10.2 如何撤销错误的提交
- **撤销本地提交**：
  ```bash
  git reset HEAD~1
  ```
- **撤销远程提交**：
  ```bash
  git revert HEAD
  git push
  ```

### 10.3 如何管理大型PR
- **拆分PR**：将大型PR拆分为多个小型PR
- **清晰描述**：详细说明PR的内容和目的
- **提供测试**：包含测试用例
- **耐心等待**：代码审查可能需要时间

## 11. 成功案例

### 11.1 知名游戏模组的开源成功
- **OptiFine**：优化模组，最初开源
- **JEI**：物品查看模组，活跃的开源社区
- **Fabric API**：Fabric模组开发框架，完全开源

### 11.2 学习经验
- **明确目标**：设定清晰的项目目标
- **开放心态**：欢迎各种贡献和建议
- **持续维护**：保持项目活跃
- **社区第一**：以社区需求为中心

## 12. 总结

### 12.1 开源共创的价值
- **技术成长**：从其他开发者那里学习
- **项目改进**：获得更多创意和想法
- **社区建设**：建立活跃的开发者社区
- **个人品牌**：提升个人在开源领域的声誉
- **行业影响**：为游戏模组生态做出贡献

### 12.2 开始你的开源之旅
1. **完善项目**：准备好所有必要的文件
2. **设置仓库**：在GitHub上创建开源仓库
3. **邀请贡献**：发布公告，邀请他人贡献
4. **建立流程**：设置分支策略和审查流程
5. **维护社区**：积极回应issues和PR
6. **持续改进**：根据社区反馈不断改进

### 12.3 结语

开源共创不仅是一种开发模式，更是一种文化和精神。通过GitHub，你可以连接全球的开发者，共同创造出更好的修仙模组。

记住：**开源不是一个人的战斗，而是一群人的狂欢**。每一个贡献，无论大小，都是对项目的支持和热爱。

祝你在GitHub开源共创的道路上越走越远！🧙‍♂️✨

---

**最后的建议**：从小处开始，逐步建立你的开源社区。最重要的是保持热情和耐心，开源共创是一场马拉松，不是短跑。
```

### 4.3 创建Issue和PR模板

**ISSUE_TEMPLATE.md**：
```markdown
# Issue模板

## 类型
- [ ] Bug报告
- [ ] 新功能请求
- [ ] 改进建议
- [ ] 其他

## 描述
请清晰描述你的问题或建议。

## 复现步骤（Bug报告）
1. 
2. 
3. 

## 期望行为

## 实际行为

## 环境信息
- Minecraft版本：
- 模组版本：
- Fabric API版本：
- 其他相关模组：

## 附加信息
（截图、日志等）
```

**PULL_REQUEST_TEMPLATE.md**：
```markdown
# Pull Request

## 类型
- [ ] Bug修复
- [ ] 新功能
- [ ] 代码优化
- [ ] 文档更新
- [ ] 其他

## 描述
请描述你的修改内容。

## 相关Issue
（如果有，链接相关的issue）

## 修改内容
- 
- 
- 

## 测试情况
- [ ] 本地构建成功
- [ ] 功能测试通过
- [ ] 无回归问题

## 附加信息
（其他需要说明的内容）
```

## 5. 分支策略

### 5.1 推荐分支结构
- **master**：主分支，保持稳定版本
- **develop**：开发分支，集成新功能
- **feature/**：功能分支，开发新特性
- **fix/**：修复分支，修复bug
- **release/**：发布分支，准备新版本

### 5.2 分支管理流程
1. 从 `develop` 分支创建功能分支
2. 在功能分支上开发
3. 开发完成后，创建PR到 `develop` 分支
4. 经过代码审查后合并
5. 准备发布时，从 `develop` 创建 `release` 分支
6. 在 `release` 分支上进行最后的调整
7. 发布后，将 `release` 分支合并到 `master` 和 `develop` 分支

## 6. 社区建设

### 6.1 吸引贡献者
- **完善文档**：清晰的README和贡献指南
- **创建Good First Issues**：为新手准备的简单任务
- **积极回应**：及时回复issues和PR
- **展示贡献者**：在README中列出主要贡献者
- **举办活动**：如代码马拉松、功能征集等

### 6.2 维护社区活跃度
- **定期更新**：保持项目活跃开发
- **发布版本**：定期发布新版本
- **社区互动**：在Discord或其他平台与用户交流
- **庆祝成就**：当项目达到里程碑时庆祝
- **感谢贡献者**：公开感谢所有贡献者

## 7. 版本管理

### 7.1 语义化版本
- **格式**：X.Y.Z
- **X**：主版本号，不兼容的API变更
- **Y**：次版本号，向后兼容的新功能
- **Z**：补丁版本号，向后兼容的bug修复

### 7.2 发布流程
1. 确保所有功能都已完成并测试通过
2. 更新版本号（在build.gradle和fabric.mod.json中）
3. 更新CHANGELOG.md
4. 创建release分支
5. 进行最终测试
6. 构建发布版本
7. 在GitHub上创建Release
8. 上传构建产物
9. 合并到master和develop分支
10. 发布公告

## 8. 处理贡献

### 8.1 代码审查
- **审查内容**：
  - 代码质量和风格
  - 功能实现是否正确
  - 是否有潜在的bug
  - 性能影响
  - 安全性

- **审查流程**：
  1. 分配审查者
  2. 审查者检查代码
  3. 提出修改建议
  4. 贡献者进行修改
  5. 再次审查
  6. 批准合并

### 8.2 处理Issues
- **分类**：使用标签对issues进行分类
- **优先级**：设置优先级（高、中、低）
- **分配**：将issues分配给合适的贡献者
- **跟踪**：使用Projects或Milestones跟踪进度
- **关闭**：问题解决后及时关闭

## 9. 高级功能

### 9.1 使用GitHub Actions
创建 `.github/workflows/build.yml` 文件：
```yaml
name: 构建测试

on:
  push:
    branches: [ master, develop ]
  pull_request:
    branches: [ master, develop ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: 设置JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'adopt'
    - name: 构建项目
      run: ./gradlew build
    - name: 测试
      run: ./gradlew test
    - name: 上传构建产物
      uses: actions/upload-artifact@v4
      with:
        name: sword-mod
        path: build/libs/*.jar
```

### 9.2 使用GitHub Projects
- 在GitHub仓库页面点击 "Projects"
- 创建新的Project
- 设置看板：
  - To Do：待处理的任务
  - In Progress：进行中的任务
  - Review：需要审查的任务
  - Done：已完成的任务
- 将issues和PR拖放到相应的列中

### 9.3 使用Dependabot
- 自动检查依赖项更新
- 在GitHub仓库设置中启用Dependabot
- 配置 `.github/dependabot.yml`：
```yaml
version: 2
updates:
  - package-ecosystem: "gradle"
    directory: "/"
    schedule:
      interval: "weekly"
  - package-ecosystem: "github-actions"
    directory: "/"
    schedule:
      interval: "weekly"
```

## 10. 常见问题

### 10.1 如何处理冲突
- **发生冲突**：当多人修改同一文件时
- **解决方法**：
  1. 拉取最新代码：`git pull`
  2. 查看冲突文件：`git status`
  3. 手动编辑冲突文件，解决冲突
  4. 添加解决后的文件：`git add .`
  5. 提交解决：`git commit -m "解决冲突"`
  6. 推送：`git push`

### 10.2 如何撤销错误的提交
- **撤销本地提交**：
  ```bash
  git reset HEAD~1
  ```
- **撤销远程提交**：
  ```bash
  git revert HEAD
  git push
  ```

### 10.3 如何管理大型PR
- **拆分PR**：将大型PR拆分为多个小型PR
- **清晰描述**：详细说明PR的内容和目的
- **提供测试**：包含测试用例
- **耐心等待**：代码审查可能需要时间

## 11. 成功案例

### 11.1 知名游戏模组的开源成功
- **OptiFine**：优化模组，最初开源
- **JEI**：物品查看模组，活跃的开源社区
- **Fabric API**：Fabric模组开发框架，完全开源

### 11.2 学习经验
- **明确目标**：设定清晰的项目目标
- **开放心态**：欢迎各种贡献和建议
- **持续维护**：保持项目活跃
- **社区第一**：以社区需求为中心

## 12. 总结

### 12.1 开源共创的价值
- **技术成长**：从其他开发者那里学习
- **项目改进**：获得更多创意和想法
- **社区建设**：建立活跃的开发者社区
- **个人品牌**：提升个人在开源领域的声誉
- **行业影响**：为游戏模组生态做出贡献

### 12.2 开始你的开源之旅
1. **完善项目**：准备好所有必要的文件
2. **设置仓库**：在GitHub上创建开源仓库
3. **邀请贡献**：发布公告，邀请他人贡献
4. **建立流程**：设置分支策略和审查流程
5. **维护社区**：积极回应issues和PR
6. **持续改进**：根据社区反馈不断改进

### 12.3 结语

开源共创不仅是一种开发模式，更是一种文化和精神。通过GitHub，你可以连接全球的开发者，共同创造出更好的修仙模组。

记住：**开源不是一个人的战斗，而是一群人的狂欢**。每一个贡献，无论大小，都是对项目的支持和热爱。

祝你在GitHub开源共创的道路上越走越远！🧙‍♂️✨

---

**最后的建议**：从小处开始，逐步建立你的开源社区。最重要的是保持热情和耐心，开源共创是一场马拉松，不是短跑。