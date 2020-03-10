/*未登录提示*/
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

/*获取当前项目主机地址*/
function getLocalhostPath() {
    const curWwwPath = window.document.location.href;
    const pathName=window.document.location.pathname;
    const pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    return curWwwPath.substring(0,pos);
}

/*根据传递的类型提交评论*/
function submit_comment_by_type(parent_id, type, content) {
    if (!content) {
        alert("不能输入空的内容哦~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parent_id,
            "content": content,
            "type": type
        }),
        dataType: "json",
        success: function (result) {
            if (result.code === 2000) {
                window.location.reload();
                $("#comment_frame").hide();
            } else {
                if (result.code === 2004) {
                    const confirm = window.confirm(result.message);
                    if (confirm) {
                        window.open(getLocalhostPath()+"/login");
                        localStorage.setItem('closable', '1');//0：不关闭 1：关闭
                    }
                } else {
                    alert(result.message);
                }
            }
        }
    });
}

/*提交问题的回复*/
function submit_question_comment() {
    const id = $("#question_parent_id").val();
    const content = $("#question_comment_content").val();
    submit_comment_by_type(id, 1, content);
}

/*提交评论的回复*/
function submit_comment_comment(e) {
    const id = e.getAttribute("data-id");//获得回复的id
    const content = $("#input-" + id).val();//根据id获得评论内容
    submit_comment_by_type(id, 2, content);
}

/*显示或收起回复的评论*/
function show_subComment(e) {
    const id = e.getAttribute("data-id");//获取评论id
    $('#sub_comment_btn-' + id).toggleClass("active");
    var subCommentContainer = $('#comment-' + id);
    subCommentContainer.toggleClass("in");

    if (subCommentContainer.hasClass("in") && subCommentContainer.children().length === 1) {
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                const mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<a/>", {
                    "href": "/user/"+comment.user.id
                })).append($("<img/>", {
                    "class": "media-object img-rounded user_avatar",
                    "src": comment.user.avatarUrl
                }));

                const mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<span/>", {
                    "class": "media-heading question_text",
                    "html": comment.user.name
                })).append($("<br/>")).append($("<span/>", {
                    "html": comment.content
                }));

                const mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                const time = moment(comment.gmtCreate).format('YYYYMMDD HHmmss');
                const operatingElement = $("<div/>", {
                    "class": "comment_operating"
                }).append($("<span/>", {
                    "class": "glyphicon glyphicon-thumbs-up btn"
                })).append($("<span/>", {
                    "class": "question_text to-right",
                    "html": moment(time, "YYYYMMDD HHmmss").fromNow()
                }));

                const lineElement = $("<hr/>", {
                    "class": "comment_cut_line"
                });

                const commentElement = $("<div/>")
                    .append(mediaElement)
                    .append(operatingElement)
                    .append(lineElement);

                subCommentContainer.prepend(commentElement);
            });
        });
    }
}

/*展示关闭标签*/
function showSelectTag(tagDtosJson) {
    const content = $("#tags_input").val();//获取输入框的值
    let arr = content.split(",");
    let list = Array.from(eval(tagDtosJson));
    let map = list.map(Object.values);
    console.log(map);
    for(let i = 0; i < map.length; i++){
        let temp = map[i];
        for(let j = 1; j < temp.length; j++){
            let tagArr = temp[j];
            for(let k = 0; k < tagArr.length; k++){
                for(let n = 0; n < arr.length; n++){
                    if(arr[n] === tagArr[k]){
                        $('#all-' + arr[n]).addClass("publish-tag-selected");
                    }
                }
            }
        }
    }
    $("#tag-list").show();
}

function closeSelectTag() {
    $("#tag-list").hide();
}

let count = 0;//选择的标签数
/*添加标签*/
function selectTag(e) {
    const value = e.getAttribute("data-tag");//获取标签的值
    const content = $("#tags_input").val();//获取输入框的值
    //防止字串干扰
    let arr = content.split(',');
    count = arr.length;
    if (arr.indexOf(value) === -1) {
        if (++count > 5) {
            alert("最多选择5个标签");
            return;
        }
        $('#all-' + value).addClass("publish-tag-selected");
        if (content) {
            $("#tags_input").val(content + ',' + value);
        } else {
            $("#tags_input").val(value);
        }
    } else {
        alert('不要再点啦，我已经选过了')
    }
}

/*删除标签*/
function deleteTag(e) {
    const removeValue = e.getAttribute("data-tag");//获取待删除的值
    const content = $("#tags_input").val();//获取输入框的值
    //防止字串干扰
    let arr = content.split(',');
    count = arr.length;
    if (arr.indexOf(removeValue) > -1) {
        for (let i = 0; i < arr.length; i++) {
            if (arr[i] === removeValue) {
                arr.splice(i, 1);
                $('#all-' + removeValue).removeClass("publish-tag-selected");
                count--;
                break;
            }
        }
        $("#tags_input").val(arr.join(','));
    } else {
        alert('不要再点啦，我还没被选呢');
    }
}

/*每日一句*/
$.getJSON("https://api.ooopn.com/ciba/api.php?type=json",
    function(data){
        $("#one-day-text-cn").text(data.ciba);
        $("#one-day-time").text(data.date);
        $("#one-day-img").attr("src", data.imgurl);
    });
$(function(){
    $("#one-day-text-cn").click(function() {
        $(this).select();
    })
});

/*页面顶置*/
let FourLeafCloverZCVar;
function FourLeafCloverZCTopFunc(){
    FourLeafCloverZCVar=setInterval(FourLeafCloverZCEachScrollBy,10);
}
function FourLeafCloverZCEachScrollBy(eachHeight){
    if(document.documentElement && document.documentElement.scrollTop)  //IE
    {
        if(document.documentElement.scrollTop<=0){
            clearInterval(FourLeafCloverZCVar);
        }else{
            window.scrollBy(0,-30);
        }
    }else{          //Chrome不支持documentElement.scrollTop
        if(document.body.scrollTop<=0){
            clearInterval(FourLeafCloverZCVar);
        }else{
            window.scrollBy(0,-30);
        }
    }
}

function notOpen() {
    alert("暂未开放，请使用第三方登录 ^-^")
}