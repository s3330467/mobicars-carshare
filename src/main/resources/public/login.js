/*
 * @Author: Vishal Pradhan
 * Date: 20-08-2017
 */

/*this function checks if the user has the input field filled up..
 * if not a warning is displayed underneath the input field
 */
$(document).ready(function(){

    $("#sign_in").click(function(){
        var email = $("#email").val();
        var password = $("#password").val();


        var isValid = true;
        //checks if the email field is not empty
        if(email == ""){
            isValid = false;
            $("#error_Email").html("Email cannot be empty");

        }else{
            $("#error_Email").html("");
        }
        //checks if the password field is not empty
        if(password == ""){
            isValid = false;
            $("#error_Password").html("Password cannot be empty");

        }else{
            $("#error_Password").html("");
        }
        
        /*once all the criteria is fulfilled, the form is processed towards process_login
         * where the process is validated at the back end of the application to see
         * if the user exists or not... 
         */
        
        if(isValid == true){
            $.ajax({
            url: "/process_login",
            type: "POST",
            data:{
                email: email,
                password: password
            },
            success: function(){

            }
            });
        }else{
            return false;
        }

    });

});