$(document).ready(function () {

    $("#sql-textarea > textarea").keypress(function (event) {
        var key = event.which; //e.which是按键的值
        var text = $(this).val();
        if (key == 10 && event.ctrlKey ) {//ctrl+enter
            $('ul.list-group').remove();
            $.post("/getSqlAnalyzeResult", {sql: text}, function (result) {
                $('#sql-textarea').after(result);
            });
        }
    });

    //TODO 提交button的事件绑定
});