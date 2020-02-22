/*获取源码未登录提示*/
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

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
                        window.open("https://github.com/login/oauth/authorize?client_id=aeb0d5116950de9541ab&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        localStorage.setItem('closable', '1');//0：不关闭 1：关闭
                    }
                } else {
                    alert(result.message);
                }
            }
        }
    });
}

/*提交问题的评论*/
function submit_question_comment() {
    const id = $("#question_parent_id").val();
    const content = $("#question_comment_content").val();
    submit_comment_by_type(id, 1, content);
}

/*提交回复的评论*/
function submit_comment_comment(e) {
    const id = e.getAttribute("data-id");//获得回复的id
    const content = $("#input-" + id).val();//根据id获得评论内容
    submit_comment_by_type(id, 2, content);
}

/*显示或收起回复的评论*/
function show_or_close_subComment(e) {
    const id = e.getAttribute("data-id");//获取评论id
    $('#sub_comment_btn-' + id).toggleClass("active");
    var subCommentContainer = $('#comment-' + id);
    subCommentContainer.toggleClass("in");

    if(subCommentContainer.hasClass("in") && subCommentContainer.children().length === 1){
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<a/>", {
                    "href": "#"
                })).append($("<img/>", {
                    "class": "media-object img-rounded user_avatar",
                    "src": comment.user.avatarUrl == null ? '' : comment.user.avatarUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<span/>", {
                    "class": "media-heading question_text",
                    "html": comment.user.name
                })).append($("<br/>")).append($("<span/>", {
                    "html": comment.content
                }));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var operatingElement = $("<div/>", {
                    "class": "comment_operating"
                }).append($("<span/>", {
                    "class": "glyphicon glyphicon-thumbs-up btn"
                })).append($("<span/>", {
                    "class": "question_text time",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                }));

                var lineElement = $("<hr/>", {
                    "class" : "comment_cut_line"
                });

                var commentElement = $("<div/>")
                    .append(mediaElement)
                    .append(operatingElement)
                    .append(lineElement);

                subCommentContainer.prepend(commentElement);
            });
        });
    }
    /*
<div th:each="commentDto : ${commentDtos}">
    <div class="media">
        <div class="media-left">
            <a href="#">
                <img class="media-object img-rounded user_avatar"
                     th:src="${commentDto.user.avatarUrl} == null ? '' : ${commentDto.user.avatarUrl}"
                     alt="...">
            </a>
        </div>
        <div class="media-body">
            <span class="media-heading question_text"
                  th:text="${commentDto.user.name}">姓名</span><br>
            <span th:text="${commentDto.content}"></span>
        </div>
    </div>
    <!--子评论操作-->
    <div class="comment_operating">
        <span class="glyphicon glyphicon-thumbs-up btn" aria-hidden="true"></span>
        <span class="question_text time"
              th:text="${#dates.format(commentDto.gmtModified, 'yyyy-MM-dd')}">评论时间</span>
    </div>
    <hr class="comment_cut_line">
</div>
     */
}