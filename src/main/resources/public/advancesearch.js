/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    load_json_function('type');
    function load_json_data(id, parent_id)
    {
       var html_code = '';
       $.getJSON('car.json', function(data){
         html_code += '<option value="">Select '+id+'</option>';
         $.each(data, function(key, value){
             if(id =='type')
             {
                 if(value.parent_id =='0')
                 {
                     html_code += '<option value="'+value.id+'">'+value.name+'</option>';
                     
                 }
             }
             else
             {
                 if(value.parent_id == 'parent_id')
                 {
                     html_code += '<option value="'+value.id+'">'+value.name+'</option>';
                     
                 }
             }
         });
         $('#'+id).html(html_code);
       });
    }
    
    $(document).on('change', '#make', function(){
  var type_id = $(this).val();
  if(type_id !='')
  {
   load_json_data('make', type_id);
  }
  else
  {
   $('#make').html('<option value="">Select Make</option>');
   $('#model').html('<option value="">Select Model</option>');
  }
 });
 $(document).on('change', '#Make', function(){
  var make_id = $(this).val();
  if(make_id !='')
  {
   load_json_data('model', make_id);
  }
  else
  {
   $('#model').html('<option value="">Select Model</option>');
  }
    
    });
});
