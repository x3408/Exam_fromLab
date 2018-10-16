/**
 * Created by shenq on 2016/12/25.
 */
window.alert = function (title) {
    swal(title)
};
window.alertCallback = function (title, callback) {
    swal({
        title: title
    },function(){
        callback();
    });
};
window.confirm = function (title, callback) {
    swal({
        title: title,
        cancelButtonText: "取消",
        confirmButtonText: "确认",
        showCancelButton: true
    },function(){
        callback();
    });
};

window.onload=function () {
    //thousand-format
    $('input.thousand-format').each(function (i, e) {
        var inputClone = $(this).clone();
        inputClone.attr('data-pid', inputClone.attr('id')).removeAttr('id');
        $(this).attr('type', 'hidden').removeClass('thousand-format').after(inputClone);
    });

    $('body').on('keyup', '.thousand-format', function(){
        // 真实值
        var value = $(this).val();
        // 转数字存hidden
        $('#'+$(this).attr('data-pid')).val(thousandToNumber(value));
        // 更新千分位，考虑小数位
        $(this).val(numberToThousand(thousandToNumber(value)));

        if ($('#cball').length>0) {
            var num = 0;
            $(".cb[type='hidden']").each(function () {
                var self = $(this);
                if (self.val()) {
                    num += parseInt(self.val());
                }
            })
            $('#cball').html(numberToThousand(num))
            $('#srall').val(numberToThousand(num))
        }
    });



};


function thousandToNumber(str){
    var temp ='';
    str.split(',').forEach(function(e,i,a){
        temp += e;
    });
    return temp;
}
function numberToThousand(number) {
    // 分割小数点
    var isFloat = (number+'').split('.').length>1;
    var rNum = (number+'').split('.')[1];
    var lNum = (number+'').split('.')[0];
    var tempStr = '';
    if (!rNum) {
        rNum = '';
    }
    if (!lNum) {
        lNum = '';
    }else{
        lNum.split('').reverse().forEach(function (e, i, a) {
            if (i%3 == 0 && i) {
                tempStr += ',';
            }
            tempStr += e;
        });

        return tempStr.split('').reverse().join('') + (isFloat?'.':'') + rNum;
    }
}