

$(document).ready(function(){

    $("#sign_up").click(function(){
        var f_name = $("#f_name").val();
        var l_name = $("#l_name").val();
        var address = $("#address").val();
        var license_number = $("#license_number").val();
        var phone_number = $("#phone_number").val();
        var email = $("#email").val();
        var password = $("#password").val();


        var isValid = true;

        if(f_name == ""){
            isValid = false;
            $("#errorFirstname").html("First Name cannot be empty");

        }else{
            $("#errorLastname").html("");
        }

        if(l_name == ""){
            isValid = false;
            $("#errorname").html("Last Name cannot be empty");

        }else{
            $("#errorLastname").html("");
        }

        if(address == ""){
            isValid = false;
            $("#errorAddress").html("Address cannot be empty");

        }else{
            $("#errorAddress").html("");
        }

        if(license_number == ""){
            isValid = false;
            $("#errorLicenseNumber").html("License Number cannot be empty");

        }else{
            $("#errorLicenseNumber").html("");
        }

        if(phone_number == ""){
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