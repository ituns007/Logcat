# Logcat
日志模块封装，可以根据开发模式决策日志输出位置，调试模式下日志输出到控制台，非调试模式下日志输出到文件。

1.在项目根 build.gradle 文件中添加 Maven 仓库：
```
allprojects {
    repositories {
        maven {
            url "http://maven.ituns.org/repository/maven-public/"
        }
    }
}
```

2.在模块 build.gradle 中添加引用：
```
dependencies {
    implementation "org.ituns.android:logcat:1.0.0"
}
```