

$(document).ready(function(){

    $("#sign_up").click(function(){
        var f_name = $("#f_name").val();
        var l_name = $("#l_name").val();
        var address = $("#address").val();
        var license_no = $("#license_no").val();
        var phone_no = $("#phone_no").val();
        var email = $("#email").val();
        var password = $("#password").val();
        var password2 =$("#password2").val();
        var ok = true;


        var isValid = true;

        if(f_name==""){
            isValid = false;
            $("#errorFirstname").html("First Name cannot be empty");

        }else{
            $("#errorFirstname").html("");
        }

        if(l_name == ""){
            isValid = false;
            $("#errorLastname").html("Last Name cannot be empty");

        }else{
            $("#errorLastname").html("");
        }

        if(address == ""){
            isValid = false;
            $("#errorAddress").html("Address cannot be empty");

        }else{
            $("#errorAddress").html("");
        }

        if(license_no == ""){
            isValid = false;
            $("#errorLicenseNumber").html("License Number cannot be empty");

        }else{
            $("#errorLicenseNumber").html("");
        }

        if(phone_no == ""){
            isValid = false;
            $("#errorPhoneNumber").html("Phone Number cannot be empty");

        }else{
            $("#errorPhoneNumber").html("");
        }

        if(email == ""){
            isValid = false;
            $("#errorEmail").html("Email cannot be empty");

        }else{
            $("#errorEmail").html("");
        }

        if(password == ""){
            isValid = false;
            $("#errorPassword").html("Password cannot be empty");

        }else{
            $("#errorPassword").html("");
        }
        
//        if(password2 == "") || (password2 != password)
//        {
//            isValid = false;
//            $("#errorPassword2").html("Password is not same");
//        }
//        }else{
//            $("#errorPassword2").html("");
//            ok = false;
//        }
//        if (password != password2) {
//        //alert("Passwords Do not match");
//        document.getElementById("password").style.borderColor = "#E34234";
//        document.getElementById("password2").style.borderColor = "#E34234";
//        ok = false;
//    }
//    else {
//        alert("Passwords Match!!!");
//    }
//    return ok;
        // will only work if the data base is updated..but since spark has auto php thing we might not need it as well
        if(isValid == true){
            $.ajax({
            url: "/card_detail",
            type: "POST",
            data:{
                f_name: f_name,
                l_name: l_name,
                address: address,
                license_no: license_no,
                phone_no: phone_no,
                email: email,
                password: password
            },
            success: function(){
                //alert("Data was successfully stored");
            }
            });
        }else{
            return false;
        }

    });

});