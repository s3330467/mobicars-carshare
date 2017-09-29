/* 
 @author: Rachel
 Date: 30.8.18
 
 Edited: 6.9.17 by Rachel: Added time elapsed script that counts minutes and seconds.
 Edited: 6.9.17 by Rachel: Edited time elapsed script to count hours.
 Edited: 6.9.17 by Rachel: Fixed minutes as it kept incrementing after reaching 60.
 Edited: 29.9.17 by Alexander Young: added a countdown timer that counts down to the expected end time of the booking
 */
function startTimer(duration, display) {
    var timer = duration, hours, minutes, seconds;
    setInterval(function () {
        hours = parseInt(timer / 3600, 10);
        minutes = parseInt(timer / 60 % 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = hours + ":" + minutes + ":" + seconds;
        if (++timer < 0) {
            timer = duration;
        }
    }, 1000);
}

/*
 @author Rachel Tan
 Date: 30.8.17
 A timer than counts down from a specified time, every second
 05/09/17 Edited by Alexander Young
 timer now subtracts time already elapsed to accurately display the time remaining
 when timer expires the user is given an error and redirected back to the map
 */

function startExpiryCountdown(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        hours = parseInt(timer / 3600, 10);
        minutes = parseInt(timer / 60 % 60, 10);
        seconds = parseInt(timer % 60, 10);

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        
        display.textContent = hours + ":" + minutes + ":" + seconds;

        if (timer < 1800) {
            display.style.color = 'orange';
        }
        if (--timer < 0) {
            display.style.color = 'red';
            display.textContent = "Please return the car as soon as possible. You are being charged double for having an overdue booking";
        }
    }, 1000);
}
window.onload = function () {
    var elapsedTimer = document.querySelector('#time');
    var expiryCountdown = document.querySelector('#countdown');
    $.post("/process_get_time_elapsed_since_collection",
            function (result)
            {
                startTimer(result, elapsedTimer);
            });
    $.post("/process_get_time_before_expiry",
            function (result)
            {
                var timeRemaining = (60 * 15) - result;
                startExpiryCountdown(timeRemaining, expiryCountdown);
            });
};