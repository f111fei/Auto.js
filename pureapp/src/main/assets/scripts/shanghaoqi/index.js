toastLog("yeyeye  run index.js ok");

if (!requestScreenCapture(true)) {
    toastLog("请求截图失败,程序即将退出");
    //exit();
    requestScreenCapByStatic(true)

    captureScreen();
    toastLog("cap over");
}
else {
    toastLog("截图权限申请成功");
}