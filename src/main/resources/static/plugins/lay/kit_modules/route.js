/** kitadmin-v2.1.2 MIT License By http://kit.zhengjinfan.cn Author Van Zheng */
;"use strict";layui.define(["utils","jquery","lodash","nprogress","layer"],function(i){var e=layui.utils,t=e.localStorage,o=t.setItem,n=t.getItem,r=layui.jquery,a=layui.lodash,s=layui.layer,u=r(window),l=void 0,d=function(){this.config={name:"KITADMINROUTE",routerViewId:void 0,beforeRender:void 0,routes:[]};var i=layui.router(location.hash);this.pathname=void 0===i.href?"/":i.href.split("?")[0],this.version="1.2.3"};d.prototype.set=function(i){return r.extend(!0,this.config,i),this},d.prototype.setRoutes=function(i){var t=this;i.name=i.name||t.config.name,t.config.name=i.name;var n={routes:[]};return r.extend(!0,n,i),a.forEach(n.routes,function(i){i.id=(new Date).getTime()+""+a.random(1e3,9999)}),o(n.name,n.routes),r(window).off("popstate").on("popstate",function(){e.isFunction(i.onChanged)?i.onChanged():t.render()}),t},d.prototype.getRoutes=function(){return n(this.config.name)},d.prototype.getRoute=function(i){var t=this.getRoutes(this.config.name);if(null!==t&&void 0!==t){i=i||location.hash;var o=layui.router(i).href.split("?"),n=e.find(t,function(i){return i.path===o[0]});return void 0===n?n:(o.length>1&&(n.component+="?"+o[1],n.path+="?"+o[1]),n)}},d.prototype.render=function(i,t,o){var n=this,a=n.config,d=void 0;NProgress.start();var c=s.load(2),v=e.randomCode();if(t&&t.length>0)d=t;else{var f=void 0===a.routerViewId?r("router-view"):r("router-view#"+a.routerViewId);f.length>0&&(f.parent().append('<div id="'+v+'"></div>'),f.remove(),d=r("#"+v),l=d)}void 0===d&&(d=l);var h=n.getRoute(i);function p(){NProgress.done(),c&&s.close(c),e.isFunction(o)&&o()}return void 0!==h?("function"==typeof a.beforeRender&&(h=a.beforeRender(h)),h.iframe?(d.html('<iframe src="'+h.component+'" data-id="'+v+'" style="height: 780px;"></iframe>'),u.on("resize",function(){var i=r(".layui-body").height();r("iframe[data-id="+v+"]").height(i-3)}).resize(),p()):e.tplLoader(h.component,function(i){d.html(i),p(),e.setUrlState(h.name,"#"+h.path)},function(i){var e=['<div class="layui-fluid">','<div class="layui-row">','<div class="layui-col-xs12">','<div class="kit-not-router">',i,"</div>","</div>","</div>","</div>"].join("");d.html(e),p()})):(d.html(['<div class="layui-fluid">','  <div class="layui-row">','    <div class="layui-col-xs12">','      <div class="layui-row">','        <div class="layui-col-md4">&nbsp;</div>','        <div class="layui-col-md4">','          <div class="kit-exception">','            <i class="layui-icon kit-exception-icon">&#xe61c;</i>','            <h3 class="kit-exception-title">:>404 抱歉，你访问的页面不存在</h3>','            <a href="javascript:history.back(-1);" class="layui-btn">返回上一页</a>',"          </div>","        </div>",'        <div class="layui-col-md4">&nbsp;</div>',"      </div>","    </div>","  </div>","</div>"].join("")),NProgress.done(),c&&s.close(c)),n},d.prototype.params=function(){var i=layui.router();if(void 0===i.href)return null;var e=i.href,t=e.substr(e.indexOf("?")+1);if(e===t)return null;var o=t.split("&"),n={};return a.forEach(o,function(i,e){var t=i.split("="),o=t[0],r=t[1];n[o]=r}),n},i("route",new d)});
//# sourceMappingURL=route.js.map