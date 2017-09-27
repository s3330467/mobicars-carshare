/* 
 * @author: Vishal Pradhan
 * Date: 20-08-2017
 */

// this function opens up a sliding side nav bar when ever the search icon is pressed 
function openNav() {
    /*
     * Edited by Vishal Pradhan Date: 25-09-2017
     * Added additional pixel from 250 to 270 px
     */
    document.getElementById("mySidenav").style.width = "270px";
    document.getElementById("navopenbtn").style.width = "0px";
}

//this function closes the sliding nav bar when ever the "x" mark is pressed
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("navopenbtn").style.width = "50px";
}

var slider = new Slider('#ex2', {});


