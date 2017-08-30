//new validation procedure for inputs
//@author- vishal pradhan
//
jQuery().ready(function() {
 
  var v = jQuery("#contactform").validate({
      rules: {
        f_name: {
          required: true,
          minlength: 2,
          maxlength: 16
        },

        l_name: {
          required: true,
          minlength: 2,
          maxlength: 16
        },

        address: {
          required: true,
          minlength: 2,
          maxlength: 16
        },

        license_no: {
          required: true,
          minlength: 2,
          maxlength: 16
        },

        phone_no: {
          required: true,
          minlength: 2,
          maxlength: 16
        },
        email: {
          required: true,
          minlength: 2,
          email: true,
          maxlength: 100,
        },
        password1: {
          required: true,
          minlength: 6,
          maxlength: 15,
        },
        password2: {
          required: true,
          minlength: 6,
          equalTo: "#password1",
        }
 
      },
      errorElement: "span",
      errorClass: "help-inline",
    });

    // Binding next button on first step
    $(".open1").click(function() {
      if (v.form()) {
        $(".frm").hide("fast");
        $("#sf2").show("slow");
      }
   });
 
     // Binding back button on second step
    $(".back2").click(function() {
      $(".frm").hide("fast");
      $("#sf1").show("slow");
    });
    
    $("form.contactform").on('submit', function(){
       var that =$(this),
            url = that.attribute('action'),
            method = that.attribute('method'),
            data ={};
            
        that.name('[name]').each(function(index, value){
            var that =($this),
                name = that.attribute('name'),
                value = that.val();
                
            data[name]= value;
        });
        
        console.log(data);
       return false;
        
    });
 
 
//    $(".open3").click(function() {
//      if (v.form()) {
//     
         //optional
         //used delay form submission for a second and show a loader image
//        $("#loader").show();
//         setTimeout(function(){
//           $("#contactform").html('<h2>Thanks.</h2>');
//         }, 1000);
//        // Remove this if you are not using ajax method for submitting values
//        return false;
//      }
//    });
 
});





//ignore as this is the old validation procedure
// $.ajax({
//            url: "/process_register",
//            type: "POST",
//            data:{
//                f_name: f_name,
//                l_name: l_name,
//                address: address,
//                license_no: license_no,
//                phone_no: phone_no,
//                email: email,
//                password: password,
//                card_name: card_name,
//                card_no: card_no,
//                expiry_month: expiry_month,
//                expiry_year: expiry_year,
//                cvv: cvv
//            },
//            success: function(){
//                //alert("Data was successfully stored");
//            }
//            });
//        }else{
//            return false;
//        }




//$(document).ready(function(){
//
//    $("#sign_up").click(function(){
//        var f_name = $("#f_name").val();
//        var l_name = $("#l_name").val();
//        var address = $("#address").val();
//        var license_no = $("#license_no").val();
//        var phone_no = $("#phone_no").val();
//        var email = $("#email").val();
//        var password = $("#password").val();
//        var password2 =$("#password2").val();
//        var ok = true;
//
//
//        var isValid = true;
//
//        if(f_name==""){
//            isValid = false;
////            $("#errorFirstname").html("First Name cannot be empty");
//            alert("Field entry required");
//
//        }else{
//            $("#errorFirstname").html("");
//        }
//
//        if(l_name == ""){
//            isValid = false;
////            $("#errorLastname").html("Last Name cannot be empty");
//              alert("Field entry required");
//
//        }else{
//            $("#errorLastname").html("");
//        }
//
//        if(address == ""){
//            isValid = false;
////            $("#errorAddress").html("Address cannot be empty");
//              alert("Field entry required");
//
//        }else{
//            $("#errorAddress").html("");
//        }
//
//        if(license_no == ""){
//            isValid = false;
////            $("#errorLicenseNumber").html("License Number cannot be empty");
//              alert("Field entry required");
//
//        }else{
//            $("#errorLicenseNumber").html("");
//        }
//
//        if(phone_no == ""){
//            isValid = false;
////            $("#errorPhoneNumber").html("Phone Number cannot be empty");
//              alert("Field entry required");
//
//        }else{
//            $("#errorPhoneNumber").html("");
//        }
//
//        if(email == ""){
//            isValid = false;
////            $("#errorEmail").html("Email cannot be empty");
//              alert("Field entry required");
//
//        }else{
//            $("#errorEmail").html("");
//        }
//
//        if(password == ""){
//            isValid = false;
////            $("#errorPassword").html("Password cannot be empty");
//              alert("Field entry required");
//
//        }else{
//            $("#errorPassword").html("");
//        }
//        
//        // will only work if the data base is updated..but since spark has auto php thing we might not need it as well
//        if(isValid == true){
//            $.ajax({
//            url: "/card_detail",
//            type: "POST",
//            data:{
//                f_name: f_name,
//                l_name: l_name,
//                address: address,
//                license_no: license_no,
//                phone_no: phone_no,
//                email: email,
//                password: password
//            },
//            success: function(){
//                //alert("Data was successfully stored");
//            }
//            });
//        }else{
//            return false;
//        }
//
//    });
//
//});