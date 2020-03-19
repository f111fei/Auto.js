
module.exports = function(runtime, global){
    var floaty = {};

    floaty.window = function(xml){
        if(typeof(xml) == 'xml'){
            xml = xml.toXMLString();
        }
        return wrap(runtime.floaty.window(function(context, parent){
             runtime.ui.layoutInflater.setContext(context);
             return runtime.ui.layoutInflater.inflate(xml.toString(), parent, true);
        }));
    }

    floaty.rawWindow = function(xml){
        if(typeof(xml) == 'xml'){
            xml = xml.toXMLString();
        }
        return wrap(runtime.floaty.rawWindow(function(context, parent){
             console.log("context is:" + context);
             console.log("parent is:" + parent);
             runtime.ui.layoutInflater.setContext(context);

             return runtime.ui.layoutInflater.inflate(xml.toString(), parent, true);
        }));

    }

    function wrap(window){
    console.log("break point 1");
        var proxyObject = new com.stardust.autojs.rhino.ProxyJavaObject(global, window, window.getClass());
        console.log("break point 2");
        var viewCache = {};
        proxyObject.__proxy__ = {
            set: function(name, value){
                window[name] = value;
            },
            get: function(name) {
               var value = window[name];
               if(typeof(value) == 'undefined'){
                   if(!value){
                        value = window.findView(name);
                   }
                   if(!value){
                      value = undefined;
                   }
               }
               return value;
            }
        };
        return proxyObject;
    }
    
    floaty.closeAll = runtime.floaty.closeAll.bind(runtime.floaty);

    return floaty;
}

