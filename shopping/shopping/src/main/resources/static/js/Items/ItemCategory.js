function category_check()
{
    var code = document.getElementById("modify_code").value;
    if(code.length == 2)
    {
        if(confirm("대분류는 삭제 시 중분류와 소분류도 함께 삭제됩니다.\n계속 하시겠습니까?") == true)
        {
            alert("삭제되었습니다.");
        }
        else
        {
            alert("중지합니다.");
            return false;
        }
    }
    else if(code.length == 4)
    {
        if(confirm("중분류는 삭제 시 소분류도 함께 삭제됩니다.\n계속 하시겠습니까?") == true)
        {
            alert("삭제되었습니다.");
        }
        else
        {
            alert("중지합니다.");
            return false;
        }
    }
    else
    {
        if(confirm("삭제하시겠습니까?") == true)
        {
            alert("삭제되었습니다.");
        }
        else
        {
            alert("중지합니다.");
            return false;
        }
    }
}

function set_second(code, id, name)
{
    var second_area = document.getElementById("second");
    var third_area = document.getElementById("third");
    $(".first_class").css("fontWeight","normal");
    document.getElementById(id).style.fontWeight = "bold";

        $.ajax({
            url : '/admin/SetLabel',
            data : {code : code},
            type : 'POST',
            dataType : 'json',
            success : function(result){
                $("#second").empty();
                $("#modify_second").empty();
                for(var a=0; a<result.length; a++)
                {
                    if(result[a].split("/")[0].length == 4)
                    {
                        var second_data = document.createElement("span");
                        var br = document.createElement("br");

                        second_data.innerHTML = result[a].split("/")[1];
                        second_data.id = result[a];
                        second_data.className = 'second_class';
                        second_data.style = 'cursor:pointer';
                        second_data.onclick = function(){set_third(this.id)};


                        second_area.append(second_data);
                        second_area.append(document.createElement("br"));
                        second_area.append(document.createElement("br"));
                    }
                }
                second_area.style.display = "inline";
                third_area.style.display = "none";
            }
        });
        document.getElementById("modify_code").value = code;
        document.getElementById("modify_name").value = name;
}

function set_third(value) // value : 1010/한국, 1020/일본 ... (code + name)
{
    $(".second_class").css("fontWeight","normal");
    document.getElementById(value).style.fontWeight = "bold";
    var code = value.split("/")[0];
    var name = value.split("/")[1];

    var third_area = document.getElementById("third");

    $.ajax({
        url : '/admin/SetLabel',
        data : {code : code},
        type : 'POST',
        dataType : 'json',
        success : function(result){
            $("#third").empty();
            for(var a=0; a<result.length; a++)
            {
                if(result[a].split("/")[0].length == 6)
                {

                    var third_data = document.createElement("span");
                    var br = document.createElement("br");

                    third_data.innerHTML = result[a].split("/")[1];
                    third_data.id = result[a];
                    third_data.className = 'third_class';
                    third_data.style = 'cursor:pointer';
                    third_data.onclick = function(){set_data(this.id)}

                    third_area.append(third_data);
                    third_area.append(document.createElement("br"));
                    third_area.append(document.createElement("br"));
                }
            }
            third_area.style.display = "inline";
        }
        });
        document.getElementById("modify_code").value = code;
        document.getElementById("modify_name").value = name;
}

function set_data(value)
{
    document.getElementById(value).style.fontWeight = "bold";
    var code = value.split("/")[0];
    var name = value.split("/")[1];
    document.getElementById("modify_code").value = code;
    document.getElementById("modify_name").value = name;
}

function change_label1(value)
{
    if(value == "category1")
    {
        document.getElementById("category2").style.display = "none";

        $.ajax({
        url : '/admin/LabelCheck',
        data : {category : value},
        type : 'POST',
        dataType : 'json',
        success : function(result){
        document.getElementById("code").value = result;
        }
        });
    }
    else
    {
        $.ajax({
        url : '/admin/SetLabel',
        data : {code : value},
        type : 'POST',
        dataType : 'json',
        success : function(result){
                var ul = document.getElementById("dropdown-menu2");
                $("#dropdown-menu2").empty();

                var basic_li1 = document.createElement("li");
                var basic_button = document.createElement("button");
                basic_button.className = "dropdown-item";
                basic_button.setAttribute("data-bs-toggle","dropdown");
                basic_button.setAttribute("aria-expanded","false");
                basic_button.innerText = "2차 카테고리 추가";
                basic_button.value = "category2";
                basic_button.onclick = function(){change_label2(this.value, value)}
                basic_li1.appendChild(basic_button);

                var basic_li2 = document.createElement("li");
                var basic_hr = document.createElement("hr");
                basic_hr.className = "dropdown-divider";
                basic_li2.appendChild(basic_hr);

                ul.appendChild(basic_li1);
                ul.appendChild(basic_li2);
                
                for(var a=0; a<result.length; a++)
                {
                    var li = document.createElement("li");
                    var button = document.createElement("button");
                    button.className = "dropdown-item";
                    button.onclick = function(){change_label2(this.value, value)}
                    button.setAttribute("type","button");

                    if(result[a].split("/")[0].length == 4)
                    {
                        button.value = result[a].split("/")[0];
                        button.innerText = result[a].split("/")[1];
                        li.appendChild(button);
                        ul.appendChild(li);

                    }
                }
                document.getElementById("code").value = "none";
                document.getElementById("category2").style.display = "inline";
        }
        });
    }
}

function change_label2(value, first)
{
    var first = first;

    if(value == "category2")
    {
        $.ajax({
        url : '/admin/LabelCheck',
        data : {category : value, info : first},
        type : 'POST',
        dataType : 'json',
        success : function(result){
            document.getElementById("code").value = result;
        }
        });
    }
    else
    {
            $.ajax({
            url : '/admin/LabelCheck',
            data : {category : 'category3', info : value},
            type : 'POST',
            dataType : 'json',
            success : function(result){
                document.getElementById("code").value = result;
            }
            });
    }
}