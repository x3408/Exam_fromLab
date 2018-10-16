function findStr(str){
    var count=0;
    for(var i=0;i<str.length;i++){
        if(str.charAt(i)==str[0]){
            count++;
        }
    }
    if(count==str.length){
        return true;
    }
}//检测是否相同

function lengthChar(str){
    return str.replace(/[^\x00-xff]/,"xx").length;
}//检测字符串长度

var pass=document.getElementById("passWord");
var user=document.getElementById("userName");
var tip=document.getElementsByClassName("tip");
var idCardValue=null;
var passWordValue=null;
//身份证校验
user.onfocus=function(){
    if(idCardValue==null){
        tip[0].innerText="请输入你的18位身份证号码";
        tip[0].style.color="red";
    }
};
user.onkeyup=function(){
    tip[0].style.color="red";
    var length=lengthChar(this.value);
    var idCardReg=/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/g;
    if(this.value.length==0){
        tip[0].innerText="不能为空";
    }
    else if(!idCardReg.test(this.value)){
        tip[0].innerText="身份证号码有误";
    }
    else if(this.value.length>18||this.value.length<18){
        tip[0].innerText=length+"位身份证号码";
    }
    else if(this.value.length==18){
        tip[0].style.color="#25BB9B";
        tip[0].innerText="OK";
        idCardValue=1;
    }
};
user.onblur=function(){
    if(this.value.length==0){
        tip[0].innerText="不能为空";
    }
    else if(this.value.length>18||this.value.length<18){
        tip[0].innerText="身份证号码不正确";
    }
}
//密码校验
pass.onfocus=function(){
    if(passWordValue==null){
        tip[1].style.color="red";
        tip[1].innerText="请输入6-12个字符";
    }
//		checkPassword();
};

pass.onkeyup=function(){
    //校验密码
    var foot=document.getElementsByClassName("foot")[0];
    var sendAccount=document.getElementsByClassName("sendAccount")[0];
    foot.style.background="#eee";
    var reg_num=/[\d]/g;
    var reg_str=/[a-zA-Z]/g;
    tip[1].style.color="red";
    //不能为空
    if(this.value.length==0){
        tip[1].innerText="不能为空";
    }
    //不能全部为相同字符
    else if(findStr(this.value)){
        tip[1].innerText="不能是相同字符";
    }
    //长度应为6到12个字符
    else if(this.value.length<6||this.value.length>16){
        tip[1].innerText="长度应为6到12个字符";
    }
    //不能全部为字母
    else if(!reg_num.test(this.value)){
        tip[1].innerText="不能全部为字母";
    }
    else if(idCardValue==1){
        tip[1].innerText="OK";
        tip[1].style.color="#25BB9B";
        passWordValue=1;
        foot.style.background="#25BB9B";


    }



};
pass.onblur=function(){
    //不能为空
    if(this.value.length==0){
        tip[1].innerText="不能为空";
    }
}
	