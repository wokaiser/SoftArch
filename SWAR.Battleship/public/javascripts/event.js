$( document ).ready(function() {    
    $( "img.enemyPlaygroundCell" ).on({
        click: function() {
            controller.shoot($( this ).data());
            console.log($( this ).data());
        }
    });
    
    $( "img.ownPlaygroundCell" ).on({
        click: function() {
            console.log("Can't shoot to own playground");
        }
    });

    $( "a.newSinglePlayerGame" ).on({
        click: function() {
            controller.newSinglePlayerGame();
        }
    });
    
});