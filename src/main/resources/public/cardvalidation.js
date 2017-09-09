

var userNumInput = document.getElementById("card_no");

function getUserInput(){
   return userNumInput.value;  
}

function luhnCheck(){
  var ccNum = getUserInput(), ccNumSplit = ccNum.split(""), sum = 0;
  var singleNums = [], doubleNums = [], finalArry = undefined;
  var validCard = false;
  
  if((!/\d{15,16}(~\W[a-zA-Z])*$/g.test(ccNum)) || (ccNum.length > 16)){
//     return false;
     alert('not a valid card');
  }

  if(ccNum.length === 15){  //american express 
     for(var i = ccNumSplit.length-1; i>=0; i--){
        if(i % 2 === 0){
           singleNums.push(ccNumSplit[i]);
        }else{
           doubleNums.push((ccNumSplit[i] * 2).toString());
        }
     }
  }else if(ccNum.length === 16){
     for(var i = ccNumSplit.length-1; i>=0; i--){
        if(i % 2 !== 0){
           singleNums.push(ccNumSplit[i]);
        }else{
           doubleNums.push((ccNumSplit[i] * 2).toString());
        }
     }
  }
  //joining makes an array to a string and I split them up again
  //so that every number is a single digit and convert back to array
  
  doubleNums = doubleNums.join("").split("");  
  finalArry = doubleNums.concat(singleNums);
  
  for(var j = 0; j<finalArry.length; j++){
     sum += parseInt(finalArry[j]);
  }
  
  if(sum % 10 === 0){
     validCard = true;
  }
  //the console log is for you, so you can see the sum, all sums that are
  //divisible by 10 should be good.  Just open up your console to view.
  
  console.log(sum);
return validCard;
alert(validCard);
}

function whatCard(){
     var ccNum = getUserInput();
     var ccObj = {
         "visa": /^4[0-9]{6,}$/g,
         "mastercard": /^5[1-5][0-9]{5,}$/g,
         "american express": /^3[47][0-9]{5,}$/g,
         "discover": /^6(?:011|5[0-9]{2})[0-9]{3,}$/g,
         "jcb": /^(?:2131|1800|35[0-9]{3})[0-9]{3,}$/g
         
     };
    
    
    
}

document.getElementById("submit").addEventListener("click", function(){
   document.getElementById("resultDiv").innerHTML = luhnCheck();
}, false);

