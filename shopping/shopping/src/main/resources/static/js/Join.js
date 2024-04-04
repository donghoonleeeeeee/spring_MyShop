function address_api()
{
    new daum.Postcode({
        oncomplete: function(data)
        {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') // 사용자가 도로명 주소를 선택했을 경우
            {
                addr = data.roadAddress;
            }
            else // 사용자가 지번 주소를 선택했을 경우(J)
            {
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R')
            {
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname))
                {
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y')
                {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== '')
                {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("add3").value = extraAddr;
            }
            else
            {
                document.getElementById("add3").value = '';
            }
            document.getElementById('add1').value = data.zonecode;
            document.getElementById("add2").value = addr;
        }
    }).open();
}

function checking()
{
    var userid = $('#userid').val();
    $.ajax({
        url : '/userid_check',
        data : {userid : userid},
        type : 'POST',
        dataType : 'json',
        success : function(result){
            if(result == true)
            {
                $('#id_check').css("color","red")
                $('#userid').css("border-color","red")
                document.getElementById("id_check").innerHTML = "사용할 수 없는 아이디!";
            }
            else
            {
                if(document.getElementById("userid").value == "")
                {
                    $('#id_check').css("color","red")
                    $('#userid').css("border-color","red")
                    document.getElementById("id_check").innerHTML = "아이디를 입력해주세요!";
                }
                else
                {
                    $('#id_check').css("color","green")
                    $('#userid').css("border-color","#0982f0")
                    document.getElementById("id_check").innerHTML = "사용 가능한 아이디!";
                }
            }
        }
    });
}

function join_check()
{
    if(mf.userid.value == "")
    {
        alert("아이디를 입력해주세요!");
        mf.userid.focus();
        return false;
    }
    else if(document.getElementById("id_check").innerHTML == "사용할 수 없는 아이디!" || document.getElementById("id_check").innerHTML == "아이디를 입력해주세요!")
    {
        alert("아이디를 확인해주세요!");
        mf.userid.focus();
        return false;
    }
    else if(mf.password.value == "")
    {
        alert("비밀번호를 입력해주세요!");
        mf.password.focus();
        return false;
    }
    else if(mf.password_ck.value == "")
    {
        alert("비밀번호 확인을 입력해주세요!");
        mf.password_ck.focus();
        return false;
    }
    else if(mf.password.value != mf.password_ck.value)
    {
        alert("비밀번호 확인이 일치하지 않습니다!");
        mf.password_ck.focus();
        return false;
    }
    else if(mf.name.value == "")
    {
        alert("이름을 입력해주세요!");
        mf.name.focus();
        return false;
    }
    else if(mf.f_rrn.value == "" || mf.l_rrn.value == "")
    {
        alert("주민번호를 확인해주세요!");
        mf.f_rrn.focus();
        return false;
    }
    else if(mf.hp.value == "")
    {
        alert("휴대폰 번호를 입력해주세요!");
        mf.hp.focus();
        return false;
    }
    else if(mf.add1.value == "" || mf.add2.value == "")
    {
        alert("주소지 정보가 부족합니다!");
        mf.add2.focus();
        return false;
    }
    else if(mf.mail.value == "")
    {
        alert("이메일을 입력해주세요!");
        mf.mail.focus();
        return false;
    }
    else if(mf.web.value == "none")
    {
        alert("웹 주소를 선택해주세요!");
        mf.web.focus();
        return false;
    }
}

function input_l_rrn(value)
{
    if(value>8)
    {
        alert("주민번호 뒷자리 최대 값은 8입니다 \n다시한번 확인해주세요!");
        document.getElementById("l_rrn").value = "";
        mf.l_rrn.focus();
        return false;
    }
}