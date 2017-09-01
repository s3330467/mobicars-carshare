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
          
          if(card_name == "" || card_no == "" || expiry_month == "" || expiry_year == "" || cvv =="")
          {
              isvalid = false;
              alert("Field entry is required")
          }
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