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
    
    /* Public methods and variables */
    return {      
        clickablePlayground  : clickablePlayground,
    };
})();