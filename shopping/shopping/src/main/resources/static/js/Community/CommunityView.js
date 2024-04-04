function zoomIn(id)
    {
        document.getElementById(id).style.width = "300px";
        document.getElementById(id).style.height = "300px";
    }
function zoomOut(id)
    {
        document.getElementById(id).style.width = "150px";
        document.getElementById(id).style.height = "150px";
    }
function Comment()
    {
        if(document.getElementById("Comment_content_box").value == "")
        {
            alert("댓글 내용을 입력해주세요!");
            return false;
        }
    }
function change_content(key, content)
    {
        document.getElementById("Comment_content_box").value = content;
        document.getElementById("comment_idx").value = key;
    }