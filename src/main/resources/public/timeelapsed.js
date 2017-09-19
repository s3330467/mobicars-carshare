/* 
@author: Rachel
Date: 30.8.18

Edited: 6.9.17 by Rachel: Added time elapsed script that counts minutes and seconds.
Edited: 6.9.17 by Rachel: Edited time elapsed script to count hours.
Edited: 6.9.17 by Rachel: Fixed minutes as it kept incrementing after reaching 60.
 */
function startTimer(duration, display) {
    var timer = duration, hours, minutes, seconds;
    setInterval(function () {
        hours = parseInt (timer / 3600, 10);
        minutes = parseInt(timer / 60 % 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = hours + " hours " + minutes + " minutes " + seconds + " seconds";

        if (++timer < 0) {
            timer = duration;
        }
    }, 1000);
}

window.onload = function () {
    var timeElapsed = 0,
        display = document.querySelector('#time');
    startTimer(timeElapsed, display);
};