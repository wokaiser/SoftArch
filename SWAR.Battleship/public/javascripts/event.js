var eventHandle = (function () {     

    var clickablePlayground = function () {
        $( "img.enemyPlaygroundCell" ).on({
            click: function() {
                controller.shoot($( this ).data());
            }
        });
    };
    
    $( "a.newSinglePlayerGame" ).on({
        click: function() {
            controller.newSinglePlayerGame();
        }
    });
    
    $( "a.newMultiPlayerGame" ).on({
        click: function() {
            controller.newMultiPlayerGame();
        }
    });
    
    $( "a.Highscore" ).on({
        click: function() {
            controller.getHighscore();
        }
    });
    
    /* Public methods and variables */
    return {      
        clickablePlayground  : clickablePlayground,
    };
})();