$(document).ready(function () {

    $("#sql-textarea > textarea").keypress(function (event) {
        var key = event.which; //e.which是按键的值
        var text = $(this).val();
        if (key == 10 && event.ctrlKey) {//ctrl+enter
            $('ul.list-group').remove();
            $.post("/getSqlAnalyzeResult", {sql: text}, function (result) {
                $('#sql-textarea').after(result);
            }).error(function (result) {
                console.log("SQL语法错误:" + result.responseJSON.message);
                alert("SQL语法错误:" + result.responseJSON.message);
            });
        }
    });

    //获取焦点后去掉红色提示边框
    $('input[name], textarea[name]').focus(function(){
        $(this).removeClass("error-input");
    });

    //提交表单
    $('button.btn.btn-primary').click(function (e) {
        $('button.btn.btn-success').attr('disabled', true);
        var inputList = $('input[name], textarea[name]');
        var isValid = true;
        for (i = 0; i < inputList.size(); i++) {
            // console.log(inputList[i].val());
            var inputValue = $(inputList[i]).val();
            if (inputValue === "") {
                $(inputList[i]).addClass("error-input");
                if(isValid){//滚动条滚到第一个错误的input那里
                    $('html, body').animate({
                        scrollTop: $("#data-caliber-form").offset().top - 200
                    }, 200);
                }
                isValid = false;
            }
        }

        if (!isValid) {
            $('button.btn.btn-success').removeAttr('disabled');
            return false;
        }
        $.post("/getSqlAnalyzeResult", {sql: $("#sql-textarea > textarea").val()}, function (result) {
            $('#data-caliber-form').submit();
        }).error(function (result) {
            console.log("SQL语法错误:" + result.responseJSON.message);
            alert("SQL语法错误:" + result.responseJSON.message);
            $('button.btn.btn-success').removeAttr('disabled');
        });
    });

    //取消录入
    $('button.btn.btn-default').click(function (e) {
        $('#data-caliber-form').slideUp(function () {
            $("#initial-input").css("display", "block");
            $("#initial-input").animate({
                "opacity": "1"
            }, 500);
        });
    });

    $('#initial-input').focus(function (e) {
        $("#initial-input").animate({
            "opacity": "0"
        }, 500);
        $("#initial-input").css("display", "none");
        // $('#initial-input').parent().parent().hide(function () {
        $('#data-caliber-form').slideDown();
        // });
    });

});