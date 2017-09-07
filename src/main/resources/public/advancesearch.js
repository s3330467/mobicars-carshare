/* 
   @autor: Vishal Pradhan
 */

$(document).ready(function(){

 load_json_data('type');

 function load_json_data()
 {
    var typeOption = '';
    var makeOption = '';
    var modelOption = '';
    
    $.getJSON("/get_all_types", function(data){
        typeOption += '<option value="">Select Type</option>';
            $.each(data, function(type, car_type){
                typeOption+="<option value='"+type+"'>"+car_type+"</option>";
            });
       
         $('#type').html(typeOption);
         
      });
      $('#type').change(function(){
          var make = $(this).val();
          var j;
          for(j = 0; j < make.length; j++)
          {
          if(make !== ''){
              load_json_data('make');
               $.getJSON("/get_all_makes", function(data){
                makeOption += '<option value="">Select Make</option>';
                    $.each(data, function(make, car_make){
                        makeOption+="<option value='"+make+"'>"+car_make+"</option>";
                });
            $('#make').html(makeOption);
            });
          };
          break ;
         }
         });
//         alert('car_make');
         $('#make').change(function(){
          var model = $(this).val();
          var k;
          for(k = 0; k < model.length; k++)
          {
          if(type === "sports" || model === "Nissan"){
              load_json_data('model');
               $.getJSON("/get_models_by_make", function(data){
                modelOption += '<option value="">Select Model</option>';
                    $.each(data, function(model, car_model){
                        modelOption+="<option value='"+model+"'>"+car_model+"</option>";
               });
            $('#model').html(modelOption);
            });
          };
          break ;
         }
         });
         
     };
});
 
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
//    if(id === 'type')
//    {
//     if(car.type === car.type)
//     {
//      html_code += '<option value="'+car.id+'">'+car.type+'</option>';
//     }
//    }
//    
//    else//this is what populates the other two dropdown below the type... 
//    {
//     if(car.type === car.type )
//     {
//      html_code += '<option value="'+car.id+'">'+car.make+'</option>';
//     }
//    }
//    else // just made this to see because the model section is also populating the make output so basically its not working
//    {
//      if (car.id === car.model)
//      {
//        html_code += '<option value="'+car.id+'">'+car.model+'</option>';
//      }
//    }
//   });
//   $('#'+id).html(html_code);
//  });
//
// }
// $(document).on('change', '#type', function(){
//  var type = $(this).val();
// 
//  if(type !== '')
//  {
//   load_json_data('make');
//    alert('second function');
//  }
//  else
//  {
//   $('#make').html('<option value="">Select Make</option>');
//   $('#model').html('<option value="">Select Model</option>');
//  }
// });
// $(document).on('change', '#make', function(){
//     
//  var make = $(this).val();
//  if(make !== '')
//  {
//   load_json_data('model');
//   alert('third part');
//  }
//  else
//  {
//   $('#model').html('<option value="">Select Model</option>');
//  }
// });
//});








//$(document).ready(function(){
////    alert('test');
//    load_json_function('type');
//    
//    function load_json_function(id)
//    {
////       alert('test');
//       var html_code = '';
//       var type_list = [100];
//       var j;
//       $.getJSON("/cars", function(cars){
//         html_code += '<option value="">Select '+id+'</option>';
//         $.each(cars, function(i, car){
//             
//             for (j = 0; j < type_list.length; j++)
//             {
//                 
//                if(type_list[j]=== car.type)
//                {
//                    //alert(car.type + ' is already in the list');
//                    break;
//                }
//              if(id ==='type')
//             {
//                     //alert(car.type + ' is already not in the list');
//                    type_list.push(car.type);
//                    html_code += '<option value="'+car.type+'">'+car.type+'</option>';
//               
//             }
//         }
////             else
////             {
////                 if(value.parent_id === 'parent_id')
////                 {
////                     html_code += '<option value="'+value.id+'">'+value.name+'</option>';
////                     
////                 }
////             }
//        
//         });
//         alert(html_code);
//         $(type).html(html_code);
//       });
//   
//    }
//    
////    $(document).on('change', '#type', function(){
////  var type_id = $(this).val();
////  if(type_id !=='')
////  {
////   load_json_data('make', type_id);
////  }
////  else
////  {
////   $('#make').html('<option value="">Select Make</option>');
////   $('#model').html('<option value="">Select Model</option>');
////  }
//// });
//// $(document).on('change', '#make', function(){
////  var make_id = $(this).val();
////  if(make_id !=='')
////  {
////   load_json_data('model', make_id);
////  }
////  else
////  {
////   $('#model').html('<option value="">Select Model</option>');
////  }
////    
////    });
//});
