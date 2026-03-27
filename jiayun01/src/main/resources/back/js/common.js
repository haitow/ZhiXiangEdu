var web_prefix = '/back'



function dateFormat(fmt, date) {
    var ret;
    var tf = function(str, len){
        if(str.length < len) {
            for( var i = 0; i < len - str.length; i++) {
                str = "0" + str;
            }
        }
        return str
    };
    var opt = {
        "y+": date.getFullYear().toString(),        // 年
        "M+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "m+": date.getMinutes().toString(),         // 分
        "s+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (var k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], ret[1].length == 1 ? opt[k] : tf(opt[k], ret[1].length))
        }
    }
    return fmt;
}

//调用value是long类型的日期，比如：new Date().getTime()
// dateFormat("yyyy-MM-dd HH:mm:ss", new Date(value))