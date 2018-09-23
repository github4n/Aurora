window.onresize=function(){
    var offsetWid = document.documentElement.clientWidth;
    if(offsetWid < 406){
        $(".layui-logo-brand").hide();
    }else {
        $(".layui-logo-brand").show();
    }
}
if (/(Android)/i.test(navigator.userAgent)){     // 判断是否为Android手机
    $(".layui-logo-brand").hide();
}else if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){  // 判断是否为苹果手机
    $(".layui-logo-brand").hide();
}