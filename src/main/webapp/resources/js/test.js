//封装get请求
function get(obj){
    var ajax=new XMLHttpRequest;
    ajax.onreadystatechange=function(){
        if(ajax.readyState==4){
            if(ajax.status>=200&&ajax.status<300||ajax.status==304){
                var json=JSON.parse(ajax.responseText);
                obj.success(json);
            }else{
                alert("错误信息："+ajax.status);
            }
        }
    }
    ajax.open("get",obj.url,true);
    ajax.send(null);
}

//提交答案
var submit=document.getElementById("submit");
var dialog=document.getElementsByClassName("dialog")[0];
var ensure=document.getElementById("ensure");
var grade=document.getElementById("testGrade");
var gradeList=grade.getElementsByTagName("li");
var paperForm=document.getElementById("paperForm");


        get({
            url:"http://localhost:8080/exam/testPaper/submit",
            success: function(text){
                if(text.obj.success){
                    gradeList[0].innerText="满分："+text.obj.baseSum+"分";
                    gradeList[1].innerText="总得分："+text.obj.paperScore+"分";
                    gradeList[2].innerText="选择题得分："+text.obj.choiceScore+"分";
                    gradeList[3].innerText="判断题得分："+text.obj.packScore+"分";
                    dialog.style.display="block";
                    dialog.classList.toggle("scale");
                }else{
                    alert("试卷提交失败，请联系监考老师");
                }
            }
        })
	}
	ensure.onclick=function(){
		dialog.style.display="none";
		dialog.classList.toggle("scale");
	}


	//发送页面答题情况
//	var paperIdMessage=null;
//	var questionId=document.getElementsByClassName("questionId");
//	$(function(){
//			$("#submit").click(function(){
//				$.post("json.html",{
//					paperId: paperIdMessage
//					for(var i=0;i<questionId.length;i++){
//						questionId : $("#username").val(),
//						questionAnswer : $("#password").val()
//					}
//					
//				},function(data,textStatus){
//					alert(data,textStatus);
//					window.open("test.html","_self");
//				})
//			})
//		})
//json渲染页面



//选择题获取答案
setTimeout(function(){
    var selectIndex=document.getElementsByClassName("selectIndex");
    var selectAnswer=new Array();
    for(var i=0;i<selectIndex.length;i++){
        var selectName=document.getElementsByName("select"+i);
        selectAnswer[i]=document.getElementsByName("list["+i+"].questionAnswer")[0];
        for(var j=0;j<selectName.length;j++){
            selectName[j].onclick=function(i){
                return function(){
                    if(this.checked){
                        selectAnswer[i].value=this.value;
                    }
                }
            }(i)
        }
    }
},1000)

//判断题获取答案 改
setTimeout(function(){
    var judgeIndex=document.getElementsByClassName("judgeIndex");
    var judgeAnswer=new Array();
    for(var i=0;i<judgeIndex.length;i++){
        var judgeName=document.getElementsByName("judge"+i);
        judgeAnswer[i]=document.getElementsByClassName("list["+i+"].questionJudgeAnswer")[0];
        for(var j=0;j<judgeName.length;j++){
            judgeName[j].onclick=function(i){
                return function(){
                    if(this.checked){
                        judgeAnswer[i].value=this.value;
                    }
                }
            }(i)
        }

    }
},1000)
	

//get获取试题数据
get({
	url: "http://localhost:8080/exam/testPaper/producePlus",
	success: function(text){
		var paperId=document.getElementById("paperId");
		paperId.value=text.obj.id;
        //动态添加选择题 改
        var choiceNums=text.obj.choiceNum;
        var choice=document.getElementById("choice");
        for(var i=0;i<choiceNums;i++){
            var choiceEle='<div class="problem selectIndex"><input  type="hidden" name="list[' + i + '].questionId" class="questionId" id="questionId' + i + ' " value="" /><input  type="text" name="list[' + i + '].questionAnswer" class="questionAnswer" id="" /><span class="tip">[错误]</span><h4 class="questionTitle">2.回答下面题目，有一问请选择ABCD（  ）</h4><ul class="answer"><li><input type="radio" name="select'+i+'" id="" value="A" /><span>A.继承实现</span></li><li><input type="radio" name="select'+i+'" id="" value="B" /><span>B.调用成员函数实现</span></li><li><input type="radio" name="select'+i+'" id="" value="C" /><span>C.封装实现</span></li><li><input type="radio" name="select'+i+'" id="" value="D" /><span>D.函数重载实现</span></li></ul></div>';
            choice.innerHTML+=choiceEle;
        }
        //动态添加填空题 改
        var judgeNums=text.obj.judgeNum;
        var judge=document.getElementById("judge");
        for(var i=0;i<judgeNums;i++){
            var indexNum=choiceNums+i;
            var selectEle='<div class="problem replay judgeIndex "><input  type="hidden" name="list[' + indexNum + '].questionId" class="questionId" id="" value="" /><input type="text" class="list[' + i + '].questionJudgeAnswer" name="list[' + indexNum + '].questionAnswer"><span class="tip">[错误]</span><h4 class="judgeAnswer">1.下列说法是否正确：给出一个二叉树点um的值。</h4><input type="radio" name="judge'+i+'" id="" value="F" />F<input type="radio" name="judge'+i+'" id="" value="T" />T</div>';
            judge.innerHTML+=selectEle;
        }

        //主题
		var paper=document.getElementById("paperTitle");
		paper.innerText=text.obj.paperTilte;
		//账户
		var user=document.getElementById("uesername");
		user.innerText=text.obj.user.userName;

        //所有题目id
        var questionId=document.getElementsByClassName("questionId");
        for(var i=0;i<questionId.length;i++){
            questionId[i].value=text.obj.questions[i].id;
        }

        //选择题目,改
        var questionTitle=document.getElementsByClassName("questionTitle");
        for(var i=0;i<questionTitle.length;i++){
            if(text.obj.questions[i].questionType==0){
                var quesText=1+i+"."+text.obj.questions[i].questionTitle+"( )";
                questionTitle[i].innerText=quesText;
            }

        }
		
//		//选择题选项数据
//		var selectText=null;
//		var arrStr=new Array();
//		var four=null;
//		for(var i=0;i<text.obj.questions.length;i++){
//			if(text.obj.questions[i].questionType==0){
//				selectText=text.obj.questions[i].alternativeOption;
//				four=spiltString(selectText);
//				arrStr.push(four);
//			}
//			
//		}
//		
//		//所有四个选项
//		var answer=document.getElementsByClassName("answer");
//		for(var i=0;i<answer.length;i++){
//			for(var j=0;j<4;j++){
//				answer[i].getElementsByTagName("li")[j].getElementsByTagName("span")[0].innerText=arrStr[i][j];
//			}
//		}
		
		//选择题选项数据
		var selectText1=null;
		var selectText2=null;
		var selectText3=null;
		var selectText4=null;
		var arrStrE=new Array();
		for(var i=0;i<text.obj.questions.length;i++){
			if(text.obj.questions[i].questionType==0){
					selectText1="A."+text.obj.questions[i].alternativeOption1;
					selectText2="B."+text.obj.questions[i].alternativeOption2;
					selectText3="C."+text.obj.questions[i].alternativeOption3;
					selectText4="D."+text.obj.questions[i].alternativeOption4;
					var arrStr=new Array();
					arrStr.push(selectText1);
					arrStr.push(selectText2);
					arrStr.push(selectText3);
					arrStr.push(selectText4);
					arrStrE[i]=arrStr;
			}
		}
		
		//所有四个选项
		var answer=document.getElementsByClassName("answer");
		for(var i=0;i<answer.length;i++){
			for(var j=0;j<4;j++){
				answer[i].getElementsByTagName("li")[j].getElementsByTagName("span")[0].innerText=arrStrE[i][j];
			}
		}
		
		//判断题数据
		var judgeText=null;
		var arrStr1=new Array();
		for(var i=0;i<text.obj.questions.length;i++){
			if(text.obj.questions[i].questionType==1){
				judgeText=text.obj.questions[i].questionTitle;
				arrStr1.push(judgeText);
			}
		}
		
		//判断题
		var judgeAnswer=document.getElementsByClassName("judgeAnswer");
		for(var i=0;i<judgeAnswer.length;i++){
			judgeAnswer[i].innerHTML=i+1+"."+arrStr1[i];
		}
		
		
	}
})
//拆分选项
function spiltString(str){
	var arr=new Array;
	var index=str.indexOf("#");
	while(index>-1){
		arr.push(index);
		index=str.indexOf("#",index+1);
	}
	var option=["A."+str.slice(0,arr[0]),"B."+str.slice(arr[0]+1,arr[1]),
	"C."+str.slice(arr[1]+1,arr[2]),"D."+str.slice(arr[2]+1,str.length-1)
	];
	return option;
}

//选择题获取答案

//var select=document.getElementsByClassName("select");
//for(var i<select.length;i++){
//	for(var j=0;j<4;j++){
//		alert(j)
//	}
//}



