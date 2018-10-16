<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/test.css"/>
<div class="main">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>试卷详情</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <!--标题-->
                        <div class="header">
                            <h1 id="paperTitle" class="title">
                                ${paperDetails.paper.paperTilte}
                            </h1>
                            <div class="account">
                                <span id="uesername">${paperDetails.paper.user.userName}</span>
                            </div>
                        </div>
                        <!--选择题-->
                        <div class="question">
                            <div class="select">
                                <div class="title1"><i class="radius"></i>[选择题]</div>
                                <div id="main">
                                    <!--选择题一个小题-->
                                    <c:forEach var="item" varStatus="status1" items="${paperDetails.list}">
                                    <c:if test="${item.questionType==0}">
                                        <div class="problem">
                                            <c:if test="${item.isRight==0&&item.personAnswer!=null}">
                                                <span style="color: red">错误</span>
                                            </c:if>
                                            <c:if test="${item.isRight==1}">
                                                <span style="color: limegreen">正确</span>
                                            </c:if>
                                            <c:if test="${item.personAnswer==null}">
                                                <span style="color: red">未答题</span>
                                            </c:if>
                                            <%--<span class="tip">[错误]1234</span>--%>
                                            <h4 class="questionTitle">${status1.count}.${item.questionTitle}
                                                （ <c:if test="${item.personAnswer!=null}">${item.personAnswer}</c:if>）</h4>
                                            <ul class="answer">
                                                <li><input type="radio" name="b1'+${status1.count}+'"  id="checkbox1"
                                                           value="1"/><span>A.${item.alternativeOption1}</span>
                                                </li>
                                                <li><input type="radio" name="b1'+${status1.count}+'" id="checkbox2"
                                                           value="1"/><span>B.${item.alternativeOption2}</span></li>
                                                <li><input type="radio" name="b1'+${status1.count}+'" id="checkbox3"
                                                           value="1"/><span>C.${item.alternativeOption3}</span>
                                                </li>
                                                <li><input type="radio" name="b1'+${status1.count}+'" id="checkbox4"
                                                           value="1"/><span>D.${item.alternativeOption4}</span></li>
                                            </ul>
                                        </div>
                                    </c:if>
                                    </c:forEach>
                                </div>
                                <!--判断题-->
                                <div class="select">
                                    <div class="title1"><i class="radius"></i>[判断题]</div>
                                    <!--判断题一个小题-->
                                    <c:forEach var="item" varStatus="status2" items="${paperDetails.list}">
                                        <c:if test="${item.questionType==1}">
                                            <!--判断题一个小题-->
                                            <div class="problem replay">
                                                <c:if test="${item.isRight==0&&item.personAnswer!=null}">
                                                    <span style="color: red">错误</span>
                                                </c:if>
                                                <c:if test="${item.isRight==1}">
                                                    <span style="color: limegreen">正确</span>
                                                </c:if>
                                                <c:if test="${item.personAnswer==null}">
                                                    <span style="color: red">未答题</span>
                                                </c:if>
                                                <span class="tip">[错误]</span>
                                                <h4 class="judgeAnswer">${status2.count-paperDetails.paper.choiceNum}.${item.questionTitle}</h4>
                                                <c:if test="${item.personAnswer=='T'}">
                                                <input type="radio" name="a1'+${status2.count-paperDetails.paper.choiceNum}+'" id="radio1" value="" />×
                                                <input type="radio" name="a1'+${status2.count-paperDetails.paper.choiceNum}+'" id="radio2" value="" checked="true"/>√
                                                </c:if>
                                                <c:if test="${item.personAnswer=='F'}">
                                                    <input type="radio" name="a2'+${status2.count-paperDetails.paper.choiceNum}+'" id="radio3" value="" checked="true"/>×
                                                    <input type="radio" name="a2'+${status2.count-paperDetails.paper.choiceNum}+'" id="radio4" value="" />√
                                                </c:if>
                                                <c:if test="${item.personAnswer==null}">
                                                    <input type="radio" name="a3'+${status2.count-paperDetails.paper.choiceNum}+'" id="radio5" value="" />×
                                                    <input type="radio" name="a3'+${status2.count-paperDetails.paper.choiceNum}+'" id="radio6" value="" />√
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <div class="col-sm-4 col-sm-offset-4">
                                        <a class="btn btn-white"
                                           href="${pageContext.request.contextPath }/exam/testPaperManage">返回</a>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

