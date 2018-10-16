(function(id,t){
    if(!document.getElementById(id)){return false;}
    var doc = document,
        auto='',
        oId = doc.getElementById(id),
        IE = /msie (\d+\.\d)/i.test(navigator.userAgent),
        num = 0,
        bot = true,
        setOpacity = function(obj, opacity){
            if(IE){
                obj.style.filter = 'Alpha(Opacity=' + (opacity * 100) + ')';
            }
            else{
                obj.style.opacity = opacity;
            };
        },
        setBottom = function(obj, bottom){
            obj.style.bottom = bottom + 'px';
        },
        fideIn = function(obj, timeLimit){
            if(obj.style.display === 'none'){
                obj.style.display = 'block';
            };
            setOpacity(obj, 0);
            obj.style.zIndex = 1;
            if(!timeLimit){
                timeLimit = 200;
            };
            var opacity = 0,
                step = timeLimit / 20;
            clearTimeout(fideInTime);
            var fideInTime = setTimeout(function(){
                bot = false;
                if(opacity >= 1){
                    bot = true;
                    return;
                };
                opacity += 1 / step;
                setOpacity(obj, opacity);
                fideInTime = setTimeout(arguments.callee, 20);
            },20);
        },
        fideOut = function(obj, timeLimit){
            if(!timeLimit){
                timeLimit = 200;
            };
            setOpacity(obj, 1);
            obj.style.zIndex = 0;
            var opacity = 1,
                step = timeLimit / 20;
            clearTimeout(fideOutTime);
            var fideOutTime = setTimeout(function(){
                if (opacity <= 0) {
                    setOpacity(obj, 0);
                    return;
                };
                opacity -= 1 / step;
                setOpacity(obj, opacity);
                fideOutTime = setTimeout(arguments.callee, 20);

            },20);
        },
        heightIn = function(obj, timeLimit){
            if(obj.style.display === 'none'){
                obj.style.display = 'block';
            };
            setBottom(obj, -40);
            if(!timeLimit){
                timeLimit = 200;
            };
            var bottom = -40,
                step = timeLimit / 20;
            clearTimeout(heightInTime);
            var heightInTime = setTimeout(function(){
                if(bottom >= 8){
                    setBottom(obj, 8);
                    return;
                };
                bottom += 28 / step;
                setBottom(obj, bottom);
                heightInTime = setTimeout(arguments.callee, 20);
            },20);
        },
        heightOut = function(obj, timeLimit){
            if(!timeLimit){
                timeLimit = 200;
            };
            setBottom(obj, 8);
            var bottom = 8,
                step = timeLimit / 20;
            clearTimeout(heightOutTime);
            var heightOutTime = setTimeout(function(){
                if(bottom <= -40){
                    setBottom(obj, -40);
                    return;
                };
                bottom -= 28 / step;
                setBottom(obj, bottom);
                heightOutTime = setTimeout(arguments.callee, 20);
            },20);
        },
        getClass = function(oElem, strTagName, strClassName){
            var arrElements = (strTagName == '*' && oElem.all) ? oElem.all : oElem.getElementsByTagName(strTagName);
            var returnArrElements = new Array();
            var oRegExp =  new RegExp('(^|\\s)' + strClassName + '($|\\s)');
            for(var i=0; i<arrElements.length; i++){
                if(oRegExp.test(arrElements[i].className)){
                    returnArrElements.push(arrElements[i]);
                }
            }
            return (returnArrElements);
        },
        createElement = function(tag, id, cla){
            var elem = document.createElement(tag);
            if(id && id !== ""){
                elem.id = id;
            }
            if(cla && cla !== ""){
                elem.className = cla;
            }
            return elem;
        },
        showImg = function(n,b){
            var turnPic = getClass(oId,'ul','turn-pic')[0];
            var oLi = turnPic.getElementsByTagName('li');
            var turnBtn = getClass(oId,'div','turn-btn')[0];
            var oSpan = turnBtn.getElementsByTagName('span')[0];
            fideIn(oLi[n],300);
            oSpan.innerHTML = (n+1)+'/'+oLi.length;
            if(b==true){
                if(n==oLi.length-1){
                    fideOut(oLi[0],300);
                }
                if(n<oLi.length-1){
                    fideOut(oLi[n+1],300);
                }
            }
            else{
                if(n>0){
                    fideOut(oLi[n-1],300);
                }
                if(n==0){
                    fideOut(oLi[oLi.length-1],300);
                }
            }
        },
        addHtml = function(){
            var oBtn = createElement('div','','turn-btn');
            var turnPic = getClass(oId,'ul','turn-pic')[0];
            var oA = turnPic.getElementsByTagName('a');
            var oImg = turnPic.getElementsByTagName('img');
            oBtn.innerHTML = '<div class="lb"></div><div class="rb"></div><span></span>';
            oId.appendChild(oBtn);
        },
        init = function(){
            addHtml();
            showImg(0);
            var turnLoading = getClass(oId,'div','turn-loading')[0];
            oId.removeChild(turnLoading);
            var turnPic = getClass(oId,'ul','turn-pic')[0];
            var oLi = turnPic.getElementsByTagName('li');
            var oLb = getClass(oId,'div','lb')[0];
            var oRb = getClass(oId,'div','rb')[0];
            oLb.onclick = function(){
                if(!bot){ return false; }
                if(num==1){
                    num = num - 1;
                    $(".lb").css("display","none");
                    $(".rb").css("display","block");
                }
                else{
                    if(num==0)
                    {
                        $(".rb").css("display","block");
                        num = oLi.length-1;
                    }
                    else
                    {
                        $(".rb").css("display","block");
                        $(".lb").css("display","block");
                        num = num - 1;
                    }
                }
                showImg(num,1);
            }
            oRb.onclick = function(){
                if(!bot){ return false; }
                if(num==oLi.length-2){
                    num = num + 1;
                    $(".rb").css("display","none");
                    $(".lb").css("display","block");
                }
                else{
                    if(num==oLi.length-1)
                    {
                        $(".lb").css("display","block");
                        num = 0;
                    }
                    else
                    {
                        $(".rb").css("display","block");
                        $(".lb").css("display","block");
                        num = num + 1;
                    }
                }
                showImg(num);
            }
        };
    init();
})('turn',3);