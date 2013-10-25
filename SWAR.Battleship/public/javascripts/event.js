$( document ).ready(function() {    
    $( "img.enemyPlaygroundCell" ).on({
      click: function() {
        console.log($( this ).data());
      }
    });
    
    $( "img.ownPlaygroundCell" ).on({
      click: function() {
        console.log("Can't shoot to own playground");
      }
    });
});