
/*
提交回复
 */
function post(){
    var questionId = $("#question_id").val();
    var content=$("#comment_content").val();
    coment2target(questionId,1,content);
}
/*
提交2级评论
 */
function  coment(e) {
    var commentId = e.getAttribute("data-id");
    var content=$("#input-"+commentId).val();
    coment2target(commentId,2,content);
}

function  coment2target(targetId,type,content) {
    if (!content){
        alert("不能回复空内容~~");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType: 'application/json',
        data:JSON.stringify({
            "parenId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if(response.code==200){
                /* $("#comment_section").hide();//隐藏*/
                window.location.reload();
            }else{
                if (response.code==2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=754ad3bc908f1eeee782&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message);

                }

            }
            console.log(response);
        },
        dataType:"json"
    });
}



/**
 * 展开二级评论
 */

function collaapseComments(e) {
    var id = e.getAttribute("data-id");//拿前端的值
    var comments=$("#comment-"+id);
    console.log(comments);
    var attribute = e.getAttribute("data-collapse");
    if (attribute){
        comments.removeClass("in");
        e.removeAttribute("data-collapse")
        e.classList.remove("active");
    }else{

        var subCommentContainer=$("#comment-"+id);
        if (subCommentContainer.children().length!=1){

            comments.addClass("in");
            //标记二级评论
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {

                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarurl
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtcreate).format('YYYY-MM-DD')
                    })));
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12  comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);

                });
                comments.addClass("in");
                //标记二级评论
                e.setAttribute("data-collapse","in");
                e.classList.add("active");

            });
        }
    }

}
function selectTag(e) {//添加标签
    var value=e.getAttribute("data-tag");
    var previous=$("#tag").val();
    if (previous.indexOf(value)==-1){
        if (previous){
            $("#tag").val(previous+','+value);
        }else{
            $("#tag").val(value);
        }
    }

}

function showSelectTag() {//点击就展示
$("#select-tag").show();
}



