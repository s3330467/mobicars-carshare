/*
    @author Rachel Tan
    Date: 30.8.17
    A timer than counts down from a specified time, every second
    05/09/17 Edited by Alexander Young
    timer now subtracts time already elapsed to accurately display the time remaining
    when timer expires the user is given an error and redirected back to the map
*/
function startCollectionCountdown(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;
        
        if (timer < 180) {
            display.style.color = 'red';
        }
        if (--timer < 0) {
            alert("Your booking has been canceled because you did not collect your car within 15 minutes");
            window.location = "/";
        }
    }, 1000);
}

window.onload = function () {
    var timeRemaining = (60 * 15) - $timeSinceBooking,
        display = document.querySelector('#time');
    startCollectionCountdown(timeRemaining, display);
};