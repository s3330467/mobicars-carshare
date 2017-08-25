/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("navopenbtn").style.width = "0px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("navopenbtn").style.width = "50px";
}

var slider = new Slider('#ex2', {});


