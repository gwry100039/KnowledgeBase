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

    //提交表单
    $('button.btn.btn-primary').click(function (e) {
        $('button.btn.btn-success').attr('disabled', true);
        for (x in $('input[name]')) {
            console.log(x);
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
            $("#initial-input").css("display","block");
            $("#initial-input").animate({
                "opacity": "1"
            }, 500);
        });
    });

    $('#initial-input').focus(function (e) {
        $("#initial-input").animate({
            "opacity": "0"
        }, 500);
        $("#initial-input").css("display","none");
        // $('#initial-input').parent().parent().hide(function () {
            $('#data-caliber-form').slideDown();
        // });
    });


});