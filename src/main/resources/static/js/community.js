/**
 * 未登录提示
 */
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});
$(function () {
    $('[data-toggle="popover"]').popover()
});

/**
 * 获取当前项目主机地址
 * @returns {string}
 */
function getLocalhostPath() {
    const curWwwPath = window.document.location.href;
    const pathName = window.document.location.pathname;
    const pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    return curWwwPath.substring(0, pos);
}

/**
 * 根据传递的类型提交评论
 * @param parent_id
 * @param type
 * @param content
 */
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
                alert(result.message);
            }
        }
    });
}

/**
 * 提交问题的回复
 */
function submit_question_comment() {
    const id = $("#question_parent_id").val();
    const content = $("#question_comment_content").val();
    submit_comment_by_type(id, 1, content);
}

/**
 * 提交评论的回复
 * @param e
 */
function submit_comment_comment(e) {
    const id = e.getAttribute("data-id");//获得回复的id
    const content = $("#input-" + id).val();//根据id获得评论内容
    submit_comment_by_type(id, 2, content);
}

/**
 * 显示或收起回复的评论
 * @param e
 */
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
                    "href": "/user/" + comment.user.id
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

/**
 * 展示标签集
 */
function showSelectTag() {
    let selectedList = $("#tags_input").val().split(",");
    selectedList.forEach(selected => $(document.getElementById('all' + selected)).addClass("publish-tag-selected"));
    $("#tag-list").show();
}

/**
 * 关闭标签集
 */
function closeSelectTag() {
    $("#tag-list").hide();
}

let count = 0;//已选择的标签数
/**
 * 添加标签
 * @param e
 */
function selectTag(e) {
    const value = e.getAttribute("data-tag");//获取标签的值
    let $tagsInput = $("#tags_input");
    const content = $tagsInput.val();//获取输入框的值
    //防止字串干扰
    let arr = content.split(',');
    count = arr.length;
    if (arr.indexOf(value) === -1) {
        if (++count > 5) {
            alert("最多选择5个标签");
            return;
        }
        $(document.getElementById('all' + value)).addClass("publish-tag-selected");
        if (content) {
            $tagsInput.val(content + ',' + value);
        } else {
            $tagsInput.val(value);
        }
    } else {
        alert('不要再点啦，我已经选过了')
    }
}

/**
 * 删除标签
 * @param e
 */
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
                $(document.getElementById('all' + removeValue)).removeClass("publish-tag-selected");
                count--;
                break;
            }
        }
        $("#tags_input").val(arr.join(','));
    } else {
        alert('不要再点啦，我还没被选呢');
    }
}

/**
 * 每日一句
 */
/*$.getJSON("https://api.ooopn.com/ciba/api.php?type=json",
    function (data) {
        $("#one-day-text-cn").text(data.ciba);
        $("#one-day-time").text(data.date);
        $("#one-day-img").attr("src", data.imgurl);
    });
$(function () {
    $("#one-day-text-cn").click(function () {
        $(this).select();
    })
});*/

/**
 * 页面顶置
 */
$(function () {
    $(window).scroll(function () {
        var scrollTop = $(this).scrollTop();
        if (scrollTop >= 300) {
            $("#returnTop").show();
        } else {
            $("#returnTop").hide();
        }
    });
    $("#returnTop").click(function () {
        $('html,body').animate({scrollTop: 0}, 200);
    })
});

/**
 * 发送邮件
 */
function sendEmail() {
    let email = $("#register-email").val();
    if (email == null || email.trim().length === 0) {
        alert("请输入邮箱！");
        return;
    }
    $.ajax({
        url: "/sendEmail",
        type: "GET",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            if (data.code === 2000) {
                invokeSetTime("#send-email-btn");
            } else {
                alert(data.message);
            }
        }
    })
}

/**
 * 发送邮件后定时60秒
 * @param obj
 */
function invokeSetTime(obj) {
    let countdown = 60;
    setTime(obj);

    function setTime(obj) {
        if (countdown === 0) {
            $(obj).attr("disabled", false);
            $(obj).text("获取验证码");
            countdown = 60;
            return;
        } else {
            $(obj).attr("disabled", true);
            $(obj).text("(" + countdown + ") s 重新发送");
            countdown--;
        }
        setTimeout(function () {
            setTime(obj)
        }, 1000);
    }
}

/**
 * 表单校验自定义主题
 */
function customTheme() {
    $.validator.setTheme('yellow_right_effect', {
        validClass: 'has-success',
        invalidClass: 'has-error',
        bindClassTo: '.form-group',
        msgClass: 'n-right',
    });
}

/**
 * 删除问题
 */
function deleteQuestion(qid) {
    console.log("qid", qid);
    if (confirm("此操作不可逆，你确定要删除吗？")) {
        $.ajax({
            type: "GET",
            url: "/question/delete/"+qid,
            success: function () {
                alert("删除成功");
                window.location.href="/";
            }
        });
    }
}