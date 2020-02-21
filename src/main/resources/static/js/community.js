$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});

function submit_comment() {
    var commentParentId = $("#comment_parent_id").val();
    var commentContent = $("#comment_content").val();
    if(!commentContent){
        alert("不能输入空的内容哦~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": commentParentId,
            "content": commentContent,
            "type": 1
        }),
        dataType: "json",
        success: function (result) {
            if(result.code === 2000){
                window.location.reload();
                $("#comment_frame").hide();
            }else{
                if(result.code === 2004){
                    const confirm = window.confirm(result.message);
                    if(confirm){
                        window.open("https://github.com/login/oauth/authorize?client_id=b3118b6cffce13fd5b90&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        localStorage.setItem('closable','1');//0：不关闭 1：关闭
                    }
                }else{
                    alert(result.message);
                }
            }
        }
    });
}

