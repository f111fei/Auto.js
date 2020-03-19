/*function sleep(d) {
    for (var t = Date.now(); Date.now() - t <= d;);
}*/

function log(s) {
    console.log(s);
}

var tempStorage = storages.create("test_storage");

var accnum = tempStorage.get("acc");
var accpswd = tempStorage.get("pswd");

//auto();

if (!requestScreenCapture(true)) {
    toastLog("请求截图失败,程序即将退出");
    exit();
}
else {
    log("截图权限申请成功");
}

getReadyFunc()

function getReadyFunc(resolve, reject) {

    screenShotImg = captureScreen();
    log("after first get cap");
    if (screenShotImg.getWidth() < screenShotImg.getHeight()) {
        log("使用参数ture申请截图后，宽高不对：width为" + screenShotImg.getWidth() + ",height为:" + screenShotImg.getHeight());
        setScreenCapture(false);
        screenShotImg = captureScreen();
        log("使用false修正后,width = " + screenShotImg.getWidth() + ", height = " + screenShotImg.getHeight());
    } else {
        log("申请截图参数正确，无需调整");
    }
}

function exitfunc() {
    mainThread = null;
    threads.shutDownAll();
    jamPreventMap = {};
}

function stopScript() {
    updateCancleImg.recycle();
    qqPlayImg.recycle();
    weixinPlayImg.recycle();
    logoutImg.recycle();

    w.close();
    threads.shutDownAll();
    jamPreventMap = {};
    engines.myEngine().forceStop();
}

events.observeKey();

var keyNames = {
    "KEYCODE_VOLUME_UP": "音量上键",
    "KEYCODE_VOLUME_DOWN": "音量下键",
    "KEYCODE_HOME": "Home键",
    "KEYCODE_BACK": "返回键",
    "KEYCODE_MENU": "菜单键",
    "KEYCODE_POWER": "电源键",
};

events.on("key", function(code, event){
    var keyName = getKeyName(code, event);
    if(event.getAction() == event.ACTION_DOWN){
        toastLog(keyName + "被按下");
    }else if(event.getAction() == event.ACTION_UP){
        toastLog(keyName + "弹起");
    }
});

function getKeyName(code, event){
    var keyCodeStr = event.keyCodeToString(code);
    var keyName = keyNames[keyCodeStr];
    if(!keyName){
        return keyCodeStr;
    }
    return keyName;
}

var w = floaty.rawWindow(
    <frame gravity="center" bg="#44ffcc00">
        <text id="text">悬浮文字</text>
    </frame>
);

w.setSize(-1, -1);
w.setTouchable(false);

