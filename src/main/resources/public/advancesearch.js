/* 
* @autor: Vishal Pradhan
* Date: 20-08-2017
* First implemented on the date mentioned above and since then a lot of changes occured with this script
 */
$(document).ready(function () {
    /*
     * Edited: Vishal Pradhan
     * date: 16-09-2017
     * added slider bars for distance and price
     */ 
//        $('#price_min').change(function(){  
//           var price = $(this).val();  
//           $("#price_range").text("Cars under Price $" + price);  
//           $.ajax({  
//                url:"loadNearbyCars();",  
//                method:"POST",  
//                data:{price:price},  
//                success:function(data){  
//                     $("#price_range").fadeIn(500).html(data);  
//                }  
//           }); 
//             
//      });  
//        $('#distance_min').change(function(){  
//           var distance = $(this).val();  
//           $("#distance_range").text("Cars within the Distance of " + distance + " Km");  
//           $.ajax({  
////                 $loadNearbyCars();
//           }); 
//             
//      }); 

        /*
       * Edited: Vishal Pradhan
       * Date: 19-09-2017
       * New function to display the range based on the selected parameter by the user
       */
        var slider1 = document.getElementById("price_min");
        var output1 = document.getElementById("price_range");
        if(slider1.value === "0"){
            output1.innerHTML= "Any";
        }
        else 
        {
            output1.innerHTML = slider1.value;
        }
      
        slider1.oninput = function() {
        if(slider1.value === "0"){
            output1.innerHTML= "Any";
        }
        else
        {
        output1.innerHTML = this.value;
        }
        };
        
        var slider = document.getElementById("distance_min");
        var output = document.getElementById("distance_range");
        if(slider.value === "0"){
            output.innerHTML= "Any";
        }
        else 
        {
            output.innerHTML = slider.value;
        }

        slider.oninput = function() {
            if(slider.value === "0"){
            output.innerHTML= "Any";
        }
        else
        {
        output.innerHTML = this.value;
        }    
        };
      
      /*
       * Edited: 07-09-2017
       * this function gets data from a Json file at the front end and loads up the map 
       * as per the users selection..
       * this is a dynamic dependent drop down list of items where the second dropdown lists
       * depends on the selection of the first list by the user
       */
    load_json_data();
    function load_json_data() {
        var typeOption = '';
        var makeOption = '';
        var modelOption = '';
        
        /*
         * gets a json file called get_all_type and fills the search dropdown with non duplicate
         *values of the different types of cars available
         */
        $.getJSON("/get_all_types", function (data) {
            typeOption = '<option value="empty">Any Type</option>';
            $.each(data, function (i, car_type) {
                typeOption += "<option value='" + car_type + "'>" + car_type + "</option>";
            });
            $('#type').html(typeOption);
        });
       /*
         * gets a json file called get_all_makes and fills the search dropdown with non duplicate
         *values of the different makes of cars available
         *This dropdown is independent of the previous one in a way that a user can select
         *just the make as well without selecting the type
         */
        $.getJSON("/get_all_makes", function (data) {
            makeOption = '<option value="empty">Any Make</option>';
            $.each(data, function (i, car_make) {
                makeOption += "<option value='" + car_make + "'>" + car_make + "</option>";
            });
            $('#make').html(makeOption);
        });
        
         /*
         * Load the value depending on the first selection of the users.
         */
        $('#type').change(function () {
            var make = $(this).val();
            var j;
            for (j = 0; j < make.length; j++)
            {
                if ($(this).val() !== '') {
                    $.getJSON("/get_make_by_type",{type: $(this).val()}, function (data) {
                        makeOption = '<option value="empty">Select Make</option>';
                        $.each(data, function (make, car_make) {
                            makeOption += "<option value='" + car_make + "'>" + car_make + "</option>";
                        });
                        $('#make').html(makeOption);

                    });
                };
                break;
            }
        });
        /*
         * Load the value depending on the previous two selection made by the user.
         */
        $('#make').change(function () {
            var k;
            for (k = 0; k < model.length; k++)
            {
                if ($(this).val() !== '') {
                    $.getJSON("/get_models_by_make", {make: $(this).val()}, function (data) {
                        modelOption = '<option value="empty">Any Model</option>';
                        $.each(data, function (model, car_model) {
                            modelOption += "<option value='" + car_model + "'>" + car_model + "</option>";
                        });
                        $('#model').html(modelOption);
                    });
                };
                break;
            }
        });
    }
    ;
    /*
     * Edited: 19-09-2017 by Vishal Pradhan
     * added distance and price variable to the search function.
     * when the user clicks the search button, cars based on the selection of the user are displayed
     */
    document.getElementById("search-btn").onclick = function () {
        $.getJSON("/search_for_cars", {make: $('#make').val(), model: $('#model').val(), type: $('#type').val()}, function (searchResults) {
            if (searchResults !== null) {
                unloadAllCars();
                $.each(searchResults, function (i, car) {
                    var distance = $('#distance_min').val();
                    var maxCost = $('#price_min').val();
                    if(distance === "0")
                    {
                        distance = 100;
                    }
                    if(maxCost === "0")
                    {
                        maxCost = 100000;
                    }
                    loadSearchResults(searchResults, distance, maxCost);
                   
                    
                });
            }
        });
    };
  
    /*
     * this version of find nearest car does not find the nearest car based on search results, only out of all cars
     * needs to be refined
     */
    document.getElementById("find-nearest-btn").onclick = function () {
        var distanceFromUser, lowestDistance, carPos;
        lowestDistance = 100000000;
        unloadAllCars();
        loadNearbyCars();
        $.getJSON("/cars", function (cars) {
            if (cars !== null) {
                $.each(cars, function (i, car) {
                    carPos = new google.maps.LatLng(car.lat, car.lng);
                    distanceFromUser = google.maps.geometry.spherical.computeDistanceBetween(carPos, userCoords) / 1000;
                    if(distanceFromUser < lowestDistance) {
                        lowestDistance = distanceFromUser;
                    }
                });
                $.each(cars, function (i, car) {
                    carPos = new google.maps.LatLng(car.lat, car.lng);
                    distanceFromUser = google.maps.geometry.spherical.computeDistanceBetween(carPos, userCoords) / 1000;
                    if(distanceFromUser === lowestDistance) {
                        map.setCenter(carPos);
                        map.setZoom(17);
                    }
                });
            }
        });
    };
    
    document.getElementById("reset-map-btn").onclick = function () {
        unloadAllCars();
        loadNearbyCars();
        load_json_data();
    };
});
