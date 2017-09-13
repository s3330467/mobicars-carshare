/* 
 * @author: Vishal Pradhan
 * Date: 20-08-2017
 */

// this function opens up a sliding side nav bar when ever the search icon is pressed 
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("navopenbtn").style.width = "0px";
}

//this function closes the sliding nav bar when ever the "x" mark is pressed
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("navopenbtn").style.width = "50px";
}

var slider = new Slider('#ex2', {});


