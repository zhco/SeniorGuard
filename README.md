# SeniorGuard - 老年人手机智能守护引擎

自动检测并修复老年人智能手机的 25 项常见问题，无需人工干预。

## 核心能力

### 自动修复（25项）
- **显示类**：字体太小、屏幕过暗、自动旋转、屏幕超时、动画异常
- **声音类**：音量过小、静音模式、勿扰模式
- **网络类**：WiFi 断开、蓝牙关闭、飞行模式、热点异常、移动数据
- **性能类**：存储不足、后台卡顿、省电模式、动画缩放
- **安全类**：诈骗电话、诈骗短信、垃圾应用、未知来源安装

### 检测引导（3项）
- 微信通知权限关闭
- 系统时间错误
- 输入法被切换

## 技术栈
- Kotlin + Jetpack Compose
- Android Accessibility Service
- Settings.System / Settings.Global API
- BroadcastReceiver (SMS/Call/Package)

## 构建
```bash
./gradlew assembleRelease
```

## 权限需求
- WRITE_SETTINGS (修改系统设置)
- BIND_ACCESSIBILITY_SERVICE (弹窗拦截)
- RECEIVE_SMS / READ_PHONE_STATE (诈骗拦截)
- PACKAGE_USAGE_STATS (后台进程管理)
