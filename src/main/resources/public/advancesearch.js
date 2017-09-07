/* 
 @autor: Vishal Pradhan
 */

$(document).ready(function () {

    load_json_data();
    function load_json_data() {
        var typeOption = '';
        var makeOption = '';
        var modelOption = '';

        $.getJSON("/get_all_types", function (data) {
            typeOption = '<option value="empty">Select Type</option>';
            $.each(data, function (i, car_type) {
                typeOption += "<option value='" + car_type + "'>" + car_type + "</option>";
            });
            $('#type').html(typeOption);
        });
        $.getJSON("/get_all_makes", function (data) {
            makeOption = '<option value="empty">Select Make</option>';
            $.each(data, function (i, car_make) {
                makeOption += "<option value='" + car_make + "'>" + car_make + "</option>";
            });
            $('#make').html(makeOption);
        });
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
                }
                ;
                break;
            }
        });

        $('#make').change(function () {
            var k;
            for (k = 0; k < model.length; k++)
            {
                if ($(this).val() !== '') {
                    $.getJSON("/get_models_by_make", {make: $(this).val()}, function (data) {
                        modelOption = '<option value="empty">Select Model</option>';
                        $.each(data, function (model, car_model) {
                            modelOption += "<option value='" + car_model + "'>" + car_model + "</option>";
                        });
                        $('#model').html(modelOption);
                    });
                }
                ;
                break;
            }
        });
    }
    ;
    document.getElementById("search-btn").onclick = function () {
        $.getJSON("/search_for_cars", {make: $('#make').val(), model: $('#model').val(), type: $('#type').val()}, function (searchResults) {
            if (searchResults !== null) {
                unloadAllCars();
                $.each(searchResults, function (i, car) {
                    loadSearchResults(searchResults);
                });
            }
        });
    };
    
    document.getElementById("find-nearest-btn").onclick = function () {
        var distanceFromUser, lowestDistance, carPos;
        lowestDistance = 100000000;
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
