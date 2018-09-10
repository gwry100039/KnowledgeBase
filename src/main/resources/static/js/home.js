var fields = [
    'REQUIREMENT_DESC',
    'comments'
];

var highlightPre = 'G20810910R20810910||';
var highlightPost = '||G20810910R20810910';

function html_encode(str)
{
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&/g, "&amp;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br/>");
    return s;
}

function on_data(data) {
    $('#results').empty();

    /*
    [
      {
        "REQUIREMENT_DESC":"根据被嵌入内容的外部容器的宽度，自动创建一个固定的比例，从而让浏览器自动确定视频或 slideshow 的尺寸，能够在各种设备上缩放。\n\n这些规则被直接应用在 <iframe>、<embed>、<video> 和 <object> 元素上。如果你希望让最终样式与其他属性相匹配，还可以明确地使用一个派生出来的 .embed-responsive-item 类。\n\n超级提示： 不需要为 <iframe> 元素设置 frameborder=\"0\" 属性，因为我们已经替你这样做了！",
        "comments":"啊实打实打算的s",
        "id":"2018-07-26 14:12:34.024",
        "_version_":1609485857122680832
      }
    ]
     */
    var docs = data.response.docs;
    /*
        "2018-07-26 14:12:34.024":{
        "REQUIREMENT_DESC":["<em>根</em><em>据</em>被嵌入内容的外部容器的宽度，自动创建一个固定的比例，从而让浏览器自动确定视频或 slideshow 的尺寸，能够在各种设备上缩放。\n\n这些规则被直接应用在 <iframe>、<embed"]}
     */
    var highlighting = data.highlighting;

    console.log(docs);

    $.each(docs, function (i, item) {
        var highlightObject = highlighting[item.id];

        //获取高亮搜索结果的html，可能有多个
        $.each(fields, function (i, item) {
            if (highlightObject[item] != undefined) {
                var html = highlightObject[item][0];
                var finalHtml = html_encode(html).split(highlightPre).join("<em>").split(highlightPost).join("</em>");
                console.log(finalHtml);
            }
        });
    });

    var total = 'Found ' + docs.length + ' results';
    $('#results').prepend('<div>' + total + '</div>');
}

function on_search() {
    var query = $('#query').val();
    if (query.length == 0) {
        return;
    }

    var q = '';

    $.each(fields, function (i, item) {
        q += item + ':' + encodeURIComponent(query) + ' ';
    });

    console.log(q);

    var url = 'http://10.181.136.168:8983/solr/new_core/select?hl.fl=' + fields.join(',') + '&hl=on&q=' + q + '&wt=json&hl.simple.pre='+highlightPre+'&hl.simple.post='+highlightPost;
    $.getJSON(url, on_data);
}

$(document).ready(function () {
    $('body').keypress(function (e) {
        if (e.keyCode == '13') {
            on_search();
        }
    });
});