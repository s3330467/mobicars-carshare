

$(document).ready(function(){

    $("#sign_in").click(function(){
        var email = $("#login_email").val();
        var password = $("#login_password").val();


        var isValid = true;

        if(email == ""){
            isValid = false;
            $("#error_Email").html("Email cannot be empty");

        }else{
            $("#error_Email").html("");
        }

        if(password == ""){
            isValid = false;
            $("#error_Password").html("Password cannot be empty");

        }else{
            $("#error_Password").html("");
        }
        // will only work if the data base is updated..but since spark has auto php thing we might not need it as well
//        if(isValid == true){
//            $.ajax({
//            url: "/process_register",
//            type: "POST",
//            data:{
//                f_name: f_name,
//                l_name: l_name,
//                address: address,
//                license_number: license_number,
//                phone_number: phone_number,
//                email: email,
//                password: password
//            },
//            success: function(){
//
//            }
//            });
//        }else{
//            return false;
//        }

    });

});