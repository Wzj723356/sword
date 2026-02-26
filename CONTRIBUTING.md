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

## 6.目前可做的贡献

- **修bug**：看见请修一下放到你的分支里（自己建）
- **作纹理**：看[需要的纹理列表](textures_neeed.md)或[纹理贡献指南](textures_guide.md)

## 7. 联系方式

- **GitHub Issues**：项目问题和讨论
- **Discord**：实时交流（如果有）
- **Email**：维护者邮箱（可选）

再次感谢你的贡献！🧙‍♂️✨
