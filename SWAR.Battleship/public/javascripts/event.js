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
    
    $( "a.getSavegames" ).on({
        click: function() {
            controller.getSavegames();
        }
    });
    
    $( "a.setSavegame" ).on({
        click: function() {
            $('#saveModal').modal('toggle');
        }
    });
    
    $( "#saveGame" ).on({
        click: function() {
            if ($("#saveGameName").val() != "") {
                var name = $("#saveGameName").val();
                $("#saveGameName").val("");
                controller.saveGame(name);
            } else {
                alert("Enter a savegame name.");
            }
        }
    });
    
    $( "#loadGame" ).on({
        click: function() {
            if (loadSelect !== undefined && loadSelect.selectedOptions !== undefined && loadSelect.selectedOptions.length > 0) {
                sel = loadSelect.selectedOptions[0].text
                console.log("Load Game "+sel);
                controller.loadGame(sel);
                $('#loadModal').modal('toggle');
            }
        }
    });
    
    /* Public methods and variables */
    return {      
        clickablePlayground  : clickablePlayground,
    };
})();