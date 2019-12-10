
$(document).ready(function () {









});






    function toggleNav() {

        var sideNavBar = document.getElementById("sidebar");
        
        var openBtn = document.getElementById("openbtn");
         var closeBtn = document.getElementById("closebtn");

        if (sideNavBar.style.width = '0px') {
       
            sideNavBar.style.width = '200px';
         closeBtn.style.display = 'block';
           openBtn.style.display = 'none';
        }
    }



function toggleNavClose(){
     var sideNavBar = document.getElementById("sidebar");
     var closeBtn = document.getElementById("closebtn");
     var openBtn = document.getElementById("openbtn");
     
      if (sideNavBar.style.width = '200px') {

            sideNavBar.style.width = '0px';
            
            closeBtn.style.display = 'none';
        
            openBtn.style.display = 'block';
           
        }
    
}




