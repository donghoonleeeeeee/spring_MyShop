window.onload = function()
    {
        var price = document.getElementsByClassName("price");
        var result = 0;
        for(var a=0; a<price.length; a++)
        {
            result += parseInt(price[a].innerHTML);
        }
        document.getElementById("total").innerHTML = result;
        document.getElementById("pay_price").innerHTML = result;
        document.getElementById("total_pay").value = result;

        var pay_cash = parseInt(document.getElementById("pay_cash").innerHTML);
        var pay_price = parseInt(document.getElementById("pay_price").innerHTML);
        if(pay_cash >= pay_price) // 잔액 < 구매금액
        {
            document.getElementById("pay_result").innerHTML = pay_cash - pay_price;
        }
        else
        {
            alert("잔액이 부족합니다!");
            location.replace('/User/Basket');
        }
    }

    function request_set(value)
    {
        if(value == "직접입력")
        {
            document.getElementById("request_content").innerHTML = "";
        }
        else if(value == "none")
        {
            document.getElementById("request_content").innerHTML = "";
        }
        else
        {
            document.getElementById("request_content").innerHTML = value;
        }
    }

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
    function payment_way(value)
    {
        if(value == "account")
        {
            document.getElementById("account_div").style.display = "inline";
            document.getElementById("card_div").style.display = "none";
            document.getElementById("drink_pay_div").style.display = "none";
        }
        else if(value == "card")
        {
            document.getElementById("account_div").style.display = "none";
            document.getElementById("card_div").style.display = "inline";
            document.getElementById("drink_pay_div").style.display = "none";
        }
        else if(value == "Drink_Pay")
        {
            document.getElementById("account_div").style.display = "none";
            document.getElementById("card_div").style.display = "none";
            document.getElementById("drink_pay_div").style.display = "inline";
        }
    }
    function random_account(value)
    {
        if(value == "none")
        {
            document.getElementById("auto_account").value = "";
        }
        else if(value == "NH_BANK")
        {
            document.getElementById("auto_account").value = "001-1234-1234-1";
        }
        else if(value == "DGB_BANK")
        {
            document.getElementById("auto_account").value = "002-1234-1234-2";
        }
        else if(value == "IBK_BANK")
        {
            document.getElementById("auto_account").value = "003-1234-1234-3";
        }
        else if(value == "SHINHAN_BANK")
        {
            document.getElementById("auto_account").value = "004-1234-1234-4";
        }
    }

    function set_add(value, user)
    {
        if(value == "user")
        {
            // 받는 분
            document.getElementById("getter").value = document.getElementById("my_name").value;
            // 연락처
            document.getElementById("hp").value = document.getElementById("my_hp").value;
            // add1
            document.getElementById("add1").value = document.getElementById("my_add1").value;
            // add2
            document.getElementById("add2").value = document.getElementById("my_add2").value;
            // add3
            document.getElementById("add3").value = document.getElementById("my_add3").value;
        }
        else
        {
            // 받는 분
            document.getElementById("getter").value = "";
            // 연락처
            document.getElementById("hp").value = "";
            // add1
            document.getElementById("add1").value = "";
            // add2
            document.getElementById("add2").value = "";
            // add3
            document.getElementById("add3").value = "";
        }
    }