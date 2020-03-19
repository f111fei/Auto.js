var testVars = {};
testVars = {"与QQ好友玩坐标": {1200, 1000}};

function testImgSearch() {
    if (!requestScreenCapture(false)) {
        toastLog("请求截图失败");
        exit();
    }
    else{
        console.log("截图权限申请成功");
    }
    sleep(5000);
    console.log("device width and height:", device.width, device.height);
    //console.log("Design width Height", getmDesignWidth(), getmDesignHeight());
    //exit();
 
    console.log("begin read");
    //var frameCanvas = { x: 1200, y: 777 };
    //setScreenMetrics(3840, 2400);
    //var gameStartImg = images.read("./gameStart-small.png");
    var qqPlayImg = images.read("./areaChange.png");
    //var weixinPlayImg = images.read("./playByWeixin.png");
    //var logoutImg = images.read("./logout.png");
    console.log("after read");

    //var img = captureScreen();
    //images.saveImage(img, "/sdcard/ss.png");

    console.log("begin cap");  
    var scrShotImg = captureScreen();
    console.log("mid cap");
    images.saveImage(scrShotImg, "/sdcard/s1.png");
    console.log("end capd");
 
    
    var clipss = images.clip(scrShotImg, 100, 150, 300, 300);
    var clip2 = images.clip(scrShotImg, 200, 250, 300, 300);

    var result = images.findImage(scrShotImg, clipss);
    if(result) {
        console.log("clip1", result);
    } else {
        console.log("not found");
    }

    var result2 = images.findImage(scrShotImg, clip2);
    if(result2) {
        console.log("clip2", result2);
    } else {
        console.log("not found");
    }

    //var areaChangeCoor = images.findImageInRegion(scrShotImg, gameStartImg, 1100, 676, 100, 300, 0.5);

    //click(1200, 920);
    //click(920, 1200);

    var areaChangeCoor = images.findImage(scrShotImg, qqPlayImg);


    if (areaChangeCoor) {
        console.log("选择其他区：" + areaChangeCoor);
        //press(areaChangeCoor.x, areaChangeCoor.y, 200);
    } else {
        console.log("not find");
        //click(1000, 700);

        //click(700, 930);
    }
    //sleep(3000)
    clipss.recycle();
    clip2.recycle();
    scrShotImg.recycle();
    //qqPlayImg.recycle();
    //weixinPlayImg.recycle();
    //gameStartImg.recycle();
    //logoutImg.recycle();
}

var accnum = "406625769";
var accpswd = "yangliLI1991";

function testControlFind1() { //有qqhd  qq已登录

    click(300,300);

    if(text("切换帐号").exists()) {
        console.log("find 切换账号");
        text("切换帐号").findOne(1000).click();

        
    }
    else {
        console.log("切换帐号 not find");
    }

    if(id("account").exists()) {
        console.log("account exist");
        id("account").findOne(1000).setText(accnum);
    } else {
        console.log("account not find");
    }

    if(id("password").exists()){
        console.log("password exist");
        id("password").findOne(1000).setText(accpswd);
    } else {
        console.log("password not find");
    }
    
    if(id("open_login_btn").exists()){
        console.log("open_login_btn exist");
        id("open_login_btn").findOne(1000).click();
        sleep(1000);
    } else {
        console.log("open_login_btn not find");
    }

    if(id("open_login_btn").exists()){
        console.log("open_login_btn exist");
        id("open_login_btn").findOne(1000).click();
    } else {
        console.log("open_login_btn not find");
    }

}

function exchangeAccWithNoQQ() {//no qq,has histroy account
    //console.log(id("swicth_login").find());
    var loginArr = text("登 录").find();
    var exchangeAccArr = id("swicth_login").find();
    //var exchangeAccArr2 = id("switch").find();
    if(text("切换帐号").exists()){ // not work
        console.log("swicth_login exist");
        console.log("id is" + text("切换帐号").findOne(1000).id());
        console.log(loginArr.size(), exchangeAccArr.findOne(clickable()).bounds());

        if(id(text("切换帐号").findOne(1000).id()).find().size() == 0) {
            console.log("id function 有问题");
        } else {
            console.log("其他地方有问题");
        }
        sleep(1000);
    } else {
        console.log("swicth_login not find");
    }
}

function testControlFind() { // no qq, not has history(qq国际版是一样的)

    exchangeAccWithNoQQ();
    return;

    var editTextArr = className("android.widget.EditText").find();
    var editNum = editTextArr.size();
    if(editNum >= 2) {
        setText(editNum - 2, accnum);
        setText(editNum - 1, accpswd);
        sleep(1000);
    } else {
        console.log("not find 2 edit");
    }

    if(id("go").exists()){ // not work
        console.log("go exist");
        id("go").findOne(1000).click();
        sleep(1000);
    } else {
        console.log("go not find");
    }

    //var loginArr = text("登 录").find();
    //var loginArr = idStartsWith("go").find();
    var loginArr = id("go").find();
    if(loginArr.size()){ // not work,bounds is different from I see，maybe there is another control named "登录"
        console.log("登录  exist");
        console.log("denglu size:", loginArr.size());
        console.log(loginArr.findOne(clickable()).id());
        //console.log(loginArr.findOne(depth("7")).bounds());
        sleep(1000);
    } else {
        console.log("登录 not find");
    }


    var depthDrawArr = depth("7").drawingOrder("0").find();
    if(depthDrawArr.size()){ // works.
        console.log("depth draw size:", depthDrawArr.size());
        console.log(depthDrawArr.findOne(clickable()).bounds());
        //depth("7").drawingOrder("0").click();
        sleep(1000);
    } else {
        console.log("depth draw not find");
    }

}

exports.testImgSearch = testImgSearch;
exports.testControlFind = testControlFind;
//export default testadd;