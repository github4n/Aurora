new Vue({
    el:'#App',
    data:{
        pwd:'',
    },
    computed:{
        inputTypeVal(){
            var that = this;
            if(this.pwd.length>0){
                if(this.pwd.length==1){
                    setTimeout(function(){
                        var t= that.$refs.limao.value;
                        that.$refs.taize.focus();
                    })
                }else{
                    setTimeout(function(){
                        that.$refs.taize.focus();
                    })
                }
                return 'password';
            }else{
                setTimeout(function(){
                    that.$refs.limao.focus();
                })
                return 'text';
            }
        }
    },
    methods:{

    }
})