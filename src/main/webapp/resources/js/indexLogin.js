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

var user=document.getElementById("userName");
var pass=document.getElementById("passWord");
var tip=document.getElementsByClassName("tip");
var sendAccount=document.getElementById("sendAccount");
var idCardValue=null;
var passWordValue=null;
var loginStatus=null;
//身份证校验
user.onfocus=function(){
		if(idCardValue==null){
			tip[0].innerText="请输入你的18位身份证号码"; 
			tip[0].style.color="red";
		}
	};
user.oninput=function(){
		checkUser(this);
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
	};

pass.oninput=function(){
	checkPassword(this);
	
	};
pass.onblur=function(){
	//不能为空
		if(this.value.length==0){
			tip[1].innerText="不能为空";
		}
}
//身份证框
	function checkUser(obj){
		sendAccount.style.display="none";
		idCardValue=null;
		loginStatus=null;
		var foot=document.getElementsByClassName("foot")[0];
		foot.style.background="#eee";
		tip[0].style.color="red";
		var length=lengthChar(obj.value);
		var idCardReg=/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/g;
		if(obj.value.length==0){
			tip[0].innerText="不能为空";
		}
		else if(!idCardReg.test(obj.value)){
			tip[0].innerText="身份证号码有误";
		}
		else if(obj.value.length>18||obj.value.length<18){
			tip[0].innerText=length+"位身份证号码";
		}
		else if(obj.value.length==18){
			tip[0].style.color="#25BB9B";
			tip[0].innerText="OK";
			idCardValue=1;
			foot.style.background="#25BB9B";
			if(loginStatus==1&&idCardValue==1){
                sendAccount.style.display="block";
               /* $('#sendAccount').click( function () {
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url:"${pageContext.request.contextPath}/protal/checkProtalLogin",
                        data:$('#loginForm').serialize(),
                        async: false,
                        error: function(request) {
                            alert("Connection error");
                        },
                        success: function(data) {
                            if(data.success){
                                window.open("${pageContext.request.contextPath}/protal/protalPaperDetails","_self");
                            }else{
                                alert("请将账号密码填写完整");
                            }
                        }
                    });
                });*/
			}
		}
	}

//	密码框
	function checkPassword(obj){
	//校验密码
		sendAccount.style.display="none";
		passWordValue=null;
		loginStatus=null;
		var foot=document.getElementsByClassName("foot")[0];
		foot.style.background="#eee";
		var reg_num=/[\d]/g;
		var reg_str=/[a-zA-Z]/g;
		tip[1].style.color="red";
		//不能为空
		if(obj.value.length==0){
			tip[1].innerText="不能为空";
		}
		//不能全部为相同字符
		else if(findStr(obj.value)){
			tip[1].innerText="不能是相同字符";
		}
		//长度应为6到12个字符
		else if(obj.value.length<6||obj.value.length>16){
			tip[1].innerText="长度应为6到12个字符";
		}
		//不能全部为字母
		else if(!reg_num.test(obj.value)){
			tip[1].innerText="不能全部为字母";
		}
		else if(idCardValue==1){
			tip[1].innerText="OK";
			tip[1].style.color="#25BB9B";
			passWordValue=1;
			foot.style.background="#25BB9B";
			loginStatus=1;
			if(loginStatus==1&&idCardValue==1){
                sendAccount.style.display="block";
                /*$('#sendAccount').click( function () {
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url:"${pageContext.request.contextPath}/protal/checkProtalLogin",
                        data:$('#loginForm').serialize(),
                        async: false,
                        error: function(request) {
                            alert("Connection error");
                        },
                        success: function(data) {
                            if(data.success){
                                window.open("${pageContext.request.contextPath}/protal/protalPaperDetails","_self");
                            }else{
                                alert("请将账号密码填写完整");
                            }
                        }
                    });
                });*/
			}
			
			
		}
	
	}