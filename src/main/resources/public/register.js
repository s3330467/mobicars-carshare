/*new validation procedure for inputs
* @author: Vishal Pradhan
* Date: 13-08-2017
*/
/*
 * Earlier version deleted due to new addition of fields to the register function
 * originally dated 13-08-2017 and since than has been reformatted
 */

/*
 * @Author:  Vishal Pradhan
 * Edited on: 29-08-2017
 */
jQuery().ready(function() {
    
 //general rules that the variables need to follow while the form field is getting
 //filled with clients input
 
  var v = jQuery("#contactform").validate({
      rules: {
        f_name: {
          required: true,
          maxlength: 16
        },

        l_name: {
          required: true,
          
          maxlength: 16
        },

        address: {
          required: true,
          maxlength: 50
        },

        license_no: {
          required: true,
          minlength: 6,
          maxlength: 16
        },

        phone_no: {
          required: true,
          minlength: 8,
          maxlength: 10
        },
        email: {
          required: true,
          email: true,
          maxlength: 20,
//          accept:"[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}"
        },
        password1: {
          required: true,
          minlength: 8,
          maxlength: 15
        },
        password2: {
          required: true,
          equalTo: "#password1"
        },
        card_name: {
          required: true,
          minlength: 2,
          maxlength: 16
        },
        card_no: {
          required: true
        },
        expiry_month: {
          required: true
        },
        expiry_year: {
          required: true
        },
        cvv: {
          required: true,
          minlength: 3,
          maxlength: 3
        }
 
      },
      errorElement: "span",
      errorClass: "help-inline"
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
    
    /*@author: Vishal Pradhan
    *Date: 13-08-2017
    * this function checks that on clicking the submit button the variable are assigned to the 
    * ID of the input.
    */
    $(document).ready(function(){
       $('#submit').click(function(){
          var f_name = $("#f_name").val();
          var l_name = $("#l_name").val();
          var address = $("#address").val();
          var license_no = $("#license_no").val();
          var phone_no = $("#phone_no").val();
          var email = $("#email").val();
          var password1 = $("#password1").val();
          var password2 =$("#password2").val();
          var card_name =$("#card_name").val();
          var card_no =$("#card_no").val();
          var expiry_month =$("#expiry_month").val();
          var expiry_year =$("#expiry_year").val();
          var cvv =$("#cvv").val();
          var isvalid= true;
          
          /*
           * edited on : 29-08-2017
           * checks to see if the field are not empty.
           */
          if(card_name == "" || card_no == "" || expiry_month == "" || expiry_year == "" || cvv =="")
          {
              isvalid = false;
              alert("Field entry is required")
          }
          //if all the fields are full it processes that data into DB using ajax 
             if(isValid == true){
                $.ajax({
                url: "/process_register",
                type: "POST",
                data:{
                    f_name: f_name,
                    l_name: l_name,
                    address: address,
                    license_no: license_no,
                    phone_no: phone_no,
                    email: email,
                    password: password1,
                    card_name: card_name,
                    card_no: card_no,
                    expiry_month: expiry_month,
                    expiry_year: expiry_year,
                    cvv: cvv
                },
                success: function(){
                    alert("Data was successfully stored");
                }
                });
            }else{
                return false;
            }

        });
          
       }); 
        
        
    });
//    