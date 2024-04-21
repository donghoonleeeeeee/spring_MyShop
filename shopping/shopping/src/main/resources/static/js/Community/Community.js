    var url = window.location.href;
    var params = new URLSearchParams(url.split('?')[1]);
    var value = params.get('type');
    if(value == "전체" || value == null)
    {
        $('#type1').css("background-color","black");
        $('#type1').css("color","white");

        $('#type2').css("background-color","white");
        $('#type2').css("color","black");

        $('#type3').css("background-color","white");
        $('#type3').css("color","black");
    }
    else if(value == "공지")
    {
        $('#type1').css("background-color","white");
        $('#type1').css("color","black");

        $('#type2').css("background-color","black");
        $('#type2').css("color","white");

        $('#type3').css("background-color","white");
        $('#type3').css("color","black");
    }
    else
    {
        $('#type1').css("background-color","white");
        $('#type1').css("color","black");

        $('#type2').css("background-color","white");
        $('#type2').css("color","black");

        $('#type3').css("background-color","black");
        $('#type3').css("color","white");
    }

function select_type(value)
{
    var type = value;
    $.ajax({
        url : '/main/Community',
        data : {type : type},
        type : 'GET',
        dataType : 'text',
        success : function(result){
            location.href='/main/Community?type='+type;
        },
        error : function(request, error){
                    alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
                }
    });
}
function delete_checked()
{
    var result = document.querySelectorAll('.check:checked').length;
    if(result == "0")
    {
        alert("체크된 항목이 없습니다!");
        return false;
    }
}